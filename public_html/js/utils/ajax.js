define([
	'backbone'
], function (Backbone) {

	function sendAjax(dataAjax, urlDest, method) {
		return $.ajax({
			type: method,
			async: false,
			url: urlDest,
			data: JSON.stringify(dataAjax)
		});
	}

	return {
		sendAjax: sendAjax
	};
});