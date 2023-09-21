package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.models.OrderDetail;
import com.kodigo.shopping.online.store.repository.IClientOrderRepository;
import com.kodigo.shopping.online.store.repository.IOrderDetailRepository;
import com.kodigo.shopping.online.store.service.implement.ClientOrderServiceImpl;
import com.kodigo.shopping.online.store.service.implement.OrderDetailServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class OrderDetailImplTest {

    @Mock
    private IOrderDetailRepository orderDetailRepository;

    @InjectMocks
    private OrderDetailServiceImpl orderDetailService;

    BigDecimal num1 = new BigDecimal("10.5");

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllOrderDetail() {
        List<OrderDetail> orders = new ArrayList<>();

        orders.add(new OrderDetail(1L, 1, num1, true, null, null));
        orders.add(new OrderDetail(2L, 2, num1, true, null, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(orderDetailRepository.findAll(pageable)).thenReturn(new PageImpl<>(orders));

        Page<OrderDetail> result = orderDetailService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindOrderDetailById_ExistingOrderDetail() {
        OrderDetail orderDetail = new OrderDetail();
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(orderDetail));

        OrderDetail result = orderDetailService.findById(1L);

        assertEquals(orderDetail, result);
    }

    @Test
    public void testFindOrderDetailById_NonExistingOrderDetail() {
        when(orderDetailRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> orderDetailService.findById(1L));
    }

    @Test
    public void testAddOrderDetail() {
        OrderDetail orderDetail = new OrderDetail(1L, 1, num1, true, null, null);
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(orderDetail);

        OrderDetail result = orderDetailService.add(orderDetail);

        assertEquals(orderDetail, result);
    }

    @Test
    public void testUpdateOrderDetail() {
        OrderDetail existingOrderDetail = new OrderDetail(1L, 1, num1, true, null, null);
        OrderDetail updatedOrderDetail = new OrderDetail(2L, 1, num1, true, null, null);

        when(orderDetailRepository.findById(1L)).thenReturn(Optional.of(existingOrderDetail));
        when(orderDetailRepository.save(any(OrderDetail.class))).thenReturn(updatedOrderDetail);

        OrderDetail result = orderDetailService.update(updatedOrderDetail, 1L);

        assertEquals(updatedOrderDetail, result);
    }

    @Test
    public void testDeleteOrderDetail() {
        Long clientOrderId = 1L;

        orderDetailService.deleteLog(clientOrderId);

        verify(orderDetailRepository, times(1)).deleteById(clientOrderId);
    }

}
