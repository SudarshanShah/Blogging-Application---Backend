package com.blog.apis.payloads;

import lombok.Data;

@Data
public class JWTAuthRequest {

	private String username;
	private String password;
}
