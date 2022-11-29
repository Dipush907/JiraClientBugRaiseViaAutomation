package com.jiraUtils;

import java.util.ArrayList;
import java.util.List;

import net.rcarz.jiraclient.BasicCredentials;
import net.rcarz.jiraclient.Field;
import net.rcarz.jiraclient.Issue;
import net.rcarz.jiraclient.Issue.FluentCreate;
import net.rcarz.jiraclient.IssueLink;
import net.rcarz.jiraclient.IssueType;
import net.rcarz.jiraclient.JiraClient;
import net.rcarz.jiraclient.JiraException;
import net.rcarz.jiraclient.RestClient;

public class JiraServiceProvider {

	public JiraClient jiraClient;
	public String project;
	public Issue newStory;
	
	public JiraServiceProvider( String jiraUrl, String username , String password , String project)
	{
		BasicCredentials creds = new BasicCredentials(username, password);
		jiraClient= new JiraClient(jiraUrl, creds);
		this.project = project;
	}
	
	public String createJiraStory(String issueType , String summary , String description , String reporterName)
	{
		try {
			FluentCreate fluentCreate= jiraClient.createIssue(project, issueType);
			fluentCreate.field(Field.SUMMARY, summary);
			fluentCreate.field(Field.DESCRIPTION, description);
			newStory = fluentCreate.execute();
			System.out.println("New Story is created in Jira : "+ newStory);
		} catch (JiraException e) {
			e.printStackTrace();
		}
		return newStory.toString();
	}
	public void createJiraTicket(String issueType , String summary , String description , String reporterName , String storyValue)
	{
		try {
			FluentCreate fluentCreate= jiraClient.createIssue(project, issueType);
			fluentCreate.field(Field.SUMMARY, summary);
			fluentCreate.field(Field.DESCRIPTION, description);
			Issue newIssue = fluentCreate.execute();
			
			System.out.println("New issue is created in Jira : "+ newIssue);
			newIssue.link(storyValue, "Blocks");
		} catch (JiraException e) {
			e.printStackTrace();
		}
	}
	
	
}
