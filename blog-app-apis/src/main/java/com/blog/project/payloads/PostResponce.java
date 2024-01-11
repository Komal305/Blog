package com.blog.project.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Setter
@Getter
public class PostResponce {
private List<PostDto> content;
private int PageNumber;
private int PageSize;
private long totalElements;
private int totalPages;
private boolean lastPage;

	
}
