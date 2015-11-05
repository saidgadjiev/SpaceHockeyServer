module.exports = function(grunt) {
	return {
		templates: {
			files: [{
				expand: true, //Расширение
				cwd: 'templates', //Исходная директория
				src: '*.xml', //Имена шаблонов
				dest: 'public_html/js/tmpl' //Результирующая директория
			}],
			options: {
	            template: function(data) {
	                return grunt.template.process(
                        'define(function () { return <%= contents %> ; });',
                        {
                            data: data
                        }
                    );
	            }
	        }
		}
	};
};