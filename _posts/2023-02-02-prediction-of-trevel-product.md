---
layout: single
title:  "[Dacon] 여행상품 신청예측"
categories: Dacon
tag: [python, Dacon, ml]
toc: true
toc_sticky: true
author_profile: true
---
데이콘 Basic 여행상품 신청예측


```python
from google.colab import drive
drive.mount('/content/drive')
```

    Mounted at /content/drive


# 0.준비


```python
import pandas as pd
```


```python
train = pd.read_csv('/content/drive/MyDrive/data/여행상품신청/train.csv')
test = pd.read_csv('/content/drive/MyDrive/data/여행상품신청/test.csv')
sample_submission = pd.read_csv('/content/drive/MyDrive/data/여행상품신청/sample_submission.csv')
```

- id : 샘플 아이디
- Age : 나이
- TypeofContact : 고객의 제품 인지 방법 (회사의 홍보 or 스스로 검색)
- CityTier : 주거 중인 도시의 등급. (인구, 시설, 생활 수준 기준) (1등급 > 2등급 > 3등급) 
- DurationOfPitch : 영업 사원이 고객에게 제공하는 프레젠테이션 기간
- Occupation : 직업
- Gender : 성별
- NumberOfPersonVisiting : 고객과 함께 여행을 계획 중인 총 인원
- NumberOfFollowups : 영업 사원의 프레젠테이션 후 이루어진 후속 조치 수
- ProductPitched : 영업 사원이 제시한 상품
- PreferredPropertyStar : 선호 호텔 숙박업소 등급
- MaritalStatus : 결혼여부
- NumberOfTrips : 평균 연간 여행 횟수
- Passport : 여권 보유 여부 (0: 없음, 1: 있음)
- PitchSatisfactionScore : 영업 사원의 프레젠테이션 만족도
- OwnCar : 자동차 보유 여부 (0: 없음, 1: 있음)
- NumberOfChildrenVisiting : 함께 여행을 계획 중인 5세 미만의 어린이 수
- Designation : (직업의) 직급
- MonthlyIncome : 월 급여
- ProdTaken : 여행 패키지 신청 여부 (0: 신청 안 함, 1: 신청함)


```python
train
```





  <div id="df-8b5e1c8d-1549-4eb7-ad44-aeff9f978c8f">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>28.0</td>
      <td>Company Invited</td>
      <td>1</td>
      <td>10.0</td>
      <td>Small Business</td>
      <td>Male</td>
      <td>3</td>
      <td>4.0</td>
      <td>Basic</td>
      <td>3.0</td>
      <td>Married</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>Executive</td>
      <td>20384.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>34.0</td>
      <td>Self Enquiry</td>
      <td>3</td>
      <td>NaN</td>
      <td>Small Business</td>
      <td>Female</td>
      <td>2</td>
      <td>4.0</td>
      <td>Deluxe</td>
      <td>4.0</td>
      <td>Single</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>Manager</td>
      <td>19599.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>45.0</td>
      <td>Company Invited</td>
      <td>1</td>
      <td>NaN</td>
      <td>Salaried</td>
      <td>Male</td>
      <td>2</td>
      <td>3.0</td>
      <td>Deluxe</td>
      <td>4.0</td>
      <td>Married</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>0.0</td>
      <td>Manager</td>
      <td>NaN</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>29.0</td>
      <td>Company Invited</td>
      <td>1</td>
      <td>7.0</td>
      <td>Small Business</td>
      <td>Male</td>
      <td>3</td>
      <td>5.0</td>
      <td>Basic</td>
      <td>4.0</td>
      <td>Married</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>Executive</td>
      <td>21274.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>42.0</td>
      <td>Self Enquiry</td>
      <td>3</td>
      <td>6.0</td>
      <td>Salaried</td>
      <td>Male</td>
      <td>2</td>
      <td>3.0</td>
      <td>Deluxe</td>
      <td>3.0</td>
      <td>Divorced</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>Manager</td>
      <td>19907.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>1951</td>
      <td>28.0</td>
      <td>Self Enquiry</td>
      <td>1</td>
      <td>10.0</td>
      <td>Small Business</td>
      <td>Male</td>
      <td>3</td>
      <td>5.0</td>
      <td>Basic</td>
      <td>3.0</td>
      <td>Single</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>Executive</td>
      <td>20723.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>1952</td>
      <td>41.0</td>
      <td>Self Enquiry</td>
      <td>3</td>
      <td>8.0</td>
      <td>Salaried</td>
      <td>Female</td>
      <td>3</td>
      <td>3.0</td>
      <td>Super Deluxe</td>
      <td>5.0</td>
      <td>Divorced</td>
      <td>1.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>1.0</td>
      <td>AVP</td>
      <td>31595.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>1953</td>
      <td>38.0</td>
      <td>Company Invited</td>
      <td>3</td>
      <td>28.0</td>
      <td>Small Business</td>
      <td>Female</td>
      <td>3</td>
      <td>4.0</td>
      <td>Basic</td>
      <td>3.0</td>
      <td>Divorced</td>
      <td>7.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>2.0</td>
      <td>Executive</td>
      <td>21651.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>1954</td>
      <td>28.0</td>
      <td>Self Enquiry</td>
      <td>3</td>
      <td>30.0</td>
      <td>Small Business</td>
      <td>Female</td>
      <td>3</td>
      <td>5.0</td>
      <td>Deluxe</td>
      <td>3.0</td>
      <td>Married</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>Manager</td>
      <td>22218.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>1955</td>
      <td>22.0</td>
      <td>Company Invited</td>
      <td>1</td>
      <td>9.0</td>
      <td>Salaried</td>
      <td>Male</td>
      <td>2</td>
      <td>4.0</td>
      <td>Basic</td>
      <td>3.0</td>
      <td>Divorced</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>0.0</td>
      <td>Executive</td>
      <td>17853.0</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
<p>1955 rows × 20 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-8b5e1c8d-1549-4eb7-ad44-aeff9f978c8f')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-8b5e1c8d-1549-4eb7-ad44-aeff9f978c8f button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-8b5e1c8d-1549-4eb7-ad44-aeff9f978c8f');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>




## 0.1.데이터 전처리

### 0.1.1.결측치 처리


```python
train.isna().sum()
```




    id                            0
    Age                          94
    TypeofContact                10
    CityTier                      0
    DurationOfPitch             102
    Occupation                    0
    Gender                        0
    NumberOfPersonVisiting        0
    NumberOfFollowups            13
    ProductPitched                0
    PreferredPropertyStar        10
    MaritalStatus                 0
    NumberOfTrips                57
    Passport                      0
    PitchSatisfactionScore        0
    OwnCar                        0
    NumberOfChildrenVisiting     27
    Designation                   0
    MonthlyIncome               100
    ProdTaken                     0
    dtype: int64




```python
test.isna().sum()
```




    id                            0
    Age                         132
    TypeofContact                15
    CityTier                      0
    DurationOfPitch             149
    Occupation                    0
    Gender                        0
    NumberOfPersonVisiting        0
    NumberOfFollowups            32
    ProductPitched                0
    PreferredPropertyStar        16
    MaritalStatus                 0
    NumberOfTrips                83
    Passport                      0
    PitchSatisfactionScore        0
    OwnCar                        0
    NumberOfChildrenVisiting     39
    Designation                   0
    MonthlyIncome               133
    dtype: int64




```python
def handle_na(data):
    temp = data.copy()
    for col, dtype in temp.dtypes.items():
        if dtype == 'object':
            # 문자형 칼럼의 경우 'Unknown'을 채워줍니다.
            value = 'Unknown'
        elif dtype == int or dtype == float:
            # 수치형 칼럼의 경우 0을 채워줍니다.
            value = 0
        temp.loc[:,col] = temp[col].fillna(value)
    return temp

train_nona = handle_na(train)

# 결측치 처리가 잘 되었는지 확인해 줍니다.
train_nona.isna().sum()
```




    id                          0
    Age                         0
    TypeofContact               0
    CityTier                    0
    DurationOfPitch             0
    Occupation                  0
    Gender                      0
    NumberOfPersonVisiting      0
    NumberOfFollowups           0
    ProductPitched              0
    PreferredPropertyStar       0
    MaritalStatus               0
    NumberOfTrips               0
    Passport                    0
    PitchSatisfactionScore      0
    OwnCar                      0
    NumberOfChildrenVisiting    0
    Designation                 0
    MonthlyIncome               0
    ProdTaken                   0
    dtype: int64



### 0.1.2.문자형 변수 전처리


```python
object_columns = train_nona.columns[train_nona.dtypes == 'object']
print('object 칼럼은 다음과 같습니다 : ', list(object_columns))

# 해당 칼럼만 보아서 봅시다
train_nona[object_columns]
```

    object 칼럼은 다음과 같습니다 :  ['TypeofContact', 'Occupation', 'Gender', 'ProductPitched', 'MaritalStatus', 'Designation']






  <div id="df-997d698d-5365-4ab9-856a-0c0fac20d880">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>TypeofContact</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>ProductPitched</th>
      <th>MaritalStatus</th>
      <th>Designation</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>Company Invited</td>
      <td>Small Business</td>
      <td>Male</td>
      <td>Basic</td>
      <td>Married</td>
      <td>Executive</td>
    </tr>
    <tr>
      <th>1</th>
      <td>Self Enquiry</td>
      <td>Small Business</td>
      <td>Female</td>
      <td>Deluxe</td>
      <td>Single</td>
      <td>Manager</td>
    </tr>
    <tr>
      <th>2</th>
      <td>Company Invited</td>
      <td>Salaried</td>
      <td>Male</td>
      <td>Deluxe</td>
      <td>Married</td>
      <td>Manager</td>
    </tr>
    <tr>
      <th>3</th>
      <td>Company Invited</td>
      <td>Small Business</td>
      <td>Male</td>
      <td>Basic</td>
      <td>Married</td>
      <td>Executive</td>
    </tr>
    <tr>
      <th>4</th>
      <td>Self Enquiry</td>
      <td>Salaried</td>
      <td>Male</td>
      <td>Deluxe</td>
      <td>Divorced</td>
      <td>Manager</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>Self Enquiry</td>
      <td>Small Business</td>
      <td>Male</td>
      <td>Basic</td>
      <td>Single</td>
      <td>Executive</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>Self Enquiry</td>
      <td>Salaried</td>
      <td>Female</td>
      <td>Super Deluxe</td>
      <td>Divorced</td>
      <td>AVP</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>Company Invited</td>
      <td>Small Business</td>
      <td>Female</td>
      <td>Basic</td>
      <td>Divorced</td>
      <td>Executive</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>Self Enquiry</td>
      <td>Small Business</td>
      <td>Female</td>
      <td>Deluxe</td>
      <td>Married</td>
      <td>Manager</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>Company Invited</td>
      <td>Salaried</td>
      <td>Male</td>
      <td>Basic</td>
      <td>Divorced</td>
      <td>Executive</td>
    </tr>
  </tbody>
</table>
<p>1955 rows × 6 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-997d698d-5365-4ab9-856a-0c0fac20d880')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-997d698d-5365-4ab9-856a-0c0fac20d880 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-997d698d-5365-4ab9-856a-0c0fac20d880');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
train_nona['Gender'].value_counts()
```




    Male       1207
    Female      692
    Fe Male      56
    Name: Gender, dtype: int64




```python
train_nona.loc[train_nona['Gender']=='Fe Male','Gender'] = 'Female'
test.loc[test['Gender']=='Fe Male','Gender'] = 'Female'
train_nona['Gender'].value_counts()
```




    Male      1207
    Female     748
    Name: Gender, dtype: int64




```python
# LabelEncoder를 준비해줍니다.
from sklearn.preprocessing import LabelEncoder

