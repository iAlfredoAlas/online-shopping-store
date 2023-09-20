package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Rating;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRatingRepository extends IGenericRepository<Rating, Long> {

    Page<Rating> findByIsRatingActive(Pageable pageable, boolean isRatingActive);

}
