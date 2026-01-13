package portfolio;

import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class Prueba1 {

    @Test
    public void searchProduct() {

        // 1- define browser
        WebDriver browser = new ChromeDriver();

        // 2- maximize windows and clear cookies
        browser.manage().window().maximize();
        browser.manage().deleteAllCookies();

        // 3- access the URL
        browser.get("http://www.automationpractice.pl/index.php");

        // 4- write the product you want to search for (text field)
        WebElement txtSearch = browser.findElement(By.id("search_query_top"));
        txtSearch.sendKeys("dress");

        // 5- simulate pressing enter
        txtSearch.sendKeys(Keys.ENTER);

        // 6- close the browser
        browser.close();
    }
}
