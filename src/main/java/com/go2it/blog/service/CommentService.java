package com.go2it.blog.service;

import com.go2it.blog.model.Comment;
import com.go2it.blog.model.Post;
import com.go2it.blog.model.dto.CommentDto;
import com.go2it.blog.model.dto.NewCommentDto;
import com.go2it.blog.repository.ICommentRepository;
import com.go2it.blog.repository.IPostRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentService {

	private ICommentRepository ICommentRepository;
	private IPostRepository IPostRepository;

	public CommentService(ICommentRepository ICommentRepository, IPostRepository IPostRepository) {
		this.ICommentRepository = ICommentRepository;
		this.IPostRepository = IPostRepository;
	}

	/**
	 * Returns a list of all comments for a blog post with passed id.
	 *
	 * @param postId id of the post
	 * @return list of comments sorted by creation date descending - most recent first
	 */
	public List<CommentDto> getCommentsForPost(Long postId) {
		final Optional<Post> byId = IPostRepository.findById(postId);
		return ICommentRepository.getAllCommentsForPost(byId.orElseThrow(() -> new RuntimeException("Post with it: " + postId + " wasn't found")))
			.stream()
			.map(item -> new CommentDto(item.getId(), item.getContent(), item.getAuthor(), item.getCreationDate()))
			.collect(Collectors.toList());
	}

	/**
	 * Creates a new comment
	 *
	 * @param newCommentDto data of new comment
	 * @return id of the created comment
	 */
	public Long addComment(NewCommentDto newCommentDto) {
		Comment comment = new Comment();
		final Optional<Post> post = IPostRepository.findById(newCommentDto.getPostId());
		if (post.isPresent()) {
			comment.setPost(post.get());
			comment.setAuthor(newCommentDto.getAuthor());
			comment.setContent(newCommentDto.getContent());
			comment.setCreationDate(LocalDateTime.now());
			comment = ICommentRepository.save(comment);
			return comment.getId();
		}
		return 0L;
	}
}
