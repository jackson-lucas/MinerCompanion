<?php

/**
* Essa classe é responsável pelas funções de conexão com o servidor
*/

class ConnectionController {
	
	/**
	 * has_new_connection
	 *
	 * @return 
	 * @author Victor Valente
	 **/
	
	function has_new_connection() {
		if (isset($_POST)) {
			print_r($_POST);

			return true;
		}

		return false;
	}
}

?>