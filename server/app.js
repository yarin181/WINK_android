const express = require('express');
const app = express();

app.use(express.json({limit:'10mb'}));
const cors = require('cors');
const {chatRouter} = require("./routes/chats");
const {tokenRouter} = require("./routes/token");
const {usersRouter} = require("./routes/users");
const sockets = require('./controllers/sockets')


const { Server } = require("socket.io");
const http = require('http');
const server = http.createServer(app);
const io = new Server(server, {
    cors: {
        origin: '*',
        methods: ['GET', 'POST']
    }
});
app.set("io", io);
sockets.setIO(io);


app.use(cors());

//connect to mongoose
const mongoose = require('mongoose');

const customEnv = require('custom-env');
customEnv.env(process.env.NODE_ENV,'./config');

mongoose.connect(process.env.CONNECTION_STRING,{
    useNewUrlParser:true,useUnifiedTopology:true
});
app.use(express.static('public'));
const db = mongoose.connection;
db.on('error', console.error.bind(console, 'MongoDB connection error:'))

app.use('/api/Chats',chatRouter);
app.use('/api/Users',usersRouter);
app.use('/api/Tokens',tokenRouter);

sockets.socketOnConnection(io)

server.listen(process.env.PORT)


// const admin = require('firebase-admin');
// const serviceAccount = require('./wink-android-32c12-firebase-adminsdk-kz9v4-cfc7b2ba24.json');
// admin.initializeApp({
//     credential: admin.credential.cert(serviceAccount)
// });
// function sendOnFireBase(registrationToken){
//     // Get the registration token of the recipient device
//     // const registrationToken = 'c6fm0NxWRYSVB2cUz_qooW:APA91bH3kKf0eBaLb2w1fdI0X5gkANv5EG7zjgBV4mFgxSCpVl6uIJB6dnuFmfrzNZFohpwHaKg-75tUL4kpgewj1mQKcMQi24nFIaL9E48jlSB4lKWkjK-YgnNXSsqws8572rsAd2Ib';
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

