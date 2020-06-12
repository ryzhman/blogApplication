package com.go2it.blog.repository;

import com.go2it.blog.model.Comment;
import com.go2it.blog.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ICommentRepository extends JpaRepository<Comment, Long> {
	@Query("SELECT c FROM Comment c WHERE c.post = :postId")
	List<Comment> getAllCommentsForPost(@Param("postId") Post postId);
}
