module.exports = {
	fest: {
		files: ['templates/*.xml'],
		tasks: ['fest'], //Перекомпилировать
		options: {
			interrupt: true, //Уничтожить текущую задачу породить новую с новыми изменениями
			atBegin: true //Запуск задач при старте
		}
	},
	server: {
		files: [
			'public_html/js/**/*.js', //следим за статикой
           	'public_html/css/**/*.css' 
		],
		options: {
			interrupt: true,
			livereload: true //Автоматическая перезагрузка страницы
		}
	}
};