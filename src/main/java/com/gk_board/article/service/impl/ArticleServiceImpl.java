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
        List<Long> articleList = keywordRepository.findByContentIn(keywords);

        log.info("리스트 확인 : {}", articleList);

        for(int i = 0; i<articleList.size(); i++){
            if(!Objects.equals(articleId, articleList.get(i))) {
                article = articleRepository.getReferenceById(articleList.get(i));
                article.setMainArticle(articleRepository.getReferenceById(articleId));
            }
        }


//        article.setRefArticle(articleDtoList);
        List<ArticleDto> checkArticle = articleRepository.findById(articleId).map(ArticleDto::from).stream().toList();


//        log.info("아티클 확인: {} , 아티클 리스트 확인 : {}, 연관 아티클 Id: {} , 연관 아티클 확인 : {}", article.getContent(),articleList,articleDtoList, checkArticle );
        log.info("아티클 확인: {} , 아티클 리스트 확인 : {},  연관 아티클 확인 : {}", article.getContent(),articleList, checkArticle );

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
