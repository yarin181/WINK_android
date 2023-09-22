import Message from "./Message"

import React, {useEffect, useRef, useState} from "react";
//import ReactDOM from 'react-dom';

function MessageWindow (props){
    const [messageContent, setMessageContent] = useState('');
    const [messages,setMessages]=useState({})
    const bottomRef = useRef(null);

    const handleSendMessage = (e) => {
        e.preventDefault()
        if (messageContent.length === 0){
            return;
        }
        props.addMessage(messageContent,props.contact.id);
        setMessageContent('');

    };
    async function  getMessages() {
        const token= props.token;
        const url = `http://localhost:5000/api/Chats/${props.contact.id}/Messages`;
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
            setMessages(data.reverse())
            setTimeout(() => {
                bottomRef.current.scrollIntoView({
                behavior: 'smooth'
            });
            });
        } catch (error) {
        }
    }

    useEffect(() => {
        const fetchData = async () => {
            // Initialization code
            await getMessages()

        };
        fetchData().then(r => {});

        return () => {
        };
    }, [props.contact,props.temp]);

    return (
        <>
        <div className="container" >
            <div className="row" id="message_placeholder">
                <div className="col col-md-12" id="message_container">
                    <div id="message-window">
                        {props.contact !== null ? messages.length > 0 ? messages.map((message, index) => (
                            <Message key={index}  msg={message} sender={message.sender.username === props.contact.user.username }/>
                        )): "" : ""}
                        <div id="bottom-ref-id" ref={bottomRef} />
                    </div>
                </div>
            </div>
        </div>
            <form  onSubmit={handleSendMessage} >
                <div className="input-group mb-3" id="chat-insert-box">
                    <input type="text" className="form-control" id="inputField" placeholder="Enter your message here..." value={messageContent} onChange={
                        (event) => setMessageContent(event.target.value)}/>
                    <button className="btn btn-outline-secondary" type="submit" id="button-addon2"  onClick={handleSendMessage} >Send</button>
                </div>
            </form>
        </>
    );
}
export default MessageWindow;
