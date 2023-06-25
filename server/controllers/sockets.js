const connectedUsers = new Map();

function assignNewSocket(username,io){
    io.on('connection', (socket) => {
        if (!connectedUsers.has(username)){
            connectedUsers.set(username, socket.id);
        }
    });
}
function sendWithSocket(recipient,io){
    for (let [key, value] of connectedUsers) {
    }
    const recipientSocketId = connectedUsers.get(recipient);
    if (recipientSocketId) {

        io.on('connection', socket => {
            socket.on('messageSent',() => {
                socket.to(10).emit('message')
            });
        });
        io.to(recipientSocketId).emit('message',{ foo : 'bar' });
    }
}

function socketOnConnection (io){

    let numOfMessages=1
    io.on('connection', (socket) => {
        socket.on('join',(data) => {
            if(!connectedUsers.has(socket.id)){
                connectedUsers.set( data , socket.id)
                socket.join(10)
            }
        });
        socket.on('messageSent',(from,to) => {
            if(connectedUsers.get(to) !== undefined){
                io.to(connectedUsers.get(to)).emit('message',{ num : numOfMessages, sender: from })
            }
            numOfMessages++


        });

        // Remove the user from connectedUsers map when they disconnect
        socket.on('disconnect', () => {

            for (let [key, value] of connectedUsers.entries()) {
                if (value === socket.id) {
                   connectedUsers.delete(key);
                }
            }
        });

    });
}

// const admin = require('firebase-admin');
//
// const serviceAccount = require('../wink-android-32c12-firebase-adminsdk-kz9v4-cfc7b2ba24.json');
//
// admin.initializeApp({
//     credential: admin.credential.cert(serviceAccount)
// });
// function sendOnFireBase(){
//     // Get the registration token of the recipient device
//     const registrationToken = 'c6fm0NxWRYSVB2cUz_qooW:APA91bH3kKf0eBaLb2w1fdI0X5gkANv5EG7zjgBV4mFgxSCpVl6uIJB6dnuFmfrzNZFohpwHaKg-75tUL4kpgewj1mQKcMQi24nFIaL9E48jlSB4lKWkjK-YgnNXSsqws8572rsAd2Ib';
//
// // Create a notification message
//     const message = {
//         notification: {
//             title: 'Title',
//             body: 'Message body'
//         },
//         token: registrationToken
//     };
//
// // Send the message
//     admin.messaging().send(message)
//         .then((response) => {
//             console.log('Successfully sent message:', response);
//         })
//         .catch((error) => {
//             console.log('Error sending message:', error);
//         });
// }





module.exports = {socketOnConnection};