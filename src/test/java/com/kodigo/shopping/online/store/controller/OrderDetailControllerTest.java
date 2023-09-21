package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.OrderDetail;
import com.kodigo.shopping.online.store.models.dto.OrderDetailDTO;
import com.kodigo.shopping.online.store.service.IOrderDetailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

class OrderDetailControllerTest {

    @Mock
    private IOrderDetailService orderDetailService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private OrderDetailController orderDetailController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<OrderDetailDTO> orderDetailDTOList = new ArrayList<>();

        when(orderDetailService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(OrderDetail.class), eq(OrderDetailDTO.class))).thenReturn(new OrderDetailDTO());

        ResponseEntity<Page<OrderDetailDTO>> responseEntity = orderDetailController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long orderDetailId = 1L;
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        OrderDetail orderDetail = new OrderDetail();

        when(orderDetailService.findById(orderDetailId)).thenReturn(orderDetail);
        when(mapper.map(orderDetail, OrderDetailDTO.class)).thenReturn(orderDetailDTO);

        ResponseEntity<?> responseEntity = orderDetailController.getOne(orderDetailId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidOrderDetailId = 999L;

        when(orderDetailService.findById(invalidOrderDetailId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = orderDetailController.getOne(invalidOrderDetailId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long orderDetailId = 1L;
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        OrderDetail orderDetail = new OrderDetail();

        when(mapper.map(orderDetailDTO, OrderDetail.class)).thenReturn(orderDetail);
        when(orderDetailService.update(orderDetail, orderDetailId)).thenReturn(orderDetail);

        ResponseEntity<?> responseEntity = orderDetailController.update(orderDetailId, orderDetailDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidOrderDetailId = 999L;
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();

        when(orderDetailService.update(any(OrderDetail.class), eq(invalidOrderDetailId))).thenReturn(null);

        ResponseEntity<?> responseEntity = orderDetailController.update(invalidOrderDetailId, orderDetailDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        OrderDetail orderDetail = new OrderDetail();

        when(mapper.map(orderDetailDTO, OrderDetail.class)).thenReturn(orderDetail);
        when(orderDetailService.add(orderDetail)).thenReturn(orderDetail);

        ResponseEntity<?> responseEntity = orderDetailController.create(orderDetailDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long orderDetailId = 1L;

        ResponseEntity<String> responseEntity = orderDetailController.delete(orderDetailId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}

