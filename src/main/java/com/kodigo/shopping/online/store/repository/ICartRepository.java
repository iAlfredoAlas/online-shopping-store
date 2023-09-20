package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Cart;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICartRepository extends IGenericRepository<Cart, Long>{

    Page<Cart> findByIsCartActive (Pageable pageable, Boolean isCartActive);

}
