package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.OrderDetail;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IOrderDetailRepository extends IGenericRepository<OrderDetail, Long> {

    Page<OrderDetail> findByIsOrderDetailActive(Pageable pageable, Boolean isOrderDetailActive);

}
