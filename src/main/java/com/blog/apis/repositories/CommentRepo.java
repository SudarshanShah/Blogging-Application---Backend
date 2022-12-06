package com.blog.apis.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.apis.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
