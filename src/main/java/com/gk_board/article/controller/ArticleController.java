package com.gk_board.article.controller;

import com.gk_board.article.dto.ArticleDto;
import com.gk_board.article.dto.request.ArticleRequestDto;
import com.gk_board.article.dto.response.ArticleListResponse;
import com.gk_board.article.service.ArticleService;
import com.gk_board.global.response.PageResponseDto;
import com.gk_board.global.response.SingleResponseDto;
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

    @GetMapping
    public ResponseEntity readArticleList(
            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC)Pageable pageable
            ){
        Page<ArticleListResponse> articlePage = articleService.getArticlesList(pageable).map(ArticleListResponse::from);
        List<ArticleListResponse> articleList = articlePage.getContent();
        return new ResponseEntity<>(
                new PageResponseDto<>(articleList,articlePage),HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity createArticle(
            @RequestBody ArticleRequestDto articleRequestDto){

        ArticleDto articleDto = articleService.saveArticle(articleRequestDto.toDto());

        return new ResponseEntity(
                new SingleResponseDto<>(articleDto), HttpStatus.CREATED);
    }
}
