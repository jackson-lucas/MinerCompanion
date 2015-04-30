<?php

include 'test/ConnectionTeste.php';

//$connectionTeste = new ConnectionTeste;

//$connectionTeste->send_post_data();


//$array_data = $_POST["data"];

if ((isset($_POST)) && !empty($_POST)) {

	$array_data = $_POST["data"];
	//$data = json_decode(utf8_decode($array_data));
	$data = json_decode(utf8_decode($array_data));
	
	//file_put_contents("matriculas.txt", $_POST["data"], FILE_APPEND);
	file_put_contents("matriculas.txt", json_encode($data, JSON_PRETTY_PRINT), FILE_APPEND);

	echo "OK";
	
}

else
	echo "Nenhum dado foi recebido\n";


?>


$json = json_decode($string);
echo json_encode($json, JSON_PRETTY_PRINT);