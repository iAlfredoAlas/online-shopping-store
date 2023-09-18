package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.User;

import java.util.List;

public interface IUserRespository extends IGenericRepository<User, Long>{

    List<User> findByIsUserActive(Boolean isUserActive);

}
