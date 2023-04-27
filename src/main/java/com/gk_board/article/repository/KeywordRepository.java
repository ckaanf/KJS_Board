package com.gk_board.article.repository;

import com.gk_board.article.entity.Article;
import com.gk_board.article.entity.Keyword;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface KeywordRepository extends JpaRepository<Keyword, Long> {
    Keyword findByRefKeyword(String Keyword);

    Keyword getReferenceByRefKeyword(String keyword);

    List<Keyword> getKeywordsByArticleId(Long articleId);

    @Query("select k from Keyword as k where k.refKeyword in :keyword")
    List<Keyword> findByContentIn(List<String> keyword);
}