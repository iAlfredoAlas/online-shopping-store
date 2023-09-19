package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Rating;

import java.util.List;

public interface IRatingRepository extends IGenericRepository<Rating, Long> {

    List<Rating> findByIsRatingActive(boolean isRatingActive);

}
