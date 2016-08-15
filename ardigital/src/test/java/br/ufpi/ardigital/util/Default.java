package br.ufpi.ardigital.util;

import static org.junit.Assert.assertEquals;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.model.User;

public class Default {

	public static final int IGNORE_FIELD_CPF = 0;
	public static final int IGNORE_FIELD_UNIDADE_GESTORA = 1;
	public static final int IGNORE_FIELD_NOME = 2;
	public static final int IGNORE_FIELD_TITULO = 3;
	public static final int IGNORE_FIELD_LOGRADOURO = 4;
	public static final int IGNORE_FIELD_NUMERO = 5;
	public static final int IGNORE_FIELD_COMPLEMENTO = 6;
	public static final int IGNORE_FIELD_BAIRRO = 7;
	public static final int IGNORE_FIELD_CEP = 8;
	public static final int IGNORE_FIELD_MUNICIPIO = 9;
	public static final int IGNORE_FIELD_UF = 10;
	public static final int IGNORE_FIELD_NONE = 11;
	public static final int IGNORE_FIELD_TIPO_DOCUMENTO = 12;
	public static final int IGNORE_FIELD_DECLARACAO_CONTEUDO = 13;
	public static final int UF_INVALIDA_MAIOR_PERMITIDO = 14;
	public static final int UF_INVALIDA_MENOR_PERMITIDO = 15;

	public static final void waitInterval() throws InterruptedException {
		Thread.sleep(750);
	}

	public static final void login(WebDriver driver, User validUser) throws InterruptedException {
		driver.get(Config.BASE_URL + Config.LOGIN_URL);
		driver.findElement(By.id("formLogin:username")).clear();
		driver.findElement(By.id("formLogin:username")).sendKeys(validUser.getLogin());
		driver.findElement(By.id("formLogin:senha")).clear();
		driver.findElement(By.id("formLogin:senha")).sendKeys(validUser.getPassword());
		driver.findElement(By.id("formLogin:botaoLogin")).click();
	}

