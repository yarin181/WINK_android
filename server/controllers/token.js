const service = require('../services/token.js')
const {validUserPassword} = require('../services/users.js')
const fireBaseDictionary = require('../models/dictionary');
const isLoggedIn = async (req, res, next) => {
    // const temp = req.headers.FireBaseToken
    // const temp2 = req.headers.fireBaseToken
    // console.log(temp)
    // console.log(temp2)
    if (req.headers.authorization) {
        const tokenJson = req.headers.authorization.split(" ")[1]
        const return_val = await service.isLoggedInCheck(tokenJson);
        if (return_val) {
            req.headers.connectedUser = return_val.username
            return next();
        } else {
            res.sendStatus(401);
        }
    } else {
        res.sendStatus(401);
    }
};
const processLogIn = async (req, res) => {
    if (!(req.body.username&& req.body.password)){
        return res.sendStatus(400);
    }
    const returnVal = await validUserPassword(req.body.username, req.body.password)
    if (returnVal) {


        // let nameKey=req.body.username
        // console.log("authorization: ",nameKey)
        // if(nameKey !== null){
        //     fireBaseDictionary[nameKey]=req.headers.authorization
        // }
        const token = await service.getUserToken(req.body.username);

        // Return the token to the browser
        return res.status(200).send(token);
    } else {
        // Incorrect username/password. The user should try again.
        return res.sendStatus(404);
    }

}
module.exports = {isLoggedIn ,processLogIn}
