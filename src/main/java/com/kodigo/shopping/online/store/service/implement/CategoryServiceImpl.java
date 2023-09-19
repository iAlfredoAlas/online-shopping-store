package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.Category;
import com.kodigo.shopping.online.store.repository.ICategoryRepository;
import com.kodigo.shopping.online.store.service.ICategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl implements ICategoryService {


    @Autowired
    private ICategoryRepository categoryRepository;

    @Override
    public List<Category> getAll() {
        log.info("Show all data.");
        return categoryRepository.findAll();
    }

    @Override
    public List<Category> findCustom(Boolean flat) {
        log.info("Show actives");
        return categoryRepository.findByIsCategoryActive(flat);
    }

    @Override
    public Category findById(Long id) {
        log.info("Show by id");
        return categoryRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public Category add(Category model) {
        log.info("Add new Category");
        return categoryRepository.save(model);
    }

    @Override
    public Category update(Category model, Long id) {
        log.info("Update Category");
        Category objCategory = categoryRepository.findById(id).get();
        objCategory.setCategoryName(model.getCategoryName());
        objCategory.setCategoryDescription(model.getCategoryDescription());
        objCategory.setIsCategoryActive(model.getIsCategoryActive());
        return categoryRepository.save(objCategory);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete Category");
        categoryRepository.deleteById(id);
    }
}
