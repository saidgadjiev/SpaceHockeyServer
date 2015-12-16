define([
	'backbone',
	'lib/input',
	'game/gameWebSocket',
	'models/userProfile'
], function(
	Backbone,
	input,
	gameWebSocket,
	userModel
) {
	var Direction = {
		LEFT: 0,
		RIGHT: 1,
		STOP: 2
	};
	var context;
	var CANVAS_WIDTH;
	var CANVAS_HEIGHT;
	var ws = undefined;
	function PlayField(x, y, width, height) {
          this.x = x;
          this.y = y;
          this.width = width;
          this.height = height;
          this.color = "green";

          this.draw = function() {
            context.strokeStyle=this.color;
            context.strokeRect(this.x, this.y, this.width, this.height);
          }

          this.clear = function() {
            context.clearRect(this.x, this.y, this.width, this.height);
          }
        }
	function Platform(x, y, width, height, velocity, direction, color) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.color = color;
		this.velocity = velocity;
		this.direction = direction;
		this.clamp = function(min, max) {
			this.x = Math.min(Math.max(this.x, min), max);
			console.log(this.x);
		}
		this.draw = function() {
			context.fillStyle = this.color; 
    		context.fillRect(this.x, this.y, this.width, this.height);
		}
		this.move = function() {
			switch(this.direction) {
				case Direction.LEFT: 
					this.x -= this.velocity;
					break;
				case Direction.RIGHT:
					this.x += this.velocity;
					break;
				case Direction.STOP:
					break;
				default: 
					console.log("Bad direction");
					break;
			}
		} 
	}
	function Ball(centerX, centerY, radius, sAngle, eAngle) {
          this.centerX = centerX;
          this.centerY = centerY;
          this.radius = radius;
          this.sAngle = sAngle;
          this.eAngl2e = eAngle;
          this.color = "red";
          this.speedX = 5;
          this.speedY = 1;

          this.draw = function() {
            context.beginPath();
            context.arc(this.centerX, this.centerY, this.radius, this.sAngle, this.eAngle, false);
            context.fillStyle = this.color;
            context.fill();
          }

          this.move = function() {
          	this.centerX += this.speedX;
          	this.centerY += this.speedY;
    	  }
    }
	var gameField = new PlayField(40, 40, 500, 630);
	var myPlatform = new Platform(235, 80, 100, 20, 4, Direction.STOP, "red");
	var enemyPlatform = new Platform(235, 610, 100, 20, 4, Direction.STOP, "red");
    var ball = new Ball(100, 100, 10, 0, Math.PI*2, false);
    var left = false, right = false, send = false;
	function start(canvas) {
		ws = gameWebSocket.initConnect();
  		analizeMessage();
		var FPS = 60;
		CANVAS_WIDTH = canvas.width;
		CANVAS_HEIGHT = canvas.height;
		context = canvas.getContext('2d');

		setInterval(function() {
  			update();
  			draw();
		}, 1000/FPS);
	}
	function collisionCheck() {
		if (myPlatform.x <= gameField.x) {
			console.log("collision");
		}
	}
	function draw() {
      	gameField.clear();
      	gameField.draw();
		myPlatform.draw();
		enemyPlatform.draw();
		collisionCheck();
		//ball.draw();
	}
	function update() {
		myPlatform.move();
		enemyPlatform.move();
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
            if (data.status == "movePlatform") {
            	myPlatform.direction = parseInt(data.first.direction, 10);
            	enemyPlatform.direction = parseInt(data.second.direction, 10);
            }
            if(data.status == "start"){
                            document.getElementById("wait").style.display = "none";
                            document.getElementById("gameplay").style.display = "block";
                            document.getElementById("myName").innerHTML = data.first.name;
                            document.getElementById("enemyName").innerHTML = data.second.name;
                        }
                        if(data.status == "finish"){
                           document.getElementById("gameOver").style.display = "block";
                           document.getElementById("gameplay").style.display = "none";
                           if(data.gameState == 0)
                                document.getElementById("win").innerHTML = "dead heat!";
                           else if (data.gameState == 1)
                                document.getElementById("win").innerHTML = "first winner!";
                           else if (data.gameState == 2)
                                document.getElementById("win").innerHTML = "second winner!";
                        }
                        if(data.status == "incrementScore"){
                            document.getElementById("myScore").innerHTML = parseInt(data.first.score, 10);
                            document.getElementById("enemyScore").innerHTML = parseInt(data.second.score, 10);
                        }
        }
	}

	return {
		start:start
	}
});