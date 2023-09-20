package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.ClientOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IClientOrderRepository extends IGenericRepository<ClientOrder, Long>{

    Page<ClientOrder> findByIsClientOrderActive(Pageable pageable, Boolean isClientOrderActive);

}
