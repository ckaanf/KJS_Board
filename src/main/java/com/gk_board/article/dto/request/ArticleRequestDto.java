package com.gk_board.article.dto.request;

import com.gk_board.article.dto.ArticleDto;

public record ArticleRequestDto(
        Long articleId,
        String title,
        String content
) {
    public static ArticleRequestDto of (Long articleId, String title, String content){
        return new ArticleRequestDto(articleId, title, content);
    }

    public ArticleDto toDto(){
        return ArticleDto.of(
                title,
                content
        );
    }
}
