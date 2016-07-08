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

import br.ufpi.ardigital.factory.FileArFactory;
import br.ufpi.ardigital.model.FileAr;
import br.ufpi.ardigital.util.Constant;
import br.ufpi.ardigital.util.Default;

public class ArDigitalSendDocumentTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Constant.ChromeDriverLib, Constant.ChromeDriverPath);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Teste para envio de um documento válido //TODO: NESSE CASO DE TESTE É
	 * NECESSÁRIO ALTERAR O DIRETORIO DO ARQUIVO NA CLASSE FileArFactory;
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendDocumentValidTest() throws Exception {
		Default.login(driver);
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		driver.findElement(By.id("form:tipo_label")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys("Teste documento válido");
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys("SAULO DE TÁRSIO");
		waitForLoadPage();
		driver.findElement(By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:upOficio_input")).clear();
		FileAr file = FileArFactory.createValidFile();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(file.getPath());
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:ajax")).click();
		assertEquals("Documento encaminhado com sucesso!",
				driver.findElement(By.cssSelector("div.ui-grid-col-12")).getText());
	}

	/**
	 * Teste para envio de um documento sem selecionar o interessado.
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendDocumentInvalidTest() throws Exception {
		Default.login(driver);
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		driver.findElement(By.id("form:tipo_label")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys("Cobrança teste");
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		assertEquals("É necessário informar um Interessado",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * destinatario com campos em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendDocumentInvalidReceiverRegisterTest() throws Exception {
		Default.login(driver);
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		driver.findElement(By.xpath("//div[@id='form:tipo']/div[3]")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys("Teste cadastro usuario invalido.");
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		assertEquals("É necessário inserir o bairro do endereço",
				driver.findElement(By.xpath("//div[@id='form:camposInteressadoMessage']/div/ul/li[5]/span")).getText());
	}

	/**
	 * Teste para envio de um documento enviando os anexos para o e-carta com
	 * numero de páginas acima do limite
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendDocumentInvalidPagesLimitTest() throws Exception {
		Default.login(driver);
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys("Documento com páginas acima do limite.");
		driver.findElement(By.id("form:tipo_label")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys("SAULO DE TÁRSIO");
		waitForLoadPage();
		driver.findElement(By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:upOficio_input")).clear();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		waitForLoadPage();
		driver.findElement(By.id("form:checkboxEnviarAnexos")).click();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		assertTrue(isElementPresent(By.cssSelector("span.ui-messages-error-summary")));
	}

	/**
	 * Teste para envio de um documento enviando sem os anexos para o e-carta
	 * com numero de páginas acima do limite
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendDocumentValidPagesLimitTest() throws Exception {
		Default.login(driver);
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		driver.findElement(By.id("form:conteudo")).clear();
		driver.findElement(By.id("form:conteudo")).sendKeys("Documento com páginas acima do limite.");
		driver.findElement(By.id("form:tipo_label")).click();
		driver.findElement(By.id("form:tipo_1")).click();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys("SAULO DE TÁRSIO");
		waitForLoadPage();
		driver.findElement(By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:upOficio_input")).clear();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt117_input")).clear();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Constant.FilePath);
		driver.findElement(By.id("form:j_idt49_next")).click();
		waitForLoadPage();
		driver.findElement(By.id("form:ajax")).click();
		assertEquals("Documento encaminhado com sucesso!",
				driver.findElement(By.cssSelector("div.ui-grid-col-12")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado valido
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedValidTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_NONE);
		assertEquals("Upload de Arquivos",
				driver.findElement(By.xpath("//fieldset[@id='form:j_idt109']/legend")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo nome do interessado em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutNameTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_NOME);
		assertEquals("É necessário inserir um nome",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo titulo em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutTitleTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_TITULO);
		assertEquals("É necessário inserir um titulo",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo logradouro em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutStreetTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_LOGRADOURO);
		assertEquals("É necessário inserir o logradouro do endereço",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo numero do endereco em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutAddressNumberTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_NUMERO);
		assertEquals("É necessário inserir o número do endereço",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo bairro em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutDistrictTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_BAIRRO);
		assertEquals("É necessário inserir o bairro do endereço",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo CEP em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutPostalCodeTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_CEP);
		assertEquals("É necessário inserir o número do CEP",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo municipio em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutCityTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_MUNICIPIO);
		assertEquals("É necessário inserir o Município do endereço",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com o campo UF em branco
	 * 
	 * @throws Exception
	 */
	@Test
	public void registerNewInterestedWithoutFederativeUnitTest() throws Exception {
		Default.registerNewInterested(driver, Constant.IGNORE_FIELD_UF);
		assertEquals("É necessário inserir a Unidade Federativa",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
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

	private void waitForLoadPage() throws InterruptedException {
		Thread.sleep(1000);
	}

}
