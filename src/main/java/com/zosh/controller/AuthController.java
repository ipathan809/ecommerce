//package com.zosh.controller;
//
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.zosh.config.JwtProvider;
//import com.zosh.exception.UserException;
//import com.zosh.model.Cart;
//import com.zosh.model.User;
//import com.zosh.repository.UserRepository;
//import com.zosh.request.LoginRequest;
//import com.zosh.response.AuthResponse;
//import com.zosh.service.CartService;
//import com.zosh.service.CustomUserServiceImplementation;
//
//import javax.validation.Valid;
//
//@RestController
//@RequestMapping("/auth")
//public class AuthController {
//
//	private UserRepository userRepository;
//	private PasswordEncoder passwordEncoder;
//	private JwtProvider jwtTokenProvider;
//	private CustomUserServiceImplementation customUserDetails;
//	private CartService cartService;
//	
//	public AuthController(UserRepository userRepository,PasswordEncoder passwordEncoder,JwtProvider jwtTokenProvider,CustomUserServiceImplementation customUserDetails,CartService cartService) {
//		this.userRepository=userRepository;
//		this.passwordEncoder=passwordEncoder;
//		this.jwtTokenProvider=jwtTokenProvider;
//		this.customUserDetails=customUserDetails;
//		this.cartService=cartService;
//	}
//	
//	@PostMapping("/signup")
//	public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException{
//		
//		  	String email = user.getEmail();
//	        String password = user.getPassword();
//	        String firstName=user.getFirstName();
//	        String lastName=user.getLastName();
//	        
//	        User isEmailExist=userRepository.findByEmail(email);
//
//	        // Check if user with the given email already exists
//	        if (isEmailExist!=null) {
//	        // System.out.println("--------- exist "+isEmailExist).getEmail());
//	        	
//	            throw new UserException("Email Is Already Used With Another Account");
//	        }
//
//	        // Create new user
//			User createdUser= new User();
//			createdUser.setEmail(email);
//			createdUser.setFirstName(firstName);
//			createdUser.setLastName(lastName);
//	        createdUser.setPassword(passwordEncoder.encode(password));
//	        
//	        
//	        
//	        User savedUser= userRepository.save(createdUser);
//	        
//	        cartService.createCart(savedUser);
//
//	        Authentication authentication = new UsernamePasswordAuthenticationToken(email, password);
//	        SecurityContextHolder.getContext().setAuthentication(authentication);
//	        
//	        String token = jwtTokenProvider.generateToken(authentication);
//
//	        AuthResponse authResponse= new AuthResponse();
//			
//	        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
//		
//	}
//	
//	@PostMapping("/signin")
//    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
//        String username = loginRequest.getEmail();
//        String password = loginRequest.getPassword();
//        
//        System.out.println(username +" ----- "+password);
//        
//        Authentication authentication = authenticate(username, password);
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        
//        
//        String token = jwtTokenProvider.generateToken(authentication);
//        AuthResponse authResponse= new AuthResponse();
//		
//		authResponse.setMessage("succ");
//		authResponse.setJwt(token);
//		
//        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
//    }
//	
//	private Authentication authenticate(String username, String password) {
//        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
//        
//        System.out.println("sign in userDetails - "+userDetails);
//        
//        if (userDetails == null) {
//        	System.out.println("sign in userDetails - null " + userDetails);
//            throw new BadCredentialsException("Invalid username or password");
//        }
//        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
//        	System.out.println("sign in userDetails - password not match " + userDetails);
//            throw new BadCredentialsException("Invalid username or password");
//        }
//        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
//    }
//}



package com.zosh.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zosh.config.JwtProvider;
import com.zosh.exception.UserException;
import com.zosh.model.Cart;
import com.zosh.model.User;
import com.zosh.repository.UserRepository;
import com.zosh.request.LoginRequest;
import com.zosh.response.AuthResponse;
import com.zosh.service.CartService;
import com.zosh.service.CustomUserServiceImplementation;


@RestController
@RequestMapping("/auth")
public class AuthController {
	
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private CustomUserServiceImplementation customUserService;
	private CartService cartService;
	
	public AuthController(UserRepository userRepository, JwtProvider jwtProvider,PasswordEncoder passwordEncoder,
			CustomUserServiceImplementation customUserService,
			CartService cartService) {
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		this.customUserService=customUserService;
		this.passwordEncoder=passwordEncoder;
		this.cartService=cartService;
	}
	
	@PostMapping("/signup")
	public ResponseEntity<AuthResponse> createUserHandler(@RequestBody User user) throws UserException{
		
		  	String email = user.getEmail();
	        String password = user.getPassword();
	        String firstName=user.getFirstName();
	        String lastName=user.getLastName();
	        
	        User isEmailExist=userRepository.findByEmail(email);
	        
	        // Check if user with the given email already exists
	        if (isEmailExist!=null) {
	        // System.out.println("--------- exist "+isEmailExist).getEmail());
	        	
	            throw new UserException("Email Is Already Used With Another Account");
	        }

	        // Create new user
			User createdUser= new User();
			createdUser.setEmail(email);
			createdUser.setFirstName(firstName);
			createdUser.setLastName(lastName);
			createdUser.setPassword(passwordEncoder.encode(password));
	        
	        
	        
	        User savedUser= userRepository.save(createdUser);
	        Cart cart=cartService.createCart(savedUser);
	        
	        Authentication authentication = new UsernamePasswordAuthenticationToken(savedUser.getEmail(), savedUser.getPassword());
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        String token = jwtProvider.generateToken(authentication);

	        AuthResponse authResponse= new AuthResponse();
	        
	        authResponse.setJwt(token);
	        authResponse.setMessage("Sign Up Success");
			
	        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.CREATED);
	        
	}
	
	 @PostMapping("/login")
	 public ResponseEntity<AuthResponse> loginUserHandler(@RequestBody LoginRequest loginRequest) {
	        String username = loginRequest.getEmail();
	        String password = loginRequest.getPassword();
	        
	        
	        
	        Authentication authentication = authenticate(username, password);
	        SecurityContextHolder.getContext().setAuthentication(authentication);
	        
	        
	        String token = jwtProvider.generateToken(authentication);
	 
            AuthResponse authResponse= new AuthResponse();
	        
	        authResponse.setJwt(token);
	        authResponse.setMessage("Sign In Success");
			
	        return new ResponseEntity<AuthResponse>(authResponse,HttpStatus.OK);
	    }
	 
	 private Authentication authenticate(String username, String password) {
	        UserDetails userDetails = customUserService.loadUserByUsername(username);
	        
	        
	        
	        if (userDetails == null) {
	        	System.out.println("sign in userDetails - null " + userDetails);
	            throw new BadCredentialsException("Invalid username or password");
	        }
	        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
	        	System.out.println("sign in userDetails - password not match " + userDetails);
	            throw new BadCredentialsException("Invalid username or password");
	        }
	        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
	    }

}
