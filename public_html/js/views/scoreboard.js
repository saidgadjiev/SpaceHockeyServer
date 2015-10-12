define([
    'backbone',
    'tmpl/scoreboard',
	'collections/scores'
], function(	
    Backbone,
    tmpl,
	playerCollection
){

    var ScoreBoardView = Backbone.View.extend({				
        el: $("#page"),
        template: tmpl,

        render: function () {
            this.$el.html(this.template);
			var self = this;             

			playerCollection.forEach(function(num){
				self.$el.find('.score-list').append('<tr class= "score-list__item"><th>'+num.get('name')+'</th><th>'+num.get('score')+'<th><tr>');
			});
        },
        show: function () {
            this.$el.render();
        },
        hide: function () {
            this.$el.empty();
        }
    });

    return  new ScoreBoardView();
});