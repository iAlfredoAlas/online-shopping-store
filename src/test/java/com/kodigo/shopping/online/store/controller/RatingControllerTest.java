package com.kodigo.shopping.online.store.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kodigo.shopping.online.store.controller.RatingController;
import com.kodigo.shopping.online.store.models.Rating;
import com.kodigo.shopping.online.store.models.dto.RatingDTO;
import com.kodigo.shopping.online.store.service.IRatingService;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RatingControllerTest {

    @Mock
    private IRatingService ratingService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RatingController ratingController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<RatingDTO> ratingDTOList = new ArrayList<>();

        when(ratingService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(Rating.class), eq(RatingDTO.class))).thenReturn(new RatingDTO());

        ResponseEntity<Page<RatingDTO>> responseEntity = ratingController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long ratingId = 1L;
        RatingDTO ratingDTO = new RatingDTO();
        Rating rating = new Rating();

        when(ratingService.findById(ratingId)).thenReturn(rating);
        when(mapper.map(rating, RatingDTO.class)).thenReturn(ratingDTO);

        ResponseEntity<?> responseEntity = ratingController.getOne(ratingId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidRatingId = 999L;

        when(ratingService.findById(invalidRatingId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = ratingController.getOne(invalidRatingId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long ratingId = 1L;
        RatingDTO ratingDTO = new RatingDTO();
        Rating rating = new Rating();

        when(mapper.map(ratingDTO, Rating.class)).thenReturn(rating);
        when(ratingService.update(rating, ratingId)).thenReturn(rating);

        ResponseEntity<?> responseEntity = ratingController.update(ratingId, ratingDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidRatingId = 999L;
        RatingDTO ratingDTO = new RatingDTO();

        when(ratingService.update(any(Rating.class), eq(invalidRatingId))).thenReturn(null);

        ResponseEntity<?> responseEntity = ratingController.update(invalidRatingId, ratingDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        RatingDTO ratingDTO = new RatingDTO();
        Rating rating = new Rating();

        when(mapper.map(ratingDTO, Rating.class)).thenReturn(rating);
        when(ratingService.add(rating)).thenReturn(rating);

        ResponseEntity<?> responseEntity = ratingController.create(ratingDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long ratingId = 1L;

        ResponseEntity<String> responseEntity = ratingController.delete(ratingId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
