package com.nttdata.page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CartPage {

    private WebDriver driver;
    private WebDriverWait wait;

    // Título principal de la página del carrito
    private By tituloCarrito =
            By.cssSelector(".cart-container h1");

    // Precio unitario del producto
    private By precioProducto =
            By.cssSelector(".product-line-grid-body .price");

    // Cantidad actual del producto en el carrito
    private By cantidadProducto =
            By.cssSelector(".js-cart-line-product-quantity");

    // Subtotal de productos mostrado en el resumen
    private By subtotalProductos =
            By.cssSelector("#cart-subtotal-products .value");

    // Total final del carrito
    private By totalCarrito =
            By.cssSelector(".cart-total .value");

    public CartPage(WebDriver driver) {
        this.driver = driver;

        // Espera explícita para los elementos del carrito
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Obtiene el título de la página
    public String obtenerTituloCarrito() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(tituloCarrito)
        ).getText();
    }

    // Obtiene el precio unitario del producto
    public double obtenerPrecioProducto() {

        String precio = wait.until(
                ExpectedConditions.visibilityOfElementLocated(precioProducto)
        ).getText();

        precio = precio
                .replace("S/", "")
                .replace("PEN", "")
                .replace(",", ".")
                .trim();

        return Double.parseDouble(precio);
    }

    // Obtiene la cantidad del producto
    public int obtenerCantidadProducto() {

        String cantidad = wait.until(
                ExpectedConditions.visibilityOfElementLocated(cantidadProducto)
        ).getAttribute("value");

        return Integer.parseInt(cantidad);
    }

    // Obtiene el subtotal mostrado en el carrito
    public double obtenerSubtotalProductos() {

        String subtotal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(subtotalProductos)
        ).getText();

        subtotal = subtotal
                .replace("S/", "")
                .replace("PEN", "")
                .replace(",", ".")
                .trim();

        return Double.parseDouble(subtotal);
    }

    // Obtiene el total final del carrito
    public double obtenerTotalCarrito() {

        String total = wait.until(
                ExpectedConditions.visibilityOfElementLocated(totalCarrito)
        ).getText();

        total = total
                .replace("S/", "")
                .replace("PEN", "")
                .replace(",", ".")
                .trim();

        return Double.parseDouble(total);
    }
}