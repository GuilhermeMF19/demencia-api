package com.gmf.demencia_api.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.gmf.demencia_api.repositories.UserRepository;

@Service
public class AuthorizationService implements UserDetailsService {

	@Autowired
	UserRepository userRepository;
	
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserDetails user = userRepository.findByLogin(username);
	    if (user == null) {
	        throw new UsernameNotFoundException("User not found: " + username);
	    }
	    return user;
	}
}
