define([
	'backbone',
	'views/main',
	'views/game',
	'views/login',
	'views/register'
], function(
	Backbone,
	main,
	game,
	login,
	register
){
	var Router = Backbone.Router.extend({
		routes: {
			'game': 'gameController',
			'login': 'loginController',
			'register': 'registerController',
			'*default': 'defaultController'
		},
		defaultController: function(){
			main.render();
		},
		gameController: function(){
			game.render();
		},
		loginController: function(){
			login.render();
		},
		registerController: function(){
			register.render();
		}
	});
	
	return new Router();	
});