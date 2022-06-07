package com.example.telecare.security;

import com.example.telecare.exception.BadRequestException;
import com.example.telecare.exception.ForbiddenException;
import com.example.telecare.repository.UserRepository;
import com.example.telecare.utils.ProjectStorage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class MyUserDetailsService implements UserDetailsService {
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordHashService passwordHashService;
    private static final Logger logger = LoggerFactory.getLogger(MyUserDetailsService.class);

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
      com.example.telecare.model.User user = userRepository.findUserByPhone(username);
        if (user == null) {
            logger.error("User not found");
            throw new UsernameNotFoundException("User not found in database");
        }
        if(user.getIsActive() == ProjectStorage.IS_NOT_ACTIVE){
            throw new ForbiddenException("Tài khoản của bạn đang chưa được kích hoạt");
        }
        else {
            String decodePass = passwordHashService.decodePasswordAlgorithm(user.getPassword());
            Collection<SimpleGrantedAuthority> authorities;
            authorities = user.getRoles()
                    .stream()
                    .map(role -> new SimpleGrantedAuthority(role.getName()))
                    .collect(Collectors.toList());
            return new User(user.getPhone(), decodePass.substring(7), authorities);
        }

    }
}
