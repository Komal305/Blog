package com.blog.project.entities;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Comment {
@Id
@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
@Column(length = 1000)
private String content;
	@ManyToOne
	private Post post ;
}
