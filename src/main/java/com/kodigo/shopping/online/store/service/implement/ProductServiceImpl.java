package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.repository.IProductRepository;
import com.kodigo.shopping.online.store.service.IProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProductServiceImpl implements IProductService {

    @Autowired
    private IProductRepository productRepository;

    @Override
    public Page<Product> getAll(Pageable pageable) {
        log.info("Show all data");
        return productRepository.findAll(pageable);
    }

    @Override
    public Page<Product> findCustom(Pageable pageable, Boolean flat) {
        log.info("Show actives");
        return productRepository.findByIsProductActive(pageable, flat);
    }

    @Override
    public Product findById(Long id) {
        log.info("Show by id");
        return productRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public Product add(Product model) {
        log.info("Save Product");
        return productRepository.save(model);
    }

    @Override
    public Product update(Product model, Long id) {
        log.info("Update Product");
        Product objProduct = productRepository.findById(id).get();
        objProduct.setProductName(model.getProductName());
        objProduct.setProductDescription(model.getProductDescription());
        objProduct.setStock(model.getStock());
        objProduct.setPrice(model.getPrice());
        objProduct.setIsProductActive(model.getIsProductActive());
        objProduct.setIdCategory(model.getIdCategory());
        return productRepository.save(objProduct);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete Product");
        productRepository.deleteById(id);
    }
}