encoder = LabelEncoder()

# LabelEcoder는 학습하는 과정을 필요로 합니다.
encoder.fit(train_nona['TypeofContact'])

#학습된 encoder를 사용하여 문자형 변수를 숫자로 변환해줍니다.
encoder.transform(train_nona['TypeofContact'])
```




    array([0, 1, 0, ..., 0, 1, 0])




```python
train_enc = train_nona.copy()

# 모든 문자형 변수에 대해 encoder를 적용합니다.
for o_col in object_columns:
    encoder = LabelEncoder()
    encoder.fit(train_enc[o_col])
    train_enc[o_col] = encoder.transform(train_enc[o_col])

# 결과를 확인합니다.
train_enc
```





  <div id="df-3e59f428-b306-41d5-85bd-2686f5982d9d">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>28.0</td>
      <td>0</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>20384.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>34.0</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19599.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>45.0</td>
      <td>0</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>29.0</td>
      <td>0</td>
      <td>1</td>
      <td>7.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>21274.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>42.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19907.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>1951</td>
      <td>28.0</td>
      <td>1</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>20723.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>1952</td>
      <td>41.0</td>
      <td>1</td>
      <td>3</td>
      <td>8.0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>4</td>
      <td>5.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>31595.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>1953</td>
      <td>38.0</td>
      <td>0</td>
      <td>3</td>
      <td>28.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>7.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>21651.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>1954</td>
      <td>28.0</td>
      <td>1</td>
      <td>3</td>
      <td>30.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>2</td>
      <td>22218.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>1955</td>
      <td>22.0</td>
      <td>0</td>
      <td>1</td>
      <td>9.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>17853.0</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
<p>1955 rows × 20 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-3e59f428-b306-41d5-85bd-2686f5982d9d')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-3e59f428-b306-41d5-85bd-2686f5982d9d button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-3e59f428-b306-41d5-85bd-2686f5982d9d');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
# 결측치 처리
test = handle_na(test)

# 문자형 변수 전처리
for o_col in object_columns:
    encoder = LabelEncoder()
    
    # test 데이터를 이용해 encoder를 학습하는 것은 Data Leakage 입니다! 조심!
    encoder.fit(train_nona[o_col])
    
    # test 데이터는 오로지 transform 에서만 사용되어야 합니다.
    test[o_col] = encoder.transform(test[o_col])

# 결과를 확인합니다.
test
```





  <div id="df-32387cc9-c583-4576-8e0c-f342b95f16ea">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>0.524590</td>
      <td>0</td>
      <td>3</td>
      <td>0.000000</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>2</td>
      <td>0</td>
      <td>1.0</td>
      <td>2</td>
      <td>-0.363121</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>0.754098</td>
      <td>1</td>
      <td>2</td>
      <td>0.305556</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>0</td>
      <td>1.0</td>
      <td>2</td>
      <td>-0.316471</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>0.606557</td>
      <td>1</td>
      <td>3</td>
      <td>0.611111</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>5.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>-0.142953</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>0.704918</td>
      <td>1</td>
      <td>1</td>
      <td>1.000000</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>3</td>
      <td>6.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>2.0</td>
      <td>2</td>
      <td>0.070608</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>0.409836</td>
      <td>1</td>
      <td>3</td>
      <td>0.194444</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
      <td>4.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>3</td>
      <td>3.0</td>
      <td>1</td>
      <td>4</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>-0.070797</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2928</th>
      <td>2929</td>
      <td>0.885246</td>
      <td>1</td>
      <td>1</td>
      <td>0.166667</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>3.0</td>
      <td>4</td>
      <td>3.0</td>
      <td>2</td>
      <td>7.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>1.309948</td>
    </tr>
    <tr>
      <th>2929</th>
      <td>2930</td>
      <td>0.540984</td>
      <td>1</td>
      <td>1</td>
      <td>0.250000</td>
      <td>3</td>
      <td>0</td>
      <td>4</td>
      <td>2.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>3</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.174085</td>
    </tr>
    <tr>
      <th>2930</th>
      <td>2931</td>
      <td>0.540984</td>
      <td>0</td>
      <td>1</td>
      <td>0.861111</td>
      <td>2</td>
      <td>1</td>
      <td>4</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.207652</td>
    </tr>
    <tr>
      <th>2931</th>
      <td>2932</td>
      <td>0.426230</td>
      <td>1</td>
      <td>1</td>
      <td>0.250000</td>
      <td>3</td>
      <td>1</td>
      <td>4</td>
      <td>2.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>3</td>
      <td>2.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>-0.041459</td>
    </tr>
    <tr>
      <th>2932</th>
      <td>2933</td>
      <td>0.508197</td>
      <td>1</td>
      <td>1</td>
      <td>0.250000</td>
      <td>2</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.054749</td>
    </tr>
  </tbody>
</table>
<p>2933 rows × 19 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-32387cc9-c583-4576-8e0c-f342b95f16ea')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-32387cc9-c583-4576-8e0c-f342b95f16ea button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-32387cc9-c583-4576-8e0c-f342b95f16ea');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>




### 0.1.3.(추가) 결측치 처리 모듈 생성
- 0으로 채운 수치형 컬럼의 결측치를 회귀예측 모델로 예측하여 채워넣는 모듈 생성


```python
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error
from sklearn.ensemble import ExtraTreesRegressor
```


```python
def prod_val(feature:str):
  if len(train_enc[train_enc[feature]==0])==0:
    return 'already processed'
  train_temp = train_enc[train_enc[feature]!=0]

  features = train_temp.columns[1:-1].drop(feature)
  target = feature

  X = train_temp[features]
  y = train_temp[target]

  X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,shuffle=False)
  model_rf = ExtraTreesRegressor(n_estimators=300)

  model_rf.fit(X_train,y_train)

  train_predict = model_rf.predict(X_test)

  print(f'{feature} MAE: {mean_absolute_error(train_predict,y_test)}')

  X = train_enc[train_enc[feature]==0][features]

  train_enc.loc[train_enc[feature]==0,feature] = model_rf.predict(X)

  test_temp = test[test[feature]==0]

  X = test_temp[features]

  test.loc[test[feature]==0,feature] = model_rf.predict(X)

  print(f'\ntrain set: \n{train_enc[feature].value_counts().sort_index().head(3)}')
  print(f'\ntest set: \n{test[feature].value_counts().sort_index().head(3)}')
```

# 1.EDA


```python
import seaborn as sns
import matplotlib.pyplot as plt
import numpy as np
```

## 1.1.target


```python
plt.figure(dpi=100)
train_enc['ProdTaken'].value_counts().plot(kind='bar')
plt.title('target distribution')
plt.xticks(np.arange(2),labels = ['not application','application'],rotation=45)
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536439-b3115f47-6f39-41ae-8b41-92615b7aa62f.png)
    


## 1.2.feature & target

### 1.2.1.Feature correlatoin(feature importances)


```python
y = np.arange(len(train_enc.corr()['ProdTaken'].values))
ind = train_enc.corr()['ProdTaken'].index

values = abs(train_enc.corr()['ProdTaken'].values)

plt.figure(dpi=150)

plt.title('Feature correlation with target')
plt.barh(y,values)
plt.yticks(y,ind)

plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536446-b7da79aa-62c2-452c-90ce-1665a84f77b8.png)
    


- 여권의 유무가 target과 가장 상관관계가 높은 것을 알 수 있다.

### 1.2.2.CityTier ~ target


```python
train_enc.groupby(['CityTier','ProdTaken'])['id'].count()
```




    CityTier  ProdTaken
    1         0            1065
              1             218
    2         0              66
              1              24
    3         0             441
              1             141
    Name: id, dtype: int64




```python
temp = train_enc.groupby(['CityTier','ProdTaken'])['id'].count().values
temp
```




    array([1065,  218,   66,   24,  441,  141])




```python
no_application = temp[[0,2,4]]
application = temp[[1,3,5]]
alpha = 0.5
no_application
```




    array([1065,   66,  441])




```python
plt.figure(dpi=100)
p1 = plt.bar(np.arange(3), no_application, color='b', alpha=alpha)
p2 = plt.bar(np.arange(3), application, color='r', alpha=alpha,bottom=no_application) # stacked bar chart

plt.title('Target distribution according to CityTier')
plt.xlabel('CityTier')
plt.ylabel('ProdTaken')
plt.xticks(np.arange(3),labels=[1,2,3])
plt.legend((p1[0],p2[0]),('no application','application'))
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536451-114b4b36-6642-4986-8b5f-a29da89d6950.png)
    



```python
ratio  = application / no_application
plt.figure(dpi=100)
plt.bar(np.arange(3),ratio)
plt.xlabel('CityTier')
plt.title('application ratio')
plt.xticks(np.arange(3),labels=[1,2,3])
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536454-b0a4423b-1ac2-4e63-8d2c-f67f380bd03a.png)
    


- 그래프를 통해 2등급 도시에는 주거 중인 시민이 별로 없다는 것을 알 수 있다.
- 또한 신청자 수는 1등급 도시에서 가장 많았지만 각 도시별 신청자 수 비율을 보면 2등급 도시가 가장 높은 것을 알 수 있다.

### 1.2.3.Passport ~ target


```python
train_enc.groupby(['Passport','ProdTaken'])['id'].count()
```




    Passport  ProdTaken
    0         0            1218
              1             168
    1         0             354
              1             215
    Name: id, dtype: int64




```python
temp = train_enc.groupby(['Passport','ProdTaken'])['id'].count().values
temp
```




    array([1218,  168,  354,  215])




```python
no_application = temp[[0,2]]
application = temp[[1,3]]
alpha = 0.5
no_application
```




    array([1218,  354])




```python
plt.figure(dpi=100)
p1 = plt.bar(np.arange(2), no_application, color='b', alpha=alpha)
p2 = plt.bar(np.arange(2), application, color='r', alpha=alpha,bottom=no_application) # stacked bar chart

plt.title('Target distribution according to Passport')
plt.xlabel('Passport')
plt.ylabel('ProdTaken')
plt.xticks(np.arange(2),labels=['no passport','passport'])
plt.legend((p1[0],p2[0]),('no application','application'))
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536459-16c6dd37-a684-4379-b3b3-4222b914db6a.png)
    



```python
fig=plt.figure(figsize=(10,10), dpi=100)
(ax1, ax2)=fig.subplots(1,2).flatten()

temp = train_enc[train_enc['Passport']==0].reset_index(drop = True)
x = temp['ProdTaken'].value_counts()
x.plot.pie(ax=ax1)
_=ax1.set_title('no passport')
ax1.legend(bbox_to_anchor=(0.9, 1), loc=2,labels=['no application','application'])

temp = train_enc[train_enc['Passport']==1].reset_index(drop = True)
x = temp['ProdTaken'].value_counts()
x.plot.pie(ax=ax2)
_=ax2.set_title('have passport')


plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536463-36ea8ad1-2047-40e7-8f69-c07fecae3e28.png)
    


- 그래프를 통해 여권을 가지고 있는 사람은 가지고 있지 않는 사람보다 더 높은 비율로 신청을 하는 것으로 보인다. (어찌보면 당연한 결과이다.)

