define([
    'backbone',
    'tmpl/game'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
		el: $("#page"),
        template: tmpl,
     
        render: function () {
            this.$el.html(this.template(JSON.parse(window.localStorage['object'])));
            return this;
        },
        show: function () {
            this.$el.render();
        },
        hide: function () {
            this.$el.empty();
        }

    });

    return new View();
});
