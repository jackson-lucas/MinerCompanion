<?php

/**
* Esta classe contém todos os testes relativos à conexão e recebimento/envio de dados.
*/

class ConnectionTeste
{
	function new_connection() {

	}
	function send_post_data($url) {
		//URL do arquivo que contém os logs de teste
		//$url = "test/logs2.txt";

		//Lê os dados do arquivo e já converte pra JSON.
		$log_json = json_decode(file_get_contents($url));

		//Envia dados para localhost
		$_POST[] = $log_json;
	}

	function send_post_data2($url) {
		//URL do arquivo que contém os logs de teste
		//$url = "test/logs2.txt";

		//Lê os dados do arquivo e já converte pra JSON.
		$log_json = json_decode(file_get_contents($url));

		//Envia dados para localhost
		$_POST[] = $log_json;
	}


}

?>