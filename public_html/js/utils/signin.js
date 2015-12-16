define([
		'backbone',
		'utils/ajax'
	],
	function (Backbone,
	          ajax) {

		var formClass = ".form_signin";

		var SigninManager = function () {

			this.signinRequest = function (model) {
				console.log("request");
				var dataAjax = {
					'login': $(formClass + " input[name = login]").val(),
					'password': $(formClass + " input[name = password]").val()
				};

				$.when(ajax.sendAjax(dataAjax, "/auth/signin", "POST")).then(
					function (response) {
						response = JSON.parse(response);
						console.log(response.status)
						if (response.status == "200") {
							console.log(response.body.login);
							model.set({
								"login": response.body.login
							});
							//model.save();
							Backbone.history.navigate('game', {trigger: true});
						}
						else {
							var $error = $(".form__row_errors");
							$error.append("Login or password is incorrect!");
							$error.show();
						}
					},
					function (error) {
						console.log(error.statusText);
					}
				);
			};
			this.saveCache = function () {
				var elements = $(formClass + " input");
				for (i = 0; i < elements.length; i++) {
					(function (element) {
						var id = element.getAttribute('id');
						element.value = sessionStorage.getItem(id); // обязательно наличие у элементов id
						element.oninput = function () {
							sessionStorage.setItem(id, element.value);
						};
					})(elements[i]);
				}
			};
		}


		return new SigninManager();

	});
