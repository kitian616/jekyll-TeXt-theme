* Quick Start: 
    https://tianqi.name/jekyll-TeXt-theme/docs/en/quick-start
* Ruby/JekyII setup: 
    https://www.notion.so/ooshyun/SETUP-a8239fb0d6bb4332a542b58b91a1f515

* Generate Gemfile.lock: 
    docker run --rm -v "$PWD":/usr/src/app -w /usr/src/app ruby:2.6 bundle install
* Build Docker image: 
    docker-compose -f ./docker/docker-compose.build-image.yml build
* Run local server(http://localhost:4000/): 
    docker-compose -f ./docker/docker-compose.default.yml up

* Post Upload
    Add the post in _post directory

* Add the Category
    Add navigation.yml

* Add nav/toc(SubCategory)
    1. _data/navigation에 navigate 항목을 추가한다.
        각 페이지마다 nav를 설정해줘야 nav가 보인다!
        - 예제. file: _data/navigation.yml
            header:
            - titles:
                ko      : &KO       수식이 깨져 보일 때
                ko-KR   : *KO
                url: 2019/01/03/when_latex_not_appear.html

            - titles:
                ko      : &KO       Youtube 채널
                ko-KR   : *KO
                url: https://www.youtube.com/channel/UCoCrPRq7wa6q0p59abF5aNw?view_as=subscriber

            docs-ko:
            - title:      기초 선형대수학 이론
                children:
                - title:  벡터의 기본 연산(상수배, 덧셈)
                    url:    2020/09/07/basic_vector_operation.html
                - title:  행렬 곱에 대한 또 다른 시각
                    url:    2020/09/08/matrix_multiplication.html
                - title:  행벡터의 의미와 벡터의 내적

            - title:      선형대수학 응용
                children:
                - title:  주성분분석(PCA)
                    url:    2019/07/27/PCA.html
                - title:  고윳값 분해(EVD)
                    url:    2020/11/19/eigen_decomposition.html
            ...

    2. nav가 보여지고 싶은 페이지마다 nav를 추가해줘야 한다.
        * 날짜 기입시 주의할 것! /2022/06/18/test-for-post.html
        - Example, Reference. https://github.com/angeloyeo/angeloyeo.github.io
            - _posts/xx.md
                ---
                title: 수식이 깨져 보일 때 대처법
                sidebar:
                    nav: docs-ko
                aside:
                    toc: true
                key: 20190103
                tags: 도움말
                ---
