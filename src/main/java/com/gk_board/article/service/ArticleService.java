package com.gk_board.article.service;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.dto.response.ArticleResponse;
import com.gk_board.article.entity.Article;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ArticleService {
    //게시물 작성
    ArticleDto saveArticle(ArticleDto dto);

    ArticleDto saveRefArticle(Long articleId, List<String> keywords);

    Page<ArticleDto> getArticlesList(Pageable pageable);

    ArticleResponse getArticle(Long articleId);
}
