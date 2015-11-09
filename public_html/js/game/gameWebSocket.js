define([
    'models/userProfile'
], function(
    userModel
) {
	function initConnect() {
	    var ws = new WebSocket("ws://localhost:8080/gameplay");
        ws.onopen = function (event) {
           console.log("Open");
        }
		
        return ws;
	}

	function sendMessage(ws, message) {
        ws.send(message);
        console.log("Message");
	}

	return {
		initConnect:initConnect,
		sendMessage:sendMessage
	};
});