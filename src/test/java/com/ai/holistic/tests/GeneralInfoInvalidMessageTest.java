package com.ai.holistic.tests;

import com.ai.holistic.base.BaseTest;
import com.ai.holistic.base.WebDriverFactory;
import com.ai.holistic.pages.GeneralInformationPage;
import com.ai.holistic.utils.ConfigReader;
import com.ai.holistic.utils.ExcelUtil;
import com.ai.holistic.utils.RetryAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class GeneralInfoInvalidMessageTest extends BaseTest {

    @Test(groups = {"regression"},dataProvider ="generalInfoInvalidTestData",retryAnalyzer = RetryAnalyzer.class)
    public void validateGeneralInfoPageMissingDataErrorMessages(String name,String org,String email,String region) {
        log.info("On GeneralInformationPage - Fill out Name , org, email , region & Submit ");
        String actualWarningMessage = new GeneralInformationPage(driver).fillGeneralInfoWithMissingData(name,org,email,region)
                .validateGeneralInfoWarnings();
        Assert.assertTrue(actualWarningMessage.contains("Please fill out all fields and provide a valid email."));
    }


    @DataProvider
    public Object[][] generalInfoInvalidTestData() {
        return ExcelUtil.getData(ConfigReader.getProperty("excel.aiRiskJourney.path"),"generalnfoInvalidData");
    }
}
