package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.OrderDetail;
import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.repository.IOrderDetailRepository;
import com.kodigo.shopping.online.store.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Autowired
    private ProductServiceImpl productService;

    @Override
    public Page<OrderDetail> getAll(Pageable pageable) {
        log.info("Show all data");
        return orderDetailRepository.findAll(pageable);
    }

    @Override
    public Page<OrderDetail> findCustom(Pageable pageable, Boolean flat) {
        log.info("Show actives");
        return orderDetailRepository.findByIsOrderDetailActive(pageable, flat);
    }

    @Override
    public OrderDetail findById(Long id) {
        log.info("Show by id");
        return orderDetailRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public OrderDetail add(OrderDetail model) {
        log.info("Save OrderDetail");
        Product product = model.getIdProduct();
        int quantity = model.getQuantity();
        if (product != null && product.getStock() >= quantity) {
            productService.updateStock(product.getIdProduct(), -quantity); // Restar la cantidad del stock
            productService.update(product, product.getIdProduct()); // Actualizar el producto
            return orderDetailRepository.save(model);
        } else {
            throw new IllegalArgumentException("No hay suficiente stock disponible para este producto.");
        }
    }

    @Override
    public OrderDetail update(OrderDetail model, Long id) {
        log.info("Update OrderDetail");
        OrderDetail objOrderDetail = orderDetailRepository.findById(id).get();
        objOrderDetail.setQuantity(model.getQuantity());
        objOrderDetail.setSubtotal(model.getSubtotal());
        objOrderDetail.setIsOrderDetailActive(model.getIsOrderDetailActive());
        objOrderDetail.setIdClientOrder(model.getIdClientOrder());
        objOrderDetail.setIdProduct(model.getIdProduct());
        return orderDetailRepository.save(objOrderDetail);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete OrderDetail");
        orderDetailRepository.deleteById(id);
    }
}
