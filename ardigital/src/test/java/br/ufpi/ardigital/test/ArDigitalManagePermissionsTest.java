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

public class ArDigitalManagePermissionsTest {
	private WebDriver driver;
	// private StringBuffer verificationErrors = new StringBuffer();

	@Before
	public void setUp() throws Exception {
		System.setProperty(Config.CHROME_DRIVER_LIB, Config.CHROME_DRIVER_PATH);
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
	}

	/**
	 * Teste que realiza a consulta dos operadores filtrando pelo usuário do
	 * operador
	 * 
	 * @throws Exception
	 *             caso ocorra falhas ao realizar o teste
	 */
	@Test
	public void filterByOperatorTest() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		findOperator(Config.COMMON_USER_NAME);
		Default.logout(driver);
		driver.quit();
	}

	/**
	 * @throws InterruptedException
	 */
	private void findOperator(String userName) throws InterruptedException {
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt50_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt50_2")).click();
		Default.waitInterval();

		// Informar o usuário do operador
		driver.findElement(By.id("j_idt45:it_nomeOperador")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:it_nomeOperador")).sendKeys(userName);

		// Clicar no botão de busca
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt105")).click();

		Default.waitInterval();
		assertEquals(userName,
				driver.findElement(By.xpath("//tbody[@id='j_idt45:tabelaOperadores_data']/tr[1]/td[1]")).getText());
	}

	/**
	 * Teste que realiza busca por nome
	 * 
	 * @throws Exception
	 */
	@Test
	public void findSectorByNameTest() throws Exception {
		Default.login(driver, UserFactory.createAdministratorUser());
		findSectorByName("DIRETORIA", "DIRETORIA PROCESSUAL");
		Default.logout(driver);

		driver.quit();
	}

	private void findSectorByName(String name, String returnName) throws InterruptedException {
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:autocompleteSetores_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:autocompleteSetores_input")).sendKeys(name);
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt45:autocompleteSetores_panel']/ul/li")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt57")).click();
		Default.waitInterval();
		assertEquals(returnName,
				driver.findElement(By.xpath("//tbody[@id='j_idt45:tabelaSetores_data']/tr[1]/td[1]")).getText());
	}

	/**
	 * Teste que altera a permissão de um usuario comum para não necessitar de
	 * aprovação o envio de documentos.
	 * 
	 * <p>
	 * Após a aprovação, o operador que teve a permissão modificada realizará um
	 * envio de um documento.
	 * 
	 * <p>
	 * O método verifica se o documento enviado está com status Envio Aprovado.
	 * 
	 * <p>
	 * Logo após essas operações, a permissão do operador é atualizada para o
	 * valor que estava definida antes, finalizando o teste.
	 * 
	 * 
	 * @throws Exception
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see #realizarEnvioSemNecessidaPermissao()
	 * @see #changePermissionUser(boolean, int)
	 */
	@Test
	public void alterarPermissaoEnvioSemNecessidadeAprovacao() throws Exception {
		// lodin operador master
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Trocar permissões - enviar documento diretamente, sem precisão de
		// análise no envio
		changePermissionUser(true, Config.ALTERA_PERMISSAO_ENVIO_SEM_NECESSIDADE_APROVACAO);
		Default.waitInterval();

		// Envio
		realizarEnvioSemNecessidaPermissao();

		// Voltar permissões originais do usuário
		Default.login(driver, UserFactory.createAdministratorUser());
		changePermissionUser(false, Config.ALTERA_PERMISSAO_ENVIO_SEM_NECESSIDADE_APROVACAO);
		
		driver.quit();
	}

	/**
	 * Método que altera a permissão de aprovação/rejeição de envios de
	 * documentos de um determinado operador.
	 * 
	 * <p>
	 * O método seleciona um operador e faz a alteração da permissão. Logo em
	 * seguida, testa a permissão que foi modificada, aprovando o envio de um
	 * documento realizado por um outro operador.
	 * 
	 * <p>
	 * Logo após as operações, a permissão é alterada para o valor que estava
	 * definida antes da alteração, finalizando o teste.
	 * 
	 * @throws Exception
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see #changePermissionUser(boolean, int)
	 * @see #aprovarEnvioDocumento()
	 */
	@Test
	public void alterarPermissaoPodeAprovarEnvio() throws Exception {
		// Login com operador master
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Trocar permissões - aprovar/rejeitar envios
		changePermissionUser(true, Config.ALTERA_PERMISSAO_PODE_APROVAR_ENVIOS);
		Default.waitInterval();

		// verificar aprovação de envio
		aprovarEnvioDocumento();

		// Voltar permissões originais do usuário
		Default.login(driver, UserFactory.createAdministratorUser());
		changePermissionUser(false, Config.ALTERA_PERMISSAO_PODE_APROVAR_ENVIOS);

		driver.quit();

	}

	/**
	 * Método que altera a permissão de alteração de permissões de setores e
	 * operadores.
	 * 
	 * <p>
	 * O teste consiste em ceder a permissão de alterar as permissões de setores
	 * e operadores a um determinado operador. O operador que foi alterado fará
	 * a mudança na permsissão de um setor e de um operador. Verifica a mensagem
	 * que é exibida ao se atualizar um operador e um setor:
	 * "O operador foi atualizado com sucesso" ou
	 * "O setor foi atualizado com sucesso"
	 * 
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see #changePermissionUser(boolean, int)
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * 
	 */
	@Test
	public void alterarPermissaoAlteraPermissaoSetorOperador() throws InterruptedException {
		// Login com um operador com permissão de alterar permissão de outros
		// setore
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Trocar permissões - aprovar e enviar documento diretamente
		changePermissionUser(true, Config.ALTERA_PERMISSAO_AJUSTAR_PERMISSOES);
		Default.waitInterval();

		// alterar permissao de envio de um operador
		ajustarPermissaoOperador(true);
		ajustarPermissaoOperador(false);

		// altera a permissao de um setor
		ajustarPermissaoSetor(true);
		ajustarPermissaoSetor(false);

		// Voltar permissões originais do usuário
		Default.login(driver, UserFactory.createAdministratorUser());
		changePermissionUser(false, Config.ALTERA_PERMISSAO_AJUSTAR_PERMISSOES);

		driver.quit();
	}

	/**
	 * Esse método testa a alteração da permissão de atualização de alguns
	 * campos do Painel de Controle de um operador.
	 * 
	 * <p>
	 * Esse método altera a permisssão do operador que faz a alteração de alguns
	 * valores do painel de controle. O operador que teve a permissão modificada
	 * irá altererar alguns campos do Painel de Controle. Em seguida um outro
	 * operador vai logar no sistema e verificar se os campos no Painel de
	 * Controle foram alterados.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o envio
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see #changePermissionUser(boolean, int)
	 * 
	 */
	@Test
	public void alterarPermissaoAjusteParametrosPainelControle() throws InterruptedException {
		// Login com um operador com permissão de alterar a permissão de outros
		// operadores
		// ou setores
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Troca a permissão de um usuário - ativa a permissao
		changePermissionUser(true, Config.ALTERA_PERMISSAO_AJUSTE_PARAMETROS_PAINEL_CONTROLE);

		// Ajusta o painel de controle
		ajustarPainelControle();

		// Loga com o operador master
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Desativa a permissão
		changePermissionUser(false, Config.ALTERA_PERMISSAO_AJUSTE_PARAMETROS_PAINEL_CONTROLE);

		driver.quit();

	}

	/**
	 * Método que realiza o teste de alteração da permissão que faz com que o
	 * operador atualize os valores dos parâmetros de comunicação com o E-Carta.
	 * 
	 * <p>
	 * O método altera a permissão de um operador e este vai até o Painel de
	 * Controle e faz a alteração dos parãmetros.
	 * 
	 * <p>
	 * Em seguida, a permissão do usuário é alterada para o valor original.
	 *
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste.
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see #changePermissionUser(boolean, int)
	 */
	@Test
	public void alterarPermissaoAjusteParametrosComunicacaoECarta() throws InterruptedException {

		// Login com o usuário qu teve a permissão alterada
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Troca a permissão de um usuário - ativa a permissao
		changePermissionUser(true, Config.ALTERA_PERMISSAO_AJUSTE_PARAMETROS_FLUXO_ECARTA);

		// Ajusta o painel de controle
		ajustarValoresComunicacaoECarta();

		// Loga com o operador master
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		changePermissionUser(false, Config.ALTERA_PERMISSAO_AJUSTE_PARAMETROS_FLUXO_ECARTA);

		driver.quit();

	}

	/**
	 * Método que altera a permissão que faz com que um operador visualize todos
	 * os envios realizados.
	 * 
	 * <p>
	 * O teste consiste em modificar a permissão e ir até a tela de
	 * acompanhamento de envios, buscando um envio de outro setor ou operador.
	 * Logo após, o método altera o valor da permissão do operador para o valor
	 * definido anteriormente.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar envio
	 * 
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see #changePermissionUser(boolean, int)
	 */
	@Test
	public void alterarPermissaoVisualizarTodosDocumentos() throws InterruptedException {
		// setore
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();

		// Trocar permissões - aprovar e enviar documento diretamente
		changePermissionUser(true, Config.ALTERA_PERMISSAO_VISUALIZAR_TODOS_DOCUMENTOS);
		
		//Acompanhar envio de outros seto e operador
		visualizarEnviosOutroSetorOperador();
		
		// Loga com o operador master
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		changePermissionUser(false, Config.ALTERA_PERMISSAO_VISUALIZAR_TODOS_DOCUMENTOS);

		driver.quit();

	}

	
	private void visualizarEnviosOutroSetorOperador() throws InterruptedException{
		// Login com o usuário qu teve a permissão alterada
		Default.login(driver, UserFactory.createAdministratorUser());
		Default.waitInterval();
		
		//realizar envio com um operador do setor processual
		//O operador que visualizará pertence a outro setor
		Default.sendDocument(driver, "Documento pertecente ao setor processual", "maria das", "Documento encaminhado com sucesso!");
		
		//Logout
		Default.logout(driver);
		
		//Login com um operador que não pertence a o setor processual
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		
		driver.manage().window().maximize();
		
		// ir para a página de acomphamento de envio
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();
		Default.waitInterval();
		
		driver.findElement(By.id("formTabelaDocumentos:tabelaAcompanhamento:0:detalheDocumentoEnvio")).click();
		Default.waitInterval();
		Default.verificarDadosDocumento(driver, "saulo.silva", "DP - Divisão de Protocolo e Comunicação Processual", "DP - Divisão de Protocolo e Comunicação Processual", "Notificação", "Documento pertecente ao setor processual", "Envio aprovado");
		
		//Logout
		Default.logout(driver);
	}
	
	private void ajustarValoresComunicacaoECarta() throws InterruptedException {
		// Login com o usuário qu teve a permissão alterada
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Acessar o painel de controle
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[9]/a/span")).click();

		Default.waitInterval();

		// atualiza o usuário FTP
		driver.findElement(By.id("form:j_idt111")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt111")).sendKeys("user_teste");
		Default.waitInterval();

		// Atualiza a senha FTP
		driver.findElement(By.id("form:j_idt113")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt113")).sendKeys("password");
		Default.waitInterval();

		// atualiza o painel de controle
		driver.findElement(By.id("form:j_idt130")).click();

		assertEquals("As configurações foram salvas.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		Default.logout(driver);
	}

	private void ajustarPainelControle() throws InterruptedException {
		// Login com o usuário qu teve a permissão alterada
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Acessar o painel de controle
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[9]/a/span")).click();

		Default.waitInterval();

		// atualiza o campo número máximo de páginas por documento
		driver.findElement(By.id("form:j_idt52")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt52")).sendKeys("250");
		Default.waitInterval();

		// atualiza o tamanho máximo do de um lote
		driver.findElement(By.id("form:j_idt62")).clear();
		Default.waitInterval();
		driver.findElement(By.id("form:j_idt62")).sendKeys("81");
		Default.waitInterval();

		// Horário de envio de lotes ao e-carta
		Default.waitInterval();
		driver.findElement(By.id("form:time1")).clear();
		driver.findElement(By.id("form:time1")).sendKeys("18:30");
		Default.waitInterval();
		// horário de envio verificação de AR no e-carta
		driver.findElement(By.id("form:time2")).clear();
		driver.findElement(By.id("form:time2")).sendKeys("18:30;22:00");
		Default.waitInterval();

		// atualiza o painel de controle
		driver.findElement(By.id("form:j_idt130")).click();

		assertEquals("As configurações foram salvas.",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		Default.logout(driver);

	}

	private void ajustarPermissaoSetor(boolean ativarPermissao) throws InterruptedException {
		// Login com operador com permissão master
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// clicar no link de gerência de permissoes
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span")).click();

		// Escolher a opção para alterar setor
		driver.findElement(By.xpath("//div[@id='j_idt45:j_idt50']/div[3]")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt50_1")).click();

		// Informa o nome de um setor
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:autocompleteSetores_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:autocompleteSetores_input")).sendKeys("SETOR NÃO");
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt45:autocompleteSetores_panel']/ul/li")).click();

		Default.waitInterval();
		// Botão pesquisar setor
		driver.findElement(By.id("j_idt45:j_idt57")).click();
		Default.waitInterval();

		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-pencil")).click();

		driver.findElement(
				By.xpath("//div[@id='j_idt45:tabelaSetores:0:opcEnvioSemNecessidadeAnaliseSetor']/div[3]/span"))
				.click();
		Default.waitInterval();

		// Muda as permissões do usuário - enviar documento de forma direta
		if (ativarPermissao) {
			driver.findElement(By.id("j_idt45:tabelaSetores:0:opcEnvioSemNecessidadeAnaliseSetor_2")).click();
		} else {
			driver.findElement(By.id("j_idt45:tabelaSetores:0:opcEnvioSemNecessidadeAnaliseSetor_1")).click();
		}

		// clica no botão de atualização
		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-check")).click();
		Default.waitInterval();

		// verifica a mensagem de atualização do operador
		assertEquals("Setor Alterado com Sucesso!",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		Default.logout(driver);

	}

	private void ajustarPermissaoOperador(boolean ativarPermissao) throws InterruptedException {
		// Login com o usuário que teve a permissão de alterar a permissão de
		// outrso operadores modificada
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Gerência de permissões
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span")).click();
		Default.waitInterval();

		// Seleciona valor operador no select one menu
		driver.findElement(By.xpath("//div[@id='j_idt45:j_idt50']/div[3]")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt50_2")).click();

		// informar o usuário o operador para a busca
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:it_nomeOperador")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:it_nomeOperador")).sendKeys(Config.COMMON_USER_NAME_2);

		// botão de busca pelo operador
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt105")).click();

		// Editar a linha
		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-pencil")).click();

		driver.findElement(
				By.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcEnvioSemNecessidadeAnaliseOperador']/div[3]/span"))
				.click();
		Default.waitInterval();

		// Muda as permissões do usuário - enviar documento de forma direta
		if (ativarPermissao) {
			driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcEnvioSemNecessidadeAnaliseOperador_2")).click();
		} else {
			driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcEnvioSemNecessidadeAnaliseOperador_1")).click();
		}

		// clica no botão de atualização
		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-check")).click();
		Default.waitInterval();

		// verifica a mensagem de atualização do operador
		assertEquals("Operador Alterado com Sucesso!",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		Default.logout(driver);
	}

	/**
	 * 
	 * 
	 * Método que testa a permissão de aprovação/rejeição de um operador.
	 * 
	 * <p>
	 * O método realiza um envio de um documento que precisa ser analisado. Logo
	 * após o envio, o operador que teve a permissão de aprovação/rejeição
	 * alterada analisará o envio do documento, aprovando o mesmo.
	 * 
	 * 
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#enviarDocumentoGenericoAprovarEnvio(WebDriver, String,
	 *      boolean, int, String, String)
	 * 
	 * 
	 */
	public void aprovarEnvioDocumento() throws InterruptedException {
		// Loga com usuário que não pode realizar o envio sem necessida de
		// análise
		Default.login(driver, UserFactory.createCommonUser2());
		Default.waitInterval();

		// Realiza um envio com o operador logado
		Default.enviarDocumentoGenericoAprovarEnvio(driver,
				"Declaração teste. Aprovar envio com operador que teve permissão alterada", true, 2, "Paulo",
				"Documento encaminhado com sucesso!");

		Default.waitInterval();

		// Realiza o logout do usuário
		Default.logout(driver);

		driver.manage().window().maximize();

		// Logando com o usuário com a permisssão alterada e realiza a aprovação
		// do envio que acabou de ser feito
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// link de aprovação de envio
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[12]/a/span")).click();
		Default.waitInterval();

		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:aprovarEnvio")).click();
		Default.waitInterval();

		// Botão de aprovação do envio
		driver.findElement(By.id("aprovarEnvioDialogForm:botaoAprovarEnvio")).click();
		Default.waitInterval();

		// Verifica a mensagem de aprovação
		assertEquals("O envio do documento foi aprovado com sucesso",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());

		// Realiza logout do operador que teve a permissão alterada
		Default.logout(driver);
	}

	/**
	 * Método que realiza envio de um documento, testando a nova permissão do
	 * operador.
	 * <p>
	 * Após o envio, o método verifica o status do documento na tela de
	 * acompanhamento de envio.
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 * 
	 * @see Default#login(WebDriver, br.ufpi.ardigital.model.User)
	 * @see Default#logout(WebDriver)
	 * @see Default#sendDocument(WebDriver, String, String, String)
	 */
	public void realizarEnvioSemNecessidaPermissao() throws InterruptedException {
		// Logar com o usuário que teve a permissão atualizada
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();

		// Enviar um documento
		Default.sendDocument(driver, Field.DECLARATION_TEXT_SEND_DOC, "MARIA DE", "Documento encaminhado com sucesso!");
		Default.waitInterval();

		// Conferindo o status do documento enviado na tela de acompanhamento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[5]/a/span")).click();

		// Status do documento
		assertEquals("Envio aprovado",
				driver.findElement(By.xpath("//tbody[@id='formTabelaDocumentos:tabelaAcompanhamento_data']/tr/td[4]"))
						.getText());
		Default.waitInterval();
		Default.logout(driver);

	}

	/**
	 * 
	 * Método que altera permissãã de um determinando operador do AR Digital.
	 * 
	 * 
	 * 
	 * @param activePermission
	 *            o valor que indica se a permissão deve ser alterada pra "Sim"
	 *            ou pra "Não"
	 * @param permissao
	 *            um inteiro que mapeia a permissão a ser alterada
	 * 
	 * @throws InterruptedException
	 *             caso ocorra falhas ao realizar o teste
	 */
	public void changePermissionUser(boolean activePermission, int permissao) throws InterruptedException {

		//
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[11]/a/span")).click();
		Default.waitInterval();

		// Seleciona valor operador no select one menu
		driver.findElement(By.xpath("//div[@id='j_idt45:j_idt50']/div[3]")).click();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt50_2")).click();

		// informar o usuário o operador para a busca
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:it_nomeOperador")).clear();
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:it_nomeOperador")).sendKeys(Config.COMMON_USER_NAME);

		// Botão de busca de operador
		Default.waitInterval();
		driver.findElement(By.id("j_idt45:j_idt105")).click();

		// botão que torna o elementos da linha da tabela editáveis
		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-pencil")).click();

		//
		Default.waitInterval();

		switch (permissao) {
		case Config.ALTERA_PERMISSAO_ENVIO_SEM_NECESSIDADE_APROVACAO:
			driver.findElement(By
					.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcEnvioSemNecessidadeAnaliseOperador']/div[3]/span"))
					.click();
			Default.waitInterval();

			// Muda as permissões do usuário - enviar documento de forma direta
			if (activePermission) {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcEnvioSemNecessidadeAnaliseOperador_2")).click();
			} else {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcEnvioSemNecessidadeAnaliseOperador_1")).click();
			}

			break;

		case Config.ALTERA_PERMISSAO_PODE_APROVAR_ENVIOS:
			// aprovar envio documento
			Default.waitInterval();
			driver.findElement(
					By.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcPodeAprovarEnvioOperador']/div[3]/span"))
					.click();
			Default.waitInterval();

			if (activePermission) {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcPodeAprovarEnvioOperador_2")).click();
			} else {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcPodeAprovarEnvioOperador_1")).click();
			}
			break;

		case Config.ALTERA_PERMISSAO_AJUSTAR_PERMISSOES:
			// alterar permissão ajustar permissões de usuários
			Default.waitInterval();
			driver.findElement(
					By.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcAjustePermissaoOperador']/div[3]/span")).click();
			Default.waitInterval();

			if (activePermission) {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcAjustePermissaoOperador_2")).click();
			} else {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcAjustePermissaoOperador_1")).click();
			}
			break;

		case Config.ALTERA_PERMISSAO_AJUSTE_PARAMETROS_PAINEL_CONTROLE:
			// Alteração de alguns valores do painel de controle:
			// valores dos parâmetros de envio de documento
			// valores dos horários de comunicação com o servidor do e-carta
			Default.waitInterval();
			driver.findElement(
					By.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcAjustarParametrosOperador']/div[3]/span"))
					.click();
			Default.waitInterval();

			if (activePermission) {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcAjustarParametrosOperador_2")).click();
			} else {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcAjustarParametrosOperador_1")).click();
			}
			break;

		case Config.ALTERA_PERMISSAO_AJUSTE_PARAMETROS_FLUXO_ECARTA:
			// Alterar valores dos parâmetros de comunicação com e-carta
			Default.waitInterval();
			driver.findElement(
					By.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcAjustarValorFluxosECartaOperador']/div[3]/span"))
					.click();
			Default.waitInterval();

			if (activePermission) {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcAjustarValorFluxosECartaOperador_2")).click();
			} else {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcAjustarValorFluxosECartaOperador_1")).click();
			}
			break;
		case Config.ALTERA_PERMISSAO_VISUALIZAR_TODOS_DOCUMENTOS:
			// Alterar permissão de visualização de todos os documentos
			Default.waitInterval();
			driver.findElement(
					By.xpath("//div[@id='j_idt45:tabelaOperadores:0:opcVisualizarTodoDocumentosOperador']/div[3]/span"))
					.click();
			Default.waitInterval();

			if (activePermission) {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcVisualizarTodoDocumentosOperador_2")).click();
			} else {
				driver.findElement(By.id("j_idt45:tabelaOperadores:0:opcVisualizarTodoDocumentosOperador_1")).click();
			}
			break;
		}

		// clica no botão de atualização
		Default.waitInterval();
		driver.findElement(By.cssSelector("span.ui-icon.ui-icon-check")).click();
		Default.waitInterval();

		// verifica a mensagem de atualização do operador
		assertEquals("Operador Alterado com Sucesso!",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
		Default.logout(driver);
	}
}
