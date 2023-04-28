# 인사말

보내주신 기회에 최대한 열심히 노력을하였습니다. 그러나 아쉬운 점이 많이 있었습니다.
하지만 백엔드 개발자로써 필요한 소양과 기본기에 대해 다시 한번 생각을 할 수 있게 되어 좋은 기회였다고 생각하며 감사의 말씀 드립니다.

# 과제 진행 명세

- [X] 게시판은 게시글의 ID, 제목, 본문, 생성날짜로 구성되며 제목과 본문은 각각 텍스트 입니다.
- [X] 게시글은 연관 게시글이라는 항목을 가지고 있으며, 연관게시글은 게시글과 내용이 유사한 게시글 입니다.
- [X] 하나의 게시글은 여러개의 연관게시글을 가질 수 있으며, 하나도 없을 수 있습니다.
- [X] 게시글목록은 게시글 제목과 날짜정보를 가져옵니다.
- [X] 게시글이 생성되면 연관게시글을 찾아서 연결합니다.
- [X] 연관게시글은 게시글의 내용을 단어별로 나눠서 각 단어가 다른 게시글에서 얼마나 많이 나타나는지를 기준으로 합니다.
- [X] 단, 문장에 자주쓰이는 단어를 배제하기 위해서 전체게시글 중에 60%이상에서 발견되는 단어는 연관게시글을 파악할때 사용하지 않습니다.
- [X] 연관게시글이 되는 기준은 40% 이하 빈도 단어가 두개이상 동시에 나타나는 것입니다.
- [ ] 그리고, 게시글에 40% 이하 빈도로 나타나는 단어는 자주 나타날수록 더 연관이 있는 것으로 계산합니다.
- [ ] 마지막으로 연관게시글에서 40% 이하 빈도로 나타나는 단어중 연관단어가 그렇지 않은 단어보다 더 빈번하게 나타날수록 연관이 더 있는것으로 파악합니다.
- [X] 게시글을 작성하고, 목록을 보여주고, 내용을 보여주는 프로그램을 만들어주시고, 게시글내용과 연관 게시글을 같이 표시해주세요.
- [ ] 연관게시글이 보여지는 순서는 연관도가 높은것을 우선적으로 보여주도록 만들어주시면 더 좋습니다

# 개선사항 및 미비사항

## 연관게시물의 재설정

연관게시물 생성 시 새롭게 생성되는 게시물만 연관 게시물 설정 가능합니다.

예를 들어 전체게시물에서 특정 단어의 비율이 60%를 넘어서 배제 했던 단어가, 게시물의 숫자가 증가할 수록 비율이 낮아져 조건을 만족할 시
게시물을 생성한 시점이 아니라면 추가로 연관게시물을 설정해주는 작업이 미비합니다.

이 부분은 `Event`를 활용하는 방법을 생각해보았습니다.
우선 현재의 로직에서는 컨트롤러 단에서 순서를 제어하는데 이를 서비스에 넘겨 서비스 단에서 `Event`로 제어하면 더욱 코드의 결합도가 낮아지고 확장성이 높아질 것이란 생각이었습니다. 
마찬가지로 하루에 한번 혹은 특정 기간, 임의의 조건을 달성할 시 이벤트를 발생시켜 연관게시물은 재설정 해주는 작업도 가능할 것 같습니다. 이는 `Spring Batch`를 통해 작업이 이루어지면 좋겠다 생각했습니다.


## 머릿속에는 떠오르나 구현하지 못한 로직에 대해

우선 먼저 양해 말씀 드립니다. 최대한 구현해보려 했으나, 역량의 부족으로 인해 떠오른 로직을 구현하지 못했습니다. 하지만 해당 고민의 흔적이라도 남기고자합니다.

첫 번째 연관게시물을 선정하는 로직
저는 `query`를 활용하여 해결하려 했습니다.
```java
@Query("select k.articleId, count(*) from Keyword as k where k.refKeyword in :keyword group by k.articleId having (count (*)>1) order by count(*) desc ")
```

제가 생각한 query는 위와 같고 이에 대한 결과 값으로

|ARTICLE_ID|COUNT(*)|
|------|---|
|1|3|
|2|2|
|3|1|


위와 같은 결과를 얻어 COUNT(*) 의 갯수가 2 이상 (연관 되는 키워드가 2개 이상) 인 경우의 `ARTICLE_ID` 를 넘겨주어 연관게시글에 매핑 하려고 했습니다.

그러나 해당 값이 `Entity`가 아닌 `Tuple`로 나와 `Count`를 활용할 수 없게 되었습니다.

제가 생각한 방식은

`Article_Id` 의 `Count` 즉 (1)연관단어의 카운팅 갯수가 높은 것을 -> 게시글에 40% 이하 빈도로 나타나는 단어는 자주 나타날 수록 더 연관이 있는 것 에 대한 빈도로 파악하고

(2) 작성한 본문에서의 해당 키워드의 비율을 계산하여 (1),(2) 두 가지를 고려해 연관게시글의 Priority를 설정해주려 했습니다.

추가로 Redis 나 ELK stack 을 활용하여 속도 개선 및 검색 성능 향상에 대한 부분도 고민해보았습니다.


# API

## 게시글 가져오기
```
Request

GET /articles/{article-id}

```
```
Response
{
    "data": {
        "articleId": 1,
        "title": "과제",
        "content": "월요일 날씨는 맑음입니다",
        "created_at": "2023-04-28T14:02:17.284903",
        "refArticles": []
    }
}

```


## 게시글 리스트 가져오기
```
Request

GET /articles/{article-id}

```
```
Response
{
    "data": [
        {
            "articleId": 5,
            "title": "과제",
            "createdAt": "2023-04-28T14:02:32.760767"
        },
        {
            "articleId": 4,
            "title": "과제",
            "createdAt": "2023-04-28T14:02:29.205467"
        },
        {
            "articleId": 3,
            "title": "과제",
            "createdAt": "2023-04-28T14:02:24.656748"
        },
        {
            "articleId": 2,
            "title": "과제",
            "createdAt": "2023-04-28T14:02:20.420317"
        },
        {
            "articleId": 1,
            "title": "과제",
            "createdAt": "2023-04-28T14:02:17.284903"
        }
    ],
    "pageInfo": {
        "page": 1,
        "size": 10,
        "totalElements": 5,
        "totalPages": 1
    },
    "barNumber": null
}
```

## 게시글 작성
```
POST /article

RequestBody
{
    "title": "과제",
    "content":"금요일 날씨는 맑음입니다"
}
```
```
Response
{
    "data": {
        "id": 5,
        "title": "과제",
        "content": "금요일 날씨는 맑음입니다",
        "createdAt": "2023-04-28T14:02:32.7607665",
        "modifiedAt": "2023-04-28T14:02:32.7607665"
    }
}
```

