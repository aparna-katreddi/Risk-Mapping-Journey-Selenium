package com.ai.holistic.listeners;

import org.testng.ISuite;
import org.testng.ISuiteListener;

public class SuiteListener implements ISuiteListener {
    @Override
    public void onStart(ISuite suite) {
        System.setProperty("suiteName", suite.getName()); // make it available globally
    }

    @Override
    public void onFinish(ISuite suite) {
        // optional clean up
    }
}

