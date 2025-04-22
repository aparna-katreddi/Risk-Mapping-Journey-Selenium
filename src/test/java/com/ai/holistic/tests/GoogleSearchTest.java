package com.ai.holistic.tests;

import com.ai.holistic.base.BaseTest;
import com.ai.holistic.pages.GoogleHomePage;
import com.ai.holistic.utils.ConfigReader;
import com.ai.holistic.utils.ExcelUtil;
import com.ai.holistic.utils.RetryAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

@Slf4j
public class GoogleSearchTest extends BaseTest {

    @Test(dataProvider = "getSearchData", retryAnalyzer = RetryAnalyzer.class)
    public void googleSearchTest(String keyword) {
        GoogleHomePage homePage = new GoogleHomePage(driver);
        log.info("Google Home Page - Type Keyword {}",keyword);
        homePage.search(keyword);
        Assert.assertTrue(driver.getCurrentUrl().contains("google"));
    }

    //@DataProvider(parallel = true)
    @DataProvider
    public Object[][] getSearchData() {
        return ExcelUtil.getData(ConfigReader.getProperty("excel.path"),"SearchKeywords");
    }
}
