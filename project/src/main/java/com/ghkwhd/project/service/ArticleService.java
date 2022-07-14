package com.ghkwhd.project.service;

import com.ghkwhd.project.dto.ArticleForm;
import com.ghkwhd.project.entity.Article;
import com.ghkwhd.project.repository.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service    // 서비스 선언 (서비스 객체를 스프링부트에 생성)
public class ArticleService {

    // Repository와 협업할 수 있도록 필드를 추가
    @Autowired
    private ArticleRepository articleRepository;

    public List<Article> index() {
        return articleRepository.findAll();
    }

    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleForm dto) {
        Article article = dto.toEntity();

        // 이미 존재하는 id를 가진 데이터가 전송된 경우
        if (article.getId() != null) {
            return null;
        }

        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleForm dto) {

        // 1. 수정용 Entity 생성
        Article article = dto.toEntity();

        // 2. 대상 Entity 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 (대상이 없거나, id가 다른 경우)
        if (target == null || id != article.getId()) {
            return null;
        }

        // 4. 업데이트
        // 수정할 데이터만 받아도 (전체 데이터를 받지 않아도) 수정되도록 하는 메소드 (직접 작성한 메소드)
        target.patch(article);
        Article updated = articleRepository.save(target);
        return updated;
    }


    public Article delete(Long id) {
        // 대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 잘못된 요청 처리
        if (target == null) {
            return null;
        }
        // 대상 삭제
        articleRepository.delete(target);
        return target;
    }


    // 트랜잭션 테스트
    @Transactional  // 해당 메소드를 트랜잭션으로 묶는다
    public List<Article> createArticles(List<ArticleForm> dtos) {
        
        // dto 묶음을 entity 묶음으로 변환
        // dtos의 항목들을 하나씩 Entity로 변환
        List<Article> articleList = dtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());
        
        // entity 묶음을 DB에 저장
        // articleList를 반복하면서 하나씩 저장
        articleList.stream().forEach(article -> articleRepository.save(article));
        
        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결재 실패!"));
        
        // 결과값 반환
        return articleList;
    }
}
