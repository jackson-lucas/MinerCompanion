<?php 

function verifica_acelerometro($dim) {
	$result = 0;
	foreach ($string->valorAcelerometro as $accel => $value) {
	$result = $result + $value;
}

if((int) $result == 0) echo "está parado";
else echo "está em mov.";
	}

function verifica_log($log) {
		$result = array_map("verifica_acelerometro", $log->valorAcelerometro);
		
		var_dump($result);
	}

$log = file_get_contents("../logs.txt");
$string = json_decode(utf8_decode($log));

var_dump($string);

$result = 0;
foreach ($string->valorAcelerometro as $accel => $value) {
	$result = $result + $value;
}

if((int) $result == 0) echo "está parado";
else echo "está em mov.";

 ?>