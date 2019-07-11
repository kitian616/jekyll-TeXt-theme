---
layout: article
title: How to change favicon
tags: Github
sidebar:
  nav: docs-en
---

**1. 이미지 다운**

512x512 이상의 1:1 비율의 이미지(png 등)를 고른다.
navigation bar에 나타나는 logo로 사용할 svg 혹은 ai 파일을 고른다. (`_includes/svg/icon/social`에 들어가보면 유명한 community svg 파일들이 있다)

**2. Favicon으로 변환**

[https://realfavicongenerator.net/](https://realfavicongenerator.net/) 에서 favicon을 생성할 수 있고
[https://convertio.co/kr/ai-svg/](https://convertio.co/kr/ai-svg/) 에서 ai 파일을 svg 파일로 변환할 수 있다.

**3. assets 폴더에 파일들을 덮어씌우면 끝!**

1) `browserconfig.xml`과  `site.webmanifest` 파일은 제외  
2) `_includes/svg`에 svg 파일을 덮어씌운다.
