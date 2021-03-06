package com.rejigl.app.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("{noop}password").roles("USER").and()
                .withUser("admin").password("{noop}password").roles("USER", "ADMIN");
    }

   /* @Bean
    public UserDetailsService userDetailsService() {

        User.UserBuilder users = User.withDefaultPasswordEncoder();
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(users.username("user").password("password").roles("USER").build());
        manager.createUser(users.username("admin").password("password").roles("USER", "ADMIN").build());
        return manager;

    }*/

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().and().authorizeRequests()
                .antMatchers("/loginh2-console").permitAll()
                .antMatchers("/surveys/**").hasRole("USER")
                .antMatchers("/users/**").hasRole("USER")
                .antMatchers("/**").hasRole("ADMIN")
                .anyRequest().authenticated()
                .and().formLogin().and()
                .httpBasic();
        http.csrf().disable();
        http.headers().frameOptions().disable();
    }

}
