package com.ai.holistic.pages;

import com.ai.holistic.utils.ConfigReader;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;

import java.io.File;
import java.net.MalformedURLException;

@Slf4j
public class GeneralInformationPage {

    private WebDriver driver;


    @FindBy(id = "deploymentName")
    WebElement DEPLOYMENT_NAME_SELECTOR;
    @FindBy(id = "organizationName")
    WebElement ORGANIZATION_NAME_SELECTOR;
    @FindBy(id = "emailAddress")
    WebElement EMAIL_ADDRESS_SELECTOR;
    @FindBy(id = "deploymentRegion")
    WebElement DEPLOYMENT_REGION_SELECTOR;
    @FindBy(xpath="//button[@onclick='validateGeneralInfo()']")
    WebElement NEXT_BTN;
    @FindBy(id = "general-info")
    WebElement GENERAL_INFO_WARNING;

    public GeneralInformationPage(WebDriver driver)  {
        try {
            this.driver = driver;
            PageFactory.initElements(driver, this);
            log.info("Navigate to URL:{}", ConfigReader.getProperty("base.url"));
            File htmlFile = new File(ConfigReader.getProperty("base.url"));
            String filePath = htmlFile.toURI().toURL().toString();  // Converts to a file URL
            driver.get(filePath);
        }
        catch(MalformedURLException e)
        {
            log.info("MalformedURLException:{}", e.toString());
        }
    }

    public GeneralInformationPage fillGeneralInformation(String deploymentName,String orgName,String email,String region)
    {
        log.info("Enter Deployment Name :{}", deploymentName);
        DEPLOYMENT_NAME_SELECTOR.sendKeys(deploymentName);
        log.info("Enter Org Name :{}", orgName);
        ORGANIZATION_NAME_SELECTOR.sendKeys(orgName);
        log.info("Enter Email Address :{}", email);
        EMAIL_ADDRESS_SELECTOR.sendKeys(email);
        log.info("Select region :{}", region);
        Select dropdown = new Select(DEPLOYMENT_REGION_SELECTOR);
        dropdown.selectByVisibleText(region);
        return this;
    }

    public AIRiskManagementPage submitGeneralInformation() {
        log.info("Click Next ");
        NEXT_BTN.click();
        return new AIRiskManagementPage(driver);
    }

     public GeneralInformationPage fillGeneralInfoWithMissingData(String name,String org,String email,String region)
    {
        DEPLOYMENT_NAME_SELECTOR.sendKeys(name );
        ORGANIZATION_NAME_SELECTOR.sendKeys(org);
        EMAIL_ADDRESS_SELECTOR.sendKeys(email);
        new Select(DEPLOYMENT_REGION_SELECTOR).selectByVisibleText(region);
        NEXT_BTN.click();
        return this;
    }

    public String  validateGeneralInfoWarnings()
    {
       return  GENERAL_INFO_WARNING.getText();
    }

}

