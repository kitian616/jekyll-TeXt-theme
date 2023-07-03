---
#layout: single
title:  "[Dacon] 구내식당 식수 예측"
categories: Dacon
tag: [python, Dacon, ml]
toc: true
author_profile: true
---
데이콘 실무역량 강화교육을 통해 경험했던 첫 번째 프로젝트에 대한 코드입니다. 코랩을 사용하였고 간단한 EDA를 통해 데이터의 특성을 파악하고 그에 따른 모델을 만들어 식수인원을 예측해보았습니다.


<head>
  <style>
    table.dataframe {
      white-space: normal;
      width: 100%;
      height: 240px;
      display: block;
      overflow: auto;
      font-family: Arial, sans-serif;
      font-size: 0.9rem;
      line-height: 20px;
      text-align: center;
      border: 0px !important;
    }

    table.dataframe th {
      text-align: center;
      font-weight: bold;
      padding: 8px;
    }

    table.dataframe td {
      text-align: center;
      padding: 8px;
    }

    table.dataframe tr:hover {
      background: #b8d1f3; 
    }

    .output_prompt {
      overflow: auto;
      font-size: 0.9rem;
      line-height: 1.45;
      border-radius: 0.3rem;
      -webkit-overflow-scrolling: touch;
      padding: 0.8rem;
      margin-top: 0;
      margin-bottom: 15px;
      font: 1rem Consolas, "Liberation Mono", Menlo, Courier, monospace;
      color: $code-text-color;
      border: solid 1px $border-color;
      border-radius: 0.3rem;
      word-break: normal;
      white-space: pre;
    }

  .dataframe tbody tr th:only-of-type {
      vertical-align: middle;
  }

  .dataframe tbody tr th {
      vertical-align: top;
  }

  .dataframe thead th {
      text-align: center !important;
      padding: 8px;
  }

  .page__content p {
      margin: 0 0 0px !important;
  }

  .page__content p > strong {
    font-size: 0.8rem !important;
  }

  </style>
</head>



```python
from google.colab import drive
drive.mount('/content/drive')
```

<pre>
Drive already mounted at /content/drive; to attempt to forcibly remount, call drive.mount("/content/drive", force_remount=True).
</pre>

```python
import pandas as pd
import matplotlib.pyplot as plt
from sklearn.model_selection import cross_val_score, train_test_split, KFold
from sklearn.metrics import mean_squared_error, make_scorer
from sklearn.ensemble import RandomForestRegressor
from xgboost import XGBRegressor
from sklearn.metrics import mean_absolute_error
```

## 1.데이터 로드, 전처리



```python
train = pd.read_csv('/content/drive/MyDrive/235743_구내식당 식사 인원 예측 AI 경진대회_data (1)/train.csv')
test = pd.read_csv('/content/drive/MyDrive/235743_구내식당 식사 인원 예측 AI 경진대회_data (1)/test.csv')
submission = pd.read_csv('/content/drive/MyDrive/235743_구내식당 식사 인원 예측 AI 경진대회_data (1)/sample_submission.csv')
```


```python
train.head(1)
```

<pre>
           일자 요일  본사정원수  본사휴가자수  본사출장자수  본사시간외근무명령서승인건수  현본사소속재택근무자수  \
0  2016-02-01  월   2601      50     150             238          0.0   

                                                조식메뉴  \
0  모닝롤/찐빵  우유/두유/주스 계란후라이  호두죽/쌀밥 (쌀:국내산) 된장찌개  쥐...   

                                                중식메뉴  \
0  쌀밥/잡곡밥 (쌀,현미흑미:국내산) 오징어찌개  쇠불고기 (쇠고기:호주산) 계란찜 ...   

                                                석식메뉴     중식계    석식계  
0  쌀밥/잡곡밥 (쌀,현미흑미:국내산) 육개장  자반고등어구이  두부조림  건파래무침 ...  1039.0  331.0  
</pre>

```python
test.head(1)
```

<pre>
           일자 요일  본사정원수  본사휴가자수  본사출장자수  본사시간외근무명령서승인건수  현본사소속재택근무자수  \
0  2021-01-27  수   2983      88     182               5        358.0   

                                                조식메뉴  \
0  모닝롤/연유버터베이글 우유/주스 계란후라이/찐계란 단호박죽/흑미밥 우거지국 고기완자...   

                                                중식메뉴  \
0  쌀밥/흑미밥/찰현미밥 대구지리 매운돈갈비찜 오꼬노미계란말이 상추무침 포기김치 양상추...   

                                            석식메뉴  
0  흑미밥 얼큰순두부찌개 쇠고기우엉볶음 버섯햄볶음 (New)아삭이고추무절임 포기김치   
</pre>

```python
submission.head(1)
```

<pre>
           일자  중식계  석식계
0  2021-01-27    0    0
</pre>

```python
# 석식이용인원 0인 날 삭제
train = train[train['석식계'] != 0]

#요일 숫자로 변경
train.loc[train['요일']=='월','요일'] =1
train.loc[train['요일']=='화','요일'] =2
train.loc[train['요일']=='수','요일'] =3
train.loc[train['요일']=='목','요일'] =4
train.loc[train['요일']=='금','요일'] =5

#월,일 컬럼 추가
def month(text:str):
  return int(text[5:7])

train['월'] = train['일자'].map(month)

def day(text:str):
  return int(text[-2:])

train['일'] = train['일자'].map(day)

train.head()

#현재원 컬럼 추가
train['현재원'] = train['본사정원수'] - train['본사휴가자수'] - train['본사출장자수'] -train['현본사소속재택근무자수']
```

<pre>
/usr/local/lib/python3.7/dist-packages/pandas/core/indexing.py:1817: SettingWithCopyWarning: 
A value is trying to be set on a copy of a slice from a DataFrame.
Try using .loc[row_indexer,col_indexer] = value instead

See the caveats in the documentation: https://pandas.pydata.org/pandas-docs/stable/user_guide/indexing.html#returning-a-view-versus-a-copy
  self._setitem_single_column(loc, value, pi)
/usr/local/lib/python3.7/dist-packages/ipykernel_launcher.py:15: SettingWithCopyWarning: 
A value is trying to be set on a copy of a slice from a DataFrame.
Try using .loc[row_indexer,col_indexer] = value instead

See the caveats in the documentation: https://pandas.pydata.org/pandas-docs/stable/user_guide/indexing.html#returning-a-view-versus-a-copy
  from ipykernel import kernelapp as app
/usr/local/lib/python3.7/dist-packages/ipykernel_launcher.py:20: SettingWithCopyWarning: 
A value is trying to be set on a copy of a slice from a DataFrame.
Try using .loc[row_indexer,col_indexer] = value instead

See the caveats in the documentation: https://pandas.pydata.org/pandas-docs/stable/user_guide/indexing.html#returning-a-view-versus-a-copy
/usr/local/lib/python3.7/dist-packages/ipykernel_launcher.py:25: SettingWithCopyWarning: 
A value is trying to be set on a copy of a slice from a DataFrame.
Try using .loc[row_indexer,col_indexer] = value instead

See the caveats in the documentation: https://pandas.pydata.org/pandas-docs/stable/user_guide/indexing.html#returning-a-view-versus-a-copy
</pre>

```python
#요일 숫자로 변경
test.loc[test['요일']=='월','요일'] =1
test.loc[test['요일']=='화','요일'] =2
test.loc[test['요일']=='수','요일'] =3
test.loc[test['요일']=='목','요일'] =4
test.loc[test['요일']=='금','요일'] =5

#월,일 컬럼 추가
def month(text:str):
  return int(text[5:7])

test['월'] = test['일자'].map(month)

def day(text:str):
  return int(text[-2:])

test['일'] = test['일자'].map(day)

test.head()

#현재원 컬럼 추가
test['현재원'] = test['본사정원수'] - test['본사휴가자수'] - test['본사출장자수'] -test['현본사소속재택근무자수']
```


```python
test.head()
```

<pre>
           일자 요일  본사정원수  본사휴가자수  본사출장자수  본사시간외근무명령서승인건수  현본사소속재택근무자수  \
0  2021-01-27  3   2983      88     182               5        358.0   
1  2021-01-28  4   2983     104     212             409        348.0   
2  2021-01-29  5   2983     270     249               0        294.0   
3  2021-02-01  1   2924     108     154             538        322.0   
4  2021-02-02  2   2924      62     186             455        314.0   

                                                조식메뉴  \
0  모닝롤/연유버터베이글 우유/주스 계란후라이/찐계란 단호박죽/흑미밥 우거지국 고기완자...   
1  모닝롤/대만샌드위치 우유/주스 계란후라이/찐계란 누룽지탕/흑미밥 황태국 시래기지짐 ...   
2  모닝롤/핫케익 우유/주스 계란후라이/찐계란 오곡죽/흑미밥 매생이굴국 고구마순볶음 양...   
3  모닝롤/촉촉한치즈케익 우유/주스 계란후라이/찐계란 누룽지탕/흑미밥 두부김칫국 새우완...   
4  모닝롤/토마토샌드 우유/주스 계란후라이/찐계란 채소죽/흑미밥 호박맑은국 오이생채 양...   

                                                중식메뉴  \
0  쌀밥/흑미밥/찰현미밥 대구지리 매운돈갈비찜 오꼬노미계란말이 상추무침 포기김치 양상추...   
1  쌀밥/보리밥/찰현미밥 우렁된장찌개 오리주물럭 청양부추전 수제삼색무쌈 겉절이김치 양상...   
2  쌀밥/흑미밥/찰현미밥 팽이장국 수제돈까스*소스 가자미조림 동초나물무침 포기김치 양상...   
3  쌀밥/흑미밥/찰현미밥 배추들깨국 오리대패불고기 시금치프리타타 부추고추장무침 포기김치...   
4  쌀밥/팥밥/찰현미밥 부대찌개 닭살데리야끼조림 버섯탕수 세발나물무침 알타리김치/사과푸...   

                                            석식메뉴  월   일     현재원  
0  흑미밥 얼큰순두부찌개 쇠고기우엉볶음 버섯햄볶음 (New)아삭이고추무절임 포기김치   1  27  2355.0  
1            충무김밥 우동국물 오징어무침 꽃맛살샐러드 얼갈이쌈장무침 석박지   1  28  2319.0  
2            흑미밥 물만둣국 카레찜닭 숯불양념꼬지어묵 꼬시래기무침 포기김치   1  29  2170.0  
3           흑미밥 동태탕 돈육꽈리고추장조림 당면채소무침 모자반무침 포기김치   2   1  2340.0  
4       흑미밥 바지락살국 쇠고기청경채볶음 두부구이*볶은김치 머위된장무침 백김치   2   2  2362.0  
</pre>
## 2.랜덤포레스트 모델


### 1) 중식계 예측 모델



```python
features= [
 '월',
 '일',
 '현재원',
 '본사휴가자수',
 '본사출장자수',
 '본사시간외근무명령서승인건수',
 '현본사소속재택근무자수',
 '중식계']
```


```python
features = features[:-1]
```


```python

X = train[features]
y = train['중식계']

X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.3, random_state=121)

lunch_model = XGBRegressor()

lunch_model.fit(X_train,y_train)

lunch_count_predict = lunch_model.predict(X_test[features])
```

<pre>
[12:40:40] WARNING: /workspace/src/objective/regression_obj.cu:152: reg:linear is now deprecated in favor of reg:squarederror.
</pre>

```python
lunch_count_predict
```

