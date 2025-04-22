package com.ai.holistic.pages;

import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

@Slf4j
public class DataSecurityPage {

    WebDriver driver;

    @FindBy(id = "securityMeasures")
    WebElement SECURITY_MEASURES_SELECTOR;
    @FindBy(id = "encryption")
    WebElement ENCRYPTION_SELECTOR;
    @FindBy(id = "dataResidencyCompliance")
    WebElement DATA_RESIDENCY_COMPLIANCE_SELECTOR;
    @FindBy(xpath = "//div[@id=\"data-security\"]/button")
    WebElement NEXT_BTN;


    public DataSecurityPage(WebDriver driver)  {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public DataSecurityPage fillDataSecurityDetails(String securityMeasures, String encryption,String region, String dataResidencyCompliance){
        SECURITY_MEASURES_SELECTOR.sendKeys(securityMeasures );
        Select dropdown = new Select(ENCRYPTION_SELECTOR);
        dropdown.selectByVisibleText(encryption);
        if (region.equals( "EU")) {
            Select dataResidencyDropdown = new Select(DATA_RESIDENCY_COMPLIANCE_SELECTOR);
            dataResidencyDropdown.selectByVisibleText(dataResidencyCompliance);
        }
        return this;
    }

    public CompliancePage navigateToCompliancePage() {
        NEXT_BTN.click();
       return new CompliancePage(driver);
    }
}
