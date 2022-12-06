package com.blog.apis.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.User;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.UserDto;
import com.blog.apis.repositories.UserRepo;
import com.blog.apis.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		// convert from dto to user object
		User user = this.dtoToUser(userDto);
		// save the user
		User savedUser = this.userRepo.save(user);
		// return the saved user by converting to dto.
		return this.userToDto(savedUser);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		// find the user
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		// update the user info
		user.setName(userDto.getName());
		user.setEmail(userDto.getEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		// save the user info
		User updatedUser = this.userRepo.save(user);
		// return dto object.
		return this.userToDto(updatedUser);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		// find the user
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		// return the user
		return this.userToDto(user);
	}

	@Override
	public List<UserDto> getAllUsers() {
		// get all users
		List<User> users = this.userRepo.findAll();

		// convert to dto objects
		List<UserDto> userDtos = users.stream().map(user -> this.userToDto(user)).collect(Collectors.toList());

		return userDtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		// find the user
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", " id ", userId));

		// delete the user
		this.userRepo.delete(user);
	}

	private User dtoToUser(UserDto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		return user;
	}
 
	private UserDto userToDto(User user) {
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return userDto;
	}

}
