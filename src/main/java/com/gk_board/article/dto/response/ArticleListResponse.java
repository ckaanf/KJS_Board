package com.gk_board.article.dto.response;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.entity.Article;

import java.time.LocalDateTime;
import java.util.Map;

public record ArticleListResponse(
        Long articleId,
        String title,
        LocalDateTime created_at
) {
    public static ArticleListResponse of (Long articleId, String title, LocalDateTime created_at){
        return new ArticleListResponse(articleId, title, created_at);
    }

    public static ArticleListResponse from(ArticleDto dto){
        return new ArticleListResponse(
                dto.id(),
                dto.title(),
                dto.created_at()
        );
    }
    public static ArticleListResponse from(Article entity){
        return new ArticleListResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getCreatedAt()
        );
    }
}
