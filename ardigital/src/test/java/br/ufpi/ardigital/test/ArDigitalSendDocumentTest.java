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

public class ArDigitalSendDocumentTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
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
		Default.login(driver, UserFactory.createCommonUser());
		Default.sendDocument(driver);
	}

	/**
	 * Teste para envio de um documento sem selecionar o interessado.
	 * 
	 * @throws Exception
	 */
	@Test
	public void sendDocumentInvalidTest() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_1")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).sendKeys("Cobrança teste");
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
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
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='form:tipo']/div[3]")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_1")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).sendKeys("Teste cadastro usuario invalido.");
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
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
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).sendKeys("Documento com páginas acima do limite.");
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_1")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys("SAULO DE TÁRSIO");
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:checkboxEnviarAnexos")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
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
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).sendKeys("Documento com páginas acima do limite.");
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_1")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:gestorAutocomplete_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:gestorAutocomplete_input")).sendKeys("SAULO DE TÁRSIO");
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='form:gestorAutocomplete_panel']/ul/li")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:upOficio_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt117_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:ajax")).click();
		Default.waitInterval();
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_NONE);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_NOME);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_TITULO);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_LOGRADOURO);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_NUMERO);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_BAIRRO);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_CEP);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_MUNICIPIO);
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
		Default.registerNewInterested(driver, Default.IGNORE_FIELD_UF);
		assertEquals("É necessário inserir a Unidade Federativa",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
	}
	
	@Test
	public void sendDocumentWithExistingUserTest() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:tipo_1")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:conteudo")).sendKeys("Declaração");
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt49_next")).click();
		Default.waitInterval();
		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		Default.waitInterval();
		driver.findElement(By.id("form:cpf")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:cpf")).sendKeys("777.777.777-79");
		Default.waitInterval();
		driver.findElement(By.id("form:nomeGestor")).click();
		Default.waitInterval();
		assertEquals("CPF de número 77777777779 já existe.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
	}
	
	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com um CPF ja cadastrado
	 * 
	 * @throws Exception
	 */
	@Test
	  public void registerNewInterestedWithExistingCPFTest() throws Exception {
	    Default.login(driver, UserFactory.createCommonUser());
	    Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:tipo_label")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:tipo_1")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:conteudo")).clear();
	    Default.waitInterval();
	    driver.findElement(By.id("form:conteudo")).sendKeys("Teste.");
	    Default.waitInterval();
	    driver.findElement(By.id("form:j_idt49_next")).click();
	    Default.waitInterval();
	    driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:cpf")).clear();
	    Default.waitInterval();
	    driver.findElement(By.id("form:cpf")).sendKeys("111.111.111-11");
	    Default.waitInterval();
	    driver.findElement(By.id("form:unidadeGestora")).click();
	    Default.waitInterval();
	    assertEquals("CPF de número 11111111111 já existe.", driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
	    Default.waitInterval();
//	    assertEquals("111.111.111-11", driver.findElement(By.id("form:cpf")).getText());
//	    Default.waitInterval();
//	    assertEquals("UNIDADE TESTE", driver.findElement(By.id("form:unidadeGestora")).getText());
//	    Default.waitInterval();
//	    assertEquals("JOSÉ DA SILVA", driver.findElement(By.id("form:nomeGestor")).getText());
//	    Default.waitInterval();
//	    assertEquals("PREFEITO", driver.findElement(By.id("form:titulo")).getText());
//	    Default.waitInterval();
//	    assertEquals("RUA JOÃO MARTINIANO", driver.findElement(By.id("form:logradouro")).getText());
//	    Default.waitInterval();
//	    assertEquals("1290", driver.findElement(By.id("form:numero")).getText());
//	    Default.waitInterval();
//	    assertEquals("CEMITÉRIO SÃO VICENTE DE PAULA", driver.findElement(By.id("form:complemento")).getText());
//	    Default.waitInterval();
//	    assertEquals("COLIBRI", driver.findElement(By.id("form:bairro")).getText());
//	    Default.waitInterval();
//	    assertEquals("64.240-000", driver.findElement(By.id("form:cep")).getText());
//	    Default.waitInterval();
//	    assertEquals("PIRACURUCA", driver.findElement(By.id("form:municipio")).getText());
//	    Default.waitInterval();
//	    assertEquals("PI", driver.findElement(By.id("form:uf")).getText());
//	    Default.waitInterval();
	    driver.findElement(By.id("form:j_idt49_next")).click();
	    Default.waitInterval();
	    assertEquals("Upload de Arquivos", driver.findElement(By.xpath("//fieldset[@id='form:j_idt109']/legend")).getText());
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

}
