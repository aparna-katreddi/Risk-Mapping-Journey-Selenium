package com.ai.holistic.base;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import io.github.bonigarcia.wdm.WebDriverManager;
import java.time.Duration;
import java.util.UUID;

@Slf4j
public class WebDriverFactory {
    // Thread-safe driver
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    // Create driver and set to ThreadLocal
    public static void initDriver(String browser) {
        WebDriver webDriver;

        switch (browser.toLowerCase()) {
            case "firefox":
                WebDriverManager.firefoxdriver().setup();
                webDriver = createFirefoxDriver();
                break;
            case "edge":
                WebDriverManager.edgedriver().setup();
                webDriver = new EdgeDriver();
                break;
            case "chrome":
            default:
                WebDriverManager.chromedriver().setup();
                //webDriver = new ChromeDriver();
                webDriver = createChromeDriver();
                break;
        }

        webDriver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
       // webDriver.manage().window().maximize();

        // Set the driver for the current thread
        driver.set(webDriver);
    }

    // Get driver for current thread
    public static WebDriver getDriver() {
        return driver.get();
    }

    // Quit and clean up
    public static void quitDriver() {
        WebDriver currentDriver = driver.get();
        if (currentDriver != null) {
            try {
                currentDriver.close();
                currentDriver.quit(); // Attempt to quit the WebDriver session
            } catch (Exception e) {
                log.error("Error quitting WebDriver: {}", e.getMessage());
            } finally {
                driver.remove(); // Remove the thread-local reference
            }
        }
    }

    public static WebDriver createChromeDriver() {
        ChromeOptions options = new ChromeOptions();
        // Create a temp dir for user-data-dir
        String userDataDir = System.getProperty("java.io.tmpdir") + "/chrome-profile-" + UUID.randomUUID();
        options.addArguments("--user-data-dir=" + userDataDir);
        boolean isCI = System.getenv("CI") != null;
        if (isCI) {
            // Headless mode for CI
            options.addArguments("--headless=new"); // For Chrome 109+
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--disable-gpu"); // Extra safe
        } else {
            // Headed mode for local runs — optional tweaks
            options.addArguments("--start-maximized"); // Optional: Maximize window
            log.info("Running in local mode with full Chrome UI");
        }
        return new ChromeDriver(options);
    }

        public static WebDriver createFirefoxDriver() {
        FirefoxOptions options = new FirefoxOptions();

        if (Boolean.parseBoolean(System.getenv("CI"))) {
            options.addArguments("-headless");
        }

        // Optional: Customize profile if needed
        // FirefoxProfile profile = new FirefoxProfile();
        // profile.setPreference("some.preference", true);
        // options.setProfile(profile);

        return new FirefoxDriver(options);
    }

}
