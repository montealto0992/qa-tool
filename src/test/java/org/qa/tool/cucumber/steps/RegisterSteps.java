package org.qa.tool.cucumber.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.qa.tool.cucumber.base.BaseTest;
import org.qa.tool.cucumber.pages.LoginPage;
import org.qa.tool.cucumber.pages.util.PropertyUtil;

import static org.assertj.core.api.BDDAssertions.then;

public class RegisterSteps extends BaseTest {

    private LoginPage loginPage;
    private PropertyUtil propertyUtil;


    @Given("User is in the registration page")
    public void navigatePage() {
        loginPage = new LoginPage();
    }
    @When("User register with valid credentials")
    public void register(){
        propertyUtil = new PropertyUtil();
        loginPage.register(propertyUtil.getProperty("username"),propertyUtil.getProperty("password"), propertyUtil.getProperty("email"));
    }
    @Then("User should be able to create account")
    public void verifyRegistration(){
        loginPage.getRegistrationConfirmation();
        propertyUtil = new PropertyUtil();
        loginPage.verifyLogin(propertyUtil.getProperty("username"),propertyUtil.getProperty("password"));
        String confirmationText = loginPage.getLoginConfirmation1();
        then(confirmationText).isEqualTo("Logout");
        close();
    }



}
