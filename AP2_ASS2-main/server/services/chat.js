const {Chats,Messages } = require('../models/chat.js');
const { usersData} = require("../models/users");


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
    if (username === newContact) {
        return 400;
    }

    let newUser = await usersData.findOne({ username: newContact });
    const user = await usersData.findOne({ username: username });

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
        const sender = await usersData.findOne({"username" : connectUser})
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
        return returnVal;
    }
    return null
};
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
module.exports = {getChats,addMessage,addChat,deleteChat,getMessages,getChatByID};

