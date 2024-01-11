package com.blog.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.blog.project.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}
