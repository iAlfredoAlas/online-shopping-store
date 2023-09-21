package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.Category;
import com.kodigo.shopping.online.store.repository.ICategoryRepository;
import com.kodigo.shopping.online.store.service.implement.CategoryServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CategoryServiceImplTest {

    @Mock
    private ICategoryRepository categoryRepository;

    @InjectMocks
    private CategoryServiceImpl categoryService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllCategories() {
        List<Category> categories = new ArrayList<>();
        categories.add(new Category(1L, "Category1", "Test description", true));
        categories.add(new Category(2L, "Category2", "Test2 description", true));

        Pageable pageable = PageRequest.of(0, 10);

        when(categoryRepository.findAll(pageable)).thenReturn(new PageImpl<>(categories));

        Page<Category> result = categoryService.getAll(pageable);

        assertEquals(2, result.getContent().size());

    }

    @Test
    public void testFindCategoryById_ExistingCategory() {
        Category category = new Category();
        when(categoryRepository.findById(1L)).thenReturn(Optional.of(category));

        Category result = categoryService.findById(1L);

        assertEquals(category, result);
    }

    @Test
    public void testFindCategoryById_NonExistingCategory() {
        when(categoryRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> categoryService.findById(1L));
    }

    @Test
    public void testAddCategory() {
        Category category = new Category();
        when(categoryRepository.save(any(Category.class))).thenReturn(category);

        Category result = categoryService.add(category);

        assertEquals(category, result);
    }

    @Test
    public void testUpdateCategory() {
        Category existingCategory = new Category();
        Category updatedCategory = new Category();

        when(categoryRepository.findById(1L)).thenReturn(Optional.of(existingCategory));
        when(categoryRepository.save(any(Category.class))).thenReturn(updatedCategory);

        Category result = categoryService.update(updatedCategory, 1L);

        assertEquals(updatedCategory, result);
    }

    @Test
    public void testDeleteCategory() {
        Long categoryId = 1L;

        categoryService.deleteLog(categoryId);

        verify(categoryRepository, times(1)).deleteById(categoryId);
    }
}

