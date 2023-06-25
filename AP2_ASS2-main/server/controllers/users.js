const service = require('../services/users.js')
const registerUser = async (req,res) =>{
   if (!(req.body.username && req.body.password && req.body.displayName && req.body.profilePic)) {
      // If the request body does not match the expected format.
      return res.sendStatus(400);
   }
   //call to the addUser method in services using POST
   const status = await service.addUser(req.body.username,req.body.password,req.body.displayName,req.body.profilePic);
   //
   if(status === 409){

      return res.sendStatus(409);
   }
   else{
      return res.sendStatus(200)
   }
};

const getUserData = async (req,res) =>{
   const user = await service.getUserDetails(req.params.username);
   //call to the addUser method in services using POST
   if(!user || (user.username !== req.headers.connectedUser)){
      return res.sendStatus(401);
   }
   res.status(200).send(user)
};
module.exports = {registerUser,getUserData}