package br.ufpi.ardigital.factory;

import br.ufpi.ardigital.model.FileAr;

public class FileArFactory {
	
	//TODO: Alterar o path para um diretorio válido pois este é do arquivo no computador do Rony
	public static FileAr createValidFile(){
		return new FileAr("C:\\Users\\Ronyerison\\git\\ardigital\\ardigital\\src\\test\\resources\\oficio_1_pag.pdf");
	}
}
