<?php

/**
* Essa classe define as atividades de recebimento e armazenamento dos Logs enviados pelos devices conectados.
*/

class LogsController /* extends AnotherClass */ {

	//Cria estrutura de armazenamento dos logs:
	//O chamado "log_container" será uma lista de no máximo 3 elementos que guardará os
	//3 últimos logs enviados por um device 'x'.
	function create_log_container() {
		$log = new Log();
	}
}


?>