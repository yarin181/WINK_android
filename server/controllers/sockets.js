const connectedUsers = new Map();

function assignNewSocket(username,io){
    io.on('connection', (socket) => {
        if (!connectedUsers.has(username)){
            connectedUsers.set(username, socket.id);
        }
    });
}
function sendWithSocket(recipient,io){
    for (let [key, value] of connectedUsers) {
    }
    const recipientSocketId = connectedUsers.get(recipient);
    if (recipientSocketId) {

        io.on('connection', socket => {
            socket.on('messageSent',() => {
                socket.to(10).emit('message')
            });
        });
        io.to(recipientSocketId).emit('message',{ foo : 'bar' });
    }
}

function socketOnConnection (io){

    let numOfMessages=1
    io.on('connection', (socket) => {
        socket.on('join',(data) => {
            if(!connectedUsers.has(socket.id)){
                connectedUsers.set( data , socket.id)
                socket.join(10)
            }
        });
        socket.on('messageSent',(from,to) => {
            if(connectedUsers.get(to) !== undefined){
                io.to(connectedUsers.get(to)).emit('message',{ num : numOfMessages, sender: from })
            }
            numOfMessages++


        });

        // Remove the user from connectedUsers map when they disconnect
        socket.on('disconnect', () => {

            for (let [key, value] of connectedUsers.entries()) {
                if (value === socket.id) {
                   connectedUsers.delete(key);
                }
            }
        });

    });
}

module.exports = {socketOnConnection};