package org.qa.tool.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.qa.tool.cucumber.pages.util.PageWait;

import java.time.Duration;


public class LoginPage extends BasePage {

    private String logoutXpath = "//*[@id=\"post-8\"]/div/div/nav/ul/li[6]/a";
    private String logoXpath = "//a[normalize-space()='Powered by WordPress']";
    @FindBy(id = "username")
    private WebElement usernameTxt;
    @FindBy(id = "password")
    private WebElement passwordTxt;
    @FindBy(name = "login")
    private WebElement submitBtn;

    @FindBy(id = "reg_username")
    private WebElement usernameReg;
    @FindBy(id = "reg_email")
    private WebElement emailReg;
    @FindBy(id = "reg_password")
    private WebElement passwordReg;
    @FindBy(name ="register")
    private WebElement registerBtn;

    @FindBy(name ="log")
    private WebElement usernameLogin;
    @FindBy(name ="pwd")
    private WebElement passwordLogin;
    @FindBy(name="wp-submit")
    private WebElement submitLogin;


    public LoginPage() {
        url = "https://shop.demoqa.com/my-account/";
        webDriver.get(url);
    }

    public void login(String username, String password) {
        usernameTxt.sendKeys(username);
        passwordTxt.sendKeys(password);
        submitBtn.click();
    }

    public void register(String username, String password, String email){
        usernameReg.sendKeys(username);
        emailReg.sendKeys(email);
        passwordReg.sendKeys(password);
        registerBtn.click();

    }

    public void verifyLogin(String username, String password){
            usernameLogin.sendKeys(username);
            passwordLogin.sendKeys(password);
            submitLogin.click();
    }

    public String getLoginConfirmation() {
        WebElement webElement = PageWait.getInstance().waitForElementExist(By.xpath(logoutXpath),
                Duration.ofSeconds(20));
        return webElement.getText();
    }

    public String getLoginConfirmation1() {

        WebElement webElement = PageWait.getInstance().waitForElementExist(By.xpath(logoutXpath),
                Duration.ofSeconds(20),
                Duration.ofSeconds(5),
                NoSuchElementException.class);
        return webElement.getText();
    }

    public void getRegistrationConfirmation(){

        WebElement webElement = PageWait.getInstance().waitForElementExist(By.xpath(logoXpath),
                Duration.ofSeconds(20),
                Duration.ofSeconds(5),
                NoSuchElementException.class);

    }

    public void clickLogo() {
        WebElement logo = webDriver.findElement(By.xpath("//*[@id='noo-site']/header/div[2]/div/div/div/div/a/img"));
        logo.click();
    }


}
