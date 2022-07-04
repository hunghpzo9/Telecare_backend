package com.example.telecare.config;


import com.example.telecare.filter.CustomAuthorizationFilter;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.utils.ProjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {



    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @Autowired
    CustomAuthorizationFilter customAuthorizationFilter;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(List.of("Authorization", "Cache-Control", "Content-Type"));
        corsConfiguration.addAllowedOriginPattern("*");
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT","OPTIONS","PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        // You can customize the following part based on your project, it's only a sample
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/auth/changePassword/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers("/api/v1/relative/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT)
                .antMatchers("/api/v1/patient/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers("/api/v1/doctor/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers(HttpMethod.PUT, "/api/v1/patient/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT)
                .antMatchers("/api/v1/ethnic/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET,"/api/v1/achievement").hasAnyAuthority(ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET,"/api/v1/experience").hasAnyAuthority(ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET,"/api/v1/doctorSpecialty").hasAnyAuthority(ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET,"/api/v1/specialty").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/agora/**").permitAll()
                .antMatchers("/api/v1/specialty/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .antMatchers("/api/v1/relative/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT, ProjectStorage.ROLE_ADMIN)
                .antMatchers("/api/v1/address/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN, ProjectStorage.ROLE_DOCTOR)
                .anyRequest().authenticated()
                .and().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.addFilterBefore(customAuthorizationFilter, UsernamePasswordAuthenticationFilter.class);

    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }
}
