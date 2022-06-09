package com.example.telecare.config;


import com.example.telecare.filter.CustomAuthenticationFilter;
import com.example.telecare.filter.CustomAuthorizationFilter;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.utils.ProjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


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
        http.csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("/api/v1/auth/login/**").permitAll()

                .antMatchers("/api/v1/auth/register/**").permitAll()

                .antMatchers("/api/v1/auth/changePassword/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN,ProjectStorage.ROLE_DOCTOR)

                .antMatchers("/api/v1/patient/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN,ProjectStorage.ROLE_DOCTOR)

                .antMatchers(HttpMethod.PUT,"/api/v1/patient/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT)

                .antMatchers("/api/v1/ethnic/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN,ProjectStorage.ROLE_DOCTOR)

                .antMatchers("/api/v1/relative/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT)
                .antMatchers( "/api/v1/address/**").hasAnyAuthority(ProjectStorage.ROLE_PATIENT,
                        ProjectStorage.ROLE_ADMIN,ProjectStorage.ROLE_DOCTOR)

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
