package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICategoryRepository extends IGenericRepository<Category, Long> {

    Page<Category> findByIsCategoryActive(Pageable pageable, Boolean isCategoryActive);

}
