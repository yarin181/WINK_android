const service = require('../services/chat.js')

const getUserContactsList = async (req,res) =>{
   res.json(await service.getChats(req.headers.connectedUser));
};
const addContact = async (req,res) =>{
   const token = req.headers.authorization.split(" ")[1]
   if (!(req.body.username)){
      return res.sendStatus(500);
   }
   const returnVal  = await service.addChat(req.headers.connectedUser,req.body.username);
   if(!returnVal){
      //user not found
      return res.sendStatus(400);
   }
   if(returnVal === 400){
      return res.sendStatus(400);
   }
   return res.status(200).send(returnVal);
};
const getChatWithID = async (req,res) =>{
   const returnVal = await service.getChatByID(req.params.id,req.headers.connectedUser);
   //call to the addUser method in services using POST
   if(!returnVal){
      return res.sendStatus(401);
   }
   return res.json(returnVal);
};
const deleteChatByID = async (req,res) =>{
   //call to the addUser method in services using POST
   const returnVal = await service.deleteChat(req.params.id);
   if(returnVal.deletedCount === 0){
      return res.sendStatus(404);
   }
   return res.sendStatus(204)
};
const addMessageToChatByID = async (req,res) =>{
   //call to the addUser method in services using POST
   if (!req.body.msg){
      return res.sendStatus(500);
   }
   const returnVal = await service.addMessage(req.params.id,req.body.msg,req.headers.connectedUser);
   if(!returnVal){
      return res.sendStatus(401);
   }
   return res.json(returnVal);
};
const getMessagesByID = async (req,res) =>{

   const returnValue = await service.getMessages(req.params.id,req.headers.connectedUser);

   //call to the addUser method in services using POST
   if(!returnValue){
      return res.sendStatus(401);
   }
   return res.json(returnValue);
};
module.exports = {getMessagesByID,addMessageToChatByID,deleteChatByID,getChatWithID,addContact,getUserContactsList}