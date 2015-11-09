define([
    'backbone',
    'tmpl/game',
    'views/showError',
    'models/userProfile',
    'game/gamePlay'
], function(
    Backbone,
    tmpl,
    error,
    userModel,
    gamePlay
){
    var View = Backbone.View.extend({
	    el: $("#page"),
        template: tmpl,
        model: userModel,
        render: function() {
            if (userModel.get("login")) {
                var userData = {
                    "login": userModel.get("login")
                }
                var userData = {
                     "login": userModel.get("login")
                }
                this.$el.html(this.template(userData));
                var canvas = document.getElementById('gamefield');
                gamePlay.start(canvas);
            } else {
                Backbone.history.navigate('login', {trigger: true});
                error.showLogoutError();
            }

            return this;
        },
        show: function() {
            this.$el.render();
        },
        hide: function() {
            this.$el.empty();
        }
    });

    return new View();
});
