package com.ghkwhd.project.repository;

import com.ghkwhd.project.entity.Article;
import org.springframework.data.repository.CrudRepository;

// JPA에서 제공하는 repository 인터페이스를 활용
public interface ArticleRepository extends CrudRepository<Article, Long> {

}
