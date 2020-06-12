package com.go2it.blog.api;

import com.go2it.blog.model.dto.CommentDto;
import com.go2it.blog.model.dto.NewCommentDto;
import com.go2it.blog.model.dto.PostDto;
import com.go2it.blog.service.CommentService;
import com.go2it.blog.service.PostService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@WebMvcTest
public class PostControllerTest {

	@Autowired
	protected MockMvc mockMvc;

	@MockBean
	protected PostService postService;

	@MockBean
	protected CommentService commentService;

	@Before
	public void setUp() {
		Mockito.reset(postService, commentService);
	}

	@Test
	public void shouldReturnFoundPost() throws Exception {
		// given
		LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
		PostDto post = new PostDto("Title", "content", creationDate);

		// when
		when(postService.getPost(1L)).thenReturn(post);

		// then
		mockMvc.perform(get("/posts/1").accept(APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(content().contentType(APPLICATION_JSON))
			.andExpect(jsonPath("$.title", is("Title")))
			.andExpect(jsonPath("$.content", is("content")))
			.andExpect(jsonPath("$.creationDate", is(creationDate.toString())));

	}


	@Test
	public void shouldReturnFoundComments() throws Exception {

		// given
		List<CommentDto> comments = new ArrayList<>();
		LocalDateTime creationDate = LocalDateTime.of(2018, 5, 20, 20, 51, 16);
		comments.add(new CommentDto(2L, "comment content", "John Smith", creationDate));

		// when
		when(commentService.getCommentsForPost(1L)).thenReturn(comments);

		// then
		mockMvc.perform(get("/posts/1/comments").accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isOk())
			.andExpect(jsonPath("$", hasSize(1)))
			.andExpect(jsonPath("$[0].id", is(2)))
			.andExpect(jsonPath("$[0].comment", is("comment content")))
			.andExpect(jsonPath("$[0].author", is("John Smith")))
			.andExpect(jsonPath("$[0].creationDate", is(creationDate.toString())));

	}

	@Test
	public void shouldAddComment() throws Exception {

		// given
		String commentBody = "{\"content\":\"Test content\", \"author\":\"John Doe\"}";
		NewCommentDto newComment = createComment("Test content", "John Doe");

		// when
		when(commentService.addComment(newComment)).thenReturn(1L);

		// then
		mockMvc.perform(post("/posts/1/comments")
			.content(commentBody)
			.contentType(APPLICATION_JSON)
			.accept(MediaType.APPLICATION_JSON))
			.andExpect(status().isCreated());
	}

	private NewCommentDto createComment(String content, String author) {
		NewCommentDto newComment = new NewCommentDto();
		newComment.setContent(content);
		newComment.setAuthor(author);
		return newComment;
	}
}
