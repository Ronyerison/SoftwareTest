package br.ufpi.ardigital.test;

import static org.junit.Assert.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.util.Config;
import br.ufpi.ardigital.util.Default;
import br.ufpi.ardigital.util.Field;

/**
 * Classe que contém os métodos que testam o caso de uso de aprovação e rejeição
 * de envios de documento.
 * 
 */
public class ArDigitalApproveDocumentTest {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Testa a aprovação de um documento que foi enviado.
	 * 
	 * <p>
	 * O método testa a aprovação de um envio de um documento. O documento
	 * enviado possui ofício e anexos, bastando apenas que o operador clique no
	 * botão de aprovação do envio.
	 * 
	 * 
	 * 
	 * @throws Exception
	 *             caso aconteça falha ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 * 
	 * 
	 */
	@Test
	public void aprovarEnvioDocumento() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, true, 2, "MARIA DAS",
				"Documento encaminhado com sucesso!");

		Default.logout(driver);
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		// Aprova o envio do documento
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio")).click();
		Default.waitInterval();
		driver.findElement(By.id("aprovarEnvioDialogForm:botaoAprovarEnvio")).click();

		Default.waitInterval();
		assertEquals("O envio do documento foi aprovado com sucesso",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
		
		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Testa a aprovação do envio de um documento quando o operador não carrega
	 * o ofício.
	 * 
	 * <p>
	 * O documento enviado não apresenta ofício uma mensagem de erro deve ser
	 * exibida ao operador.
	 * 
	 * 
	 * @throws Exception
	 *             caso aconteça falha ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 * 
	 * @see Default#rejeitarEnvioGenerico(WebDriver)
	 */
	@Test
	public void aprovarEnvioDocumentoSemOficio() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, false, 2, "PEDRO",
				"Documento encaminhado com sucesso!");
		Default.logout(driver);

		Default.waitInterval();

		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		// Aceitar o envio do documento
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio")).click();
		Default.waitInterval();
		driver.findElement(By.id("aprovarEnvioDialogForm:botaoAprovarEnvio")).click();
		Default.waitInterval();
		assertEquals("O envio do documento não pode ser aprovado. Um ofício deve ser carregado",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		// Limpa a tabela
		Default.rejeitarEnvioGenerico(driver);
		Default.waitInterval();

		// logout
		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Testa a aprovação do envio de um documento enviado sem ofício.
	 * 
	 * <p>
	 * Um ofício com orientação Paisagem será carregado na aprovação e uma
	 * mensagem de erro deve ser exibida ao operador.
	 * 
	 * 
	 * @throws Exception
	 *             caso aconteça falha ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 * @see Default#rejeitarEnvioGenerico(WebDriver)
	 * 
	 */
	@Test
	public void aprovarEnvioDocumentoComOficioPaisagem() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, false, 2, "PEDRO ",
				"Documento encaminhado com sucesso!");
		Default.logout(driver);
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		// Aceitar o envio do documento
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();

		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio")).click();
		Default.waitInterval();

		// carregar ofício com orientação paisagem
		driver.findElement(By.id("aprovarEnvioDialogForm:upOficio_input")).clear();
		driver.findElement(By.id("aprovarEnvioDialogForm:upOficio_input")).sendKeys(Config.OFICIO_PAISAGEM);

		driver.findElement(By.id("aprovarEnvioDialogForm:botaoAprovarEnvio")).click();
		Default.waitInterval();
		assertEquals("O envio do documento não pode ser aprovado. Não é permitido ofício com orientação Paisagem",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		// Limpa a tabela
		Default.rejeitarEnvioGenerico(driver);
		Default.waitInterval();

		// logout
		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Testa a aprovação do envio de um documento enviado sem ofício.
	 *
	 * <p>
	 * O documento enviado não apresenta o ofício e a quantidade de páginas do
	 * ofício carregado mais a quantidade de páginas de todos os anexos
	 * ultrapassa o limite de páginas definido no Painel de Controle do Ar
	 * Digital.
	 *
	 * @throws Excpetion
	 *             caso aconteça falha ao realizar o teste
	 *
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 * @see Default#rejeitarEnvioGenerico(WebDriver)
	 */
	@Test
	public void aprovarEnvioDocumentoOficioUltrapassLimitePaginas() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, false, 9, "PEDRO ",
				"Documento encaminhado com sucesso!");
		Default.logout(driver);

		// Login com o operador que pode realizar aprovação
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		// Aceitar o envio do documento
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();

		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio")).click();
		Default.waitInterval();

		// carregar ofício com orientação paisagem
		driver.findElement(By.id("aprovarEnvioDialogForm:upOficio_input")).clear();
		driver.findElement(By.id("aprovarEnvioDialogForm:upOficio_input")).sendKeys(Config.OFICIO_DUAS_PAGINAS);

		driver.findElement(By.id("aprovarEnvioDialogForm:botaoAprovarEnvio")).click();
		Default.waitInterval();
		assertEquals(
				"O envio do documento não pode ser aprovado. Você só pode anexar um ofício de no máximo 1 página(s)",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		// Limpa a tabela
		Default.rejeitarEnvioGenerico(driver);
		Default.waitInterval();

		// logout
		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Testa a aprovação do envio de um documento enviado sem ofício.
	 *
	 * <p>
	 * O ofício que será carregado está ok e a aprovação será realizada com
	 * sucesso.
	 *
	 * @throws Exception
	 *             caso aconteça falha ao realizar o teste
	 *
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 * 
	 */
	@Test
	public void aprovarEnvioDocumentoCarregandoOficio() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, false, 5, "MARIA das",
				"Documento encaminhado com sucesso!");
		Default.logout(driver);

		// Login com um operador que pode realizar aprovação de envio
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		// Aceitar o envio do documento
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();

		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio")).click();
		Default.waitInterval();

		// carregar ofício com orientação paisagem
		driver.findElement(By.id("aprovarEnvioDialogForm:upOficio_input")).clear();
		driver.findElement(By.id("aprovarEnvioDialogForm:upOficio_input")).sendKeys(Config.OFICIO_DUAS_PAGINAS);

		driver.findElement(By.id("aprovarEnvioDialogForm:botaoAprovarEnvio")).click();
		Default.waitInterval();
		assertEquals("O envio do documento foi aprovado com sucesso",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		
		Default.logout(driver);

		driver.quit();
	}

	/**
	 * Testa a rejeição de um determinando envio.
	 * 
	 * <p>
	 * O motivo da rejeição não é informado e uma mensagem de erro deve ser
	 * exibida ao operador.
	 * 
	 ** @throws Exception
	 *             caso aconteça falha ao realizar o teste
	 *
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 */
	@Test
	public void rejeitarEnviDocumentoSemInformarMotivo() throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, false, 2, "Maria de",
				"Documento encaminhado com sucesso!");

		Default.logout(driver);
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:rejeitarEnvio")).click();
		Default.waitInterval();

		// Motivo da rejeição não é informado
		driver.findElement(By.id("j_idt160:motivoRejeicaoEnvio")).clear();
		driver.findElement(By.id("j_idt160:motivoRejeicaoEnvio")).sendKeys("");

		// Tenta realizar a rejeição
		driver.findElement(By.id("j_idt160:botaoRejeitarEnvio")).click();
		Default.waitInterval();
		assertEquals("O envio do documento não pode ser rejeitado. Um motivo deve ser informado",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		// Limpa a tabela
		Default.rejeitarEnvioGenerico(driver);
		Default.waitInterval();

		// logout
		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Testa a rejeição de um envio.
	 * 
	 * <p>
	 * O motivo da rejeição é informado e uma mensagem deve ser exibida ao
	 * operador, informando que o documento foi rejeitado com sucesso.
	 */
	@Test
	public void rejeitarEnvioDocumento() throws Exception {		
		Default.login(driver, UserFactory.createCommonUser());
		Default.enviarDocumentoGenericoAprovarEnvio(driver, Field.DECLARATION_TEXT_SEND_DOC, false, 2, "Maria de",
				"Documento encaminhado com sucesso!");

		Default.logout(driver);

		// Login com um operador que pode realizar a rejeição de envio
		Default.login(driver, UserFactory.createAdministratorUser());
		driver.manage().window().maximize();

		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:rejeitarEnvio")).click();
		Default.waitInterval();

		// Informando o motivo da rejeição
		driver.findElement(By.id("j_idt160:motivoRejeicaoEnvio")).clear();
		driver.findElement(By.id("j_idt160:motivoRejeicaoEnvio")).sendKeys("Rejeição Teste");

		driver.findElement(By.id("j_idt160:botaoRejeitarEnvio")).click();
		Default.waitInterval();

		assertEquals("O envio do documento foi rejeitado com sucesso",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
		
		Default.logout(driver);

		driver.quit();
	}

}
