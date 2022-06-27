package com.ghkwhd.project.controller;

import com.ghkwhd.project.dto.ArticleForm;
import com.ghkwhd.project.entity.Article;
import com.ghkwhd.project.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        System.out.println(form.toString());

        //1. DTO를 Entity로 변환
        Article article = form.toEntity();
        System.out.println(article);

        //2. Repository가 Entity를 DB안에 저장하게 함
        // save()는 CRUDrepository에 기본적으로 정의되어 있음
        Article saved = articleRepository.save(article);
        System.out.println(saved.toString());

        return "";
    }
}
