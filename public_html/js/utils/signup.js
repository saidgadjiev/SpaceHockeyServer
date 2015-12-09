define([
		'backbone',
		'utils/ajax'
	],
	function (Backbone,
	          ajax) {
		var formClass = ".form_signup";

		var SignupManager = function () {

			this.signupRequest = function () {
				var dataAjax = {
					'login': $(formClass + " input[name = login]").val(),
					'password': $(formClass + " input[name = password]").val(),
					'email': $(formClass + " input[name = email]").val()
				};

				$.when(ajax.sendAjax(dataAjax, "/auth/signup", "POST")).then(
					function (response) {
						data = JSON.parse(response);
						if (parseInt(data["status"]) == "200") {
							console.log("ajax success");
							Backbone.history.navigate('', {trigger: true});
						}
						else {
							var $error = $(".form__row_errors");
							$error.append("User cann't be registrated. Try to change your input data");
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

		return new SignupManager();

	});
