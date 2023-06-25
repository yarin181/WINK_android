const express = require('express');
const chatController = require('../controllers/chat');
const {isLoggedIn} = require("../controllers/token");
const chatRouter = express.Router();

chatRouter.route('/')
    .get(isLoggedIn,chatController.getUserContactsList)
    .post(isLoggedIn,chatController.addContact);

chatRouter.route('/:id/Messages')
    .post(isLoggedIn,chatController.addMessageToChatByID)
    .get(isLoggedIn,chatController.getMessagesByID);

chatRouter.route('/:id')
    .get(isLoggedIn,chatController.getChatWithID)
    .delete(isLoggedIn,chatController.deleteChatByID);


module.exports= {chatRouter};


