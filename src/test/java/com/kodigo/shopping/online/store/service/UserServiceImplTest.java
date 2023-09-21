package com.kodigo.shopping.online.store.service;
import com.kodigo.shopping.online.store.models.User;
import com.kodigo.shopping.online.store.repository.IUserRespository;
import com.kodigo.shopping.online.store.service.implement.UserServiceImpl;
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
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceImplTest {

    @Mock
    private IUserRespository userRespository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllUsers() {
        List<User> users = new ArrayList<>();

        users.add(new User(1L, "user1", "password1", "user1@example.com", true, null));
        users.add(new User(2L, "user2", "password2", "user2@example.com", true, null));

        Pageable pageable = PageRequest.of(0, 10);

        when(userRespository.findAll(pageable)).thenReturn(new PageImpl<>(users));

        Page<User> result = userService.getAll(pageable);

        assertEquals(2, result.getContent().size());
    }

    @Test
    public void testFindUserById_ExistingUser() {
        User user = new User();
        when(userRespository.findById(1L)).thenReturn(Optional.of(user));

        User result = userService.findById(1L);

        assertEquals(user, result);
    }

    @Test
    public void testFindUserById_NonExistingUser() {
        when(userRespository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EmptyResultDataAccessException.class, () -> userService.findById(1L));
    }

    @Test
    public void testAddUser() {
        User user = new User(1L, "user1", "password1", "user1@example.com", true, null);
        when(passwordEncoder.encode(any(String.class))).thenReturn("hashedPassword");
        when(userRespository.save(any(User.class))).thenReturn(user);

        User result = userService.add(user);

        assertEquals(user, result);
    }

    @Test
    public void testUpdateUser() {
        User existingUser = new User(1L, "user1", "password1", "user1@example.com", true, null);
        User updatedUser = new User(2L, "user2", "password2", "user2@example.com", true, null);

        when(userRespository.findById(1L)).thenReturn(Optional.of(existingUser));
        when(passwordEncoder.encode(any(String.class))).thenReturn("hashedPassword");
        when(userRespository.save(any(User.class))).thenReturn(updatedUser);

        User result = userService.update(updatedUser, 1L);

        assertEquals(updatedUser, result);
    }

    @Test
    public void testDeleteUser() {
        Long userId = 1L;

        userService.deleteLog(userId);

        verify(userRespository, times(1)).deleteById(userId);
    }
}

