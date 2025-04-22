package com.ai.holistic.tests;

import com.ai.holistic.base.BaseTest;
import com.ai.holistic.pages.*;
import com.ai.holistic.utils.ConfigReader;
import com.ai.holistic.utils.ExcelUtil;
import com.ai.holistic.utils.RetryAnalyzer;
import lombok.extern.slf4j.Slf4j;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;





@Slf4j
public class AIRiskManagementPageDummyTest extends BaseTest {

    @Test(dataProvider ="getAllRegionValidData",retryAnalyzer = RetryAnalyzer.class)
    public void dummyVerifyRiskMappingJourneyE2eFlow(String name, String org, String email, String region,String sensitiveData,String riskLevel,String securityMeasures,String encryption,String dataResidencyCompliance,String riskAssessment,String audit) {
        log.info("On GeneralInformationPage - Fill out Name , org, email , region & Submit ");
        AIRiskManagementPage aiRiskManagementPage = new GeneralInformationPage(driver)
                .fillGeneralInformation(name,org,email,region)
                .submitGeneralInformation();
        log.info("On AIRiskManagementPage - Fill out sensitive data , risk level,navigate to Data Security Page");
        Assert.assertEquals(aiRiskManagementPage.verifyTitleOnAIRiskManagementPage(),"AI Risk Management");
        DataSecurityPage dataSecurityPage = aiRiskManagementPage.selectSensitiveData(sensitiveData)
                .selectRiskLevel(riskLevel)
                .navigateToDataSecurityPage();
        log.info("On DataSecurityPage - Fill out security measures data, encryption implemented , data residency compliance, navigate to Compliance Measures ");
        CompliancePage compliancePage = dataSecurityPage.fillDataSecurityDetails(securityMeasures,encryption,region,dataResidencyCompliance)
                .navigateToCompliancePage();
        log.info("On ComplianceMeasuresPage - Fill out compliance measures data, riskAssessment and audit submit form to view final summary ");
        SummaryPage summaryPage = compliancePage.completeComplianceMeasures(riskAssessment,audit)
                .navigateToSummaryPage();
        String summaryContent = summaryPage.validateSummary( org, email, region, sensitiveData, riskLevel, securityMeasures, encryption, dataResidencyCompliance, riskAssessment, audit);
        String expectedSummaryContent = "Organization Name: " +org
                +"Email Address: "+email
                +"Region: "+region
                +"Sensitive Data: "+sensitiveData
                +"Risk Level: "+riskLevel
                +"Security Measures: "+securityMeasures
                +"Encryption: "+encryption
                +"Data Residency Compliance: "+dataResidencyCompliance
                +"Risk Assessment: "+riskAssessment
                +"Compliance Audit: "+audit;
        log.info("data residency flga {}",dataResidencyCompliance);
        Assert.assertTrue(summaryContent.contains("Organization Name: " +org),"org mismatch"+org);
        Assert.assertTrue(summaryContent.contains("Email Address: "+email),"email mismatch"+email);
        Assert.assertTrue(summaryContent.contains("Region: "+region),"region mismatch"+region);
        Assert.assertTrue(summaryContent.contains("Sensitive Data: "+sensitiveData),"sensitive data mismatch"+sensitiveData);
        Assert.assertTrue(summaryContent.contains("Security Measures: "+securityMeasures),"security measures mismatch"+securityMeasures);
        Assert.assertTrue(summaryContent.contains("Encryption: "+encryption),"encryption mismatch"+encryption);
        String expectedDataResidencyCompliance =
                (dataResidencyCompliance!= null && !dataResidencyCompliance.trim().isEmpty() ? "Data Residency Compliance: " + dataResidencyCompliance:"Data Residency Compliance:");
        Assert.assertTrue(summaryContent.contains(expectedDataResidencyCompliance),"data residency mismatch"+dataResidencyCompliance);
        Assert.assertTrue(summaryContent.contains("Risk Assessment: "+riskAssessment),"risk assessment mismatch"+riskAssessment);
        Assert.assertFalse(summaryContent.contains("Compliance Audit: "+audit),"compliance audit mismatch"+audit);
    }

    //@DataProvider(parallel = true)
    @DataProvider
    public Object[][] getAllRegionValidData() {
        return ExcelUtil.getData(ConfigReader.getProperty("excel.aiRiskJourney.path"),"allRegionsValidData");
    }
}
