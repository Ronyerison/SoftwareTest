package br.ufpi.ardigital.util;

public class Config {
	
	//Testar alterar permissão ususário
	public static final int ALTERA_PERMISSAO_AJUSTE_PARAMETROS_FLUXO_ECARTA = 0;
	public static final int ALTERA_PERMISSAO_AJUSTE_PARAMETROS_PAINEL_CONTROLE= 1;
	public static final int ALTERA_PERMISSAO_PODE_APROVAR_ENVIOS = 2;
	public static final int ALTERA_PERMISSAO_ENVIO_SEM_NECESSIDADE_APROVACAO = 3;
	public static final int ALTERA_PERMISSAO_VISUALIZAR_TODOS_DOCUMENTOS = 4;
	public static final int ALTERA_PERMISSAO_AJUSTAR_PERMISSOES = 5;

	
	
	//Diretório dos arquivos que serão cerrgados nos casos de uso de envio e aprovação de documentos
	public static final String FILE_PATH = "C:\\Users\\saulo_000\\Desktop\\banco_teste_ar_digital\\oficio_teste.pdf";
	public static final String OFICIO_PAISAGEM = "C:\\Users\\saulo_000\\Desktop\\banco_teste_ar_digital\\oficio_paisagem_teste.pdf";
	public static final String ANEXO_PAISAGEM = "C:\\Users\\saulo_000\\Desktop\\banco_teste_ar_digital\\anexo_paisagem_teste.pdf";
	public static final String OFICIO_DUAS_PAGINAS = "C:\\Users\\saulo_000\\Desktop\\banco_teste_ar_digital\\oficio_teste_2_paginas.pdf";
	
	//Windows
	public static final String CHROME_DRIVER_PATH = "src//test//resources//chromedriver.exe";
	public static final String CHROME_DRIVER_LIB = "webdriver.chrome.driver";
	public static final String BASE_URL = "http://localhost:8080";
	public static final String LOGIN_URL = "/ar-digital/login.xhtml";
	public static final String DOWNLOAD_URL = "/downloadDocumento/downloadDocumento.xhtml";
	
	//Senhas e usuários para teste de operadores
	//Operador comum 1
	public static final String COMMON_USER_NAME = "paulo.filho";
	public static final String COMMON_USER_PASSWORD = "15Anaamytears@";
	//Operador comum 2
	public static final String COMMON_USER_NAME_2 = "george.silva";
	public static final String COMMON_USER_PASSWORD_2 = "b1!ior1#";
	//Operador master
	public static final String ADMINISTRATOR_USER_NAME = "saulo.silva";
	public static final String ADMINISTRATOR_USER_PASSWORD = "#saulo135";
}
