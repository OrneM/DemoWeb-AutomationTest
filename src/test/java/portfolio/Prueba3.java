package portfolio;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class Prueba3 {
    String url = "http://www.automationpractice.pl/index.php";
    WebDriver browser;

    @BeforeSuite
    public void openBrowser() {
        // (1) Set up the browser
        browser = new ChromeDriver();

        // (2) Navigate to the test page
        browser.get(url);
        browser.manage().window().maximize();

    }

    @Test (priority = 2, description = "CP02")
    public void goToContact (){


        // (3) Click on 'Contact Us'
        WebElement lnkContact = browser.findElement(By.linkText("Contact us"));
        lnkContact.click();

        // (4) Fill out the form

        // (4) Fill out the form
        // (4.1) Select a value from the 'Subject' list
        Select lstSubject = new Select(browser.findElement(By.cssSelector("#id_contact")));
        lstSubject.selectByValue("1");
        // (4.2) Email
        WebElement txtEmail = browser.findElement(By.id("email"));
        txtEmail.sendKeys("correo@gmail.com");
        // (4.3) Order reference number
        WebElement txtOrder = browser.findElement(By.id("id_order"));
        txtOrder.sendKeys(("abc 12345"));
        // (4.4) Message
        WebElement txtMsg = browser.findElement(By.tagName("textarea"));
        txtMsg.sendKeys("Mensaje de contacto con la tienda");

        // (4.5) Attach file
        WebElement atFile = browser.findElement(By.cssSelector("#fileUpload"));
        atFile.sendKeys("/Users/ornelamarielmansilla/Documents/ProyectosIt/Testing Varios/addIntegerData.rtf");

        // (5) Click on 'Send'
        WebElement btnSend = browser.findElement(By.xpath("//span[normalize-space()='Send']"));
        btnSend.click();

    }

    @Test (priority = 1, description = "CP01")
    public void searchWord () {

        // (3) Write word
        WebElement txtSearch = browser.findElement(By.id("search_query_top"));
        txtSearch.sendKeys("dress");

        // (4) Click in lens
        WebElement btnSearch = browser.findElement(By.name("submit_search"));
        btnSearch.click();

    }

    @AfterSuite
    public void quitBrowser(){
        browser.quit();
    }

}
