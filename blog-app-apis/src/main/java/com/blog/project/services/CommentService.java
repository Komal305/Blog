package com.blog.project.services;

import com.blog.project.payloads.CommentDto;

public interface CommentService {
CommentDto createComment(CommentDto commentDto, Integer postId);
void deleteComment(Integer commentId);

}
