package com.lehrerkalender.service;

import com.lehrerkalender.dao.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.crypto.password.PasswordEncoder;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService underTest;

    @BeforeEach
    void setUp() {
        this.underTest = new UserService(userRepository, passwordEncoder);
    }

    @Test
    @Disabled
    void registerUser() {
    }

    @Test
    @Disabled
    void encryptPassword() {
    }

    @Test
    @Disabled
    void mailIsUnique() {
    }

    @Test
    @Disabled
    void usernameIsUnique() {
    }
}