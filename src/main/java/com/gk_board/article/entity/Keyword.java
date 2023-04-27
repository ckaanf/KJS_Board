package com.gk_board.article.entity;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Keyword {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private Long articleId;

    @Column
    private String refKeyword;


    public Keyword(Long articleId, String refKeyword) {
        this.articleId = articleId;
        this.refKeyword = refKeyword;

    }

    public static Keyword of(Long articleId, String keyword){
        return new Keyword(articleId, keyword);
    }
}
