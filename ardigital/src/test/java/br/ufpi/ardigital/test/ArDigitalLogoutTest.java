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

public class ArDigitalLogoutTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Teste que realiza o logout.
	 * 
	 * <p>
	 * O teste consiste em fazer que com que o operador realize o logout e
	 * confirmar se ele tá na tela de login, verificando a presença de elementos
	 * dessa página.
	 * 
	 * @throws InterruptedException caso ocorra falhas ao realizar o teste
	 * @throws Exception caso ocorra falhas ao realizar o teste
	 * 
	 *
	 */
	@Test
	public void testarLogout() throws InterruptedException{
		//realizar o login
		Default.login(driver, UserFactory.createCommonUser());
		
		//Realizar o logout
		driver.findElement(By.id("formNotificacaoes:acaoSair_button")).click();
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='formNotificacaoes:acaoSair_menu']/ul/li/a/span[2]")).click();
		Default.waitInterval();
		
		//Verificar elementos da página de login
		assertEquals("Usuário*", driver.findElement(By.id("formLogin:j_idt12")).getText());
		assertEquals("Senha*", driver.findElement(By.id("formLogin:j_idt14")).getText());
		
		Default.waitInterval();
		driver.quit();
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
