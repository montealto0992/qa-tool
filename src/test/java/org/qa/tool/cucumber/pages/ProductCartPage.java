package org.qa.tool.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.qa.tool.cucumber.pages.BasePage;

public class ProductCartPage extends BasePage {



    public ProductCartPage() {
        url = "https://shop.demoqa.com/cart/";
    }

    public void productCart() {

        WebElement proceedCheckout = webDriver.findElement(By.xpath("//*[@id='post-6']/div/div/div[2]/div[2]/div/a"));
        proceedCheckout.click();
    }

}
