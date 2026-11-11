package com.example.linkedInApp.posts_service.repository;

import com.example.linkedInApp.posts_service.entity.PostMedia;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostMediaRepository extends JpaRepository<PostMedia,Long> {
}
