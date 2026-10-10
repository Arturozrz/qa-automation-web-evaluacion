package com.nttdata.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.WebElement;

import java.time.Duration;

public class StorePage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Enlace para ir a la pantalla de inicio de sesión
    private By linkIniciarSesion =
            By.cssSelector("a[title='Acceda a su cuenta de cliente']");

    // Enlace de la cuenta que aparece cuando el usuario está autenticado
    private By linkCuenta =
            By.cssSelector("a[title='Ver mi cuenta de cliente']");

    // Categoría Clothes del menú principal
    private By menuClothes =
            By.cssSelector("#category-3 > a");

    // Subcategoría Men
    private By menuMen =
            By.cssSelector("#category-4 > a");

    // Primer producto mostrado en la categoría
    private By primerProducto =
            By.cssSelector(".product-title a");

    // Precio actual del producto
    private By precioActual =
            By.cssSelector(".current-price-value");

    // Botón para aumentar la cantidad del producto
    private By btnAumentarCantidad =
            By.cssSelector("button.bootstrap-touchspin-up");

    // Botón para agregar el producto al carrito
    private By btnAgregarCarrito =
            By.cssSelector("button[data-button-action='add-to-cart']");

    // Mensaje de confirmación mostrado al agregar un producto
    private By mensajePopup = By.id("myModalLabel");

    // Cantidad agregada mostrada en el popup
    private By cantidadPopup =
            By.cssSelector(".product-quantity strong");

    // Total calculado mostrado en el popup
    private By totalPopup =
            By.cssSelector(".product-total .value");

    // Botón para ir al carrito
    private By btnFinalizarCompra =
            By.cssSelector(".cart-content-btn a.btn-primary");

    public StorePage(WebDriver driver) {
        this.driver = driver;

        // Espera explícita de hasta 10 segundos
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Abre la página principal de la tienda
    public void abrirTienda() {
        driver.get("https://qalab.bensg.com/store");
    }

    // Ingresa a la pantalla de inicio de sesión
    public void irAInicioSesion() {
        wait.until(
                ExpectedConditions.elementToBeClickable(linkIniciarSesion)
        ).click();
    }

    // Obtiene el nombre del usuario después de iniciar sesión
    public String obtenerNombreUsuario() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(linkCuenta)
        ).getText();
    }

    // Navega a Clothes y después selecciona la subcategoría Men
    public void navegarAClothesMen() {

        // Espera a que la categoría Clothes sea visible
        WebElement clothes = wait.until(
                ExpectedConditions.visibilityOfElementLocated(menuClothes)
        );

        // Pasa el mouse sobre Clothes para mostrar el submenú
        Actions actions = new Actions(driver);
        actions.moveToElement(clothes).perform();

        // Espera a que Men pueda recibir clic y lo selecciona
        wait.until(
                ExpectedConditions.elementToBeClickable(menuMen)
        ).click();
    }

    // Ingresa al primer producto disponible
    public void seleccionarPrimerProducto() {
        wait.until(
                ExpectedConditions.elementToBeClickable(primerProducto)
        ).click();
    }

    // Obtiene el precio actual del producto
    public double obtenerPrecioUnitario() {

        String precio = wait.until(
                ExpectedConditions.visibilityOfElementLocated(precioActual)
        ).getAttribute("content");

        return Double.parseDouble(precio);
    }

    // Agrega el producto al carrito
    public void agregarAlCarrito() {
        wait.until(
                ExpectedConditions.elementToBeClickable(btnAgregarCarrito)
        ).click();
    }
    // Aumenta la cantidad del producto desde el valor inicial 1
    public void seleccionarCantidad(int cantidad) {

        for (int i = 1; i < cantidad; i++) {
            wait.until(
                    ExpectedConditions.elementToBeClickable(btnAumentarCantidad)
            ).click();
        }
    }

    // Obtiene el mensaje de confirmación del popup
    public String obtenerMensajePopup() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(mensajePopup)
        ).getText();
    }

    // Obtiene la cantidad mostrada en el popup
    public int obtenerCantidadPopup() {

        String cantidad = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cantidadPopup)
        ).getText();

        return Integer.parseInt(cantidad);
    }

    // Obtiene el total mostrado en el popup
    public double obtenerTotalPopup() {

        String total = wait.until(
                ExpectedConditions.visibilityOfElementLocated(totalPopup)
        ).getText();

        // Limpia la moneda y cambia la coma decimal por punto
        total = total
                .replace("S/", "")
                .replace("PEN", "")
                .replace(",", ".")
                .trim();

        return Double.parseDouble(total);
    }

    // Ingresa al carrito desde el popup
    public void finalizarCompra() {
        wait.until(
                ExpectedConditions.elementToBeClickable(btnFinalizarCompra)
        ).click();
    }

}