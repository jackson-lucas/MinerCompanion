<?php

/**
* Esta classe fica responsável pelo armazenamento lógico e físico dos logs de dispositivo.
* Armazenamento lógico: estrutura salva na memória do programa.
* Armazenamento físico: alertas que serão salvos no banco de dados.
*/

class StorageController {

	public static function criar_log_buffer($log_container) {
		$log_buffer = new LogBuffer();
		array_push($log_container, $log_buffer);
	}
}

?>