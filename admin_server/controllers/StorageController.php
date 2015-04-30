<?php

/**
* Esta classe fica responsável pelo armazenamento lógico e físico dos logs de dispositivo.
* Armazenamento lógico: estrutura salva na memória do programa.
* Armazenamento físico: alertas que serão salvos no banco de dados.
*/

class StorageController {

	public static function criar_log_buffer($log_container, $id) {
		$log_buffer = array();
		$log_container[$id] = $log_buffer;

		return $log_buffer;
	}

	public static function armazena_log($log_container, $id) {
		$log_post = $_POST["log"];
		$log = json_decode(utf8_decode($log_post));

		array_push($log_container[$id], $log);
	}
}

?>