### 1.2.4. ProductPitched ~ target


```python
train_nona['ProductPitched'].unique()
```




    array(['Basic', 'Deluxe', 'King', 'Standard', 'Super Deluxe'],
          dtype=object)




```python
train_nona.groupby(['ProductPitched','ProdTaken'])['id'].count()
```




    ProductPitched  ProdTaken
    Basic           0            522
                    1            223
    Deluxe          0            599
                    1             90
    King            0             80
                    1              9
    Standard        0            251
                    1             51
    Super Deluxe    0            120
                    1             10
    Name: id, dtype: int64




```python
temp = train_nona.groupby(['ProductPitched','ProdTaken'])['id'].count().values
temp
```




    array([522, 223, 599,  90,  80,   9, 251,  51, 120,  10])




```python
ind = train_nona['ProductPitched'].unique()
ind
```




    array(['Basic', 'Deluxe', 'King', 'Standard', 'Super Deluxe'],
          dtype=object)




```python
no_application = temp[[0,2,4,6,8]]
application = temp[[1,3,5,7,9]]
alpha = 0.5
no_application
```




    array([522, 599,  80, 251, 120])




```python
plt.figure(figsize=(12,7))
p1 = plt.bar(np.arange(5), no_application, color='b', alpha=alpha)
p2 = plt.bar(np.arange(5), application, color='r', alpha=alpha,bottom=no_application) # stacked bar chart

plt.title('Target distribution according to ProductPitched')
plt.xlabel('ProductPitched')
plt.ylabel('ProdTaken')
plt.xticks(np.arange(5),labels=ind)
plt.legend((p1[0],p2[0]),('no application','application'))
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536467-9fcfc7df-d667-4486-834c-c5541a45296d.png)
    



```python
ratio  = application / no_application
plt.figure(figsize=(12,7))
plt.bar(np.arange(5),ratio)
plt.xlabel('ProductPitched')
plt.title('application ratio')
plt.xticks(np.arange(5),labels=ind)
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536472-17165ac7-a4ab-4706-b21f-b5181e3c6505.png)
    


- 그래프를 통해 영업사원이 Basic 상품을 추천했을때 가장 신청률이 높은 것을 알 수 있다.

### 1.2.5.MaritalStatus ~ target


```python
train_nona['MaritalStatus'].value_counts()
```




    Married      949
    Divorced     375
    Single       349
    Unmarried    282
    Name: MaritalStatus, dtype: int64




```python
train_enc['MaritalStatus'].value_counts()
```




    1    949
    0    375
    2    349
    3    282
    Name: MaritalStatus, dtype: int64




```python
fig=plt.figure(figsize=(10,10), dpi=100)
(ax1, ax2,ax3,ax4)=fig.subplots(1,4).flatten()

temp = train_enc[train_enc['MaritalStatus']==0].reset_index(drop = True)
x = temp['ProdTaken'].value_counts()
x.plot.pie(ax=ax1)
_=ax1.set_title('Divorced')


temp = train_enc[train_enc['MaritalStatus']==1].reset_index(drop = True)
x = temp['ProdTaken'].value_counts()
x.plot.pie(ax=ax2)
_=ax2.set_title('Married')

temp = train_enc[train_enc['MaritalStatus']==2].reset_index(drop = True)
x = temp['ProdTaken'].value_counts()
x.plot.pie(ax=ax3)
_=ax3.set_title('Single')

temp = train_enc[train_enc['MaritalStatus']==3].reset_index(drop = True)
x = temp['ProdTaken'].value_counts()
x.plot.pie(ax=ax4)
_=ax4.set_title('Unmarried')
ax4.legend(bbox_to_anchor=(0.9, 1), loc=2,labels=['no application','application'])
plt.show() 
```


    
![](https://user-images.githubusercontent.com/122070432/216536475-6d178b59-73f2-4739-b2a3-99d55fbdbc50.png)
    


- Divorced 는 이혼한 사람을 뜻하는데 이혼한 사람과 결혼한 사람이 비슷한 신청률을 보였고, 미혼인 사람과 싱글인 사람이 25퍼센트가 넘는 신청률을 보였다.

### 1.2.6.CityTier ~ MonthlyIncome


```python
plt.rcParams['font.size'] = 15
data = train_enc.describe().loc['min':'max', 'MonthlyIncome']

plt.title('MonthlyIncome')
plt.plot(data, color = 'red', marker = 'o')
plt.grid(True)
```


    
![](https://user-images.githubusercontent.com/122070432/216536479-78a03593-904f-4274-aad3-838450d21ebd.png)
    



```python
plt.figure(figsize=(10,5))
sns.boxplot(data=train_nona, x="CityTier", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536482-eb11db8c-8a62-4d68-bc55-85315711bf84.png)
    



```python
plt.figure(figsize=(10,5))
sns.boxplot(data=train_nona[train_nona['MonthlyIncome']<60000], x="CityTier", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536483-803cfd01-dfdd-4f5c-ad2e-5b489de5516e.png)
    


### 1.2.7.Designation ~ MonthlyIncome


```python
plt.figure(figsize=(10,5))
sns.boxplot(data=train_nona, x="Designation", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536484-67fc9ee7-369e-4b1e-9f7d-ba3d2f4fe0a8.png)
    



```python
plt.figure(figsize=(10,5))
sns.boxplot(data=train_nona[train_nona['MonthlyIncome']<60000], x="Designation", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536486-5d6e2e79-bef8-4d05-b8d2-b1a5532dab2e.png)
    


- MonthlyIncome의 이상치는 특정한 도시 등급과 직급에서 발견됨.


```python
train_enc[train_enc['MonthlyIncome']>40000]['ProdTaken'].value_counts()
```




    0    2
    Name: ProdTaken, dtype: int64



### 1.2.8.NumberOfTrips ~ MonthlyIncome




```python
plt.figure(figsize=(10,5))
sns.boxplot(data=train_nona, x="NumberOfTrips", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536488-4cb52966-2c66-4a6c-8c66-87e6ac14ea64.png)
    


- 평균 연간 여행 횟수가 0인 사람들의 월 급여의 평균이 가장 높았다. 
꼭 월 급여가 높다고 여행을 많이 다니는 것은 아닌 것을 알 수 있다.

### 1.2.9.DurationOfPitch ~ PitchSatisfactionScore




```python
plt.figure(figsize=(10,5))
sns.boxplot(data=train_enc, x="PitchSatisfactionScore", y="DurationOfPitch")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536489-0ed99141-b2a2-4fc1-b787-92ef18433350.png)
    


- 두 변수간 큰 상관관계가 없는 것으로 보임

### 1.2.10.heatmap


```python
plt.figure(figsize=(15,15))
sns.heatmap(train_enc.corr(method='pearson'),annot=True, fmt='.1f', linewidths=.5, cmap='Blues')

```




    <matplotlib.axes._subplots.AxesSubplot at 0x7fac6aeff410>




    
![](https://user-images.githubusercontent.com/122070432/216536493-bd2b7207-610d-43d5-8f87-fb07abf75c7d.png)
    


## 1.3.heatmap 정보를 기반으로 EDA

### 1.3.1. MonthlyIncome ~ Age


```python
plt.figure(dpi=130)

sns.scatterplot(train_enc['Age'],train_enc['MonthlyIncome'])
plt.xlabel('Age')
plt.ylabel('MonthlyIncome')
plt.show()
```

    /usr/local/lib/python3.7/dist-packages/seaborn/_decorators.py:43: FutureWarning: Pass the following variables as keyword args: x, y. From version 0.12, the only valid positional argument will be `data`, and passing other arguments without an explicit keyword will result in an error or misinterpretation.
      FutureWarning



    
![](https://user-images.githubusercontent.com/122070432/216536498-ad06ff9d-d423-412b-9491-701d19a329be.png)
    


- 나이가 0인 데이터가 확인되고 또 그 중 월 급여가 0이 아닌 데이터가 보인다. 데이터의 오류가 아닐까 싶어 Age데이터를 확인해 봐야겠다.


```python
train_enc[train_enc['Age']==0]
```





  <div id="df-96ccd95d-add1-488f-9790-de3ec103807f">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>13</th>
      <td>14</td>
      <td>0.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>26</th>
      <td>27</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>6.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>0.0</td>
      <td>1</td>
      <td>18591.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>35</th>
      <td>36</td>
      <td>0.0</td>
      <td>1</td>
      <td>2</td>
      <td>14.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>87</th>
      <td>88</td>
      <td>0.0</td>
      <td>1</td>
      <td>2</td>
      <td>8.0</td>
      <td>2</td>
      <td>1</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>18539.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>121</th>
      <td>122</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>35.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1882</th>
      <td>1883</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>15.0</td>
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>0.0</td>
      <td>1</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1888</th>
      <td>1889</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>12.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>1</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1914</th>
      <td>1915</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>7.0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1916</th>
      <td>1917</td>
      <td>0.0</td>
      <td>1</td>
      <td>2</td>
      <td>26.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>18669.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1923</th>
      <td>1924</td>
      <td>0.0</td>
      <td>0</td>
      <td>3</td>
      <td>16.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>94 rows × 20 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-96ccd95d-add1-488f-9790-de3ec103807f')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-96ccd95d-add1-488f-9790-de3ec103807f button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-96ccd95d-add1-488f-9790-de3ec103807f');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
test[test['Age']==0]
```





  <div id="df-bbbb24dd-37dc-4ab7-bcac-aa9d67b6b4cc">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>20</th>
      <td>21</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>8.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>5.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>6.0</td>
      <td>1</td>
      <td>3</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>18464.0</td>
    </tr>
    <tr>
      <th>25</th>
      <td>26</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>12.0</td>
      <td>2</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>18702.0</td>
    </tr>
    <tr>
      <th>54</th>
      <td>55</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>6.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>0.0</td>
    </tr>
    <tr>
      <th>67</th>
      <td>68</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>6.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>0.0</td>
    </tr>
    <tr>
      <th>95</th>
      <td>96</td>
      <td>0.0</td>
      <td>0</td>
      <td>1</td>
      <td>15.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2799</th>
      <td>2800</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>8.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>5.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>6.0</td>
      <td>1</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>1</td>
      <td>18464.0</td>
    </tr>
    <tr>
      <th>2811</th>
      <td>2812</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>13.0</td>
      <td>2</td>
      <td>1</td>
      <td>3</td>
      <td>1.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>0.0</td>
      <td>1</td>
      <td>18578.0</td>
    </tr>
    <tr>
      <th>2834</th>
      <td>2835</td>
      <td>0.0</td>
      <td>1</td>
      <td>3</td>
      <td>14.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>0.0</td>
    </tr>
    <tr>
      <th>2841</th>
      <td>2842</td>
      <td>0.0</td>
      <td>0</td>
      <td>1</td>
      <td>22.0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>1</td>
      <td>4</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>0.0</td>
    </tr>
    <tr>
      <th>2916</th>
      <td>2917</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>11.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
    </tr>
  </tbody>
</table>
<p>132 rows × 19 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-bbbb24dd-37dc-4ab7-bcac-aa9d67b6b4cc')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-bbbb24dd-37dc-4ab7-bcac-aa9d67b6b4cc button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-bbbb24dd-37dc-4ab7-bcac-aa9d67b6b4cc');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
plt.figure(figsize=(10,5),dpi=100)

sns.scatterplot(train_enc['Age'],train_enc['MonthlyIncome'],hue=train_enc['ProductPitched'],s=60)
plt.xlabel('Age')
plt.ylabel('MonthlyIncome')

plt.legend(title='ProductPitched',loc='upper right')
plt.show()
```

    /usr/local/lib/python3.7/dist-packages/seaborn/_decorators.py:43: FutureWarning: Pass the following variables as keyword args: x, y. From version 0.12, the only valid positional argument will be `data`, and passing other arguments without an explicit keyword will result in an error or misinterpretation.
      FutureWarning



    
![](https://user-images.githubusercontent.com/122070432/216536500-5e0d03a6-3bb5-4ec6-b65f-4091efdae84a.png)
    


#### 1.3.1.1.Age가 0인 데이터 age 예측

- age가 0일 수는 없다고 생각해서 다른 feature들로 age를 예측해봐야겠다고 생각했다.


```python
temp = train_enc[train_enc['Age']!=0]
temp
```





  <div id="df-ddae0d7f-3888-4f1d-be15-8984af5afadc">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>28.0</td>
      <td>0</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>20384.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>34.0</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19599.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>45.0</td>
      <td>0</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>29.0</td>
      <td>0</td>
      <td>1</td>
      <td>7.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>21274.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>42.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19907.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>1951</td>
      <td>28.0</td>
      <td>1</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>20723.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>1952</td>
      <td>41.0</td>
      <td>1</td>
      <td>3</td>
      <td>8.0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>4</td>
      <td>5.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>31595.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>1953</td>
      <td>38.0</td>
      <td>0</td>
      <td>3</td>
      <td>28.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>7.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>21651.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>1954</td>
      <td>28.0</td>
      <td>1</td>
      <td>3</td>
      <td>30.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>2</td>
      <td>22218.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>1955</td>
      <td>22.0</td>
      <td>0</td>
      <td>1</td>
      <td>9.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>17853.0</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
<p>1861 rows × 20 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-ddae0d7f-3888-4f1d-be15-8984af5afadc')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-ddae0d7f-3888-4f1d-be15-8984af5afadc button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-ddae0d7f-3888-4f1d-be15-8984af5afadc');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
features = temp.columns[2:-1]
target = 'Age'
```


```python
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error

X = temp[features]
y = temp[target]

X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,shuffle=False)
model_rf = RandomForestRegressor()

model_rf.fit(X_train,y_train)

predict = model_rf.predict(X_test)


```


```python
y_test
```




    1755    54.0
    1756    59.0
    1757    21.0
    1758    43.0
    1759    48.0
            ... 
    1950    28.0
    1951    41.0
    1952    38.0
    1953    28.0
    1954    22.0
    Name: Age, Length: 187, dtype: float64




```python
plt.figure(dpi=130)
plt.plot(predict)
plt.plot(y_test.values,alpha=0.7)
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536505-4c00bda4-860b-400e-8b65-91b5530e2ac0.png)
    



