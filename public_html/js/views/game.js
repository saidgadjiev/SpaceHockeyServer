define([
	'backbone',
	'game/socket',
	'tmpl/game',
	'models/userProfile',
	'game/gamePlay'
], function (Backbone,
             socket,
             tmpl,
             User,
             gamePlay) {

	var View = Backbone.View.extend({
		template: tmpl,

		events: {
			"click .submit-btn": "restart"
		},

		initialize: function () {
			this.render();
			self = this;
			this.listenTo(User, 'change', function () {
				self.render();
			});
		},

		render: function () {
			user = User.get('login');
			console.log("render!");
			if (user) {
				var userData = {
					'login': user
				}
				// socket.init(userData);
				console.log(gamePlay.gameStarted);
				this.$el.html(this.template(userData));
				var canvas = document.getElementById('gamefield');
				if(gamePlay.gameStarted == false){
					console.log("gameStarted");
					gamePlay.start(canvas);
				}
				else{
					Backbone.history.navigate('', {trigger: true});			
				}
			}
			else {
				Backbone.history.navigate('login', {trigger: true});
			}
			return this;
		},

		restart: function () {
			console.log("restart!");
			this.render();
		},

		show: function () {
			this.$el.show();
			this.trigger("show", this);
		},
		hide: function () {
			this.$el.hide();
		}

	});

	return new View({model: User});
});
