package com.kodigo.shopping.online.store.security.service;

import com.kodigo.shopping.online.store.models.Rol;
import com.kodigo.shopping.online.store.models.User;
import com.kodigo.shopping.online.store.security.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        log.info("Loading user by username. {}", userName);
        User userResponse = userRepository.findByUserName(userName);
        if (userResponse != null) {
            log.info("User found by username successfully !!!");
            return new org.springframework.security.core.userdetails.User(userResponse.getUserName(), userResponse.getPassword(),
                    getGrantedAuthorities(userResponse.getRolList()));
        } else {
            log.error("User not found by username.");
            throw new UsernameNotFoundException(String.format("User with name%s not found.", userName));
        }
    }

    private List<GrantedAuthority> getGrantedAuthorities(List<Rol> roles) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Rol rol : roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_".concat(rol.getNameRol())));
        }
        return authorities;
    }

}
