package com.example.VegroKart.SecurityConfiguration;

import java.io.IOException;

import org.hibernate.validator.internal.util.stereotypes.Lazy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter  extends OncePerRequestFilter{

//	@Autowired
//	private JwtService jwtService;
//	
//	@Autowired
//	@Lazy
//	private UserDetailsService userDetailsService;
	
	 private JwtService jwtService;
	    private UserDetailsService userDetailsService;

	    @Autowired
	    public void setJwtService(JwtService jwtService) {
	        this.jwtService = jwtService;
	    }
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		String token = null;
		String username = null;
		String header = request.getHeader("Authorization");
		if(null != header && header.startsWith("Bearer ")) {
			token = header.substring(7);
			username = jwtService.extractUserName(token);
		}
		if(null != username && SecurityContextHolder.getContext().getAuthentication() == null) {
			UserDetails userDetails = userDetailsService.loadUserByUsername(username);
			if(jwtService.isTokenValid(token, userDetails)) {
				UsernamePasswordAuthenticationToken userToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				userToken.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(userToken);
			}
		}
		filterChain.doFilter(request, response);
	}



	
}