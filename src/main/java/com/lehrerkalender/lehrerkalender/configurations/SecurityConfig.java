package com.lehrerkalender.lehrerkalender.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Spring sec doc: https://docs.spring.io/spring-security/reference/servlet/index.html
    //Anleitung Security: https://labs.micromata.de/category/best-practices/tutorial-spring-security/

    private final PasswordEncoder passwordEncoder;

    //UserDetailsService wird genutzt, um username, password und GrantedAuthorities für User zu erhalten
    @Autowired
    UserDetailsService userDetailsService;


    @Autowired
    public SecurityConfig(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService);

                /*
        User.UserBuilder users = User.withDefaultPasswordEncoder();

        auth.inMemoryAuthentication()
                .withUser(users.username("john").password("test123").roles("TEACHER"))
                .withUser(users.username("mary").password("test123").roles("TEACHER"))
                .withUser(users.username("susan").password("test123").roles("ADMIN"));

                 */
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/students/**").hasRole("TEACHER")
                .antMatchers("/user/**").hasRole("TEACHER")
                .antMatchers("/resources/**").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .defaultSuccessUrl("/user/home")
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                .logout().permitAll()
                    .logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }
}
