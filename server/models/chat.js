const mongoose = require('mongoose');

const { Schema } = mongoose;

const messageSchema = new Schema({
    id: {
        type: Number,
        required: true
    },
    created: {
        type: String,
        default: Date
    },
    sender: {
        type: Schema.Types.ObjectId,
        ref: 'usersData',
        required: true
    },
    content: {
        type: String,
        required: true
    }
});
const chatsSchema = new Schema({
    id: {
        type: Number,
        required: true
    },
    //an array of users
    users: [{
        type: Schema.Types.ObjectId,
        ref: 'usersData',
        required: true
    }],
    //an array of messages
    messages: [{
        type: Schema.Types.ObjectId,
        ref: 'Messages',
        required: true
    }]
});

const Messages = mongoose.model('Message', messageSchema);
const Chats = mongoose.model('chats', chatsSchema);



module.exports = { Chats, Messages};
