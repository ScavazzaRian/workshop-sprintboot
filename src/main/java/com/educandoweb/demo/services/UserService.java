package com.educandoweb.demo.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import com.educandoweb.demo.entities.User;
import com.educandoweb.demo.repositories.UserRepository;
import com.educandoweb.demo.services.exceptions.DatabaseException;
import com.educandoweb.demo.services.exceptions.ResourceNotFoundException;

// @Component Registra a classe como componente do Spring
// @Repository
@Service
public class UserService {
	@Autowired
	private UserRepository userRepository;

	public List<User> findAll() {
		return userRepository.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> obj = userRepository.findById(id);
		return obj.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	
	public User insert(User user) {
		return userRepository.save(user);
	}
	
	public void delete(Long id) {
		try{
			userRepository.deleteById(id);
		} catch (EmptyResultDataAccessException e) {
			throw new ResourceNotFoundException(id);
		} catch (DataIntegrityViolationException e) {
			throw new DatabaseException(e.getMessage());
		}
	}
	
	public User update(Long id, User user) {
		User obj = userRepository.getReferenceById(id);
		obj.setName(user.getName());
		obj.setPassword(user.getPassword());
		obj.setEmail(user.getEmail());
		obj.setPhone(obj.getPhone());
		return obj;
	}
}
