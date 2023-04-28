package com.gk_board.article.repository.querydsl.impl;

import com.gk_board.article.entity.Keyword;
import com.gk_board.article.entity.QKeyword;
import com.gk_board.article.repository.querydsl.KeywordRepositoryCustom;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPQLQuery;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

import static com.gk_board.article.entity.QKeyword.keyword;

@Slf4j
public class KeywordRepositoryCustomImpl extends QuerydslRepositorySupport implements KeywordRepositoryCustom {

    public KeywordRepositoryCustomImpl() {
        super(Keyword.class);
    }


    @Override
    public List<String> findWithArticleIdAndKeyword(List<String> keyword) {
        QKeyword qKeyword = QKeyword.keyword;

        JPQLQuery<Keyword> query =
                from(qKeyword)
                        .select(qKeyword)
                        .where(qKeyword.refKeyword.in(keyword))
                        .groupBy(qKeyword.articleId)
                        .having(qKeyword.count().goe(1))
                        .orderBy(qKeyword.count().desc())
                        .fetchAll();


        return keyword;
    }
}

