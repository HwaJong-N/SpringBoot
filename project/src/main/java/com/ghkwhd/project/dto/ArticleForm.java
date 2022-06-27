package com.ghkwhd.project.dto;

import com.ghkwhd.project.entity.Article;
import lombok.AllArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@ToString
public class ArticleForm {

    private String title;
    private String content;

    // Article(Entity) 객체를 반환
    public Article toEntity() {
        // 생성자 호출
        // id는 null값, title과 content는 DTO의 title, content를 사용
        return new Article(null, title, content);
    }
}
