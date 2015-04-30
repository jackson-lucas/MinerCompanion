<?php 

//Recebe dados vindos dos dispositivos e trata pra ver se encaixa nas seguintes opções:
//1 - Usuário está fazendo login
//2 - Envio de log do dispositivo
//3 - Usuário está fazendo logout
//4 - Mensagem misteriosa (o.O')

if ((isset($_POST)) && !empty($_POST)) {

	if(isset($_POST["login"])) {
		//Cria estrutura de armazenamento "log_buffer" para logs que virão daquele dispositivo.
		$id = $_POST["idDispositivo"];
		criar_log_buffer($id);
	}

	else if(isset($_POST["log"])) {
		//Armazena logs
		//Faz tratamento dos possíveis alertas
	}

	else if(isset($_POST["logout"])) {
		//Para de verificar logs para aquele dispositivo
	}

	else //Mensagem misteriosa
		echo "Dados recebidos são inválidos\n";
}

?>