define([
	'backbone',
	'tmpl/scoreboard',
	'models/score',
	'collections/scores'
], function (Backbone,
             tmpl,
             playerModel,
             playerCollection) {

	var ScoreBoardView = Backbone.View.extend({
		template: tmpl,
		collection: playerCollection,

		initialize: function () {
			this.render();
		},

		render: function () {
			this.$el.html(this.template(this.collection.toJSON()));
		},


		show: function () {
			this.trigger('show', this);
			this.$el.show();

		},
		hide: function () {
			this.$el.hide();
		}

	});

	return new ScoreBoardView();
});

