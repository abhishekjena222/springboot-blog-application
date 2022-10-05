package com.abhi.blog.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "tags")
public class Tags {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	
	@Column(name = "name")
	private String name;
	
	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDate createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDate updatedAt;
	
	
	@ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST,mappedBy = "tags")
//	@JoinTable(name = "post_tags",
//			joinColumns = {@JoinColumn(name = "tag_id",referencedColumnName = "id")},
//			inverseJoinColumns = {@JoinColumn(name = "post_id",referencedColumnName = "id")})
	private List<Posts> posts = new ArrayList<>();

	
	
	
	public List<Posts> getPosts() {
		return posts;
	}
	public void setPosts(List<Posts> posts) {
		this.posts = posts;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public LocalDate getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDate createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDate getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDate updatedAt) {
		this.updatedAt = updatedAt;
	}
	
	
}
