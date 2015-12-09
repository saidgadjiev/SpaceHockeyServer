define([
	'backbone'
], function (Backbone) {

	var PlayerModel = Backbone.Model.extend({
		defaults: {
			login: 'Unnamed player',
			score: 0
		}
	});

	return PlayerModel;
});
