package com.gk_board.article.repository;

import com.gk_board.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ArticleRepository extends JpaRepository<Article, Long> {
    Page<Article> findAll(Pageable pageable);

    Long countArticleByContentContaining(String keyword);

    @Query("select a from Article as a where a.content in :keyword")
    List<Article> findByContentIn(List<String> keyword);
}
