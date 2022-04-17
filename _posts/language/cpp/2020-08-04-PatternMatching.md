---
titile: Pattern Matching, regex
tag: cpp
---



+ Password
  + 영문 대소문자, 숫자만 입력

---

## 정규 표현식

**정규 표현식**(正規表現式, [영어](https://ko.wikipedia.org/wiki/영어): regular expression, 간단히 regexp[[1\]](https://ko.wikipedia.org/wiki/정규_표현식#cite_note-1) 또는 regex, rational expression)[[2\]](https://ko.wikipedia.org/wiki/정규_표현식#cite_note-Mitkov2003-2)[[3\]](https://ko.wikipedia.org/wiki/정규_표현식#cite_note-Lawson2003-3) 또는 **정규식**(正規式)은 특정한 규칙을 가진 문자열의 집합을 표현하는 데 사용하는 [형식 언어](https://ko.wikipedia.org/wiki/형식_언어)이다. 정규 표현식은 많은 [텍스트 편집기](https://ko.wikipedia.org/wiki/텍스트_편집기)와 [프로그래밍 언어](https://ko.wikipedia.org/wiki/프로그래밍_언어)에서 문자열의 검색과 치환을 위해 지원하고 있으며, 특히 [펄](https://ko.wikipedia.org/wiki/펄)과 [Tcl](https://ko.wikipedia.org/wiki/Tcl)은 언어 자체에 강력한 정규 표현식을 구현하고 있다.

컴퓨터 과학의 [정규 언어](https://ko.wikipedia.org/wiki/정규_언어)로부터 유래하였으나 구현체에 따라서 정규 언어보다 더 넓은 언어를 표현할 수 있는 경우도 있으며, 심지어 정규 표현식 자체의 문법도 여러 가지 존재하고 있다. 현재 많은 [프로그래밍 언어](https://ko.wikipedia.org/wiki/프로그래밍_언어), 텍스트 처리 프로그램, 고급 텍스트 편집기 등이 정규 표현식 기능을 제공한다. 일부는 [펄](https://ko.wikipedia.org/wiki/펄), [자바스크립트](https://ko.wikipedia.org/wiki/자바스크립트), [루비](https://ko.wikipedia.org/wiki/루비_(프로그래밍_언어)), [Tcl](https://ko.wikipedia.org/wiki/Tcl)처럼 문법에 내장되어 있는 반면 [닷넷 언어](https://ko.wikipedia.org/wiki/닷넷_프레임워크), [자바](https://ko.wikipedia.org/wiki/자바_(프로그래밍_언어)), [파이썬](https://ko.wikipedia.org/wiki/파이썬), [POSIX C](https://ko.wikipedia.org/wiki/C_POSIX_라이브러리), [C++](https://ko.wikipedia.org/wiki/C%2B%2B) ([C++11](https://ko.wikipedia.org/wiki/C%2B%2B11) 이후)에서는 [표준 라이브러리](https://ko.wikipedia.org/wiki/표준_라이브러리)를 통해 제공한다. 그 밖의 대부분의 언어들은 별도의 라이브러리를 통해 정규 표현식을 제공한다.

정규 표현식은 [검색 엔진](https://ko.wikipedia.org/wiki/검색_엔진), [워드 프로세서](https://ko.wikipedia.org/wiki/워드_프로세서)와 [문서 편집기](https://ko.wikipedia.org/wiki/문서_편집기)의 찾아 바꾸기 대화상자, 그리고 [sed](https://ko.wikipedia.org/wiki/Sed), [AWK](https://ko.wikipedia.org/wiki/AWK)와 같은 문자 처리 유틸리티, [어휘 분석](https://ko.wikipedia.org/wiki/낱말_분석)에 사용된다.

----

### Sum

숫자만 가능 : [ 0 ~ 9 ] 주의 : 띄어쓰기 불가능

> /^[0-9]+$/

 이메일 형식만 가능

> /^([\w-]+(?:\.[\w-]+)*)@((?:[\w-]+\.)*\w[\w-]{0,66})\.([a-z]{2,6}(?:\.[a-z]{2})?)$/

한글만 가능 : [ 가나다라 ... ] 주의 : ㄱㄴㄷ... 형식으로는 입력 불가능 , 띄어쓰기 불가능

> /^[가-힣]+$/

한글,띄어쓰기만 가능 : [ 가나다라 ... ] 주의 : ㄱㄴㄷ... 형식으로는 입력 불가능 , 띄어쓰기 가능

> /^[가-힣\s]+$/

영문만 가능 :

> /^[a-zA-Z]+$/

 영문,띄어쓰기만 가능

> /^[a-zA-Z\s]+$/

전화번호 형태 : 전화번호 형태 000-0000-0000 만 받는다. ]

> /^[0-9]{2,3}-[0-9]{3,4}-[0-9]{4}$/

도메인 형태, http:// https:// 포함안해도 되고 해도 되고

> /^(((http(s?))\:\/\/)?)([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?$/

도메인 형태, http:// https:// 꼭 포함

> /^((http(s?))\:\/\/)([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?$/

도메인 형태, http:// https:// 포함하면 안됨

> /^[^((http(s?))\:\/\/)]([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?$/

한글과 영문만 가능

> /^[가-힣a-zA-Z]+$/;

숫자,알파벳만 가능

> /^[a-zA-Z0-9]+$/;

주민번호, -까지 포함된 문자열로 검색

> /^(?:[0-9]{2}(?:0[1-9]|1[0-2])(?:0[1-9]|[1,2][0-9]|3[0,1]))-[1-4][0-9]{6}$/

+ 예외
  Jquery 에서는 $.test() 메서드로,

   PHP 에서는 preg_match() 함수로 사용



### 기본 문법

| ^The        | The로 시작하는 문자열                                        |
| ----------- | ------------------------------------------------------------ |
| of despair$ | of despair로 끝나는 문자열                                   |
| ^abc$       | abc로 시작하고 abc로 끝나는 문자열 (abc 라는 문자열도 해당됨) |
| notice      | notice가 들어 있는 문자열                                    |

| ab*  | a 다음에 b가 0개 이상 (a, ab, abbb 등등) |
| ---- | ---------------------------------------- |
| ab+  | a 다음에 b가 1개 이상 (ab, abbb 등등)    |
| ab?  | a 다음에 b가 있거나 없거나 (ab 또는 a)   |

| ab{2}   | a 다음에 b가 2개 있는 문자열 (abb)                       |
| ------- | -------------------------------------------------------- |
| ab{2,}  | a 다음에 b가 2개 이상 (abb, abbbb 등등)                  |
| ab{3,5} | a 다음에 b가 3개에서 5개 사이 (abbb, abbbb, 또는 abbbbb) |

*, +, ?는 각각 {0,}, {1,}, {0,1}과 같습니다.



| ( )는 문자열을 묶음 처리할 때 사용 |                                    |
| ---------------------------------- | ---------------------------------- |
| a(bc)*                             | a 다음에 bc가 0개 이상 (묶음 처리) |
| a(bc){1,5}                         | a 다음에 bc가 1개에서 5개 사이     |

| hi\|hello | hi나 hello가 들어 있는 문자열                           |
| --------- | ------------------------------------------------------- |
| (b\|cd)ef | bef 또는 cdef                                           |
| (a\|b)*c  | a와 b가 섞여서 여러번 나타나고 그뒤에 c가 붙어있는 패턴 |

| . (점) | 임의의 한 문자             |
| ------ | -------------------------- |
| ^.{3}$ | 3문자로만 되어 있는 문자열 |

| [ ]         | 괄호 안에 있는 내용 중 임의의 한 문자                        |
| ----------- | ------------------------------------------------------------ |
| [^ ]        | 첫문자로 ^를 쓰면 괄호 내용의 부정. 즉 괄호 안에 포함되지 않는 한 문자 |
| [ab]        | a 또는 b (a\|b 와 동일한 표현)                               |
| [a-d]       | 소문자 a에서 d까지 (a\|b\|c\|d 또는 [abcd] 와 동일)          |
| ^[a-zA-Z]   | 영문자로 시작하는 문자열                                     |
| [0-9]%      | % 문자 앞에 하나의 숫자가 붙어 있는 패턴                     |
| %[^a-zA-Z]% | 두 % 문자 사이에 영문자가 없는 패턴                          |

| 특수 문자 자체를 검색하기 및 사용하기 |                         |      |                            |
| ------------------------------------- | ----------------------- | ---- | -------------------------- |
| \^                                    | ^                       | \.   | .                          |
| \[                                    | [                       | \$   | $                          |
| \(                                    | (                       | \)   | )                          |
| \|                                    | \|                      | \*   | *                          |
| \+                                    | +                       | \?   | ?                          |
| \{                                    | {                       | \\   | \                          |
| \n                                    | 줄넘김 문자             | \r   | 리턴 문자                  |
| \w                                    | 알파벳과 _ (언더바)     | \W   | 알파벳과 _ 가 아닌 것      |
| \s                                    | 빈 공간(space)          | \S   | 빈 공간이 아닌 것          |
| \d                                    | 숫자                    | \D   | 숫자가 아닌 것             |
| \b                                    | 단어와 단어 사이의 경계 | \B   | 단어 사이의 경계가 아닌 것 |
| \t                                    | Tab 문자                | \xnn | 16진수 nn에 해당하는 문자  |

[ ] 안에서는 특수 문자가 모두 효력을 잃게 됩니다.

.

.

[정리: C/C++ 정규표현식](https://m.blog.naver.com/PostView.nhn?blogId=gnsehfvlr&logNo=221317606321&proxyReferer=https:%2F%2Fwww.google.com%2F)

---

## 정규 표현식 라이브러리

### egular expression

정규 표현식은 문자열에서 패턴을 찾는데 사용하는데, 이를 통해

- 주어진 문자열이 주어진 규칙에 맞는지 확인할 때
- 주어진 문자열에서 원하는 패턴의 문자열을 검색할 때
- 주어진 문자열에서 원하는 패턴의 문자열로 치환할 때

와 같은 경우에 매우 유용하게 사용됩니다.

#### 정의

> ex) 파일들의 이름을 표준화 시켰을 때
>
> db-\d*-log\.txt
>
> 여기서 `\d*` 는 임의의 개수의 숫자를 의미하는 것이고, `.` 앞에 `\` 을 붙여준 이유는 `.` 을 그냥 썼을 때 *임의의 문자*로 해석되기 때문입니다.

```cpp
std::regex re("db-\\d*-log\\.txt");
```

C++ 에서 정규 표현식을 사용하기 위해서는 먼저 위와 같이 정규 표현식 객체를 정의해야 합니다.

참고로 정규 표현식 문법의 종류와, 정규 표현식을 처리하는 엔진 역시 여러가지 종류가 있는데, 위 생성자에 추가적인 인자로 전달할 수 있습니다. 

#### 사용

```cpp
#include <iostream>
#include <regex>
#include <vector>
 
using namespace std;

int main() 
{
  // 주어진 파일 이름들.
    vector<string> file_names = {"db-123-log.txt", "db-124-log.txt",
                                         "not-db-log.txt", "db-12-log.txt",
                                         "db-12-log.jpg"};
    //regex의 re 생성 ("기준")
    regex re("db-\\d*-log\\.txt");
    
    //file_name를 참조해서 배열 file_names에 대입
  for (const auto &file_name : file_names) 
  {
    // std::boolalpha 는 bool 을 0 과 1 대신에 false, true 로 표현하게 해줍니다.
    cout << file_name << ": " << boolalpha
              << regex_match(file_name, re) << '\n';
      //file_name을 re의 형식에 맞게
      //regex_match 를 사용해서 전체 문자열이 주어진 정규 표현식 패턴을 만족하는지 알아 낼 수 있다
  }
}
```

---

예를 들어서 `grep` 의 정규 표현식 문법을 사용하고 싶다면

```cpp
std::regex re("db-\\d*-log\\.txt", std::regex::grep);
```

* 만약에 인자를 지정하지 않았다면 디폴트로

```cpp
std::regex re("db-\\d*-log\\.txt",std::regex::ECMAScript);
```

어떤 문법을 사용할 지 이외에도 몇 가지 특성들을 더 추가할 수 있는데, 

* 예를 들어서 `std::regex::icase` 를 전달한다면 <u>대소 문자를 구분하지 않게 됩니다.</u>
  * 이 때 <u>특성을 추가하는 방법</u>은 `|` 로 연결하면 됩니다. 

```cpp
std::regex re("db-\\d*-log\\.txt", std::regex::grep | std::regex::icase);
```

자 그렇다면 만들어진 정규식 객체를 사용하는 부분을 살펴봅시다. 

`std::regex_match` 는 첫 번째 인자로 전달된 문자열 (위 경우 `file_name`) 이 두 번째 인자로 전달된 정규 표현식 객체 (위 경우 `re`) 와 **완전히** 매칭이 된다면 `true` 를 리턴합니다. 

완전히 매칭 된다는 말은 문자열 전체가 정규 표현식의 패턴에 부합해야 한다는 것이지요.



### 부분 매칭 뽑아내기

예를 들어서 사용자로 부터 전화번호를 받는 정규 표현식을 생각해봅시다. 전화번호는 간단히 생각해서 다음과 같은 규칙을 만족한다고 생각합니다.

- (숫자)-(숫자)-(숫자) 꼴로 있어야 합니다.
- 맨 앞자리는 반드시 3 자리이며 0 과 1 로만 이루어져 있어야 합니다.
- 가운데 자리는 3 자리 혹은 4 자리 여야 합니다.
- 마지막 자리는 4 자리 여야 합니다.

```
[01]{3}-\d{3,4}-\d{4}
```

맨 앞에 `[01]` 의 뜻은 0 혹은 1 이라는 의미이고, 뒤에 `{3}` 은 해당 종류의 문자가 3 번 나타날 수 있다는 의미입니다.

---

+ 캡쳐 그룹 (capture group) 을 사용

```cpp
std::regex re("[01]{3}-(\\d{3,4})-\\d{4}");
```

위와 같이 `()` 로 원하는 부분을 감싸게 된다면 해당 부분에 매칭된 문자열을 얻을 수 있게 됩니다.

> 그렇다면 매칭된 부분을 어떻게 얻을 수 있는지 살펴보도록 합시다.

```cpp
std::smatch match;
```

+ `smatch`  
  + 매칭된 문자열을 [std::string](https://modoocode.com/237) 의 형태로 돌려줍니다. 



```cpp
if (std::regex_match(number, match, re)) {
```

다음에 `regex_match` 에 매칭된 결과를 보관할 `match` 와 정규 표현식 `re` 를 모두 전달합니다. 

만일 `number` 가 `re` 의 패턴에 부합하다면 `match` 에 매칭된 결과가 들어 있을 것입니다.

```cpp
for (size_t i = 0; i < match.size(); i++) {
  std::cout << "Match : " << match[i].str() << std::endl;
}
```

자 그럼 이제 `match` 에서 매칭된 문자열들을 `match[i].str()` 을 통해 접근할 수 있습니다. 

참고로 우리의 `match` 가 `smatch` 이므로 `match[i].str()` 은 [std::string](https://modoocode.com/237) 이 됩니다. 

반면에 `match` 가 `cmatch` 였다면 `match[i].str()` 는 `const char*` 이 되겠지요.



### 원하는 패턴 검색하기

앞서 `regex_match` 를 통해 문자열 전체가 패턴에 부합하는지 확인하는 작업을 하였습니다. 이번에는 전체 말고 패턴을 만족하는 문자열 **일부** 를 검색하는 작업을 수행해보도록 하겠습니다.

우리가 하고 싶은 일은 HTML 문서에서 아래와 같은 형태의 태그만 읽어들이는 것입니다.

```
<div class="sk...">...</div>
```

그렇다면 해당 조건을 만족하는 정규 표현식은 아래와 같이 작성할 수 있습니다.

```
<div class="sk[\w -]*">\w*</div>
```

---

```cpp
while (std::regex_search(html, match, re)) {
```

문자열에서 원하는 패턴을 검색하는 일은 `regex_search` 를 사용하면 됩니다. `regex_match` 처럼, 첫 번째에 검색을 하고픈 문자열을, 두 번째에 일치된 패턴을 보관할 `match` 객체를, 마지막 인자로 실제 정규 표현식 객체를 전달하면 됩니다. 만일 `html` 에서 정규 표현식과 매칭이 되는 패턴이 존재한다면 `regex_search` 가 `true` 를 리턴하게 되지요.

```cpp
std::cout << match.str() << '\n';
```

그리고 매칭된 패턴은 위와 같이 `match.str()` 을 통해서 접근할 수 있습니다. 우리의 `match` 가 `smatch` 의 객체 이므로 만들어진 `match.str()` 은 [string](https://modoocode.com/237) 이 됩니다.



#### 재실행, 업데이트

문제는 만일 그냥 `std::regex_search(html, match, re)` 를 다시 실행하게 된다면 그냥 이전에 찾았던 패턴을 다시 뱉을 것입니다. 

따라서 우리는 `html` 을 업데이트 해서 <u>검색된 패턴 바로 뒤 부터 다시 검색할 수 있도록 바꿔야합니다.</u>

```cpp
html = match.suffix();
```

+ `match.suffix()` 를 하면 `std::sub_match` 객체를 리턴합니다. 
  * `sub_match` 는 단순히 어떠한 문자열의 시작과 끝을 가리키는 반복자 두 개를 가지고 있다고 보시면 됩니다. 

이 때 `suffix` 의 경우, <u>원 문자열에서 검색된 패턴 바로 뒤 부터, 이전 문자열의 끝 까지에 해당하는 `sub_match` 객체를 리턴합니다.</u>



### std::regex_iterator

//반복자

```cpp
auto start = std::sregex_iterator(html.begin(), html.end(), re);
```

`regex_iterator` 의 경우 위와 같이 생성자를 호출함으로써 생성할 수 있습니다. 

첫 번째와 두 번째 인자로 검색을 수행할 문자열의 시작과 끝을 전달하고, 마지막 인자로 사용하고픈 정규 표현식 객체를 전달하면 됩니다.



### 원하는 패턴 치환하기

//정리 x

.

.

[정리: 씹어먹는 C++: 정규 표현식](https://modoocode.com/303)

---

https://hamait.tistory.com/342

https://codingcoding.tistory.com/928