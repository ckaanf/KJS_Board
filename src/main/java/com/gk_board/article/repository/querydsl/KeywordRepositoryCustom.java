package com.gk_board.article.repository.querydsl;

import com.gk_board.article.entity.Keyword;

import java.util.List;

public interface KeywordRepositoryCustom {
    List<Keyword> findAllWithKeyWord(List<String> keyword);
}