	public static void logout(WebDriver driver) throws InterruptedException {
		// logout
		driver.findElement(By.id("formNotificacaoes:acaoSair_button")).click();
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='formNotificacaoes:acaoSair_menu']/ul/li/a/span[2]")).click();
		Default.waitInterval();
	}

	/**
	 * Teste que é realizado quando um documento é enviado a um novo
	 * interessado.
	 * <p>
	 * O teste será feito sobre os campos obrigatórios do interessado
	 * 
	 * @param driver
	 *            a instância do driver do chrome criada para o teste
	 * @param ignoreField
	 *            campo a ser validado
	 * 
	 * @throws Exception
	 *             caso ocorra falhas ao realizar o teste
	 */
	public static final void registrarNovoInteressado(WebDriver driver, int ignoreField) throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");

		// Ir para o próximo passo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
		Default.waitInterval();

		if (ignoreField != IGNORE_FIELD_NOME) {
			driver.findElement(By.id("formEnviarDocumento:nomeGestor")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:nomeGestor")).sendKeys("MARIA DAS DORES");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_TITULO) {
			driver.findElement(By.id("formEnviarDocumento:titulo")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:titulo")).sendKeys("PREFEITA");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_LOGRADOURO) {
			driver.findElement(By.id("formEnviarDocumento:logradouro")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:logradouro")).sendKeys("RUA JOÃO MARTINIANO");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_NUMERO) {
			driver.findElement(By.id("formEnviarDocumento:numero")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:numero")).sendKeys("1290");
			Default.waitInterval();
		}

		// Opcional
		if (ignoreField != IGNORE_FIELD_COMPLEMENTO) {
			driver.findElement(By.id("formEnviarDocumento:complemento")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:complemento"))
					.sendKeys("PRÓXIMO AO CEMITÉRIO SÃO VICENTE DE PAULA");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_BAIRRO) {
			driver.findElement(By.id("formEnviarDocumento:bairro")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:bairro")).sendKeys("COLIBRI");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_CEP) {
			driver.findElement(By.id("formEnviarDocumento:cep")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:cep")).sendKeys("64240000");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_MUNICIPIO) {
			driver.findElement(By.id("formEnviarDocumento:municipio")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:municipio")).sendKeys("PIRACURUCA");
			Default.waitInterval();
		}
		if (ignoreField != IGNORE_FIELD_UF) {
			driver.findElement(By.id("formEnviarDocumento:uf")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:uf")).sendKeys("PI");
			Default.waitInterval();
		}

		// Ir para o step de upload de arquivos
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();
	}

	/**
	 * Teste que é realizado quando um documento é enviado a um novo
	 * interessado.
	 * <p>
	 * A unidade federativa do interessado é definida de forma errada. O valor
	 * da unidade federativa só pode apresentar dois caracteres apenas.
	 * 
	 * @param driver
	 *            a instância do driver do chrome criada para o teste
	 * @param erroUF
	 *            indica o tipo de erro do valor da unidade federativa: maior
	 *            que 2 carecateres ou menor que 2 caracteres
	 * 
	 * @throws Exception
	 *             caso ocorra falhas ao realizar o teste
	 */
	public static final void registrarNovoInteressadoUFInvalida(WebDriver driver, int erroUF) throws Exception {
		Default.login(driver, UserFactory.createCommonUser());
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");

		// Ir para o próximo passo
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

		driver.findElement(By.id("formEnviarDocumento:numero")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:numero")).sendKeys("1290");
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:complemento")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:complemento"))
				.sendKeys("PRÓXIMO AO CEMITÉRIO SÃO VICENTE DE PAULA");
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:bairro")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:bairro")).sendKeys("COLIBRI");
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:cep")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:cep")).sendKeys("64240000");
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:municipio")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:municipio")).sendKeys("PIRACURUCA");
		Default.waitInterval();

		if (erroUF == Default.UF_INVALIDA_MAIOR_PERMITIDO) {
			driver.findElement(By.id("formEnviarDocumento:uf")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:uf")).sendKeys("PI Teste");
			Default.waitInterval();
		}

		if (erroUF == Default.UF_INVALIDA_MENOR_PERMITIDO) {
			driver.findElement(By.id("formEnviarDocumento:uf")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:uf")).sendKeys("P");
			Default.waitInterval();
		}

		// Ir para o step de upload de arquivos
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();
	}

	/**
	 * Método que preenche todas as informações até o step de arquivos, tornando
	 * um método genérico para testar o upload de arquivos.
	 */
	public static void enviarDocumentoGenericoParaArquivos(WebDriver driver, boolean userSetorProcessual)
			throws InterruptedException {

		// Login
		if (userSetorProcessual) {
			Default.login(driver, UserFactory.createAdministratorUser());
		} else {
			Default.login(driver, UserFactory.createCommonUser());
		}
		Default.waitInterval();

		// Link de envio do documento
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();

		// Informar tipo de documento
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		// Declaração de conteúdo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys("Declaração teste");
		Default.waitInterval();

		// Ir para o próximo passso
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		// Gestor da base do TCE
		driver.findElement(By.id("formEnviarDocumento:gestorAutocomplete_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:gestorAutocomplete_input")).sendKeys("Maria das");

		// Escolha do interessado
		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='formEnviarDocumento:gestorAutocomplete_panel']/ul/li")).click();

		Default.waitInterval();

		// Ir para o próximo step - Arquivos
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
	}

	/**
	 * Método genérico utilizado para aprovação de docuementos.
	 * 
	 * @param driver
	 * @param description
	 * @param carregaOficio
	 * @param numeroPaginasAnexos
	 * @param receiver
	 * @param returnMsg
	 * @throws InterruptedException
	 */
	public static void enviarDocumentoGenericoAprovarEnvio(WebDriver driver, String description, boolean carregaOficio,
			int numeroPaginasAnexos, String receiver, String returnMsg) throws InterruptedException {
		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();

		// Informar tipo de documento
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		// Declaração de conteúdo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys(description);
		Default.waitInterval();

		// Ir para o próximo passso
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:gestorAutocomplete_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:gestorAutocomplete_input")).sendKeys(receiver);

		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='formEnviarDocumento:gestorAutocomplete_panel']/ul/li")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();

		Default.waitInterval();

		// Carregar ofício
		if (carregaOficio) {
			driver.findElement(By.id("formEnviarDocumento:upOficio_input")).clear();
			Default.waitInterval();
			driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.FILE_PATH);
			Default.waitInterval();
		}

		for (int i = 0; i < numeroPaginasAnexos; i++) {
			driver.findElement(By.id("formEnviarDocumento:upAnexo_input")).sendKeys(Config.FILE_PATH);
			Default.waitInterval();
		}

		// Marcar o botão para envio dos anexos para o e-carta
		driver.findElement(By.id("formEnviarDocumento:checkboxEnviarAnexos")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:ajax")).click();
		Default.waitInterval();

		assertEquals(returnMsg, driver.findElement(By.cssSelector("div.ui-grid-col-12")).getText());
	}

	/**
	 * Método genérico que realiza um envio de um documento.
	 * 
	 * <p>
	 * O método é utilizado para auxiliar o teste das demais funcionalidades.
	 * 
	 * 
	 * @param driver
	 *            a instancia do driver no instante do teste
	 * @param description
	 *            a declaração de conteúdo do envio
	 * @param receiver
	 *            o nome do destinatário do envio
	 * @param returnMsg
	 *            a mensagem de sucesso do envio
	 * 
	 * @throws InterruptedException
	 *             caso haja falhas na operação do envi do documento
	 */
	public static void sendDocument(WebDriver driver, String description, String receiver, String returnMsg)
			throws InterruptedException {

		driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
		Default.waitInterval();

		// Informar tipo de documento - sempre notificação
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_label")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:tipo_2")).click();

		// Declaração de conteúdo
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:conteudo")).sendKeys(description);
		Default.waitInterval();

		// Ir para o próximo passso
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();

		driver.findElement(By.id("formEnviarDocumento:gestorAutocomplete_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:gestorAutocomplete_input")).sendKeys(receiver);

		Default.waitInterval();
		driver.findElement(By.xpath("//div[@id='formEnviarDocumento:gestorAutocomplete_panel']/ul/li")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).clear();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:upOficio_input")).sendKeys(Config.FILE_PATH);
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:passosEnvioDocumento_next")).click();
		Default.waitInterval();
		driver.findElement(By.id("formEnviarDocumento:ajax")).click();
		Default.waitInterval();
		assertEquals(returnMsg, driver.findElement(By.cssSelector("div.ui-grid-col-12")).getText());
	}

	/**
	 * Método genérico de rejeição de documento.
	 * 
	 * <p>
	 * Utilizado para remover elementos da tabela.
	 * 
	 * @param driver
	 *            a instância do driver no momento do teste
	 * @throws InterruptedException
	 * 
	 * 
	 */
	public static void rejeitarEnvioGenerico(WebDriver driver) throws InterruptedException {

		// botão da ação de rejeição
		driver.findElement(By.id("formTabelaDocumentos:tabelaAprovacaoPendencia:0:rejeitarEnvio")).click();
		Default.waitInterval();

		// Informar o motivo da rejeição
		driver.findElement(By.id("j_idt160:motivoRejeicaoEnvio")).clear();
		driver.findElement(By.id("j_idt160:motivoRejeicaoEnvio")).sendKeys("TESTANDO A REJEIÇÃO");

		Default.waitInterval();

		//
		driver.findElement(By.id("j_idt160:botaoRejeitarEnvio")).click();
		Default.waitInterval();

		assertEquals("O envio do documento foi rejeitado com sucesso",
				driver.findElement(By.cssSelector("span.ui-messages-info-summary")).getText());
	}

	/**
	 * Método que verifica os detalhes de um documento enviado.
	 * 
	 * @param driver
	 *            a instância do driver no momento do teste
	 * @param operador
	 *            o operador que realizou o envio
	 * @param remetente
	 *            o setor do operador
	 * @param setorRemetente
	 *            o setor remetente
	 * @param tipoDocumento
	 *            o tipo do documento enviado
	 * @param declaracao
	 *            a declaração de conteúdo informada no momento do envio
	 * @param status
	 *            o status do envio do documento
	 */
	public static void verificarDadosDocumento(WebDriver driver, String operador, String remetente, String setorRemetente, String tipoDocumento,
			String declaracao, String status) {

		// Dados do documento
		// operador
		assertEquals(operador, driver.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt135']/tbody/tr[1]/td[2]")).getText());
		// Remetente
		assertEquals(remetente, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt135']/tbody/tr[2]/td[2]")).getText());
		// Setor Remetente
		assertEquals(setorRemetente, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt135']/tbody/tr[3]/td[2]")).getText());
		// Tipo do documento
		assertEquals(tipoDocumento, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt135']/tbody/tr[4]/td[2]")).getText());
		// Declração de conteúdo
		assertEquals(declaracao, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt135']/tbody/tr[5]/td[2]")).getText());
		// Status do documento
		assertEquals(status, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt135']/tbody/tr[6]/td[2]")).getText());
	}

	/**
	 * Método que verifica os dados do interessado.
	 * 
	 *  @param driver
	 *            a instância do driver no momento do teste
	 * @param nomeInterassado
	 *            nome do interessado
	 * @param tituloInteressado
	 *            titulo do interessado
	 * @param unidadeGestora
	 *            a unidade gestora do interessado
	 * @param cep
	 *            o cep do interessado
	 * @param logradouro
	 *            rua/avenida de endereço do interessado
	 * @param numero
	 *            o número de endereço do interessado
	 * @param complemento
	 *            o complemento de endereço do interessado
	 * @param bairro
	 *            o bairro do interessado
	 * @param cidade
	 *            a cidade do interessado
	 * @param estado
	 *            a UF do interessado
	 */
	public static void verificarDadosInteressado(WebDriver driver, String nomeInterassado, String tituloInteressado, String unidadeGestora,
			String cep, String logradouro, String numero, String complemento, String bairro, String cidade,
			String estado) {
		// dados do Destinatário

		// nome do interessado
		assertEquals(nomeInterassado, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[1]/td[2]")).getText());
		// Titulo interessado
		assertEquals(tituloInteressado, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[2]/td[2]")).getText());
		// Unidade gestora
		assertEquals(unidadeGestora, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[3]/td[2]")).getText());
		// CEP
		assertEquals(cep, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[4]/td[2]")).getText());
		// Logradouro
		assertEquals(logradouro, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[5]/td[2]")).getText());

		// Numero
		assertEquals(numero, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[6]/td[2]")).getText());

		// Complemento
		assertEquals(complemento, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[7]/td[2]")).getText());

		// Bairro
		assertEquals(bairro, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[8]/td[2]")).getText());

		// Cidade
		assertEquals(cidade, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[9]/td[2]")).getText());

		// Estado
		assertEquals(estado, driver
				.findElement(By.xpath("//table[@id='formDetalhesDocumento:j_idt181']/tbody/tr[10]/td[2]")).getText());

	}

}
