define([
	'models/userProfile'
], function (userModel) {
	function initConnect() {
		var ws = new WebSocket("ws://localhost:8080/gameplay");
		ws.onopen = function () {
			console.log("Open");
		}
//		var myName = null;
//		var enemyName = "";

		return ws;
	}

	function sendMessage(ws, message) {
		ws.send(message);
	}

	return {
		initConnect: initConnect,
		sendMessage: sendMessage
	};
});