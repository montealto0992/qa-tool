package org.qa.tool.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class ProductPage extends BasePage {



    public ProductPage() {
        url = "https://shop.demoqa.com/";
    }

    public void product() {

        WebElement product = webDriver.findElement(By.xpath("//*[@id='noo-site']/div[2]/div[3]/div/div[2]/div/div/div/div[2]/div[2]/div[3]/div/h3/a"));
        product.click();

    }
}
