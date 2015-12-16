define([
		'backbone',
		'utils/ajax'
	],
	function (Backbone,
	          ajax) {
		// модуль, отвечающий за разлогинивание

		var SignoutManager = function () {

			this.exitRequest = function (model) {

				$.when(ajax.sendAjax('', "/auth/signout", "POST")).then(
					function (response) {
						console.log("geted..response");
						response = JSON.parse(response);
						if (response.status == "200") {
							console.log("ajax success");
							model.clear();
							Backbone.history.navigate('', {trigger: true});
						}

					},
					function (error) {
						console.log(error.statusText);
					}
				);
			};

		};

		return new SignoutManager();

	});
