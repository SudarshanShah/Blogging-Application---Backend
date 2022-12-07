package com.blog.apis;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.blog.apis.config.AppConstants;
import com.blog.apis.entities.Role;
import com.blog.apis.repositories.RoleRepo;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class BlogAppApisApplication implements CommandLineRunner {
	
	@Autowired
	private RoleRepo roleRepo;

	public static void main(String[] args) {
		SpringApplication.run(BlogAppApisApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	@Override
	public void run(String... args) throws Exception {
		
		try {
			Role role1 = new Role();
			role1.setRoleId(AppConstants.ADMIN_USER);
			role1.setName("ROLE_ADMIN");	
			
			Role role2 = new Role();
			role2.setRoleId(AppConstants.NORMAL_USER);
			role2.setName("ROLE_USER");
			
			List<Role> roles = List.of(role1, role2);
			List<Role> results = roleRepo.saveAll(roles);
			
			results.forEach(role -> log.info(role.toString()));
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
