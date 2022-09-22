package com.zn.cms.configuration;

import com.zn.cms.user.service.CmsUserDetailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurity {

    private CmsUserDetailServiceImpl cmsUserDetailService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        authProvider.setUserDetailsService(getCmsUserDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    public CmsUserDetailServiceImpl getCmsUserDetailService() {
        return cmsUserDetailService;
    }

    @Autowired
    @Lazy
    public void setCmsUserDetailService(CmsUserDetailServiceImpl cmsUserDetailService) {
        this.cmsUserDetailService = cmsUserDetailService;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.authenticationProvider(authenticationProvider())
                .authorizeRequests()
                .anyRequest().authenticated()
//                .antMatchers("/login").permitAll()
//                .antMatchers("/users/**", "/settings/**").hasAuthority("Admin")
                .and().httpBasic();
//                .loginPage("/login")
//                .usernameParameter("email")

//                .logout().permitAll();

        http.headers().frameOptions().sameOrigin();

        return http.build();
    }



}
