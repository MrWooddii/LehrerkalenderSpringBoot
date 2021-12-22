package com.lehrerkalender.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Autowired
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
    void shouldEncryptPassword() {
         String rawPassword = "test";

         String encryptedPassword = underTest.encryptPassword(rawPassword);

         assertThat(encryptedPassword).isNotEqualTo(rawPassword).isNotEmpty();
    }

    @Test
    @DisplayName("Gibt zurück, ob ein Account mit der E-Mail bereits existiert.")
    void shouldReturnIfMailIsTaken() {
        underTest.mailIsTaken(anyString());
        verify(userRepository, times(1)).existsByEmailIsLike(anyString());
    }

    @Test
    @DisplayName("Gibt zurück, ob ein Account mit dem Username bereits existiert.")
    void shouldReturnIfUsernameIsTaken() {
        underTest.usernameIsTaken(anyString());
        verify(userRepository, times(1)).existsByUsernameIsLike(anyString());
    }
}