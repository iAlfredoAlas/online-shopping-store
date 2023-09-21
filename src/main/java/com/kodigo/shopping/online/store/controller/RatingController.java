package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.OrderDetail;
import com.kodigo.shopping.online.store.models.Rating;
import com.kodigo.shopping.online.store.models.dto.OrderDetailDTO;
import com.kodigo.shopping.online.store.models.dto.RatingDTO;
import com.kodigo.shopping.online.store.service.IOrderDetailService;
import com.kodigo.shopping.online.store.service.IRatingService;
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
@RequestMapping("/rating")
public class RatingController implements IGenericController<RatingDTO, Long>{

    @Autowired
    private IRatingService ratingService;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<Page<RatingDTO>> getPage(Pageable pageable) {
        Page<Rating> ratingPage = ratingService.getAll(pageable);
        Page<RatingDTO> ratingDTOPage = ratingPage.map(rating -> mapper.map(rating, RatingDTO.class));
        return ResponseEntity.ok(ratingDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(ratingService.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<RatingDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<Rating> ratingPage = ratingService.findCustom(pageable, filter);
        Page<RatingDTO> ratingDTOPage = ratingPage.map(rating -> mapper.map(rating, RatingDTO.class));
        return ResponseEntity.ok(ratingDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, RatingDTO model) {
        try {
            Rating updatedRating = ratingService.update(mapper.map(model, Rating.class), id);
            if (updatedRating != null) {
                return ResponseEntity.ok(updatedRating);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(RatingDTO model) {
        try {
            return ResponseFactory.responseCreated(ratingService.add(mapper.map(model, Rating.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        ratingService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
