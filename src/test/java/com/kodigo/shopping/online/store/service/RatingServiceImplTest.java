package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.Rating;
import com.kodigo.shopping.online.store.repository.IRatingRepository;
import com.kodigo.shopping.online.store.service.implement.RatingServiceImpl;
import com.kodigo.shopping.online.store.util.ETypeRating;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RatingServiceImplTest {

    @Mock
    private IRatingRepository ratingRepository;

    @InjectMocks
    private RatingServiceImpl ratingService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRatings() {
        List<Rating> ratings = new ArrayList<>();

        ratings.add(new Rating(1L, "Rating1", ETypeRating.POSITIVE, LocalDate.now(), true, null, null));
        ratings.add(new Rating(2L, "Rating2", ETypeRating.NEGATIVE, LocalDate.now(), true, null, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(ratingRepository.findAll(pageable)).thenReturn(new PageImpl<>(ratings));

        Page<Rating> result = ratingService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindRatingById_ExistingRating() {
        Rating rating = new Rating();
        when(ratingRepository.findById(1L)).thenReturn(Optional.of(rating));

        Rating result = ratingService.findById(1L);

        assertEquals(rating, result);
    }

    @Test
    public void testFindRatingById_NonExistingRating() {
        when(ratingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> ratingService.findById(1L));
    }

    @Test
    public void testAddRating() {
        Rating rating = new Rating(1L, "Rating1", ETypeRating.POSITIVE, LocalDate.now(), true, null, null);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);

        Rating result = ratingService.add(rating);

        assertEquals(rating, result);
    }

    @Test
    public void testUpdateRating() {
        Rating existingRating = new Rating(1L, "Rating1", ETypeRating.NEGATIVE, LocalDate.now(), true, null, null);
        Rating updatedRating = new Rating(2L, "Rating2", ETypeRating.POSITIVE, LocalDate.now(), true, null, null);

        when(ratingRepository.findById(1L)).thenReturn(Optional.of(existingRating));
        when(ratingRepository.save(any(Rating.class))).thenReturn(updatedRating);

        Rating result = ratingService.update(updatedRating, 1L);

        assertEquals(updatedRating, result);
    }

    @Test
    public void testDeleteRating() {
        Long ratingId = 1L;

        ratingService.deleteLog(ratingId);

        verify(ratingRepository, times(1)).deleteById(ratingId);
    }
}
