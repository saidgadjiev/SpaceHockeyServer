define([
	'backbone',
	'tmpl/login',
	'views/ajax',
    'views/showError',
    'models/userProfile'
], function(
	Backbone,
	tmpl,
	ajax,
    error,
    userModel
){
	var View =  Backbone.View.extend({
		el: $("#page"),
		template: tmpl,
		model: userModel,
		events: {
			"submit .form_signin": "submitSignin",
			"click a": "hide"
		},
		render: function(){
			$(this.el).html(this.template());

			return this;
		},
		submitSignin: function(event){
			var dataAjax = {
				'login': $("input[name = login]").val(),
				'password': $("input[name = password]").val()
			};
			$.when(ajax.sendAjax(dataAjax, "/auth/signin", "POST")).then(
				function (response) {
		  			response = JSON.parse(response);
		  			if (response.status == "200") {
		  				userModel.set({
		  					"login": response.body.login
		  				});
		  				Backbone.history.navigate('game', {trigger: true});
		  			} else {
		  				error.showLoginError(response);
		  			}
				},
				function (error) {
		 		 	console.log(error.statusText);
				}
  			);			

            return false;
		},
		show: function(){
			this.$el.render();
		},
		hide: function(){
			this.$el.empty();
		}
	});

	return new View();
});