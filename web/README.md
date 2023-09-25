[![My Skills](https://skills.thijs.gg/icons?i=js,html,css,react,bootstrap,mongodb,nodejs,git)](https://skills.thijs.gg)
# WINK - Chat Application 🗨💻
### ***Overview:***
The Chat Application is a web-based application that allows users to log in, sign up, and engage in real-time chat conversations.<br /> 
It consists of three main pages: Log In page, Sign Up page, and Chat page.<br />

**MongoDB Server Configuration:**

Before running the server, make sure you have a MongoDB server running with the following configuration:

- Connection String: "mongodb://localhost:27017"

**How to run the Project?<br />**
* Make sure the MongoDB server is running with the specified configuration.
* Navigate to the server directory using the command line: `cd server`.
* Install the dependents of the project `npm install`
* Run the server by the command line `npm start`
* Write in the url:"http://localhost:5000"
<br /> 


**Log In Page:<br />**
The Login page is the entry point for users who have already registered for an account.<br />
It provides a form for users to input their credentials, such as username and password, and a "Log In" button to submit the form.<br /> 
If the credentials are valid and the user id exist, the user is granted access to the Chat page.<br />
Otherwise, an error message is displayed indicating that the login was unsuccessful.<br /><br />
**Sign Up Page:<br />**
The Sign-Up page allows new users to create an account by providing required information, such as username,displayname, profile picture, and password - the password should have at least 8 chars at least one capital letter and one digit,<br>
when the user create the password tooltip with those requirements is presented.<br />
The Sign-Up page include validation checks to ensure that the entered information is valid, if the username is unique, and provide error messages if any issues arise. <br />  
Once the user fills out the required information and submits the form, the information is stored securely in the application's database,<br />
the user is redirected to the Login page to log in with their newly created account.<br /><br />
**Chat Page:<br />**
The Chat page is the main interface where users can engage in real-time chat conversations.<br />
By clicking on the Add friend icon, the connected user can add a friend that already registred to the application.<br/>
if the user not exsits "in valid userneme" error will be displayed.<br/>
In the left side of the window the user's contacts will displayed.<br> 
The user can select etch of those contacts and the whole chat between them will displayed.<br/>
The users can send and receive messages in real-time view the chat history.<br />
and get real-time updates without needing to refresh the page.<br> 
Also the chat page have features such as user authentication to ensure that only logged-in users can access the chats information, by implemntation JWT.<br/><br>

**Technologies Used:<br />**
* Front-end: HTML, CSS, Bootstrap, React
* Back-end: JavaScript
* Database: MongoDB (using Mongoose)
* Real-time Communication: WebSocket (using Socket.IO)

**Development Guidelines:**<br>
* The application follows a modular component-based architecture using React.<br>
* CSS stylesheets are organized and scoped to their respective components.<br>
* Code is thoroughly tested to ensure functionality and reliability.
* Git branching and version control practices are followed for collaborative development.




