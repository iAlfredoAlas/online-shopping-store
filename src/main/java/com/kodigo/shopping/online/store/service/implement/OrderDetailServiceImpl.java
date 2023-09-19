package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.OrderDetail;
import com.kodigo.shopping.online.store.repository.IOrderDetailRepository;
import com.kodigo.shopping.online.store.service.IOrderDetailService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class OrderDetailServiceImpl implements IOrderDetailService {

    @Autowired
    private IOrderDetailRepository orderDetailRepository;

    @Override
    public List<OrderDetail> getAll() {
        log.info("Show all data");
        return orderDetailRepository.findAll();
    }

    @Override
    public List<OrderDetail> findCustom(Boolean flat) {
        log.info("Show actives");
        return orderDetailRepository.findByIsOrderDetailActive(flat);
    }

    @Override
    public OrderDetail findById(Long id) {
        log.info("Show by id");
        return orderDetailRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public OrderDetail add(OrderDetail model) {
        log.info("Save OrderDetail");
        return orderDetailRepository.save(model);
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
