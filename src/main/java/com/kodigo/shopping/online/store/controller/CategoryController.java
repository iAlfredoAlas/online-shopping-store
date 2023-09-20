package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Category;
import com.kodigo.shopping.online.store.models.dto.CategoryDTO;
import com.kodigo.shopping.online.store.service.ICategoryService;
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
@RequestMapping("/category")
public class CategoryController implements IGenericController<CategoryDTO, Long> {

    @Autowired
    private ICategoryService categoryService;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<Page<CategoryDTO>> getPage(Pageable pageable) {
        Page<Category> categoryPage = categoryService.getAll(pageable);
        Page<CategoryDTO> categoryDTOPage = categoryPage.map(category -> mapper.map(category, CategoryDTO.class));
        return ResponseEntity.ok(categoryDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(categoryService.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<CategoryDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<Category> categoryPage = categoryService.findCustom(pageable, filter);
        Page<CategoryDTO> categoryDTOPage = categoryPage.map(category -> mapper.map(category, CategoryDTO.class));
        return ResponseEntity.ok(categoryDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, CategoryDTO model) {
        try {
            Category updatedCategory = categoryService.update(mapper.map(model, Category.class), id);
            if (updatedCategory != null) {
                return ResponseEntity.ok(updatedCategory);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(CategoryDTO model) {
        try {
            return ResponseFactory.responseCreated(categoryService.add(mapper.map(model, Category.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }

    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        categoryService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
