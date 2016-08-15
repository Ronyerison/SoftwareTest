package br.ufpi.ardigital.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.text.SimpleDateFormat;
import java.util.Date;
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

public class ArDigitalManageDocumentsTest {
	private WebDriver driver;
	private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
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
	 * Teste que realiza o cadastro de um novo tipo de documento. Ao final do
	 * processo, uma mensagem é exibida ao operador.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 */
	@Test
	public void cadastrarNovoTipoDocumento() throws InterruptedException {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String aux = sdf.format(new Date(System.currentTimeMillis()));

		String descricaoTipoDocumento = "DOCUMENTO " + aux;
		String setorOrigem = "SISTEMA ORIGEM " + aux;

		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).sendKeys(descricaoTipoDocumento);

		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).sendKeys(setorOrigem);
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).sendKeys("20");

		// Cadastra documento na base de dados
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:botaoSalvarNovo")).click();
		Default.waitInterval();

		assertEquals("Tipo de documento cadastrado com sucesso.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Método que faz a alteração de um tipo de documento e verifica se o valor
	 * foi modificado.
	 * 
	 * <p>
	 * O tipo de documento alterado é Multa. Muda o prazo e o sistema de origem
	 * desse tipo de documento.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void alterarTipoDocumento() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Gerênciar de tipo de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// realizar a busca pelo documento MULTA
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("multa");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Clica no botão de edicação do elemento buscado
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoEditar")).click();
		Default.waitInterval();

		// Altera o prazo do tipo do documento
		driver.findElement(By.id("formDialogEditarTipoDocumento:prazoAr")).clear();
		driver.findElement(By.id("formDialogEditarTipoDocumento:prazoAr")).sendKeys("100");
		Default.waitInterval();

		// Alterar nome do setor origem
		driver.findElement(By.id("formDialogEditarTipoDocumento:sistemaOrigem")).clear();
		driver.findElement(By.id("formDialogEditarTipoDocumento:sistemaOrigem")).sendKeys("SETOR PROCESSUAL");
		Default.waitInterval();

		// ação que salva as alterações
		driver.findElement(By.id("formDialogEditarTipoDocumento:botaoSalvarAlteracoes")).click();
		Default.waitInterval();

		assertEquals("Tipo de Documento Multa atualizado com sucesso!",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		// Confirma os dados que foram alterado
		confirmarDadosTipoDocumento("multa", "Multa", "SETOR PROCESSUAL", "Não", 100);

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Método que testa a remoção de um novo tipo de um documento cadastrado
	 * 
	 * <p>
	 * O método cadastra um novo tipo de documento e faz a remoção do mesmo. O
	 * tipo do documento não foi utilizado e será removido da base de dados.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void removerTipoDocumento() throws InterruptedException {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String aux = sdf.format(new Date(System.currentTimeMillis()));

		String descricaoTipoDocumento = "DOCUMENTO " + aux;
		String setorOrigem = "SISTEMA ORIGEM " + aux;

		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).sendKeys(descricaoTipoDocumento);

		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).sendKeys(setorOrigem);
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).sendKeys("20");

		// Cadastra documento na base de dados
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:botaoSalvarNovo")).click();
		Default.waitInterval();

		// verificação da mensagem de cadastro
		assertEquals("Tipo de documento cadastrado com sucesso.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		// Logout
		Default.logout(driver);

		// Login
		Default.login(driver, UserFactory.createAdministratorUser());

		// Link de gerência de tipo de documento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// Busca do tipo do documento
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys(descricaoTipoDocumento);
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// realizar a exclusão do tipo de documento
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoExcluir")).click();
		Default.waitInterval();

		// realiza a exclusão
		driver.findElement(By.id("j_idt78:botaoInativarTipo")).click();
		Default.waitInterval();

		// Verificação da mensagem após a operação
		assertEquals("Tipo de documento excluído com sucesso.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		driver.quit();

	}

	/**
	 * Método que testa a remoção de um tipo de documento que já foi utilizado
	 * em envios de documentos.
	 * 
	 * <p>
	 * O método realiza um envio utilizando um tipo de documento e logo após o
	 * envio faz a remoção do tipo de documento utilizado.
	 * 
	 * <p>
	 * O tipo de documento não será removido, pois o mesmo foi utilizado no
	 * envio. Ele será apenas desativado.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see #ativarTipoDocumento()
	 * 
	 */
	@Test
	public void remocaoTipoDocumentoJaUtilizadoEnvios() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createAdministratorUser());

		// realização do envio utilzando o tipo de documento ofício
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_4")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");

		// Ir para o próximo passo =>
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:nomeGestor")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:nomeGestor")).sendKeys("MARIA DAS DORES");
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:titulo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:titulo")).sendKeys("PREFEITA");
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:logradouro")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:logradouro")).sendKeys("RUA JOÃO MARTINIANO");
		Default.waitInterval();

		// Número
		driver.findElement(By.id("formEnviarDocumento:numero")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:numero")).sendKeys("1290");
		Default.waitInterval();

		// Complemento
		driver.findElement(By.id("formEnviarDocumento:complemento")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:complemento"))
				.sendKeys("PRÓXIMO AO CEMITÉRIO SÃO VICENTE DE PAULA");
		Default.waitInterval();

		// Bairro
		driver.findElement(By.id("formEnviarDocumento:bairro")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:bairro")).sendKeys("COLIBRI");
		Default.waitInterval();

		// CEP
		driver.findElement(By.id("formEnviarDocumento:cep")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:cep")).sendKeys("64240000");
		Default.waitInterval();

		// Municipio
		driver.findElement(By.id("formEnviarDocumento:municipio")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:municipio")).sendKeys("PIRACURUCA");
		Default.waitInterval();

		// UF
		driver.findElement(By.id("formEnviarDocumento:uf")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:uf")).sendKeys("PI");
		Default.waitInterval();

		// ir para o próximo passo =>
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		// Carregar ofício
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.FILE_PATH);

		// ir para o próximo passo =>
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		// Enviar
		driver.findElement(By.id("formEnviarDocumento:ajax")).click();
		Default.waitInterval();

		// Fim do envio

		// Gerência de tipo de documento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// Busca do tipo do documento
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("oficio");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// realizar a exclusão do tipo de documento
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoExcluir")).click();
		Default.waitInterval();

		// realiza a exclusão
		driver.findElement(By.id("j_idt78:botaoInativarTipo")).click();
		Default.waitInterval();

		// verificação da mensagem
		assertEquals("O Tipo de documento foi desativado, pois o mesmo possui documentos enviados.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		// Confirmar o status do documento enviado
		confirmarDadosTipoDocumento("Oficio", "Ofício", "AR digital", "Sim", 15);

		//
		Default.logout(driver);
		ativarTipoDocumento();
		Default.logout(driver);
		driver.quit();

	}

	/**
	 * 
	 * Método que testa o cadastro de um novo tipo de documento sem informar uma
	 * descrição para ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 */
	@Test
	public void cadastrarTipoDocumentoSemDescricao() throws InterruptedException {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String aux = sdf.format(new Date(System.currentTimeMillis()));

		String sistemaOrigem = "SISTEMA ORIGEM " + aux;

		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();

		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).sendKeys(sistemaOrigem);
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).sendKeys("20");

		// Cadastra documento na base de dados
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:botaoSalvarNovo")).click();
		Default.waitInterval();

		assertEquals("É necessário informar um nome para o tipo de documento",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * 
	 * Método que testa o cadastro de um novo tipo de documento sem informar um
	 * sistema de origem para ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 */
	@Test
	public void cadastrarTipoDocumentoSemSistemaOrigem() throws InterruptedException {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String aux = sdf.format(new Date(System.currentTimeMillis()));

		String descricaoTipoDocumento = "DOCUMENTO " + aux;

		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).sendKeys(descricaoTipoDocumento);

		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).sendKeys("10");

		// Cadastra documento na base de dados
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:botaoSalvarNovo")).click();
		Default.waitInterval();

		assertEquals("É necessário informar um sistema de origem",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	
	/**
	 * 
	 * Método que testa o cadastro de um novo tipo de documento sem informar um
	 * prazo de recebimento de AR para ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 */
	@Test
	public void cadastrarTipoDocumentoSemPrazoRecebimentoAR() throws InterruptedException {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String aux = sdf.format(new Date(System.currentTimeMillis()));

		String descricaoTipoDocumento = "DOCUMENTO " + aux;
		String sistemaOrigem = "SISTEMA ORIGEM "+aux;
		
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).sendKeys(descricaoTipoDocumento);

		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).sendKeys(sistemaOrigem);
		Default.waitInterval();


		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).sendKeys("");

		// Cadastra documento na base de dados
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:botaoSalvarNovo")).click();
		Default.waitInterval();

		assertEquals("É necessário informar um valor para o prazo para emissão de alertas sobre atrasos nos ARs",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	
	/**
	 * 
	 * Método que testa o cadastro de um novo tipo de documento atribuindo valor
	 * 0 ao prazo de recebimento de AR para o documento.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 */
	@Test
	public void cadastrarTipoDocumentoPrazoARComValorZero() throws InterruptedException {
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyhhmmss");

		String aux = sdf.format(new Date(System.currentTimeMillis()));

		String descricaoTipoDocumento = "DOCUMENTO " + aux;
		String sistemaOrigem = "SISTEMA ORIGEM " + aux;

		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaTiposDocumentos:botaoAdicionarNovo")).click();
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:nomeTipoNovo")).sendKeys(descricaoTipoDocumento);

		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:sistemaOrigemNovo")).sendKeys(sistemaOrigem);
		Default.waitInterval();

		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:prazoArNovo")).sendKeys("0");

		// Cadastra documento na base de dados
		Default.waitInterval();
		driver.findElement(By.id("formDialogAdicionarTipoDocumento:botaoSalvarNovo")).click();
		Default.waitInterval();

		assertEquals(
				"Prazo para a emissão de alertas sobre atrasos nos ARs: Erro de validação: o valor é menor do que o mínimo permitido de \"1\"",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Método que faz a alteração de um tipo de documento.
	 * 
	 * <p>
	 * O método altera o tipo de documento Multa e atribui uma descrição vazia a
	 * ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void alterarTipoDocumentoDescricaoVazia() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Gerênciar de tipo de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// realizar a busca pelo documento MULTA
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("multa");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Clica no botão de edicação do elemento buscado
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoEditar")).click();
		Default.waitInterval();

		// Atribui a descrição vazia ao tipo do documento
		driver.findElement(By.id("formDialogEditarTipoDocumento:nomeTipo")).clear();
		driver.findElement(By.id("formDialogEditarTipoDocumento:nomeTipo")).sendKeys("");
		Default.waitInterval();
		

		// ação que salva as alterações
		driver.findElement(By.id("formDialogEditarTipoDocumento:botaoSalvarAlteracoes")).click();
		Default.waitInterval();

		assertEquals("É necessário informar uma descrição para o tipo de documento",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Método que faz a alteração de um tipo de documento.
	 * 
	 * <p>
	 * O método altera o tipo de documento Multa e atribui um sistema de origem
	 * vazio a ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void alterarTipoDocumentoSistemaOrigemVazio() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Gerênciar de tipo de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// realizar a busca pelo documento MULTA
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("multa");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Clica no botão de edicação do elemento buscado
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoEditar")).click();
		Default.waitInterval();
		
		// Sistema de origem vazio
		driver.findElement(By.id("formDialogEditarTipoDocumento:sistemaOrigem")).clear();
		driver.findElement(By.id("formDialogEditarTipoDocumento:sistemaOrigem")).sendKeys("");
		Default.waitInterval();

		// ação que salva as alterações
		driver.findElement(By.id("formDialogEditarTipoDocumento:botaoSalvarAlteracoes")).click();
		Default.waitInterval();

		assertEquals("É necessário informar um sistema de origem",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Método que faz a alteração de um tipo de documento.
	 * 
	 * <p>
	 * O método altera o tipo de documento Multa e atribui um prazo de
	 * recebimento de AR vazio a ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void alterarTipoDocumentoPrazoRecebimentoARVazio() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Gerênciar de tipo de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// realizar a busca pelo documento MULTA
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("multa");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Clica no botão de edicação do elemento buscado
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoEditar")).click();
		Default.waitInterval();

		// prazo de aviso de AR vazio
		 driver.findElement(By.id("formDialogEditarTipoDocumento:prazoAr")).clear();
		 driver.findElement(By.id("formDialogEditarTipoDocumento:prazoAr")).sendKeys("");
		 Default.waitInterval();


		// ação que salva as alterações
		driver.findElement(By.id("formDialogEditarTipoDocumento:botaoSalvarAlteracoes")).click();
		Default.waitInterval();

		assertEquals("É necessário informar um valor para o prazo para emissão de alertas sobre atrasos nos ARs",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}

	/**
	 * Método que faz a alteração de um tipo de documento.
	 * 
	 * <p>
	 * O método altera o tipo de documento Multa e atribui um prazo de
	 * recebimento de AR com valor 0 a ele.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void alterarTipoDocumentoPrazoRecebimentoARValorZero() throws InterruptedException {
		// Login
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Gerênciar de tipo de documentos
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		// realizar a busca pelo documento MULTA
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("multa");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Clica no botão de edicação do elemento buscado
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoEditar")).click();
		Default.waitInterval();

		// prazo de aviso de AR vazio
		 driver.findElement(By.id("formDialogEditarTipoDocumento:prazoAr")).clear();
		 driver.findElement(By.id("formDialogEditarTipoDocumento:prazoAr")).sendKeys("0");
		 Default.waitInterval();


		// ação que salva as alterações
		driver.findElement(By.id("formDialogEditarTipoDocumento:botaoSalvarAlteracoes")).click();
		Default.waitInterval();

		assertEquals("Prazo para a emissão de alertas sobre atrasos nos ARs: Erro de validação: o valor é menor do que o mínimo permitido de \"1\"",
				driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());

		Default.logout(driver);
		driver.quit();
	}
	
	
	/**
	 * Método utilizado para ativar o tipo de documento desativado pelo teste
	 * que faz a remoção de um documento utilizado em envios.
	 * 
	 * <p>
	 * A execução desse método é necessária para não causar problemas na
	 * execução do método que faz o teste de remoção de tipos de documento que
	 * já foram utilizados em envios, pois o mesmo realiza um envio e utiliza o
	 * tipo de documento que foi desativado. Se o tipo de documento não tem
	 * status ativo, um erro de teste ocorrerá.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 */
	public void ativarTipoDocumento() throws InterruptedException {
		// login
		Default.login(driver, UserFactory.createAdministratorUser());
		// Mudar o status do documento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[10]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys("oficio");
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Editar tipo o documento
		// Clica no botão de edicação do elemento buscado
		driver.findElement(By.id("formTabelaTiposDocumentos:tabelaTiposDocumentos:0:botaoEditar")).click();
		Default.waitInterval();

		// Muda o status inativo para "Não"
		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		Default.waitInterval();

		// Salvando as alterações
		driver.findElement(By.id("formDialogEditarTipoDocumento:botaoSalvarAlteracoes")).click();
	}

	/**
	 * Método que faz a verificação de algumas informaçãoe de um determinado
	 * tipo de documento.
	 * 
	 * @param termoBusca
	 *            o termo digitado para a busca do tipo
	 * @param tipo
	 *            a descrição do tipo do documento
	 * @param sistemaOrigem
	 *            o sistema origem do tipo de documento
	 * @param tipoInativo
	 *            o campo que indica se o tipo de documento está ativo ou não
	 * @param quantDias
	 *            quantidade de dias de prazo para recebimento de AR para o tipo
	 *            de documento
	 * 
	 * @throws InterruptedException
	 *             caso ocorra alguma falha ao realizar o teste
	 */
	private void confirmarDadosTipoDocumento(String termoBusca, String tipo, String sistemaOrigem, String tipoInativo,
			int quantDias) throws InterruptedException {
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:tipoParaPesquisa")).sendKeys(tipo);
		Default.waitInterval();
		driver.findElement(By.id("formPesquisar:botaoPesquisar")).click();
		Default.waitInterval();

		// Nome
		assertEquals(tipo,
				driver.findElement(By
						.xpath("//tbody[@id='formTabelaTiposDocumentos:tabelaTiposDocumentos_data']/tr[1]/td[1]/span"))
						.getText());

		// Sistema origem
		assertEquals(sistemaOrigem,
				driver.findElement(
						By.xpath("//tbody['formTabelaTiposDocumentos:tabelaTiposDocumentos_data']/tr[1]/td[2]/span"))
						.getText());

		// Tipo inativo
		assertEquals(tipoInativo,
				driver.findElement(
						By.xpath("//tbody['formTabelaTiposDocumentos:tabelaTiposDocumentos_data']/tr[1]/td[3]/span"))
						.getText());
		// Prazo
		assertEquals(quantDias,
				Integer.parseInt(driver
						.findElement(By
								.xpath("//tbody['formTabelaTiposDocumentos:tabelaTiposDocumentos_data']/tr[1]/td[4]/span"))
						.getText()));
	}

}
