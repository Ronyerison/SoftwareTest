package br.ufpi.ardigital.factory;

import br.ufpi.ardigital.model.FileAr;
import br.ufpi.ardigital.util.Config;

public class FileArFactory {
	
	//TODO: Alterar o path para um diretorio válido pois este é do arquivo no computador do Rony
	public static FileAr createValidFile(){
		return new FileAr(Config.FILE_PATH);
	}
	
}
