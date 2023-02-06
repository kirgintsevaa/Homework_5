import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyMainTest {
    private final String login = "pomobaf324@quamox.com";
    private final String password = "Pomobaf324@quamox.com";
    private final String skype = "alen_alen92";
    private final String telegram = "@kirgintseva";
    private WebDriver driver;

    @BeforeAll
    public static void setUp() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("---start-fullscreen");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    public void close() {
        if (driver != null)
            driver.quit();
    }

    @Test
    public void testTask() {
        // 1. Открыть https://otus.ru
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        Logger logger = LogManager.getLogger(String.valueOf(MyMainTest.class));
        driver.get("https://otus.ru/");
        // 2. Авторизоваться на сайте
        loginInOtus();
        logger.info(driver.manage().getCookies().toString());
        // 3. Войти в личный кабинет
        Actions action = new Actions(driver);
        WebElement elementMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header3__user-info-name")));
        WebElement elementMyMenu = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Мой профиль')]")));
        action.moveToElement(elementMenu).click(elementMyMenu).build().perform();

        // 4. В разделе "О себе" заполнить все поля "Личные данные" и добавить не менее двух контактов // 5. Нажать сохранить
        driver.findElement(By.id("id_fname")).clear();
        WebElement elementFname = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_fname")));
        elementFname.sendKeys("Алёна");
        driver.findElement(By.id("id_fname_latin")).clear();
        WebElement elementFnameL = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_fname_latin")));
        elementFnameL.sendKeys("Alena");
        driver.findElement(By.id("id_lname")).clear();
        WebElement elementLname = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_lname")));
        elementLname.sendKeys("Киргинцева");
        driver.findElement(By.id("id_lname_latin")).clear();
        WebElement elementLnameL = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_lname_latin")));
        elementLnameL.sendKeys("Kirgintseva");
        driver.findElement(By.id("id_blog_name")).clear();
        WebElement elementBname = wait.until(ExpectedConditions.presenceOfElementLocated(By.id("id_blog_name")));
        elementBname.sendKeys("Алёна");
        driver.findElement(By.cssSelector("input[name='date_of_birth']")).clear();
        WebElement elementDate = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='date_of_birth']")));
        elementDate.sendKeys("24.12.1992");

        WebElement elementCountry = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name='country'] ~ div")));
        action.moveToElement(elementCountry).click().perform();
        WebElement elementCurCountry = driver.findElement(By.cssSelector("button[title='Россия']"));
        elementCurCountry.click();

        WebElement elementLanguage = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("[name='english_level']+div")));
        action.moveToElement(elementLanguage).click().perform();
        WebElement elementCurLanguage = driver.findElement(By.cssSelector("button.lk-cv-block__select-option[title='Средний (Intermediate)']"));
        elementCurLanguage.click();

        WebElement elementCity = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[data-title='Город']+div")));
        action.moveToElement(elementCity).click().perform();
        WebElement elementCurCity = driver.findElement(By.cssSelector("button.lk-cv-block__select-option[title='Санкт-Петербург']"));
        elementCurCity.click();

        driver.findElement(By.cssSelector("#id_ready_to_relocate_1+span")).click();
        driver.findElement(By.cssSelector("input[value='remote']+span")).click();

        WebElement elementFirstContact = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='contact-0-service']+div")));
        action.moveToElement(elementFirstContact).click().perform();
        WebElement elementFirstContactType = driver.findElement(By.cssSelector("button[data-value='skype']"));
        elementFirstContactType.click();
        WebElement elementFirstContactValue = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='contact-0-value']")));
        elementFirstContactValue.clear();
        elementFirstContactValue.sendKeys(skype);

        driver.findElement(By.cssSelector(".js-lk-cv-custom-select-add")).click();

        WebElement elementSecondContact = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='contact-1-service']+div")));
        action.moveToElement(elementSecondContact).click().perform();
        WebElement elementSecondContactType = driver.findElement(By.cssSelector("[data-num='1'] button:nth-child(7)"));
        elementSecondContactType.click();
        WebElement elementSecondContactValue = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("input[name='contact-1-value']")));
        elementSecondContactValue.clear();
        elementSecondContactValue.sendKeys(telegram);

        driver.findElement(By.cssSelector("[title='Сохранить и продолжить']")).click();

        // 6. Открыть https://otus.ru в "чистом браузере"
        driver.manage().deleteAllCookies();
        driver.get("https://otus.ru");
        // 7. Авторизоваться на сайте
        loginInOtus();
        // 8. Войти в личный кабинет
        WebElement element4 = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".header3__user-info-name")));
        action.moveToElement(element4);
        WebElement element5 = wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//a[contains(text(), 'Мой профиль')]")));
        action.moveToElement(element5).click(element5).build().perform();

        // 9. Проверить, что в разделе "О себе" отображаются указанные ранее данные
        Assertions.assertEquals("Алёна", driver.findElement(By.id("id_fname")).getAttribute("value"));
        Assertions.assertEquals("Alena", driver.findElement(By.id("id_fname_latin")).getAttribute("value"));
        Assertions.assertEquals("Киргинцева", driver.findElement(By.id("id_lname")).getAttribute("value"));
        Assertions.assertEquals("Kirgintseva", driver.findElement(By.id("id_lname_latin")).getAttribute("value"));
        Assertions.assertEquals("Алёна", driver.findElement(By.id("id_blog_name")).getAttribute("value"));
        Assertions.assertEquals("24.12.1992", driver.findElement(By.cssSelector("input[name='date_of_birth']")).getAttribute("value"));
        Assertions.assertEquals("Россия", driver.findElement(By.cssSelector("[name='country'] ~ div")).getText());
        Assertions.assertEquals("Санкт-Петербург", driver.findElement(By.cssSelector("input[data-title='Город']+div")).getText());
        Assertions.assertEquals("Средний (Intermediate)", driver.findElement(By.cssSelector("[name='english_level']+div")).getText());
        Assertions.assertEquals("Да", driver.findElement(By.cssSelector("#id_ready_to_relocate_1+span")).getText());
        Assertions.assertTrue(true, driver.findElement(By.cssSelector("input[value='remote']+span")).getAttribute("checked"));
        Assertions.assertEquals("Skype", driver.findElement(By.cssSelector("input[name='contact-0-service']+div")).getText());
        Assertions.assertEquals(skype, driver.findElement(By.cssSelector("input[name='contact-0-value']")).getAttribute("value"));
        Assertions.assertEquals("Тelegram", driver.findElement(By.cssSelector("input[name='contact-1-service']+div")).getText());
        Assertions.assertEquals(telegram, driver.findElement(By.cssSelector("input[name='contact-1-value']")).getAttribute("value"));
    }

    private void loginInOtus() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        driver.findElement(By.cssSelector(".header3__button-sign-in")).click();
        try {
            wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.cssSelector(".header3__button-sign-in"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
        WebElement element = wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector("form[action='/login/']>div+div input[type='text']")));
        element.sendKeys(login);
        driver.findElement(By.cssSelector("input[type = 'password']")).sendKeys(password);
        driver.findElement(By.cssSelector("form[action='/login/']>div+div+div+div>button")).click();
    }
}


