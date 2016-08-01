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
import br.ufpi.ardigital.util.Default;

public class ArDigitalDownloadDocumentTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	@Test
	public void downloadDocumentInvalidTest() throws Exception {
		downloadWithoutKey(Config.BASE_URL + Config.DOWNLOAD_URL, "A chave de acesso deve ser informada.");
	}

	@Test
	public void downloadDocumentInvalidKeyTest() throws Exception {
		downloadWithoutCaptcha(Config.BASE_URL + Config.DOWNLOAD_URL, "O captcha deve ser processado.");
	}
	
	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	/**
	 * @throws InterruptedException
	 */
	private void downloadWithoutKey(String url, String returnMsg) throws InterruptedException {
		driver.get(url);
		Default.waitInterval();
		driver.findElement(By.id("formPrincipal:j_idt28")).click();
		Default.waitInterval();
		assertEquals(returnMsg, driver
				.findElement(By.cssSelector("span.ui-messages-error-summary"))
				.getText());
	}
	
	/**
	 * @throws InterruptedException
	 */
	private void downloadWithoutCaptcha(String url, String returnMsg) throws InterruptedException {
		driver.get(url);
		Default.waitInterval();
		driver.findElement(By.id("formPrincipal:j_idt26")).sendKeys("Chave Inválida");
		Default.waitInterval();
		driver.findElement(By.id("formPrincipal:j_idt28")).click();
		Default.waitInterval();
		assertEquals(returnMsg, driver
				.findElement(By.cssSelector("span.ui-messages-error-summary"))
				.getText());
	}

}
