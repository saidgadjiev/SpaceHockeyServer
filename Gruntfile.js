module.exports = function(grunt){
	grunt.initConfig({
		watch: {
			fest: {
				files: ['templates/*.xml'],
				tasks: ['fest'],
				options: {
					interrupt: true,
					atBegin: true
				}
			},
			sass: {
				files: ['public_html/css/scss/*.scss'],
				tasks: ['sass:dev'],
			},
			server: {
				files: [
					'public_html/js/**/*.js', /* следим за статикой*/
					'public_html/css/**/*.css'
				],
				options: {
					interrupt: true,
					livereload: true /* перезагрузить страницу */
				}
			}
		},
		shell: {
			server: {
				command: 'java -cp Server.jar main.Main 8080'
			}
		},
		fest: {
			templates: { /* Цель */
				files: [{
					expand: true,
					cwd: 'templates', /* исходная директория */
					src: '*.xml', /* имена шаблонов */
					dest: 'public_html/js/tmpl' /* результирующая директория */
				}],
				options: {
					template: function (data) {
						return grunt.template.process(
							// 'var <%= name %>Tmpl = <%= contents %> ;',
							'define(function () { return <%= contents %> ; });',
							{data: data}
						);
					}
				}
			}
		},
		concurrent: {
			target: ['watch', 'shell'],
			options: {
				logConcurrentOutput: true /* Вывод логов */
			}
		},
		sass: {
			dev: {
				options: {
					style: 'expanded'
				},
				files: [{
					expand: true,
					cwd: 'public_html/css/scss',
					src: 'main.scss',
					dest: 'public_html/css',
					ext: '.css'
				}]
			}
		}
	});

	grunt.loadNpmTasks('grunt-contrib-watch');
	grunt.loadNpmTasks('grunt-concurrent');
	grunt.loadNpmTasks('grunt-shell');
	grunt.loadNpmTasks('grunt-fest');
	grunt.loadNpmTasks('grunt-contrib-sass');

	grunt.registerTask('default', ['sass:dev', 'concurrent']); /* задача по умолчанию */

}

