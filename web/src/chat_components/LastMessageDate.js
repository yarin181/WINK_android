import React from 'react';

function LastMessageDetails({ userInfo,temp }) {
    // Extract the day and time from the last message
    if (!userInfo.lastMessage){
        return ;
    }
    const messageDate = new Date(userInfo.lastMessage.created);
    const day = messageDate.toLocaleDateString();
    const options = { hour: 'numeric', minute: 'numeric', hour12: false };
    const time = messageDate.toLocaleTimeString([], options);

    return (
        <div className="grid-item item3">
            <p>{day}</p>
            <p className="small-message">{time}</p>
        </div>
    );
}

export default LastMessageDetails;