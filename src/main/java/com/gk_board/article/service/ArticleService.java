package com.gk_board.article.service;

import com.gk_board.article.dto.ArticleDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleService {
    //게시물 작성
    ArticleDto saveArticle(ArticleDto dto);

    Page<ArticleDto> getArticlesList(Pageable pageable);
}
