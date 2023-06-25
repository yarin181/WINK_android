const jwt = require("jsonwebtoken")

const key = "Some super secret key shhhhhhhhhhhhhhhhh!!!!!"

const isLoggedInCheck = async (token) => {
    try {
        return await jwt.verify(token, key);
    } catch (err) {
        return null;
    }
}

const getUsername = async (token) =>{
    try {
        const data = jwt.verify(token, key);
        const userName = data.username;
        return userName;
    } catch (err) {
        return null;
    }

}
const getUserToken = async (username) =>{
    // Here, we will only put the *validated* username
    const data = { username: username}
    // Generate the token.
    return await jwt.sign(data, key)
}

// Handle login form submission
module.exports = {isLoggedInCheck,getUsername,getUserToken}
