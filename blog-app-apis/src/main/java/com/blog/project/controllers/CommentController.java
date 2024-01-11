package com.blog.project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.blog.project.payloads.ApiResponse;
import com.blog.project.payloads.CommentDto;
import com.blog.project.services.CommentService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	
	@PostMapping("/post/{postId}/comments")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto
			,@PathVariable Integer postId){
		CommentDto createComments=this.commentService.createComment(commentDto, postId);
				return new ResponseEntity<CommentDto>(createComments,HttpStatus.CREATED);
		
	}
	
	@DeleteMapping("/post/{commentId}")
	public ResponseEntity<ApiResponse> deleteComment(@PathVariable Integer commentId){
		this.commentService.deleteComment(commentId);
				return new ResponseEntity<ApiResponse>(new ApiResponse("deleted"),HttpStatus.OK);
		
	}
}
