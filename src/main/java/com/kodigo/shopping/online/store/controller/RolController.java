package com.kodigo.shopping.online.store.controller;

import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.dto.RolDTO;
import com.kodigo.shopping.online.store.service.IRolService;
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
@RequestMapping("/rol")
public class RolController implements IGenericController<RolDTO, Long>{

    @Autowired
    private IRolService rolService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public ResponseEntity<Page<RolDTO>> getPage(Pageable pageable) {
        Page<Rol> rolPage = rolService.getAll(pageable);
        Page<RolDTO> rolDTOPage = rolPage.map(rol -> mapper.map(rol, RolDTO.class));
        return ResponseEntity.ok(rolDTOPage);
    }

    @Override
    public ResponseEntity<?> getOne(Long id) {
        try {
            return ResponseEntity.ok(rolService.findById(id));
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Page<RolDTO>> getPage(Pageable pageable, Boolean filter) {
        Page<Rol> rolPage = rolService.findCustom(pageable, filter);
        Page<RolDTO> rolDTOPage = rolPage.map(rol -> mapper.map(rol, RolDTO.class));
        return ResponseEntity.ok(rolDTOPage);
    }

    @Override
    public ResponseEntity<?> update(Long id, RolDTO model) {
        try {
            Rol updatedRol = rolService.update(mapper.map(model, Rol.class), id);
            if (updatedRol != null) {
                return ResponseEntity.ok(updatedRol);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (EmptyResultDataAccessException e) {
            log.error(e.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<?> create(RolDTO model) {
        try {
            return ResponseFactory.responseCreated(rolService.add(mapper.map(model, Rol.class)));
        } catch (DataAccessException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<String> delete(Long id) {
        rolService.deleteLog(id);
        return ResponseEntity.ok("Record deleted");
    }
}
