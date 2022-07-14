package com.ghkwhd.project.entity;

import com.ghkwhd.project.dto.CommentDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne  // 해당 댓글 Entity 여러 개가, 하나의 Article에 연관
    @JoinColumn(name = "article_id")    // article_id 컬럼(FK)에 Article의 대푯값(PK)을 저장
    // 댓글의 부모 게시글
    private Article article;

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto dto, Article article) {
        // 예외 발생
        if (dto.getId() != null)
            throw new IllegalArgumentException("댓글 생성 실패! 댓글의 id가 없어야 합니다.");
        // 게시글의 id가 일치하지 않는 경우 예외 발생
        if (dto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패! 게시글의 id가 잘못되었습니다.");
        
        // Entity 생성 및 반환
        return new Comment(dto.getId(), article, dto.getNickname(), dto.getBody());
    }

    public void patch(CommentDto dto) {
        // 예외 발생
        // url에서 받아온 id와 json으로 받아온 id가 다른 경우
        if (this.id != dto.getId())
            throw new IllegalArgumentException("댓글 수정 실패! 잘못된 id가 입력되었습니다.");

        // 객체 갱신
        // 닉네임을 수정한 경우 ( JSON 데이터에 nickname이 있는 경우 )
        if(dto.getNickname() != null)
            this.nickname = dto.getNickname();

        // 수정할 내용이 있는 경우
        if(dto.getBody() != null)
            this.body = dto.getBody();
    }
}
