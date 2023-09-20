package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Rol;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IRolRepository extends IGenericRepository<Rol, Long>{

    Page<Rol> findByIsRolActive(Pageable pageable, Boolean isRolActive);

}
