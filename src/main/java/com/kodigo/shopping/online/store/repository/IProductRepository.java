package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface IProductRepository extends IGenericRepository<Product, Long> {

    Page<Product> findByIsProductActive(Pageable pageable, Boolean isProductActive);

    @Query("SELECT u FROM Product u WHERE u.productName LIKE %:productName%")
    Page<Product> findByNombreUsuario(String productName, Pageable pageable);

}
