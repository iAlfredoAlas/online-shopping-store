package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Category;

import java.util.List;

public interface ICategoryRepository extends IGenericRepository<Category, Long> {

    List<Category> findByIsCategoryActive(Boolean isCategoryActive);

}
