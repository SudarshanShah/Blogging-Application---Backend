package com.blog.apis.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.blog.apis.entities.Category;
import com.blog.apis.entities.Post;
import com.blog.apis.entities.User;
import com.blog.apis.exceptions.ResourceNotFoundException;
import com.blog.apis.payloads.PostDto;
import com.blog.apis.payloads.PostResponse;
import com.blog.apis.repositories.CategoryRepo;
import com.blog.apis.repositories.PostRepo;
import com.blog.apis.repositories.UserRepo;
import com.blog.apis.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo repo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		
		User user = userRepo.findById(userId)
						    .orElseThrow(() -> new ResourceNotFoundException("User", " userId ", userId));
		
		Category category = categoryRepo.findById(categoryId)
										.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		Post post = this.mapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.repo.save(post);
		
		return this.mapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " postId ", postId));
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.repo.save(post);
		return this.mapper.map(updatedPost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.repo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", " postId ", postId));
		this.repo.delete(post);
	}

	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
		// get the Sort object
		// sortBy -> it sorts by given field
		// sortDir -> it sorts in ascending/descending order
		Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		
		// get a pageable object
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		
		// get Pages of Posts
		Page<Post> pagePost = this.repo.findAll(pageable);
		// fetch content from Pages
		List<Post> posts = pagePost.getContent();
		List<PostDto> postDtos = posts.stream()
									  .map(post -> this.mapper.map(post, PostDto.class))
									  .collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		return postResponse;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post = this.repo.findById(postId)
							 .orElseThrow(() -> new ResourceNotFoundException("Post", " postId ", postId));
		return this.mapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostsByCategory(Integer categoryId) {
		
		// finding category by categoryId
		Category category = this.categoryRepo
								.findById(categoryId).orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		
		// finding posts by category
		List<Post> posts = this.repo.findByCategory(category);
		
		// converting 'List of Posts' objects to 'List of PostDtos' objects.
		List<PostDto> postDtos = posts.stream().map(post -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	@Override
	public List<PostDto> getPostsByUser(Integer userId) {
		
		// finding user by userId
		User user = this.userRepo.findById(userId)
								 .orElseThrow(() -> new ResourceNotFoundException("User", " userId ", userId));
		
		// finding posts by user
		List<Post> posts = this.repo.findByUser(user);
		
		// converting 'List of Posts' objects to 'List of PostDtos' objects.
		List<PostDto> postDtos = posts.stream().map(post -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		
		return postDtos;
	}

	// search posts by title
	@Override
	public List<PostDto> searchPosts(String keyword) {
		List<Post> posts = this.repo.searchByTitle("%"+keyword+"%");
		List<PostDto> postDtos = posts.stream().map(post -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

}
