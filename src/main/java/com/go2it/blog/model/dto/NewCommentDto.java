package com.go2it.blog.model.dto;

import java.util.Objects;

public class NewCommentDto {

	private Long postId;

	private String author;

	private String content;

	public Long getPostId() {
		return postId;
	}

	public void setPostId(Long postId) {
		this.postId = postId;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		NewCommentDto that = (NewCommentDto) o;
		return getAuthor().equals(that.getAuthor()) &&
			getContent().equals(that.getContent());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getAuthor(), getContent());
	}
}
