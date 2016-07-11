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
import br.ufpi.ardigital.util.Constant;
import br.ufpi.ardigital.util.Default;

public class ArDigitalLoginTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Constant.ChromeDriverLib, Constant.ChromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void validLogintest() throws Exception {
		User usuarioValido = UserFactory.criaUsuarioValido();
		Default.login(driver, usuarioValido);
		assertEquals(usuarioValido.getLogin(),
				driver.findElement(By.id("formNotificacaoes:j_idt41_button"))
						.getText());
	}

	@Test
	public void testArDigitalLoginInvalid() throws Exception {
		Default.login(driver, UserFactory.criaUsuarioInvalido());
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
