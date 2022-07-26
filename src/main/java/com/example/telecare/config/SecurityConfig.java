package com.example.telecare.config;


import com.example.telecare.filter.CustomAuthorizationFilter;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.utils.Constants;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
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
        corsConfiguration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PUT", "OPTIONS", "PATCH", "DELETE"));
        corsConfiguration.setAllowCredentials(false);
        corsConfiguration.setExposedHeaders(List.of("Authorization"));

        // You can customize the following part based on your project, it's only a sample
        http.authorizeRequests().antMatchers("/**").permitAll().anyRequest()
                .authenticated().and().csrf().disable().cors().configurationSource(request -> corsConfiguration);
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/auth/**").permitAll()
                .antMatchers("/api/v1/auth/changePassword/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers("/api/v1/relative/**").hasAnyAuthority(Constants.ROLE_PATIENT)
                .antMatchers("/api/v1/patient/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers("/api/v1/doctor/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers(HttpMethod.PUT, "/api/v1/patient/**").hasAnyAuthority(Constants.ROLE_PATIENT)
                .antMatchers("/api/v1/ethnic/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET, "/api/v1/achievement").hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET, "/api/v1/experience").hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET, "/api/v1/doctorSpecialty").hasAnyAuthority(Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET, "/api/v1/specialty").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/prescription/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/medicalRecord/**").permitAll()
                .antMatchers(HttpMethod.GET, "/api/v1/agora/**").permitAll()
                .antMatchers("/api/v1/specialty/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers("/api/v1/relative/**").hasAnyAuthority(Constants.ROLE_PATIENT, Constants.ROLE_ADMIN)
                .antMatchers("/api/v1/address/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers(HttpMethod.GET,"/api/v1/payment/returnIpn").permitAll()
                .antMatchers(HttpMethod.GET,"/api/v1/payment/returnPayment").permitAll()
                .antMatchers("/api/v1/payment/**").hasAnyAuthority(Constants.ROLE_PATIENT,
                        Constants.ROLE_ADMIN, Constants.ROLE_DOCTOR)
                .antMatchers("/api/v1/notification/**").hasAnyAuthority(Constants.ROLE_PATIENT,Constants.ROLE_DOCTOR)
                .antMatchers("/api/v1/.well-known/assetlinks.json").permitAll()
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
