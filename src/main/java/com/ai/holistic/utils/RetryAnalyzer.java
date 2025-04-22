package com.ai.holistic.utils;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int count = 0;
    private final int maxTry;

    public RetryAnalyzer() {
        // Check system property first
        String retryCountFromSystem = System.getProperty("retries");
        if (retryCountFromSystem != null) {
            maxTry = Integer.parseInt(retryCountFromSystem);
        } else {
            // Fallback to config.properties
            String retryFromConfig = ConfigReader.getProperty("retry.count");
            maxTry = retryFromConfig != null ? Integer.parseInt(retryFromConfig) : 0; // default to 0
        }
    }

    @Override
    public boolean retry(ITestResult result) {
        if (count < maxTry) {
            count++;
            return true;
        }
        return false;
    }
}
