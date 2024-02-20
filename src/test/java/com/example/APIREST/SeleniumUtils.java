package com.example.APIREST;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v91.network.Network;
import org.openqa.selenium.devtools.v91.network.model.Request;
import org.openqa.selenium.devtools.v91.network.model.Response;

public class SeleniumUtils {

	private WebDriver driver;
	private DevTools devTools;

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
        driver.manage().timeouts().implicitlyWait(timeInSeconds, TimeUnit.SECONDS);
    }

    public void explicitWaitAndClick(String xpath, long timeInSeconds) {
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


	public void closeBrowser() {
		if (driver != null) {
			driver.quit();
		}
	}
	
	  

	    public SeleniumDevToolsIni() {
	    	this.initializeDriver();
	        devTools = driver.getDevTools();
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
	            // Selenium does not support making POST requests.
	            // You would need to use another library like Apache HttpClient to make the POST request.
	        }

	        devTools.send(Network.disable());
	    }

	    public void closeBrowser() {
	        driver.quit();
	    }
	}

}


