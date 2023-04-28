package com.gk_board.article.entity;

import com.gk_board.global.AuditingFields;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "mainArticle")
    @Setter
    private Article mainArticle;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "mainArticle")
    @Setter
    private List<Article> refArticle;

    public Article(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public static Article of(String title, String content) {
        return new Article(title, content);
    }
}
