package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.Cart;
import com.kodigo.shopping.online.store.repository.ICartRepository;
import com.kodigo.shopping.online.store.service.ICartService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CartServiceImpl implements ICartService {

    @Autowired
    private ICartRepository cartRepository;


    @Override
    public Page<Cart> getAll(Pageable pageable) {
        log.info("Show all data.");
        return cartRepository.findAll(pageable);
    }

    @Override
    public Page<Cart> findCustom(Pageable pageable, Boolean flat) {
        log.info("Show actives");
        return cartRepository.findByIsCartActive(pageable, flat);
    }

    @Override
    public Cart findById(Long id) {
        log.info("Show by id.");
        return cartRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public Cart add(Cart model) {
        log.info("Add entity.");
        return cartRepository.save(model);
    }

    @Override
    public Cart update(Cart model, Long id) {
        log.info("Update entity.");
        Cart objCart = cartRepository.findById(id).get();
        objCart.setQuatity(model.getQuatity());
        objCart.setIdOrderDetail(model.getIdOrderDetail());
        objCart.setIdProduct(model.getIdProduct());
        return cartRepository.save(objCart);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete entity.");
        cartRepository.deleteById(id);
    }
}
