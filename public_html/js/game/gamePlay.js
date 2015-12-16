define([
	'backbone',
	'lib/input',
	'game/gameWebSocket'
], function (Backbone,
			input,
			gameWebSocket) {
	var Direction = {
		LEFT: 0,
		RIGHT: 1,
		STOP: 2
	};
	var context;
	var CANVAS_WIDTH;
	var CANVAS_HEIGHT;
	var ws = undefined;
	var gameStarted = false;

	function PlayField(x, y, width, height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = "green";

		this.draw = function () {
			context.strokeStyle = this.color;
			context.strokeRect(this.x, this.y, this.width, this.height);
		}
		this.clear = function () {
			context.clearRect(this.x, this.y, this.width, this.height);
		}
	}

	function Platform(x, y, width, height, color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.draw = function () {
			context.fillStyle = this.color;
			context.fillRect(this.x, this.y, this.width, this.height);
		}
	}

	function Ball(centerX, centerY, radius, sAngle, eAngle) {
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.sAngle = sAngle;
		this.eAngle = eAngle;
		this.color = "red";
		this.image = new Image();
		this.image.src = 'img/ball.png';

		this.draw = function () {
			context.drawImage(this.image,this.centerX-this.radius,this.centerY-this.radius, 20, 20);
		}
		/*this.draw = function () {
			context.beginPath();
			context.arc(this.centerX, this.centerY, this.radius, this.sAngle, this.eAngle, false);
			context.fillStyle = this.color;
			context.fill();
		}*/
	}

	var gameField = new PlayField(40, 40, 500, 630);
	var myPlatform = new Platform(235, 80, 100, 20, "red");
	var enemyPlatform = new Platform(235, 610, 100, 20, "red");
	var ball = new Ball(100, 100, 10, 0, Math.PI * 2, false);
	var left = false, right = false, send = false;

	function start(canvas) {
		gameStarted = true;
		ws = gameWebSocket.initConnect();		
		console.log("INIT CONNECT");
		analizeMessage();
		var FPS = 60;
		CANVAS_WIDTH = canvas.width;
		CANVAS_HEIGHT = canvas.height;
		context = canvas.getContext('2d');

		setInterval(function () {
			update();
			draw();
		}, 1000 / FPS);
	}

	function draw() {
		gameField.clear();
		gameField.draw();
		myPlatform.draw();
		enemyPlatform.draw();
		ball.draw();
	}

	function update() {
		if (input.isDown('LEFT') && !right) {
			left = true;
			right = false;
			if (!send) {
				var message = {
					"status": "movePlatform",
					"direction": "LEFT"
				}
				gameWebSocket.sendMessage(ws, JSON.stringify(message));
				send = true;
			}
		}
		if (!input.isDown('LEFT') && left) {
			left = false;
			send = false;
			var message = {
				"status": "movePlatform",
				"direction": "STOP"
			}
			gameWebSocket.sendMessage(ws, JSON.stringify(message));
		}
		if (input.isDown('RIGHT') && !left) {
			right = true;
			left = false;
			if (!send) {
				var message = {
					"status": "movePlatform",
					"direction": "RIGHT"
				}
				gameWebSocket.sendMessage(ws, JSON.stringify(message));
				send = true;
			}
		}
		if (!input.isDown('RIGHT') && right) {
			right = false;
			send = false;
			var message = {
				"status": "movePlatform",
				"direction": "STOP"
			}
			gameWebSocket.sendMessage(ws, JSON.stringify(message));
		}
	}

	function analizeMessage() {
		ws.onmessage = function (event) {
			var data = JSON.parse(event.data);
			console.log(data);
			if (data.status == "worldInfo") {
				myPlatform.x = parseInt(data.first.positionX, 10);
				enemyPlatform.x = parseInt(data.second.positionX, 10);
				ball.centerX = parseInt(data.ball.positionX, 10);
				ball.centerY = parseInt(data.ball.positionY, 10);
			}
			if (data.status == "start" && data.second.name != data.first.name) {
				$(".wait").hide();
				$(".gameplay").show();

				$(".firstPlayer").html(data.first.name);
				$(".secondPlayer").html(data.second.name);

			}
			if (data.status == "finish") {
				$(".gameOver").show();
				$(".gameplay").hide()
				if (data.gameState == 0)
					$(".win").html("dead heat!");
				else if (data.gameState == 1)
					$(".win").html("first winner!");
				else if (data.gameState == 2)
					$(".win").html("second winner!");
				gameStarted = false;
			}
			if (data.status == "incrementScore") {
				$(".myScore").html(data.first.score);
				$(".enemyScore").html(data.second.score);
			}

		}
	}

	var Game = Backbone.View.extend({
		gameStarted: gameStarted,
		start: start
	});

	return new Game();
});