```python
mean_absolute_error(predict,y_test)
```




    5.528181818181818




```python
temp = train_enc[train_enc['Age']==0]
temp
```





  <div id="df-4ead7b50-a9fb-4aff-8a92-851e68dd97af">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>13</th>
      <td>14</td>
      <td>0.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>26</th>
      <td>27</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>6.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>0.0</td>
      <td>1</td>
      <td>18591.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>35</th>
      <td>36</td>
      <td>0.0</td>
      <td>1</td>
      <td>2</td>
      <td>14.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>87</th>
      <td>88</td>
      <td>0.0</td>
      <td>1</td>
      <td>2</td>
      <td>8.0</td>
      <td>2</td>
      <td>1</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>18539.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>121</th>
      <td>122</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>35.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1882</th>
      <td>1883</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>15.0</td>
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>0.0</td>
      <td>1</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1888</th>
      <td>1889</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>12.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>1</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1914</th>
      <td>1915</td>
      <td>0.0</td>
      <td>1</td>
      <td>1</td>
      <td>7.0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1916</th>
      <td>1917</td>
      <td>0.0</td>
      <td>1</td>
      <td>2</td>
      <td>26.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>18669.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1923</th>
      <td>1924</td>
      <td>0.0</td>
      <td>0</td>
      <td>3</td>
      <td>16.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>94 rows × 20 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-4ead7b50-a9fb-4aff-8a92-851e68dd97af')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-4ead7b50-a9fb-4aff-8a92-851e68dd97af button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-4ead7b50-a9fb-4aff-8a92-851e68dd97af');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
X = temp[features]

train_enc.loc[train_enc['Age']==0,'Age'] = model_rf.predict(X)
```


```python
train_enc['Age'].value_counts().sort_index()
```




    18.0     5
    19.0    16
    20.0    13
    21.0    17
    22.0    20
            ..
    57.0     9
    58.0    11
    59.0    14
    60.0    12
    61.0     3
    Name: Age, Length: 135, dtype: int64




```python
plt.figure(figsize=(10,5),dpi=100)

sns.scatterplot(train_enc['Age'],train_enc['MonthlyIncome'],hue=train_enc['ProductPitched'],s=60)
plt.xlabel('Age')
plt.ylabel('MonthlyIncome')

plt.legend(title='ProductPitched',loc='upper right')
plt.show()
```

    /usr/local/lib/python3.7/dist-packages/seaborn/_decorators.py:43: FutureWarning: Pass the following variables as keyword args: x, y. From version 0.12, the only valid positional argument will be `data`, and passing other arguments without an explicit keyword will result in an error or misinterpretation.
      FutureWarning



    
![](https://user-images.githubusercontent.com/122070432/216536506-f0ece5b4-0f55-45ca-b93b-bf78bec230ed.png)
    


- Age 가 0인 데이터가 없어진 것을 확인 할 수있다.

### 1.3.2.MonthlyIncome ~ NumberOfPersonVisiting


```python
train_enc['NumberOfPersonVisiting'].value_counts()
```




    3    988
    2    543
    4    412
    1     11
    5      1
    Name: NumberOfPersonVisiting, dtype: int64




```python
temp = train_enc['NumberOfPersonVisiting'].value_counts()

plt.figure(dpi=100)

plt.bar(temp.index,temp.values)
plt.xlabel('NumberOfPersonVisiting')
plt.ylabel('counts')
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536509-4116ef1d-decd-4ad1-b19f-cb935d029919.png)
    


- 혼자 가거나 5명이서 가는 경우는 거의 없고 2,3,4명이서 가는 경우가 많다


```python
plt.figure(dpi=100)
sns.boxplot(data=train_enc, x="NumberOfPersonVisiting", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536510-886ce79c-e2c5-43e3-a903-81531ae1d208.png)
    



```python
plt.figure(dpi=100)
sns.violinplot(data=train_enc, x="NumberOfPersonVisiting", y="MonthlyIncome")
plt.show()
```


    
![](https://user-images.githubusercontent.com/122070432/216536512-e8329b00-3466-4317-a797-242510ad2ced.png)
    


- 2,3,4 에는 차이가 크지 않지만 확실히 혼자가는 사람은 월 급여가 낮은 것을 알 수 있다.

### 1.3.3.MonthlyIncome ~ DurationOfPitch


```python
train_enc['DurationOfPitch'].value_counts()
```




    9.0     199
    7.0     126
    8.0     122
    6.0     116
    16.0    114
    14.0    112
    15.0    105
    10.0    103
    0.0     102
    12.0     85
    11.0     83
    13.0     83
    17.0     75
    23.0     41
    30.0     39
    22.0     36
    31.0     34
    25.0     32
    27.0     31
    32.0     30
    20.0     29
    35.0     29
    26.0     27
    29.0     27
    24.0     27
    28.0     25
    21.0     24
    18.0     23
    33.0     22
    19.0     18
    34.0     18
    36.0     15
    5.0       3
    Name: DurationOfPitch, dtype: int64




```python
plt.figure(dpi=130)

sns.scatterplot(train_enc['DurationOfPitch'],train_enc['MonthlyIncome'])
plt.xlabel('DurationOfPitch')
plt.ylabel('MonthlyIncome')
plt.show()
```

    /usr/local/lib/python3.7/dist-packages/seaborn/_decorators.py:43: FutureWarning: Pass the following variables as keyword args: x, y. From version 0.12, the only valid positional argument will be `data`, and passing other arguments without an explicit keyword will result in an error or misinterpretation.
      FutureWarning



    
![](https://user-images.githubusercontent.com/122070432/216536515-d9c3e1e9-3659-4529-8444-9a7628e94612.png)
    


- 상관관계를 찾기 힘들어보임.

# 2.Feature engineering


```python
plt.figure(figsize=(15,15))
sns.heatmap(train_enc.corr(method='pearson'),annot=True, fmt='.1f', linewidths=.5, cmap='Blues')
```




    <matplotlib.axes._subplots.AxesSubplot at 0x7fac6aa35410>




    
![](https://user-images.githubusercontent.com/122070432/216536517-6a270096-611e-4c7e-86a2-558f1107971e.png)
    


- 새로운 파생변수를 만들고 싶지만 데이터의 타입이 대부분 카테고리형태여서 힘들 것 같다.

## 2.1.drop

- target과 상관관계가 낮은 feature는 설정X


```python
train_enc.corr()['ProdTaken']
```




    id                         -0.048933
    Age                        -0.136257
    TypeofContact              -0.047598
    CityTier                    0.085583
    DurationOfPitch             0.069795
    Occupation                 -0.042101
    Gender                      0.019991
    NumberOfPersonVisiting      0.006483
    NumberOfFollowups           0.102778
    ProductPitched             -0.150399
    PreferredPropertyStar       0.108886
    MaritalStatus               0.169245
    NumberOfTrips               0.060995
    Passport                    0.293726
    PitchSatisfactionScore      0.067736
    OwnCar                     -0.040465
    NumberOfChildrenVisiting    0.010089
    Designation                -0.096041
    MonthlyIncome              -0.077508
    ProdTaken                   1.000000
    Name: ProdTaken, dtype: float64




```python
train_enc.corr()['ProdTaken'].index
```




    Index(['id', 'Age', 'TypeofContact', 'CityTier', 'DurationOfPitch',
           'Occupation', 'Gender', 'NumberOfPersonVisiting', 'NumberOfFollowups',
           'ProductPitched', 'PreferredPropertyStar', 'MaritalStatus',
           'NumberOfTrips', 'Passport', 'PitchSatisfactionScore', 'OwnCar',
           'NumberOfChildrenVisiting', 'Designation', 'MonthlyIncome',
           'ProdTaken'],
          dtype='object')




```python
features = ['Age','CityTier', 'DurationOfPitch',
       'Occupation',  'NumberOfFollowups',
       'ProductPitched', 'PreferredPropertyStar', 'MaritalStatus',
       'NumberOfTrips', 'Passport', 'PitchSatisfactionScore', 'OwnCar',
        'Designation', 'MonthlyIncome',
       'ProdTaken']
