define([
	'backbone',
	'utils/ajax'
], function (Backbone,
             ajax) {
	return function (method, model, options) {
		options || (options = {});
		switch (method) {
			case 'create':
				$.ajax({
					type: 'POST',
					url: options.url || this.url,
					data: options.data || this.toJSON(),
					success: function (response) {
						try {
							var responseObj = JSON.parse(response);
						} catch (err) {
							responseObj = response;
						}
						if (responseObj.login_status == false) {
							options.error(responseObj.error_massage);
						}
						else {
							options.success(response);
						}
					},
					error: function (xhr, status, error) {
						options.error(error);
					}
				});
				break;
			case 'update':
				this.sync.call(this, 'create', model, options);
				break;
			case 'delete':
				console.log('destroy');
				options.type = 'GET';
				Backbone.sync.call(this, method, model, options);
				break;
			case 'read':
				console.log('read');
				$.when(ajax.sendAjax({}, options.url || this.url, "POST")).then(
					function (response) {
						resp = JSON.parse(response);
						console.log(resp.body);
						console.log(resp.status);
						if (resp.status == '200')
							model.set(resp.body);
						else
							console.log("UNAUTHORIZED");
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