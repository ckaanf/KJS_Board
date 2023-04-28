package com.gk_board.article.service.impl;

import com.gk_board.article.entity.Article;
import com.gk_board.article.entity.Keyword;
import com.gk_board.article.repository.ArticleRepository;
import com.gk_board.article.repository.KeywordRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class RefServiceImpl {

    private final ArticleRepository articleRepository;
    private final KeywordRepository keywordRepository;


    //본문 조각화 키워드 저장
    public List<String> saveArticleToWord(Long articleId) {
        Article article = articleRepository.getReferenceById(articleId);
        List<String> arr = Arrays.stream(article.getContent().split(" "))
                .sorted()
                .collect(Collectors.toList());

        log.info("keyword: {}", arr);

        for (int i = 0; i < arr.size(); i++) {
            keywordRepository.save(new Keyword(articleId, arr.get(i)));

        }
        
        return arr;
    }

    public Set<String> getArticleKeyword(Long articleId) {
        List<Keyword> keywordList = keywordRepository.getKeywordsByArticleId(articleId);
        Set<String> refKeyword = new HashSet<>();
        long allArticle = articleRepository.count();

        for (int i = 0; i < keywordList.size(); i++) {
            int cnt = 0;
            long keywordRatio = articleRepository.countArticleByContentContaining(keywordList.get(i).getRefKeyword());
            float ratio = (float) keywordRatio / allArticle;

            if (ratio < 0.4) {
                refKeyword.add(keywordList.get(i).getRefKeyword());
                ratio = (float) keywordRatio / allArticle;
            }

            log.info(" 전체 게시글 : {}, 현재 키워드 {}, 키워드 비율 : {}", allArticle,keywordList.get(i).getRefKeyword(), ratio);
        }
        log.info("keyword 리스트 : {}", refKeyword);

        return refKeyword;
    }

}