features
```




    ['Age',
     'CityTier',
     'DurationOfPitch',
     'Occupation',
     'NumberOfFollowups',
     'ProductPitched',
     'PreferredPropertyStar',
     'MaritalStatus',
     'NumberOfTrips',
     'Passport',
     'PitchSatisfactionScore',
     'OwnCar',
     'Designation',
     'MonthlyIncome',
     'ProdTaken']



## 2.2.scaling


```python
train_enc.head()
```





  <div id="df-ace79a6b-3c2f-4628-8890-3f93de393dd9">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>28.0</td>
      <td>0</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>20384.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>34.0</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19599.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>45.0</td>
      <td>0</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>29.0</td>
      <td>0</td>
      <td>1</td>
      <td>7.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>21274.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>42.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19907.0</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-ace79a6b-3c2f-4628-8890-3f93de393dd9')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-ace79a6b-3c2f-4628-8890-3f93de393dd9 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-ace79a6b-3c2f-4628-8890-3f93de393dd9');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
from sklearn.preprocessing import StandardScaler

scaler = StandardScaler()

train_enc[['MonthlyIncome']] = scaler.fit_transform(train_enc[['MonthlyIncome']])
train_enc.head()
```





  <div id="df-4f32cfb6-3a09-4043-8644-f3eaab0c771c">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>28.0</td>
      <td>0</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>-0.268499</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>34.0</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>-0.372240</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>45.0</td>
      <td>0</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>-2.962326</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>29.0</td>
      <td>0</td>
      <td>1</td>
      <td>7.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>-0.150882</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>42.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>-0.331537</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-4f32cfb6-3a09-4043-8644-f3eaab0c771c')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-4f32cfb6-3a09-4043-8644-f3eaab0c771c button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-4f32cfb6-3a09-4043-8644-f3eaab0c771c');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
train_enc['MonthlyIncome'].hist()
```




    <matplotlib.axes._subplots.AxesSubplot at 0x7fac6ad5add0>




    
![](https://user-images.githubusercontent.com/122070432/216536520-d813293b-f9c1-4b97-82d7-88b5f3de6cf7.png)
    


# 3.모델링을 위한 데이터 전처리

### 0.1.3.(추가) 결측치 처리 모듈 생성
- 0으로 채운 수치형 컬럼의 결측치를 회귀예측 모델로 예측하여 채워넣는 모듈 생성


```python
from sklearn.ensemble import RandomForestRegressor
from sklearn.model_selection import train_test_split
from sklearn.metrics import mean_absolute_error
from sklearn.ensemble import ExtraTreesRegressor
```


```python
def prod_val(feature:str):
  if len(train_enc[train_enc[feature]==0])==0:
    return 'already processed'
  train_temp = train_enc[train_enc[feature]!=0]

  features = train_temp.columns[1:-1].drop(feature)
  target = feature

  X = train_temp[features]
  y = train_temp[target]

  X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,shuffle=False)
  model_rf = ExtraTreesRegressor(n_estimators=300)

  model_rf.fit(X_train,y_train)

  train_predict = model_rf.predict(X_test)

  print(f'{feature} MAE: {mean_absolute_error(train_predict,y_test)}')

  X = train_enc[train_enc[feature]==0][features]

  train_enc.loc[train_enc[feature]==0,feature] = model_rf.predict(X)

  test_temp = test[test[feature]==0]

  X = test_temp[features]

  test.loc[test[feature]==0,feature] = model_rf.predict(X)

  print(f'\ntrain set: \n{train_enc[feature].value_counts().sort_index().head(3)}')
  print(f'\ntest set: \n{test[feature].value_counts().sort_index().head(3)}')
```


```python
train = pd.read_csv('/content/drive/MyDrive/data/여행상품신청/train.csv')
test = pd.read_csv('/content/drive/MyDrive/data/여행상품신청/test.csv')
sample_submission = pd.read_csv('/content/drive/MyDrive/data/여행상품신청/sample_submission.csv')
```

### 3.1.결측치 처리


```python
def handle_na(data):
    temp = data.copy()
    for col, dtype in temp.dtypes.items():
        if dtype == 'object':
            # 문자형 칼럼의 경우 'Unknown'을 채워줍니다.
            value = 'Unknown'
        elif dtype == int or dtype == float:
            # 수치형 칼럼의 경우 0을 채워줍니다.
            value = 0
        temp.loc[:,col] = temp[col].fillna(value)
    return temp

train_nona = handle_na(train)

# 결측치 처리가 잘 되었는지 확인해 줍니다.
train_nona.isna().sum()
```




    id                          0
    Age                         0
    TypeofContact               0
    CityTier                    0
    DurationOfPitch             0
    Occupation                  0
    Gender                      0
    NumberOfPersonVisiting      0
    NumberOfFollowups           0
    ProductPitched              0
    PreferredPropertyStar       0
    MaritalStatus               0
    NumberOfTrips               0
    Passport                    0
    PitchSatisfactionScore      0
    OwnCar                      0
    NumberOfChildrenVisiting    0
    Designation                 0
    MonthlyIncome               0
    ProdTaken                   0
    dtype: int64



## 3.2.문자형 변수 전처리


```python
train_nona.loc[train_nona['Gender']=='Fe Male','Gender'] = 'Female'
test.loc[test['Gender']=='Fe Male','Gender'] = 'Female'
train_nona['Gender'].value_counts()
```




    Male      1207
    Female     748
    Name: Gender, dtype: int64




```python
# LabelEncoder를 준비해줍니다.
from sklearn.preprocessing import LabelEncoder

encoder = LabelEncoder()

# LabelEcoder는 학습하는 과정을 필요로 합니다.
encoder.fit(train_nona['TypeofContact'])

#학습된 encoder를 사용하여 문자형 변수를 숫자로 변환해줍니다.
encoder.transform(train_nona['TypeofContact'])
```




    array([0, 1, 0, ..., 0, 1, 0])




```python
train_enc = train_nona.copy()
object_columns = train_nona.columns[train_nona.dtypes == 'object']
# 모든 문자형 변수에 대해 encoder를 적용합니다.
for o_col in object_columns:
    encoder = LabelEncoder()
    encoder.fit(train_enc[o_col])
    train_enc[o_col] = encoder.transform(train_enc[o_col])

# 결과를 확인합니다.
train_enc
```





  <div id="df-a1a751fc-f2c3-4359-9c5e-73626287fb98">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>28.0</td>
      <td>0</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>20384.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>34.0</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19599.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>45.0</td>
      <td>0</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>0.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>29.0</td>
      <td>0</td>
      <td>1</td>
      <td>7.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>21274.0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>42.0</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>19907.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>1951</td>
      <td>28.0</td>
      <td>1</td>
      <td>1</td>
      <td>10.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>20723.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>1952</td>
      <td>41.0</td>
      <td>1</td>
      <td>3</td>
      <td>8.0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>3.0</td>
      <td>4</td>
      <td>5.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>31595.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>1953</td>
      <td>38.0</td>
      <td>0</td>
      <td>3</td>
      <td>28.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>7.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>2.0</td>
      <td>1</td>
      <td>21651.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>1954</td>
      <td>28.0</td>
      <td>1</td>
      <td>3</td>
      <td>30.0</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2.0</td>
      <td>2</td>
      <td>22218.0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>1955</td>
      <td>22.0</td>
      <td>0</td>
      <td>1</td>
      <td>9.0</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>0.0</td>
      <td>1</td>
      <td>17853.0</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
<p>1955 rows × 20 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-a1a751fc-f2c3-4359-9c5e-73626287fb98')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-a1a751fc-f2c3-4359-9c5e-73626287fb98 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-a1a751fc-f2c3-4359-9c5e-73626287fb98');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
# 결측치 처리
test = handle_na(test)

# 문자형 변수 전처리
for o_col in object_columns:
    encoder = LabelEncoder()
    
    # test 데이터를 이용해 encoder를 학습하는 것은 Data Leakage 입니다! 조심!
    encoder.fit(train_nona[o_col])
    
    # test 데이터는 오로지 transform 에서만 사용되어야 합니다.
    test[o_col] = encoder.transform(test[o_col])

# 결과를 확인합니다.
test
```





  <div id="df-75deace8-28ff-43ec-97e1-0165baf8e365">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>32.0</td>
      <td>0</td>
      <td>3</td>
      <td>0.0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>2</td>
      <td>0</td>
      <td>1.0</td>
      <td>2</td>
      <td>19668.0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>46.0</td>
      <td>1</td>
      <td>2</td>
      <td>11.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>0.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>0</td>
      <td>1.0</td>
      <td>2</td>
      <td>20021.0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>37.0</td>
      <td>1</td>
      <td>3</td>
      <td>22.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>5.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>0.0</td>
      <td>2</td>
      <td>21334.0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>43.0</td>
      <td>1</td>
      <td>1</td>
      <td>36.0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>6.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>3</td>
      <td>6.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>2.0</td>
      <td>2</td>
      <td>22950.0</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>25.0</td>
      <td>1</td>
      <td>3</td>
      <td>7.0</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
      <td>4.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>3</td>
      <td>3.0</td>
      <td>1</td>
      <td>4</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>21880.0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2928</th>
      <td>2929</td>
      <td>54.0</td>
      <td>1</td>
      <td>1</td>
      <td>6.0</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>3.0</td>
      <td>4</td>
      <td>3.0</td>
      <td>2</td>
      <td>7.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>0</td>
      <td>32328.0</td>
    </tr>
    <tr>
      <th>2929</th>
      <td>2930</td>
      <td>33.0</td>
      <td>1</td>
      <td>1</td>
      <td>9.0</td>
      <td>3</td>
      <td>0</td>
      <td>4</td>
      <td>2.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>3</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>1.0</td>
      <td>2</td>
      <td>23733.0</td>
    </tr>
    <tr>
      <th>2930</th>
      <td>2931</td>
      <td>33.0</td>
      <td>0</td>
      <td>1</td>
      <td>31.0</td>
      <td>2</td>
      <td>1</td>
      <td>4</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>23987.0</td>
    </tr>
    <tr>
      <th>2931</th>
      <td>2932</td>
      <td>26.0</td>
      <td>1</td>
      <td>1</td>
      <td>9.0</td>
      <td>3</td>
      <td>1</td>
      <td>4</td>
      <td>2.0</td>
      <td>0</td>
      <td>5.0</td>
      <td>3</td>
      <td>2.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>22102.0</td>
    </tr>
    <tr>
      <th>2932</th>
      <td>2933</td>
      <td>31.0</td>
      <td>1</td>
      <td>1</td>
      <td>9.0</td>
      <td>2</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>22830.0</td>
    </tr>
  </tbody>
</table>
<p>2933 rows × 19 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-75deace8-28ff-43ec-97e1-0165baf8e365')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-75deace8-28ff-43ec-97e1-0165baf8e365 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-75deace8-28ff-43ec-97e1-0165baf8e365');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>




## 3.3.결측치 처리 모듈 적용


```python
prod_val('Age')
```

    Age MAE: 5.071390374331551
    
    train set: 
    18.0     5
    19.0    16
    20.0    13
    Name: Age, dtype: int64
    
    test set: 
    18.0     9
    19.0    16
    20.0    25
    Name: Age, dtype: int64



```python
train_enc['Age'] = train_enc['Age'].round()
test['Age'] = test['Age'].round()
train_enc['Age'].unique()
```




    array([28., 34., 45., 29., 42., 32., 43., 36., 35., 31., 49., 33., 52.,
           22., 50., 23., 41., 37., 40., 56., 54., 39., 20., 38., 46., 27.,
           25., 26., 24., 30., 21., 51., 47., 55., 44., 53., 48., 18., 57.,
           60., 59., 19., 58., 61.])




```python
prod_val('MonthlyIncome')
```

    MonthlyIncome MAE: 1205.1359677419355
    
    train set: 
    1000.00     1
    14781.04    1
    16009.00    1
    Name: MonthlyIncome, dtype: int64
    
    test set: 
    2196.853333     1
    4678.000000     1
    14921.480000    1
    Name: MonthlyIncome, dtype: int64



