define([
    'backbone'
], function(
    Backbone
){

    var Model = Backbone.Model.extend({
			name: 'Anonimus',
			score: 0;
    });

    return Model;
});
