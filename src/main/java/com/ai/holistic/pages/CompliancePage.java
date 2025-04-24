package com.ai.holistic.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

public class CompliancePage {

    private WebDriver driver;

    @FindBy(id = "riskAssessment")
    WebElement RISK_ASSESSMENT_SELECTOR;
    @FindBy(id = "audit")
    WebElement AUDIT_SELECTOR;
    @FindBy(xpath = "//div[@id=\"compliance\"]/button")
    WebElement NEXT_BTN;


    public CompliancePage(WebDriver driver)  {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public CompliancePage completeComplianceMeasures(String riskAssessment,String audit) {
        new Select(RISK_ASSESSMENT_SELECTOR).selectByVisibleText(riskAssessment);
        new Select(AUDIT_SELECTOR).selectByVisibleText(audit);
        return this;
    }

    public SummaryPage navigateToSummaryPage() {
        NEXT_BTN.click();
        return new SummaryPage(driver);
    }
}
