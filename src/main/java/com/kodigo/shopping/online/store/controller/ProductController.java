package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.dto.ProductDTO;
import com.kodigo.shopping.online.store.models.dto.RolDTO;
import com.kodigo.shopping.online.store.service.IProductService;
import com.kodigo.shopping.online.store.util.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController implements IGenericController<ProductDTO, Long>{

    @Autowired
    private IProductService productService;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<Page<ProductDTO>> getPage(Pageable pageable) {
        Page<Product> productPage = productService.getAll(pageable);
        Page<ProductDTO> productDTOPage = productPage.map(product -> mapper.map(product, ProductDTO.class));
        return ResponseEntity.ok(productDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(productService.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<ProductDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<Product> productPage = productService.findCustom(pageable, filter);
        Page<ProductDTO> productDTOPage = productPage.map(product -> mapper.map(product, ProductDTO.class));
        return ResponseEntity.ok(productDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, ProductDTO model) {
        try {
            Product updatedProduct = productService.update(mapper.map(model, Product.class), id);
            if (updatedProduct != null) {
                return ResponseEntity.ok(updatedProduct);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(ProductDTO model) {
        try {
            return ResponseFactory.responseCreated(productService.add(mapper.map(model, Product.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        productService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
