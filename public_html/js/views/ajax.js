define([
	'backbone'
], function(Backbone) {
    
    function sendAjax(dataAjax, urlDest, method) {
    	return $.ajax({
			type: method,
			url: urlDest,
			data: JSON.stringify(dataAjax)
    	});
    }

    return {
        sendAjax:sendAjax
    };
});