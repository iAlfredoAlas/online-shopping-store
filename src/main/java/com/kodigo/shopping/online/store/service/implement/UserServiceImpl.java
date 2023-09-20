package com.kodigo.shopping.online.store.service.implement;

import com.kodigo.shopping.online.store.models.User;
import com.kodigo.shopping.online.store.repository.IUserRespository;
import com.kodigo.shopping.online.store.service.IUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserServiceImpl implements IUserService {


    @Autowired
    private IUserRespository userRespository;

    private PasswordEncoder passwordEncoder;

    @Override
    public Page<User> getAll(Pageable pageable) {
        log.info("Show all data");
        return userRespository.findAll(pageable);
    }

    @Override
    public Page<User> findCustom(Pageable pageable, Boolean flat) {
        log.info("Show actives");
        return userRespository.findByIsUserActive(pageable, flat);
    }

    @Override
    public User findById(Long id) {
        log.info("Show by id");
        return userRespository.findById(id).orElseThrow(() -> new EmptyResultDataAccessException("Not found", 404));
    }

    @Override
    public User add(User model) {
        log.info("Save User");
        return userRespository.save(model);
    }

    @Override
    public User update(User model, Long id) {
        log.info("Update User");
        User objUser = userRespository.findById(id).get();
        objUser.setUserName(model.getUserName());
        if (model.getPassword() != null && !model.getPassword().isBlank()) {
            objUser.setPassword(passwordEncoder.encode(model.getPassword()));
        }
        objUser.setUserMail(model.getUserMail());
        objUser.setIsUserActive(model.getIsUserActive());
        objUser.setRolList(model.getRolList());
        return userRespository.save(objUser);
    }

    @Override
    public void deleteLog(Long id) {
        log.info("Delete User");
        userRespository.deleteById(id);
    }
}
