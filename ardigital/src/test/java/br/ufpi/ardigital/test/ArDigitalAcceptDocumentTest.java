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

import br.ufpi.ardigital.factory.FileArFactory;
import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.model.FileAr;
import br.ufpi.ardigital.model.User;
import br.ufpi.ardigital.util.Constant;

public class ArDigitalAcceptDocumentTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Constant.ChromeDriverLib, Constant.ChromeDriverPath);
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
	public void sendDocumentValidTest() throws Exception {
		login(UserFactory.criaUsuarioValido());
		sendDocument();
		logout();
		login(UserFactory.criaUsuarioAdminValido());
		acceptDocument();
	}

	private void acceptDocument() throws InterruptedException {
		waitForLoadPage();
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span"))
				.click();
		waitForLoadPage();
		driver.findElement(
				By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio"))
				.click();
		driver.findElement(By.id("aprovarEnvioDialogForm:j_idt147")).click();
		assertEquals(
				"Documento aprovado com sucesso.",
				driver.findElement(
						By.cssSelector("span.ui-messages-info-summary"))
						.getText());
	}

	/**
	 * @throws InterruptedException
	 */
	private void sendDocument() throws InterruptedException {
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span"))
				.click();
		driver.findElement(By.id("form:tipo_label")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys(
				"Teste documento válido");
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys(
				"SAULO DE TÁRSIO");
		waitForLoadPage();
		driver.findElement(
				By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li"))
				.click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:upOficio_input")).clear();
		FileAr file = FileArFactory.createValidFile();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(
				file.getPath());
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:ajax")).click();
		assertEquals("Documento encaminhado com sucesso!",
				driver.findElement(By.cssSelector("div.ui-grid-col-12"))
						.getText());
	}

	/**
	 * @throws InterruptedException
	 * 
	 */
	private void logout() throws InterruptedException {
		driver.findElement(By.id("formNotificacaoes:j_idt42_button")).click();
		waitForLoadPage();
		driver.findElement(
				By.xpath("//div[@id='formNotificacaoes:j_idt42_menu']/ul/li/a/span[2]"))
				.click();
		waitForLoadPage();
	}

	private void login(User validUser) {
		driver.get(Constant.BaseURL + Constant.LoginURL);
		driver.findElement(By.id("j_idt11:email")).clear();
		driver.findElement(By.id("j_idt11:email")).sendKeys(
				validUser.getLogin());
		driver.findElement(By.id("j_idt11:senha")).clear();
		driver.findElement(By.id("j_idt11:senha")).sendKeys(
				validUser.getPassword());
		driver.findElement(By.id("j_idt11:j_idt17")).click();
	}

	private void waitForLoadPage() throws InterruptedException {
		Thread.sleep(2000);
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
