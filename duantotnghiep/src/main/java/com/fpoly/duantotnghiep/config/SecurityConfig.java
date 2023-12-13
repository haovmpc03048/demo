package com.fpoly.duantotnghiep.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import com.fpoly.duantotnghiep.Entity.NguoiDung;
import com.fpoly.duantotnghiep.jparepository.NguoiDungRepository;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Autowired
    NguoiDungRepository nguoiDungRepository;
    @Autowired
    HttpSession session;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    // authentication
    public UserDetailsService userDetailsService(PasswordEncoder pe) {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                NguoiDung userInfo = nguoiDungRepository.findByTaiKhoan(username);
                String password = userInfo.getMatKhau();
                String roles = userInfo.getChucVu();
                Boolean xacminh = userInfo.isXac_minh();
                session.setAttribute("user", userInfo);
                if (userInfo.getChucVu().equals("true")) {
                    session.setAttribute("admin", userInfo);
                }
                return User.withUsername(username).password(pe.encode(password)).roles(roles).accountExpired(!xacminh)
                        .build();
            }
        };
    }

    @Bean
    public AuthenticationFailureHandler customAuthenticationFailureHandler() {
        return new CustomAuthenticationFailureHandler();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeHttpRequests().requestMatchers("/Admin/**").hasRole("true")
                .and().authorizeHttpRequests().requestMatchers("/courseOnline/video/**")
                .authenticated().and()
                .authorizeHttpRequests().anyRequest().permitAll().and().exceptionHandling()
                .accessDeniedPage("/courseOnline/index").and().formLogin().loginPage("/courseOnline/dangnhap")
                .loginProcessingUrl("/login").defaultSuccessUrl("/courseOnline/index", true)
                .failureHandler(customAuthenticationFailureHandler())
                .and().logout().logoutUrl("/logoff").logoutSuccessUrl("/courseOnline/index").and().oauth2Login()
                .loginPage("/auth/login/form")
                .defaultSuccessUrl("/oauth2/login/success", true)
                .failureUrl("/auth/login/error")
                .authorizationEndpoint().baseUri("/oauth2/authorization");
        return http.build();

    }

}
