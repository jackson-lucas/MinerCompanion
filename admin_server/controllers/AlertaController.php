<?php

define(LUZ_MINIMA, 10);
define(MOV_MINIMA, [10,10,10]);

	function analisa_logs($log) {
		foreach ($log as $log_unico) {
			checar_alertas($log_unico);
		}
	}

	function emitir_alerta($id, $tipo_alerta) {
		if($tipo_alerta == 1) echo "Minerador $id está com problemas de conexão.";

		else if($tipo_alerta == 2) echo "Minerador $id está com suspeita de soterramento.";

		else if($tipo_alerta == 3) echo "Minerador $id está com suspeita de desmaio.";
	}

	function checar_alertas($log_unico) {
		$id 	= $log_unico->id_dispositivo;
		$lum 	= $log_unico->valorLuminosidade;
		$mov 	= $log_unico->valorAcelerometro;
		$loc 	= $log_unico->valorLocalizacao;

		//Primeiro checa se houve resposta de todos os dispositivos
		if($id == "") emitir_alerta($id, 1);
		
		//Depois checa por suspeita de soterramento
		if($lum < LUZ_MINIMA && $mov < MOV_MINIMA) emitir_alerta(2);

		//Depois checa por suspeita de desmaio
		if($mov < MOV_MINIMA) emitir_alerta(3);
	}
?>