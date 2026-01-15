package portfolio;

import Utils.EvidenceCapture;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;

public class Prueba3 {
    String url = "http://www.automationpractice.pl/index.php";
    WebDriver browser;
    String evidenceDirectory = "/Users/ornelamarielmansilla/IdeaProjects/DemoWeb/Evidence";
    String nameDoc = "Evidence - AutomationPractice.docx";
    File screenshot;

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

        String docPath = evidenceDirectory + File.separator + nameDoc;

        EvidenceCapture.escribirTituloEnDocumento(
                docPath,
                "Documento de Evidencias - AutomationPractice (CP02)",
                20
        );

        EvidenceCapture.captureScreenshotIntoDoc(browser, docPath, "CP02_Click_ContactUs");

        // (3) Click on 'Contact Us'
        WebElement lnkContact = browser.findElement(By.linkText("Contact us"));
        lnkContact.click();

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

        EvidenceCapture.captureScreenshotIntoDoc(browser, docPath, "CP02_Before_Send");

        // (5) Click on 'Send'
        WebElement btnSend = browser.findElement(By.xpath("//span[normalize-space()='Send']"));
        btnSend.click();

        EvidenceCapture.captureScreenshotIntoDoc(browser, docPath, "CP02_After_Send");
    }


    @Test (priority = 1, description = "CP01")
    public void searchWord () throws IOException {

        //Evidence
        screenshot = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(evidenceDirectory + "01_PantallaInicial.jpg"));
        // (3) Write word
        WebElement txtSearch = browser.findElement(By.id("search_query_top"));
        txtSearch.sendKeys("dress");

        // (4) Click in lens
        WebElement btnSearch = browser.findElement(By.name("submit_search"));
        btnSearch.click();

        screenshot = ((TakesScreenshot)browser).getScreenshotAs(OutputType.FILE);
        FileUtils.copyFile(screenshot, new File(evidenceDirectory + "02_PantallaInicial.jpg"));

        // assertions
        String expectedTitle = "Search - My Shop";
        String actualTitle = browser.getTitle();
        Assert.assertEquals(actualTitle, expectedTitle);

    }

    @AfterSuite
    public void quitBrowser(){
        browser.quit();
    }

}
