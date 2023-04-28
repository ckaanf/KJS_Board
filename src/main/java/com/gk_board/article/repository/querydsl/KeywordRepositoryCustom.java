package com.gk_board.article.repository.querydsl;

import com.gk_board.article.entity.Keyword;
import com.querydsl.core.Tuple;

import java.util.List;

public interface KeywordRepositoryCustom {
    List<String> findWithArticleIdAndKeyword(List<String> keyword);
}
