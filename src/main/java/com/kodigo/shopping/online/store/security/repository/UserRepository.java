package com.kodigo.shopping.online.store.security.repository;

import com.kodigo.shopping.online.store.models.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query("SELECT u FROM User u WHERE u.userName = :userName")
    User findByUser(@Param("userName") String userName);

    User findByEmail(String email);

    User findByUserName(String nombreUsuario);
}

