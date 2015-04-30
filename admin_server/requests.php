<?php

include 'controllers/AlertaController.php';

//Recebe dados vindos dos dispositivos e trata pra ver se encaixa nas seguintes opções:
//1 - Usuário está fazendo login
//2 - Envio de log do dispositivo
//3 - Usuário está fazendo logout
//4 - Mensagem misteriosa (o.O')

if ((isset($_POST)) && !empty($_POST)) {

	if(isset($_POST["login"])) {
		echo "OK";
	}

	else if(isset($_POST["log"])) {
		$log = $_POST["log"];

		//Armazena logs: salva no arquivo logs.txt
		echo "OK";

		//ARMAZENAMENTO DOS LOGS EM ARQUIVO
		$data = json_decode(utf8_decode($log));
		// file_put_contents("logs.txt", json_encode($data, JSON_PRETTY_PRINT), FILE_APPEND);
		// file_put_contents("logs.txt", ",", FILE_APPEND);
		
		$alerta = new AlertaController();

		$alerta->verifica_log($data);
	
		echo "OK";

		//Faz tratamento dos possíveis alertas
	}

	else if(isset($_POST["logout"])) {
		//Para de verificar logs para aquele dispositivo
		echo "OK";
	}


	else //Mensagem misteriosa
		echo "Dados recebidos são inválidos\n";
}

?>