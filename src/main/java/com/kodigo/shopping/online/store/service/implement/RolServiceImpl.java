package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.repository.IRolRepository;
import com.kodigo.shopping.online.store.service.IRolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class RolServiceImpl implements IRolService {


    @Autowired
    private IRolRepository rolRepository;

    @Override
    public List<Rol> getAll() {
        log.info("Show all data");
        return rolRepository.findAll();
    }

    @Override
    public List<Rol> findCustom(Boolean flat) {
        log.info("Show actives");
        return rolRepository.findByIsRolActive(flat);
    }

    @Override
    public Rol findById(Long id) {
        log.info("Show by id");
        return rolRepository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public Rol add(Rol model) {
        log.info("Add Rol");
        return rolRepository.save(model);
    }

    @Override
    public Rol update(Rol model, Long id) {
        log.info("Update Rol");
        Rol objRol = rolRepository.findById(id).get();
        objRol.setNameRol(model.getNameRol());
        objRol.setIsRolActive(model.getIsRolActive());
        return rolRepository.save(objRol);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete Rol");
        rolRepository.deleteById(id);
    }
}
