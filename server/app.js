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

