define([
	'backbone',
	'tmpl/main'
], function(
	Backbone,
	tmpl
){
	var View = Backbone.View.extend({
		el: $('#page'),
		template: tmpl,
		render: function() {
			this.$el.html(this.template);
			
			return this;
		},
		show: function() {
			this.$el.render();
		},
		hide: function() {
			this.$el.empty();
		}
	});

	return new View();;
});