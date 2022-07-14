package com.ghkwhd.project.repository;

import com.ghkwhd.project.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글 조회
    // parameter와 : 뒤에 붙는 이름이 같아야 매칭이 됨
    @Query(value = "SELECT * FROM Comment WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId);

    // 특정 닉네임의 모든 댓글 조회

    List<Comment> findByNickname(String nickname);
}