```python
prod_val('DurationOfPitch')
```

    DurationOfPitch MAE: 6.203512544802866
    
    train set: 
    5.0      3
    6.0    116
    7.0    126
    Name: DurationOfPitch, dtype: int64
    
    test set: 
    5.0      3
    6.0    191
    7.0    216
    Name: DurationOfPitch, dtype: int64



```python
train_enc['DurationOfPitch'] = train_enc['DurationOfPitch'].round()
test['DurationOfPitch'] = test['DurationOfPitch'].round()
test['DurationOfPitch'].unique()
```




    array([ 16.,  11.,  22.,  36.,   7.,   8.,   6.,  29.,   9.,  12.,  13.,
            17.,  15.,  10.,  14.,  35.,  24.,  31.,  21.,  19.,  32.,  27.,
            18.,  33.,  30.,  26.,  34.,  23.,  20.,  28.,  25., 126.,   5.,
           127.])




```python
prod_val('NumberOfTrips')
```

    NumberOfTrips MAE: 1.2843333333333333
    
    train set: 
    1.000000    234
    1.963333      1
    2.000000    594
    Name: NumberOfTrips, dtype: int64
    
    test set: 
    1.000000    386
    2.000000    870
    2.856667      1
    Name: NumberOfTrips, dtype: int64



```python
train_enc['NumberOfTrips'] = train_enc['NumberOfTrips'].round()
test['NumberOfTrips'] = test['NumberOfTrips'].round()
test['NumberOfTrips'].unique()
```




    array([ 1.,  5.,  6.,  3.,  7.,  4.,  2.,  8., 22., 21., 20.])




```python
prod_val('NumberOfFollowups')
```

    NumberOfFollowups MAE: 0.605094017094017
    
    train set: 
    1.000000    74
    2.000000    89
    2.436667     1
    Name: NumberOfFollowups, dtype: int64
    
    test set: 
    1.000000    102
    2.000000    140
    2.706667      1
    Name: NumberOfFollowups, dtype: int64



```python
train_enc['NumberOfFollowups'] = train_enc['NumberOfFollowups'].round()
test['NumberOfFollowups'] = test['NumberOfFollowups'].round()
test['NumberOfFollowups'].unique()
```




    array([5., 3., 4., 6., 1., 2.])




```python
prod_val('PreferredPropertyStar')
```

    PreferredPropertyStar MAE: 0.6105299145299146
    
    train set: 
    3.000000    1212
    3.126667       1
    3.353333       1
    Name: PreferredPropertyStar, dtype: int64
    
    test set: 
    3.000000    1781
    3.033333       1
    3.100000       1
    Name: PreferredPropertyStar, dtype: int64



```python
train_enc['PreferredPropertyStar'] = train_enc['PreferredPropertyStar'].round()
test['PreferredPropertyStar'] = test['PreferredPropertyStar'].round()
test['PreferredPropertyStar'].unique()
```




    array([3., 4., 5.])




```python
prod_val('NumberOfChildrenVisiting')
```

    NumberOfChildrenVisiting MAE: 0.5187938596491228
    
    train set: 
    1.000000    1099
    1.003333       6
    1.030000       1
    Name: NumberOfChildrenVisiting, dtype: int64
    
    test set: 
    1.000000    1712
    1.003333       3
    1.043333       1
    Name: NumberOfChildrenVisiting, dtype: int64



```python
train_enc['NumberOfChildrenVisiting'] = train_enc['NumberOfChildrenVisiting'].round()
test['NumberOfChildrenVisiting'] = test['NumberOfChildrenVisiting'].round()
test['NumberOfChildrenVisiting'].unique()
```




    array([1., 2., 3.])



## 3.3.(모듈대체) Age가 0인 데이터 age 예측

- age의 결측치를 0으로 채웠었는데 다른 feature들로 age를 예측해봐야겠다고 생각했다.


```python
# temp = train_enc[train_enc['Age']!=0]
# temp.head()
```


```python
# features = temp.columns[2:-1]
# target = 'Age'
```


```python
# from sklearn.ensemble import RandomForestRegressor
# from sklearn.model_selection import train_test_split
# from sklearn.metrics import mean_absolute_error

# X = temp[features]
# y = temp[target]

# X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,shuffle=False)
# model_rf = ExtraTreesRegressor(n_estimators=500)

# model_rf.fit(X_train,y_train)

# predict = model_rf.predict(X_test)
```


```python
# mean_absolute_error(predict,y_test)
```


```python
# temp = train_enc[train_enc['Age']==0]
# temp.head()
```


```python
# X = temp[features]

# train_enc.loc[train_enc['Age']==0,'Age'] = model_rf.predict(X)
```


```python
# train_enc['Age'].value_counts().sort_index()
```


```python
# temp = test[test['Age']==0]
# temp.head()
```


```python
# X = temp[features]

# test.loc[test['Age']==0,'Age'] = model_rf.predict(X)
```


```python
# test['Age'].value_counts().sort_index()
```

## 3.4.(모듈대체) Monthly Income 결측치 예측
- Age와 같은 방법으로 Monthly Income을 예측해보자


```python
# temp = train_enc[train_enc['MonthlyIncome']!=0]
# temp.head()
```


```python
# features = temp.columns[1:-1].drop('MonthlyIncome')
# target = 'MonthlyIncome'
# features
```


```python
# from sklearn.ensemble import ExtraTreesRegressor

# X = temp[features]
# y = temp[target]

# X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,shuffle=False)
# model_rf = ExtraTreesRegressor(n_estimators=500)

# model_rf.fit(X_train,y_train)

# predict = model_rf.predict(X_test)
# mean_absolute_error(predict,y_test)
```


```python
# temp = train_enc[train_enc['MonthlyIncome']==0]
# temp.head()
```


```python
# X = temp[features]

# train_enc.loc[train_enc['MonthlyIncome']==0,'MonthlyIncome'] = model_rf.predict(X)
```


```python
# train_enc['MonthlyIncome'].value_counts().sort_index()
```


```python
# temp = test[test['MonthlyIncome']==0]
# temp.head()
```


```python
# X = temp[features]

# test.loc[test['MonthlyIncome']==0,'MonthlyIncome'] = model_rf.predict(X)
```


```python
# test['MonthlyIncome'].value_counts().sort_index()
```

## 3.4.scaling


```python
from sklearn.preprocessing import StandardScaler
from sklearn.preprocessing import MinMaxScaler

scaler = StandardScaler()
min_scaler = MinMaxScaler()

train_enc[['MonthlyIncome']] = scaler.fit_transform(train_enc[['MonthlyIncome']])
test[['MonthlyIncome']] = scaler.transform(test[['MonthlyIncome']])

train_enc[['Age','DurationOfPitch']] = min_scaler.fit_transform(train_enc[['Age','DurationOfPitch']])
test[['Age','DurationOfPitch']] = min_scaler.transform(test[['Age','DurationOfPitch']])

train_enc.head()
```





  <div id="df-227d7b8c-32be-442d-97c9-152a68bfb78e">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>Age</th>
      <th>TypeofContact</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>Gender</th>
      <th>NumberOfPersonVisiting</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>NumberOfChildrenVisiting</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>0.232558</td>
      <td>0</td>
      <td>1</td>
      <td>0.161290</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>-0.543896</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>0.372093</td>
      <td>1</td>
      <td>3</td>
      <td>0.516129</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>-0.684698</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>0.627907</td>
      <td>0</td>
      <td>1</td>
      <td>0.322581</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>-0.697951</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>0.255814</td>
      <td>0</td>
      <td>1</td>
      <td>0.064516</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>-0.384262</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>0.558140</td>
      <td>1</td>
      <td>3</td>
      <td>0.032258</td>
      <td>2</td>
      <td>1</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>1.0</td>
      <td>2</td>
      <td>-0.629453</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-227d7b8c-32be-442d-97c9-152a68bfb78e')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-227d7b8c-32be-442d-97c9-152a68bfb78e button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-227d7b8c-32be-442d-97c9-152a68bfb78e');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>




# 4.모델링


```python
features = ['Age','CityTier', 'DurationOfPitch',
       'Occupation',  'NumberOfFollowups',
       'ProductPitched', 'PreferredPropertyStar', 'MaritalStatus',
       'NumberOfTrips', 'Passport', 'PitchSatisfactionScore', 'OwnCar',
        'Designation', 'MonthlyIncome']
features
```




    ['Age',
     'CityTier',
     'DurationOfPitch',
     'Occupation',
     'NumberOfFollowups',
     'ProductPitched',
     'PreferredPropertyStar',
     'MaritalStatus',
     'NumberOfTrips',
     'Passport',
     'PitchSatisfactionScore',
     'OwnCar',
     'Designation',
     'MonthlyIncome']



## 4.1.RandomForestClassifier


```python
from sklearn.ensemble import RandomForestClassifier
from sklearn.metrics import accuracy_score

X = train_enc[features]
y = train_enc['ProdTaken']

X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,random_state=42,shuffle=False)

rf_model = RandomForestClassifier()

rf_model.fit(X_train,y_train)

y_pred_rf = rf_model.predict(X_test)

print(accuracy_score(y_pred_rf,y_test))
```

    0.8826530612244898


## 4.2.Xgboost


```python
from xgboost import XGBClassifier

X = train_enc[features]
y = train_enc['ProdTaken']

X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,random_state=42,shuffle=False)

xgb_model = XGBClassifier()

xgb_model.fit(X_train,y_train)

y_pred_xgb = xgb_model.predict(X_test)

print(accuracy_score(y_pred_xgb,y_test))
```

    0.8673469387755102


## 4.3.catboost


```python
!pip install catboost
```


```python
X
```





  <div id="df-2d465ff9-cb36-4dc1-8bf9-5cefa56320d7">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Age</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0.232558</td>
      <td>1</td>
      <td>0.161290</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1</td>
      <td>-0.543896</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0.372093</td>
      <td>3</td>
      <td>0.516129</td>
      <td>3</td>
      <td>4.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>2</td>
      <td>1.0</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>2</td>
      <td>-0.684698</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0.627907</td>
      <td>1</td>
      <td>0.322581</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>4.0</td>
      <td>1</td>
      <td>2.0</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>2</td>
      <td>-0.697951</td>
    </tr>
    <tr>
      <th>3</th>
      <td>0.255814</td>
      <td>1</td>
      <td>0.064516</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>4.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1</td>
      <td>-0.384262</td>
    </tr>
    <tr>
      <th>4</th>
      <td>0.558140</td>
      <td>3</td>
      <td>0.032258</td>
      <td>2</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>2.0</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>-0.629453</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>0.232558</td>
      <td>1</td>
      <td>0.161290</td>
      <td>3</td>
      <td>5.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>2</td>
      <td>2.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>-0.483092</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>0.534884</td>
      <td>3</td>
      <td>0.096774</td>
      <td>2</td>
      <td>3.0</td>
      <td>4</td>
      <td>5.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>0</td>
      <td>1.466964</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>0.465116</td>
      <td>3</td>
      <td>0.741935</td>
      <td>3</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>7.0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>-0.316641</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>0.232558</td>
      <td>3</td>
      <td>0.806452</td>
      <td>3</td>
      <td>5.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>1</td>
      <td>3.0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2</td>
      <td>-0.214941</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>0.093023</td>
      <td>1</td>
      <td>0.129032</td>
      <td>2</td>
      <td>4.0</td>
      <td>0</td>
      <td>3.0</td>
      <td>0</td>
      <td>1.0</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>1</td>
      <td>-0.997869</td>
    </tr>
  </tbody>
