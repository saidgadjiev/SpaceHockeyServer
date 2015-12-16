require.config({
	urlArgs: "_=" + (new Date()).getTime(), //Для кеширования дополнительные параметры
	baseUrl: "js", //Базовый путь, где лежат все модули
	paths: { //Пути для модулей, которые находятся не в baseUrl
		jquery: "lib/jquery",
		underscore: "lib/underscore",
		backbone: "lib/backbone",
	},
	shim: {  //Для поддержки модулей сторонних модулей описанных не через define
		backbone: {
			deps: ['underscore', 'jquery'],
			exports: 'Backbone' //Имя модуля
		},
		underscore: {
			exports: '_'
		}
	}
});

define ([//Описание модуля
	'backbone',
	'router'
], function(  //Функция выполняется после загрузки всех модулей
	Backbone,
	router
){
	Backbone.history.start();
});