package javaweb.br.ufpi.ardigital.test;



import java.util.regex.Pattern;
import java.util.concurrent.TimeUnit;
import org.junit.*;
import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class ArDigitalLoginTest {
  private WebDriver driver;
  private String baseUrl;
  private boolean acceptNextAlert = true;
  private StringBuffer verificationErrors = new StringBuffer();

  @Before
  public void setUp() throws Exception {
    driver = new FirefoxDriver();
    baseUrl = "http://10.28.14.224:8181";
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
  }

  @Test
  public void testInvalidPassword() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user5");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("testedesenha");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    try {
      assertTrue(isElementPresent(By.id("j_idt11:j_idt17")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    assertEquals("Usuário ou senha incorretos", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
  }
  
  @Test
  public void testInvalidLogin() throws Exception {
    driver.get(baseUrl + "/ar-digital/login.xhtml");
    driver.findElement(By.id("j_idt11:email")).clear();
    driver.findElement(By.id("j_idt11:email")).sendKeys("ar_user10");
    driver.findElement(By.id("j_idt11:senha")).clear();
    driver.findElement(By.id("j_idt11:senha")).sendKeys("armestrado2016");
    driver.findElement(By.id("j_idt11:j_idt17")).click();
    try {
      assertTrue(isElementPresent(By.id("j_idt11:j_idt17")));
    } catch (Error e) {
      verificationErrors.append(e.toString());
    }
    assertEquals("Usuário ou senha incorretos", driver.findElement(By.cssSelector("span.ui-messages-error-summary")).getText());
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
