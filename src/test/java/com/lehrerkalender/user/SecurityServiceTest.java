package com.lehrerkalender.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SecurityServiceTest {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    private SecurityService underTest;

    private String password;

    @BeforeEach
    void setUp() {
        password = "password";

        User user = new User();
        user.setUsername("steve");
        user.setPassword(password);
        user.setEmail("test@mail.de");

        userService.registerUser(user);
    }

    @Test
    void shouldAutoLogin() {

        UserDetails userDetails = userDetailsService.loadUserByUsername("steve");

        Authentication requestAuthenticated = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        Authentication result = this.authenticationManager.authenticate(requestAuthenticated);

        assertThat(result.isAuthenticated()).isTrue();
    }
}