package com.jiraUtils;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

public class ListenerImplementation implements ITestListener {

	public static int count= 0;
	public static String storyValue;
	
//	public void onStart(ITestContext context) {
//		count++;
//	}
	public void onTestStart(ITestResult result) {
		count++;
	}

	public void onTestFailure(ITestResult result) {  
		JiraPolicy jiraPolicy = result.getMethod().getConstructorOrMethod().getMethod().getAnnotation(JiraPolicy.class);
		boolean isTicketReady=jiraPolicy.logTicketReady();
		
		if(isTicketReady)
		{
			//raise a jira ticket
			System.out.println("Is ticket ready for jira "+ isTicketReady);
			JiraServiceProvider jiraServiceProvide= new JiraServiceProvider("https://bibhudatta.atlassian.net", "bibhudattaSahu913@gmail.com", 
										"1Fxd8OwaUO2Cx8F8exy2DFFC",  "RAIS");
			String issueSummary = result.getMethod().getConstructorOrMethod().getMethod().getName()+"Got failed due to some exception";
			String issueDescription = result.getThrowable().getMessage()+"\n";
			
			
			issueDescription.concat(ExceptionUtils.getFullStackTrace(result.getThrowable()));
			if(count==1)
			{
				storyValue=jiraServiceProvide.createJiraStory("Story", issueSummary, issueDescription, "Bibhudatta");
			}
			
			jiraServiceProvide.createJiraTicket("Bug", issueSummary, issueDescription, "Bibhudatta", storyValue);
		}
		
		
	}

	

	
}
