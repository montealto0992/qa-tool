package org.qa.tool.cucumber.pages.util;

import com.browserstack.local.Local;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;
import junit.framework.TestResult;
import net.minidev.json.parser.ParseException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerOptions;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariOptions;


import java.io.FileNotFoundException;
import java.io.FileReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public final class Driver {

    private final static Driver browser = new Driver();
    private WebDriver webDriver;
    private URL url;
    private DesiredCapabilities capabilities;
    private MutableCapabilities browserOptions;

    private Driver() {
    }

    public static Driver getInstance() {
        return browser;
    }

    public WebDriver getWebDriver()  {
        if (this.webDriver != null)
            return this.webDriver;
        String remote =  System.getProperty("REMOTE");
        String browser = DriverProperty.getProperty("browser");
        String server = DriverProperty.getProperty("server");

        if("browserstack".equalsIgnoreCase(remote)){
            setBrowserStack();

            this.webDriver = new Augmenter().augment(
                    new RemoteWebDriver(url, capabilities));
        }else if("saucelabs".equalsIgnoreCase(remote)){
            setSaucelabs();



            this.webDriver = new Augmenter().augment(
                    new RemoteWebDriver(url, browserOptions));

            TestResult result = new TestResult();
            ((JavascriptExecutor) webDriver).executeScript("sauce:job-result=" + (result.wasSuccessful() ? "passed" : "failed"));

        }
        else if("grid-local".equalsIgnoreCase(browser)){
            try {
                EdgeOptions edgeOptions =  new EdgeOptions();
                ChromeOptions chromeOptions = new ChromeOptions();
                this.webDriver = new Augmenter().augment(
                            new RemoteWebDriver(new URL(server ), edgeOptions));
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        } else {
            this.webDriver = WebDriverManager.getInstance(browser).create();
        }
        return this.webDriver;
    }


    private void setSaucelabs()   {

        String username = System.getProperty("USERNAME");
        String accessKey = System.getProperty("ACCESS_KEY");


        MutableCapabilities sauceOptions = new MutableCapabilities();
        sauceOptions.setCapability("name", "successfully login");
        //sauceOptions.setCapability("tags", "tag1");
        //sauceOptions.setCapability("build", "build-1");
        sauceOptions.setCapability("username", username);
        sauceOptions.setCapability("accessKey", accessKey);

        String browserName = System.getProperty("BROWSER_NAME");
        String browserVersion = System.getProperty("BROWSER_VERSION");
        String platformName = System.getProperty("PLATFORM_NAME");

        browserOptions = new MutableCapabilities();

        switch (browserName) {
            case "SAFARI": {
                browserOptions = new SafariOptions();
                browserOptions.setCapability("platformName", platformName);
                browserOptions.setCapability("browserVersion",browserVersion);
                break;
            }
            case "FIREFOX": {
                browserOptions = new FirefoxOptions();
                browserOptions.setCapability("platformName",platformName);
                browserOptions.setCapability("browserVersion",browserVersion);
                break;
            }
            case "IE": {
                browserOptions = new InternetExplorerOptions();
                browserOptions.setCapability("platformName",platformName);
                browserOptions.setCapability("browserVersion",browserVersion);
                break;
            }
            case "EDGE": {
                browserOptions = new EdgeOptions();
                browserOptions.setCapability("platformName",platformName);
                browserOptions.setCapability("browserVersion",browserVersion);
                break;
            }
            case  "CHROME": {
                browserOptions = new ChromeOptions();
                browserOptions.setCapability("platformName",platformName);
                browserOptions.setCapability("browserVersion",browserVersion);
                break;
            }
        }

        browserOptions.setCapability("sauce:options", sauceOptions);

        try {
            url = new URL("https://" + username + ":" + accessKey + "@ondemand.eu-central-1.saucelabs.com:443/wd/hub");
            System.out.println(url.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public void setBrowserStack() {

        JSONParser parser = new JSONParser();
        JSONObject config = null;
        JavascriptExecutor jse = (JavascriptExecutor)webDriver;
        try {

            config = (JSONObject) parser.parse(new FileReader("src/test/resources/conf/suite.conf.json"));
            JSONObject envs = (JSONObject) config.get("environments");

            capabilities = new DesiredCapabilities();

            setEnvCapabilities(envs, capabilities);
            setCommonCapabilities(config, capabilities);

            String username = System.getProperty("USERNAME");
            String accessKey = System.getProperty("ACCESS_KEY");

            setLocal(capabilities, accessKey);

           url = new URL("http://" + username + ":" + accessKey + "@" + config.get("server") + "/wd/hub");

            String title = webDriver.getTitle();

            if (webDriver.getTitle().equals(title)) {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\": \"passed\", \"reason\": \"Title matched!\"}}");
            }
            else {
                jse.executeScript("browserstack_executor: {\"action\": \"setSessionStatus\", \"arguments\": {\"status\":\"failed\", \"reason\": \"Title not matched\"}}");
            }
        } catch (ParseException | FileNotFoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    private void setLocal(DesiredCapabilities capabilities, String accessKey) throws Exception {
        Local l;
        if (capabilities.getCapability("browserstack.local") != null
                && capabilities.getCapability("browserstack.local") == "true") {
            l = new Local();
            Map<String, String> options = new HashMap<String, String>();
            options.put("key", accessKey);
            l.start(options);
        }
    }

    private void setCommonCapabilities(JSONObject config, DesiredCapabilities capabilities) {
        Iterator it;

        Map<String, String> commonCapabilities = (Map<String, String>) config.get("capabilities");
        it = commonCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (capabilities.getCapability(pair.getKey().toString()) == null) {
                capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
            }
        }
    }

    private void setEnvCapabilities(JSONObject envs, DesiredCapabilities capabilities) {
        Map<String, String> envCapabilities = (Map<String, String>) envs.get("chrome");
        Iterator it = envCapabilities.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }
        Map<String, String> envCapabilities1 = (Map<String, String>) envs.get("edge");
        Iterator it1 = envCapabilities1.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it1.next();
            capabilities.setCapability(pair.getKey().toString(), pair.getValue().toString());
        }
    }
}
