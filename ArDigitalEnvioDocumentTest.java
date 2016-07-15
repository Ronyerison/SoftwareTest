package javaweb.br.ufpi.ardigital.test;


import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * Classe para realizar os testes na tela de envio de documentos
 * @author Ranieri
 *
 */
public class ArDigitalEnvioDocumentTest {
  //atributo para controle do driver de emulacao web
  private WebDriver driver;
  //variavel para armazenar o link de base para a aplicacao
  private String baseUrl;
  //variavel auxiliar
  private boolean acceptNextAlert = true;
  //variavel auxiliar
  private StringBuffer verificationErrors = new StringBuffer();

  /**
   * Metodo que é chamado antes da excecusao dos testes
   * @throws Exception
   */
  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://10.28.14.224:8181";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  /**
   * Teste de envio de documentos sem passar a informacao de conteúdo
   * @throws Exception
   */
  @Test
  public void testErrorImputDelConteudoEnviarDocumento() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user10");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_1")).click();
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertEquals("É necessário informar uma declaração de conteúdo", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
  }
  
  @Test
  public void testErrorImputEmissorEnviarDocumento() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user10");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertEquals("É necessário informar um tipo de documento", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
  }
  
  @Test
  public void testNormalTipoDocumentoSelect2() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user5");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_2")).click();
    driver.findElement(By.id("form:conteudo")).clear();
    driver.findElement(By.id("form:conteudo")).sendKeys("Conteudo de teste");
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertTrue(isElementPresent(By.id("form:j_idt69")));
    try {
      assertTrue(isElementPresent(By.xpath("//div[@id='form:j_idt49']/ul/li[2]")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }
  
  @Test
  public void testNormalTipoDocumentoSelect3() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user5");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_3")).click();
    driver.findElement(By.id("form:conteudo")).clear();
    driver.findElement(By.id("form:conteudo")).sendKeys("Conteudo de teste");
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertTrue(isElementPresent(By.id("form:j_idt69")));
    try {
      assertTrue(isElementPresent(By.xpath("//div[@id='form:j_idt49']/ul/li[2]")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }
  
  @Test
  public void testNormalTipoDocumentoSelect4() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user5");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_4")).click();
    driver.findElement(By.id("form:conteudo")).clear();
    driver.findElement(By.id("form:conteudo")).sendKeys("Conteudo de teste");
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertTrue(isElementPresent(By.id("form:j_idt69")));
    try {
      assertTrue(isElementPresent(By.xpath("//div[@id='form:j_idt49']/ul/li[2]")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }
  
  @Test
  public void testAdminTipoDocumentoSelect2() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user10");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_2")).click();
    driver.findElement(By.id("form:conteudo")).clear();
    driver.findElement(By.id("form:conteudo")).sendKeys("Conteudo de teste");
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertTrue(isElementPresent(By.id("form:j_idt69")));
    try {
      assertTrue(isElementPresent(By.xpath("//div[@id='form:j_idt49']/ul/li[2]")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }
  
  @Test
  public void testAdminTipoDocumentoSelect3() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user10");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_3")).click();
    driver.findElement(By.id("form:conteudo")).clear();
    driver.findElement(By.id("form:conteudo")).sendKeys("Conteudo de teste");
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertTrue(isElementPresent(By.id("form:j_idt69")));
    try {
      assertTrue(isElementPresent(By.xpath("//div[@id='form:j_idt49']/ul/li[2]")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
  }
  
  @Test
  public void testAdminTipoDocumentoSelect4() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user10");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    driver.findElement(By.xpath("//div[@id='j_idt14:j_idt15']/ul/li[4]/a/span")).click();
    driver.findElement(By.id("form:tipo_label")).click();
    driver.findElement(By.id("form:tipo_4")).click();
    driver.findElement(By.id("form:conteudo")).clear();
    driver.findElement(By.id("form:conteudo")).sendKeys("Conteudo de teste");
    driver.findElement(By.id("form:j_idt49_next")).click();
    assertTrue(isElementPresent(By.id("form:j_idt69")));
    try {
      assertTrue(isElementPresent(By.xpath("//div[@id='form:j_idt49']/ul/li[2]")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
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

  private boolean isAlertPresent() {
    try {
      driver.switchTo().alert();
      return true;
    } catch (NoAlertPresentException e) {
      return false;
    }
  }

  private String closeAlertAndGetItsText() {
    try {
      Alert alert = driver.switchTo().alert();
      String alertText = alert.getText();
      if (acceptNextAlert) {
        alert.accept();
      } else {
        alert.dismiss();
      }
      return alertText;
    } finally {
      acceptNextAlert = true;
    }
  }
}
