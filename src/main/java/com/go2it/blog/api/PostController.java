package com.go2it.blog.api;

import com.go2it.blog.model.dto.CommentDto;
import com.go2it.blog.model.dto.NewCommentDto;
import com.go2it.blog.model.dto.PostDto;
import com.go2it.blog.service.CommentService;
import com.go2it.blog.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RestController
@RequestMapping("/posts")
public class PostController {

	private final PostService postService;
	private final CommentService commentService;


	public PostController(PostService postService, CommentService commentService) {
		this.postService = postService;
		this.commentService = commentService;
	}

	@GetMapping(value = "/{id}")
	@ResponseStatus(HttpStatus.OK)
	public PostDto getPost(@PathVariable Long id) {
		return postService.getPost(id);
	}


	@GetMapping("/{id}/comments")
	public List<CommentDto> getCommentsByPostId(@PathVariable Long id) {
		return commentService.getCommentsForPost(id);
	}

	@PostMapping("/{id}/comments")
	public ResponseEntity<String> addNewComment(@PathVariable Long id, @RequestBody NewCommentDto commentDto) {
		commentDto.setPostId(id);
		Long resultId = commentService.addComment(commentDto);
		return resultId != 0
			? ResponseEntity.status(201).body("")
			: ResponseEntity.status(500).body("Failed to save the comment");
	}
}
