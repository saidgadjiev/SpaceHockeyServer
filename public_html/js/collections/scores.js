define([
	'backbone',
	'models/score',
	'syncs/scoreSync'
], function (Backbone,
             PlayerModel,
             sync) {
	var PlayerCollection = Backbone.Collection.extend({
		model: PlayerModel,
		url: '/score?limit=10',
		sync: sync,

		comparator: function (playerA, playerB) {
			var scoreDiff = playerB.get('score') - playerA.get('score');
			if (scoreDiff === 0) {
				return playerA.get('name') < playerB.get('name') ? -1 : 1;
			}
			return scoreDiff;
		}
	});
	var playerCollection = new PlayerCollection();
	playerCollection.fetch();
	return playerCollection;
});
