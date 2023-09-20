package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IProductRepository extends IGenericRepository<Product, Long> {

    Page<Product> findByIsProductActive(Pageable pageable, Boolean isProductActive);

}
