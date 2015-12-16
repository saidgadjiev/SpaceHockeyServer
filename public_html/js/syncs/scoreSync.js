define([
	'backbone',
	'utils/ajax'
], function (Backbone,
             ajax) {
	return function (method, collection, options) {
		options || (options = {});
		switch (method) {
			case 'read':
				console.log('read');
				$.when(ajax.sendAjax({}, options.url || this.url, "GET")).then(
					function (response) {
						resp = JSON.parse(response);
						console.log(resp.body);
						collection.set(resp.body.scoreList);
					},
					function (error) {
						console.log(error.statusText);
					}
				);
				break;
			default:
				console.error('Unknown method:', method);
				break;
		}
	};
});