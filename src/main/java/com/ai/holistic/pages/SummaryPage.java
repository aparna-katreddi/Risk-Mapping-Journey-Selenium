package com.ai.holistic.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

@Slf4j
public class SummaryPage {

    private WebDriver driver;

        @FindBy(id = "summaryContent")
        WebElement SUMMARY_CONTENT;

        public SummaryPage(WebDriver driver)  {
            this.driver = driver;
            PageFactory.initElements(driver, this);
        }

    public String validateSummary(String organization,String email,String region,String sensitiveData,String riskLevel,String securityMeasures,String encryption,String dataResidencyCompliance,String riskAssessment,String audit) {
        String summaryContent = SUMMARY_CONTENT.getText();
        log.info(SUMMARY_CONTENT.getText());

   return summaryContent;
    }
}
