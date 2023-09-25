const {Chats,Messages } = require('../models/chat.js');
const { usersData} = require("../models/users");
const fireBaseDictionary = require('../models/dictionary');
const {sendAlertToSocket} = require("../controllers/sockets")


//return all contacts (GET/api/chat)
const getChats = async (username) => {
    const jsonArray = [];
    const chats = await Chats.find();

    if (chats !== null) {
        for (const chat of chats) {
            const user1 = await usersData
                .findOne({ "_id": chat.users[0] })
                .populate("username displayName profilePic")
                .lean();
            const user2 = await usersData
                .findOne({ "_id": chat.users[1] })
                .populate("username displayName profilePic")
                .lean();

            if (user1.username === username || user2.username === username) {
                let lastMessage = null;

                if (chat.messages.length > 0) {
                    const message = await Messages.findOne({
                        _id: chat.messages[chat.messages.length - 1]
                    })
                        .populate("id created content")
                        .lean();

                    lastMessage = {
                        id: message.id,
                        created: message.created,
                        content: message.content
                    };
                }

                let otherUser;
                if (user1.username === username) {
                    otherUser = {
                        username: user2.username,
                        displayName: user2.displayName,
                        profilePic: user2.profilePic
                    };
                } else {
                    otherUser = {
                        username: user1.username,
                        displayName: user1.displayName,
                        profilePic: user1.profilePic
                    };
                }

                const jsonObject = {
                    id: chat.id,
                    user: otherUser,
                    lastMessage: lastMessage
                };
                jsonArray.push(jsonObject);
            }
        }
    }
    return jsonArray;
};


//add contact (POST/api/chat)
const addChat = async (username, newContact) => {
    //todo========================================
    if (username === newContact) {
        return 400;
    }

    let newUser = await usersData.findOne({ username: newContact });
    const user = await usersData.findOne({ username: username });
    sendReloadChatsOnFireBase(fireBaseDictionary[newUser.username])

    if (newUser && user) {
        const maxChatID = await Chats.findOne().sort('-id').limit(1).exec();
        let chatID = 1;

        if (maxChatID) {
            chatID = maxChatID.id + 1;
        }

        const newChat = new Chats({
            "id": chatID,
            "users": [user, newUser],
            "messages": []
        });
        await newChat.save();
        newUser = await newUser.populate("username displayName profilePic");
        const jsonObject = {
            "username": newUser.username,
            "displayName": newUser.displayName,
            "profilePic": newUser.profilePic
        };
        return {
            "id": chatID,
            "user": jsonObject
        };
    }

    return null;
};

//get contact by id (GET/api/chat/{id})
const getChatByID = async (id,connectUser) => {
    const chat = await Chats.findOne({ "id": id });

    if (chat) {

        if (await inChat(connectUser, chat.users[0], chat.users[1])){
            return null
        }
        const messageArray = [];
        const usersArray = [];

        for (const msg of chat.messages) {
            const newMsg = await Messages.findOne({ _id: msg });
            const msgWithSender = await Messages
                .findOne({ _id: newMsg._id })
                .populate({
                    path: 'sender',
                    select: 'username displayName profilePic',
                    model: 'usersData'
                })
                .lean();
            const theSender = await usersData.findOne(newMsg.sender);
            messageArray.push({
                "id": newMsg.id,
                "created": newMsg.created,
                "sender": {
                    "username" : theSender.username,
                    "displayName" :theSender.displayName,
                    "profilePic" : theSender.profilePic
                },
                "content": newMsg.content
            });
        }

        for (const user of chat.users) {
            const newUser = await usersData.findOne({ _id: user }).populate('username displayName profilePic')
            usersArray.push({
                "username": newUser.username,
                "displayName": newUser.displayName,
                "profilePic": newUser.profilePic
            });
        }

        return {
            id: id,
            users: usersArray,
            messages: messageArray
        };
    }

    return null;
};

//Delete chat by id (POST/api/chat/{id}
const deleteChat = async (id) => {
    return Chats.deleteOne({"id":id});
};


