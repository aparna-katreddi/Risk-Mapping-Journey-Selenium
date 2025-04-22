package com.ai.holistic.utils;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

@Slf4j
public class CommonUtil {

    public static void scrollIntoView(WebDriver driver, WebElement element, boolean highlight) {
        log.info("Scrolls the element : '{}' to the center of the screen",element);
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView({block: 'center'});", element);
        log.info("Highlight the element with red border : '{}' ",element);
        if (highlight) {
            ((JavascriptExecutor) driver)
                    .executeScript("arguments[0].style.border='2px solid red'", element);
        }
    }



}
