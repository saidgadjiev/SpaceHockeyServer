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
		UP: 2,
		DOWN: 3,
		STOP: 4
	};
	var context;
	var CANVAS_WIDTH;
	var CANVAS_HEIGHT;
	var ws = undefined;
	function GameField(x, y, height, width, angleRadius, color) {
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.angleRadius = angleRadius;
		this.color = color;
		this.draw = function() {
			context.beginPath();
			context.strokeStyle = this.color;
			context.lineWidth = 30;
		    context.moveTo(this.x, this.y + this.angleRadius);
		    context.lineTo(this.x, this.y + this.height - this.angleRadius);
		    context.quadraticCurveTo(this.x, this.y + this.height, this.x + this.angleRadius, this.y + this.height);
		    context.lineTo(this.x + this.width - this.angleRadius, this.y + this.height);
		    context.quadraticCurveTo(this.x + this.width, this.y + this.height, this.x + this.width, this.y + this.height - this.angleRadius);
		    context.lineTo(this.x + width, this.y + this.angleRadius);
		    context.quadraticCurveTo(this.x + this.width, this.y, this.x + this.width - this.angleRadius, this.y);
		    context.lineTo(this.x + this.angleRadius, this.y);
		    context.quadraticCurveTo(this.x, this.y, this.x, this.y + this.angleRadius);
		    context.stroke();
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
				case Direction.UP: 
					this.y -= this.velocity;
					break;
				case Direction.DOWN:
					this.y += this.velocity;
					break;
				case Direction.STOP:
					break;
				default: 
					console.log("Bad direction");
					break;
			}
		} 
	}
	var field = new GameField(40, 40, 630, 500, 50, "blue");
	var myPlatform = new Platform(235, 80, 100, 20, 4, Direction.STOP, "red");
	var enemyPlatform = new Platform(235, 610, 100, 20, 4, Direction.STOP, "red");
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
	function draw() {
		context.clearRect(0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		field.draw();
		myPlatform.draw();
		enemyPlatform.draw();
	}
	function update() {
		myPlatform.move();
		enemyPlatform.move();
		if (input.isDown('LEFT')) {
			//myPlatform.direction = Direction.LEFT;
        	//myPlatform.move();
        	var message = {
        		"direction": "LEFT"
        	}
        	gameWebSocket.sendMessage(ws, JSON.stringify(message));
    	} 
    	if (input.isDown('RIGHT')) {
    		//myPlatform.direction = Direction.RIGHT;
    		//myPlatform.move();
    		var message = {
                "direction": "RIGHT"
           	}
            gameWebSocket.sendMessage(ws, JSON.stringify(message));
    	}
    	myPlatform.clamp(field.x + 40, field.width - myPlatform.width);
    	enemyPlatform.clamp(field.x + 40, field.width - myPlatform.width);
	}
	function analizeMessage() {
		ws.onmessage = function (event) {
            var data = JSON.parse(event.data);
            console.log(data);
            /*if(data.status == "move"){
                switch (data.direction) {
                	case "LEFT":
                		if (data.PlatformID == "1") {
                			myPlatform.direction = Direction.LEFT;
                		} else {
                			enemyPlatform.direction = Direction.LEFT;
                		}
                		break;
                	case "RIGHT":
                		if (data.PlatformID == "2") {
                			myPlatform.direction = Direction.RIGHT;
                		} else {
                			enemyPlatform.direction = Direction.RIGHT;
                		}
                		break;
                }
            }*/
            if(data.status == "move" && data.name == userModel.get("login")){
                 switch (data.direction) {
                        case "LEFT":
                                 			myPlatform.direction = Direction.LEFT;

                                 		break;
                                 	case "RIGHT":
                                 			myPlatform.direction = Direction.RIGHT;

                                 		break;
                                 }
            }
            if(data.status == "move" && data.name == document.getElementById("enemyName").innerHTML){
               switch (data.direction) {
                                       case "LEFT":
                                                			enemyPlatform.direction = Direction.LEFT;

                                                		break;
                                                	case "RIGHT":
                                                			enemyPlatform.direction = Direction.RIGHT;

                                                		break;
                                                }
                           }
            if(data.status == "start"){
            	document.getElementById("wait").style.display = "none";
                document.getElementById("gameplay").style.display = "block";
                document.getElementById("enemyName").innerHTML = data.enemyName;
            }
            /*
            if(data.status == "finish"){
               document.getElementById("gameOver").style.display = "block";
               document.getElementById("gameplay").style.display = "none";
               if(data.gameState == 0)
                    document.getElementById("win").innerHTML = "dead heat!";
               else if (data.gameState == 1)
                    document.getElementById("win").innerHTML = "winner!";
               else if (data.gameState == 2)
                    document.getElementById("win").innerHTML = "loser!";
            }
            */
        }
	}

	return {
		start:start
	}
});