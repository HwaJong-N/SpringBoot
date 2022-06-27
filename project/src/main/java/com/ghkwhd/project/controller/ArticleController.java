package com.ghkwhd.project.controller;

import com.ghkwhd.project.dto.ArticleForm;
import com.ghkwhd.project.entity.Article;
import com.ghkwhd.project.repository.ArticleRepository;
import jdk.nashorn.internal.runtime.options.Option;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j // Logging을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    @PostMapping("/articles/create")
    public String createArticle(ArticleForm form) {
        log.info(form.toString());

        //1. DTO를 Entity로 변환
        Article article = form.toEntity();
        log.info(article.toString());

        //2. Repository가 Entity를 DB안에 저장하게 함
        // save()는 CRUDrepository에 기본적으로 정의되어 있음
        Article saved = articleRepository.save(article);
        log.info(saved.toString());

        // getID()는 Article에 @getter 선언 필요
        return "redirect:/articles/" + saved.getId();
    }

    @GetMapping("/articles/{id}")   // {id}로 명시하면 이 id는 변하는 값임을 의미
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id로 DB에서 데이터를 가져옴 (Entity로)
        Article articleEntity =  articleEntity = articleRepository.findById(id).orElse(null);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3. 보여줄 페이지를 설정
        // articles/show에서 article이라는 Model을 사용 가능
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 Article을 가져온다
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 리스트를 view로 전달 (Model을 사용)
        model.addAttribute("articleList", articleEntityList);

        // 3. view 페이지를 설정
        return "articles/index";
    }
}
