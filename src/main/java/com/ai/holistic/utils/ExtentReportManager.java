package com.ai.holistic.utils;

import com.aventstack.extentreports.*;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ExtentReportManager {

    private static ExtentReports extent;
    //private static ExtentTest test;
    private static final ThreadLocal<ExtentTest> test = new ThreadLocal<>();


    // Create report instance (called once)
    public static void startTest() {
        if (extent == null) {
            String suiteName = System.getProperty("suiteName", "DefaultSuite");
            String timestamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
            String reportPath = System.getProperty("user.dir") + "/reports/TestReport_"+ suiteName +"_" + timestamp + ".html";

            ExtentSparkReporter spark = new ExtentSparkReporter(reportPath);
            spark.config().setReportName("Automation Test Results");
            spark.config().setDocumentTitle("Execution Report");

            extent = new ExtentReports();
            extent.attachReporter(spark);
            extent.setSystemInfo("QA Engineer", "Aparna Katreddi");
            extent.setSystemInfo("Environment", "QA");
        }
    }

    public static void createTest(String testName) {
        ExtentTest extentTest = extent.createTest(testName);
        test.set(extentTest);
    }

    // Create a test with name and optional description
    public static void createTest(String testName, String description) {
        ExtentTest extentTest = extent.createTest(testName, description);
        test.set(extentTest);
    }

    public static ExtentTest getTest() {
        return test.get();
    }

    public static void log(Status status, String message) {
        getTest().log(status, message);
    }

    // Log result and attach screenshot if failed
    public static void endTest(ITestResult result, WebDriver driver) {
        if (getTest() == null) {
            createTest(result.getMethod().getMethodName());
        }

        switch (result.getStatus()) {
            case ITestResult.FAILURE:
                //getTest().fail(result.getThrowable());
                String screenshotPath = captureScreenshot(driver, result.getMethod().getMethodName());
                if (screenshotPath != null) {
                    try {
                       // getTest().addScreenCaptureFromPath(screenshotPath);
                       // getTest().fail(result.getThrowable(),
                              //  MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                        // Log the failure WITH the screenshot — only once
                        getTest().fail("Test failed: " + result.getThrowable().getMessage(),
                                MediaEntityBuilder.createScreenCaptureFromPath(screenshotPath).build());
                    } catch (Exception e) {
                        //getTest().warning("Failed to attach screenshot.");
                        getTest().fail(result.getThrowable());
                    }
                }
                break;
            case ITestResult.SUCCESS:
                getTest().pass("Test passed");
                break;
            case ITestResult.SKIP:
                getTest().skip("Test skipped: " + result.getThrowable());
                break;
        }

        extent.flush();
    }





    private static String captureScreenshot(WebDriver driver, String testName) {
        try {
            File src = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
            String dest = System.getProperty("user.dir") + "/screenshots/" + testName + "_" + timestamp + ".png";

            File targetFile = new File(dest);
            targetFile.getParentFile().mkdirs(); // Create dirs if not exist
            Files.copy(src.toPath(), targetFile.toPath());

            return dest;
        } catch (IOException e) {
            ExtentReportManager.log(Status.WARNING, "Failed to capture screenshot: " + e.getMessage());
            return null;
        }
    }


    public static boolean isReportNotInitialized() {
        return extent == null;
    }
}

