package com.jiraRunner;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Iterator;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;


import io.github.bonigarcia.wdm.WebDriverManager;

public class RaiseTicketInJira {

	@Test
	public void raiseTicketTest() throws Throwable
	{
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();

		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
		driver.get("https://id.atlassian.com/login");
		JavascriptExecutor jse = (JavascriptExecutor) driver;

		driver.findElement(By.name("username")).sendKeys("bibhudattasahu913@gmail.com");
		driver.findElement(By.xpath("//span[text()='Continue']")).click();
		driver.findElement(By.name("password")).sendKeys("DipuBibhu1");
		Thread.sleep(1000);
		driver.findElement(By.xpath("//span[text()='Log in']")).click();

		driver.findElement(By.xpath("//div[text()='Jira Software']")).click();

		String projName= driver.findElement(By.xpath("//tbody/tr/td[2]/a/div/span")).getText();

		if(!projName.equals("RaiseTicketViaScript"))
		{
			driver.findElement(By.xpath("//span[text()='Create project']")).click();
		}
		else
		{
			driver.findElement(By.xpath("//tbody/tr/td[2]/a/div/span")).click();
		}
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Create']")).click();
		driver.findElement(By.xpath("//label[contains(text(),'Issue type')]/following-sibling::div[contains(@class,'css')]")).click();
		Thread.sleep(1000);
		List<WebElement> allListOptions= driver.findElements(By.xpath("//div[@id='issue-create.ui.modal.create-form.type-picker.issue-type-select']/descendant::div[contains(@id,'react-select-4-option')]"));
		List<WebElement> allText= driver.findElements(By.xpath("//div[@id='issue-create.ui.modal.create-form.type-picker.issue-type-select']/descendant::div[contains(@class,'singleValue')]/div/div/div"));
		for(WebElement w: allText)
		{
			System.out.println(w.getText());
			if(w.getText().equalsIgnoreCase("Bug")) {
				System.out.println("do nothing");
			}else
			{
				for (int i = 0; i < allListOptions.size(); i++) {
					if (allListOptions.get(i).getText().equalsIgnoreCase("Bug")) {
						allListOptions.get(i).click();
						break;
					}
				}
			}break;
		}
		
		driver.findElement(By.name("summary")).sendKeys("This ticket is raised via automation");
		driver.findElement(By.xpath("//div[@aria-label='Main content area']")).sendKeys("This is a bug raised via selenium automation");
		
		driver.findElement(By.xpath("//span[text()='browse']")).click();
		StringSelection s= new StringSelection("C:\\Users\\Bidhudatta\\Pictures\\Screenshots\\Screenshot (65).png");
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(s, null);

		Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        robot.delay(250);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_V);
        robot.keyRelease(KeyEvent.VK_CONTROL);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.delay(150);
        robot.keyRelease(KeyEvent.VK_ENTER);
        
        WebElement attachmentName=driver.findElement(By.xpath("//div[@data-testid='media-card-file-name']"));
        if(attachmentName.isDisplayed())
        {
        	WebElement assignee=driver.findElement(By.xpath("//label[text()='Assignee']/following-sibling::div/div/div"));
        	jse.executeScript("arguments[0].scrollIntoView(true);", assignee);
        	assignee.click();
        }
        
		List<WebElement> linkedIssuesDropDowns= driver.findElements(By.xpath("//label[@id='issuelinks-field-label']/parent::div/descendant::div[contains(@class,'container')]"));
		for (int i = 0; i < linkedIssuesDropDowns.size(); i++) {
			if(i==1)
			{
				linkedIssuesDropDowns.get(i).click();
			}
		}
		List<WebElement> allIssues= driver.findElements(By.xpath("//div[text()='Open Issues']/following-sibling::div/div/span/span"));
		for(WebElement w: allIssues)
		{
			if(w.getText().equalsIgnoreCase("RAIS-2 bug1"))
			{
				w.click();
				break;
			}
		}
		
		List<WebElement> assigneeDropdown= driver.findElements(By.xpath("//label[@id='assignee-field-label']/parent::div/descendant::div/div[contains(@class,'container')]"));
		for(WebElement w: assigneeDropdown)
		{
			if(w.isEnabled())
			{
				w.click();
				break;
			}
		}
		List<WebElement> assigneeDropDownOptions= driver.findElements(By.xpath("//div[contains(@class,'fabric-user-picker__menu-list')]/div"));
		for (int i = 2; i < assigneeDropDownOptions.size(); i++) {
			assigneeDropDownOptions.get(i).click();
			break;
		}
		Thread.sleep(4000);
		driver.findElement(By.xpath("//div[@data-testid='issue-create.ui.modal.modal-wrapper.modal--footer']/descendant::span[text()='Create']")).click();
	}
}
