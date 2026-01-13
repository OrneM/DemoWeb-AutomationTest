package portfolio;

import com.github.javafaker.Faker;
import org.testng.annotations.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class Prueba2 {

    @Test
    public void registerUser(){

        // (1) Set up the browser
        WebDriver driver = new ChromeDriver();

        // (2) Navigate to the page
        driver.navigate().to("http://www.automationpractice.pl/index.php");

        // (3) Maximize the window
        driver.manage().window().maximize();

        // (4) Click on 'Sign In'
        WebElement lnkSignIn = driver.findElement(By.partialLinkText("Sign"));
        lnkSignIn.click();

        // (5) Enter the email
        WebElement txtEmail = driver.findElement(By.id("email_create"));
        txtEmail.sendKeys("orne@mans.com");

        // (6) Click the 'Create' button
        WebElement btnCreate = driver.findElement(By.xpath("//button[@id='SubmitCreate']"));
        btnCreate.click();

        // (7) Select the Title
        WebDriverWait timePased = new WebDriverWait(driver, Duration.ofSeconds(10));
        timePased.until(ExpectedConditions.visibilityOfElementLocated(By.id("account-creation_form")));
        timePased.until(ExpectedConditions.elementToBeClickable(By.id("id_gender2")));

        // (8) Fill in the first name
        WebElement txtName = driver.findElement(By.name("customer_firstname"));
        txtName.sendKeys("Orne");

        // (9) Fill in the last name
        WebElement txtLastName = driver.findElement(By.xpath("//input[@id='customer_lastname']"));
        txtLastName.sendKeys("Mans");

        // (10) Update the email
        WebElement txtEmailForm = driver.findElement(By.cssSelector("#email"));
        txtEmailForm.clear();
        Faker faker = new Faker();
        String mailValue = faker.internet().emailAddress();
        txtEmailForm.sendKeys(mailValue);

        // (11) Enter the password
        WebElement txtPassword = driver.findElement(By.id("passwd"));
        txtPassword.sendKeys("12345");

        // (12) Select the date of birth (day, month, year)
        Select lstDias = new Select(driver.findElement(By.name("days")));
        lstDias.selectByValue("18");

        Select lstMeses = new Select(driver.findElement(By.name("months")));
        lstMeses.selectByValue("11");

        Select Anios = new Select(driver.findElement(By.name("years")));
        Anios.selectByValue("2000");

        // (13) Click on 'Receive updates'
        WebElement chkNewsletter = driver.findElement(By.id("newsletter"));
        chkNewsletter.click();

        // (14) Click on 'Register'
        WebElement btnRegister = driver.findElement(By.name("submitAccount"));
        btnRegister.click();

        //(15) Close the browser
        driver.close();
    }
}
