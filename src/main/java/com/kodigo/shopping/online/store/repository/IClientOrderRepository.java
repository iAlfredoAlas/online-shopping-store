package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.ClientOrder;

import java.util.List;

public interface IClientOrderRepository extends IGenericRepository<ClientOrder, Long>{

    List<ClientOrder> findByIsClientOrderActive(Boolean isClientOrderActive);

}
