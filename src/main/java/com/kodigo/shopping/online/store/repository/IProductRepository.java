package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Product;

import java.util.List;

public interface IProductRepository extends IGenericRepository<Product, Long> {

    List<Product> findByIsProductActive(Boolean isProductActive);

}
