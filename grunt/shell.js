module.exports = {
	options: {
        stdout: true,
        stderr: true
    },
	server: { //Подзадача
				command: 'java -cp Server.jar main.Main 8080' //Запуск сервера
	}
}