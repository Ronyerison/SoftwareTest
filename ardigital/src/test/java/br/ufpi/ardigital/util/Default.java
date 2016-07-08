package br.ufpi.ardigital.util;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import br.ufpi.ardigital.factory.UserFactory;
import br.ufpi.ardigital.model.User;

public class Default {
	public static final void waitInterval() throws InterruptedException{
		Thread.sleep(100);
	}
	
	public static final void login(WebDriver driver) throws InterruptedException {
		User validUser = UserFactory.criaUsuarioValido();
		driver.get(Constant.BaseURL + Constant.LoginURL);
		driver.findElement(By.id("j_idt11:email")).clear();
		driver.findElement(By.id("j_idt11:email")).sendKeys(
				validUser.getLogin());
		driver.findElement(By.id("j_idt11:senha")).clear();
		driver.findElement(By.id("j_idt11:senha")).sendKeys(
				validUser.getPassword());
		driver.findElement(By.id("j_idt11:j_idt17")).click();
	}
	
	public static final void registerNewInterested(WebDriver driver, int ignoreField) throws Exception {
		Default.login(driver);
	    Default.waitInterval();
	    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:tipo_label")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:tipo_8")).click();
	    Default.waitInterval();
	    driver.findElement(By.id("form:conteudo")).clear();
	    Default.waitInterval();
	    driver.findElement(By.id("form:conteudo")).sendKeys("Teste.");
	    Default.waitInterval();
	    driver.findElement(By.id("form:j_idt49_next")).click();
	    Default.waitInterval();
	    driver.findElement(By.cssSelector("div.ui-inputswitch-handle.ui-state-default")).click();
	    Default.waitInterval();
	    if (ignoreField != Constant.IGNORE_FIELD_CPF) {
	    	driver.findElement(By.id("form:cpf")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:cpf")).sendKeys("123.456.789-10");
	    	Default.waitInterval();			
		}
	    if (ignoreField != Constant.IGNORE_FIELD_UNIDADE_GESTORA) {
	    	driver.findElement(By.id("form:unidadeGestora")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:unidadeGestora")).sendKeys("UFPI");
	    	Default.waitInterval();
		}
	    if (ignoreField != Constant.IGNORE_FIELD_NOME) {
	    	driver.findElement(By.id("form:nomeGestor")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:nomeGestor")).sendKeys("Pedro Neto");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_TITULO) {
	    	driver.findElement(By.id("form:titulo")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:titulo")).sendKeys("Doutor");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_LOGRADOURO) {
	    	driver.findElement(By.id("form:logradouro")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:logradouro")).sendKeys("Rua do Teste");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_NUMERO) {
	    	driver.findElement(By.id("form:numero")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:numero")).sendKeys("0");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_COMPLEMENTO) {
	    	driver.findElement(By.id("form:complemento")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:complemento")).sendKeys("Bloco Z, Apto. 999");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_BAIRRO) {
	    	driver.findElement(By.id("form:bairro")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:bairro")).sendKeys("Ininga");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_CEP) {
	    	driver.findElement(By.id("form:cep")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:cep")).sendKeys("64.049-550");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_MUNICIPIO) {
	    	driver.findElement(By.id("form:municipio")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:municipio")).sendKeys("Teresina");
	    	Default.waitInterval();
	    }
	    if (ignoreField != Constant.IGNORE_FIELD_UF) {
	    	driver.findElement(By.id("form:uf")).clear();
	    	Default.waitInterval();
	    	driver.findElement(By.id("form:uf")).sendKeys("PI");
	    	Default.waitInterval();
	    }
	    driver.findElement(By.id("form:j_idt49_next")).click();
	    Default.waitInterval();
	}
	
}
