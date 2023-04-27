package com.gk_board.article.service.impl;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.dto.response.ArticleResponse;
import com.gk_board.article.entity.Article;
import com.gk_board.article.entity.Keyword;
import com.gk_board.article.repository.ArticleRepository;
import com.gk_board.article.repository.KeywordRepository;
import com.gk_board.article.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;
    private final KeywordRepository keywordRepository;
    //게시물 작성
    @Override
    public ArticleDto saveArticle(ArticleDto dto){

        ArticleDto articleDto = ArticleDto.from(articleRepository.save(dto.toEntity()));

        return articleDto;
    }

    @Override
    public ArticleDto saveRefArticle(Long articleId, List<String> keywords){
        Article article = articleRepository.getReferenceById(articleId);
        List<Keyword> articleList = keywordRepository.findByContentIn(keywords);

        //키워드에서 중복을 제거하고 검색
        Set<Long> refArticleId = new HashSet<>();
        for(int i = 0; i<articleList.size(); i++) {
            if(!articleId.equals(articleList.get(i).getArticleId()))
            refArticleId.add(articleList.get(i).getArticleId());
        }

        for(int i = 0; i<articleList.size(); i++){
            if(!articleId.equals(articleList.get(i).getArticleId()))
                article = articleRepository.getReferenceById(articleList.get(i).getArticleId());
            article.setMainArticle(articleRepository.getReferenceById(articleId));
        }



//        List<Article> articleDtoList = new ArrayList<>();
//        for (Long aLong : refArticleId) {
//            articleDtoList.add(articleRepository.getReferenceById(aLong));
//        }
//
//        article.setRefArticle(articleDtoList);
        List<ArticleDto> checkArticle = articleRepository.findById(articleId).map(ArticleDto::from).stream().toList();


        log.info("아티클 확인: {} , 아티클 리스트 확인 : {}, 연관 아티클 Id: {} , 연관 아티클 확인 : {}", article.getContent(),articleList,refArticleId, checkArticle );

        return null;
    }


    @Override
    public Page<ArticleDto> getArticlesList(Pageable pageable){
        return articleRepository.findAll(pageable).map(ArticleDto::from);
    }

    @Override
    public ArticleResponse getArticle(Long articleId){
        Article article = articleRepository.getReferenceById(articleId);
        return ArticleResponse.from(article);
    }


}
