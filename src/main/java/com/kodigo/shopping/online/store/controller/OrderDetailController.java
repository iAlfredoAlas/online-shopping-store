package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.models.OrderDetail;
import com.kodigo.shopping.online.store.models.dto.ClientOrderDTO;
import com.kodigo.shopping.online.store.models.dto.OrderDetailDTO;
import com.kodigo.shopping.online.store.service.IClientOrderService;
import com.kodigo.shopping.online.store.service.IOrderDetailService;
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
@RequestMapping("/order-detail")
public class OrderDetailController implements IGenericController<OrderDetailDTO, Long> {

    @Autowired
    private IOrderDetailService orderDetailService;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<Page<OrderDetailDTO>> getPage(Pageable pageable) {
        Page<OrderDetail> orderDetailPage = orderDetailService.getAll(pageable);
        Page<OrderDetailDTO> orderDetailDTOPage = orderDetailPage.map(orderDetail -> mapper.map(orderDetail, OrderDetailDTO.class));
        return ResponseEntity.ok(orderDetailDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(orderDetailService.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<OrderDetailDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<OrderDetail> orderDetailPage = orderDetailService.findCustom(pageable, filter);
        Page<OrderDetailDTO> orderDetailDTOPage = orderDetailPage.map(orderDetail -> mapper.map(orderDetail, OrderDetailDTO.class));
        return ResponseEntity.ok(orderDetailDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, OrderDetailDTO model) {
        try {
            OrderDetail updatedOrderDetail = orderDetailService.update(mapper.map(model, OrderDetail.class), id);
            if (updatedOrderDetail != null) {
                return ResponseEntity.ok(updatedOrderDetail);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(OrderDetailDTO model) {
        try {
            return ResponseFactory.responseCreated(orderDetailService.add(mapper.map(model, OrderDetail.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        orderDetailService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
