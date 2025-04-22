package com.ai.holistic.pages;

import com.ai.holistic.utils.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Slf4j
public class GoogleHomePage {
    WebDriver driver;

    @FindBy(name = "q")
    WebElement searchBox;

    public GoogleHomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
        log.info("Navigate to URL:{}",ConfigReader.getProperty("base.url"));
        driver.get(ConfigReader.getProperty("base.url"));
    }

    public void search(String keyword) {
        searchBox.sendKeys(keyword);
        log.info("Entered Keyword:{}",keyword);
        searchBox.submit();
        log.info("Click Submit");
    }
}
