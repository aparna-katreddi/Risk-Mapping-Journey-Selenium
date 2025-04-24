package com.ai.holistic.listeners;

import org.testng.IMethodInstance;
import org.testng.IMethodInterceptor;
import org.testng.ITestContext;

import java.util.ArrayList;
import java.util.List;

public class FilterGroupsListener implements IMethodInterceptor {
    @Override
    public List<IMethodInstance> intercept(List<IMethodInstance> methods, ITestContext context) {
        String group = System.getProperty("groups"); // read from -Dgroups
        if (group == null || group.isEmpty()) return methods; // no filter, run all
        List<IMethodInstance> filtered = new ArrayList<>();
        for (IMethodInstance method : methods) {
            String[] groups = method.getMethod().getGroups();
            for (String g : groups) {
                if (g.equalsIgnoreCase(group)) {
                    filtered.add(method);
                    break;
                }
            }
        }
        return filtered;
    }
}

