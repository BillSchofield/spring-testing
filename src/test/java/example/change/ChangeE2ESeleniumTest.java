package example.change;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ChangeE2ESeleniumTest {

    private WebDriver driver;

    @LocalServerPort
    private int port;

    @BeforeAll
    public static void setUpClass() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void setUp() {
        var chromeOptions = new ChromeOptions();
        chromeOptions.addArguments("--headless=new");
        driver = new ChromeDriver(chromeOptions);
    }

    @AfterEach
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void shouldHaveNoCoinsWhenThereIsNoChange() {
        int changeInCents = 0;
        var actualCoins = findCoinsFor(changeInCents);

        verifyCoins(actualCoins, "0 quarters, 0 dimes, 0 nickels, 0 pennies");
    }

    @Test
    public void shouldHaveOnePennyWhenChangeIsOneCent() {
        int changeInCents = 1;
        var actualCoins = findCoinsFor(changeInCents);

        verifyCoins(actualCoins, "1 pennies");
    }

    @Test
    public void shouldHaveOneNickelWhenChangeIsFiveCents() {
        int changeInCents = 5;
        var actualCoins = findCoinsFor(changeInCents);

        verifyCoins(actualCoins, "1 nickels");
    }

    @Test
    public void shouldHaveOneDimeWhenChangeIsTenCents() {
        int changeInCents = 10;
        var actualCoins = findCoinsFor(changeInCents);

        verifyCoins(actualCoins, "1 dimes");
    }

    @Test
    public void shouldHaveOneQuarterWhenChangeIsTwentyFiveCents() {
        int changeInCents = 25;
        var actualCoins = findCoinsFor(changeInCents);

        verifyCoins(actualCoins, "1 quarters");
    }





    private static void verifyCoins(WebElement actualCoins, String expectedCoins) {
        assertThat(actualCoins.getText(), containsString(expectedCoins));
    }

    private WebElement findCoinsFor(int changeInCents) {
        driver.navigate().to(String.format("http://localhost:%s/change/?cents=" + changeInCents, port));
        var result = driver.findElement(By.tagName("body"));
        return result;
    }

}
