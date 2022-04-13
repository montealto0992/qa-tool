@FunctionalTest
Feature: Register in practice site
  @Regression
  Scenario: Register Valid Credentials
    Given User is in the registration page
    When User register with valid credentials
    Then User should be able to create account

