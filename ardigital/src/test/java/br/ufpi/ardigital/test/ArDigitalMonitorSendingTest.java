package br.ufpi.ardigital.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.util.Config;
import br.ufpi.ardigital.util.Default;
import br.ufpi.ardigital.util.Field;

public class ArDigitalMonitorSendingTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	
	
	/**
	 * Teste para verificar se um documento enviado aparece na lista de acompanhamento
	 * 
	 * @throws Exception
	 */
	@Test
	public void monitorSendingSuccessfully() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "SAULO DE TÁRSIO", "Documento encaminhado com sucesso!");
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		Default.waitInterval();
		assertEquals("Cobrança",
				driver.findElement(By.xpath("//tbody[@id='formTabelaDocumentos:tabelaAcompanhamento_data']/tr/td[2]"))
						.getText());
	}
	
	/**
	 * 
	 * Caso de teste para busca avançada pelo campo "DECLARAÇÃO" durante o acompanhamento de envio. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void monitorSendingAdvancedSearchDeclarationField() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "SAULO DE TÁRSIO", "Documento encaminhado com sucesso!");
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.xpath("//fieldset[@id='formPesquisar:j_idt66']/legend")).click();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:declaracaoDeConteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:declaracaoDeConteudo")).sendKeys(Field.DECLARATION_TEXT_SEND_DOC.substring(0, 15));
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:ajax")).click();
		Default.waitInterval();
		assertEquals("SAULO DE TÁRSIO SILVA SOUSA", driver.findElement(By.cssSelector("td")).getText());
	}
	
	/**
	 *  Caso de teste para busca avançada pelo campos "DATA" durante o acompanhamento de envio. 
	 *  
	 * @throws Exception
	 */
	@Test
	  public void monitorSendingAdvancedSearchDateField() throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String actualDate = sdf.format(Calendar.getInstance().getTime());
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "SAULO DE TÁRSIO", "Documento encaminhado com sucesso!");
		Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
	    Default.waitInterval();
	    driver.findElement(By.xpath("//fieldset[@id='formPesquisar:j_idt66']/legend")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:j_idt83_input")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:j_idt86_input")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:ajax")).click();
	    Default.waitInterval();
	    String tableDate = driver.findElement(By.xpath("//tbody[@id='formTabelaDocumentos:tabelaAcompanhamento_data']/tr/td[3]")).getText();
	    Default.waitInterval();
	    assertEquals(actualDate, tableDate.substring(0, 10));	
	}
	
	/**
	 * Caso de teste para busca avançada pelo campo "OPERADOR" durante o acompanhamento de envio 
	 * com usuário ADMINISTRADOR.
	 *  
	 * @throws Exception
	 */
	@Test
	  public void monitorSendingAdvancedSearchForOperatorWithAdministratorUser() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "SAULO DE TÁRSIO", "Documento encaminhado com sucesso!");
		Default.waitInterval();
		Default.logout(driver);
		Default.waitInterval();
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
	    Default.waitInterval();
	    driver.findElement(By.xpath("//fieldset[@id='formPesquisar:j_idt66']/legend")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:operadorAutocomplete_input")).clear();
	    Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:operadorAutocomplete_input")).sendKeys(Config.COMMON_USER_NAME);
	    Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:ajax")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("formTabelaDocumentos:tabelaAcompanhamento:0:detalheDocumentoEnvio")).click();
	    Default.waitInterval();
	    assertEquals(Config.COMMON_USER_NAME, driver.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt136']/tbody/tr/td[2]")).getText());
	  }
	
	/**
	 * Caso de teste para busca avançada pelo campo "SETOR RESPONSÁVEL" durante o acompanhamento de envio. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void monitorSendingAdvancedSearchForSender() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "SAULO DE TÁRSIO", "Documento encaminhado com sucesso!");
		Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		Default.waitInterval();
	    driver.findElement(By.xpath("//fieldset[@id='formPesquisar:j_idt66']/legend")).click();
		Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:setorAutocomplete_input")).clear();
		Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:setorAutocomplete_input")).sendKeys("UFPI - Universidade Federal do Pauí");
		Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:ajax")).click();
		Default.waitInterval();
	    driver.findElement(By.id("formTabelaDocumentos:tabelaAcompanhamento:0:detalheDocumentoEnvio")).click();
		Default.waitInterval();
	    assertEquals(Field.ADMINISTRATOR_USER_SECTOR, driver.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt136']/tbody/tr[2]/td[2]")).getText());
	}
	
	/**
	 * Caso de teste para busca avançada pelo campo "SETOR REMETENTE" durante o acompanhamento de envio. 
	 * 
	 * @throws Exception
	 */
	@Test
	public void monitorSendingAdvancedSearchForSectorSender() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "SAULO DE TÁRSIO", "Documento encaminhado com sucesso!");
		Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		Default.waitInterval();
	    driver.findElement(By.xpath("//fieldset[@id='formPesquisar:j_idt66']/legend")).click();
		Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:setorAutocomplete_input")).clear();
		Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:setorRemtenteAutocomplete_input")).sendKeys("UFPI - Universidade Federal do Pauí");
		Default.waitInterval();
	    driver.findElement(By.id("formPesquisar:ajax")).click();
		Default.waitInterval();
	    driver.findElement(By.id("formTabelaDocumentos:tabelaAcompanhamento:0:detalheDocumentoEnvio")).click();
		Default.waitInterval();
	    assertEquals(Field.ADMINISTRATOR_USER_SECTOR, driver.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt136']/tbody/tr[3]/td[2]")).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
