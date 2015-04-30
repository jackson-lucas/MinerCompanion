<?php

/**
* 
*/
class AlertaController
{	
	function verifica_acelerometro($log) {
		$result = 0;

		//var_dump($log);

		foreach ($log as $accel => $value) {
			$result = $result + $value;
		}

		if((int) $result < 10) return false;
		else return true;
	}

	function verifica_luminosidade($valor) {
		if ($valor < 100) return false;
		else return true;
	}

	public function verifica_log($log) {
		if(!$this->verifica_acelerometro($log->valorAcelerometro)) echo "Minerador $log->idDispositivo está com suspeita de desmaio.";
		if($this->verifica_luminosidade($log->valorLuminosidade) == false) echo "Minerador $log->idDispositivo está com suspeita de soterramento.";
	}
}

?>