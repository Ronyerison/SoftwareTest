package br.ufpi.ardigital.util;

public class Config {
	public static final String FILE_PATH = System.getProperty("user.home") + System.getProperty("file.separator") + "Dropbox" + System.getProperty("file.separator") + "script test" + System.getProperty("file.separator") + "oficio_1_pag.pdf";
	
	//Linux
	//public static final String CHROME_DRIVER_PATH = "src//test//resources//chromedriver";
	
	//Windows
	public static final String CHROME_DRIVER_PATH = "src//test//resources//chromedriver.exe";
	public static final String CHROME_DRIVER_LIB = "webdriver.chrome.driver";
	public static final String BASE_URL = "http://10.28.14.224:8181/";
	public static final String LOGIN_URL = "/ar-digital/login.xhtml";
	public static final String DOWNLOAD_URL = "/downloadDocumento/downloadDocumento.xhtml";
	public static final String COMMON_USER_NAME = "ar_user3";
	public static final String COMMON_USER_PASSWORD = "armestrado2016";
	public static final String ADMINISTRATOR_USER_NAME = "ar_user8";
	public static final String ADMINISTRATOR_USER_PASSWORD = "armestrado2016";
}
