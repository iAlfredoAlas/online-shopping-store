package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.models.Product;
import com.kodigo.shopping.online.store.models.dto.ClientOrderDTO;
import com.kodigo.shopping.online.store.service.IClientOrderService;
import com.kodigo.shopping.online.store.util.ResponseFactory;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/client-controller")
public class ClientOrderController implements IGenericController<ClientOrderDTO, Long> {

    @Autowired
    private IClientOrderService clientOrderService;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<Page<ClientOrderDTO>> getPage(Pageable pageable) {
        Page<ClientOrder> clientOrderPage = clientOrderService.getAll(pageable);
        Page<ClientOrderDTO> clientOrderDTOPage = clientOrderPage.map(clientOrder -> mapper.map(clientOrder, ClientOrderDTO.class));
        return ResponseEntity.ok(clientOrderDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(clientOrderService.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<ClientOrderDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<ClientOrder> clientOrderPage = clientOrderService.findCustom(pageable, filter);
        Page<ClientOrderDTO> clientOrderDTOPage = clientOrderPage.map(clientOrder -> mapper.map(clientOrder, ClientOrderDTO.class));
        return ResponseEntity.ok(clientOrderDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, ClientOrderDTO model) {
        try {
            ClientOrder updatedClientOrder = clientOrderService.update(mapper.map(model, ClientOrder.class), id);
            if (updatedClientOrder != null) {
                return ResponseEntity.ok(updatedClientOrder);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(ClientOrderDTO model) {
        try {
            return ResponseFactory.responseCreated(clientOrderService.add(mapper.map(model, ClientOrder.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        clientOrderService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
