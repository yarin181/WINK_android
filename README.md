[![My Skills](https://skills.thijs.gg/icons?i=java,firebase,react,js,androidstudio,mongodb,nodejs,git)](https://skills.thijs.gg)
# WINK - Chat Application 🗨📱
### ***Overview:***
The WINK Chat Application is a mobile app developed for Android devices.<br /> 
It allows users to log in, sign up, and engage in real-time chat conversations. The app consists of five main screens:<br /> 
the Log In screen, the Sign Up screen, the Chat screen, the Contact screen, and the Settings screen.

**MongoDB Server Configuration:**

Before running the server, make sure you have a MongoDB server running with the following configuration:
- Connection String: "mongodb://localhost:27017"


## Running the Server

Before running the application, make sure the MongoDB server is running with the specified configuration. To run the server:
1.  Open the command line.
2.  Navigate to the server directory using the command line: `cd server`.
3.  Install the dependencies of the project by running the following command: `npm install`.
4.  Run the server using the command: `npm start`.
    The server will start running, and the Android application will be able to connect to it.n interact with it.

## Prerequisites

Before running the application, ensure that you have the following:

-   Android Studio installed on your computer.
-   A compatible Android device connected to your computer via USB, if you want to run the application on a physical cellphone.
-   (Optional) An Android emulator set up in Android Studio.



## Getting Started

To run the application, follow these steps:
1.  Clone or download the repository to your local machine.
2.  Open Android Studio and select "Open an existing Android Studio project" or "Import project."
3.  Navigate to the location where you cloned or downloaded the repository and select the project folder.
4.  Once the project is loaded, you have two options to run the application:
    a. Emulator: If you want to run the application on an emulator, click on the "Run" button (usually a green triangle) or select "Run" from the toolbar. Select the emulator you want to use from the list of available devices. The application will be installed and launched on the selected emulator.
    b. Physical Cellphone: If you want to run the application on a physical cellphone, connect your Android device to your computer using a USB cable. Click on the "Run" button or select "Run" from the toolbar. Select your connected Android device from the list of available devices. The application will be installed and launched on your device.
5.  Once the application is launched on the emulator or the physical cellphone, you can interact with it.



## Updating Server IP Address

If you want to update the server IP address used by the application, follow these steps:
1.  Launch the application on your emulator or physical cellphone.
2.  Navigate to the "Settings" screen within the application.
3.  Look for the "Enter server IP Address" field.
4.  Enter the desired server IP address in the provided field.
5.  Save the changes ("set ip").
6.  The application will now use the updated server IP address for any server-related functionality.

Note: It is important to ensure that the server IP address is accurate and up-to-date for the application to function correctly.


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
