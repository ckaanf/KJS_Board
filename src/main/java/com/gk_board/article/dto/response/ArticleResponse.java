package com.gk_board.article.dto.response;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.entity.Article;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public record ArticleResponse(
        Long articleId,
        String title,
        String content,
        LocalDateTime created_at,
        List<ArticleDto> refArticles
) {
    public static ArticleResponse of (Long articleId, String title,String content, LocalDateTime created_at, List<ArticleDto> refArticles){
        return new ArticleResponse(articleId, title, content, created_at,refArticles);
    }


    public static ArticleResponse from(Article entity){
        return new ArticleResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getRefArticle().stream()
                        .map(ArticleDto::from)
                        .collect(Collectors.toList())
        );
    }
}
