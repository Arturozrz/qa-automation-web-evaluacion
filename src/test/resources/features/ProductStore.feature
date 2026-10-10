Feature: Product - Store

  @exitoso
  Scenario: Validación del precio de un producto
    Given estoy en la página de la tienda
    And me logueo con mi usuario "USUARIO_VALIDO" y clave "CLAVE_VALIDA"
    When navego a la categoría "Clothes" y subcategoría "Men"
    And agrego 2 unidades del primer producto al carrito
    Then valido en el popup la confirmación del producto agregado
    And valido en el popup que el monto total sea calculado correctamente
    When finalizo la compra
    Then valido el título de la página del carrito
    And vuelvo a validar el cálculo de precios en el carrito

  @loginInvalido
  Scenario: Inicio de sesión con credenciales inválidas
    Given estoy en la página de la tienda
    And me logueo con mi usuario "arturin99@gmail.com" y clave "123456"

  @categoriaInvalida
  Scenario: Navegación a una categoría inexistente
    Given estoy en la página de la tienda
    And me logueo con mi usuario "USUARIO_VALIDO" y clave "CLAVE_VALIDA"
    When navego a la categoría "Autos" y subcategoría "Men"