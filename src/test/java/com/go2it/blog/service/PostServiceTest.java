package com.go2it.blog.service;

import java.time.LocalDateTime;

import com.go2it.blog.model.Post;
import com.go2it.blog.model.dto.PostDto;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PostServiceTest {

	@Autowired
	com.go2it.blog.repository.IPostRepository IPostRepository;

	@Autowired
	PostService postService;

	@Test
	public void shouldReturnCreatedPost() {
		Post post = new Post();
		post.setTitle("Test title");
		post.setContent("Test content");
		LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
		post.setCreationDate(creationDate);
		IPostRepository.save(post);

		PostDto postDto = postService.getPost(post.getId());

		assertNotNull("Post shouldn't be null", postDto);
		assertThat(postDto.getContent(), equalTo("Test content"));
		assertThat(postDto.getTitle(), equalTo("Test title"));
		assertThat(postDto.getCreationDate(), equalTo(creationDate));
	}

	@Test
	public void shouldReturnNullForNotExistingPost() {
		PostDto postDto = postService.getPost(123L);

		assertNull(postDto);
	}
}
