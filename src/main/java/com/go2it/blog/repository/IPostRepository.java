package com.go2it.blog.repository;

import com.go2it.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPostRepository extends JpaRepository<Post, Long> {
}
