package com.example.telecare.filter;

import com.example.telecare.exception.ForbiddenException;
import com.example.telecare.security.MyUserDetailsService;
import com.example.telecare.utils.JwtTokenUtil;
import com.example.telecare.utils.ProjectStorage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class CustomAuthorizationFilter extends OncePerRequestFilter {

    @Autowired
    private MyUserDetailsService userDetailsService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException, ForbiddenException {
       if(request.getServletPath().equals("/api/v1/auth/login")){
           filterChain.doFilter(request,response);
       }else {
           final String authorizationHeader = request.getHeader(ProjectStorage.AUTHORIZATION);
           String username = null;
           String jwt ;
           try {
               if (authorizationHeader != null && authorizationHeader.startsWith(ProjectStorage.BEARER)) {
                   jwt = authorizationHeader.substring(7);
                   username = jwtTokenUtil.getUsernameFromToken(jwt);
               }
               if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                   UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                           userDetails, null, userDetails.getAuthorities());
                   usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
               }

           } catch (Exception e) {
               logger.error(e);
           }
           filterChain.doFilter(request, response);
       }

    }
}
