package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Cart;
import com.kodigo.shopping.online.store.service.ICartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartControllerTest {

    @Mock
    private ICartService cartService;

    @InjectMocks
    private CartController cartController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        // Crear una instancia ficticia de Page<Cart> para simular la respuesta del servicio.
        Page<Cart> cartPage = mock(Page.class);

        // Configurar el comportamiento del servicio mock.
        when(cartService.getAll(null)).thenReturn(cartPage); // Pasa null en lugar de any(Pageable.class).

        // Llamar al método del controlador bajo prueba.
        ResponseEntity<Page<Cart>> responseEntity = cartController.getPage(null);

        // Verificar que el código de estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar que el servicio se llamó una vez con null como argumento.
        verify(cartService, times(1)).getAll(null);
    }


    @Test
    void testGetOne() {
        Long cartId = 1L;
        Cart cart = new Cart();

        // Configurar el comportamiento del servicio mock.
        when(cartService.findById(cartId)).thenReturn(cart);

        // Llamar al método del controlador bajo prueba.
        ResponseEntity<?> responseEntity = cartController.getOne(cartId);

        // Verificar que el código de estado de la respuesta sea HttpStatus.OK.
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        // Verificar que el servicio se llamó una vez con el ID de carrito especificado.
        verify(cartService, times(1)).findById(cartId);
    }
}

