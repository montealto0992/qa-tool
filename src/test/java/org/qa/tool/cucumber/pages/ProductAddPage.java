package org.qa.tool.cucumber.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class ProductAddPage extends BasePage {


    public ProductAddPage() {
        url = "https://shop.demoqa.com/product/black-lux-graphic-t-shirt/";
    }

    public void productAdd() {

        Select pickColor = new Select(webDriver.findElement(By.xpath("//*[@id='pa_color']")));
        pickColor.selectByVisibleText("Black");

        Select pickSize = new Select(webDriver.findElement(By.xpath("//*[@id='pa_size']")));
        pickSize.selectByVisibleText("34");

        WebElement addToCart = webDriver.findElement(By.xpath("//*[@id='product-1485']/div[1]/div[2]/form/div/div[2]/button"));
        addToCart.click();
    }

    public void viewCart() {
        WebElement viewCart = webDriver.findElement(By.xpath("//*[@id='noo-site']/div[2]/div/div/div[1]/div/a"));
        viewCart.click();

    }
}