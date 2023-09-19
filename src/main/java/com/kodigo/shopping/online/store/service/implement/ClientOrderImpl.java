package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.repository.IClientOrderRepository;
import com.kodigo.shopping.online.store.service.IClientOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ClientOrderImpl implements IClientOrder {

    @Autowired
    private IClientOrderRepository clientOrderRepository;

    @Override
    public List<ClientOrder> getAll() {
        log.info("Show all data");
        return clientOrderRepository.findAll();
    }

    @Override
    public List<ClientOrder> findCustom(Boolean flat) {
        log.info("Show actives");
        return clientOrderRepository.findByIsClientOrderActive(flat);
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
