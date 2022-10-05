package com.abhi.blog.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "posts")
public class Posts {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private long id;

	@Column(name = "title")
	private String title;

	@Column(name = "excerpt")
	private String excerpt;

	@Column(name = "content", length=512)
	private String content;

	@Column(name = "author")
	private String author;

	@CreationTimestamp
	@Column(name = "published_at")
	private LocalDateTime publishedAt;

	@Column(name = "is_published")
	private boolean isPublished = false;


	@CreationTimestamp
	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt;
	
	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;
	
	
	@OneToMany( cascade = CascadeType.ALL, mappedBy= "post")
	private List<Comments> comments;
	
	@ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.PERSIST)
	@JoinTable(name = "post_tags",
		joinColumns = {@JoinColumn(name = "post_id",referencedColumnName = "id")},
		inverseJoinColumns = {@JoinColumn(name = "tag_id",referencedColumnName = "id")})
	private List<Tags> tags = new ArrayList<>();

	@ManyToOne
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public List<Tags> getTags() {
		return tags;
	}
	public void setTags(List<Tags> tags) {
		this.tags = tags;
	}
	public List<Comments> getComments() {
		return comments;
	}
	public void setComments(List<Comments> comments) {
		this.comments = comments;
	}
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getExcerpt() {
		return excerpt;
	}
	public void setExcerpt(String excerpt) {
		this.excerpt = excerpt;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public LocalDateTime getPublishedAt() {
		return publishedAt;
	}
	public void setPublishedAt(LocalDateTime publishedAt) {
		this.publishedAt = publishedAt;
	}
	public boolean getIsPublished() {
		return isPublished;
	}
	public void setIsPublished(boolean isPublished) {
		this.isPublished = isPublished;
	}
	public LocalDateTime getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}
	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}
	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
}



