package com.blog.project.services;

import java.util.List;

import com.blog.project.payloads.PostDto;
import com.blog.project.payloads.PostResponce;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId);
    PostDto updatePost(PostDto postDto, Integer postId);
    void deletePost(Integer postId);
    PostResponce getAllPost(Integer pageNumber,Integer pageSize,String sortBy, String sortDir);
    PostDto getPostById(Integer postId);
    List<PostDto> getPostByCategory( Integer categoryId);
    List<PostDto> getPostByUser(Integer userId);
    List<PostDto> searchPost(String keyword);
    
}
