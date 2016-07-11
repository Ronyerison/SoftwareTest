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

public class ArDigitalApproveDocumentTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Este teste realiza o envio de um documento com um usuario normal e
	 * realiza a aprovação do envio com um usuario admin
	 * 
	 * @throws Exception
	 */
	@Test
	public void acceptDocumentValidTest() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.sendDocument(driver);
		Default.logout(driver);
		Default.login(driver, UserFactory.createAdministratorUser());
		acceptDocument();
	}
	
	/**
	 * Este teste realiza o envio de um documento com um usuario normal e
	 * realiza a rejeição do envio com um usuario admin
	 * 
	 * @throws Exception
	 */
	@Test
	public void rejectDocumentValidTest() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.sendDocument(driver);
		Default.logout(driver);
		Default.login(driver, UserFactory.createAdministratorUser());
		rejectDocument();
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	public void rejectDocument() throws Exception {
		Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:rejeitarEnvio")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("j_idt164:motivoRejeicaoEnvio")).clear();
	    driver.findElement(By.id("j_idt164:motivoRejeicaoEnvio")).sendKeys("Rejeição Teste");
	    driver.findElement(By.id("j_idt164:j_idt168")).click();
	    Default.waitInterval();
	    assertEquals("O envio do documento foi rejeitado com sucesso.", driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
	  }
	
	private void acceptDocument() throws InterruptedException {
		Default.waitInterval();
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span"))
				.click();
		Default.waitInterval();
		driver.findElement(
				By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio"))
				.click();
		Default.waitInterval();
		driver.findElement(By.id("aprovarEnvioDialogForm:j_idt147")).click();
		Default.waitInterval();
		assertEquals(
				"Documento aprovado com sucesso.",
				driver.findElement(
						By.cssSelector("span.ui-messages-info-summary"))
						.getText());
	}

	
	

}
