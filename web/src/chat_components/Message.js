
function Message({msg,index,sender}) {
    const className =  sender ? "message sent" : "message received";
    const date = new Date(msg.created);
    const hours = date.getHours().toString().padStart(2, '0');
    const minutes = date.getMinutes().toString().padStart(2, '0');
    const time =  `${hours}:${minutes}`;
    return (
        <div className={className} id={index}>
            <p className="message-text">{msg.content}</p>
            <p className="time-stamp">{time}</p>
        </div>
    );
}

export default Message;