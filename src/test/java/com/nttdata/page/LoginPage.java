package com.nttdata.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class LoginPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Elementos de la pantalla de inicio de sesión
    private By txtCorreo = By.id("field-email");
    private By txtPassword = By.id("field-password");
    private By btnIniciarSesion = By.id("submit-login");

    public LoginPage(WebDriver driver) {
        this.driver = driver;

        // Espera explícita para los elementos del login
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Ingresa el correo electrónico
    public void ingresarCorreo(String correo) {
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(txtCorreo)
        ).sendKeys(correo);
    }

    // Ingresa la contraseña
    public void ingresarPassword(String password) {
        driver.findElement(txtPassword).sendKeys(password);
    }

    // Presiona el botón Iniciar sesión
    public void clickIniciarSesion() {
        wait.until(
                ExpectedConditions.elementToBeClickable(btnIniciarSesion)
        ).click();
    }
}