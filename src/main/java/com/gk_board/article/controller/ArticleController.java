package com.gk_board.article.controller;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.dto.request.ArticleRequestDto;
import com.gk_board.article.dto.response.ArticleListResponse;
import com.gk_board.article.dto.response.ArticleResponse;
import com.gk_board.article.service.ArticleService;
import com.gk_board.article.service.impl.RefServiceImpl;
import com.gk_board.global.response.PageResponseDto;
import com.gk_board.global.response.SingleResponseDto;
import jakarta.persistence.GeneratedValue;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/articles")
@RestController
public class ArticleController {
    private final ArticleService articleService;
    private final RefServiceImpl refService;

    //게시글 리스트
    @GetMapping
    public ResponseEntity readArticleList(
            @PageableDefault(size = 10, sort = "created_at", direction = Sort.Direction.DESC) Pageable pageable
    ) {
        Page<ArticleListResponse> articlePage = articleService.getArticlesList(pageable).map(ArticleListResponse::from);
        List<ArticleListResponse> articleList = articlePage.getContent();
        return new ResponseEntity<>(
                new PageResponseDto<>(articleList, articlePage), HttpStatus.OK);
    }

    //게시글 단건
    @GetMapping("/{article-id}")
    public ResponseEntity readArticle(
            @PathVariable("article-id") Long articleId) {

        ArticleResponse articleResponse = articleService.getArticle(articleId);

        return new ResponseEntity<>(
                new SingleResponseDto<>(articleResponse), HttpStatus.OK
        );
    }


    @PostMapping
    public ResponseEntity createArticle(
            @RequestBody ArticleRequestDto articleRequestDto) {

        //게시글 저장
        ArticleDto articleDto = articleService.saveArticle(articleRequestDto.toDto());

        //게시글 본문을 조각화 하여 연관 게시글 저장
        refService.saveArticleToWord(articleDto.id());

        List<String> refKeyword = refService.getArticleKeyword(articleDto.id()).stream().toList();

        if(refKeyword.size()>1) {
            articleService.saveRefArticle(articleDto.id(), refKeyword);
        }

        return new ResponseEntity(
                new SingleResponseDto<>(articleDto), HttpStatus.CREATED);
    }
}
