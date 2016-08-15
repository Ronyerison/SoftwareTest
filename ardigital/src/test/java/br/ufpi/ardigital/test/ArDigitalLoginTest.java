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

import br.ufpi.ardigital.util.Config;

public class ArDigitalLoginTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}
	
	/**
	 * Testar login sem senha
	 * @throws Exception
	 */
	@Test
	public void testarLoginSemSenha() throws Exception {
		driver.get("http://localhost:8080/ar-digital/login.xhtml");
		
		driver.findElement(By.id("formLogin:username")).clear();
		driver.findElement(By.id("formLogin:username")).sendKeys("teste");
		driver.findElement(By.id("formLogin:botaoLogin")).click();
		
		assertEquals("É necessário informar uma senha", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		
		driver.quit();
	}
	
	/**
	 * Testar login sem usuário
	 * @throws Exception
	 */
	@Test
	public void testarLoginSemUsuario() throws Exception {
		driver.get("http://localhost:8080/ar-digital/login.xhtml");
		
		driver.findElement(By.id("formLogin:username")).clear();
		driver.findElement(By.id("formLogin:senha")).sendKeys("teste");
		driver.findElement(By.id("formLogin:botaoLogin")).click();
		
		assertEquals("É necessário informar um usuário", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		
		driver.quit();
	}
	
	/**
	 * Testar login com senha ou usuário incorretos
	 * @throws Exception
	 */
	@Test
	public void testarLoginSenhaUsuarioSenhaIncorretos() throws Exception {
		driver.get("http://localhost:8080/ar-digital/login.xhtml");
		
		driver.findElement(By.id("formLogin:username")).sendKeys("teste");
		driver.findElement(By.id("formLogin:senha")).sendKeys("teste");
		driver.findElement(By.id("formLogin:botaoLogin")).click();
		
		assertEquals("Usuário ou senha incorretos", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		
		driver.quit();
	}
	
	/**
	 * Testar login com senha e usuário corretos.
	 * 
	 * <p>
	 *	Teste com operador administrador
	 *
	 * @throws Exception caso ocorra falhas ao realizar o teste
	 */
	@Test
	public void testarLoginOk1() throws Exception {
		driver.get("http://localhost:8080/ar-digital/login.xhtml");
		
		driver.findElement(By.id("formLogin:username")).sendKeys(Config.ADMINISTRATOR_USER_NAME);
		driver.findElement(By.id("formLogin:senha")).sendKeys(Config.ADMINISTRATOR_USER_PASSWORD);
		driver.findElement(By.id("formLogin:botaoLogin")).click();
		
		assertEquals("saulo.silva", driver.findElement(By.xpath("//button[@id='formNotificacaoes:acaoSair_button']/span[2]")).getText());
		
		assertEquals("Principal", driver.findElement(By.xpath("//div[@id='formNotificacaoes:j_idt37']/ul/li[2]/a")).getText());
		
		driver.quit();
	}
	
	/**
	 * Testar login com senha e usuário corretos.
	 * 
	 * <p>
	 *	Teste com operador comum
	 *
	 * @throws Exception caso ocorra falhas ao realizar o teste
	 */
	@Test
	public void testarLoginOk2() throws Exception {
		driver.get("http://localhost:8080/ar-digital/login.xhtml");
		
		driver.findElement(By.id("formLogin:username")).sendKeys(Config.COMMON_USER_NAME);
		driver.findElement(By.id("formLogin:senha")).sendKeys(Config.COMMON_USER_PASSWORD);
		driver.findElement(By.id("formLogin:botaoLogin")).click();
		
		assertEquals("paulo.filho", driver.findElement(By.xpath("//button[@id='formNotificacaoes:acaoSair_button']/span[2]")).getText());
		
		assertEquals("Principal", driver.findElement(By.xpath("//div[@id='formNotificacaoes:j_idt37']/ul/li[2]/a")).getText());
		
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
