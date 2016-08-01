package br.ufpi.ardigital.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.util.Config;
import br.ufpi.ardigital.util.Default;

public class ArDigitalManageDocumentsTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Teste para verificar a busca dos tipos de documentos filtrando pelo nome
	 * 
	 * @throws Exception
	 */
	@Test
	public void filterByNameDocumentTest() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		findType("cobrança", "Cobrança");
	}

	/**
	 * Teste para verificar o funcionamento do botão limpar em gerenciar
	 * documentos
	 * 
	 * @throws Exception
	 */
	@Test
	public void clearFilterByNameTest() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span"))
				.click();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys(
				"cobrança");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoLimpar")).click();
		Default.waitInterval();
		assertTrue(isElementPresent(By
				.xpath("//div[@id='formTabelaTiposDocumentos:tabelaTiposDocumentos_paginator_bottom']/span/span")));
	}

	/**
	 * Teste que cadastra um novo tipo de documento e realiza exclusão do tipo
	 * criado
	 * 
	 * @throws Exception
	 */
	@Test
	public void testArDigitalManageDocuments() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		createAndRemoveType("TESTE FUNCIONAL AR DIGITAL", "Tipo de documento cadastrado com sucesso.", "Tipo de documento excluído com sucesso.");
	}

	@After
	public void tearDown() throws Exception {
		driver.quit();
		String verificationErrorString = verificationErrors.toString();
		if (!"".equals(verificationErrorString)) {
			fail(verificationErrorString);
		}
	}

	private boolean isElementPresent(By by) {
		try {
			driver.findElement(by);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}
	
	/**
	 * @throws InterruptedException
	 */
	private void findType(String description, String returnMsg) throws InterruptedException {
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span"))
				.click();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys(
				description);
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();
		assertEquals(returnMsg,
				driver.findElement(By.cssSelector("span.sorting_1")).getText());
	}
	
	/**
	 * @throws InterruptedException
	 */
	private void createAndRemoveType(String description, String returnMsg1, String returnMsg2) throws InterruptedException {
		driver.findElement(
				By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span"))
				.click();
		Default.waitInterval();
		driver.findElement(
				By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:nomeTipoNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:nomeTipoNovo")).sendKeys(
				description);
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:sistemaOrigemNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:sistemaOrigemNovo")).sendKeys(
				description);
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:prazoArNovo")).sendKeys("10");
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:botaoSalvarNovo")).click();
		Default.waitInterval();
		assertEquals(returnMsg1, driver
				.findElement(By.cssSelector("span.ui-messages-info-summary"))
				.getText());
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys(
				description);
		Default.waitInterval();
		driver.findElement(
				By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoExcluir"))
				.click();
		Default.waitInterval();
		driver.findElement(By.id("formDialogs:botaoInativarTipo")).click();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();
		assertEquals(returnMsg2, driver
				.findElement(By.cssSelector("span.ui-messages-info-summary"))
				.getText());
	}

}
