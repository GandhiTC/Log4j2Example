package com.github.GandhiTC.java.Log4j2Example.tests;



import java.io.File;
import java.util.Enumeration;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeDriverService;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.ie.InternetExplorerDriverService;
import org.openqa.selenium.interactions.Actions;



public class RunAsJavaAppTest
{
	private static Logger		log		= LogManager.getLogger(RunAsJavaAppTest.class.getName());
	private static WebDriver	driver;


	public static void main(String[] browser)
	{
//		seLoggingtLevel(Level.SEVERE);
		seLoggingtLevel(Level.OFF);
		
		log.debug("Setting up web driver");
		if(browser.length > 0)
		{
			if(browser[0].toLowerCase().contains("firefox"))
			{
				System.setProperty("webdriver.gecko.driver", "src/test/resources/drivers/geckodriver.exe");
				System.setProperty(FirefoxDriver.SystemProperty.BROWSER_LOGFILE, System.getProperty("java.io.tmpdir") + "geckodriverlogs.txt");
				
				if(browser[0].toLowerCase().contains("headless"))
				{
					FirefoxOptions options = new FirefoxOptions();
					options.setHeadless(true);
					driver = new FirefoxDriver(options);
				}
				else
				{
					driver = new FirefoxDriver();
				}
			}
			else if(browser[0].toLowerCase().contains("ie"))
			{
				System.setProperty("webdriver.ie.driver", "src/test/resources/drivers/IEDriverServer.exe");
				InternetExplorerDriverService ieDriverService = new InternetExplorerDriverService.Builder().withSilent(true).build();
				driver = new InternetExplorerDriver(ieDriverService);
			}
			else if(browser[0].toLowerCase().contains("edge"))
			{
				System.setProperty("webdriver.edge.driver", "src/test/resources/drivers/MicrosoftWebDriver.exe");
				EdgeDriverService edgeDriverService = new EdgeDriverService.Builder().withLogFile(new File(System.getProperty("java.io.tmpdir") + "edgedriverlogs.txt")).build();
				driver = new EdgeDriver(edgeDriverService);
			}
			else
			{
				System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
				System.setProperty("webdriver.chrome.args", "--disable-logging");
				System.setProperty("webdriver.chrome.silentOutput", "true");
				
				if(browser[0].toLowerCase().contains("headless"))
				{
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.setHeadless(true);
					driver = new ChromeDriver(chromeOptions);
				}
				else
				{
					driver = new ChromeDriver();
				}
			}
		}
		else
		{
			System.setProperty("webdriver.chrome.driver", "src/test/resources/drivers/chromedriver.exe");
			System.setProperty("webdriver.chrome.args", "--disable-logging");
			System.setProperty("webdriver.chrome.silentOutput", "true");
			
			ChromeOptions chromeOptions = new ChromeOptions();
			chromeOptions.setHeadless(true);
			driver = new ChromeDriver(chromeOptions);
		}

		log.info((String)((JavascriptExecutor)driver).executeScript("return navigator.userAgent;"));
		
		driver.manage().window().maximize();
		log.info("Window Maximized");
		
		log.debug("Now hitting Amazon server");
		driver.get("https://www.amazon.com/");
		log.info("Landed on amazon home page");
		
		log.debug("Navigating to http://jqueryui.com/demos/droppable/");
		driver.get("http://jqueryui.com/demos/droppable/");
		
		String iFrameCount = "iframe count = " + driver.findElements(By.tagName("iframe")).size();
		log.info(iFrameCount);
		
		log.debug("Switching to iframe");
		try
		{
			driver.switchTo().frame(2); // frame(2) to fail test frame(0) to pass
			log.info("Successfully switched to iframe");
		}
		catch(Exception e)
		{
			log.fatal("Cannot identify the iframe\n\n");
			driver.quit();
			driver = null;
			System.exit(-1);
		}

		log.debug("Identifying drag/drop objects");
		WebElement	draggable	= driver.findElement(By.id("draggable"));
		WebElement	droppable	= driver.findElement(By.id("droppable"));
		
		new Actions(driver).dragAndDrop(draggable, droppable).build().perform();
		log.info("Drag and drop action performed successfully");
		
		driver.switchTo().defaultContent();
		driver.quit();
		log.info("Test Completed\n\n");
	}


	//	https://www.logicbig.com/tutorials/core-java-tutorial/logging/levels.html
	private static void seLoggingtLevel(Level targetLevel)
	{
		Enumeration<String> loggerNames = java.util.logging.LogManager.getLogManager().getLoggerNames();
		
		while(loggerNames.hasMoreElements())
		{
			String loggerName = loggerNames.nextElement();
			
			java.util.logging.Logger root = java.util.logging.Logger.getLogger(loggerName);
			root.setLevel(targetLevel);

			for(Handler handler : root.getHandlers())
			{
				handler.setLevel(targetLevel);
			}
		}
		
//		java.util.logging.Logger root = java.util.logging.Logger.getLogger("");
//		root.setLevel(targetLevel);
//
//		for(Handler handler : root.getHandlers())
//		{
//			handler.setLevel(targetLevel);
//		}
	}
}
