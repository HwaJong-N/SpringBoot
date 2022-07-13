package com.ghkwhd.project.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

@Entity // DB가 해당 객체를 인식 가능 (해당 클래스로 테이블을 생성한다)
@AllArgsConstructor
@ToString
@NoArgsConstructor
@Getter
public class Article {

    @Id // 대푯값을 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) // DB가 ID를 자동 생성
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    public void patch(Article article) {
        if(article.title != null) {
            this.title = article.title;
        }

        if(article.content != null) {
            this.content = article.content;
        }
    }

}
