@FunctionalTest
Feature: Checkout

  @Regression
  Scenario: Registered User Successful Checkout
    Given Registered User is login
    When Product is added in the cart
    And Submitted for checkout
    Then User should successfully checkout