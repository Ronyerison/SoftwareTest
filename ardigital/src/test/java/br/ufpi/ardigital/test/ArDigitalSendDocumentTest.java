package br.ufpi.ardigital.test;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.util.Config;
import br.ufpi.ardigital.util.Default;

/**
 * Classe que contém os métodos que testam o caso de uso de envio de documentos.
 * 
 * 
 * @author Saulo de Társio
 *
 */
public class ArDigitalSendDocumentTest {
	private WebDriver driver;

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Teste de envio de documento sem informar o tipo de documento
	 * 
	 * @throws Exception
	 *             caso aconteça falhas ao realizar o teste
	 */
	@Test
	public void realizarEnvioSemTipoDocumento() throws Exception {
		// Login com usuário comum
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Clicar no link para envio de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		// informar a declaração de contéudoo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");

		// Ir para o próximo passo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		assertEquals("É necessário informar um tipo de documento",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 * Teste de envio de documento sem informar a declaração de conteúdo
	 *
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioSemDeclaracaoConteudo() throws Exception {
		// Login
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// clica no link para envio de documento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		// Informa o tipo de documento
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		// Ir para o próximo passo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		assertEquals("É necessário informar uma declaração de conteúdo",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * Nesse caso, o método tenta realizar um envio de documento sem informar o
	 * nome do interessado.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemNome() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_NOME);
		assertEquals("É necessário inserir um nome",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * Testa o envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O título do interessado não é informado no momento de definir o
	 * interessado.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemTitulo() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_TITULO);
		assertEquals("É necessário inserir um titulo",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessado. O logradouro do novo interessado não é informado.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemLogradouro() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_LOGRADOURO);
		assertEquals("É necessário inserir um logradouro",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessado. O logradouro do novo interessado não é informado.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemNumero() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_NUMERO);
		assertEquals("É necessário inserir um número de endereço",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessado. O CEP do novo interessado não é informado.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemCep() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_CEP);
		assertEquals("É necessário inserir um CEP",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessado sem município.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemMunicipio() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_MUNICIPIO);
		assertEquals("É necessário inserir um município",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessado sem unidade federativa
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoSemUF() throws Exception {
		Default.registrarNovoInteressado(driver, Default.IGNORE_FIELD_UF);
		assertEquals("É necessário inserir uma unidade federativa",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessado informando um valor errado para a unidade federativa. Nesse
	 * caso, o valor definido será maior que o permitido para a UF.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoUFMaiorPermitido() throws Exception {
		Default.registrarNovoInteressadoUFInvalida(driver, Default.UF_INVALIDA_MAIOR_PERMITIDO);
		assertEquals("UF: Erro de validação: o comprimento é maior do que o máximo permitido de \"2\"",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento informando um novo interessado
	 * 
	 * <p>
	 * O método tenta realizar um envio de um documento informando um novo
	 * interessad,o informando um valor errado para a unidade federativa. Nesse
	 * caso, o valor definido será menor que o permitido para a UF.
	 * 
	 * @see Default#registrarNovoInteressado(WebDriver, int)
	 * 
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioRegistrarNovoInteressadoUFMenorPermitido() throws Exception {
		Default.registrarNovoInteressadoUFInvalida(driver, Default.UF_INVALIDA_MENOR_PERMITIDO);
		assertEquals("UF: Erro de validação: o comprimento é menor do que o mínimo permitido de \"2\"",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 * 
	 * Teste de envio de documento sem informar interessado do envio no campo de
	 * busca.
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see UserFactory#createCommonUser()
	 * 
	 * @throws InterruptedException
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioSemBuscarInteressado() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		// Informar tipo de documento
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		// Informar a declaração de conteúdo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");
		Default.waitInterval();

		// ir para o próximo passo
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		// ir para o próximo passo
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		assertEquals("É necessário informar um interessado",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 * Teste para envio de um documento inserindo um novo cadastro de
	 * interessado com um CPF já cadastrado na base de dados do Ar Digital
	 *
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see UserFactory#createCommonUser()
	 *
	 * @throws Exception
	 *             caso ocorra alguma falha no teste
	 */
	@Test
	public void realizarEnvioInteressadoCpfExistente() throws Exception {
		// Login
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Link de envio de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();

		// Tipo do documento
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_1")).click();
		// Declaração de conteúdo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração de conteúdo");
		Default.waitInterval();

		// ir para o step interessado
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		// Informar novo interessado
		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();

		// Informar o cpf do interessado
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:cpf")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:cpf")).sendKeys("07321910334");
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:unidadeGestora")).click();

		Default.waitInterval();

		// Mensagem que aparece ao operador ao terminar de digitar o cpf
		// existente
		assertEquals("CPF de número 07321910334 já existe",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
		Default.waitInterval();

		// ir para a aba de uploads
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals("Upload de Arquivos",
				driver.findElement(By.xpath("//div[@id='formEnviarDocumento:tabArquivos']/fieldset/legend")).getText());

		driver.quit();
	}

	// Parte dos arquivos
	/**
	 *
	 * Teste de envio de documento sem carregar ofício e anexos.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 *
	 * @throws InterruptedException
	 *             caso ocorra alguma falha no teste
	 *
	 */
	@Test
	public void realizarEnvioSemOficioEAnexos() throws InterruptedException {
		// Realizar envio de um documento
		Default.enviarDocumentoGenericoParaArquivos(driver, false);
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		assertEquals("É necessário fazer o upload de pelo menos um anexo ou ofício",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		driver.quit();
	}

	/**
	 *
	 * Teste de envio de documento quando um operador comum carrega apenas os
	 * anexos e não marca a opção de envio de anexos ao E-Carta.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 *
	 * @throws InterruptedException
	 *             caso ocorra alguma falha no teste
	 *
	 *
	 */
	@Test
	public void realizarEnvioNaoMarcarOpcaoEnvioAnexos() throws InterruptedException {
		Default.enviarDocumentoGenericoParaArquivos(driver, false);
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals("A opção para enviar anexos deve ser marcada",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 *
	 * Teste de envio de documento quando o operador carrega anexos e estes
	 * atingem ou ultrapassa o limite de páginas definido no Painel de Controle
	 * do AR Digital. É necessário deixar um espaço para carregar um ofício de
	 * pelo menos uma página.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 *
	 * @throws InterruptedException
	 *             caso ocorra alguma falha no ao realizar o teste
	 */
	@Test
	public void realizarEnvioNaoDeixarEspacoOficio() throws InterruptedException {
		Default.enviarDocumentoGenericoParaArquivos(driver, false);
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();

		for (int i = 0; i < 10; i++) {
			driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
			Default.waitInterval();
		}
		driver.findElement(By.id("formEnviarDocumento:checkboxEnviarAnexos")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals(
				"Você ultrapassou o limite de páginas definido no Painel de Controle apenas com anexos. É necessário deixar espaço para carregar o ofício",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 *
	 * Teste de envio de documento quando o operador ultrapassa o limite de
	 * páginas definido no Painel de Controle do AR Digital.
	 *
	 * <p>
	 * Esse envio contém 11 páginas (10 páginas dos anexos + 1 página do
	 * ofício), sendo que o limite de páginas para este teste é de 10 de
	 * páginas.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 * @throws InterruptedException
	 *             caso ocorra alguma falha ao realizar o teste
	 *
	 */
	@Test
	public void realizarEnvioUltrapassaLimitePaginas() throws InterruptedException {
		Default.enviarDocumentoGenericoParaArquivos(driver, false);
		Default.waitInterval();

		// Ofício
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.FILE_PATH);

		// Anexos
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();

		for (int i = 0; i < 10; i++) {
			driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
			Default.waitInterval();
		}

		driver.findElement(By.id("formEnviarDocumento:checkboxEnviarAnexos")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals(
				"Você ultrapassou o limite de páginas configurado no Painel de Controle. O documento possui 11 páginas. O limite de páginas é 10",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 * Teste de envio de documento com um ofício com orientação em modo
	 * paisagem.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 *
	 * @throws InterruptedException
	 *             caso ocorra alguma falha ao realizar o teste
	 */
	@Test
	public void realizarEnvioOficioOrientacaoPaisagem() throws InterruptedException {
		Default.enviarDocumentoGenericoParaArquivos(driver, false);
		Default.waitInterval();

		// Ofício
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.OFICIO_PAISAGEM);

		// Anexos
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();

		for (int i = 0; i < 5; i++) {
			driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
			Default.waitInterval();
		}
		driver.findElement(By.id("formEnviarDocumento:checkboxEnviarAnexos")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals("O ofício carregado tem orientação Paisagem. Carregue um ofício com orientação Retrato",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 * Teste de envio de documento com um dos anexos com orientação em modo
	 * paisagem.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 *
	 * @throws InterruptedException
	 *             caso ocorra alguma falha ao realizar o teste
	 */
	@Test
	public void realizarEnvioAnexoOrientacaoPaisagem() throws InterruptedException {
		Default.enviarDocumentoGenericoParaArquivos(driver, false);
		Default.waitInterval();

		// Ofício
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.FILE_PATH);

		// Anexos 1 - ok
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();

		// Anexo 2 - orientacao em modo paisagem
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.ANEXO_PAISAGEM);
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:checkboxEnviarAnexos")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals(
				"Há um anexo com orientação em modo Paisagem. Não é permitido enviar anexo com esse tipo de orientação",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
		
		driver.quit();
	}

	/**
	 * Teste de envio de documento quando o operador pertence ao
	 * "SETOR PROCESSUAL" ou a um setor que possui o "SETOR PROCESSUAL" como
	 * lotação superior e o operador não carrega o ofício no momento do envio.
	 *
	 * @see Default#enviarDocumentoGenericoParaArquivos(WebDriver, boolean)
	 *
	 * @throws InterruptedException
	 *             caso ocorra alguma falha ao realizar o teste
	 */
	@Test
	public void realizarEnvioSemOficioUserAdm() throws InterruptedException {
		Default.enviarDocumentoGenericoParaArquivos(driver, true);
		Default.waitInterval();

		// Anexos 1 - ok
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:checkboxEnviarAnexos")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		assertEquals("Um ofício dever ser anexado ao envio",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		driver.quit();
	}

	/**
	 * Método que faz o envio de um documento e compara as informações do
	 * documento enviado na tela de acompanhamento de envios. O interessado é
	 * selecionado da base de dados do TCE.
	 *
	 * @throws InterruptedException
	 *             caso aconteça alguma falha ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#sendDocument(WebDriver, String, String, String)
	 */
	@Test
	public void realizarEnvioConfirmarDadosTelaAcompanhamento() throws InterruptedException {
		// Realiza o login
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Faz o envio do documento
		Default.sendDocument(driver, "Declaração de conteúdo teste", "Maria das", "Documento encaminhado com sucesso!");
		Default.waitInterval();

		// Maximizar janela
		driver.manage().window().maximize();

		// tela de acompanhamento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		// detalhes do documento
		driver.findElement(By.id("formTabelaDocumentos:tabelaAcompanhamento:0:detalheDocumentoEnvio")).click();
		Default.waitInterval();

		// Confirmar os dados do documento enviado
		Default.verificarDadosDocumento(driver, "paulo.filho", "SETOR NÃO DEFINIDO", "SETOR NÃO DEFINIDO",
				"Notificação", "Declaração de conteúdo teste", "Envio solicitado");

		Default.verificarDadosInteressado(driver, "MARIA DAS GRAÇAS ERNESTO COSTA MARTINS", "DIRETOR(A)",
				"HOSP. AREOLINO DE ABREU / TERESINA", "64049440", "AV. LINDOLFO MONTEIRO", "2212", "", "JOCKEY CLUB",
				"Teresina", "PI");

		// Remover o envio da tabela para não causar problemas para o teste de
		// aprovação de envios.
		
		//realiza logout
		Default.logout(driver);
		
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[13]/a/span")).click();
		Default.rejeitarEnvioGenerico(driver);
		
		Default.logout(driver);
		
		driver.quit();

	}

	/**
	 * Método que realiza um envio de um documento para um interessado que será
	 * cadastrado no processo de envio.
	 *
	 *
	 */
	@Test
	public void realizarEnvioNovoInteressadoVerificarEnvio() throws InterruptedException {
		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String nomeInteressado = "NOVO INTERESSADO " + sdf.format(new Date(System.currentTimeMillis()));
		
		// Realiza o login
		Default.login(driver, UserFactory.createAdministratorUser());

		// Eviar documento
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		// Step one
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");

		// Ir para o próximo passo =>
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		// NOVO INTERESSADO
		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		Default.waitInterval();

		// Nome do interessado
		driver.findElement(By.id("formEnviarDocumento:nomeGestor")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:nomeGestor")).sendKeys(nomeInteressado);
		Default.waitInterval();

		// Titulo
		driver.findElement(By.id("formEnviarDocumento:titulo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:titulo")).sendKeys("DIRETOR");
		Default.waitInterval();

		// LOGRADOURO
		driver.findElement(By.id("formEnviarDocumento:logradouro")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:logradouro")).sendKeys("RUA TESTE");
		Default.waitInterval();

		// NUMERO
		driver.findElement(By.id("formEnviarDocumento:numero")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:numero")).sendKeys("1111");
		Default.waitInterval();

		// bairro
		driver.findElement(By.id("formEnviarDocumento:bairro")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:bairro")).sendKeys("BAIRRO TESTE");
		Default.waitInterval();

		// CEP
		driver.findElement(By.id("formEnviarDocumento:cep")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:cep")).sendKeys("64000000");
		Default.waitInterval();

		// municipio
		driver.findElement(By.id("formEnviarDocumento:municipio")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:municipio")).sendKeys("MUNICIPIO TESTE");
		Default.waitInterval();

		// UF
		driver.findElement(By.id("formEnviarDocumento:uf")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:uf")).sendKeys("PI");

		// Próximo step
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		// carrega ofício
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();

		// próximo step =>
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		// enviar o documento
		driver.findElement(By.id("formEnviarDocumento:ajax")).click();
		Default.waitInterval();

		
		// Maximizar janela
		driver.manage().window().maximize();
		
		// detalhes do envio
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		driver.findElement(By.id("formTabelaDocumentos:tabelaAcompanhamento:0:detalheDocumentoEnvio")).click();
		Default.waitInterval();

		// Condirmar dados do documento
		Default.verificarDadosDocumento(driver, "saulo.silva", "DP - Divisão de Protocolo e Comunicação Processual",
				"DP - Divisão de Protocolo e Comunicação Processual", "Notificação", "Declaração teste", "Envio aprovado");

		// confirmar dados destinatario do envio
		Default.verificarDadosInteressado(driver, nomeInteressado, "DIRETOR", "", "64000000", "RUA TESTE", "1111", "", "BAIRRO TESTE",
				"MUNICIPIO TESTE", "PI");

		driver.quit();
	}

}
