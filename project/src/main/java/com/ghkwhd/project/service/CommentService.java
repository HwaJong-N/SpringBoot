package com.ghkwhd.project.service;

import com.ghkwhd.project.dto.CommentDto;
import com.ghkwhd.project.entity.Article;
import com.ghkwhd.project.entity.Comment;
import com.ghkwhd.project.repository.ArticleRepository;
import com.ghkwhd.project.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    // Service는 Repository와 협업
    @Autowired
    private CommentRepository commentRepository;

    // Comment 데이터와 함께 Article 데이터도 DB에서 가져와야 하기 때문
    @Autowired
    private ArticleRepository articleRepository;


    // 데이터 조회
    public List<CommentDto> comments(Long articleId) {
        // Service가 데이터를 가져올 때 Repository에게 시킨다
        
//        // 조회: 댓글 목록 (해당 게시글의 모든 댓글 가져오기)
//        List<Comment> comments = commentRepository.findByArticleId(articleId);
//
//        // 변환: Entity -> DTO
//        List<CommentDto> dtos = new ArrayList<CommentDto>();
//        for(int i = 0; i < comments.size(); i++) {
//            Comment c = comments.get(i);
//            CommentDto dto = CommentDto.createCommentDto(c);
//            dtos.add(dto);
//        }
//
//        // 반환
//        return dtos;

        // 한 번에 작성
        return commentRepository.findByArticleId(articleId)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    // DB를 조작하는 것이기 때문에 선언 필요
    @Transactional
    public CommentDto create(Long articleId, CommentDto dto) {
        // 게시글 조회 및 예외 발생 (실패한 경우에 에러번호가 자동으로 반환되도록 작성, Controller에는 성공한 경우의 반환만 작성)
        // 찾는 id를 가진 게시글이 없다면 예외 발생
        Article article = articleRepository.findById(articleId).
                orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패!, 대상 게시글이 없습니다."));
        
        // 댓글 Entity 생성
        Comment comment = Comment.createComment(dto, article);
        
        // 댓글 Entity DB에 저장
        Comment created = commentRepository.save(comment);
        
        // DTO로 변경하여 반환
        return CommentDto.createCommentDto(created);
    }

    @Transactional
    public CommentDto update(Long id, CommentDto dto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패!, 대상 댓글이 없습니다"));

        // 댓글 수정
        target.patch(dto);

        // DB로 갱싱
        Comment updated = commentRepository.save(target);


        // 댓글 Entity를 DTO로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 삭제 실패!, 대상 댓글이 없습니다"));
        
        // 댓글 삭제
        commentRepository.delete(target);
        
        // 삭제 댓글을 DTO로 반환
        return CommentDto.createCommentDto(target);
    }
}
