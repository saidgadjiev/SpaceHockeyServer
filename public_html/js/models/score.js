define([
    'backbone'
], function(
    Backbone
){

    var PlayerModel = Backbone.Model.extend({
			name: 'Anonimus',
			score: 0
    });

    return PlayerModel;
});
