define([
	'models/userProfile'
], function (User) {


	var Game = function () {

		var started = false;
		var finished = false;

		//var me = JSON.parse(localStorage.getItem("user"));
		var myName = null;
		var enemyName = "";

		var ws;

		this.init = function (user) {
			myName = User.login;
			ws = new WebSocket("ws://localhost:8080/gameplay");

			ws.onopen = function (event) {
				console.log("connection opened");
			}

			ws.onmessage = function (event) {
				console.log("onmessage");
				var data = JSON.parse(event.data);
				if (data.status == "start" && data.enemyName != myName) {
					$("#wait").hide();
					$("#gameplay").show();
					$("#enemyName").html(data.enemyName);
				}

				if (data.status == "finish") {
					$("#gameOver").show();
					$("#gameplay").hide();

					if (data.win)
						$("#win").html("winner!");
					else
						$("#win").html("loser!");
				}

				if (data.status == "increment" && data.name == myName) {

					$("#myScore").html(data.score);
				}

				if (data.status == "increment" && data.name == $("#enemyName").html()) {
					$("#enemyScore").html(data.score);
				}
			}

		};

		this.sendMessage = function () {
			var message = "{}";
			ws.send(message);
		};
	}

	return new Game();
});
            
            
