package com.kodigo.shopping.online.store.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kodigo.shopping.online.store.controller.ProductController;
import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.models.dto.ProductDTO;
import com.kodigo.shopping.online.store.service.IProductService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class ProductControllerTest {

    @Mock
    private IProductService productService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ProductController productController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<ProductDTO> productDTOList = new ArrayList<>();

        when(productService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(Product.class), eq(ProductDTO.class))).thenReturn(new ProductDTO());

        ResponseEntity<Page<ProductDTO>> responseEntity = productController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();

        when(productService.findById(productId)).thenReturn(product);
        when(mapper.map(product, ProductDTO.class)).thenReturn(productDTO);

        ResponseEntity<?> responseEntity = productController.getOne(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidProductId = 999L;

        when(productService.findById(invalidProductId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = productController.getOne(invalidProductId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long productId = 1L;
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();

        when(mapper.map(productDTO, Product.class)).thenReturn(product);
        when(productService.update(product, productId)).thenReturn(product);

        ResponseEntity<?> responseEntity = productController.update(productId, productDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidProductId = 999L;
        ProductDTO productDTO = new ProductDTO();

        when(productService.update(any(Product.class), eq(invalidProductId))).thenReturn(null);

        ResponseEntity<?> responseEntity = productController.update(invalidProductId, productDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        ProductDTO productDTO = new ProductDTO();
        Product product = new Product();

        when(mapper.map(productDTO, Product.class)).thenReturn(product);
        when(productService.add(product)).thenReturn(product);

        ResponseEntity<?> responseEntity = productController.create(productDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long productId = 1L;

        ResponseEntity<String> responseEntity = productController.delete(productId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
