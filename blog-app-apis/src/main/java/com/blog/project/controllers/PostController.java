package com.blog.project.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.blog.project.config.AppConstants;
import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.PostDto;
import com.blog.project.payloads.PostResponce;
import com.blog.project.services.FileService;
import com.blog.project.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api")
public class PostController {

	
	@Autowired
	private PostService postService;
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId, @PathVariable Integer categoryId)
	{
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);	
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
		
	}
	
	
	@GetMapping("/user/{userId}/posts")
	public  ResponseEntity<List<PostDto>>  getPostByUser(@PathVariable Integer userId){
	List<PostDto> posts=	this.postService.getPostByUser(userId);
	return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
	}
	
	@GetMapping("/category/{categoryId}/posts")
	public ResponseEntity<List<PostDto>>  getPostByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts=	this.postService.getPostByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		}
	
	//get All Post
	@GetMapping("/getAllPost")
	public ResponseEntity<PostResponce> getAllPost(@RequestParam(value="pageNumber", defaultValue =AppConstants.PAGE_NUMBER, required = false)Integer pageNumber,
	                                               @RequestParam(value="pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false)Integer pageSize,
	                                               @RequestParam(value="sortBy", defaultValue = AppConstants.SORT_BY, required = false)String sortBy,
	                                               @RequestParam(value="sortDir", defaultValue =AppConstants.SORT_DIR, required = false)String sortDir){
		PostResponce postResponce = this.postService.getAllPost(pageNumber,pageSize, sortBy, sortDir);
		
		return new ResponseEntity<PostResponce>(postResponce, HttpStatus.OK);
	}
	
	//get post by id
	@GetMapping("/getPostById/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
	PostDto postDto=this.postService.getPostById(postId);
		
		return new ResponseEntity<PostDto>(postDto, HttpStatus.OK);
	}
	
	//delete post
	@DeleteMapping("/deletePostById/{postId}")
	public ApiResponse deletePostById(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post deleted");
	}
	//update Post
	@PutMapping("/updatePostById/{postId}")
	public ResponseEntity<PostDto> updatePostById(@RequestBody PostDto postDto ,@PathVariable Integer postId) {
	PostDto updatePostById=	this.postService.updatePost(postDto, postId);
	return new ResponseEntity<PostDto>(updatePostById, HttpStatus.OK);
	}
	
	
	@GetMapping("/posts/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable("keywords") String keywords){
		List<PostDto> searchPost = this.postService.searchPost(keywords);
		return new ResponseEntity<List<PostDto>>(searchPost,HttpStatus.OK);
		
	}
	
	//post image 
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile image,
			@PathVariable Integer postId) throws IOException{
		
		PostDto postDto=this.postService.getPostById(postId);
		String filename=this.fileService.uploadImage(path, image);

		postDto.setImageName(filename);
		PostDto updatePost=this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
	}
	//method to serve file
	
	@GetMapping(value="/post/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName, 
			/**/HttpServletResponse response)throws IOException{
		InputStream resource=this.fileService.getResource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}
	
	
}
