package org.qa.tool.cucumber.steps;


import io.cucumber.java.After;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.qa.tool.cucumber.base.BaseTest;
import org.qa.tool.cucumber.pages.*;


import static org.assertj.core.api.BDDAssertions.then;

public class CheckoutStepS extends BaseTest {

    private ProductCheckoutPage productCheckoutPage;

    @Given("Registered User is login")
    public void navigateLoginPage() {
        LoginPage loginPage = new LoginPage();
        loginPage.login("qacore", "qacore@123");
        loginPage.clickLogo();
    }


    @When("Product is added in the cart")
    public void productStep() {
        ProductPage productPage = new ProductPage();
        productPage.product();

        ProductAddPage productAddPage = new ProductAddPage();
        productAddPage.productAdd();
        productAddPage.viewCart();

        ProductCartPage productCartPage = new ProductCartPage();
        productCartPage.productCart();
    }

    @And("Submitted for checkout")
    public void productCheckout() {
        productCheckoutPage = new ProductCheckoutPage();
        productCheckoutPage.productCheckout();
    }

    @Then("User should successfully checkout")
    public void verifySuccessCheckout() {
        String confirmationText = productCheckoutPage.getCheckoutConfirmation();
        then(confirmationText).isEqualTo("Thank you. Your order has been received.");
    }

    @After()
    public void closeBrowser() {
        close();
    }
}
