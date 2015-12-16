define(
function() {

	function initConnect() {
	   /* ws = new WebSocket("ws://clicker.net:80/gameplay");
        ws.onopen = function (event) {
        }
        ws.onmessage = function (event) {
            console.log("Message");
            var data = JSON.parse(event.data);
            if(data.status == "start"){
                document.getElementById("wait").style.display = "none";
                document.getElementById("gameplay").style.display = "block";
                document.getElementById("enemyName").innerHTML = data.enemyName;
            }
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
            if(data.status == "increment" && data.name == "${myName}"){
                document.getElementById("myScore").innerHTML = data.score;
            }
            if(data.status == "increment" && data.name == document.getElementById("enemyName").innerHTML){
                document.getElementById("enemyScore").innerHTML = data.score;
            }
        }
		
        return ws;*/
        console.log("init");
	}

	function sendMessage(ws) {
		/*var message = "{}";
        ws.send(message);*/
        console.log("Message");
	}

	return {
		initConnect:initConnect,
		sendMessage:sendMessage
	};
});