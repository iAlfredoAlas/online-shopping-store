package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.OrderDetail;

import java.util.List;

public interface IOrderDetailRepository extends IGenericRepository<OrderDetail, Long> {

    List<OrderDetail> findByIsOrderDetailActive(Boolean isOrderDetailActive);

}
