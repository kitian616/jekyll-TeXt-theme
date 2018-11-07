---
title: "[WebHacking] Browser XSS Filter 해제하기"
key: 20180315
tags: "WebHacking XSS"
---

XSS(Cross Site Script) 공격을 차단하기 위해 Internet Explorer, Chrome 등 브라우저 자체적으로 XSS를 방어하는 보안기능을 제공하고 있습니다.

브라우저 XSS 필터 기능은 Stored XSS 공격은 차단할 수 없지만 파라미터에 악의적인 스크립트를 삽입하고 전달하는 Reflected XSS 방식은 잘(?) 방어해주는 편입니다.

(SXSS취약점은 정상적인 스크립트인지 악의적으로 삽입 된 스크립트인지 브라우저가 판단하기 어렵기 때문입니다.)

브라우저는 "XSS 필터 사용"을 기본적으로 설정되어 있습니다. 따라서 사용자가 일부러 브라우저 XSS 필터를 해제하여 사용하거나, 낮은 버전에 브라우저를 사용하지 않는다면 공격에 성공하기는 어렵고, 영향력이 떨어지는 편입니다.

![](https://t1.daumcdn.net/cfile/tistory/99C0DF435AA78C7722)





이러한 브라우저(IE) XSS 필터를 우회하기 위한 취약점 XXN(X-XSS-Nightmare)이 나오기도 했습니다.

(참고 : https://www.slideshare.net/masatokinugawa/xxn-en)



개발을 하거나, 침투 테스를 위해서 XSS Filter를 해제가 필요할 경우도 있는데요.

브라우저 별로 해제 하는 방법을 알아보겠습니다.



## 1. Chrome
```
"C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --disable-xss-auditor
```

크롬 바로가기 아이콘 속성에서 대상에 chrome 파일명 뒤에  --disable-xss-auditor를 추가 해주면 됩니다.
![](https://t1.daumcdn.net/cfile/tistory/9930A24B5AA792E207)


해제 후 아래처럼 XSS 공격이 성공한 것 을 볼 수 있습니다.
![](https://t1.daumcdn.net/cfile/tistory/991B9B475AA7932625)


## 2. Internet Explorer
IE 브라우저는 인터넷 옵션 - 인터넷 - 사용자 지정수준 - XSS 필터 사용 - 사용안함 체크 로 해제 할 수 있습니다.
![](https://t1.daumcdn.net/cfile/tistory/9933624A5AA792CA03)


## 3. Firefox
Firefox 브라우저는 주소입력 창에 "about:config"를 입력하고 "browser.urlbar.filter.javascript" 를 검색하여 "false" 값으로 변경하여 해제 할 수 있습니다. (파폭은 설정을 따로 안해줘도... xss가 발생하는거 같긴한데...)
![](https://t1.daumcdn.net/cfile/tistory/995611415AA9E39609)

