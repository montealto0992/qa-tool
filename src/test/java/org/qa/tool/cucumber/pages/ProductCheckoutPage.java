package org.qa.tool.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebElement;
import org.qa.tool.cucumber.pages.util.PageWait;

import java.time.Duration;

public class ProductCheckoutPage extends BasePage {


    private String thankyouXpath = "//*[@id='post-7']/div/div/div/p[1]";


    public ProductCheckoutPage() {
        url = "https://shop.demoqa.com/checkout/";
    }

    public void productCheckout() {

//        WebElement firstNameElement = webDriver.findElement(By.xpath("//*[@id='billing_first_name']"));
//        firstNameElement.sendKeys(firstname);
//
//        WebElement lastNameElement = webDriver.findElement(By.xpath("//*[@id='billing_last_name']"));
//        lastNameElement.sendKeys(lastname);
//
//        WebElement companyNameElement = webDriver.findElement(By.xpath("//*[@id='billing_company']"));
//        companyNameElement.sendKeys(company);

        WebElement termsElement = webDriver.findElement(By.xpath("//*[@id='terms']"));
        termsElement.click();

        WebElement placeOrderElement = webDriver.findElement(By.xpath("//*[@id='place_order']"));
        placeOrderElement.click();
    }

    public String getCheckoutConfirmation() {

        WebElement webElement = PageWait.getInstance().waitForElementExist(By.xpath(thankyouXpath),
                Duration.ofSeconds(20),
                Duration.ofSeconds(5),
                NoSuchElementException.class);
        return webElement.getText();
    }
}
