define([
    'backbone',
    'tmpl/main'
], function(
    Backbone,
    tmpl
){

    var Main = Backbone.View.extend({
        el: $("#page"),
        template: tmpl,
     		render: function () {
            this.$el.html(this.template);
            return this;
        },
        show: function () {
            this.$el.render();
        },
        hide: function () {
            this.$el.empty();
        }

    });
    var main = new Main($('#page'));
    return main;
});
