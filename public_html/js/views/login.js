define([
	'backbone',
	'tmpl/login',
	'utils/validator',
	'utils/signin',
	'models/userProfile'
], function (Backbone,
             tmpl,
             Validator,
             SigninManager,
             User) {
	var form_class = ".form_signin";
	var validator = new Validator(form_class);

	var View = Backbone.View.extend({
		template: tmpl,
		model: User,

		events: {
			"submit .form_signin": "submitSignin"
		},

		initialize: function () {
			this.render();
			SigninManager.saveCache();
		},

		render: function () {
			$(this.el).html(this.template());
			return this;
		},

		submitSignin: function (event) {
			validator.clearErrors();
			validator.validateForm();


			if (validator.form_valid) {
				SigninManager.signinRequest(this.model);
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
