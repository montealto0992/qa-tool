
package org.qa.tool.cucumber;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;


@CucumberOptions(
        features = "classpath:features/",
        glue = {"org.qa.tool.cucumber.steps" },
        plugin = { "pretty","html:cucumber-result/cucumber", "json:cucumber-result/cucumber.json" })


public class CucumberTest extends AbstractTestNGCucumberTests {

}
