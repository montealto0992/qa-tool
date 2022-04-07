
package org.qa.tool.cucumber;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

//@org.testng.annotations.Parameters(value = { "config", "environment" })
@CucumberOptions(
        features = "classpath:features",
        glue = {"org.qa.tool.cucumber.steps" })
public class CucumberTest extends AbstractTestNGCucumberTests {

}
