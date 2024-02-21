package com.example.APIREST;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class StepDefinition {
	private String apiEndpoint;
	private WebDriver driver;

	@Given("que la API está en {string}")
	public void queLaAPIEstáEn(String apiEndpoint) {
		this.apiEndpoint = apiEndpoint;
		System.setProperty("webdriver.gecko.driver", "path/to/geckodriver");
		driver = new FirefoxDriver();
	}

	@When("envío una solicitud GET a la API")
	public void envíoUnaSolicitudGETALaAPI() {
		driver.get(apiEndpoint);
	}

	@Then("debería recibir una respuesta de la API")
	public void deberíaRecibirUnaRespuestaDeLaAPI() {
		String response = driver.getPageSource();
		System.out.println("Respuesta de la API: " + response);
		driver.quit();
	}
}
