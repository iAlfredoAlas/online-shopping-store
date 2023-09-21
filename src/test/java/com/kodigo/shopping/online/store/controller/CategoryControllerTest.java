package com.kodigo.shopping.online.store.controller;
import com.kodigo.shopping.online.store.models.Category;
import com.kodigo.shopping.online.store.models.dto.CategoryDTO;
import com.kodigo.shopping.online.store.service.ICategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class CategoryControllerTest {

    @Mock
    private ICategoryService categoryService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CategoryController categoryController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<CategoryDTO> categoryDTOList = new ArrayList<>();

        when(categoryService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(Category.class), eq(CategoryDTO.class))).thenReturn(new CategoryDTO());

        ResponseEntity<Page<CategoryDTO>> responseEntity = categoryController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category = new Category();

        when(categoryService.findById(categoryId)).thenReturn(category);
        when(mapper.map(category, CategoryDTO.class)).thenReturn(categoryDTO);

        ResponseEntity<?> responseEntity = categoryController.getOne(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidCategoryId = 999L;

        when(categoryService.findById(invalidCategoryId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = categoryController.getOne(invalidCategoryId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long categoryId = 1L;
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category = new Category();

        when(mapper.map(categoryDTO, Category.class)).thenReturn(category);
        when(categoryService.update(category, categoryId)).thenReturn(category);

        ResponseEntity<?> responseEntity = categoryController.update(categoryId, categoryDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidCategoryId = 999L;
        CategoryDTO categoryDTO = new CategoryDTO();

        when(categoryService.update(any(Category.class), eq(invalidCategoryId))).thenReturn(null);

        ResponseEntity<?> responseEntity = categoryController.update(invalidCategoryId, categoryDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        CategoryDTO categoryDTO = new CategoryDTO();
        Category category = new Category();

        when(mapper.map(categoryDTO, Category.class)).thenReturn(category);
        when(categoryService.add(category)).thenReturn(category);

        ResponseEntity<?> responseEntity = categoryController.create(categoryDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long categoryId = 1L;

        ResponseEntity<String> responseEntity = categoryController.delete(categoryId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}




