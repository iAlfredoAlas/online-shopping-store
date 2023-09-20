package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.repository.IClientOrderRepository;
import com.kodigo.shopping.online.store.service.IClientOrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ClientOrderServiceImpl implements IClientOrderService {

    @Autowired
    private IClientOrderRepository clientOrderRepository;

    @Override
    public Page<ClientOrder> getAll(Pageable pageable) {
        log.info("Show all data");
        return clientOrderRepository.findAll(pageable);
    }

    @Override
    public Page<ClientOrder> findCustom(Pageable pageable, Boolean flat) {
        log.info("Show actives");
        return clientOrderRepository.findByIsClientOrderActive(pageable, flat);
    }

    @Override
    public ClientOrder findById(Long id) {
        log.info("Show by id");
        return clientOrderRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public ClientOrder add(ClientOrder model) {
        log.info("Save ClientOrder");
        return clientOrderRepository.save(model);
    }

    @Override
    public ClientOrder update(ClientOrder model, Long id) {
        log.info("Update ClientOrder");
        ClientOrder objClientOrder = clientOrderRepository.findById(id).get();
        objClientOrder.setIsClientOrderActive(model.getIsClientOrderActive());
        objClientOrder.setDateOrder(model.getDateOrder());
        objClientOrder.setIdUser(model.getIdUser());
        return clientOrderRepository.save(objClientOrder);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete ClientOrder");
        clientOrderRepository.deleteById(id);
    }
}
