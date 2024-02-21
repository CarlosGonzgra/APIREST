package com.example.APIREST;

import java.time.Duration;
import java.util.Optional;
//import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v111.network.Network;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class SeleniumUtils {

	public WebDriver driver;
	private DevTools devTools;
	private String lastResponse;

	public void initializeDriver() {
		System.setProperty("webdriver.chrome.driver", "path/to/chromedriver");
		driver = new ChromeDriver();
	}

	public void navigateToUrl(String url) {
		if (driver != null) {
			driver.get(url);
		}
	}
	
	public void clickElementByXpath(String xpath) {
        WebElement element = driver.findElement(By.xpath(xpath));
        element.click();
    }

    public void setImplicitWait(long timeInSeconds) {
      //  driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
    }

    public void explicitWaitAndClick(String xpath, Duration timeInSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSeconds);
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
        element.click();
    }

    public void captureResponse() {
         lastResponse = driver.getPageSource();
    }

    public String getLastResponse() {
        return lastResponse;
    } 

	    public void SeleniumDevToolsIni() {
	    	this.initializeDriver();
	        devTools = ((HasDevTools) driver).getDevTools();
	        devTools.createSession();
	    }

	    public void captureRequestAndResponse(String url, String method, String requestBody) {
	        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));

	        devTools.addListener(Network.requestWillBeSent(), request -> {
	            if (request.getRequest().getUrl().equals(url) && request.getRequest().getMethod().equals(method)) {
	                System.out.println("Request: " + request.getRequest().getUrl());
	            }
	        });

	        devTools.addListener(Network.responseReceived(), response -> {
	            if (response.getResponse().getUrl().equals(url)) {
	                System.out.println("Response: " + response.getResponse().getStatus());
	                if (response.getResponse().getStatus() >= 200 && response.getResponse().getStatus() < 300) {
	                    System.out.println("Request approved with status code: " + response.getResponse().getStatus());
	                } else {
	                    System.out.println("Request denied with status code: " + response.getResponse().getStatus());
	                }
	            }
	        });

	        if (method.equalsIgnoreCase("GET")) {
	            driver.get(url);
	        } else if (method.equalsIgnoreCase("POST")) {
	              }

	        devTools.send(Network.disable());
	    }

	    public void closeBrowser() {
	        driver.quit();
	    }
	}



