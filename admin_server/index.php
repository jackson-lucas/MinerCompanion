<?php

include 'test/ConnectionTeste.php';

session_start();

//Inicializa log container
$log_container = array();

$_SESSION["log_container"] = $log_container;

//Fica processando logs em busca de alertas.

/* ARMAZENAMENTO DOS LOGS EM ARQUIVO
	$array_data = $_POST["data"];
	//$data = json_decode(utf8_decode($array_data));
	$data = json_decode(utf8_decode($array_data));
	
	//file_put_contents("matriculas.txt", $_POST["data"], FILE_APPEND);
	file_put_contents("matriculas.txt", json_encode($data, JSON_PRETTY_PRINT), FILE_APPEND);

	echo "OK";
*/


?>