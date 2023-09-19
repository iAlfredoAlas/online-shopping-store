package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.Rating;
import com.kodigo.shopping.online.store.repository.IRatingRepository;
import com.kodigo.shopping.online.store.service.IRatingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RatingServiceImpl implements IRatingService {


    @Autowired
    private IRatingRepository ratingRepository;

    @Override
    public List<Rating> getAll() {
        log.info("Show all data");
        return ratingRepository.findAll();
    }

    @Override
    public List<Rating> findCustom(Boolean flat) {
        log.info("Show actives");
        return ratingRepository.findByIsRatingActive(flat);
    }

    @Override
    public Rating findById(Long id) {
        log.info("Show by id");
        return ratingRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public Rating add(Rating model) {
        log.info("Save Rating");
        return ratingRepository.save(model);
    }

    @Override
    public Rating update(Rating model, Long id) {
        log.info("Update Rating");
        Rating objRating = ratingRepository.findById(id).get();
        objRating.setRatingDetail(model.getRatingDetail());
        objRating.setTypeRating(model.getTypeRating());
        objRating.setDateRating(model.getDateRating());
        objRating.setIsRatingActive(model.getIsRatingActive());
        objRating.setIdUser(model.getIdUser());
        objRating.setIdProduct(model.getIdProduct());
        return ratingRepository.save(objRating);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete Rating");
        ratingRepository.deleteById(id);
    }
}
