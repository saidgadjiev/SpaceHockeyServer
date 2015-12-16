define([
	'backbone',
	'tmpl/main',
	'models/userProfile',
	'utils/signout'
], function (Backbone,
             tmpl,
             User,
             SignoutManager) {

	var Main = Backbone.View.extend({
		template: tmpl,
		model: User,

		events: {
			"click .menu__item_logout": "logout"
		},

		initialize: function () {
			this.render();
			that = this;
			this.listenTo(User, 'change', function () {
				that.render();
			});
		},

		logout: function () {
			SignoutManager.exitRequest(this.model);
			this.render();
		},

		render: function () {
			userlogin = User.get('login');
			var userData = {
				"login": userlogin
			}
			this.$el.html(this.template(userData));
		},

		show: function () {
			this.trigger('show', this);
			this.$el.show();
		},

		hide: function () {
			this.$el.hide();
		},

	});

	return new Main();
});
