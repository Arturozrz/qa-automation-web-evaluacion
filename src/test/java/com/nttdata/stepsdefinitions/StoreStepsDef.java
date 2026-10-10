package com.nttdata.stepsdefinitions;

import com.nttdata.core.DriverManager;
import com.nttdata.page.LoginPage;
import com.nttdata.page.StorePage;
import io.cucumber.java.en.Given;
import org.junit.Assert;
import io.cucumber.java.en.When;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import com.nttdata.page.CartPage;

public class StoreStepsDef {

    private StorePage storePage;
    private LoginPage loginPage;
    private double precioUnitario;
    private int cantidadProductos;
    private CartPage cartPage;

    @Given("estoy en la página de la tienda")
    public void estoyEnLaPaginaDeLaTienda() {

        // Se crean las páginas utilizando el mismo navegador
        storePage = new StorePage(DriverManager.getDriver());
        loginPage = new LoginPage(DriverManager.getDriver());
        cartPage = new CartPage(DriverManager.getDriver());

        // Abre la tienda
        storePage.abrirTienda();
    }

    @Given("me logueo con mi usuario {string} y clave {string}")
    public void meLogueoConMiUsuarioYClave(
            String usuario,
            String clave) {

        // Si el escenario usa credenciales válidas, se toman del ambiente
        if (usuario.equals("USUARIO_VALIDO")) {
            usuario = System.getenv("QA_USER");
        }

        if (clave.equals("CLAVE_VALIDA")) {
            clave = System.getenv("QA_PASSWORD");
        }

        // Ingresa a la pantalla de login
        storePage.irAInicioSesion();

        // Completa las credenciales
        loginPage.ingresarCorreo(usuario);
        loginPage.ingresarPassword(clave);

        // Inicia sesión
        loginPage.clickIniciarSesion();

        // Valida que el usuario haya iniciado sesión correctamente
        String nombreUsuario = storePage.obtenerNombreUsuario();

        Assert.assertFalse(
                "El usuario no se autentico correctamente",
                nombreUsuario.isEmpty()
        );
    }

    @When("navego a la categoría {string} y subcategoría {string}")
    public void navegoALaCategoriaYSubcategoria(
            String categoria,
            String subcategoria) {

        // Valida que la categoría solicitada exista en el escenario
        if (categoria.equalsIgnoreCase("Clothes")
                && subcategoria.equalsIgnoreCase("Men")) {

            storePage.navegarAClothesMen();

        } else {

            Assert.fail(
                    "La categoria o subcategoria indicada no existe: "
                            + categoria + " / " + subcategoria
            );
        }
    }

    @And("agrego {int} unidades del primer producto al carrito")
    public void agregoUnidadesDelPrimerProductoAlCarrito(int cantidad) {

        // Ingresa al primer producto disponible
        storePage.seleccionarPrimerProducto();

        // Guarda el precio actual para validar el total posteriormente
        precioUnitario = storePage.obtenerPrecioUnitario();
        cantidadProductos = cantidad;

        // Indica la cantidad y agrega el producto al carrito
        storePage.seleccionarCantidad(cantidad);
        storePage.agregarAlCarrito();
    }

    @Then("valido en el popup la confirmación del producto agregado")
    public void validoConfirmacionProductoAgregado() {

        // Obtiene el mensaje mostrado en el popup
        String mensaje = storePage.obtenerMensajePopup();

        // Valida que aparezca el mensaje esperado
        Assert.assertTrue(
                "No se mostro el mensaje de producto agregado",
                mensaje.contains(
                        "Producto añadido correctamente a su carrito de compra"
                )
        );

        // Valida que la cantidad agregada sea la solicitada
        Assert.assertEquals(
                cantidadProductos,
                storePage.obtenerCantidadPopup()
        );
    }

    @Then("valido en el popup que el monto total sea calculado correctamente")
    public void validoMontoTotalPopup() {

        // Calcula el total esperado
        double totalEsperado = precioUnitario * cantidadProductos;

        // Obtiene el total mostrado por la aplicación
        double totalObtenido = storePage.obtenerTotalPopup();

        // Guarda evidencia del popup en el reporte
        DriverManager.screenShot();

        // Compara el total esperado contra el mostrado
        Assert.assertEquals(
                "El total mostrado en el popup no es correcto",
                totalEsperado,
                totalObtenido,
                0.01
        );
    }

    @When("finalizo la compra")
    public void finalizoLaCompra() {

        // Ingresa a la página del carrito
        storePage.finalizarCompra();
    }

    @Then("valido el título de la página del carrito")
    public void validoElTituloDeLaPaginaDelCarrito() {

        // Obtiene y valida el título de la página
        String titulo = cartPage.obtenerTituloCarrito();

        Assert.assertEquals(
                "CARRITO",
                titulo
        );
    }

    @Then("vuelvo a validar el cálculo de precios en el carrito")
    public void validoCalculoDePreciosEnElCarrito() {

        // Obtiene los datos mostrados en el carrito
        double precio = cartPage.obtenerPrecioProducto();
        int cantidad = cartPage.obtenerCantidadProducto();
        double subtotal = cartPage.obtenerSubtotalProductos();
        double total = cartPage.obtenerTotalCarrito();

        // Calcula el subtotal esperado
        double subtotalEsperado = precio * cantidad;

        // Valida precio por cantidad contra subtotal
        Assert.assertEquals(
                "El subtotal del carrito no es correcto",
                subtotalEsperado,
                subtotal,
                0.001
        );

        // Valida que el total final coincida con el subtotal
        Assert.assertEquals(
                "El total del carrito no es correcto",
                subtotal,
                total,
                0.001
        );

        // Guarda evidencia de la pantalla final del carrito
        DriverManager.screenShot();
    }
}