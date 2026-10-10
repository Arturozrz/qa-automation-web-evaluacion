package com.nttdata.core;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.Map;

public class DriverManager {
    private static WebDriver driver;
    private static Scenario scenario;

    public static WebDriver getDriver() {
        return driver;
    }

    @Before(order = 0)
    public void setUp() {

        // Detecta el sistema operativo para seleccionar ChromeDriver
        String os = System.getProperty("os.name").toLowerCase();

        if (os.contains("win")) {
            System.setProperty(
                    "webdriver.chrome.driver",
                    "drivers\\chromedriver.exe"
            );
        } else if (os.contains("mac")) {
            System.setProperty(
                    "webdriver.chrome.driver",
                    "drivers/chromedriver"
            );
        } else {
            throw new IllegalStateException(
                    "Unsupported operating system: " + os
            );
        }

        System.setProperty(
                "webdriver.http.factory",
                "jdk-http-client"
        );

        // Configuración para evitar avisos del gestor de contraseñas
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);

        // Abre Chrome con las opciones configuradas
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();
    }

    @Before(order = 1)
    public void setScenario(Scenario scenario) {
        this.scenario = scenario;
    }

    @After
    public void quitDriver() {
        // Si el escenario falla, guarda una captura como evidencia
        if (scenario.isFailed()) {
            screenShot();
        }

        driver.quit();
    }

    public static void screenShot() {
        byte[] evidencia = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
        scenario.attach(evidencia, "image/png", "evidencias");
    }

    public static void scrollDown(){
        JavascriptExecutor js = (JavascriptExecutor) driver;
        // This  will scroll down the page by  1000 pixel vertical
        js.executeScript("window.scrollBy(0,1000)");
    }
    public static void esperaImplicita(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
