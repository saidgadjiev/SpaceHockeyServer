define([
	'backbone'
], function(Backbone) {
	var Model = Backbone.Model.extend({
		initialize: function() {
			console.log("This model has been initialized");
		},
		isLoggedIn: function() {
			console.log("Check login");
		},
		signin: function(data) {
			this.set(data);
		}
	});

	return new Model();
});