package com.kodigo.shopping.online.store.service;

import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.repository.IRolRepository;
import com.kodigo.shopping.online.store.service.implement.RolServiceImpl;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RolServiceImplTest {

    @Mock
    private IRolRepository rolRepository;

    @InjectMocks
    private RolServiceImpl rolService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllRols() {
        List<Rol> rols = new ArrayList<>();

        rols.add(new Rol(1L, "Admin", true, null));
        rols.add(new Rol(2L, "User", true, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(rolRepository.findAll(pageable)).thenReturn(new PageImpl<>(rols));

        Page<Rol> result = rolService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindRolById_ExistingRol() {
        Rol rol = new Rol();
        when(rolRepository.findById(1L)).thenReturn(Optional.of(rol));

        Rol result = rolService.findById(1L);

        assertEquals(rol, result);
    }

    @Test
    public void testFindRolById_NonExistingRol() {
        when(rolRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> rolService.findById(1L));
    }

    @Test
    public void testAddRol() {
        Rol rol = new Rol(1L, "Admin", true, null);
        when(rolRepository.save(any(Rol.class))).thenReturn(rol);

        Rol result = rolService.add(rol);

        assertEquals(rol, result);
    }

    @Test
    public void testUpdateRol() {
        Rol existingRol = new Rol(1L, "Admin", true, null);
        Rol updatedRol = new Rol(2L, "User", true, null);

        when(rolRepository.findById(1L)).thenReturn(Optional.of(existingRol));
        when(rolRepository.save(any(Rol.class))).thenReturn(updatedRol);

        Rol result = rolService.update(updatedRol, 1L);

        assertEquals(updatedRol, result);
    }

    @Test
    public void testDeleteRol() {
        Long rolId = 1L;

        rolService.deleteLog(rolId);

        verify(rolRepository, times(1)).deleteById(rolId);
    }
}

