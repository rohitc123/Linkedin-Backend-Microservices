package com.example.linkedInApp.posts_service.repository;

import com.example.linkedInApp.posts_service.entity.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostLikeRepository extends JpaRepository<PostLike,Long> {
    boolean existsByPostIdAndUserId(Long postId, long userId);

    void deleteByPostIdAndUserId(Long postId, long userId);
}
