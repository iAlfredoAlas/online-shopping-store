package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.repository.IProductRepository;
import com.kodigo.shopping.online.store.service.implement.ProductServiceImpl;
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

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ProductServiceImplTest {

    @Mock
    private IProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    BigDecimal num1 = new BigDecimal("10.5");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllProducts() {
        List<Product> products = new ArrayList<>();

        products.add(new Product(1L, "Product1", "Test description 1", 10, num1, true, null));
        products.add(new Product(2L, "Product2", "Test description 2", 20, num1, true, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(productRepository.findAll(pageable)).thenReturn(new PageImpl<>(products));

        Page<Product> result = productService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindProductById_ExistingProduct() {
        Product product = new Product();
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Product result = productService.findById(1L);

        assertEquals(product, result);
    }

    @Test
    public void testFindProductById_NonExistingProduct() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> productService.findById(1L));
    }

    @Test
    public void testAddProduct() {
        Product product = new Product(1L, "Product1", "Test description", 10, num1, true, null);
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Product result = productService.add(product);

        assertEquals(product, result);
    }

    @Test
    public void testUpdateProduct() {
        Product existingProduct = new Product(1L, "Product1", "Test description 1", 10, num1, true, null);
        Product updatedProduct = new Product(2L, "Product2", "Test description 2", 20, num1, true, null);

        when(productRepository.findById(1L)).thenReturn(Optional.of(existingProduct));
        when(productRepository.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct, 1L);

        assertEquals(updatedProduct, result);
    }

    @Test
    public void testDeleteProduct() {
        Long productId = 1L;

        productService.deleteLog(productId);

        verify(productRepository, times(1)).deleteById(productId);
    }

}
