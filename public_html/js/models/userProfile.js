define([
	'backbone',
	'syncs/userSync'
], function (Backbone,
             userSync) {

	var UserModel = Backbone.Model.extend({
		sync: userSync,
		url: "/profile",

		defaults: {
			login: "",
			password: "",
			email: "",
			logged_in: false
		},

		initialize: function () {
			console.log("This model has been initialized");
		},
		isLoggedIn: function () {
			console.log("Check login");
		},

		loginSuccess: function (data) {
			this.set(data);
		}
	});

	var user = new UserModel();
	user.fetch();
	return user;
});
