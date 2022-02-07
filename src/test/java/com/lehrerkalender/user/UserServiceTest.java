package com.lehrerkalender.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

//@SpringBootTest(classes = {BCryptPasswordEncoder.class})
//kümmert sich u.a. um die Initialisierung der Mocks
@ExtendWith(MockitoExtension.class)
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

    @ParameterizedTest
    @MethodSource("createTestUser")
    void shouldRegisterActiveUserWithRolesAndEncryptedPassword(User user) {
        //auch als Feld mit Annotation @Captor möglich
        ArgumentCaptor<User> userCaptor = ArgumentCaptor.forClass(User.class);

        underTest.registerUser(user);

        verify(userRepository, times(1)).save(userCaptor.capture());

        User capturedUser = userCaptor.getValue();
        assertThat(capturedUser).isEqualTo(user);
        assertThat(user.isActive()).isTrue();
        assertThat(user.getRoles()).contains("USER");
    }

    private static List<User> createTestUser() {
        User user1 = new User();
        user1.setPassword("test1");

        User user2 = new User();
        user2.setPassword("");

        return List.of(user1, user2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"password", ""})
    @DisplayName("Soll ein verschlüsseltes Passwort zurückgeben")
    void shouldEncryptPassword(String rawPassword) {
         when(passwordEncoder.encode(rawPassword)).thenReturn("thisGotEncoded");

         String encryptedPassword = underTest.encryptPassword(rawPassword);

         assertThat(encryptedPassword).isNotEqualTo(rawPassword).isNotNull();
    }

    @Test
    @DisplayName("Soll zurückgeben, ob ein Account mit der E-Mail bereits existiert.")
    void shouldReturnIfMailIsTaken() {
        String email = "testmail";
        when(userRepository.existsByEmailIsLike(email))
                .thenReturn(false)
                .thenReturn(true);

        boolean bool = underTest.mailIsTaken(email);
        assertThat(bool).isFalse();

        bool = underTest.mailIsTaken(email);
        assertThat(bool).isTrue();

        verify(userRepository, times(2)).existsByEmailIsLike(anyString());
    }

    @Test
    @DisplayName("Soll zurückgeben, ob ein Account mit dem Usernamen bereits existiert.")
    void shouldReturnIfUsernameIsTaken() {
        String uniqueUser = "uniqueUser";
        when(userRepository.existsByUsernameIsLike(uniqueUser))
                .thenReturn(false);

        String takenUser = "takenUser";
        when(userRepository.existsByUsernameIsLike(takenUser)).thenReturn(true);

        boolean bool = underTest.usernameIsTaken(uniqueUser);
        assertThat(bool).isFalse();

        bool = underTest.usernameIsTaken(takenUser);
        assertThat(bool).isTrue();
        verify(userRepository, times(2)).existsByUsernameIsLike(anyString());
    }
}