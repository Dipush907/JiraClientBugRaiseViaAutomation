package com.jiraRunner;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;



import io.github.bonigarcia.wdm.WebDriverManager;

public class RaiseTicketInJira_WithStory {

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
		driver.findElement(By.name("password")).sendKeys("DipuBibhu1@");
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

		Thread.sleep(4000);
		driver.findElement(By.xpath("//label[contains(text(),'Issue type')]/following-sibling::div[contains(@class,'css')]")).click();

		List<WebElement> optionsOfIssueType=driver.findElements(By.xpath("//label[contains(text(),'Issue type')]/following-sibling::div[contains(@class,'css')]/descendant::div[contains(@class,'option')]/div/div/div[not(@style)]"));
		for(WebElement w: optionsOfIssueType)
		{
			if(w.getText().equalsIgnoreCase("Story"))
			{
				w.click();
				break;
			}
		}
		driver.findElement(By.name("summary")).sendKeys("NewStoryForAutomation");
		driver.findElement(By.xpath("//div[@aria-label='Main content area']")).sendKeys("This ticket is raised via automation via selenium java");

		WebElement assignee=driver.findElement(By.xpath("//label[text()='Assignee']/following-sibling::div/div/div"));
		jse.executeScript("arguments[0].scrollIntoView(true);", assignee);
		assignee.click();

		Thread.sleep(2000);
		List<WebElement> assigneeDropDownOptions= driver.findElements(By.xpath("//div[contains(@class,'fabric-user-picker__menu-list')]/div"));
		for (int i = 2; i < assigneeDropDownOptions.size(); i++) {
			assigneeDropDownOptions.get(i).click();
		}

		driver.findElement(By.xpath("//div[@data-testid='issue-create.ui.modal.modal-wrapper.modal--footer']/descendant::span[text()='Create']")).click();

		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[@aria-label='Dismiss']")).click();

		List<WebElement> toDOList= driver.findElements(By.xpath("//div[text()='To Do']/ancestor::div[contains(@data-test-id,'platform-board-kit.common')]/parent::div/following-sibling::div/descendant::div[contains(@data-test-id,'platform-card.ui.card.focus-container')]"));
		for (int i = 0; i < toDOList.size(); i++) {
			jse.executeScript("arguments[0].scrollIntoView(true);", toDOList.get(i));
		}
		String text=driver.findElement(By.xpath("(//div[@data-test-id='platform-card.ui.card.focus-container'])[last()]//child::div//following-sibling::div//descendant::span[contains(text(),'RAIS')]")).getText();
		System.out.println(text);

		/*BUG RAISING*/
		Thread.sleep(2000);
		driver.findElement(By.xpath("//span[text()='Create']")).click();
		Thread.sleep(1000);
		driver.findElement(By.xpath("//label[contains(text(),'Issue type')]/following-sibling::div[contains(@class,'css')]/div/div[last()]")).click();
		List<WebElement> optionsOfIssueTypeForBug=driver.findElements(By.xpath("//label[contains(text(),'Issue type')]/following-sibling::div[contains(@class,'css')]/descendant::div[contains(@class,'option')]/div/div/div[not(@style)]"));
		for(WebElement w: optionsOfIssueTypeForBug)
		{
			if(w.getText().equalsIgnoreCase("Bug"))
			{
				w.click();
				break;
			}
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

		Thread.sleep(2000);
		WebElement assigneee=driver.findElement(By.xpath("//label[text()='Assignee']/following-sibling::div/div/div"));
		jse.executeScript("arguments[0].scrollIntoView(true);", assigneee);
		assigneee.click();


		List<WebElement> linkedIssuesDropDowns= driver.findElements(By.xpath("//label[@id='issuelinks-field-label']/parent::div/descendant::div[contains(@class,'container')]"));
		for (int i = 0; i < linkedIssuesDropDowns.size(); i++) {
			if(i==1)
			{
				linkedIssuesDropDowns.get(i).click();
				Thread.sleep(2000);
				driver.findElement(By.xpath("//div[text()='Select Issue']/following-sibling::div/div/input")).sendKeys(text , Keys.ENTER);
			}
		}
	}

}
