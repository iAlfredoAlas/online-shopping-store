package com.kodigo.shopping.online.store.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kodigo.shopping.online.store.controller.RolController;
import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.dto.RolDTO;
import com.kodigo.shopping.online.store.service.IRolService;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class RolControllerTest {

    @Mock
    private IRolService rolService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private RolController rolController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<RolDTO> rolDTOList = new ArrayList<>();

        when(rolService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(Rol.class), eq(RolDTO.class))).thenReturn(new RolDTO());

        ResponseEntity<Page<RolDTO>> responseEntity = rolController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long rolId = 1L;
        RolDTO rolDTO = new RolDTO();
        Rol rol = new Rol();

        when(rolService.findById(rolId)).thenReturn(rol);
        when(mapper.map(rol, RolDTO.class)).thenReturn(rolDTO);

        ResponseEntity<?> responseEntity = rolController.getOne(rolId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidRolId = 999L;

        when(rolService.findById(invalidRolId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = rolController.getOne(invalidRolId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long rolId = 1L;
        RolDTO rolDTO = new RolDTO();
        Rol rol = new Rol();

        when(mapper.map(rolDTO, Rol.class)).thenReturn(rol);
        when(rolService.update(rol, rolId)).thenReturn(rol);

        ResponseEntity<?> responseEntity = rolController.update(rolId, rolDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidRolId = 999L;
        RolDTO rolDTO = new RolDTO();

        when(rolService.update(any(Rol.class), eq(invalidRolId))).thenReturn(null);

        ResponseEntity<?> responseEntity = rolController.update(invalidRolId, rolDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        RolDTO rolDTO = new RolDTO();
        Rol rol = new Rol();

        when(mapper.map(rolDTO, Rol.class)).thenReturn(rol);
        when(rolService.add(rol)).thenReturn(rol);

        ResponseEntity<?> responseEntity = rolController.create(rolDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long rolId = 1L;

        ResponseEntity<String> responseEntity = rolController.delete(rolId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}
