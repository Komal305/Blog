package com.blog.project.entities;




import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Role {
@Id @GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
@Column
	private String name;

}
