package com.go2it.blog.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
public class Comment {
	@Id
	@GeneratedValue
	@Column(name = "COMMENT_ID")
	private Long id;

	@Column(length = 4096)
	@NotNull
	private String content;

	@NotNull
	private LocalDateTime creationDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "POST_ID")
	@NotNull
	private Post post;

	@NotNull
	private String author;

	public Comment() {
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public LocalDateTime getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(LocalDateTime creationDate) {
		this.creationDate = creationDate;
	}

	public Post getPost() {
		return post;
	}

	public void setPost(Post post) {
		this.post = post;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
