package com.blog.apis.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.blog.apis.exceptions.ApiException;
import com.blog.apis.payloads.JWTAuthRequest;
import com.blog.apis.payloads.JWTAuthResponse;
import com.blog.apis.payloads.UserDto;
import com.blog.apis.security.JWTTokenHelper;
import com.blog.apis.services.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/auth/")
@Slf4j
public class AuthController {
	
	@Autowired
	private JWTTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JWTAuthResponse> createToken(@RequestBody JWTAuthRequest jwtAuthRequest) throws Exception {
		// authenticate credentials
		this.authenticate(jwtAuthRequest.getUsername(), jwtAuthRequest.getPassword());
		
		// get user details
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtAuthRequest.getUsername());
		
		// get token from user details
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		// set the response as JWT Token
		JWTAuthResponse response = new JWTAuthResponse();
		response.setToken(token);
		
		return new ResponseEntity<JWTAuthResponse>(response, HttpStatus.OK);
	}

	// method to authenticate user credentials
	private void authenticate(String username, String password) throws Exception {

		// we get user's credentials token
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
			
		// authenticate credentials using Auth Manager
		try {
			
			this.authenticationManager.authenticate(authenticationToken);
		} catch (BadCredentialsException e) {
			log.info("Invalid details");
			throw new ApiException("Invalid username or password!");
		}

	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> registerUser(@RequestBody UserDto userDto) {
		UserDto newUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
	}
}
