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
import br.ufpi.ardigital.model.User;

public class ArDigitalLoginTest {
	private WebDriver driver;
	private String baseUrl;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty("chromedriver.exe", "..//resources//chromedriver.exe");
		driver = new ChromeDriver();
		baseUrl = "http://10.28.14.224:8181/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void validLogintest() throws Exception {
		User usuarioValido = UserFactory.criaUsuarioValido();
		driver.get(baseUrl + "/ar-digital/login.xhtml");
		driver.findElement(By.id("j_idt11")).click();
		driver.findElement(By.id("j_idt11:email")).clear();
		driver.findElement(By.id("j_idt11:email")).sendKeys(
				usuarioValido.getLogin());
		driver.findElement(By.id("j_idt11:senha")).clear();
		driver.findElement(By.id("j_idt11:senha")).sendKeys(
				usuarioValido.getPassword());
		driver.findElement(By.id("j_idt11:j_idt17")).click();
		assertEquals(usuarioValido.getLogin(),
				driver.findElement(By.id("formNotificacaoes:j_idt41_button"))
						.getText());
	}

	@Test
	public void testArDigitalLoginInvalid() throws Exception {
		User usuarioInvalido = UserFactory.criaUsuarioInvalido();
		driver.get(baseUrl + "/ar-digital/login.xhtml");
		driver.findElement(By.id("j_idt11:email")).clear();
		driver.findElement(By.id("j_idt11:email")).sendKeys(
				usuarioInvalido.getLogin());
		driver.findElement(By.id("j_idt11:senha")).clear();
		driver.findElement(By.id("j_idt11:senha")).sendKeys(
				usuarioInvalido.getPassword());
		driver.findElement(By.id("j_idt11:j_idt17")).click();
		assertEquals(
				"Usuário ou senha incorretos",
				driver.findElement(
						By.cssSelector("span.ui-messages-error-summary"))
						.getText());
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