<pre>
array([ 945.72906,  539.6704 , 1186.4695 , 1000.1311 ,  866.5936 ,
       1179.529  , 1090.462  , 1015.5139 ,  824.95374,  749.7693 ,
        912.766  ,  820.5057 ,  864.0983 ,  618.9503 , 1022.23706,
        903.33545,  862.64636, 1002.95233,  869.6132 ,  886.7356 ,
        702.4766 ,  898.84064,  867.4587 ,  908.203  ,  838.39294,
        820.34595,  654.7836 ,  951.83514,  940.3116 ,  912.1308 ,
        967.53107,  829.6354 , 1175.6791 ,  930.3572 ,  857.8167 ,
        871.6047 ,  941.6267 ,  631.5817 ,  991.15234,  958.33734,
        572.5593 , 1095.0574 ,  998.5118 ,  895.9858 ,  860.61365,
       1123.1451 ,  749.8649 ,  993.2236 , 1186.4003 ,  783.4473 ,
       1045.7416 , 1198.7057 , 1062.2472 , 1032.5531 ,  922.61176,
       1231.4353 , 1197.9161 ,  579.7688 ,  795.3104 ,  663.2518 ,
        966.3141 ,  943.6151 ,  875.8223 ,  961.96967, 1149.7561 ,
        731.13   ,  746.8876 , 1053.3972 ,  600.7532 ,  539.03345,
        880.67645,  842.24347,  898.4181 ,  759.09186,  874.743  ,
       1170.4321 ,  877.7689 ,  871.3899 , 1132.841  , 1150.67   ,
       1085.3727 ,  904.3873 ,  847.164  ,  949.7919 , 1046.3679 ,
        927.10596,  859.9272 ,  775.84216, 1165.0404 ,  605.0369 ,
        397.76468,  948.38763,  793.17267, 1108.1409 ,  670.02026,
        836.3036 ,  851.8093 ,  726.95166,  957.61395,  772.92035,
        933.2524 , 1201.1577 ,  938.01526,  750.73236,  641.255  ,
        884.43225,  862.0689 , 1021.932  ,  741.13165,  702.452  ,
        870.5559 ,  719.277  ,  866.1785 ,  933.4715 , 1165.7429 ,
        927.05255, 1080.068  ,  745.60345,  826.38367, 1045.7416 ,
        926.73016,  899.98755, 1149.8062 , 1100.2124 , 1088.5576 ,
        847.4715 ,  891.8503 ,  541.43536,  786.1559 , 1231.6101 ,
       1126.5914 ,  677.0973 ,  747.98315,  781.1359 ,  907.05035,
       1135.4366 ,  953.1696 ,  968.1085 ,  797.1345 ,  829.36163,
        948.31726,  806.768  ,  958.382  ,  892.5848 , 1157.5659 ,
        680.4911 ,  928.4836 ,  903.1377 ,  911.29047, 1037.3842 ,
       1121.2179 ,  951.7742 ,  521.12683,  867.3005 ,  811.7522 ,
       1127.2616 ,  695.6873 ,  744.62915,  693.4935 ,  985.8001 ,
        620.4086 , 1050.0452 ,  971.53986,  830.3142 ,  834.98944,
       1052.7211 , 1103.9701 ,  654.7836 ,  548.16016,  637.187  ,
        914.1126 ,  595.23083,  828.99005,  947.46606,  976.89294,
       1208.3907 ,  799.8632 ,  710.0127 ,  833.639  ,  813.0613 ,
        751.99176,  960.1474 , 1121.0444 ,  612.2651 ,  873.08484,
        951.23047, 1021.57623,  918.07623,  841.03125,  941.181  ,
        877.2818 ,  936.2629 ,  907.5369 ,  904.9996 ,  723.80414,
       1092.5776 ,  783.09357,  684.5076 , 1238.9667 ,  877.38605,
        639.3852 ,  968.8393 ,  820.5667 ,  909.1334 , 1266.623  ,
        941.17206,  768.3477 ,  971.9485 ,  647.83093,  740.97424,
        604.58167,  908.203  ,  569.7109 , 1204.2941 ,  734.0915 ,
        602.7272 ,  813.4198 , 1176.5483 ,  974.48   ,  784.04193,
        666.133  , 1118.6285 ,  979.6803 ,  989.7375 ,  577.63275,
        926.74585, 1169.1998 ,  879.5887 , 1166.3804 ,  935.2413 ,
        605.6789 ,  692.4347 ,  854.79974,  731.7876 ,  802.7087 ,
       1068.8733 , 1028.7754 , 1135.6577 ,  867.25586,  996.7889 ,
       1248.1224 ,  928.75183,  887.4299 ,  949.27423,  853.7457 ,
        757.33093, 1144.966  ,  607.1081 ,  790.24036, 1020.96344,
        887.75024, 1118.3428 ,  602.80756, 1195.208  ,  786.3926 ,
        554.8449 ,  894.1206 ,  907.6923 ,  659.589  ,  883.43445,
        808.6752 ,  738.56683, 1002.5877 ,  907.376  ,  817.523  ,
        820.7947 , 1078.0437 ,  718.6383 ,  589.1289 , 1058.8018 ,
       1031.9985 ,  775.413  ,  867.3005 , 1083.4542 ,  886.41797,
        859.06256, 1184.3181 , 1134.058  ,  695.4951 ,  924.43933,
        749.54834,  990.0405 ,  983.5169 ,  686.65796, 1217.0554 ,
       1190.5892 ,  930.9009 ,  893.369  ,  718.7207 ,  792.41833,
        528.152  ,  798.1784 ,  931.7515 , 1065.5066 , 1224.7103 ,
        664.31433,  872.8551 ,  993.2236 ,  554.53296, 1125.5431 ,
        938.55597, 1211.1798 ,  874.6232 ,  561.23956,  617.21765,
        772.92035, 1171.1814 ,  855.8379 ,  886.93115, 1035.8958 ,
        511.5861 ,  648.32635, 1079.765  ,  548.4623 ,  691.4108 ,
        625.8013 ,  943.7871 ,  929.40137,  843.5466 ,  907.48883,
        585.81683,  791.13385,  943.80414, 1174.2709 ,  886.4823 ,
        651.3803 , 1118.5608 ,  618.7849 , 1188.1068 ,  972.6595 ,
       1044.0734 ,  689.3907 ,  658.0526 , 1152.7595 ,  842.5968 ,
       1037.5266 ,  944.82477,  897.6893 , 1197.9519 , 1071.4222 ,
        863.40106,  897.5146 ,  986.6445 ,  594.67926,  977.44147,
       1125.7396 , 1169.0137 ,  641.5496 , 1105.3958 ], dtype=float32)
</pre>

```python
mean_absolute_error(y_test, lunch_count_predict)
```

<pre>
80.4657417887603
</pre>

```python
plt.plot(lunch_count_predict)
plt.show()
```

<img src="data:image/png;base64,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"/>

```python
#lunch_model.estimators_
```

### 2) 석식계 예측 모델



```python
features
```

<pre>
['월', '일', '현재원', '본사휴가자수', '본사출장자수', '본사시간외근무명령서승인건수', '현본사소속재택근무자수']
</pre>

```python
features.append('중식계')
features
```

<pre>
['월', '일', '현재원', '본사휴가자수', '본사출장자수', '본사시간외근무명령서승인건수', '현본사소속재택근무자수', '중식계']
</pre>

```python
test['중식계'] = lunch_count_predict
test.head()
```

<pre>
           일자 요일  본사정원수  본사휴가자수  본사출장자수  본사시간외근무명령서승인건수  현본사소속재택근무자수  \
0  2021-01-27  3   2983      88     182               5        358.0   
1  2021-01-28  4   2983     104     212             409        348.0   
2  2021-01-29  5   2983     270     249               0        294.0   
3  2021-02-01  1   2924     108     154             538        322.0   
4  2021-02-02  2   2924      62     186             455        314.0   

                                                조식메뉴  \
0  모닝롤/연유버터베이글 우유/주스 계란후라이/찐계란 단호박죽/흑미밥 우거지국 고기완자...   
1  모닝롤/대만샌드위치 우유/주스 계란후라이/찐계란 누룽지탕/흑미밥 황태국 시래기지짐 ...   
2  모닝롤/핫케익 우유/주스 계란후라이/찐계란 오곡죽/흑미밥 매생이굴국 고구마순볶음 양...   
3  모닝롤/촉촉한치즈케익 우유/주스 계란후라이/찐계란 누룽지탕/흑미밥 두부김칫국 새우완...   
4  모닝롤/토마토샌드 우유/주스 계란후라이/찐계란 채소죽/흑미밥 호박맑은국 오이생채 양...   

                                                중식메뉴  \
0  쌀밥/흑미밥/찰현미밥 대구지리 매운돈갈비찜 오꼬노미계란말이 상추무침 포기김치 양상추...   
1  쌀밥/보리밥/찰현미밥 우렁된장찌개 오리주물럭 청양부추전 수제삼색무쌈 겉절이김치 양상...   
2  쌀밥/흑미밥/찰현미밥 팽이장국 수제돈까스*소스 가자미조림 동초나물무침 포기김치 양상...   
3  쌀밥/흑미밥/찰현미밥 배추들깨국 오리대패불고기 시금치프리타타 부추고추장무침 포기김치...   
4  쌀밥/팥밥/찰현미밥 부대찌개 닭살데리야끼조림 버섯탕수 세발나물무침 알타리김치/사과푸...   

                                            석식메뉴  월   일     현재원          중식계  
0  흑미밥 얼큰순두부찌개 쇠고기우엉볶음 버섯햄볶음 (New)아삭이고추무절임 포기김치   1  27  2355.0   982.415771  
1            충무김밥 우동국물 오징어무침 꽃맛살샐러드 얼갈이쌈장무침 석박지   1  28  2319.0   993.918274  
2            흑미밥 물만둣국 카레찜닭 숯불양념꼬지어묵 꼬시래기무침 포기김치   1  29  2170.0   582.148621  
3           흑미밥 동태탕 돈육꽈리고추장조림 당면채소무침 모자반무침 포기김치   2   1  2340.0  1194.138306  
4       흑미밥 바지락살국 쇠고기청경채볶음 두부구이*볶은김치 머위된장무침 백김치   2   2  2362.0  1070.221436  
</pre>

```python
X = train[features]
y = train['석식계']

dinner_model = XGBRegressor()


dinner_model.fit(X,y)

dinner_count_predict = dinner_model.predict(test[features])

plt.plot(dinner_count_predict)
plt.show()
```

