package com.blog.apis.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class Role {

	@Id
	private int roleId;
	
	private String name;
}
