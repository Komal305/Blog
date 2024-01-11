package com.blog.project.services.impl;

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

import com.blog.project.entities.Category;
import com.blog.project.entities.Post;
import com.blog.project.entities.User;
import com.blog.project.exceptions.ResourceNotFoundException;
import com.blog.project.payloads.PostDto;
import com.blog.project.payloads.PostResponce;
import com.blog.project.repositories.CategoryRepo;
import com.blog.project.repositories.PostRepo;
import com.blog.project.repositories.UserRepo;
import com.blog.project.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	
	@Override
	public PostDto createPost(PostDto postDto, Integer userId, Integer categoryId) {
		User user=this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "User Id", userId));
		Category category=this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "Category Id", categoryId));
		
		Post post = this.modelMapper.map(postDto, Post.class);
		post.setImageName("komal.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post newPost= this.postRepo.save(post);
		return this.modelMapper.map(newPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("POst", "Post Id", postId));
		post.setContent(postDto.getContent());
		post.setTitle(postDto.getTitle());
		post.setImageName(postDto.getImageName());
		
		Post updatePost=this.postRepo.save(post);
		return this.modelMapper.map(updatePost, PostDto.class);
	}

	@Override
	public void deletePost(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "Post Id", postId));
		this.postRepo.delete(post);
	}

	@Override
	public PostResponce getAllPost(Integer pageNumber,Integer pageSize, String sortBy, String sortDir) {
	
		Sort sort=(sortDir.equalsIgnoreCase("asc"))? Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable p=PageRequest.of(pageNumber, pageSize,sort);
		Page<Post> pagePost=this.postRepo.findAll(p);
		List<Post> findAllPost=pagePost.getContent();
		List<PostDto> postDtos= findAllPost.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		PostResponce postResponce= new PostResponce();
		postResponce.setContent(postDtos);
		postResponce.setPageNumber(pagePost.getNumber());
		postResponce.setPageSize(pagePost.getSize());
		postResponce.setTotalElements(pagePost.getTotalElements());
		postResponce.setLastPage(pagePost.isLast());
		postResponce.setTotalPages(pagePost.getTotalPages());
		
		return postResponce;
	}

	@Override
	public PostDto getPostById(Integer postId) {
		Post post=this.postRepo.findById(postId).orElseThrow(()-> new ResourceNotFoundException("post", "post id", postId));
		return this.modelMapper.map(post, PostDto.class);
	}

	@Override
	public List<PostDto> getPostByCategory(Integer categoryId) {
		Category category= this.categoryRepo.findById(categoryId).orElseThrow(
				()->new ResourceNotFoundException("Category", "Category Id", categoryId));
		List<Post> posts=this.postRepo.findByCategory(category);
		return posts.stream().map((post)-> this.modelMapper.map(posts, PostDto.class)).collect(Collectors.toList());
		
	}

	@Override
	public List<PostDto> getPostByUser(Integer userId) {
		User user=this.userRepo.findById(userId).orElseThrow(
				()-> new ResourceNotFoundException("user", "user Id", userId));
		List<Post> users=this.postRepo.findByUser(user);
		List<PostDto> postDtos= users.stream().map((post)-> this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPost(String keyword) {
		List<Post> posts=this.postRepo.searchByTitle(keyword);
		List<PostDto> postDtos= posts.stream().map((post)->this.modelMapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postDtos;
	}

	
}
