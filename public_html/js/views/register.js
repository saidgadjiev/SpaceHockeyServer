define([
	'backbone',
	'tmpl/register',
	'utils/validator',
	'utils/signup',
	'models/userProfile'
], function (Backbone,
             tmpl,
             Validator,
             SignupManager,
             User) {
	var formClass = ".form_signup";
	var validator = new Validator(formClass);

	var View = Backbone.View.extend({
		template: tmpl,

		events: {
			"submit .form_signup": "submitSignup"
		},

		initialize: function () {
			this.render();
			SignupManager.saveCache();
		},

		render: function () {
			$(this.el).html(this.template());
			return this;
		},

		submitSignup: function (event) {
			validator.clearErrors();
			validator.validateForm();
			if (validator.form_valid) {
				SignupManager.signupRequest();
			}
			return false;
		},

		show: function () {
			validator.clearErrors()
			this.$el.show();
			this.trigger("show", this);
		},

		hide: function () {
			this.$el.hide();
		}

	});

	return new View();
});
