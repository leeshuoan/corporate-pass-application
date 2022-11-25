package com.oopproject.corporatepass;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.oopproject.corporatepass.controller.jwt.JwtTokenFilter;
import com.oopproject.corporatepass.repository.UserRepository;


@Configuration
@EnableWebSecurity
public class ApplicationSecurity {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private JwtTokenFilter jwtTokenFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserDetailsService() {

            @Override
            public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
                return userRepo.findByEmailEquals(username)
                        .orElseThrow(
                                () -> new UsernameNotFoundException("User " + username + " not found"));
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authConfig) throws Exception {
        return authConfig.getAuthenticationManager();
    }

    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http.cors();
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                // TODO: remove user/all, user/add and corporatePass from permitAll and shift each to their respective roles 
                .antMatchers("/api/v1/auth/login", "/api/v1/auth/logout", "/api/v1/user/add", "/api/v1/user/register", "/api/v1/user/verify-otp", "/api/v1/user/all", "/api/v1/user/all-with-role", "/api/v1/corporatePass/**", "/api/v1/attractions/all", "/api/v1/email/**", "/api/v1/pdf/generatepdf", "/api/v1/limits/**").permitAll()
                .antMatchers("/api/v1/auth/employee").hasAnyAuthority("employee", "admin")
                .antMatchers("/api/v1/auth/admin", "/api/v1/report/**", "/api/v1/attractions/updateAttraction", "/api/v1/admin/**").hasAuthority("admin")
                .antMatchers("/api/v1/auth/gop").hasAnyAuthority("gop", "admin")
                .antMatchers("/api/v1/employee").hasAnyAuthority("employee", "admin")
                .anyRequest().authenticated();

        http.exceptionHandling()
                .authenticationEntryPoint(
                        (request, response, ex) -> {
                            response.sendError(
                                    HttpServletResponse.SC_UNAUTHORIZED,
                                    ex.getMessage());
                        });

        http.addFilterBefore(jwtTokenFilter, UsernamePasswordAuthenticationFilter.class);
        
        return http.build();
    }
}
