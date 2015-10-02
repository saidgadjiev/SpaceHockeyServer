define([
    'backbone',
    'tmpl/login'
], function(
    Backbone,
    tmpl
){

    var View = Backbone.View.extend({
				el: $("#page"),
        template: tmpl,
        initialize: function () {
            // TODO
        },
        render: function () {

            $(this.el).html(this.template());

						$("#idForm").on("submit", function(event) {
						var formData = {
						    "login":$("#login").val(),
						    "password":$("#password").val()
						};
						alert($('#password').val());
						$.ajax({
							type: "POST",
							url: "SignIn",
							data:"jsonData=" + $.toJSON(formData),
                            dataType:'json',
							success: function(data)
								{
								    alert("OK");
								}
						});

						});
            return this;
        },
        show: function () {
            // TODO
        },
        hide: function () {
            // TODO
        }

    });

    return new View();
});


