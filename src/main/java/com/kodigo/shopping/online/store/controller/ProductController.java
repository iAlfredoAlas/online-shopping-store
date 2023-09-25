package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.dto.ProductDTO;
import com.kodigo.shopping.online.store.models.dto.RolDTO;
import com.kodigo.shopping.online.store.repository.IProductRepository;
import com.kodigo.shopping.online.store.service.IProductService;
import com.kodigo.shopping.online.store.util.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/product")
public class ProductController implements IGenericController<ProductDTO, Long>{

    @Autowired
    private IProductService productService;

    @Autowired
    private IProductRepository productRepository;

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

    @GetMapping("/pageable")
    public ResponseEntity<?> findAllPageable(@PageableDefault(size = 10) Pageable pageable,
                                             @RequestParam(value = "productName", required = false, defaultValue = "") String productName) {
        Page<Product> productPage = productRepository.findByNombreUsuario("%" + productName + "%", pageable);
        List<ProductDTO> productsDTO = productPage.getContent().stream()
                .map(usuario -> mapper.map(usuario, ProductDTO.class)).collect(Collectors.toList());

        Map<String, Object> response = new HashMap<>();
        response.put("Products", productsDTO);
        response.put("currentPage", productPage.getNumber());
        response.put("totalItems", productPage.getTotalElements());
        response.put("totalPages", productPage.getTotalPages());

        return ResponseEntity.ok(response);
    }

}
