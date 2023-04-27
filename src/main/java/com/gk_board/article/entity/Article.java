package com.gk_board.article.entity;

import com.gk_board.global.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.Map;
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Article extends AuditingFields {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;
    @Column(nullable = false, length = 10000)
    private String content;

    @ElementCollection
    private Map<Integer,Integer> refArticle;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }


    public static Article of(String title, String content) {
        return new Article(title, content);
    }
}
