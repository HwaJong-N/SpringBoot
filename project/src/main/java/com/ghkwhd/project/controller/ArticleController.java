package com.ghkwhd.project.controller;

import com.ghkwhd.project.dto.ArticleForm;
import com.ghkwhd.project.dto.CommentDto;
import com.ghkwhd.project.entity.Article;
import com.ghkwhd.project.repository.ArticleRepository;
import com.ghkwhd.project.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
@Slf4j // Logging을 위한 어노테이션
public class ArticleController {

    @Autowired // 스프링부트가 미리 생성해놓은 객체를 가져다가 자동으로 연결
    private ArticleRepository articleRepository;

    @Autowired
    private CommentService commentService;

    // 게시글 생성 페이지
    @GetMapping("/articles/new")
    public String newArticleForm() {
        return "articles/new";
    }

    // 게시글 생성하기
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

    // 게시글 상세 보기
    @GetMapping("/articles/{id}")   // {id}로 명시하면 이 id는 변하는 값임을 의미
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);

        // 1. id로 DB에서 데이터를 가져옴 (Entity로)
        Article articleEntity =  articleEntity = articleRepository.findById(id).orElse(null);
        List<CommentDto> commentDtos = commentService.comments(id);

        // 2. 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);
        model.addAttribute("commentDtos", commentDtos);

        // 3. 보여줄 페이지를 설정
        // articles/show에서 article이라는 Model을 사용 가능
        return "articles/show";
    }

    // 전체 게시글 보기
    @GetMapping("/articles")
    public String index(Model model) {

        // 1. 모든 Article을 가져온다
        ArrayList<Article> articleEntityList = articleRepository.findAll();

        // 2. 가져온 Article 리스트를 view로 전달 (Model을 사용)
        model.addAttribute("articleList", articleEntityList);

        // 3. view 페이지를 설정
        return "articles/index";
    }

    // 게시글 수정 페이지
    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model) {

        // 수정할 데이터 가져오기
        Article articleEntity =  articleEntity = articleRepository.findById(id).orElse(null);

        // model에 데이터 등록
        model.addAttribute("article", articleEntity);

        return "articles/edit";
    }

    // 게시글 수정하기
    @PostMapping("/articles/update")
    public String update(ArticleForm form) {
        log.info(form.toString());

        // 1. DTO --> Entity
        Article articleEntity = form.toEntity();
        
        // 2. Entity를 DB에 저장
        // 2-1. DB에 기존 데이터를 가져온다
        // 가져오려는 값이 없는 경우 null을 반환하도록
        // 원래 반환되는 값은 Optional<Article>
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2. 기존 데이터가 있다면 값을 갱신
        if(target != null) {
            articleRepository.save(articleEntity);
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }

    // 게시글 삭제하기
    @GetMapping("/articles/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {

        // 1. 삭제 대상을 가져온다
        Article target = articleRepository.findById(id).orElse(null);

        // 2. 대상을 삭제한다
        if(target != null) {
            articleRepository.delete(target);
            // 삭제완료 메세지 출력을 위한 메소드
            rttr.addFlashAttribute("msg", target.getId() + "번 글이 삭제되었습니다");
        }

        // 3. 결과 페이지로 리다이렉트
        return "redirect:/articles";
    }

}
