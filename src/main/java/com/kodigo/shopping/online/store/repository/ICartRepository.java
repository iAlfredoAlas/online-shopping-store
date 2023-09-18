package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Cart;

import java.util.List;

public interface ICartRepository extends IGenericRepository<Cart, Long>{

    List<Cart> findByIsCartActive (Boolean isCartActive);

}
