package com.blog.apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer> {

}
