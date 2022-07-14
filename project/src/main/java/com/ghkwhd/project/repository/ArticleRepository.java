package com.ghkwhd.project.repository;

import com.ghkwhd.project.entity.Article;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

// JPA에서 제공하는 CRUDRepository 인터페이스를 활용
public interface ArticleRepository extends CrudRepository<Article, Long> {

    @Override
    ArrayList<Article> findAll();
}
