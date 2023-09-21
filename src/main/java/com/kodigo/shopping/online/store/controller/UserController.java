package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.User;
import com.kodigo.shopping.online.store.models.dto.UserDTO;
import com.kodigo.shopping.online.store.models.dto.UserNoPassDTO;
import com.kodigo.shopping.online.store.service.IUserService;
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
@RequestMapping("/user")
public class UserController implements IUserController<UserDTO, Long, UserNoPassDTO> {

    @Autowired
    private IUserService userService;

    @Autowired
    private ModelMapper mapper;


    @Override
    public ResponseEntity<Page<UserNoPassDTO>> getPage(Pageable pageable) {
        Page<User> userPage = userService.getAll(pageable);
        Page<UserNoPassDTO> userDTOPage = userPage.map(user -> mapper.map(user, UserNoPassDTO.class));
        return ResponseEntity.ok(userDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            User user = userService.findById(id);
            if (user != null) {
                UserNoPassDTO userNoPassDTO = mapper.map(user, UserNoPassDTO.class);
                return ResponseEntity.ok(userNoPassDTO);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<UserNoPassDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<User> userPage = userService.findCustom(pageable, filter);
        Page<UserNoPassDTO> userDTOPage = userPage.map(user -> mapper.map(user, UserNoPassDTO.class));
        return ResponseEntity.ok(userDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, UserDTO model) {
        try {
            User updatedUser = userService.update(mapper.map(model, User.class), id);
            if (updatedUser != null) {
                return ResponseEntity.ok(updatedUser);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(UserDTO model) {
        try {
            return ResponseFactory.responseCreated(userService.add(mapper.map(model, User.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        userService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
