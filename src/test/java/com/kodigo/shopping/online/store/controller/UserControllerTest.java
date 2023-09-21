package com.kodigo.shopping.online.store.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.kodigo.shopping.online.store.controller.UserController;
import com.kodigo.shopping.online.store.models.User;
import com.kodigo.shopping.online.store.models.dto.UserDTO;
import com.kodigo.shopping.online.store.models.dto.UserNoPassDTO;
import com.kodigo.shopping.online.store.service.IUserService;
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

class UserControllerTest {

    @Mock
    private IUserService userService;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetPage() {
        List<UserNoPassDTO> userDTOList = new ArrayList<>();

        when(userService.getAll(any(Pageable.class))).thenReturn(Page.empty());
        when(mapper.map(any(User.class), eq(UserNoPassDTO.class))).thenReturn(new UserNoPassDTO());

        ResponseEntity<Page<UserNoPassDTO>> responseEntity = userController.getPage(Mockito.mock(Pageable.class));

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_ValidId() {
        Long userId = 1L;
        UserNoPassDTO userNoPassDTO = new UserNoPassDTO();
        User user = new User();

        when(userService.findById(userId)).thenReturn(user);
        when(mapper.map(user, UserNoPassDTO.class)).thenReturn(userNoPassDTO);

        ResponseEntity<?> responseEntity = userController.getOne(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testGetOne_InvalidId() {
        Long invalidUserId = 999L;

        when(userService.findById(invalidUserId)).thenThrow(EmptyResultDataAccessException.class);

        ResponseEntity<?> responseEntity = userController.getOne(invalidUserId);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_ValidId() {
        Long userId = 1L;
        UserDTO userDTO = new UserDTO();
        User user = new User();

        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(userService.update(user, userId)).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.update(userId, userDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    @Test
    void testUpdate_InvalidId() {
        Long invalidUserId = 999L;
        UserDTO userDTO = new UserDTO();

        when(userService.update(any(User.class), eq(invalidUserId))).thenReturn(null);

        ResponseEntity<?> responseEntity = userController.update(invalidUserId, userDTO);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    void testCreate() {
        UserDTO userDTO = new UserDTO();
        User user = new User();

        when(mapper.map(userDTO, User.class)).thenReturn(user);
        when(userService.add(user)).thenReturn(user);

        ResponseEntity<?> responseEntity = userController.create(userDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @Test
    void testDelete() {
        Long userId = 1L;

        ResponseEntity<String> responseEntity = userController.delete(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}

