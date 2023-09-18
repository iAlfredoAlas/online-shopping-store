package com.kodigo.shopping.online.store.repository;

import com.kodigo.shopping.online.store.models.Rol;

import java.util.List;

public interface IRolRepository extends IGenericRepository<Rol, Long>{

    List<Rol> findByIsRolActive(Boolean isRolActive);

}
