---
title: Django admin 아이디, 비번 찾기
tag: django
---





## 둘다 모를때

```
\# python manage.py shell
\# from django.contrib.auth.models import User
\# User.objects.filter(is_superuser=True)
-> superuser id 확인. id를 알고있으면 생략
\# super_id = User.objects.get(username='admin id')
\# super_id.set_password('변경 비밀번호')
\# super_id.save()
\# exit()
```



## 아이디를 알 때

```
# python manage.py changepassword 사용자ID
# 변경할 비밀번호 입력
```



[본문](User.objects.filter(is_superuser=True) 출처: https://iyc1030.tistory.com/entry/PythonDjango-Admin-SuperUser-일반-User-패스워드-비밀번호-변경 [기록, 메모장])

