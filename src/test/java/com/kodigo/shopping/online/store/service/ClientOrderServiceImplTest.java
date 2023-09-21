package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.Cart;
import com.kodigo.shopping.online.store.models.Category;
import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.repository.ICartRepository;
import com.kodigo.shopping.online.store.repository.IClientOrderRepository;
import com.kodigo.shopping.online.store.service.implement.CartServiceImpl;
import com.kodigo.shopping.online.store.service.implement.ClientOrderServiceImpl;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ClientOrderServiceImplTest {

    @Mock
    private IClientOrderRepository clientOrderRepository;

    @InjectMocks
    private ClientOrderServiceImpl clientOrderService;

    LocalDate specificDate = LocalDate.of(2023, 9, 21);

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllClientOrder() {
        List<ClientOrder> clients = new ArrayList<>();

        clients.add(new ClientOrder(1L, true, specificDate, null));
        clients.add(new ClientOrder(2L, true, specificDate, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(clientOrderRepository.findAll(pageable)).thenReturn(new PageImpl<>(clients));

        Page<ClientOrder> result = clientOrderService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindClientOrderById_ExistingClientOrder() {
        ClientOrder clientOrder = new ClientOrder();
        when(clientOrderRepository.findById(1L)).thenReturn(Optional.of(clientOrder));

        ClientOrder result = clientOrderService.findById(1L);

        assertEquals(clientOrder, result);
    }

    @Test
    public void testFindClientOrderById_NonExistingClientOrder() {
        when(clientOrderRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> clientOrderService.findById(1L));
    }

    @Test
    public void testAddClientOrder() {
        ClientOrder clientOrder = new ClientOrder(1L, true, specificDate, null);
        when(clientOrderRepository.save(any(ClientOrder.class))).thenReturn(clientOrder);

        ClientOrder result = clientOrderService.add(clientOrder);

        assertEquals(clientOrder, result);
    }

    @Test
    public void testUpdateClientOrder() {
        ClientOrder existingClientOrder = new ClientOrder(1L, true, specificDate, null);
        ClientOrder updatedClientOrder = new ClientOrder(1L, false, specificDate, null);

        when(clientOrderRepository.findById(1L)).thenReturn(Optional.of(existingClientOrder));
        when(clientOrderRepository.save(any(ClientOrder.class))).thenReturn(updatedClientOrder);

        ClientOrder result = clientOrderService.update(updatedClientOrder, 1L);

        assertEquals(updatedClientOrder, result);
    }

    @Test
    public void testDeleteClientOrder() {
        Long clientOrderId = 1L;

        clientOrderService.deleteLog(clientOrderId);

        verify(clientOrderRepository, times(1)).deleteById(clientOrderId);
    }

}
