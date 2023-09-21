package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.ClientOrder;
import com.kodigo.shopping.online.store.models.dto.ClientOrderDTO;
import com.kodigo.shopping.online.store.service.IClientOrderService;
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

class ClientOrderControllerTest {

    @Mock
    private IClientOrderService clientOrderService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private ClientOrderController clientOrderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<ClientOrderDTO> clientOrderDTOList = new ArrayList<>();

        when(clientOrderService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(ClientOrder.class), eq(ClientOrderDTO.class))).thenReturn(new ClientOrderDTO());

        ResponseEntity<Page<ClientOrderDTO>> responseEntity = clientOrderController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long clientOrderId = 1L;
        ClientOrderDTO clientOrderDTO = new ClientOrderDTO();
        ClientOrder clientOrder = new ClientOrder();

        when(clientOrderService.findById(clientOrderId)).thenReturn(clientOrder);
        when(mapper.map(clientOrder, ClientOrderDTO.class)).thenReturn(clientOrderDTO);

        ResponseEntity<?> responseEntity = clientOrderController.getOne(clientOrderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidClientOrderId = 999L;

        when(clientOrderService.findById(invalidClientOrderId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = clientOrderController.getOne(invalidClientOrderId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long clientOrderId = 1L;
        ClientOrderDTO clientOrderDTO = new ClientOrderDTO();
        ClientOrder clientOrder = new ClientOrder();

        when(mapper.map(clientOrderDTO, ClientOrder.class)).thenReturn(clientOrder);
        when(clientOrderService.update(clientOrder, clientOrderId)).thenReturn(clientOrder);

        ResponseEntity<?> responseEntity = clientOrderController.update(clientOrderId, clientOrderDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidClientOrderId = 999L;
        ClientOrderDTO clientOrderDTO = new ClientOrderDTO();

        when(clientOrderService.update(any(ClientOrder.class), eq(invalidClientOrderId))).thenReturn(null);

        ResponseEntity<?> responseEntity = clientOrderController.update(invalidClientOrderId, clientOrderDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        ClientOrderDTO clientOrderDTO = new ClientOrderDTO();
        ClientOrder clientOrder = new ClientOrder();

        when(mapper.map(clientOrderDTO, ClientOrder.class)).thenReturn(clientOrder);
        when(clientOrderService.add(clientOrder)).thenReturn(clientOrder);

        ResponseEntity<?> responseEntity = clientOrderController.create(clientOrderDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long clientOrderId = 1L;

        ResponseEntity<String> responseEntity = clientOrderController.delete(clientOrderId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
