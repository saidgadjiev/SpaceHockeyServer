define([
	'lib/noty/packaged/jquery.noty.packaged'
],
	function() {
		function showRegistrationError(data) {
			console.log(data);
			if (data.status == "400") {
				if (data.body.login == "") {
					show('Error: login can consists [a-zA-Z0-9]{4,25}');
				}
				if (data.body.password == "") {
					show('Error: password can consists [a-zA-Z0-9]{6,25}');
				}
			} else {
				show('Error: user with that login already exists');
			}
		}
		function showLoginError(data) {
			if (data.status == "302") {
				show('Error: you are logged in');
			} else {
				show('Error: wrong login or password');
			}
		}
		function showLogoutError() {
			console.log("Logout");
			show('Error: you not authorized');
		}
		function show(textError) {
			noty({
    			text: textError,
    			type: 'error',
    			layout: 'bottomRight',
    			timeout: 5000
    		});
		}

		return {
			showRegistrationError:showRegistrationError,
			showLoginError:showLoginError,
			showLogoutError:showLogoutError
		}
	}
)