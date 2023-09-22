import friend from "../img/invite.png";
import UserBar from "./UserBar";
import React from "react";
//import img from "../photos/robo4.jpg";
import signout from "../img/sign-out.png";

function ContactsSide({user,contactsList,handleItemClick,contact,handleLogOut,temp}){
    return(
        <div className="container">
            <div className="row" id="chat-bar">
                <div id="user" className="col col-2">
                    <div className="container users">
                        {user.profilePic ? <img src={user.profilePic} alt="your-image-description" className="rounded-circle img-responsive img-rounded" width={60} height={60}/> : ""}
                    </div>
                </div>
                <div id="nickName" className="col col-6">
                    &nbsp; &nbsp; {user.displayName}
                </div>
                <div id="log-out-btn" className="col col-2">
                    <button onClick={handleLogOut} id="log-out-btn" className="btn add_friend">
                        <img id="sign-out" className="icon" src={signout} alt="icon"/>
                    </button>
                </div>
                <div id="add" className="col col-2">
                    <button type="button" id="add_friend_btn" className="btn add_friend" data-bs-toggle="modal" data-bs-target="#exampleModal">
                        <img  className="icon" src={friend} alt="icon"/>
                    </button>
                </div>
            </div>
            <br/>
            <ul className="list-group" id="contactsList">
                {contactsList.map((c, index) => (
                    <ul key={index}>
                        <UserBar userInfo={c} onItemClick={handleItemClick} contact={contact} temp={temp}/>
                    </ul>
                ))}
            </ul>
        </div>
    );

}
export default ContactsSide;