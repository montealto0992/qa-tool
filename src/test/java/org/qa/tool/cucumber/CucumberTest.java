
package org.qa.tool.cucumber;


import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;
import org.testng.annotations.DataProvider;


//

@CucumberOptions(
        features = "classpath:features/login/login.feature",
        glue = {"org.qa.tool.cucumber.steps" })

public class CucumberTest extends AbstractTestNGCucumberTests {
//    @Override
//    @DataProvider(parallel = true)
//    public Object[][] scenarios() {
//        return super.scenarios();
//    }
}
