package com.ai.holistic.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;


@Slf4j
public class AIRiskManagementPage {

    private WebDriver driver;

    @FindBy(xpath = "//div[@id=\"ai-risk\"]/h2")
    WebElement AI_RISK_SECTION_SELECTOR;
    @FindBy(id = "sensitiveData")
    WebElement SENSITIVE_DATA_SELECTOR;
    @FindBy(id = "riskLevel")
    WebElement RISK_LEVEL_SELECTOR;
    @FindBy(id = "ai-risk-warning")
    WebElement AI_RISK_WARNING_SELECTOR;
    @FindBy(xpath = "//div[@id=\"ai-risk\"]//button")
    WebElement NEXT_BTN;


    public AIRiskManagementPage(WebDriver driver)  {
            this.driver = driver;
            PageFactory.initElements(driver, this);
    }

    public String verifyTitleOnAIRiskManagementPage()
    {
        log.info("Verify Title on AI Risk Management Page");
        return AI_RISK_SECTION_SELECTOR.getText();
    }
    public AIRiskManagementPage  selectSensitiveData(String sensitiveData){
        Select dropdown = new Select(SENSITIVE_DATA_SELECTOR);
        dropdown.selectByVisibleText(sensitiveData);
        return this;
    }
    public AIRiskManagementPage selectRiskLevel(String riskLevel) {
            Select dropdown = new Select(RISK_LEVEL_SELECTOR);
            dropdown.selectByVisibleText(riskLevel);
            return this;
    }

    public DataSecurityPage navigateToDataSecurityPage() {
        NEXT_BTN.click();
        return new DataSecurityPage(driver);
    }
}
