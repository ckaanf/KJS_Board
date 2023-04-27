package com.gk_board.article.repository.querydsl.impl;

import com.gk_board.article.entity.Keyword;
import com.gk_board.article.repository.querydsl.KeywordRepositoryCustom;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;

@Slf4j
public class KeywordRepositoryCustomImpl extends QuerydslRepositorySupport implements KeywordRepositoryCustom {

    public KeywordRepositoryCustomImpl() {
        super(Keyword.class);
    }


    @Override
    public List<Keyword> findAllWithKeyWord(List<String> keyword) {
        return null;
    }
}

