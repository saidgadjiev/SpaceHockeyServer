define([
    'backbone',
    'tmpl/register',
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
    var View = Backbone.View.extend({
		el: $("#page"),
        template: tmpl,		
		events: {
            "submit #form_signup": "submitSignup",
            "submit #form_signout": "submitSignout",
            "click a": "hide"
		},
        render: function () {
            $(this.el).html(this.template());

            return this;
		},
		submitSignup: function(event) {
			var dataAjax = {
				'login': $("input[name = login]").val(),
				'password': $("input[name = password]").val(),
				'email': $("input[name = email]").val()
			}
			$.when(ajax.sendAjax(dataAjax, "/auth/signup", "POST")).then(
				function (response) {
		  			response = JSON.parse(response);
		  			if (response.status == "200") {
		  				Backbone.history.navigate('login', {trigger: true});
		  			} else {
		  				error.showRegistrationError(response);
		  			}
				},
				function (error) {
		 		 	console.log(error.statusText);
				}
  			);								
            return false;
        },
        submitSignout: function(event) {
            $.when(ajax.sendAjax('', "/auth/signout", "GET")).then(
                function (response) {
                    response = JSON.parse(response);
                    if (response.status == "200") {
                        userModel.clear();
                    } else {
                        error.showLogoutError();
                    }
                },
                function (error) {
                    console.log(error.statusText);
                }
            );                             
            return false;
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
