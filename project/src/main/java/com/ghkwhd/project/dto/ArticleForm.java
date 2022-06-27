package com.ghkwhd.project.dto;

import com.ghkwhd.project.entity.Article;

public class ArticleForm {

    private String title;
    private String content;

    public ArticleForm(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    // 데이터가 잘 받아졌는지 확인을 위해 작성
    public String toString() {
        return "ArticleForm{" +
                "title='" + title + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

    // Article(Entity) 객체를 반환
    public Article toEntity() {
        // 생성자 호출
        // id는 null값, title과 content는 DTO의 title, content를 사용
        return new Article(null, title, content);
    }
}
