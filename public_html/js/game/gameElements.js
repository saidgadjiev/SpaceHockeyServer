define(
function() {
	function drawField(ctx, x, y, height, width, radius) {
		var canvas = document.getElementById('gamefield'); 
		var ctx = canvas.getContext('2d'); 
		ctx.beginPath();
		ctx.strokeStyle = "blue";
		ctx.lineWidth = 30;
	    ctx.moveTo(x, y + radius);
	    ctx.lineTo(x, y + height - radius);
	    ctx.quadraticCurveTo(x, y + height, x + radius, y + height);
	    ctx.lineTo(x + width - radius, y + height);
	    ctx.quadraticCurveTo(x + width, y + height, x + width, y + height - radius);
	    ctx.lineTo(x + width, y + radius);
	    ctx.quadraticCurveTo(x + width, y, x + width - radius, y);
	    ctx.lineTo(x + radius, y);
	    ctx.quadraticCurveTo(x, y, x, y + radius);
	    ctx.stroke();
	}

	function drawPlatform(ctx, x, y, width, height) {
		ctx.fillStyle = "red"; 
    	ctx.fillRect(x, y, width, height); 
	}

	return {
		drawField:drawField,
		drawPlatform:drawPlatform
	}
});