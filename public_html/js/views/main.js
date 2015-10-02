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
        initialize: function () {
            // this.listenTo(this.collection, "change", this.render);
        },
        render: function () {
            $(this.el).html(this.template());
						

            return this;
        },
        show: function () {

        },
        hide: function () {
        
        }

    });
    var main = new Main($('#page'));
    return main;
});
