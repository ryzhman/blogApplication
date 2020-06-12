package com.go2it.blog.service;


import com.go2it.blog.model.dto.PostDto;
import com.go2it.blog.repository.IPostRepository;
import org.springframework.stereotype.Service;

@Service
public class PostService {

	private final com.go2it.blog.repository.IPostRepository IPostRepository;

	public PostService(IPostRepository IPostRepository) {
		this.IPostRepository = IPostRepository;
	}

	public PostDto getPost(Long id) {
		return IPostRepository.findById(id)
			.map(post -> new PostDto(post.getTitle(), post.getContent(), post.getCreationDate()))
			.orElse(null);
	}
}
