package com.blog.project.entities;



import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;





@Setter
@Getter
@Entity
@Table(name="post")
public class Post {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
private Integer postId;
	@Column(nullable = false)
private String title;
@Column(length=500)
private String content ;
@Column(name="image")
private String imageName ;
@Column(name="DATE")
private Date addedDate ;

@ManyToOne
@JoinColumn(name="CategoryID")
private Category category;

@ManyToOne
private User user;

@OneToMany(mappedBy="post", cascade=CascadeType.ALL)
private Set<Comment> comments=new HashSet<>();
}
