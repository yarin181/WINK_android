const mongoose = require('mongoose');
const { Schema, model } = mongoose;


const usersDataSchema = new Schema({
    username: {
        type: String,
        required: true,
        unique: true

    },
    password: {
        type: String,
        required: true
    },
    displayName: {
        type: String,
        required: true
    },
    profilePic: {
        type: String,
        required: true
    }
});


const usersData = model('usersData', usersDataSchema);


module.exports = {usersData};
