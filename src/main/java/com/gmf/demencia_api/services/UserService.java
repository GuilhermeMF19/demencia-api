package com.gmf.demencia_api.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.gmf.demencia_api.domain.user.User;
import com.gmf.demencia_api.exceptions.NotFoundException;
import com.gmf.demencia_api.repositories.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserService {
	@Autowired
	UserRepository userRepository;
	
	public Page<User> findAll(Pageable pageable) {
		return userRepository.findAll(pageable);
	}
	
	public User findById(String id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new NotFoundException(id));
	}
	
	@Transactional
	public User create(User obj) {
		return userRepository.save(obj);
	}
	
	@Transactional
	public void deleteById(String id) {
	    if (!userRepository.existsById(id)) {
	        throw new NotFoundException(id);
	    }
	    
	    try {
	        userRepository.deleteById(id);
	    } catch (EmptyResultDataAccessException e) {
	        throw new NotFoundException(id);
	    }
	}
	
	@Transactional
	public User update(String id, User obj) {
		Optional<User> optionalEntity = userRepository.findById(id);
		User entity = optionalEntity.orElseThrow(() -> new NotFoundException(id));
		
		updateData(entity, obj);
		return userRepository.save(entity);			
	}

	private void updateData(User entity, User obj) {
		if (obj.getLogin() != null) {
	        entity.setLogin(obj.getLogin());
	    }
	    if (obj.getPassword() != null) {
	        entity.setPassword(obj.getPassword());
	    }
	    if (obj.getFullName() != null) {
	        entity.setFullName(obj.getFullName());
	    }
	    if (obj.getRole() != null) {
	    	entity.setRole(obj.getRole());
	    }
	}
}
