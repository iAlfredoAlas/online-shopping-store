package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Cart;
import com.kodigo.shopping.online.store.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private ICartService cartService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(cartService.findById(id));
        } catch (DataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }


}
