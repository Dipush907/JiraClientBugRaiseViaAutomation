package com.jiraRunner;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.Assert;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import com.jiraUtils.JiraPolicy;

import io.github.bonigarcia.wdm.WebDriverManager;

public class RaiseTicketWithJiraClient {

	public WebDriver driver;
	
	@BeforeSuite
	public void BC()
	{
		ChromeOptions op= new ChromeOptions();
		op.addArguments("--headless");

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver(op);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.get("https://www.google.com");
	}

	@JiraPolicy(logTicketReady = true)
	@Test (invocationCount = 1)
	public void mainRunnner() throws Throwable
	{
		driver.findElement(By.xpath("//input[@name='qs']")).sendKeys("selenium automation", Keys.ENTER);
	}
	
	@JiraPolicy(logTicketReady = true)
	@Test (invocationCount = 1)
	public void mainRunnnerr() throws Throwable
	{
		driver.findElement(By.xpath("//input[@name='qs']")).sendKeys("selenium automation", Keys.ENTER);
	}
}
