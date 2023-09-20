package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IUserRespository extends IGenericRepository<User, Long>{

    Page<User> findByIsUserActive(Pageable pageable, Boolean isUserActive);

}
