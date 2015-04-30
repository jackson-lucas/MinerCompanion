<?php 

session_start();

//Se o log_container já tiver sido inicializado, vamos inserir os novos buffers nele.
if(isset($_SESSION["log_container"])) {
	$log_container = $_SESSION["log_container"];
}

else {
	//Inicializa log container
	$log_container = array();
	$_SESSION["log_container"] = $log_container;
}

//Recebe dados vindos dos dispositivos e trata pra ver se encaixa nas seguintes opções:
//1 - Usuário está fazendo login
//2 - Envio de log do dispositivo
//3 - Usuário está fazendo logout
//4 - Mensagem misteriosa (o.O')

if ((isset($_POST)) && !empty($_POST)) {

	if(isset($_POST["login"])) {
		//Cria estrutura de armazenamento "log_buffer" para logs que virão daquele dispositivo.
		$id = $_POST["idDispositivo"];

		$log_buffer = StorageController::criar_log_buffer($log_container, $id);
		echo "OK";
	}

	else if(isset($_POST["log"])) {
		$log = $_POST["log"];

		//Armazena logs
		$log_container[$log->id] = var_dump($log);
		echo "OK";
		
		//Faz tratamento dos possíveis alertas
	}

	else if(isset($_POST["logout"])) {
		//Para de verificar logs para aquele dispositivo
	}

	else if(isset($_POST["data"])) {
		echo "OK";
	}

	else //Mensagem misteriosa
		echo "Dados recebidos são inválidos\n";
}

?>