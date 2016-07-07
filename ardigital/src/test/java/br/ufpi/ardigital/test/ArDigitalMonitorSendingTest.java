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

public class ArDigitalMonitorSendingTest {
  private WebDriver driver;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
	  System.setProperty(Constant.ChromeDriverLib,
				Constant.ChromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testEAcompanharEnvio() throws Exception {
	  User validUser = UserFactory.criaUsuarioValido();
		driver.get(Constant.BaseURL + Constant.LoginURL);
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
		driver.findElement(By.id("form:tipo_8")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys(
				"Teste acompanhar documento");
		driver.findElement(By.id("form:j_idt49_next")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys(
				"SAULO DE TÁRSIO");
		Thread.sleep(2000);
		driver.findElement(
				By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li"))
				.click();
		Thread.sleep(2000);
		driver.findElement(By.id("form:j_idt49_next")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("form:upOficio_input")).clear();
		FileAr file = FileArFactory.createValidFile();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(file.getPath());
		driver.findElement(By.id("form:j_idt49_next")).click();
		Thread.sleep(2000);
		driver.findElement(By.id("form:ajax")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
    Thread.sleep(2000);
    assertEquals("documento para teste", driver.findElement(By.xpath("//tbody[@id='formTabelaDocumentos:tabelaAcompanhamento_data']/tr/td[2]")).getText());
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
