const express = require('express');
const tokenController = require('../controllers/token');
const tokenRouter = express.Router();

tokenRouter.route('/')
    .post(tokenController.processLogIn);

module.exports= {tokenRouter};