</table>
<p>1955 rows × 14 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-2d465ff9-cb36-4dc1-8bf9-5cefa56320d7')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-2d465ff9-cb36-4dc1-8bf9-5cefa56320d7 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-2d465ff9-cb36-4dc1-8bf9-5cefa56320d7');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
cat_features = [1,3,10,11,12]
```


```python
train_enc[features].info()
```

    <class 'pandas.core.frame.DataFrame'>
    RangeIndex: 1955 entries, 0 to 1954
    Data columns (total 14 columns):
     #   Column                  Non-Null Count  Dtype  
    ---  ------                  --------------  -----  
     0   Age                     1955 non-null   float64
     1   CityTier                1955 non-null   int64  
     2   DurationOfPitch         1955 non-null   float64
     3   Occupation              1955 non-null   int64  
     4   NumberOfFollowups       1955 non-null   float64
     5   ProductPitched          1955 non-null   int64  
     6   PreferredPropertyStar   1955 non-null   float64
     7   MaritalStatus           1955 non-null   int64  
     8   NumberOfTrips           1955 non-null   float64
     9   Passport                1955 non-null   int64  
     10  PitchSatisfactionScore  1955 non-null   int64  
     11  OwnCar                  1955 non-null   int64  
     12  Designation             1955 non-null   int64  
     13  MonthlyIncome           1955 non-null   float64
    dtypes: float64(6), int64(8)
    memory usage: 214.0 KB



```python
train_enc = train_enc.astype({'DurationOfPitch':'int','NumberOfFollowups':'int','PreferredPropertyStar':'int','NumberOfTrips':'int'})
train_enc[features].dtypes
```




    Age                       float64
    CityTier                    int64
    DurationOfPitch             int64
    Occupation                  int64
    NumberOfFollowups           int64
    ProductPitched              int64
    PreferredPropertyStar       int64
    MaritalStatus               int64
    NumberOfTrips               int64
    Passport                    int64
    PitchSatisfactionScore      int64
    OwnCar                      int64
    Designation                 int64
    MonthlyIncome             float64
    dtype: object




```python
from catboost import CatBoostClassifier

X = train_enc[features]
y = train_enc['ProdTaken']

X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,random_state=42,shuffle=False)

cat_model = CatBoostClassifier()

cat_model.fit(X_train,y_train,
          eval_set=(X_test,y_test),
          cat_features=cat_features,
          use_best_model=True,
          verbose=True
          )

y_pred_cat = cat_model.predict(X_test)
```


```python
print(accuracy_score(y_pred_cat,y_test))
```

    0.8826530612244898


## 4.4.LightGBM


```python
from lightgbm import LGBMClassifier

X = train_enc[features]
y = train_enc['ProdTaken']

X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,random_state=42,shuffle=False)

lgbm_model = LGBMClassifier()

lgbm_model.fit(X_train,y_train)

y_pred_lgbm = lgbm_model.predict(X_test)

print(accuracy_score(y_pred_lgbm,y_test))
```

    0.8724489795918368


## 4.5.ExtraTrees


```python
from sklearn.ensemble import ExtraTreesClassifier

X = train_enc[features]
y = train_enc['ProdTaken']

X_train,X_test,y_train,y_test = train_test_split(X,y,train_size=0.9,random_state=42,shuffle=False)

et_model = ExtraTreesClassifier()

et_model.fit(X_train,y_train)

y_pred_et = et_model.predict(X_test)

print(accuracy_score(y_pred_et,y_test))
```

    0.8979591836734694


# 5.모델 앙상블


```python
from sklearn.model_selection import GridSearchCV
```


```python
train_enc.dtypes
```




    id                            int64
    Age                         float64
    TypeofContact                 int64
    CityTier                      int64
    DurationOfPitch               int64
    Occupation                    int64
    Gender                        int64
    NumberOfPersonVisiting        int64
    NumberOfFollowups             int64
    ProductPitched                int64
    PreferredPropertyStar         int64
    MaritalStatus                 int64
    NumberOfTrips                 int64
    Passport                      int64
    PitchSatisfactionScore        int64
    OwnCar                        int64
    NumberOfChildrenVisiting    float64
    Designation                   int64
    MonthlyIncome               float64
    ProdTaken                     int64
    dtype: object




```python
X = train_enc[features]
y = train_enc['ProdTaken']
```


```python
X
```





  <div id="df-70ce2c92-ee70-4dcb-b58e-d886df95dc91">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>Age</th>
      <th>CityTier</th>
      <th>DurationOfPitch</th>
      <th>Occupation</th>
      <th>NumberOfFollowups</th>
      <th>ProductPitched</th>
      <th>PreferredPropertyStar</th>
      <th>MaritalStatus</th>
      <th>NumberOfTrips</th>
      <th>Passport</th>
      <th>PitchSatisfactionScore</th>
      <th>OwnCar</th>
      <th>Designation</th>
      <th>MonthlyIncome</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0.232558</td>
      <td>1</td>
      <td>0</td>
      <td>3</td>
      <td>4</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>1</td>
      <td>-0.543896</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0.372093</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4</td>
      <td>1</td>
      <td>4</td>
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>5</td>
      <td>1</td>
      <td>2</td>
      <td>-0.684698</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0.627907</td>
      <td>1</td>
      <td>0</td>
      <td>2</td>
      <td>3</td>
      <td>1</td>
      <td>4</td>
      <td>1</td>
      <td>2</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>2</td>
      <td>-0.697951</td>
    </tr>
    <tr>
      <th>3</th>
      <td>0.255814</td>
      <td>1</td>
      <td>0</td>
      <td>3</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1</td>
      <td>-0.384262</td>
    </tr>
    <tr>
      <th>4</th>
      <td>0.558140</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>0</td>
      <td>3</td>
      <td>1</td>
      <td>2</td>
      <td>-0.629453</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>1950</th>
      <td>0.232558</td>
      <td>1</td>
      <td>0</td>
      <td>3</td>
      <td>5</td>
      <td>0</td>
      <td>3</td>
      <td>2</td>
      <td>2</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>-0.483092</td>
    </tr>
    <tr>
      <th>1951</th>
      <td>0.534884</td>
      <td>3</td>
      <td>0</td>
      <td>2</td>
      <td>3</td>
      <td>4</td>
      <td>5</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>0</td>
      <td>1.466964</td>
    </tr>
    <tr>
      <th>1952</th>
      <td>0.465116</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>4</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>7</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>-0.316641</td>
    </tr>
    <tr>
      <th>1953</th>
      <td>0.232558</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>5</td>
      <td>1</td>
      <td>3</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>2</td>
      <td>-0.214941</td>
    </tr>
    <tr>
      <th>1954</th>
      <td>0.093023</td>
      <td>1</td>
      <td>0</td>
      <td>2</td>
      <td>4</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>1</td>
      <td>-0.997869</td>
    </tr>
  </tbody>
</table>
<p>1955 rows × 14 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-70ce2c92-ee70-4dcb-b58e-d886df95dc91')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-70ce2c92-ee70-4dcb-b58e-d886df95dc91 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-70ce2c92-ee70-4dcb-b58e-d886df95dc91');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
models = []

rf = RandomForestClassifier()

models.append(rf)

xgb = XGBClassifier()

models.append(xgb)

cat = CatBoostClassifier()

models.append(cat)

lgbm = LGBMClassifier()

models.append(lgbm)

et = ExtraTreesClassifier()

models.append(et)
```


```python
param_rf = {
    'max_depth': [80, 100,None],
    'min_samples_leaf': [1,3],
    'min_samples_split': [2,8, 10],
    'n_estimators': [100, 300, 500,700],
    'criterion': ['gini','entropy']
}

param_xgb = {
        'learning_rate': [0.05, 0.1],
        'min_child_weight': [1, 5, 10],
        'n_estimators': [100, 300,500,700],
        'gamma': [0,0.5, 1],
        #'subsample': [0.6,  1.0],
        #'colsample_bytree': [0.6, 1.0],
        'max_depth': [3, 4, 5]
        }

param_cat = {
        #'iterations':[600,None],
        'learning_rate': [0.05, 0.1,None],
        'depth': [4, 6,None],
        'l2_leaf_reg': [1, 3, 5,None],
        'cat_features' : [cat_features]
        
        }

param_lgbm = {
    'learning_rate': [0.05, 0.1],
    'n_estimators': [100,  300, 500],
    #'num_leaves': [6,16,31], # large num_leaves helps improve accuracy but might lead to over-fitting
    'boosting_type' : ['dart','gbdt'], # for better accuracy -> try dart ['gbdt'] drop함 
    'objective' : ['binary'],
    #'colsample_bytree' : [0.64, 0.66,1],
    #'subsample' : [0.7,0.75,1],
    'reg_alpha' : [1,1.2,0],
    'reg_lambda' : [1,1.4,0],
    }
param_et = {
    'max_depth': [80, 90, 100,None],
    'min_samples_leaf': [1,2,3],
    #'min_samples_split': [2,10, 12],
    'n_estimators': [100,  300, 500,700],
    'criterion': ['gini','entropy']
}
```


```python
params = []
params.append(param_rf)
params.append(param_xgb)
params.append(param_cat)
params.append(param_lgbm)
params.append(param_et)
params
```




    [{'max_depth': [80, 100, None],
      'min_samples_leaf': [1, 3],
      'min_samples_split': [2, 8, 10],
      'n_estimators': [100, 300, 500, 700],
      'criterion': ['gini', 'entropy']},
     {'learning_rate': [0.05, 0.1],
      'min_child_weight': [1, 5, 10],
      'n_estimators': [100, 300, 500, 700],
      'gamma': [0, 0.5, 1],
      'max_depth': [3, 4, 5]},
     {'learning_rate': [0.05, 0.1, None],
      'depth': [4, 6, None],
      'l2_leaf_reg': [1, 3, 5, None],
      'cat_features': [[1, 3, 10, 11, 12]]},
     {'learning_rate': [0.05, 0.1],
      'n_estimators': [100, 300, 500],
      'boosting_type': ['dart', 'gbdt'],
      'objective': ['binary'],
      'reg_alpha': [1, 1.2, 0],
      'reg_lambda': [1, 1.4, 0]},
     {'max_depth': [80, 90, 100, None],
      'min_samples_leaf': [1, 2, 3],
      'n_estimators': [100, 300, 500, 700],
      'criterion': ['gini', 'entropy']}]




```python
# best_models = {}

# models = GridSearchCV(models[0],param_grid = params[0], cv=7, return_train_score = True, verbose=2)

# models.fit(X,y)

# best_models[0] = models[0].best_estimator_
```


```python
# models = GridSearchCV(models[1],param_grid = params[1], cv=7, return_train_score = True, verbose=2)

# models.fit(X,y)

# best_models[1] = models[1].best_estimator_
```


```python
# models = GridSearchCV(models[2],param_grid = params[2], cv=7, return_train_score = True, verbose=10)

# models.fit(X,y)

# best_models[2] = models[2].best_estimator_
```


```python
# models = GridSearchCV(models[3],param_grid = params[3], cv=7, return_train_score = True, verbose=2)

# models.fit(X,y)

# best_models[3] = models[3].best_estimator_
```


```python
# models = GridSearchCV(models[4],param_grid = params[4], cv=7, return_train_score = True, verbose=2)

# models.fit(X,y)

# best_models[4] = models[4].best_estimator_
```


