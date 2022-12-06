package com.blog.apis.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank
	@Size(min = 4, message = "Title should be of minimum 4 characters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min = 10, message = "Description should be of minimum 10 characters")
	private String categoryDescription;
}
