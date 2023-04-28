package com.gk_board.article.dto;

import com.gk_board.article.entity.Article;

import java.time.LocalDateTime;

public record ArticleDto(
        Long id,
        String title,
        String content,
        LocalDateTime createdAt,
        LocalDateTime modifiedAt

) {
    public static ArticleDto of(String title, String content){
        return new ArticleDto(null, title, content, null, null);
    }

    public static ArticleDto from(Article entity){
        return new ArticleDto(
                entity.getId(),
                entity.getTitle(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getModifiedAt()
        );
    }



    public Article toEntity(){
        return Article.of(
                title,
                content
        );
    }
}