```python
best_models = {}

for i,model in enumerate(models):
  model = GridSearchCV(model,param_grid = params[i], cv=7, return_train_score = True, verbose=2)

  model.fit(X,y)

  best_models[i] = model.best_estimator_
```


```python
test = test.astype({'DurationOfPitch':'int','NumberOfFollowups':'int','PreferredPropertyStar':'int','NumberOfTrips':'int'})
test[features].dtypes
```




    Age                       float64
    CityTier                    int64
    DurationOfPitch             int64
    Occupation                  int64
    NumberOfFollowups           int64
    ProductPitched              int64
    PreferredPropertyStar       int64
    MaritalStatus               int64
    NumberOfTrips               int64
    Passport                    int64
    PitchSatisfactionScore      int64
    OwnCar                      int64
    Designation                 int64
    MonthlyIncome             float64
    dtype: object




```python
best_models
```




    {0: RandomForestClassifier(n_estimators=700),
     1: XGBClassifier(learning_rate=0.05, max_depth=5, n_estimators=700),
     2: <catboost.core.CatBoostClassifier at 0x7f2430a31e90>,
     3: LGBMClassifier(n_estimators=500, objective='binary', reg_alpha=0, reg_lambda=0),
     4: ExtraTreesClassifier(max_depth=100, n_estimators=300)}




```python
pred0 = best_models[0].predict(test[features])
pred1 = best_models[1].predict(test[features])
pred2 = best_models[2].predict(test[features])
pred3 = best_models[3].predict(test[features])
pred4 = best_models[4].predict(test[features])
pred = pd.DataFrame({'pred0':pred0,'pred1':pred1,'pred2':pred2,'pred3':pred3,'pred4':pred4})
pred.head()
```





  <div id="df-67bf7451-851a-4e38-b999-b7cda7fea34c">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>pred0</th>
      <th>pred1</th>
      <th>pred2</th>
      <th>pred3</th>
      <th>pred4</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>4</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-67bf7451-851a-4e38-b999-b7cda7fea34c')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-67bf7451-851a-4e38-b999-b7cda7fea34c button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-67bf7451-851a-4e38-b999-b7cda7fea34c');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
pred
```





  <div id="df-41a58e76-0ee4-4929-8bb1-8b7ef0580e4d">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>pred0</th>
      <th>pred1</th>
      <th>pred2</th>
      <th>pred3</th>
      <th>pred4</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>4</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2928</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2929</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2930</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2931</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2932</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>2933 rows × 5 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-41a58e76-0ee4-4929-8bb1-8b7ef0580e4d')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-41a58e76-0ee4-4929-8bb1-8b7ef0580e4d button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-41a58e76-0ee4-4929-8bb1-8b7ef0580e4d');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
pred['pred3'].value_counts()
```




    0    2511
    1     422
    Name: pred3, dtype: int64




```python
pred['pred'] = pred.mode(axis=1)[0].astype(int)
pred
```





  <div id="df-741fa627-7e70-4b27-a38c-206057f0e539">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>pred0</th>
      <th>pred1</th>
      <th>pred2</th>
      <th>pred3</th>
      <th>pred4</th>
      <th>pred</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>4</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2928</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2929</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2930</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2931</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2932</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>2933 rows × 6 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-741fa627-7e70-4b27-a38c-206057f0e539')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-741fa627-7e70-4b27-a38c-206057f0e539 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-741fa627-7e70-4b27-a38c-206057f0e539');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
pred[pred['pred4']!= pred['pred']]
```





  <div id="df-6204fc36-ae68-4e56-868a-48b90283b5d5">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>pred0</th>
      <th>pred1</th>
      <th>pred2</th>
      <th>pred3</th>
      <th>pred4</th>
      <th>pred</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>7</th>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>22</th>
      <td>1</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>70</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>83</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>114</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2830</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2869</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2890</th>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>1</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>2908</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2909</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>118 rows × 6 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-6204fc36-ae68-4e56-868a-48b90283b5d5')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-6204fc36-ae68-4e56-868a-48b90283b5d5 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-6204fc36-ae68-4e56-868a-48b90283b5d5');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
sample_submission['ProdTaken'] = pred['pred']
sample_submission.to_csv('submission1.csv',index=False)
```


```python
sample_submission['ProdTaken'] = pred['pred4']
sample_submission.to_csv('submission3.csv',index=False)
```


```python
pred0 = best_models[0].predict_proba(test[features])
pred1 = best_models[1].predict_proba(test[features])
pred2 = best_models[2].predict_proba(test[features])
pred3 = best_models[3].predict_proba(test[features])
pred4 = best_models[4].predict_proba(test[features])
pred = pd.DataFrame(pred0+pred1+pred2+pred3+pred4/5)
pred.head()
```





  <div id="df-862de30b-c3ff-45a5-847c-065b6c884085">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>0</th>
      <th>1</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>3.278279</td>
      <td>0.921721</td>
    </tr>
    <tr>
      <th>1</th>
      <td>3.971699</td>
      <td>0.228301</td>
    </tr>
    <tr>
      <th>2</th>
      <td>4.168868</td>
      <td>0.031132</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4.053194</td>
      <td>0.146806</td>
    </tr>
    <tr>
      <th>4</th>
      <td>0.285364</td>
      <td>3.914636</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-862de30b-c3ff-45a5-847c-065b6c884085')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-862de30b-c3ff-45a5-847c-065b6c884085 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-862de30b-c3ff-45a5-847c-065b6c884085');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
import numpy as np
pred['pred'] = pd.DataFrame(np.argmax(np.array(pred),axis =1 ))
pred
```





  <div id="df-52cb9b56-350d-427a-a15c-f7a60ba7281a">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>0</th>
      <th>1</th>
      <th>pred</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>3.278279</td>
      <td>0.921721</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>3.971699</td>
      <td>0.228301</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>4.168868</td>
      <td>0.031132</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4.053194</td>
      <td>0.146806</td>
      <td>0</td>
    </tr>
    <tr>
      <th>4</th>
      <td>0.285364</td>
      <td>3.914636</td>
      <td>1</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2928</th>
      <td>4.167008</td>
      <td>0.032992</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2929</th>
      <td>4.162987</td>
      <td>0.037013</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2930</th>
      <td>3.921309</td>
      <td>0.278691</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2931</th>
      <td>3.899447</td>
      <td>0.300553</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2932</th>
      <td>4.135267</td>
      <td>0.064733</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>2933 rows × 3 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-52cb9b56-350d-427a-a15c-f7a60ba7281a')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-52cb9b56-350d-427a-a15c-f7a60ba7281a button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-52cb9b56-350d-427a-a15c-f7a60ba7281a');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
sample_submission['ProdTaken'] = pred['pred']
sample_submission.to_csv('submission2.csv',index=False)
```


```python
estimators =[
    ('rf', best_models[0]),
    ('xgb', best_models[1]),
    ('cat', best_models[2]),
    ('lgbm', best_models[3]),
    ('et', best_models[4])
]
```


```python
from sklearn.ensemble import VotingClassifier

model = VotingClassifier(estimators = estimators, voting='soft')
model.fit(X,y)
```


```python
model.predict(test[features])
```




    array([0, 0, 0, ..., 0, 0, 0])




```python
model.score(X,y)
```




    1.0




```python
sample_submission['ProdTaken'] = pd.DataFrame(model.predict(test[features]))
sample_submission
```





  <div id="df-0209f350-6b5b-462f-88ec-6d2b3180b010">
    <div class="colab-df-container">
      <div>
<style scoped>
    .dataframe tbody tr th:only-of-type {
        vertical-align: middle;
    }

    .dataframe tbody tr th {
        vertical-align: top;
    }

    .dataframe thead th {
        text-align: right;
    }
</style>
<table border="1" class="dataframe">
  <thead>
    <tr style="text-align: right;">
      <th></th>
      <th>id</th>
      <th>ProdTaken</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>1</th>
      <td>2</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3</td>
      <td>0</td>
    </tr>
    <tr>
      <th>3</th>
      <td>4</td>
      <td>0</td>
    </tr>
    <tr>
      <th>4</th>
      <td>5</td>
      <td>1</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>2928</th>
      <td>2929</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2929</th>
      <td>2930</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2930</th>
      <td>2931</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2931</th>
      <td>2932</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2932</th>
      <td>2933</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
<p>2933 rows × 2 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-0209f350-6b5b-462f-88ec-6d2b3180b010')"
              title="Convert this dataframe to an interactive table."
              style="display:none;">

  <svg xmlns="http://www.w3.org/2000/svg" height="24px"viewBox="0 0 24 24"
       width="24px">
    <path d="M0 0h24v24H0V0z" fill="none"/>
    <path d="M18.56 5.44l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94zm-11 1L8.5 8.5l.94-2.06 2.06-.94-2.06-.94L8.5 2.5l-.94 2.06-2.06.94zm10 10l.94 2.06.94-2.06 2.06-.94-2.06-.94-.94-2.06-.94 2.06-2.06.94z"/><path d="M17.41 7.96l-1.37-1.37c-.4-.4-.92-.59-1.43-.59-.52 0-1.04.2-1.43.59L10.3 9.45l-7.72 7.72c-.78.78-.78 2.05 0 2.83L4 21.41c.39.39.9.59 1.41.59.51 0 1.02-.2 1.41-.59l7.78-7.78 2.81-2.81c.8-.78.8-2.07 0-2.86zM5.41 20L4 18.59l7.72-7.72 1.47 1.35L5.41 20z"/>
  </svg>
      </button>

  <style>
    .colab-df-container {
      display:flex;
      flex-wrap:wrap;
      gap: 12px;
    }

    .colab-df-convert {
      background-color: #E8F0FE;
      border: none;
      border-radius: 50%;
      cursor: pointer;
      display: none;
      fill: #1967D2;
      height: 32px;
      padding: 0 0 0 0;
      width: 32px;
    }

    .colab-df-convert:hover {
      background-color: #E2EBFA;
      box-shadow: 0px 1px 2px rgba(60, 64, 67, 0.3), 0px 1px 3px 1px rgba(60, 64, 67, 0.15);
      fill: #174EA6;
    }

    [theme=dark] .colab-df-convert {
      background-color: #3B4455;
      fill: #D2E3FC;
    }

    [theme=dark] .colab-df-convert:hover {
      background-color: #434B5C;
      box-shadow: 0px 1px 3px 1px rgba(0, 0, 0, 0.15);
      filter: drop-shadow(0px 1px 2px rgba(0, 0, 0, 0.3));
      fill: #FFFFFF;
    }
  </style>

      <script>
        const buttonEl =
          document.querySelector('#df-0209f350-6b5b-462f-88ec-6d2b3180b010 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-0209f350-6b5b-462f-88ec-6d2b3180b010');
          const dataTable =
            await google.colab.kernel.invokeFunction('convertToInteractive',
                                                     [key], {});
          if (!dataTable) return;

          const docLinkHtml = 'Like what you see? Visit the ' +
            '<a target="_blank" href=https://colab.research.google.com/notebooks/data_table.ipynb>data table notebook</a>'
            + ' to learn more about interactive tables.';
          element.innerHTML = '';
          dataTable['output_type'] = 'display_data';
          await google.colab.output.renderOutput(dataTable, element);
          const docLink = document.createElement('div');
          docLink.innerHTML = docLinkHtml;
          element.appendChild(docLink);
        }
      </script>
    </div>
  </div>





```python
sample_submission.to_csv('submission.csv', index=False)
```
