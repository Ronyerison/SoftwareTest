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

public class ArDigitalManagePermissionsTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Teste que realiza a consulta dos operadores filtrando pelo nome
	 * 
	 * @throws Exception
	 */
	@Test
	public void filterByOperatorTest() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span"))
				.click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:j_idt51_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:j_idt51_2")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:it_nomeOperador")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:it_nomeOperador")).sendKeys(
				Config.COMMON_USER_NAME);
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:j_idt112")).click();
		Default.waitInterval();
		assertEquals(
				Config.COMMON_USER_NAME,
				driver.findElement(
						By.cssSelector("tr.ui-widget-content.ui-datatable-even > td"))
						.getText());
	}

	/**
	 * Teste que altera a permissão do usuario para envio de documento sem
	 * necessitar de aprovação
	 * 
	 * @throws Exception
	 */
	@Test
	public void testArDigitalManagePermissions() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		changePermissionUser(true);
		Default.waitInterval();
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		Default.sendDocument(driver);
		Default.waitInterval();
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span"))
				.click();
		assertEquals(
				"Envio aprovado",
				driver.findElement(
						By.xpath("//tbody[@id='formTabelaDocumentos:tabelaAcompanhamento_data']/tr/td[4]"))
						.getText());
		Default.waitInterval();
		driver.findElement(By.id("formNotificacaoes:j_idt43_button")).click();
		Default.waitInterval();
		driver.findElement(
				By.xpath("//div[@id='formNotificacaoes:j_idt43_menu']/ul/li/a/span[2]"))
				.click();
		Default.waitInterval();
		Default.login(driver, UserFactory.createAdministratorUser());
		changePermissionUser(false);
	}

	/**
	 * @throws InterruptedException
	 */
	private void changePermissionUser(boolean activePermission) throws InterruptedException {
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span"))
				.click();
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt46:j_idt51']/div[3]"))
				.click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:j_idt51_2")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:it_nomeOperador")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:it_nomeOperador")).sendKeys(
				Config.COMMON_USER_NAME);
		Default.waitInterval();
		driver.findElement(By.id("j_idt46:j_idt112")).click();
		Default.waitInterval();
		driver.findElement(
				By.cssSelector("span.ui-icon.ui-icon-pencil"))
				.click();
		Default.waitInterval();
		driver.findElement(
				By.xpath("//div[@id='j_idt46:tabelaOperadores:0:j_idt144']/div[3]/span"))
				.click();
		Default.waitInterval();
		if(activePermission){
			driver.findElement(By.id("j_idt46:tabelaOperadores:0:j_idt144_2"))
			.click();
		}else{
			driver.findElement(By.id("j_idt46:tabelaOperadores:0:j_idt144_1"))
			.click();
		}
		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-check"))
		.click();
		Default.waitInterval();
		driver.findElement(By.id("formNotificacaoes:j_idt42_button")).click();
		Default.waitInterval();
		driver.findElement(
				By.xpath("//div[@id='formNotificacaoes:j_idt42_menu']/ul/li/a/span[2]"))
				.click();
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
