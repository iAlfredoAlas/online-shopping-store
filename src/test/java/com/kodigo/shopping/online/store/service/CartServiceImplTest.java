package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.Cart;
import com.kodigo.shopping.online.store.repository.ICartRepository;
import com.kodigo.shopping.online.store.service.implement.CartServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CartServiceImplTest {

    @Mock
    private ICartRepository cartRepository;

    @InjectMocks
    private CartServiceImpl cartService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCarts() {
        List<Cart> carts = new ArrayList<>();
        carts.add(new Cart(1L, null, 1, true, null, null));
        carts.add(new Cart(2L, null, 1, true, null, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(cartRepository.findAll(pageable)).thenReturn(new PageImpl<>(carts));

        Page<Cart> result = cartService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindCartById_ExistingCart() {
        Cart cart = new Cart(1L, null, 1, true, null, null);
        when(cartRepository.findById(1L)).thenReturn(Optional.of(cart));

        Cart result = cartService.findById(1L);

        assertEquals(cart, result);
    }

    @Test
    public void testFindCartById_NonExistingCart() {
        when(cartRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> cartService.findById(1L));
    }

    @Test
    public void testAddCart() {
        Cart cart = new Cart(1L, null, 1, true, null, null);
        when(cartRepository.save(any(Cart.class))).thenReturn(cart);

        Cart result = cartService.add(cart);

        assertEquals(cart, result);
    }

    @Test
    public void testUpdateCart() {
        Cart existingCart = new Cart(1L, null, 1, false, null, null);
        Cart updatedCart = new Cart(1L, null, 1, true, null, null);

        when(cartRepository.findById(1L)).thenReturn(Optional.of(existingCart));
        when(cartRepository.save(any(Cart.class))).thenReturn(updatedCart);

        Cart result = cartService.update(updatedCart, 1L);

        assertEquals(updatedCart, result);
    }

    @Test
    public void testDeleteCart() {
        Long cartId = 1L;

        cartService.deleteLog(cartId);

        verify(cartRepository, times(1)).deleteById(cartId);
    }
}

