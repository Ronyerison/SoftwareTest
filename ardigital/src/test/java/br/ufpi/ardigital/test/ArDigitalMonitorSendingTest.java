package br.ufpi.ardigital.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

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
		Default.sendDocument(driver);
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		Default.waitInterval();
		assertEquals("Cobrança",
				driver.findElement(By.xpath("//tbody[@id='formTabelaDocumentos:tabelaAcompanhamento_data']/tr/td[2]"))
						.getText());
	}
	
	@Test
	public void monitorSendingAdvancedSearchDeclarationField() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		Default.sendDocument(driver);
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

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

}
