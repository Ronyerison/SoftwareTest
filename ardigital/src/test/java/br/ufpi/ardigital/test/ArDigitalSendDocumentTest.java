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

public class ArDigitalSendDocumentTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("chromedriver.exe",
				"..//resources//chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://10.28.14.224:8181/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void sendDocumentInvalidTest() throws Exception {
		User validUser = UserFactory.criaUsuarioValido();
		driver.get(baseUrl + "/ar-digital/login.xhtml");
		driver.findElement(By.id("j_idt11:email")).clear();
		driver.findElement(By.id("j_idt11:email")).sendKeys(
				validUser.getLogin());
		driver.findElement(By.id("j_idt11:senha")).clear();
		driver.findElement(By.id("j_idt11:senha")).sendKeys(
				validUser.getPassword());
		driver.findElement(By.id("j_idt11:j_idt17")).click();
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span"))
				.click();
		driver.findElement(By.id("form:tipo_label")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys("Cobrança teste");
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		assertEquals("É necessário informar um Interessado", driver
				.findElement(By.cssSelector("span.ui-messages-error-summary"))
				.getText());
	}

	@Test
	public void sendDocumentValidTest() throws Exception {
		User validUser = UserFactory.criaUsuarioValido();
		driver.get(baseUrl + "/ar-digital/login.xhtml");
		driver.findElement(By.id("j_idt11:email")).clear();
		driver.findElement(By.id("j_idt11:email")).sendKeys(
				validUser.getLogin());
		driver.findElement(By.id("j_idt11:senha")).clear();
		driver.findElement(By.id("j_idt11:senha")).sendKeys(
				validUser.getPassword());
		driver.findElement(By.id("j_idt11:j_idt17")).click();
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
		driver.findElement(By.id("form:upOficio_input")).sendKeys(file.getPath());
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:ajax")).click();
	    assertEquals("Documento encaminhado com sucesso!", driver.findElement(By.cssSelector("div.ui-grid-col-12")).getText());
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private void waitForLoadPage() throws InterruptedException {
		Thread.sleep(2000);
	}

}
