package com.lehrerkalender.configurations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    //Spring sec doc: https://docs.spring.io/spring-security/reference/servlet/index.html
    //Anleitung Security: https://labs.micromata.de/category/best-practices/tutorial-spring-security/

    //UserDetailsService wird genutzt, um username, password und GrantedAuthorities für User zu erhalten
    @Autowired
    UserDetailsService userDetailsService;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(getPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/students/**").hasRole("USER")
                .antMatchers("/user/register").permitAll()
                .antMatchers("/user/**").hasRole("USER")
                .antMatchers("/resources/**").permitAll()
                .antMatchers("/register").permitAll()
                .and()
                .formLogin()
                    .loginPage("/login")
                    .loginProcessingUrl("/authenticateTheUser")
                    .defaultSuccessUrl("/students/class-overview")
                    .failureUrl("/login-error")
                    .permitAll()
                .and()
                .logout().permitAll()
                    .logoutUrl("/logout")
                .and()
                .exceptionHandling().accessDeniedPage("/access-denied");
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
