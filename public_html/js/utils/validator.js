define(['backbone'],
	function (Backbone) {
		// if typeForm = 1 -> it is login form, if typeForm = 0 -> register form
		var Validator = function (typeForm) {
			this.form_valid = false;
			this.type = typeForm;
			this.validateForm = function () {
				if (typeForm == ".form_signin")
					this.form_valid = validateLogin(this);
				else if (typeForm == ".form_signup")
					this.form_valid = validateRegister(this);
			};

			this.clearErrors = function () {
				$('.form__row_errors').text("");
				$('.form__row_errors').css('display', 'none');
			};
		}


		function validateLogin(form) {
			var valid = checkName(form) && checkPassword(form);
			if (!valid)
				$('.form__row_errors').css('display', 'block');
			return valid;
		}

		function validateRegister(form) {
			var valid = checkName(form) && checkPasswords(form) && checkEmail(form);
			if (!valid)
				$('.form__row_errors').css('display', 'block');
			return valid;
		}

		function checkPasswords(form) {
			var userPassword1 = $(form.type + " input[name = password]").val();
			var userPassword2 = $(form.type + " input[name = password2]").val();

			if (userPassword1.length < 6) {
				$('.form__row_errors').text("Your password must contain at least 6 symbols");
				return false;

			}
			if (userPassword1 != userPassword2) {
				$('.form__row_errors').text("Passwords should be the same!Input again, please.");
				return false;
			}
			return true;
		}

		function checkName(form) {
			var userName = $(form.type + " input[name = login]").val();

			if (userName == '') {
				$('.form__row_errors').text("Input your login, please!");
				return false;
			}
			return true;
		}

		function checkPassword(form) {
			var userPassword = $(form.type + " input[name = password]").val();
			if (userPassword == '') {
				$('.form__row_errors').text("Input your password, please!");
				return false;
			}
			return true;
		}

		function checkEmail(form) {
			var userEmail = $(form.type + " input[name = email]").val();
			if (userEmail == '') {
				$('.form__row_errors').text("Input your email, please!");
				return false;
			}
			return true;
		}

		return Validator;
	});