//add message to the chat that has this id (POST/api/chats/{id}/messages
const addMessage = async (id,content,connectUser) => {

    const chat = await Chats.findOne({ "id" :id });

    if (chat) {
        if (await inChat(connectUser, chat.users[0], chat.users[1])){
            return null
        }
        //sending to the fire base
        await findIdAndSendFireBase(connectUser, chat.users[0], chat.users[1],content)
        const sender = await usersData.findOne({"username" : connectUser})
        const recipient  = await getRecipient(connectUser, chat.users[0], chat.users[1]);
        const maxMessageID = await Messages.findOne().sort('-id').limit(1).exec();
        let messageID = 1;
        if (maxMessageID && maxMessageID.id) {
            messageID = maxMessageID.id + 1;
        }
        const newMessage = await new Messages({
            "id": messageID,
            "sender": sender,
            "content": content
        });


        const filteredSender = await sender.populate('username displayName profilePic')
        const returnVal={
            "id" : messageID,
            "created": new Date(),
            "sender" :  {   "username": filteredSender.username,
                            "displayName": filteredSender.displayName,
                            "profilePic": filteredSender.profilePic
                        },
            "content" : content
        }

        await newMessage.save();
        chat.messages.push(newMessage);
        await chat.save();
        sendAlertToSocket(recipient.username,filteredSender.username)

        return returnVal;

    }
    return null
};

const getRecipient = async (connectUser ,id1, id2) =>{
    const user1 = await usersData.findOne(id1)
    const user2 = await usersData.findOne(id2)
    if (user1.username !== connectUser){
        return user1;
    }
    return user2;

}

const inChat= async (connected,id1,id2) =>{
    //check that the sender is in the chat
    const user1 = await usersData.findOne(id1)
    const user2 = await usersData.findOne(id2)
    return !(connected === user1.username || connected === user2.username);

}
//return all the message between the login user and the id contact (GET/api/chats/{id}/messages
const getMessages = async (id,connectUser) => {
    const chatArray =[]
    const chat = await Chats.findOne({"id" : id});
    if(chat){
        if (await inChat(connectUser, chat.users[0], chat.users[1])){
            return null
        }
        for (const msg of chat.messages) {
            const foundMsg = await Messages.findOne(msg);
            const sender = await usersData.findOne(foundMsg.sender).populate('username')
            chatArray.push({
                "id": foundMsg.id,
                "created": foundMsg.created,
                "sender": {
                    "username":sender.username
                },
                "content": foundMsg.content
            });
        }
        return chatArray.reverse();
    }
    return null;

};

const admin = require('firebase-admin');
const serviceAccount = require(process.env.FIREBASE_KEY_PATH);
admin.initializeApp({
    credential: admin.credential.cert(serviceAccount)
});


async function findIdAndSendFireBase(connected, id1, id2,content) {
    let recipient
    const user1 = await usersData.findOne(id1)
    const user2 = await usersData.findOne(id2)
    if(connected === user1.username){
        recipient=user2.username
    }else {
        recipient=user1.username
    }
    sendOnFireBase(fireBaseDictionary[recipient],content,connected)
}
function sendOnFireBase(registrationToken,content,sender){
    if (registrationToken === undefined){
        return
    }
    const message = {
        notification: {
            title: sender,
            body: content,
        },
        token: registrationToken,
        data: {
            key:"fromServer"
        }

    };

// Send the message
    admin.messaging().send(message)
        .then((response) => {
            //console.log('Successfully sent message:', response);
        })
        .catch((error) => {

        });
}
function sendReloadChatsOnFireBase(registrationToken){
    if (registrationToken === undefined){
        return
    }
    console.log("send to update")
    console.log("token: ",registrationToken)
    const message = {
        notification: {
            title:"",
            body: "new friend add you",
        },
        token: registrationToken,
        data: {
            key:"fromServer"
        }
    };

// Send the message
    admin.messaging().send(message)
        .then((response) => {
            console.log('Successfully sent message:', response);
        })
        .catch((error) => {
            console.log('Error sending message:', error);
        });
}


module.exports = {getChats,addMessage,addChat,deleteChat,getMessages,getChatByID};

