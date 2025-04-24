package com.ai.holistic.base;

import com.ai.holistic.utils.ExtentReportManager;
import com.aventstack.extentreports.Status;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.lang.reflect.Method;

@Slf4j
public class BaseTest {
    protected WebDriver driver;

    @BeforeSuite(alwaysRun = true)
    public void initializeExtentReport() {
        ExtentReportManager.startTest();  // safe to do once here
        }

    @BeforeMethod(alwaysRun = true)
    @Parameters({"browser"})
    public void setUp(@Optional("chrome") String browser, Method method, Object[] testData) {
        WebDriverFactory.initDriver(browser);
        driver = WebDriverFactory.getDriver();
/* 
// Ensure startTest is called first (initializes the ExtentReports object)
        if (ExtentReportManager.isReportNotInitialized()) {
            ExtentReportManager.startTest(); // Initialize ExtentReports only once if not already initialized
        }
        */
        
        // Capture class name and method name
        String className = this.getClass().getSimpleName();
        String testName =  method.getName();
        // Capture the parameters from the data provider if available
        if (testData != null && testData.length > 0) {
            StringBuilder params = new StringBuilder();
            for (Object param : testData) {
                params.append(param.toString()).append(", ");
            }
            // Remove the trailing comma
            testName += " [" + params.substring(0, params.length() - 2) + "]";
        }
        // Create the test in ExtentReport with the formatted name
        ExtentReportManager.createTest(testName);
        // Assign the class name as a category (Group by class name)
        ExtentReportManager.getTest().assignCategory(className);  // This groups the tests by class name
    }


    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        // Capture the test name with parameters
        String parameterizedTestName = getParameterizedTestName(result);
        ExtentReportManager.endTest(result, driver);
        // Optionally add test name with parameters to the report
        if (parameterizedTestName != null) {
            ExtentReportManager.log(Status.INFO, "Test Parameters: " + parameterizedTestName);
        }
        WebDriverFactory.quitDriver();
    }

    private String getParameterizedTestName(ITestResult result) {
        if (result.getParameters().length > 0) {
            StringBuilder paramStr = new StringBuilder();
            for (Object param : result.getParameters()) {
                paramStr.append(param.toString()).append(", ");
            }
            return result.getMethod().getMethodName() + " [" + paramStr + "]";
        }
        return null;
    }

    @AfterSuite(alwaysRun = true)
        public void flushExtentReport() {
           ExtentReportManager.flushReport();
    }


}

