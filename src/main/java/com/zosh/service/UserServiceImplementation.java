package com.zosh.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.zosh.config.JwtProvider;
import com.zosh.exception.UserException;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;

@Service
public class UserServiceImplementation implements UserService {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	
	public UserServiceImplementation(UserRepository userRepository,JwtProvider jwtProvider) {
		
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		
	}

	@Override
	public User findUserById(Long userId) throws UserException {
		Optional<User> user=userRepository.findById(userId);
		
		if(user.isPresent()){
			return user.get();
		}
		throw new UserException("user not found with id "+userId);
	}

	@Override
	public User findUserProfileByJwt(String jwt) throws UserException {
		System.out.println("user service");
		String email=jwtProvider.getEmailFromJwtToken(jwt);
		
		System.out.println("email"+email);
		
		User user=userRepository.findByEmail(email);
		
		
		
		if(user==null) {
			throw new UserException("user not exist with email "+email);
		}
		System.out.println("email user"+user.getEmail());
		return user;
	}

}