package com.blog.project.payloads;





import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor@Getter@Setter
public class CategoryDto {

	private Integer categoryId;
	
	@NotEmpty
		private String categoryTitle;
	
	@NotEmpty@Size(min=5 , message="please write more words")
		private String categoryDescription;
}
