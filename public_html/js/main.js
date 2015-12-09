require.config({
	// urlUrgs - доп. параметры для скрипта(решает вопрос кэширования)
	urlArgs: "_=" + (new Date()).getTime(),

	// базовый путь, где лежат все модули
	baseUrl: "js",
	paths: {
		// пути для модулей, которые находятся не в baseUrl
		jquery: "lib/jquery",
		underscore: "lib/underscore",
		backbone: "lib/backbone",
	},
	shim: {
		// параметр shim позволяет добавить сторонние модули
		// (который без метода define)
		'backbone': {
			deps: ['underscore', 'jquery'],
			exports: 'Backbone'
		},
		'underscore': {
			exports: '_'
		}
	}
});

define([
	'backbone',
	'router'
], function (Backbone,
             router) {
	Backbone.history.start();
});
