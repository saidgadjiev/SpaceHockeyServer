define([
    'backbone',
    'views/game',
    'views/main',
    'views/login',
    'views/scoreboard',
		'views/register'
], function(
    Backbone,
    game,
    main,
    login,
    scoreboard,
		register
){

    var Router = Backbone.Router.extend({
        routes: {
            'scoreboard': 'scoreboardAction',
            'game': 'gameAction',
            'login': 'loginAction',
            'register': 'registerAction',
            '*default': 'defaultActions'
        },
        defaultActions: function () {
            main.render();
        },
        scoreboardAction: function () {
            scoreboard.render();
        },
        gameAction: function () {
            game.render();
        },
        loginAction: function () {
            login.render();
        },
				registerAction: function () {
            register.render();
        }
    });

    return new Router();
});
