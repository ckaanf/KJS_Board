package com.gk_board.article.service.impl;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.repository.ArticleRepository;
import com.gk_board.article.service.ArticleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class ArticleServiceImpl implements ArticleService {

    private final ArticleRepository articleRepository;

    //게시물 작성
    @Override
    public ArticleDto saveArticle(ArticleDto dto){

        ArticleDto articleDto = ArticleDto.from(articleRepository.save(dto.toEntity()));
        return articleDto;
    }

    @Override
    public Page<ArticleDto> getArticlesList(Pageable pageable){
        Page<ArticleDto> articleList = articleRepository.findAll(pageable).map(ArticleDto::from);
        return articleList;
    }


}