<pre>
[12:28:28] WARNING: /workspace/src/objective/regression_obj.cu:152: reg:linear is now deprecated in favor of reg:squarederror.
</pre>
<img src="data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAXcAAAD4CAYAAAAXUaZHAAAABHNCSVQICAgIfAhkiAAAAAlwSFlzAAALEgAACxIB0t1+/AAAADh0RVh0U29mdHdhcmUAbWF0cGxvdGxpYiB2ZXJzaW9uMy4yLjIsIGh0dHA6Ly9tYXRwbG90bGliLm9yZy+WH4yJAAAgAElEQVR4nO29eZwkV3Xn+zu575W1q7urtbZWtNMICcQiJGGEATEejMGeMeOPPcyzeR57vGJ/3jy/ebYHezzveXsePGDskTdsjMESGGwJIdkII0GLVqtFt4S6JbW6q7ururv23CPyvj8ibmRkZuwZN7Iq834/H326KitVEZGVeeLc3z3nd4gxBolEIpGMFrFhn4BEIpFIwkcGd4lEIhlBZHCXSCSSEUQGd4lEIhlBZHCXSCSSESQx7BMAgJmZGXbppZcO+zQkEolkR/H000+fZ4zNWv1sWwT3Sy+9FAcOHBj2aUgkEsmOgohO2P1MyjISiUQygsjgLpFIJCOIDO4SiUQygsjgLpFIJCOIDO4SiUQygrgGdyK6moieMf23QUQ/TURTRPQIEb2o/zupP5+I6PeI6BgRPUtEt4q/DIlEIpGYcQ3ujLEXGGM3M8ZuBvBaAFUAnwfwUQCPMsauBPCo/j0A3AfgSv2/DwP4uIgTl0gkEok9fmWZuwEcZ4ydAHA/gAf0xx8A8F796/sB/CnTeBJAmYh2hXK2EsmQYIzhM986iVpTHfapSCSe8BvcPwDg0/rX84yxM/rXZwHM61/vAXDS9P+c0h/rgog+TEQHiOjAuXPnfJ6GRBIt313awi/87bP4zIGT7k+WSLYBnoM7EaUAvAfA3/T+jGkTP3xN/WCMfYIxtp8xtn921rJ7ViLZNmw1WgCAbxy/MOQzkUi84Sdzvw/AtxljS/r3S1xu0f9d1h9fBLDX9P8t6I9JJDuWqi7HPPnyBbTbcnqZZPvjJ7h/EB1JBgAeAvAh/esPAXjQ9PgP61UztwNYN8k3EsmOhGvta9UWjp7dGPLZSCTueAruRJQHcC+Az5ke/g0A9xLRiwDu0b8HgC8BeAnAMQCfBPAToZ2tRDIkaq3ORqqUZiQ7AU+ukIyxCoDpnscuQKue6X0uA/CRUM5OItkmcFmmmEngX45fwI+96fIhn5FE4ozsUJVIPMCD+1uumsU3X16BoraHfEYSiTMyuEskHqg1FQDA3dfOYauh4PDi+pDPSCJxRgZ3icQD1aaKeIzwpiu1st1vvCR1d8n2Rgb3EaLeUvHv/uSbeF5Wc4ROraUil4xjppDG1fPFSDdVGWP4sydPYK3ajOyYkp2PDO4jxOJaDY+/cA5/c+DUsE9l5Kg1VWRTcQDAHVdM41uvrKCpRKO7n16v4z//3XP49Ddld6zEOzK4jxB1vVzv68fOD/lMRo9qU0XOFNzrrTaeObkWybErDU3vP7wYzfEko4EM7iNEQ88knz+7ieXN+pDPZrSoNlVkklpwv/2yaRAB/3I8mpsor9R59pTcxJV4Rwb3EaJuarT5l2Nywy9Mai3FyNwnckm8ZncpMt29qlfqnFqtYaUidXeJN2RwHyEarY4G/LUXpTQTJrWmilyq0/P3hitmcPDVta4bqshjc2QJpsQrMriPEA1FCwJXzObxxLFz0JqFJWFQNW2oAsAdl0+jqbbx9IlV4cc2Wx8cPiV1d4k3ZHAfIep65n7PtfNY2mjg+LmtIZ/R6FBrdTZUAeB1l00hHqNIdHeuuedScam7Szwjg/sIwSWCu6/V5qZIaSY8zNUyAFBIJ3DjwkQkujuXZV57yaSUZSSekcF9hODVMvvmCrhkOidLIkOkZqqW4bzhimkcOrWOLb1UURQ8c3/9ZVM4s16XlVAST8jgPkLwzD2diOHOfTN48qUVtCIyuGKM4VNPvIwTFyqRHC9KGGOoNpWuzB0A7rh8Bmqb4Vsvrwg9Ptfc9186BQB4TmbvEg/I4D5CcM09k4zjzn0z2GooOBRRo82FShO/+sUj+KE/egrLG6OVWTbVNtoMXdUygCaTpOIx4T4ztaaCbDKO6/dMgEjWu0u8IYP7CNFQVCTjhHiM8IYrZkAUne7OuyhPrdbwI//rW8Kliijhmne2R5bJpuK45eKy8E1VrvcX0glcMVuQmbvEEzK4jxD1VhvpRKfR5sY9E5Hp7lwX/uBte/H82U38+J8/HZn3imjM1Sq93HHFNL5zegPr1Zaw45t9bW7cMyEzd4knZHAfIeqKikyy8ye988oZHDy5hs26uMDD4V2U77h+Fz72fTfgay+ex0c/9+xI1Nrz4J61CO5vuGIGjGmDs0Uen99YbliYwPJmA0sjJn1JwkcG9xGiYcrcAeDOfbNQ2wxPvSR2ww8AKg0tAOZTcbx//178zL1X4XPfXsR/f/gF4ccWjZ0sAwA37y0jGSccfFXc3katpSKr6/03LkwAkLq7xB0Z3EeIuqIibcrcb72kjGwyjicikGY60oUWhH7ybfvwwdv24g8eO44/e/KE8OM/dOg0zq6LyWZ5tUrvhioApBIxTOVTWBXo+VJrqsjqf9frdk0gRrJTVeKODO4jRKOlImPK3NOJOG67bCqi4K7JMlw+ICL86v3X4+5r5vArDz6Hx15YFnbshqLiP376IP7ym68K+f382qxkGQCYyCaxVhMX3KstxbixZFNxXDVfxLNyU1XiggzuI0RDaXdp7gBw574ZHFvewpn1mtBjV3jmnu4EwEQ8ht//wVswU0jjb58WN0CkqktCF7YaQn5/zWFDFQDK2RTWa+L2NXp9bW7YM4HDp9ZHYj9DIg4Z3EeIekvt0twBbVMVAJ4QXBLJB0jne6SLXCqBXRMZbNbFlUZy2USUHa5TtQwAlLJJrAmslqk3tRF/nBsXJnCh0sRpQTKUZDSQwX2EqLf6M/er54uYKaSEl0TyDVWrTcdiJim0YocH9wuignvL/toATZbZEJm5t3oy94UyAKm7S5yRwX2EaCj9/iexGOGN+2bwxLELQpfxVb2LMhajvp8VMwmxmXtTtCzjrLmXc8lIZZlrLioiEaPIKmak/LMzkcF9hNCamPr/pG/cN4PzWw28sLQp7NjVpop82jr4CQ/ugmWZWlNrxrKqlgG0zL3SVIX4+KhthqbSRi7ZOXYmGcfVFxUjcYhsKm285bcex188Jb7iyYr/76sv4rAs+wyEDO4jhFXmDmibqoBY3b3aM6nIjGhZhmvia7UW1Hb4WWa1pSCViCFusSoBtMwdgJDsvbcKiXPjgtapKjqrXqs18epKFb/6xSN45Xy0pnAttY3//vB38XN/c0jI33XUkcF9hLDL3HeXs5gvpfH8WXGZe6XR75rIKaQTqDRVYR9QLsswBqxWw8/eaz1e7r1MZLXgLmJTla9KeiWhG/aUsV5r4eSK2Coo/trWW2384t8+i3aEQZbftF9Y2hRabTWqyOA+QtRb1pk7AMwU0kKHK/cOszBTzGgZvSgzsVqr83tFXGO1p1qlFx7cRWTudt2xRqfqothNVR5g77p6Fk+9vBKpPMOvnQj4fx55oWuWrMQdGdxHBMYYGkobaZsgNF1IC6smATT5IJ+2lmVKGS34iZJmuCYOABe2xGTuGQ+Z+7qARia7Msyr5otIxWPC9Wh+/B9+w6V405Uz+NiXn8fJlarQY3aOrd20f/C2i7G00cAff/3lSI47KsjgPiLwKUxWsgwAzORTwqpJAL2iw+bGwjN3UZuqPAgAYjL33vmpvYjM3O1My1KJGK7ZVRReMVM19S987PtuAAH45c8fjqSChktSb75qFvdeN4+PP34c5wW+h0cNGdxHhIZpUIcV04WUkKyWU3HI3ItG5i4muPMJVACwUgn/w19tKl3VKr2UcykAEGL7W3fwtblhzwSeW1wXqoObVw4Lkzl89L5r8LUXz+MzB04KOybHLEn94juuQa2l4vcffVH4cUcFGdxHhIaifRB6m5g404U0ai21K8sNE6dNx07mLkaWqTZV8EIWEdJTrafOvJeSfn1rIjN3i5v2jQsT2GwoeEXgaMNe64Ufev0leP1lU/i1Lx4VbmlhvrHsmyvgA6/bi7946lW8HHHVzk5FBvdtwIPPLOLBZxYH+h18xF6v/QBnKq9ll6Ky90rDS3AXtaGqIp9KYCKbFLeh6hDcE/EYiumE0FJIq5vLDXv0TlWB9e69bp+xGOE3//WNaLXb+OXPiZVneiWpn77nKqQSMfzWPz4v7JijhAzu24BPPfEyfv3vjw60vK67ZO4zBS24i9As222m69LW0kWBB3dR1TJ6Zj2dTwnJ3Hs7RK0oZZNCZBkn07Ir5wtIJ8RuqlrdXC6dyePnv+caPPbCOTz4zGlhx+ZVUPx9NVtM4z+8+Qp86fBZPH1iVdhxRwVPwZ2IykT0WSJ6noiOEtEdRDRFRI8Q0Yv6v5P6c4mIfo+IjhHRs0R0q9hL2Pls1hUsbzbw3OngH1JDc7fJ3KfzaQCCqkl0XdiuQ1V4tYzuvTKVT2FF0PXZbRZzRFkQOJmWJeMxLExmsbgmTh6xO/6/e8OlWJjM4svPnYn02D/2psswW0zjY186Km0RXPCauf8ugH9gjF0D4CYARwF8FMCjjLErATyqfw8A9wG4Uv/vwwA+HuoZjyA86H3lyFLg38Ez97St5q5l7iJki4qR3Vln7ulEDMk4iZNl9EqdqXxKTLWMiywDcE/36JqYOHPFDJY3xVZBJeOEZLz7fRWPEXZPZIW6YdYsKoXy6QR+5t6rcODEKh4e4PMyDrgGdyKaAPBmAJ8CAMZYkzG2BuB+AA/oT3sAwHv1r+8H8KdM40kAZSLaFfqZjxAbetD7ytHgAy14VYVttYyeuZ8XUU1iGrFnBREJtSDgmft0IXxZhktOdjcuzkRWTOZe0zeLU3Hrj+pcKS10nmqtqdjKbSVB18wxMvee9/T3v3YBF0/l8GlBw1lGBS+Z+2UAzgH4EyI6SER/RER5APOMMb4mOwtgXv96DwBzndQp/bEuiOjDRHSAiA6cO3cu+BXscBqKiqbSxnQ+hSNnNgIvsd1kmWwqjnwqLkSWqTS7tVErRJqH8cx6Kp/CarUZamkgXxG5Ze4iZZlcKgEia1+buWIay5sNYRKF02aycKvjpopUPIZEz40tEY9h31wB5wSuWACtMXAnSz9egnsCwK0APs4YuwVABR0JBgDAtFfA16vAGPsEY2w/Y2z/7Oysn/91pOAB79037QYAfPVosKWmmywD6F2qAjZU+fLZTnMHxAb3qiHLpKG2GTZCXCG4Derg8A3VsINBraU4bubOFTNoKm1s1AS+tg7BXWTmXmvaX7soCc7M/X/wdbzu1x/Fv//TA/gfjx/DN45fQEVQUYAInNeaGqcAnGKMPaV//1lowX2JiHYxxs7osgvXFBYB7DX9/wv6YxILeMC7ae8ELpvJ45Gjy/i3d1zq+/fUXTJ3AEJkC8A0Ys8hCBXSCWwJbGLKphKY5uWelabRWDQo/MZlJ3dxytkUmmob9VbbtbLG7/GdXte5kia3LW/WMaG7U4ZJtWlvCFfOdayOezX5cI5tf+28MooxZruqGZSjZzawdzKH48tbeETX92OkWT/8yBsvxQ+87mIhxw0L178IY+wsgJNEdLX+0N0AjgB4CMCH9Mc+BOBB/euHAPywXjVzO4B1k3wj6YHr0MV0EvdcO4cnj18IZLDl1sQEaB+I8wJkmWrDiyyTDDWj7jp+U0U2GRNSy19recvcDWfIkP1lnGwdAC1zByBsU1UzTbP3sQfE2C4A/ROozEzlU2gqbWNlFTZNpY2WyvB9t+7BV3/urTj4n+/Fn/zI6/C/v+1KbNRa+JOvvyLkuGHi9Xb7kwD+goieBXAzgP8K4DcA3EtELwK4R/8eAL4E4CUAxwB8EsBPhHrGIwbP3IuZBO6+dh5NtY2vfdf/HoRbExOgbaqKkGX4B6x3fqoZoZq7XmPPg3uYFgReZRlRnu41hwAHdGfuIqi11K6h52ZEB/eaw42t87cWNaClu3lrMp/CXVfP4WfuvQpvvmpWSJIUNl5kGTDGngGw3+JHd1s8lwH4yIDnNTYYmXsmiavmC5jIJvGVo8u47wZ/BUYNT5q7plOGvZR16qLklERWyzQ1q2Ne7ulFevrLp17F14+dxx/8kHMbhnFtDt4ygDhPd7fu2LmiHtw3xGXuC5PDC+521z5lkuD2TuVCP3bFZkgKwO2zG1DbzHaAy3ZAdqgOmQ1T5p6Ix3DX1bN47IVl34MtOpm784aq0mahb75VPG6objWU0DccFbWNpto26twBeGpkevyFZTz2gnvpqVOHqBlRgU6TZexvLIV0AtlkXJws01Bsj1+KRJaxPraIVVrXsfnf3cIMb7aYRlvQYJgwkcF9yHCpgndx3nPdPFYqTRx81V97daOlIp2IOWbkhgVByB+IalMFkfNmbjGTQJt1bgRhYdbE04k4CumEp8x9ca2Gqoe5p15lGVHBve4iyxAR5kppccHdwe6YS1GiyiFrTcV2SIrIjmvANN7Q4vgzBe3YoksxB0UG9yHDpQruv/Lmq2aRiBEe8VkS2VDarhUdoj4QWnYXR8xhiVpIa4Eg7IoZHtz5MA2vJXKn9X4Ct32AmktzGIdXqnjxl/nKkSX8p79+xvV5ALcbdj72XDGNZUGNTG517oCY8YJux54S2HHNjw3Acr9BpE9TmMjgPmQ26wryqbih3ZUySdx++TQe9dmtWtczdycMTTrkN2XFYTg2R5TtryGbJL0H92pTwaoekNwyba+yTDGdQDxGnjL3R59fwucPLnqS3ryYls0VM0KySLXN0FTatn/bSDZUba49n4ojlYgJDO72FWCz+j6HDO4SRzbrLWOYBefua+dwbHnL17R5p/mpHF4Hfj7kD4Q2Ys8l+OnBfUNQ5s6DgBdnyNOmLmA3SaHX8tYOIkIpk/BUCrmkb356KXn14mszWxQjy1QdNhUBzbgsl4qL09wdrp2IhLmA8mMD1pYaMzy4b0rNXeLAZl0xAh/nnms1J4ev+JBmNFnG+c85mReTuVc9Ze5inCF7zaW0zN35+k6tmoK7y/nUmoq2n+Dy2gLaRKZ1D5vVvGzR7bVoqW0obeYa3OdKaWw1lNC7J+1G/JkR1aXqxdNHZJcq90uyuvZiOoFUIoZzMnOXOGEV3PdO5XD1fNGXNKPJMs5BIBmPoZxLhq+5O3QxckqCBnaYR7EBWkUQL/e04/RaR592C0y8ichL6Wgpm8SahwoKXrbo9lrw4Oq2IhPVyORlM1lUcPfi6SM0uJtmx/ZCRJgtpHFebqhKnLCSZQDgnuvm8M1XVjwPgKi33DN3gMsWIWvuDlOYOKLmqPZ2kE7nU2ipzHEwyOJa1fjarSy06sHLnePFSEttM0OrdXtubyONHfO8kSnkTVU3WQbQg7uADVUvNxaRwb3ismqZKaZl5i5xxipzB4C7r52H2mZ4/LvesveG4q65A9w8LNwPRK2pOnanAp1qoK1G+HXgQCdz91LrvrhaM57nJsvUPWxocsoestgLWw3wfVT3zN09uALiMncvNxeRVseA9exYjsjgXmuqiMfItkhhtiDGyiNMZHAfMht1xTJzv3mhjJlCyrPHe73Vdq2WAbQyrrA3oSoeZJl8Ko4YicvcDc3dQ5fq6bU69s0VkPBQ3eLWIWrGy8AOcwDedLnRuQ3q4BhdqiMky3jR+6fzKWw1FKM7O0wqegmqnRw3U0jLOneJM5v1lqFHm4nFCG+5ag5fP3be0++pKyrSXjJ3Af4y1aa9/wiHiFBIh+8v06e5e/AcWVyrYaGc9SSjOHVJ9lLOab/PyU/ePFjDtcbeQ/bKj5uKx0L3l/FiKyEuuLuvWqb0vg1h07cc3tNmC4LtigzuQ6SptNFQ2payDADsLmewVnXeHOQ0Wm3HDlHOdCGF1WoLiktnph+qTcVVlgHEOEN2NHft+G5t6YraxtmNOvZMZlHKJl1LM526JHuZyCbRZs6DwLsyd48bqm4rByLCbDGNcyH7y3gpAy3nkqi11NCzZ2PV4mC9IMIFlOPWu7ETLAhkcB8iZtMwK3IprWW/obgH4oaiOpqGcYzMNqQ3pdpmnj3MRThD8gDEJSmjC9cmm1va1LKt3eUsSplE6LIM4LxRyitlEjFyvdF5kSY4ImrdvcoygAA3TA/HFjkXuOYiNXILgu3cyCSD+xAx2/1awRuDvNQv1z1n7uFaEDiVjPUiwhlSa96KGdYH2VQc2WTcdkN1Ua9x31PWM3e3ipWWalgbuOGlHX95s46pfEqXcNysD9x98jnauL1wZRkvAbbk4YYWBK/VMoCY4O52U+cWBNtZd5fBfYh0grt95g503uhOaNUy3jP38IK7vQdHL2Iy9/4BzlMOnYu8O3U3D+6uTUyqL1kGcM5ilzYamCumPQ0MrzW1FZuXlYMI8zAvsozozN1tQxUQE9wrTee9lp1gQSCD+xDpyDI2mbv+xube0naobYaWylybmABT5h5SrbtXXRjQyiGDTJlyotZs9204Oo0T5API95SzKGU8bKj6kGX4aD+nQHdus465UsbTjY6viryUuM4VM1irtkLVvqtNBalEzNGz3Ms1Bz024HxjKWWSiMdImCxjZT3A2QkWBDK4D5ENF1mGe0lXGs4fWC8j9jgzhXAz94qHEXscEZm71QBpJwuCxbUapvMpZFNxvVrG2WO+5pLBmfEyam95k2fuCQ+Zu/cbJ29kClMm8HJjEzakxMN4w1iMMJlLipkL3HDub+AWBDJzl1jCP9wlG1mGZw5Vl8zdGI7tIcMrZZJIxCj0zN1rtcxmvRXqwA6rUWxT+ZSj5r67nAUAlLIJNNW27YY1HwTiPXN3lijabYZzPLink+6Ze0tFMk6ehk/zRqalECtmqh6a00TKMkTOw2cAb15CgY7fcr52bkEgNXeJJW4bqjxzcMvc663uihEnYjHSNOmQN1S9au4tlXmq/vF+/P4Mi7sFWt1EFtdq2MODe8Y5MHnJHs1kkpoNrV07/kq1CaXNMFdMo5R1X8U4zRDthWvA50LcVLVaFfXCezRETKByaiLiiOpSrTTcG/O2uwWBDO5DhH+4CxajvIBONsyrJuzgwdJL5g5oH4iwWqf9aO584zjMWve6hffLVD6NhtLu24hmjOH0WidzdytdrHs07jLjZEHAyyDnSxmPG6rerQ86g7KjlWUS8RgKafeS0iDH9iKHTefTocsyaltLQNykxu1uQSCDu86vffEI/uCxY5Eec7PeQi4VR8Jm2Z1L+8vcvWjugFajG5YswzV3b6WQ4TtD1izGwNlVUaxVW6g2VeyZ5LKM883Gz42LM5FN2urPS3pWPVfSNPdKU3VsJtNG3HnT+6fzacQo3EHZ1Ya3lYOILlW3OnOOiMzdq6fPTCEtNfedwFeOLuF3H30x9FphJ+xMwzh5oxTSTXPnsoy3IDRdCE+W6XVldIKvUMIctVe10dyB/kamTqWMpk+7SQpBg7vd7+MdpHPFjLGKcaoeqjUVz7JMPEaYKYRb615teQuwIpwhrf6uVkzlU1gLueO65rG8d6agWXlsVwsCGdx1thoqmkobn3ri5ciOudmwtvvl8De3e7WM9sb20qEKhOsvw8/NW7VM+La/VgOkO/M1u6+xE9xzAMyyjPX5cDnMa7UMoG2q2soyeuCd1atlAOfXwk8ZJhB+rbvmGeR+7UIyd5fB4Bzepepm2OaHiseb+na3IJDBXYdnx3/+jRNC/KmtcMvcYzFCLhUXkrlXmqrx/w1C1cekIhFzVK0yPLtGLaM7VaAsU3IIdEsbDUxkk8gk48aqwWn/wWuA48wVM6HKMl4buEQEd683tslc+I1MXmrsge1vQSCDO7QStWpTxfe8Zh6Vpoo//cYrkRzXzu7XTC6VMDIJOzqlkF41d3dbXK9UGlrJmJdJRV6yVT8wxiwDIG/U6v3An16rIZOMYVIvWTSqZWxu5l5dGc2UsynHzJ3b85Y8rGL8VMsA3IIgvEDjpWIEEKW5ewvuYXdcA95v6vxztF0bmWRwR6fkbf8lU7j7mjn88ddfds2Ww0CbwuScHeTTcVRdujo7TUweM3durhVCxuGlXI4TdrVMQ2mDsf4W9XxKK0nsDe68DJLfiFKJGLLJuO35ePVTNzORTWKroaBloQEvbzYwX9L0fi8SlX9ZJoMLlUZo+rPbDFOOkxQl+thTAszDvA5FN8pPt6Lbp/ODDO4wdVmm4/iJu/ZhtdrCp795UvhxN+uKpZe7GS+Ze0PP3L3UuQMdnTKMbEfL3L0FIL6hGlbmbpdZE5FR627GXAbJKWUTtpp7EFmGNzJZlVcu674yQGcV42R/4LUckDNXTIMx5xXZ2fU6Hnxm0fV3tdQ2Wqr7cG5Ak6IaSjsUmY9T9Wi17GbxHOjYDY/VMtvcgkAGd3SCeyGdwGsvmcTrL5vCJ//5JSETXszYzU81k/eiuQfM3MPQCq2Mu+yIxwj5VDw0fxmnJiOrErnFtRoWJnuCe8Y+6zSCu4OneC8dC4Lu38mY1p06W+oO7k77D3WLMk8njIlMDrr7//zn4/ipv3rGuDHa4efGJqJL1ao5zQquuYdZ6+6163q7WxDI4I7+io+P3LUPZzfq+LuD7hlOUFpqG/VWG0WXaoRcOuFeLePDfgAwZe4hfCCqTdWwJvaCl+Ydr/AAZXXdvc6Q9ZaK81tNozuVM+HgDFnzMImoF7tAt1Ztoam2DZsAN1mGMYaqj1JIQJNlADiWQx54ZdXy/HrxOpwbEBPcvWruyXgME9mkkA1Vt7+7YUEgg/v2hbsu8iD1pitncMOeCXz88ePCaljdrAc4njJ3H/YDgJaNZZKxUDR3N2vUXsI0D3MKQNM9niNmq18zTra/1aaKRIyQ8vi6AsCEjb8M3+jkBl+pRAzpRMx2alNDaaNtsZ/gBM/c7fxlKg0FR85sAHDf96h4bOQB3D11/NJU2lDa3iQhAJYS3CAYmbuHpGWmuH39ZWRwR3+XJRHhI3ddgVcuVPGlw2eEHNNtChMnl3LP3OuKNqndi8EUwDXpcNq2qw1na9ReQg3uLftqlql8uss8zGz1a8ZpGlPNwtrADSOL7anA4bNTeeYOOK9i/DhCcnhpnl3mfujkmpGseM3cvdxc7K45KJ1je0sanIzigsD3uLwMv9nOFgQyuIBkY/wAACAASURBVKPzx8ybJJK3X3cRrpjN4388fjxUF0OO58w97Z65a/NT/f0pZ0LqUq26zJrsJUxZxmn53FvLb5e5c9tfK/x4u3DKNhIFz9x5dg3om7k2Nzq/pmWAthqYyqdsyyEPnFg1vvbiY+/1+Hb7DEGptryvGgBgMp8KtZGIWx/EHHzsOdvZgkAGd5gyd9MyLBYj/Phb9+HomQ08/sK50I+54Sdzd6tzV1SkfWaY0yH5y1Q9eoBwwszc646Ze/e+wuJqDTECLprIdD2vlNVuNm0L+c1vKSL/fUC/v/myyVeGU3QYFuI3e+XMFdO2G6oHTqwaFUvus2O9+/SHrblXbaqg7Ahblqn4+LtvZwsCGdxhDu7db+T7b96NPeUsPv5Px0M/ph/Nvam0LeumOUEy9+mQbH8rTdWT3S+nmLHPVv3i5GtjlMjp17i4VsdFpUyfdFXKJNFmwJbF6shvKSKgbfBZuSQubzRQTCe6gmXJ4UYXpIEK0GqvrWx/1TbDwROreNOVMwDcM3c/slDRxTrZL34kIUD7W6/aWDwHPb7X1eh2tiCQwR2mapmeD1IyHsO/vnUPDryy4mlItR/4h9puUAeHv8Gd5qjWlbYvW1pAa/64sDXYB0JR22gqbU+OkJxiJomthnMQaLeZp3LJqkMQMDoX9dXJ4lq1T5IBnG1/ax6Ns6x+Z+80puXNulEGyXGaxuTVmbCX+VLGUpb57tImNhsK7rpmDgCw7jKc26u/CqCVuJYyidCGZPvtL5jKp6C0mevAca947cwFtrcFgafgTkSvENFhInqGiA7oj00R0SNE9KL+76T+OBHR7xHRMSJ6lohuFXkBYVBtKsgkY5bWu7dcMok2Aw6dWgv1mG7zUzl8NeGku9dbqq+KDgCYyafRVNu21RpfOHQa3/+H/+IY/IPowsV0AvWW80rk8wcXccfHHnWtxXbK8KZ6bH8X12qGp4yZUpY3E/W/Dl7L8fp/Z7/cYm5g4jhNY6oG6I4FNFnm3GajT2bievvtl00jn7LvyuX4LQOdCLFL1e+NrVPaG06AtbKRtmM7WxD4iQh3McZuZozt17//KIBHGWNXAnhU/x4A7gNwpf7fhwF8PKyTFcVWQ7HNPm/ZWwYAHHw17OCuN065dqi6Z+6NAJm7W5fqnz95At96ZdUxG6r6cITkePGXOby4js264vphdZIueKPWSqUJtc1wdr1umbk7TWOqNlXfrytgPbDDbD3Acdp/qAeolgG04K60WZ9M8PQrK5gtprF3Kutobsbx2oLP0Xzswwlwnb0Ur9Uy1l5CQdEyd++yDLA9LQgGkWXuB/CA/vUDAN5revxPmcaTAMpEtGuA4whHa8Sx/mOWcylcPpvHwVdXLX8elM16C5lkzLV80fB0dyiHrLdUz6ZhHG6uZVXrvlJp4luvrACAY4NGb3+AFzrNO/bB5ZTu3ug2dLnW0urQrV7DUjahz4pt4txmAy2V9ZVBas+z97vxk8GZ6R3YwRjD0ka9L3MvZZOotVTLVYzfTUVOp5Gp++924MQq9l8yCSLSK4S8BXevxw/TPMyvLDNt498/yPE9Z+7b2ILAa0RgAB4moqeJ6MP6Y/OMMV4EfhbAvP71HgBmY5ZT+mPbli0Xje3Wiydx8NW1UEsiNz04QgKmaUwOskyjpXq2++U4fSC++vwy+KreqdEpyKafl8z91GoVgLdyPTvZgIgwqdc/GzXuFrKMk+YepFoG6DfS2qgraCjtrhp3oPNaWA0vGUSWAbqD+9JGHadWa3jtJZMAnC0XODU9YYh7KAcEhhvcJ20mbw1yfK/H3s4WBF6D+52MsVuhSS4fIaI3m3/ItKjnK/IR0YeJ6AARHTh3LvxSQz9Um4rtHFMAuOXiMi5Umnh1pRraMd283DlepjFpsozfOneeufd/IB45chYJ/UPt1KBhV2XkhJszJGMMJ/XX2S1YWM1PNcNL5OwamICOLGNVwaNZ7vqrlgH4hmrn3M9ZlEECzq9FzUcpohl+A1ne6MgE3HJg/6VTAHhXrsuGqg9pAgAmsinXTVqv+K2WsRurGBSvQ0qA7W1B4CkiMMYW9X+XAXwewG0Alrjcov+7rD99EcBe0/++oD/W+zs/wRjbzxjbPzs7G/wKQmCr4fzHvPViLeP5dojSzIYH0zCgI3k4dalqsozPahnDB7v7TVlvqfjn757H21+jLcScMpIgrolO2SqgSTG8UsOLLux07CndgoAP6bDS3As2o/a4V3wgWSaXRNPkkrhkGq9nxmkVE1yW6c/cD5xYQSYZw2t2lwBwJ0wPw7l9HJtLPWGsbv3q/ZlkHLlUPMTg7s2RkrNdLQhcgzsR5YmoyL8G8HYAzwF4CMCH9Kd9CMCD+tcPAfhhvWrmdgDrJvlmW1JtKCg46MZXzReRT8VD3VT1YvcLdN7gztUybc++MpxUIoZSJtEnyzzx4nnUWirev38vYuQsy3Q8OMLbUD252lkdeZMO3IJ7E6fXapjIJi1XZ/EYabX3Pcdqqm2obeZbFgFMHZu67m7VwASYbH9t9P5Uwrsswskk4yhmEl2Z+9MnVnHTQtnYm/CqufudHdtU20bvwSBUW4rvaw9rUHa7rd/Ufbynt6sFgZeIMA/gCSI6BOCbAP6eMfYPAH4DwL1E9CKAe/TvAeBLAF4CcAzAJwH8ROhnHTJuS9B4jHDT3rKnzF1tM3z26VOu3tZeBnUAHVnGKXNvKMGqOqYtWqcfObKEYjqBN1wxg6l8CuecZBleLudLc3feUD25UjO+9uJ/4hSAZgppQ5axkmQ4pUy/eVjQJiKgv2OTd4z2Vss4TWMKWoYJdE9kqjYVfOf0BvZfOtl13M2G4thVWfUZ4MLsUg1y7WF1qdYVFYz59/TZjpq761+PMfYSgJssHr8A4G6LxxmAj4RydhGx1XDW3AFNd//Df3rJ1W/k0aNL+Lm/OYR0IoZ337Tb9nmbdQXFtLss02liCjdzB/q7VNU2w1eOLuGt18whlYgZrdV2VANp7s6ZO99MzSRjrl4lbjNGp/IpbNYVvHKhgstnCrbPs6pLDyI5ccpZfWizXhq4tNFALhXve485Bfeqx/mlVpgbmZ7RzcL2XzJl/JwH4s16C2XdD72Xmk9pwuwMuWvC/kbqBau5uG5oiUgYdhrcy91fcOclt35XWiIZ+w5VzTfbPVO49eJJqG2GZ12amb783FkAHRdCO7xuqKYSMaTiMVt/GcYY6oEz91RXLfnBV1dxodLEvddpertbRuKni5GTjMeQSdpb3Z5crWIim8Tuiawnzd1pw5PvK7x8vtI3pMPMhMU0JqfuVzf6MvfN/jJIwHlgRxDTMo6WuWuyzNP6ZirfNwI65Z9Or2+l4V+WAcJxhgxy7b0uoEHhJcd+bCdmi2moFr0FnHab4cmXLgx8bn4Z++De0L2j3bLPm3kz00n74N5QVHzlyBKAjguhFS1dm/SyoQpo5ZB2c1RbKgNj3gd1mJkupLsy90eOLCEZJ7z16ln9585aYq2pIkbefeQ5Ts6Qp1ZrRqONmy5cd8nceRUFY9aVMhwnWcZvtQrQ72++vNno20wFOpu5lt2xLX9um2bmShksbzTAGMOBE6u4ar5g+MwD5vJPh+HcLq9tL2E6Q/o1owN4ojK4vwx3pPSbuQP2xQefP7iID3ziSRzVvfSjYuyDu9dl2HQhjUunc/j2CXvd/YkXz2OzoSAeI6NCw4otj6ZhnLyDMyQfsRdElpnRrVLVNgNjDA8fWcLtl08bcoGbLFNpap29fOC0V4pp+87MkytVLJRznuqmay7SBc/cAetKGY5Vx6aTKZkbvZnxuc1G32YqoK1issm45Y3O7xQmM3PFNBpKG+u1Fr796ipea5JkABgb+U6vr98AG6bmrklS/m5sk7kUGkrbsZPbC5WG/xWbmwXBP3xHW81HXVEz9sHdT632LRdP4uBJ+2amvz98BhPZJN64b8ZRlvHqCMnJOUxjMqYwBczc20zTho+f28LL5yt4+2suMv1c80S383ipNvw5QnLs2u4ZY0bm3tsIZHn8puKcuRc6wd2qgYljVT3CX+8gK6JiOoEYdQKd1p3an7kD9q/FILIMb4n/+rEL2Kwr2H/JZNfPeRbv5C/j16e/5NAM5he/qwYgvFr3WoAKMP56W2XutaaKr72o9fGE5XfvFRncm96D+60Xl3Fus2G0x5tpKm08cmQJ9143j0umco6yjFcvd47THFVjfmqQDVXTLNWHdTnp3mvnjZ+7LTerAaUDO1nm3FYDDaWNvVPeMvd6y9lTh3uOAMDusnVwBTRZptJUoZhsAIJMQuLEYoSSbkGw1VBQbaqWmTugB3cLl8yg3bFAp57+S89pFcjmShnA2U8H8L4PZab3hjYIQapleo3ighKkAoxbEFhl5k8cO4+6/hkNc8asF2Rw95m5A9a6+9ePncdmXcH33rALu8tZbNQVW125Y/frVZaxz9wbSvDMnX8gzm818PB3lnDjwkTXMAtjuWkX3H1Yo5qxy1Z5GeTCZNYI7lZDNADdblhtOx6/nE0iRtqm9EzeOrgCJmdI0zkNUi0DdNrxeb35vE1w14aF2HnJBwzu+rG+enQZM4U0Lp7K9Z0bYJ9l8xp/P9fOb2hhyTK+N1QLw8vcnSwIHv7O2c6AlIg932Vwb3gvfbrmoiIyyZil7v6lw2dQzCTwxn0zRpZ4Zt3aKc7r/FROLhW3zdzrA2TuPDM/emYTz5xcw9uvm7f8ud2mKtfc/WIX3HkZ5N5JLXNnDLZVNU7zUzmxGGEyl8KectZxZJpVsAvq7cIp6xYEdt2pnGLG2grAzVrBCV6ZU2uphlmYmVwqjniM7GfHBpwC1WuYFpQgncFhmYf5GQzOsbMgUNsMjz6/jLuvnUM2GQ/ltfGDDO4+MvdEPIYbF8p9mXtLbeNhXZJJJWJGZYad7u5fc0+4Zu6BqmX0D8RnvqX5vN173UVdP59xcI4E9OVzIM3dWpbhctfCZM5Vw/XqPzJTSDtWygBmf5nOseoDVMsAnU1aozvVohQSsB/YMYgsU0gnjBtDryQDoOMMaTsoJNiqJSzzMG0z19/r3pFlBtu0DCrHzRTTfUnQ0ydWsaKXFodprOYVGdyNahlvb6ZbL57EkdPrXR2oXz92Huu1Ft55veZszCsz7HR3r4M6ONqQbOfMPUi1TDmXQoyAF5Y2ccl0DlfNdzf6mGUbK/zMmjRTSGvVP70dkidXqpgppJBNxV2rL7xk7gDwq++9Hh+97xrH51jVfQf1duGUcyls1FqGDjtXss7ctQlG3Tdu3gLvN3PmEJEhA732kv7gzo9rZ/Q1zODebjPUW23fr3shnUAqHhs8cw8wowDQLAh6NfdHjpxFKh7DW66aRTmXlBuqUWM1HNuJWy4uo6UyfOf0uvHYlw9rutqbrtLmU86XMojHyCG488zdqyyTsA3ug2Tu8RgZAfzea+f7lu/cp8ROlqn6dA7kGOZhPZLLydUqFiY1fbjsEty9BqDbLpvC9XsmHJ9jVfcdxN+k+3cmsFZtYmmjjrTu42OF1SqGl7cGzdwBTQZKJ2J4zW7ra3fyl/EzHNvr7/RK0BJUIu29PGgjU7WlIB3g797b8MdLi++4YhrFTFJm7sPAT7UMoAV3APj2CU2aaalt/OORs7jn2jnDUz0eI1xUyuD0mo3m3tDeQF5H4+VTcVSaimUJpqG5B8ww+cSie3v0do5Tl2q1pfpq9uCUbPxlTq3WjE7SiZy3zD0zQAA0zsfYUO0caxBvF0CzIFjXNfe5Utq2F6CYTqChaLNozccGBgvud187hw/edrHte8xp83OYmfsgncGTIZiHBf27cwsCXgDw4vIWTlyoGu6qE9lkKN27fpDBXW868iprzBUzWJjM4uBJbVP1yZcuYK3awjtv6B42taecddDcvdn9cnLpBBjrBHIzRp17AFkG0Mohp/Ip2+X7TCHlUC0TTDqw8pdR2wyn12rYq1d29Dor9mIEwIA3NTNWpYGDeLsA2vm3GfDS+S3bzVSgIwmZb3Q8wAW9YQPAf3jLFfi/3vMax+Paae5+/dQ53Md+kC7RQQzbwjAP02wX/L+ney0IHtYbl+7RS4u99G2EjQzuDS379NNlecvFk0bm/qXDZ5BPxfHmq7o96XeXM7ayzIZHu18Oz46tpjE1lMEy9/9495X4rffdaDkcHOBdqv0fmKailSIGydyLFoZZSxt1tFSGvZPdwd29omPw4J5LxZGIUZekMEgTEdA5/2PLW7ZlkID1jW6Q7livlDJJW/uBqs99KM5ENgm1zWy7qb3A2/+DBNgpveN6EGqtYOW9vPiAV8w8cmQJN+0tG06g2o1PlkJGSqWh+KppBbRmprMbdZxcqeIfv7OEu6+d7wuuu8tZnF2vW9qqejUN4+Qc5qjyzN3vJCbO7ZdP4+5rrSUZgPvLWHfeAfBlC8uxMszi05e4LJNNxpGM25frVUMMgETUl8kO4u0CdGSleqt/vJ4ZqxvdoDX2ns7PYbhGNUA5IP+dwGDNOoNcexiae8VlcI8dZguCs+t1HDq13lVaXM6lUG+1Xa3Aw2Tsg7vTcGw7eDPTH/7TcaxUmnjnDRf1PWd3OQulzSy71vzKMnmHOaqdahkxgWCmkMZqtdXVvQmYM6wA1TIW2epJvQySyzK8XM8uUNRDkC7M9FaPDOLtAnQCHdBpT7fCamBHR5oIfnNxo5RNoKm2jZWfmaC6t2GYNoC2PMiKbDqfwmZDMYoMgh4/iBxntiB45KjW7W0O7m6NYyIY++C+1VB8SwvX7SohlYjhr751ErlUHG+9eq7vOU617oEzd0tZZjDN3Y1pfbnZu1HVKRkL1qEKdDconVqtgqjbJkBERYcdvccaVJYpm1wYe4d0mLFaxdT0G2cYkpMdTll20OzZi5WwGwNl7nr2vFoJfvxKU/FcOWdmxhzcjyzhspk89s11SovDdM30ytgH92rTvyyTSsRww54JqG2Gu66Zs8wedzsGd29TmDhOc1TrrTZSiZhjB+YgzBoWBN3BnQfXIB2qVtUyJ1dqmC9mulYgTpl7TV+xDJJdd51TjywzSBMR0J252zUwAdYDuqOQZYzjWry+fDh3xudqsHPDCC6NBJWEAHOXavBGJu2mHkBq1C0IXjpfwTeOn8e913WXFvfaQEfB2Af3rYC747fqJZHf21Mlw+EZqNWmqpa5+6iWccjc6y1VWNYO2JuHDRKA0okYknHqkWWq2DvV3UnqtAlVG7BKqJdSJtlXLTNQ5p7tOFLamYbx4wLWmntYNy4r3DL3XCruO2EIQ3OvG7YPQTZUrVeZfqj4nEDF4RYEXzx0Gi2V9ZUWu1V/iWDsg3u16Twc2477b96De6+bx10WkgygbZQVM4m+4K6omue0r8zdYY5qQ3F2RhyUadvgrmdYATafiKiveWdxtWZUynAcM3ddEw9rxaINB+kE2HoAfxMzmaQ2QQsA5h02VAtWskwUmTvXgK2sDwJeOx/ZF4osE8gIT7umQYJ7NaClBqBJMxt1BdP5VNfkK6B/9GIUiNux2SFUGkqgAHX9ngl88of3Oz5nTznbF9x5V6a/Onf7OaoN4Zm7vtTdstbcg5RCAt3mYS21jTPrtb5ReE6NH0E8v50oZRMWskzwj4dRgVNrdenvvcRjhHwqblMKKe7j6Zi5N5x98u3IuxiSeWGQJiaeuVuV7nohiNWxGS5h3n3tXF+Hq1tTnghkcG+orsOxg7K7nMViT5eqX9MwwJS5W9QPB52f6pWCjZ3pIKWQQHdwP71WQ5sBCxbWtJsNBe0268vQgwxRdqKUSaKpaKVqqXgMtdbgr2s5l0Q6EXPtoShmujdzeYATedPmfRZWte5BJiEB7hVOXgg6uhHoWDwHrXXvWB0He09zCfPt1/VXzxXTCVBIfvdeGWtZRtUNmkQtf60yd54d+mliyiRjIILlHNVGqx24xt0LXEvs3VA1rFEDBsBCOmGMG+y4QfZk7rmUZvtrZ4kb4t/NXKoWhrcLoNVdm/3x7ei1QA5bcrLCqbJlkFWRk+3vvxw/j/f/z284lirym7bf0Y1Ax+I5aJdqdYAKMADYN1fAVD6FO6+csTy3qP1lxjpz5zKHyMx9vaZN4+HH8GsaBmgBNpeM22buomrcOVYWBIY2GlCfLGaSRuMS/9dKcweAtVqza8AzP36YN2WzBs2D6qC//1fefR28dOL3TmMK+9qsSMZjyKXiltUyWu9HsOM7edb88RMv45svr2BxtYbLZwuWz6m1lMBumMBgjUy8MS5IBRgA/MgbL8MPvG6v7YovLL97r4x15h7U3tMrxtAOU/YeRJYBNPnDulpGbOYOaJuqvcG90lCQiJGxaegXc7Z6crWKeIywqyfLddKFa81w5Sjz0OhB/E3MvGb3hKsjJcCdIbs1d5E17hy7TFLLnoN9Jso2vQmrlSYef0GbJcoHmFgx6I1tagDzML4yDvrax2PkmLTxAS5RMd7B3XCEFCfLAN217n6nMHHyNtOYGorqux7ZLzOFVN8mFS8VDLJ8BjSNm78Wp1Zr2DWR6fO3cQzuIctpneEgSiQbmr3H7pZlwt1PsD1uxto8TBuWMdh4wV6+/NxZKLoVBx9gYsWgwX26kApc52546giKBxO5lNTcw8CLh4Ph5S4sc+dDOzpv5sCZu42ne73VRjqCzP1CpdHlQ1INOGKPU8wksNXQbIxPrlT7JBnAPXMPMwBOmGSZKJqIzPROY4pClgGcM/dBgrtVdvrQoUVjZbbskLkP2hm8ayKLVy5U8X8++BzOrNsPqbeiMxxbTDzQqr+iK4UcyeB+8NVVXP8r/4jnz244Ps8o5xOkuc8V031DO/xOYeJo05is7QfEZ+5ptFTWVVlRGaAeGNCuv82033NqtdbXwAQ4d/UN2mTUi9n2l7/OIquQzBQzia4O1UEDnFdK2f4pUIMen9s4mAebn1mv4amXV/ADr9uLbDKOpQ2nzD34qgEAPnLXPrx//wL+8qlX8Zb/9jj+j787bGu93UtNcOZejnhDdSSD+988fQpKm+GlcxXH5/mdwuSXRDymD+3o1txTiZjvTdBcKmG9odpqIy04CPFad/MA4EGHWRTSWjA9v9nA8mbDmMBkxilzH2SAtBXGwA6T5h5V5m4uwwQ0U7YoJCGrzU+t1jv4qoz72G+ZEpEvHjoDxoD33LQbc6U0lizM9DiD6P2Aprl/7PtuxOM//1a8b/8C/vpbJ/HW33oMv/S5w8YAdjsqgv/ufKXUtnCKFcHIBfeW2saXD58B4D4J3e8UpiDsLme6Mge/Xu6cfDpuWQop2n4AsLYgqAQcscfhK5fnz24CgGXmnknGkUrELBuZwq5zTyfiyCRj2KgrQ5FlgI5kF1nmbqG5N5Q22iz4puKEhTPkg4cWcePCBC6fLWC+mMGyQ+Ye1l7KwmQO//Vf3YDHf/4u/MDr9uJvnz6F+37na1h1iAm1kM3oeinntBvfpsXnWAQjF9yfOHYeq/oby+kPCZi7LEUG92zfhqrfzVTAXnMXbT8AdIK7eVO12gw2Yo/DA9qRM5p0ZpW5A9a6MGNi+hNKejORMXw78uCuXWdUG6oT+kaueebAoDe23tXW8XNbeG5xA++5aTcAzWdn2SFzH3RF2Muecha/9t4b8Ps/eAs2GwpeOm+/mh/E6dQLUdv+jlxw/8Izp1HKJJBPxV1LoqqCq2WA/qEdfu1+OXyOqpl2m6GpRFEKyZ0hOx/KajOYbQOH3+CO6sHdakMVsA7uxvSpsIO7fqyOLBNRtUyPeVhQbxffx9WDzVaXaVlwV0agP7g/9MxpEAHv5sG9mHHU3EWtWvj7y23VAIj7u0dtHjZSwb3eUvHwkSXcd/0uzBTTrsGd+7yI/BD3Du3wa/fLyaUTfZOYeJAT3cQ0mUshRsCFruA+2IxRLk0dOb2BVDxma4trFdwHMZdyYkK3/Y3CldFM7zSmsDeL7bDa0+gMywiuufPfyRjDQ4dO4/bLpg1P+/lSGtWmanz2zDDGhN3Y+KhDp1WD0bshSOYMw1jNDyMV3B97fhlbDQXvvmm3p2aGSkNr8+41+QmTPXojE5dmNusKimn/skw+FUdTbaNpmpzD27hFZ+7xGGEqn8I5kywTZDyhGR7QFtdq2DOZtW21t6owECWblDJa9UitqYBI/OvKMcsyqr4aC+Lt4hfDX8ZiOHdQyc1c4XR4cR0vn6/g/pt3Gz/nQd4qe+feLiJuqpO5FJJxcqnUEbtiMndcR8FIBfeHDp3GTCGNO66YxlTOQ3AfoM3aK3vK2nLwtDm4B8nc9UyqZtLd+Yi9KEr2Znq6VAf9IBRMr0Gvp4wZq8ydb3wN0qZuBZdlBvE3CYJ51F7nxiX+o2mVuRu13iHIMg89cxrJOOG+6zszD/gKzSrIDrpqcCIW0zySnLtjxVYpRT2wY2SC+2a9ha8+v4x33bjLyDS9ZO4iK2WA/qEdQTdUreao1kMeWOHETCFtyDJNpQ2lzQYK7vlUHDxZ3ztlrbcDesCt9gb3cKcwcQxZJiLNm2OWZaqCblxWlCw2+Abdb+CDzVcrTXzh2dN4y1VzXb5Ac3rmbjVbWHSV0lwp494dKzDZk5p7QB45soSG0sa7b9KyBB7craa7cyoBpzD5wTy0Q20zVHwO6uBYTWMyNhYjyNynCynDGTKM+aVEZJipuWXum43eio7BNv3sMKplItK8Ofx12KgrneAaUbUM0J1JDhpgue3vI0eWsLTR6JJkgI72bZW5iw7u86X0UGWZTDKOdCK2/TJ3IooT0UEi+qL+/WVE9BQRHSOivyailP54Wv/+mP7zS8WcejdfOHQae8pZYwLKVD6Fptq2bPzhVBrBpjD5ZY/u674V0HoAsJ6jOqzMnb+mg0paPGO1q5QBrMvHuHQR9k2tlNW6Zs9vNSLRvDnxGKGY1iwIoqyxt5rGFIZpWimbxEvnK8il4rjn2u5xc4V0Qu9S7c/cwzJss2O+lBmqLANo6t3v/AAAGBNJREFU0ozdAJqw8RMVfgrAUdP3vwngtxlj+wCsAvhR/fEfBbCqP/7b+vOEslpp4msvnse7btpl6KST+rBcJ/vPKP6YgFYxc3qtZvJy9y/L8K49K1kmqsy90lRRa6qhad78JueWuQPWFR1hB0B+rDPr9dDLLN3gLpnGjSuC41tNTqqG0NhX1l/Ht18337cCIiLM29S6h7EidGK+lMF6rWXrOxWFp4/TXOCw8RTciWgBwPcC+CP9ewLwNgCf1Z/yAID36l/fr38P/ed3k+CdqS89dwZKmxmNEkBnEvqKg1GP2WddJLvLGZxerwU2DQM6WbK5HLIjy0STuQNaVjvoiD0Ov8k5ae5Wm1DGpmPYmbt+Pkvr9UhkETN8pmyUsgwRGRVCnDBa8PlN8v6b91j+fK5kXeteFdw8xjdz7YzLtMY8wZl7NjpnSK9R4XcA/AIAXoc3DWCNMcbfFacA8L/kHgAnAUD/+br+/C6I6MNEdICIDpw7dy7g6Wt84dBpXD6bx3W7SsZjUzy4O9h/DjKUwA+7y1msVVtY0jdzgnaoAnYbquKvYdYc3EPKsIoZbYnOb8RWOOnCoZdC6sfabAxmXhUEnrlXB9zQ9Euvv0ytqYICjrnjXDSRwUzBeiIRoGXQVs1Eoj19jDJMm03VoLNj/TCRi25gh+tfkIjeBWCZMfZ0mAdmjH2CMbafMbZ/dnY28O85u17HUy+v4D037e4qXesEd/sXcmtAfxSvcF/3F3QflYEyd3MpZISZe6dLtRnah/C63SXcdtmUY8lhpza483esC8rw+LFE/G43NGfIlqlaJprj8wohDm9OG2Sx/fPfcw0+9+NvRNJmkMtcUZNleosdxG+oOlsOV1uDWWp4IcpRe16izBsBvIeI3gkgA6AE4HcBlIkooWfnCwAW9ecvAtgL4BQRJQBMALgQ+pnr/P1hzXHu3Td178q7Ze580nk0sszgwb1TLWOSZSLM3Dv+Mg3DdmDQVc/Pvv1q1+c4Zu6CZBkgOtMwTjGjbULWW9FtqALaNXdLXoONuQO0z96Uw2rM3KVqXsWK9vRxqrEHNMlTdAlqlLa/rikfY+yXGGMLjLFLAXwAwFcZYz8E4DEA79Of9iEAD+pfP6R/D/3nX2VO9YgD8tCh03jN7hKu6JnJWEgnkIyTrTNkQ9EnnUckywAdB8RgsgzX3E2yDLcfiCBz5x/W81sN4xyisqUF+qtlknGyzQyDH6tzPVHJIuZjm2WZqKwPuP86JwqpstOl2p14ReHKmIrHLGWZltpGU21HkrlXm2pXp7koBvl0/CKAnyGiY9A09U/pj38KwLT++M8A+Ohgp2jPqxeqOHRyrS9rB7TNoql8ytYZknu5R5G5zxfTiBFwfHkLQLDMPRmPIZWIdZV2NiKslskk4yhmEji/1eyUQkYQAK1qg8Oen8ox33SjGtRhPra5FDIqWaaUTWDdvKHaEO9IOcs3NnuCrOgbGxFprpQWskxUr3uUXaq+Pp2MsccBPK5//RKA2yyeUwfw/SGcmytfePY0AOBdN+6y/PmkgwWB6OHYZoyhHet1pOKxwIEjn4pbNzFFIMsAHQsCPrwjqgDUWxscti0sx6g3H9KGaktlWKs2ERtwQ9MPpR7NvdYSf+122netqSKViAn1epq3qdTpTGESGw9KhszYNG5yooh27Rky73vtAhYms7Ze4NMFh+De5Jl7NB/iPZNZnF6vB8raOblUoq+JiQhIxqPxQJkppPRqmRyScXHueb30bkLVQp7CZKakd8QOQ3MHgLMbDeRSich8bcxToDLJeCTlgHbmYVHUmc+X0sbel5mKoK7nXrgzZBQVMzvafmC+lLGtpQWAqby97W8lQt0Y6OjugwT33jmqDaWNTCI6gyutS7WJakRVRpzexg/NElfM8XlmFXW1DHdoXNqoRyoJ9XYAR2G9UEgnkEvF+xqZBrWR9sJcMWMpy0Tl4V+2KBAQxY4O7m5M5ZIOmXs0yzBOJ7j730zl9M5R1bKt6P6E03rmHkWGZUbL3Lvr+7OCrpsH2WHIMoBmqBXlsUs9wWbQCVtesZJHtEod8ZLQZkMxkjtOJ9kTv6EKyMx9YKbyaWzUFbTU/p1p0cOxewktc290B7koyiA5M4U0VqstrNdakQegjZ4WeVEZlpG5R7yhanTHbtQjv3ECHX+ZanPwUkgvzBX7Nza1pEG0JGQ9tKMaUQlqlBuqIx7ctRfSqmLGCO4RyQt8aMfAmru5WiaCEXtmpvVa91OrtchWPICV5i5ubuyEIctEux3FV3RRTWHi8JWKOXOP4uZiZb8bxbXPFW30/ogKLPjfeU0G98GYymvByMpfppO57xxZprdahm+CRcWsXiVzcqUaaWZbzqaw1VCg6CuwWlPchifPoIcly0R97I7mrggbPG7FfFEbnGFugRFVBdV1XLvMPaIN1XiM+/nI4D4Qk3rmbuUMGZZtrVfCkGVy6d5qmXZkJXNAp0t1M4IhJ2Ymsh2/c0B0tYx2rKhlGfP7IspjmzX3eqsNxqIpMpgvZVBrqdg0yYxVgTdtzpxRhtmr90fXGVzOpbDmYGgYFiMd3KddMvdEjJAKucvRjlImiXfftBtvsjFT8kJ/nbuKdISBgMsyQMTZZY5vQvFhIeKW7zyTjTpzz6cS4EVPUUpCfKWyUWtFlr0CwFyp36Gx1lQNa2tRlDIJZJKxPlnGcDqNIGnRqr+2WRPTTqPjL9Mf3LU26+jqiQHg9z94y0D/fzaVQLWpot1miMUI9VbbyLyigDcvAdHtVQD9/jL1lrjgfuPCBK6YzRu12FERi2mTqTbrSqR2w6lEDNlk3JgdC0RTBsq17+WNOvbNadYhUYw31Pzk+4d28KHoUayEy7lo/GVGOnPnO9NWwX2roURS8hUm/Hz5ElKrlonuT1hIJ4zGpSg3/czBvaW20VKZMOnitZdM4dGffWukshOHZ9FR19gbs2MjnAJljNszbapGobkDwHyxvwyzojdvRZHsWc0FFsFIB/dkPIaJrHWte7UZrW4cBtyNkXfTNRVxVSNWEJHh6x7VXgXQHdyj1EajhuvukTdQZRN65h6lLNNtQaC2mV79FY0kZNVAFZmdRkTOkCMd3AFNmrFyhtxqqEaw3CnwzJ2XbUWduQMdaSbaDlXtmHx4NRC9sVcUGJU6EV+b5gypRNalCWirwHyqM0s1yps2b6AyV+pUm9Gt5Ms5TXMXaJYLYEyCu1WdezWi4dhh0juNqR5xnTvQ2VQdRrneuim4y8w9PLine5SyDKAHWV2WiXTVUOz4yXNEWlr0MpFNQm2zruOLYOSDu50zZFRTmMKESyE8wDVaamSOkByeuUe5oco3/daqLWHzU7cDRcP6INr3JdfcozLP4mj2u1pwrxmbudGUYQLdfvKRZu76SlS0NDPywX06bx3co5rCFCadzF2XZZR2JIM6zBiZe8SrHt6lGrXfeZQUh9RAxeeoRhlgAd3ES9e+o1w1dMowO5uqUWrupYj8ZUY+uE8VUlitNvv0rcoQbF0HxZijqndrqm02hMw9elkG6AT3+ghn7ryBKur9hFI2ia2GYsgEUWn+86W0oX1HedM2/ORNm6rVhnirYw6v4hPdpTr6wT2XQktlXZ1wgKZb77TMPW/K3DvDsYcjywxDOujWhXfW384LQ8vcMwkw1gl2Ua3K5ksZ1FttbNRNm7kRvJ+t/OSrEQwp4fDgLrqRafSDO29kMlkQKGob9VZ7xwUIY45qUzEy2KhlmVsvnsT1e0pG40lUTOiNH50hyqP31i0OyW6Yb1ifWa8jHmHXNp9EdG6zbtpQjb5SB9Ay96hualHZ/o7eJ6QHHtzN5ZDc3jPKWu0w4HX5lYYa+Yg9zt6pHL74k28y5Jmo4IOc+RDlUSyF5B/6qPsvuAa8tF5HLhnd8Bfzxmbnph19pQ4Qjd0wJ6oN1Z2VugaAB3dzOWTUjpBhkU7EEKPhZu7DgvtxRFmLHTV3XzOPX33v9bjmomKkxzUy941apBvVZnmEJyvDqNRpt6NzwwSATDKGVDzWNV1MBCMfGaz8ZXZqcCci5PU5qkZwjzhzHxYT2SSqTdVwhhzFDdVsKo5/e/slkfodAaZBIevRToGaK3bsd6OusZ8rZobSQAVon+OJXFJuqA6KEdyr5uCuyzI7rFoG0Da7qk2lI8uMUeYOAGf1bGtcrjsKuOtmU412HyqfTqCQTmBpo24kK1HJbeZKHV7fH6UbZzmbFK6576zUNQC5VBzpRGwkMndAq5ipNNXIPwzDZsKkC2cj1IXHgdKQBoUAXB7RVgyxiFwZAU0SaijtLtuFKJO9iQiC+8inP0SEqZ5GJmNQxw7UbXP6HNVGS8vco/aWGRY8uzyzHu2M0XGgkE4gZnjJR/vazhe1cXt8QzOqmzY3LlvarBsr+SjfV1HY/o5FZOgL7hEPxw6TXDKBSlNBQxnPzP3sRn1srjkqiMiomBlG5r600dAGdUS5mVvsDAuptaIrw+SUInCGHJvgfqErc9f+mDutiQngmruKems4TUzDggf3lUpTZu4C6EygivYzwR0aoxrMbT4uoFXqDCVzz6ZkcA+DXmdInrnvNMtfQNfcG6ZSyHGRZUwTp0bRV2bYDGsw+FwxjYbSxtmNeqQVUHOmYSHD6Hou5zTLh5Y+9F0EYxEZ+mWZ6FqdwyaX0jL3xpDsB4ZFV3Afk2uOkmHNjuXa9yvnK5EeO5dKoJhJYHmjEandMIe/3iLLIccjuOdS2Gp0dGpuGhaL7byKi3y6O3Mfl5LAZDxmVDPIzD18uGlZlOWAgEn73mwMVRIConU6jcJfZiwiw1SBd6lqL2RFH469E8ml4qi1Opr7uDQxAcPLLseBYb225kHkUd+054ppPbgPZ0MVEOsvMx7BPdfdpVrZgcOxOfl0Ai2VYavRQjJOiO/A1UdQ+AdiXKSoKBma5l7qeBQN48aytNHpjo1S7itLWSYcei0IduJwbA7/AFyoNMcqawc62aXU3MOnNKRqmVwqgWJ6OG6Y2qDsOioNBZlkLNJEqawnnCL9ZcYquF+oaF4SWw1lRzYwAZ3Gq5VKc2z0do6UZcQxrDp3oJO9R70imy9m0FIZTq/VI48HxlxgKcsMRq8zZLWp7sgGJqCz6bMqM3dJiBiv7TCCe1HT3Yel9798vjKEoeTazURuqA5IOZcCUUeW2WooO7LGHehk7hcqzbGx++XwCoOoKzrGgVndn39SlwuiZL7ERzdGXS2jHfeVC5XIM/dEPIZiOjHcDVUiyhDRN4noEBF9h4j+i/74ZUT0FBEdI6K/JqKU/nha//6Y/vNLhZ29R+IxQjmbNJwhqw0VhR0aIHh2s1ppRj6oY9h0MvfxuqlFwe2XT+Evf+z1uGlhIvJj8ww66hUZP26Uw7HNiLb99fIpaQB4G2PsJgA3A3gHEd0O4DcB/DZjbB+AVQA/qj//RwGs6o//tv68oWNuZKo0lEhrWsPEmMbUVMdYc9+ZN+btDBHhDftmhuK2yRuZopZl+Jg/YDg+U+VccriyDNPY0r9N6v8xAG8D8Fn98QcAvFf/+n79e+g/v5u2gT8rD+7cv3kn+soA3R+AcdPcjVJIuaE6UvChHVFnz5lk3LQajD4eTAg2D/OU+hFRnIieAbAM4BEAxwGsMcYU/SmnAOzRv94D4CQA6D9fBzBt8Ts/TEQHiOjAuXPnBrsKD/DgXm+10WY7N/szl3CObeYuN1RHil0TWuZezET/meS6+1Ay92wKa9Uhl0IyxlTG2M0AFgDcBuCaQQ/MGPsEY2w/Y2z/7OzsoL/OFR7cO46QOzNAmDP3cWvmuXZXCVfNF3B1xDNGJWK59eJJ/Lf33Yg794mPA73MD0kSAsTb/vq6VTLG1ojoMQB3ACgTUULPzhcALOpPWwSwF8ApIkoAmABwIcRzDsRUPoXVagtb9ehbjcPEfN7j4gjJmS9l8PB/esuwT0MSMrEY4f379w7l2J0yzOjjAR/YwRgTstfhpVpmlojK+tdZAPcCOArgMQDv05/2IQAP6l8/pH8P/edfZYyxME86CFP5NNQ2w+n1GoCdOWIP0Cp/eFAft8xdIgmbThnmEKplskm0VGbYH4SNlwi3C8ADRBSHdjP4DGPsi0R0BMBfEdGvATgI4FP68z8F4M+I6BiAFQAfEHDevpnKa3rtqRUe3HduYMynE2goTRncJZIB6cgyQ8jceZdqrSUk2XT9jYyxZwHcYvH4S9D0997H6wC+P5SzC5GpvHaHPrlaBbBzM3dAyzJWKuMny0gkYTPMzN2w/a22sLucDf33j0104M6QJ1f04L5DNXegc+5pmblLJAMxrBp7oFPaK2pTdXyCu+7pfnJ158syvAFr3EohJZKwuXq+iDv3zeCWiycjP3Y5q8WkdUHOkDs3ffXJSGbuY9bEJJGETT6dwJ//2OuHcuyJnNiBHWOT+mVTcWSTcSxvara/O11zB2TmLpHsZKbzKfxvb7lCWN/Gzo1wAZjKp7C4VkMyTkjt4M1IfmMaN+MwiWSUyCTj+Oh9A/eD2rJzI1wAuK/7Ts7agU7mPm6WvxKJxDtjFR2M4L6D9XZAZu4SicSd8QzuO7hSBpCZu0QicWesogMP7jvVV4bDVx6yQ1UikdgxlsF9p3q5c4w6dynLSCQSG8YyuO90WYbfnLKpsfrzSSQSH4xVdBiVDdV7rp3Hr733elwxWxj2qUgkkm3Kzo5yPhmVUsh8OoF/c/slwz4NiUSyjRnLzH2nDseWSCQSr4xXcM+NhiwjkUgkboxVlCvnkvj577ka911/0bBPRSKRSIQyVsGdiPCRu/YN+zQkEolEOGMly0gkEsm4IIO7RCKRjCAyuEskEskIIoO7RCKRjCAyuEskEskIIoO7RCKRjCAyuEskEskIIoO7RCKRjCDEGBv2OYCIzgE4EfB/nwFwPsTT2SmM63UD43vt8rrHCy/XfQljbNbqB9siuA8CER1gjO0f9nlEzbheNzC+1y6ve7wY9LqlLCORSCQjiAzuEolEMoKMQnD/xLBPYEiM63UD43vt8rrHi4Gue8dr7hKJRCLpZxQyd4lEIpH0IIO7RCKRjCA7OrgT0TuI6AUiOkZEHx32+YiCiP6YiJaJ6DnTY1NE9AgRvaj/OznMcxQBEe0loseI6AgRfYeIfkp/fKSvnYgyRPRNIjqkX/d/0R+/jIie0t/vf01EqWGfqwiIKE5EB4noi/r3I3/dRPQKER0momeI6ID+2EDv8x0b3IkoDuAPANwH4DoAHySi64Z7VsL4XwDe0fPYRwE8yhi7EsCj+vejhgLgZxlj1wG4HcBH9L/xqF97A8DbGGM3AbgZwDuI6HYAvwngtxlj+wCsAvjRIZ6jSH4KwFHT9+Ny3Xcxxm421bYP9D7fscEdwG0AjjHGXmKMNQH8FYD7h3xOQmCM/TOAlZ6H7wfwgP71AwDeG+lJRQBj7Axj7Nv615vQPvB7MOLXzjS29G+T+n8MwNsAfFZ/fOSuGwCIaAHA9wL4I/17whhctw0Dvc93cnDfA+Ck6ftT+mPjwjxj7Iz+9VkA88M8GdEQ0aUAbgHwFMbg2nVp4hkAywAeAXAcwBpjTNGfMqrv998B8AsA2vr30xiP62YAHiaip4now/pjA73Px2pA9qjCGGNENLI1rURUAPC3AH6aMbahJXMao3rtjDEVwM1EVAbweQDXDPmUhENE7wKwzBh7mojeOuzziZg7GWOLRDQH4BEiet78wyDv852cuS8C2Gv6fkF/bFxYIqJdAKD/uzzk8xECESWhBfa/YIx9Tn94LK4dABhjawAeA3AHgDIR8YRsFN/vbwTwHiJ6BZrM+jYAv4vRv24wxhb1f5eh3cxvw4Dv850c3L8F4Ep9Jz0F4AMAHhryOUXJQwA+pH/9IQAPDvFchKDrrZ8CcJQx9v+afjTS105Es3rGDiLKArgX2n7DYwDepz9t5K6bMfZLjLEFxtil0D7PX2WM/RBG/LqJKE9ERf41gLcDeA4Dvs93dIcqEb0TmkYXB/DHjLFfH/IpCYGIPg3grdAsQJcA/AqAvwPwGQAXQ7NLfj9jrHfTdUdDRHcC+BqAw+hosL8MTXcf2WsnohuhbaDFoSVgn2GM/d9EdDm0jHYKwEEA/4Yx1hjemYpDl2V+jjH2rlG/bv36Pq9/mwDwl4yxXyeiaQzwPt/RwV0ikUgk1uxkWUYikUgkNsjgLpFIJCOIDO4SiUQygsjgLpFIJCOIDO4SiUQygsjgLpFIJCOIDO4SiUQygvz/4TiF0JyP6SYAAAAASUVORK5CYII="/>


```python
submission['중식계'] = lunch_count_predict
submission['석식계'] = dinner_count_predict
submission.to_csv('submission.csv',index=False)
```
