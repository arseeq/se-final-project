class SocketMethods {
    static sendMessage(ws, msg){
        // Wait until the state of the socket is not ready and send the message when it is...
        this.waitForSocketConnection(ws, function(){
            console.log("message sent!!!");
            ws.send(msg);
        });
    }

    static waitForSocketConnection(socket, callback){
        let self = this;
        setTimeout(
            function () {
                if (socket.readyState === 1) {
                    console.log("Connection is made");
                    if(callback != null){
                        callback();
                    }
                    return;

                } else {
                    console.log("wait for connection...");
                    self.waitForSocketConnection(socket, callback);
                }
            }, 5); // wait 5 milisecond for the connection...
    }

}

export default SocketMethods;