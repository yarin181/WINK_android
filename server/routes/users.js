const {registerUser,getUserData} =  require("../controllers/users.js");
const express = require('express')
const {isLoggedIn} = require("../controllers/token");
const usersRouter = express.Router();

usersRouter.route('/')
    .post(registerUser);

usersRouter.route('/:username')
    .get(isLoggedIn,getUserData)

module.exports= {usersRouter};
