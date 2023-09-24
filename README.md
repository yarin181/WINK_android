[![My Skills](https://skills.thijs.gg/icons?i=java,firebase,react,js,androidstudio,mongodb,nodejs,git)](https://skills.thijs.gg)




# WINK Chat Application

Welcome to the WINK Chat Application project! This cross-platform chat application is designed to provide an efficient and scalable messaging solution for users. Whether you're on a web browser or an Android device, WINK has got you covered. In this README, we'll provide an overview of the project, its architecture, and how to get started.

| Web Interface | Android Application |
|----------|-----------|
| ![Untitled video - Made with Clipchamp (1)](https://github.com/yarin181/WINK_android/assets/90701490/7dcb6c99-cc3e-4db3-a74e-e6dec4fb1569) | ![Untitled video - Made with Clipchamp (2)](https://github.com/yarin181/WINK_android/assets/90701490/5b66e5f7-eea7-4ab0-a84c-45bec9969150) |
## Table of Contents

1. [Introduction](#introduction)
2. [Features](#features)
3. [Architecture](#architecture)
4. [Getting Started](#getting-started)
5. [Dependencies](#dependencies)
6. [WINK - Chat Application 🗨📱 (Android App)](#wink---chat-application--android-app)
7. [WINK - Chat Application 🗨💻 (web client)](#wink---chat-application--web-client)

## Introduction

WINK Chat Application is a versatile chat platform that enables real-time communication between users. It consists of both a web application and an Android app, ensuring users can chat conveniently from their preferred devices.

### Features

- **Cross-Platform**: Users can chat seamlessly across web browsers and Android devices.
- **Efficient Server**: The server is built using Node.js with the Express framework, providing an efficient and scalable foundation for the application.
- **Database**: MongoDB is used to store and retrieve chat data securely.
- **Web Client**: The web client is developed using React, offering a responsive and user-friendly interface.
- **Real-Time Communication**: Socket.IO is implemented for real-time messaging capabilities.
- **Android App**: An Android app has been created using Android Studio to extend the application's reach.
- **Android Real-Time Communication**: Firebase is used for real-time communication in the Android app.
- **Offline Functionality**: SQLite provides local storage for offline functionality in the Android app.

## Architecture

The WINK Chat Application is built on a robust architecture that ensures efficiency and reliability:

- **Server**: The server is built on a RESTful API architecture using Node.js with the Express framework. It handles client requests, manages user accounts, and facilitates real-time messaging using Socket.IO. MongoDB is used as the database to store chat data securely.

- **Web Client**: The web client is constructed with React, providing users with a responsive and intuitive interface. Users can register, log in, and send and receive real-time messages through the server.

- **Android App**: The Android app is developed using Android Studio, extending the application's reach to Android users. Firebase is used to facilitate real-time messaging in the app, and SQLite is employed for local storage, enabling users to access messages even when offline.The app consists of five main screens:<br />
  the Log In screen, the Sign Up screen, the Chat screen, the Contact screen, and the Settings screen.

    
## Getting Started

To get started with the WINK Chat Application, follow these steps:

1. Clone this repository to your local machine:

   ```
   git clone https://github.com/yarin181/WINK_android.git
   ```
2. MongoDB Server Configuration:

    - Before running the server, make sure you have a MongoDB server running with the following configuration:

      ```
      Connection String: "mongodb://localhost:27017"
      ```

2. Set up the server:

    - Navigate to the server directory and install dependencies:

      ```
      cd server
      npm install
      ```

    - Start the server:

      ```
      npm start
      ```

3. Set up the web client:

    - Navigate to the client directory and install dependencies:

      ```
      cd client
      npm install
      ```

    - Start the web client:

      ```
      npm start
      ```

4. Set up the Android app:

    - Open the Android project in Android Studio.

    - Configure Firebase for real-time messaging.

    - Build and run the Android app on your device or emulator.

5. Updating Server IP Address ath the Android app.
    - At the app navigate to the "Settings" screen within the application.
    
    - Set desired server IP address in the provided field.

Now, you can use WINK Chat Application from both the web client and the Android app!

## Dependencies

- Node.js
- Express
- MongoDB
- React
- Socket.IO
- Android Studio
- Firebase (for Android app)
- SQLite (for Android app)

For specific versions and more detailed dependencies, please refer to the package.json files in the server and client directories.


# WINK - Chat Application 🗨📱 (Android App)

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
* Back-end: Java, js, react
* Database: MongoDB (using Mongoose), Sqllite
* Real-time Communication: FireBase


# WINK - Chat Application 🗨💻 (web client)

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
