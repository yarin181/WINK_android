[![My Skills](https://skills.thijs.gg/icons?i=java,firebase,androidstudio,mongodb,nodejs,git)](https://skills.thijs.gg)
# WINK - Chat Application 🗨📱
### ***Overview:***
The WINK Chat Application is a mobile app developed for Android devices.<br /> 
It allows users to log in, sign up, and engage in real-time chat conversations. The app consists of five main screens:<br /> 
the Log In screen, the Sign Up screen, the Chat screen, the Contact screen, and the Settings screen.


**How to run the Project?<br />**
* Make sure the MongoDB server is running with the specified configuration.
* Navigate to the server directory using the command line: `cd server`.
* Install the dependents of the project `npm install`
* Run the server by the command line `npm start`
<br /> 


**Log In Page:<br />**
The Login page is the entry point for users who have already registered for an account.<br />
It provides a form for users to input their credentials, such as username and password, and a "Log In" button to submit the form.<br /> 
If the credentials are valid and the user id exists, the user is granted access to the Contact page.<br />
Otherwise, an error alert is displayed indicating that the login was unsuccessful.<br /><br />
**Sign Up Page:<br />**
The Sign-Up page allows new users to create an account by providing the required information, such as <br />
username,displayname, profile picture, and password - the password should have at least 8 chars at least one capital letter and one digit,<be>
when the user creates the password popup with those requirements is presented.<br />
The user can choose a profile photo from the gallery or take a pic.<br />
The Sign-Up page includes validation checks to ensure that the entered information is valid, if the username is unique, and provide error messages if any issues arise. <br />  
Once the user fills out the required information and submits the form, the information is stored securely in the application's database,<br />
the user is redirected to the Login page to log in with their newly created account.<br /><br />
**Chat Page:<br />**
The Contacts screen displays all the contacts of the connected user. Users can view the list of their contacts <br />
and select a contact to chat with.<br /> 
The user can add a new chat by clicking on the Add friend icon, we can add new contact only if he already registered<br />
and his account exists in the database.<br />
If the entered username does not exist in the database, an alert will be shown to inform the user that the username is invalid.<br />
This ensures that users can only initiate chats with registered users of the application.<br />
By clicking on chosen contact the chat page will be displayed.<br />
**Chat Page:<br />**
On the Chat page, users can engage in real-time chat conversations.<br />
The users can send and receive messages in real-time and view the chat history.<br />
and get real-time updates without needing to refresh the page.<br> 
**Settings Page:<br />**
The Settings screen allows users to customize the app according to their preferences.<br />
Users can change the app mode between day and night themes to suit their visual preferences.<br />
Additionally, users can change the IP address configuration for the app to connect to the server.<br />
This feature allows users to specify a custom IP address if needed.<br />
The Settings screen is accessible from any activity within the app, providing easy access for users to modify these settings.<br />
**Technologies Used:<br />**
* Front-end: XML
* Back-end: Java
* Database: MongoDB (using Mongoose)
* Real-time Communication: FireBase





