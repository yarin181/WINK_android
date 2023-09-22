import "./chatStyle.css"
import React, {useEffect, useState} from 'react';
import AddContact from "./AddContact";
import {Navigate} from "react-router-dom";
import ContactsSide from "./ContactsSide";
import ChatSide from "./ChatSide";
import {Alert} from "react-bootstrap";
import io from "socket.io-client";
const socket = io("http://localhost:5000");

function Chat(props){
    const [temp, setTemp]=useState(0)

    const [logOut, setLogOut] = useState(false);
    const [showAlert, setShowAlert] = useState(false);
    const [errorMessage,setErrorMessage] = useState('')
    const [connectUser,setConnectUser]=useState({})
    const [contactsList,setContactsList] = useState([]);
    const [newMessageSent,setNewMessageSent]=useState()
    const [numOfSocketMessages,setNumOfSocketMessages]=useState({})
    function handleLogOut() {
        props.setIsConnected(false)
        setLogOut(true);
    }
    //getting the user information
    async function getUser() {
        const token= props.token;
        const url = `http://localhost:5000/api/Users/${props.currentUser.username}`;
        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}` // Add the token as an 'Authorization' header
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            setConnectUser(data)
            await initializeSocket(data.username)
        } catch (error) {
            console.error('Error:', error.message);
            handleLogOut()
        }
    }

    function sortContacts(data){

        data = data.sort((a, b) => {
            if(a.lastMessage !== null && b.lastMessage !== null){
                const dateA = new Date(a.lastMessage.created);
                const dateB = new Date(b.lastMessage.created);
                if (dateA.getTime() > dateB.getTime()) {
                    return -1;
                }
            }else if(a.lastMessage !== null && b.lastMessage === null){
                return -1;
            }else if(a.lastMessage === null && b.lastMessage !== null) {
                return 1;
            }
            return 1;
        });
        setContactsList(data)
    }

    //getting the chats of the user
    async function getUsersWithToken() {
        const token= props.token;
        const url = 'http://localhost:5000/api/Chats';

        try {
            const response = await fetch(url, {
                method: 'GET',
                headers: {
                    'Authorization': `Bearer ${token}`, // Add the token as an 'Authorization' header
                    // 'Content-Type': 'application/json'
                }
            });

            if (!response.ok) {
                throw new Error(`HTTP error! Status: ${response.status}`);
            }

            const data = await response.json();
            setContactsList(data)
            sortContacts(data)
        } catch (error) {
            console.error('Error:', error.message);
            handleLogOut()
        }
    }
    async function postContact(newUser){
        const token= props.token;
        try {
            const response = await fetch('http://localhost:5000/api/Chats', {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newUser)
            });
            if (response.ok) {
                const data = await response.json();

                setContact(data)
                return 1
            } else if(response.status === 400){
                handleError("invalid username");
            }else{
                console.error('Request failed');
                handleLogOut()
                return 0
            }
        } catch (error) {
            console.error(error);
            return 0
        }
    }
    async function postMessage(newMsg,id){
        const token= props.token;
        try {
            const response = await fetch(`http://localhost:5000/api/Chats/${id}/Messages`, {
                method: 'POST',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newMsg)
            });
            if (response.ok) {
                return 1
            } else {
                console.error('Request failed');
                return 0
            }
        } catch (error) {
            console.error(error);
            handleLogOut()
            return 0
        }
    }
    const [currentContact, setContact] = useState('');

    const handleItemClick = (ContactInfo) => {
        setContact(ContactInfo);
    };

      const addContact = async (newContact) => {
          setShowAlert(false);
          const validContact = await postContact(newContact);
          if (validContact) {
              await getUsersWithToken();
          }

      };
    const handleError = (errorMsg) =>{
        setErrorMessage(errorMsg);
        setShowAlert(true);
        setTimeout(() =>setShowAlert(false), 3000);
    }
    // socket.on('message', async (data) => {
    //     await setNumOfSocketMessages(data.num)
    // });

    function sendOnSocket(username){
        socket.emit('messageSent',connectUser.username,username)
    }
    const addMessage = async (newMsg, id) => {
        const msgJson= {msg:newMsg}
        await postMessage( msgJson, id)
        sendOnSocket(currentContact.user.username)
        await getUsersWithToken();
        setTemp(temp+1)
    };

    useEffect(() => {
        const messageRecived =async () => {
            await getUsersWithToken();
            if(currentContact !== ''){
                if(currentContact.user !== ''){
                    if(currentContact.user.username === numOfSocketMessages.from){
                        await setTemp(temp + 1)
                    }
                }
            }

        }
        messageRecived().then(r => {});

        return () => {
        };

    }, [numOfSocketMessages]);
    async function initializeSocket(username){
        await socket.emit('join',username)
    }

    useEffect(() => {
        const fetchData = async () => {
            // Initialization code
            await getUser();
            await getUsersWithToken();
            socket.on('message', async (data) => {
                await setNumOfSocketMessages({num: data.num,from : data.sender})
            });
        };

        // Call the async function immediately
        fetchData().then(r => {});

        return () => {
            // Cleanup code (if needed)
        };
    }, []);

    if (logOut) {
        return <Navigate to="/" />;
    }
    return (
        <>   {showAlert && (
            <Alert variant="danger" id="alert-fade-out" onClose={() => setShowAlert(false)} dismissible>
                {errorMessage}
            </Alert>
        )}
            <div className="card card-chat" id="chat">
                <div className="card-body">
                    <div className="row card-content">
                        <div className="col" id="contacts_area">
                            <ContactsSide user={connectUser} contactsList={contactsList} handleItemClick={handleItemClick} contact={currentContact} handleLogOut={handleLogOut} temp={temp}/>
                        </div>
                        <div className="col"><div id="chat-side-space">
                            {currentContact  ? < ChatSide token = {props.token} currentContact={currentContact} addMessage={addMessage} temp={temp}/>: ""
                            }</div>
                        </div>
                    </div>
                </div>
            </div>
            {/* Modal */}
            <AddContact onAdd={addContact} contacts={contactsList} handleError={handleError} />
        </>
    );
}

export default Chat;