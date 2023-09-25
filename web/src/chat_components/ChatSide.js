import MessageWindow from "./MessageWindow";
import React from "react";

function ChatSide({currentContact,addMessage,token,temp}){

    return(
        <>
            <div className="row" id="chat-bar">
                <div className="col col-2">
                    <div className="container">
                        <img src={currentContact.user.profilePic} id="chatSide-photo" className="rounded-circle" alt="Placeholder Image Avatar" width={60} height={60}/>
                    </div>
                </div>
                <div id="connectName" className="col col-8">
                    {currentContact.user.displayName}
                </div>

        </div>
            <br />
            <MessageWindow token={token} id="msgWindow" contact={currentContact} addMessage={addMessage} temp={temp} />
            </>

    );
}
export default ChatSide;