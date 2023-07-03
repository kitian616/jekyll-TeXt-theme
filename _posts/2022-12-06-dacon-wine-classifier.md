---
layout: single
title:  "[Dacon] 와인 품질 분류 경진대회"
categories: Dacon
tag: [python, Dacon, ml]
toc: true
author_profile: true
---

데이콘 실무역량 강화교육 간에 실시한 프로젝트인 와인분류 프로젝트입니다. 

성능 향상을 위해 앙상블기법을 활용해 보았습니다.

또한 하드보팅과 소프트 보팅에 대해 배웠습니다.

# 0.준비

## 1) 데이터 로드


```python
from google.colab import drive
drive.mount('/content/drive')
```

    Drive already mounted at /content/drive; to attempt to forcibly remount, call drive.mount("/content/drive", force_remount=True).



```python
import matplotlib.pyplot as plt
import pandas as pd
plt.rc('font',family = 'NanumBarunGothic')

from sklearn.ensemble import RandomForestClassifier
from sklearn.model_selection import GridSearchCV
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.ensemble import ExtraTreesClassifier
from xgboost.sklearn import XGBClassifier
from sklearn.model_selection import StratifiedKFold
from xgboost import XGBClassifier
from sklearn.preprocessing import MinMaxScaler
from sklearn.preprocessing import StandardScaler
import seaborn as sns
import numpy as np
```


```python
train = pd.read_csv('/content/drive/MyDrive/와인1/train.csv')
test = pd.read_csv('/content/drive/MyDrive/와인1/test.csv')
submission = pd.read_csv('/content/drive/MyDrive/와인1/sample_submission.csv')
```


```python
train
```





  <div id="df-2b2eef28-f6ff-4e58-b089-a395d43d1f2c">
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
      <th>index</th>
      <th>quality</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>5</td>
      <td>5.6</td>
      <td>0.695</td>
      <td>0.06</td>
      <td>6.8</td>
      <td>0.042</td>
      <td>9.0</td>
      <td>84.0</td>
      <td>0.99432</td>
      <td>3.44</td>
      <td>0.44</td>
      <td>10.2</td>
      <td>white</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>5</td>
      <td>8.8</td>
      <td>0.610</td>
      <td>0.14</td>
      <td>2.4</td>
      <td>0.067</td>
      <td>10.0</td>
      <td>42.0</td>
      <td>0.99690</td>
      <td>3.19</td>
      <td>0.59</td>
      <td>9.5</td>
      <td>red</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>5</td>
      <td>7.9</td>
      <td>0.210</td>
      <td>0.39</td>
      <td>2.0</td>
      <td>0.057</td>
      <td>21.0</td>
      <td>138.0</td>
      <td>0.99176</td>
      <td>3.05</td>
      <td>0.52</td>
      <td>10.9</td>
      <td>white</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>6</td>
      <td>7.0</td>
      <td>0.210</td>
      <td>0.31</td>
      <td>6.0</td>
      <td>0.046</td>
      <td>29.0</td>
      <td>108.0</td>
      <td>0.99390</td>
      <td>3.26</td>
      <td>0.50</td>
      <td>10.8</td>
      <td>white</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>6</td>
      <td>7.8</td>
      <td>0.400</td>
      <td>0.26</td>
      <td>9.5</td>
      <td>0.059</td>
      <td>32.0</td>
      <td>178.0</td>
      <td>0.99550</td>
      <td>3.04</td>
      <td>0.43</td>
      <td>10.9</td>
      <td>white</td>
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
      <th>5492</th>
      <td>5492</td>
      <td>5</td>
      <td>7.7</td>
      <td>0.150</td>
      <td>0.29</td>
      <td>1.3</td>
      <td>0.029</td>
      <td>10.0</td>
      <td>64.0</td>
      <td>0.99320</td>
      <td>3.35</td>
      <td>0.39</td>
      <td>10.1</td>
      <td>white</td>
    </tr>
    <tr>
      <th>5493</th>
      <td>5493</td>
      <td>6</td>
      <td>6.3</td>
      <td>0.180</td>
      <td>0.36</td>
      <td>1.2</td>
      <td>0.034</td>
      <td>26.0</td>
      <td>111.0</td>
      <td>0.99074</td>
      <td>3.16</td>
      <td>0.51</td>
      <td>11.0</td>
      <td>white</td>
    </tr>
    <tr>
      <th>5494</th>
      <td>5494</td>
      <td>7</td>
      <td>7.8</td>
      <td>0.150</td>
      <td>0.34</td>
      <td>1.1</td>
      <td>0.035</td>
      <td>31.0</td>
      <td>93.0</td>
      <td>0.99096</td>
      <td>3.07</td>
      <td>0.72</td>
      <td>11.3</td>
      <td>white</td>
    </tr>
    <tr>
      <th>5495</th>
      <td>5495</td>
      <td>5</td>
      <td>6.6</td>
      <td>0.410</td>
      <td>0.31</td>
      <td>1.6</td>
      <td>0.042</td>
      <td>18.0</td>
      <td>101.0</td>
      <td>0.99195</td>
      <td>3.13</td>
      <td>0.41</td>
      <td>10.5</td>
      <td>white</td>
    </tr>
    <tr>
      <th>5496</th>
      <td>5496</td>
      <td>6</td>
      <td>7.0</td>
      <td>0.350</td>
      <td>0.17</td>
      <td>1.1</td>
      <td>0.049</td>
      <td>7.0</td>
      <td>119.0</td>
      <td>0.99297</td>
      <td>3.13</td>
      <td>0.36</td>
      <td>9.7</td>
      <td>white</td>
    </tr>
  </tbody>
</table>
<p>5497 rows × 14 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-2b2eef28-f6ff-4e58-b089-a395d43d1f2c')"
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
          document.querySelector('#df-2b2eef28-f6ff-4e58-b089-a395d43d1f2c button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-2b2eef28-f6ff-4e58-b089-a395d43d1f2c');
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
data_preprocessing()
```

    3      26
    4     372
    5    1788
    6    2416
    7    3696
    8     304
    9       5
    Name: quality, dtype: int64






  <div id="df-42ff386f-5904-47f4-bf91-f5f8dcc64559">
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
      <th>index</th>
      <th>quality</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
      <th>new_col</th>
      <th>new_col2</th>
      <th>new_col3</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>5</td>
      <td>5.6</td>
      <td>0.695</td>
      <td>0.06</td>
      <td>6.8</td>
      <td>0.042</td>
      <td>9.0</td>
      <td>84.0</td>
      <td>0.99432</td>
      <td>3.44</td>
      <td>0.44</td>
      <td>10.2</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.124107</td>
      <td>0.107143</td>
      <td>6.295</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>5</td>
      <td>8.8</td>
      <td>0.610</td>
      <td>0.14</td>
      <td>2.4</td>
      <td>0.067</td>
      <td>10.0</td>
      <td>42.0</td>
      <td>0.99690</td>
      <td>3.19</td>
      <td>0.59</td>
      <td>9.5</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.069318</td>
      <td>0.238095</td>
      <td>9.410</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>5</td>
      <td>7.9</td>
      <td>0.210</td>
      <td>0.39</td>
      <td>2.0</td>
      <td>0.057</td>
      <td>21.0</td>
      <td>138.0</td>
      <td>0.99176</td>
      <td>3.05</td>
      <td>0.52</td>
      <td>10.9</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.026582</td>
      <td>0.152174</td>
      <td>8.110</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>6</td>
      <td>7.0</td>
      <td>0.210</td>
      <td>0.31</td>
      <td>6.0</td>
      <td>0.046</td>
      <td>29.0</td>
      <td>108.0</td>
      <td>0.99390</td>
      <td>3.26</td>
      <td>0.50</td>
      <td>10.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.030000</td>
      <td>0.268519</td>
      <td>7.210</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>6</td>
      <td>7.8</td>
      <td>0.400</td>
      <td>0.26</td>
      <td>9.5</td>
      <td>0.059</td>
      <td>32.0</td>
      <td>178.0</td>
      <td>0.99550</td>
      <td>3.04</td>
      <td>0.43</td>
      <td>10.9</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.051282</td>
      <td>0.179775</td>
      <td>8.200</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-42ff386f-5904-47f4-bf91-f5f8dcc64559')"
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
          document.querySelector('#df-42ff386f-5904-47f4-bf91-f5f8dcc64559 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-42ff386f-5904-47f4-bf91-f5f8dcc64559');
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




## 2) 전처리


```python
import numpy as np

def ACC(true,pred):
  score = np.mean(true==pred)
  return score
```


```python
def make_plot():
  acc = ACC(y_valid,y_pred)

  validation = pd.DataFrame({'y_valid':y_valid,'y_pred':y_pred})

  validation_count = pd.DataFrame(validation['y_valid'].value_counts().sort_index())
  validation_count.loc[validation['y_pred'].value_counts().sort_index().index,'y_pred']=validation['y_pred'].value_counts().sort_index().values
  validation_count = validation_count.fillna(0)

  x = validation_count.index
  y_valid_count = validation_count['y_valid']
  y_pred_count = validation_count['y_pred']

  width = 0.35

  plt.figure(dpi=150)

  plt.title('ACC: '+str(acc)[:6])
  plt.xlabel('quality')
  plt.ylabel('count')

  p1 = plt.bar([idx-width/2 for idx in x],y_valid_count,width,label = 'real')
  p2 = plt.bar([idx+width/2 for idx in x],y_pred_count,width,label = 'pred')

  plt.legend()
  plt.show()
```

### 원핫인코딩


```python
train = train.append(train[train['quality'].isin([4,7,8])])

train = pd.concat([train,pd.get_dummies(train['type'])],axis=1)
test = pd.concat([test,pd.get_dummies(test['type'])],axis=1)
train
```





  <div id="df-5d93c80e-6d78-4ee4-ae76-af73495f3c5c">
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
      <th>index</th>
      <th>quality</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>5</td>
      <td>5.6</td>
      <td>0.695</td>
      <td>0.06</td>
      <td>6.8</td>
      <td>0.042</td>
      <td>9.0</td>
      <td>84.0</td>
      <td>0.99432</td>
      <td>3.44</td>
      <td>0.44</td>
      <td>10.2</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>5</td>
      <td>8.8</td>
      <td>0.610</td>
      <td>0.14</td>
      <td>2.4</td>
      <td>0.067</td>
      <td>10.0</td>
      <td>42.0</td>
      <td>0.99690</td>
      <td>3.19</td>
      <td>0.59</td>
      <td>9.5</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>5</td>
      <td>7.9</td>
      <td>0.210</td>
      <td>0.39</td>
      <td>2.0</td>
      <td>0.057</td>
      <td>21.0</td>
      <td>138.0</td>
      <td>0.99176</td>
      <td>3.05</td>
      <td>0.52</td>
      <td>10.9</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>6</td>
      <td>7.0</td>
      <td>0.210</td>
      <td>0.31</td>
      <td>6.0</td>
      <td>0.046</td>
      <td>29.0</td>
      <td>108.0</td>
      <td>0.99390</td>
      <td>3.26</td>
      <td>0.50</td>
      <td>10.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>6</td>
      <td>7.8</td>
      <td>0.400</td>
      <td>0.26</td>
      <td>9.5</td>
      <td>0.059</td>
      <td>32.0</td>
      <td>178.0</td>
      <td>0.99550</td>
      <td>3.04</td>
      <td>0.43</td>
      <td>10.9</td>
      <td>white</td>
      <td>0</td>
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
      <th>5481</th>
      <td>5481</td>
      <td>7</td>
      <td>5.4</td>
      <td>0.835</td>
      <td>0.08</td>
      <td>1.2</td>
      <td>0.046</td>
      <td>13.0</td>
      <td>93.0</td>
      <td>0.99240</td>
      <td>3.57</td>
      <td>0.85</td>
      <td>13.0</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
    </tr>
    <tr>
      <th>5482</th>
      <td>5482</td>
      <td>4</td>
      <td>6.2</td>
      <td>0.430</td>
      <td>0.49</td>
      <td>6.4</td>
      <td>0.045</td>
      <td>12.0</td>
      <td>115.0</td>
      <td>0.99630</td>
      <td>3.27</td>
      <td>0.57</td>
      <td>9.0</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>5485</th>
      <td>5485</td>
      <td>4</td>
      <td>6.3</td>
      <td>0.280</td>
      <td>0.22</td>
      <td>9.5</td>
      <td>0.040</td>
      <td>30.0</td>
      <td>111.0</td>
      <td>0.99338</td>
      <td>3.05</td>
      <td>0.31</td>
      <td>10.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>5491</th>
      <td>5491</td>
      <td>7</td>
      <td>6.9</td>
      <td>0.340</td>
      <td>0.30</td>
      <td>4.7</td>
      <td>0.029</td>
      <td>34.0</td>
      <td>148.0</td>
      <td>0.99165</td>
      <td>3.36</td>
      <td>0.49</td>
      <td>12.3</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
    <tr>
      <th>5494</th>
      <td>5494</td>
      <td>7</td>
      <td>7.8</td>
      <td>0.150</td>
      <td>0.34</td>
      <td>1.1</td>
      <td>0.035</td>
      <td>31.0</td>
      <td>93.0</td>
      <td>0.99096</td>
      <td>3.07</td>
      <td>0.72</td>
      <td>11.3</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
    </tr>
  </tbody>
</table>
<p>6759 rows × 16 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-5d93c80e-6d78-4ee4-ae76-af73495f3c5c')"
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
          document.querySelector('#df-5d93c80e-6d78-4ee4-ae76-af73495f3c5c button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-5d93c80e-6d78-4ee4-ae76-af73495f3c5c');
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




### feature engineering


```python
def make_plots(text):
  plt.title(text + ' vs quality')

  x = train.groupby('quality').mean().reset_index()['quality']
  y = train.groupby('quality').mean().reset_index()[text]
  
  plt.bar(x,y)
  plt.show()
```


```python
train['new_col'] = train['volatile acidity'] / train['fixed acidity']
train['new_col2'] = train['free sulfur dioxide'] / train['total sulfur dioxide']
train['new_col3'] = train['volatile acidity'] + train['fixed acidity']
train
```





  <div id="df-55a134cd-3021-41a4-a9e0-fea706aa5a02">
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
      <th>index</th>
      <th>quality</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
      <th>new_col</th>
      <th>new_col2</th>
      <th>new_col3</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>5</td>
      <td>5.6</td>
      <td>0.695</td>
      <td>0.06</td>
      <td>6.8</td>
      <td>0.042</td>
      <td>9.0</td>
      <td>84.0</td>
      <td>0.99432</td>
      <td>3.44</td>
      <td>0.44</td>
      <td>10.2</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.124107</td>
      <td>0.107143</td>
      <td>6.295</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>5</td>
      <td>8.8</td>
      <td>0.610</td>
      <td>0.14</td>
      <td>2.4</td>
      <td>0.067</td>
      <td>10.0</td>
      <td>42.0</td>
      <td>0.99690</td>
      <td>3.19</td>
      <td>0.59</td>
      <td>9.5</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.069318</td>
      <td>0.238095</td>
      <td>9.410</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>5</td>
      <td>7.9</td>
      <td>0.210</td>
      <td>0.39</td>
      <td>2.0</td>
      <td>0.057</td>
      <td>21.0</td>
      <td>138.0</td>
      <td>0.99176</td>
      <td>3.05</td>
      <td>0.52</td>
      <td>10.9</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.026582</td>
      <td>0.152174</td>
      <td>8.110</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>6</td>
      <td>7.0</td>
      <td>0.210</td>
      <td>0.31</td>
      <td>6.0</td>
      <td>0.046</td>
      <td>29.0</td>
      <td>108.0</td>
      <td>0.99390</td>
      <td>3.26</td>
      <td>0.50</td>
      <td>10.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.030000</td>
      <td>0.268519</td>
      <td>7.210</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>6</td>
      <td>7.8</td>
      <td>0.400</td>
      <td>0.26</td>
      <td>9.5</td>
      <td>0.059</td>
      <td>32.0</td>
      <td>178.0</td>
      <td>0.99550</td>
      <td>3.04</td>
      <td>0.43</td>
      <td>10.9</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.051282</td>
      <td>0.179775</td>
      <td>8.200</td>
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
      <th>5481</th>
      <td>5481</td>
      <td>7</td>
      <td>5.4</td>
      <td>0.835</td>
      <td>0.08</td>
      <td>1.2</td>
      <td>0.046</td>
      <td>13.0</td>
      <td>93.0</td>
      <td>0.99240</td>
      <td>3.57</td>
      <td>0.85</td>
      <td>13.0</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.154630</td>
      <td>0.139785</td>
      <td>6.235</td>
    </tr>
    <tr>
      <th>5482</th>
      <td>5482</td>
      <td>4</td>
      <td>6.2</td>
      <td>0.430</td>
      <td>0.49</td>
      <td>6.4</td>
      <td>0.045</td>
      <td>12.0</td>
      <td>115.0</td>
      <td>0.99630</td>
      <td>3.27</td>
      <td>0.57</td>
      <td>9.0</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.069355</td>
      <td>0.104348</td>
      <td>6.630</td>
    </tr>
    <tr>
      <th>5485</th>
      <td>5485</td>
      <td>4</td>
      <td>6.3</td>
      <td>0.280</td>
      <td>0.22</td>
      <td>9.5</td>
      <td>0.040</td>
      <td>30.0</td>
      <td>111.0</td>
      <td>0.99338</td>
      <td>3.05</td>
      <td>0.31</td>
      <td>10.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.044444</td>
      <td>0.270270</td>
      <td>6.580</td>
    </tr>
    <tr>
      <th>5491</th>
      <td>5491</td>
      <td>7</td>
      <td>6.9</td>
      <td>0.340</td>
      <td>0.30</td>
      <td>4.7</td>
      <td>0.029</td>
      <td>34.0</td>
      <td>148.0</td>
      <td>0.99165</td>
      <td>3.36</td>
      <td>0.49</td>
      <td>12.3</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.049275</td>
      <td>0.229730</td>
      <td>7.240</td>
    </tr>
    <tr>
      <th>5494</th>
      <td>5494</td>
      <td>7</td>
      <td>7.8</td>
      <td>0.150</td>
      <td>0.34</td>
      <td>1.1</td>
      <td>0.035</td>
      <td>31.0</td>
      <td>93.0</td>
      <td>0.99096</td>
      <td>3.07</td>
      <td>0.72</td>
      <td>11.3</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.019231</td>
      <td>0.333333</td>
      <td>7.950</td>
    </tr>
  </tbody>
</table>
<p>6759 rows × 19 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-55a134cd-3021-41a4-a9e0-fea706aa5a02')"
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
          document.querySelector('#df-55a134cd-3021-41a4-a9e0-fea706aa5a02 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-55a134cd-3021-41a4-a9e0-fea706aa5a02');
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
|train.corr()['quality'].to_frame()
```





  <div id="df-4e9a379c-a49a-43ca-8481-fe9d2eba88fd">
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
      <th>quality</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>index</th>
      <td>-0.007835</td>
    </tr>
    <tr>
      <th>quality</th>
      <td>1.000000</td>
    </tr>
    <tr>
      <th>fixed acidity</th>
      <td>-0.079555</td>
    </tr>
    <tr>
      <th>volatile acidity</th>
      <td>-0.282066</td>
    </tr>
    <tr>
      <th>citric acid</th>
      <td>0.091287</td>
    </tr>
    <tr>
      <th>residual sugar</th>
      <td>-0.028293</td>
    </tr>
    <tr>
      <th>chlorides</th>
      <td>-0.215320</td>
    </tr>
    <tr>
      <th>free sulfur dioxide</th>
      <td>0.082963</td>
    </tr>
    <tr>
      <th>total sulfur dioxide</th>
      <td>-0.034012</td>
    </tr>
    <tr>
      <th>density</th>
      <td>-0.316107</td>
    </tr>
    <tr>
      <th>pH</th>
      <td>0.016610</td>
    </tr>
    <tr>
      <th>sulphates</th>
      <td>0.048132</td>
    </tr>
    <tr>
      <th>alcohol</th>
      <td>0.460041</td>
    </tr>
    <tr>
      <th>red</th>
      <td>-0.120606</td>
    </tr>
    <tr>
      <th>white</th>
      <td>0.120606</td>
    </tr>
    <tr>
      <th>new_col</th>
      <td>-0.244637</td>
    </tr>
    <tr>
      <th>new_col2</th>
      <td>0.135119</td>
    </tr>
    <tr>
      <th>new_col3</th>
      <td>-0.111243</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-4e9a379c-a49a-43ca-8481-fe9d2eba88fd')"
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
          document.querySelector('#df-4e9a379c-a49a-43ca-8481-fe9d2eba88fd button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-4e9a379c-a49a-43ca-8481-fe9d2eba88fd');
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




### 스케일링


```python
test['new_col'] = test['volatile acidity'] / test['fixed acidity']
test['new_col2'] = test['free sulfur dioxide'] / test['total sulfur dioxide']
test['new_col3'] = test['volatile acidity'] + test['fixed acidity']
```


```python
test
```





  <div id="df-70ca4041-18b1-437d-a397-16c1a75d7224">
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
      <th>index</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
      <th>new_col</th>
      <th>new_col2</th>
      <th>new_col3</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>9.0</td>
      <td>0.31</td>
      <td>0.48</td>
      <td>6.60</td>
      <td>0.043</td>
      <td>11.0</td>
      <td>73.0</td>
      <td>0.99380</td>
      <td>2.90</td>
      <td>0.38</td>
      <td>11.6</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.034444</td>
      <td>0.150685</td>
      <td>9.31</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>13.3</td>
      <td>0.43</td>
      <td>0.58</td>
      <td>1.90</td>
      <td>0.070</td>
      <td>15.0</td>
      <td>40.0</td>
      <td>1.00040</td>
      <td>3.06</td>
      <td>0.49</td>
      <td>9.0</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.032331</td>
      <td>0.375000</td>
      <td>13.73</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>6.5</td>
      <td>0.28</td>
      <td>0.27</td>
      <td>5.20</td>
      <td>0.040</td>
      <td>44.0</td>
      <td>179.0</td>
      <td>0.99480</td>
      <td>3.19</td>
      <td>0.69</td>
      <td>9.4</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.043077</td>
      <td>0.245810</td>
      <td>6.78</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>7.2</td>
      <td>0.15</td>
      <td>0.39</td>
      <td>1.80</td>
      <td>0.043</td>
      <td>21.0</td>
      <td>159.0</td>
      <td>0.99480</td>
      <td>3.52</td>
      <td>0.47</td>
      <td>10.0</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.020833</td>
      <td>0.132075</td>
      <td>7.35</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>6.8</td>
      <td>0.26</td>
      <td>0.26</td>
      <td>2.00</td>
      <td>0.019</td>
      <td>23.5</td>
      <td>72.0</td>
      <td>0.99041</td>
      <td>3.16</td>
      <td>0.47</td>
      <td>11.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.038235</td>
      <td>0.326389</td>
      <td>7.06</td>
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
    </tr>
    <tr>
      <th>995</th>
      <td>995</td>
      <td>7.1</td>
      <td>0.59</td>
      <td>0.02</td>
      <td>2.30</td>
      <td>0.082</td>
      <td>24.0</td>
      <td>94.0</td>
      <td>0.99744</td>
      <td>3.55</td>
      <td>0.53</td>
      <td>9.7</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.083099</td>
      <td>0.255319</td>
      <td>7.69</td>
    </tr>
    <tr>
      <th>996</th>
      <td>996</td>
      <td>8.7</td>
      <td>0.15</td>
      <td>0.30</td>
      <td>1.60</td>
      <td>0.046</td>
      <td>29.0</td>
      <td>130.0</td>
      <td>0.99420</td>
      <td>3.22</td>
      <td>0.38</td>
      <td>9.8</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.017241</td>
      <td>0.223077</td>
      <td>8.85</td>
    </tr>
    <tr>
      <th>997</th>
      <td>997</td>
      <td>8.8</td>
      <td>0.66</td>
      <td>0.26</td>
      <td>1.70</td>
      <td>0.074</td>
      <td>4.0</td>
      <td>23.0</td>
      <td>0.99710</td>
      <td>3.15</td>
      <td>0.74</td>
      <td>9.2</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.075000</td>
      <td>0.173913</td>
      <td>9.46</td>
    </tr>
    <tr>
      <th>998</th>
      <td>998</td>
      <td>7.0</td>
      <td>0.42</td>
      <td>0.19</td>
      <td>2.30</td>
      <td>0.071</td>
      <td>18.0</td>
      <td>36.0</td>
      <td>0.99476</td>
      <td>3.39</td>
      <td>0.56</td>
      <td>10.9</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.060000</td>
      <td>0.500000</td>
      <td>7.42</td>
    </tr>
    <tr>
      <th>999</th>
      <td>999</td>
      <td>8.5</td>
      <td>0.21</td>
      <td>0.26</td>
      <td>9.25</td>
      <td>0.034</td>
      <td>73.0</td>
      <td>142.0</td>
      <td>0.99450</td>
      <td>3.05</td>
      <td>0.37</td>
      <td>11.4</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.024706</td>
      <td>0.514085</td>
      <td>8.71</td>
    </tr>
  </tbody>
</table>
<p>1000 rows × 18 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-70ca4041-18b1-437d-a397-16c1a75d7224')"
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
          document.querySelector('#df-70ca4041-18b1-437d-a397-16c1a75d7224 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-70ca4041-18b1-437d-a397-16c1a75d7224');
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
# scaler = StandardScaler()

# train[train.columns[2:-6]] = scaler.fit_transform(train[train.columns[2:-6]])
# train[train.columns[-3:]] = scaler.fit_transform(train[train.columns[-3:]])

scaler = MinMaxScaler()

train[train.columns[2:-6]] = scaler.fit_transform(train[train.columns[2:-6]])
test[test.columns[1:-6]] = scaler.transform(test[test.columns[1:-6]])
train[train.columns[-3:]] = scaler.fit_transform(train[train.columns[-3:]])
test[test.columns[-3:]] = scaler.fit_transform(test[test.columns[-3:]])
```

## 3) 성능 검증


```python
def data_preprocessing():
  
  train = pd.read_csv('/content/drive/MyDrive/와인1/train.csv')
  test = pd.read_csv('/content/drive/MyDrive/와인1/test.csv')
  submission = pd.read_csv('/content/drive/MyDrive/와인1/sample_submission.csv')

  train = train.append(train[train['quality'].isin([4,7,8])])
  train = train.append(train[train['quality'].isin([7])])

  train = pd.concat([train, pd.get_dummies(train['type'])],axis=1)
  test = pd.concat([test, pd.get_dummies(test['type'])],axis=1)

  train['new_col'] = train['volatile acidity'] / train['fixed acidity']
  train['new_col2'] = train['free sulfur dioxide'] / train['total sulfur dioxide']
  train['new_col3'] = train['volatile acidity'] + train['fixed acidity']

  print(train['quality'].value_counts().sort_index())

  
  return train,test,submission
```


```python
def test_preprocessing(test):
  test['new_col'] = test['volatile acidity'] / test['fixed acidity']
  test['new_col2'] = test['free sulfur dioxide'] / test['total sulfur dioxide']
  test['new_col3'] = test['volatile acidity'] + test['fixed acidity']
  return test
```


```python
def scaling(train,test):
  scaler = MinMaxScaler()

  train[train.columns[2:-6]] = scaler.fit_transform(train[train.columns[2:-6]])
  test[test.columns[1:-6]] = scaler.transform(test[test.columns[1:-6]])
  
  train[train.columns[-3:]] = scaler.fit_transform(train[train.columns[-3:]])
  test[test.columns[-3:]] = scaler.fit_transform(test[test.columns[-3:]])
  return train,test
```


```python
data_preprocessing()
```

    3      26
    4     372
    5    1788
    6    2416
    7    3696
    8     304
    9       5
    Name: quality, dtype: int64





    (      index  quality  fixed acidity  volatile acidity  citric acid  \
     0         0        5            5.6             0.695         0.06   
     1         1        5            8.8             0.610         0.14   
     2         2        5            7.9             0.210         0.39   
     3         3        6            7.0             0.210         0.31   
     4         4        6            7.8             0.400         0.26   
     ...     ...      ...            ...               ...          ...   
     5456   5456        7            5.9             0.170         0.29   
     5466   5466        7            6.0             0.290         0.41   
     5481   5481        7            5.4             0.835         0.08   
     5491   5491        7            6.9             0.340         0.30   
     5494   5494        7            7.8             0.150         0.34   
     
           residual sugar  chlorides  free sulfur dioxide  total sulfur dioxide  \
     0                6.8      0.042                  9.0                  84.0   
     1                2.4      0.067                 10.0                  42.0   
     2                2.0      0.057                 21.0                 138.0   
     3                6.0      0.046                 29.0                 108.0   
     4                9.5      0.059                 32.0                 178.0   
     ...              ...        ...                  ...                   ...   
     5456             3.1      0.030                 32.0                 123.0   
     5466            10.8      0.048                 55.0                 149.0   
     5481             1.2      0.046                 13.0                  93.0   
     5491             4.7      0.029                 34.0                 148.0   
     5494             1.1      0.035                 31.0                  93.0   
     
           density    pH  sulphates  alcohol   type  red  white   new_col  \
     0     0.99432  3.44       0.44     10.2  white    0      1  0.124107   
     1     0.99690  3.19       0.59      9.5    red    1      0  0.069318   
     2     0.99176  3.05       0.52     10.9  white    0      1  0.026582   
     3     0.99390  3.26       0.50     10.8  white    0      1  0.030000   
     4     0.99550  3.04       0.43     10.9  white    0      1  0.051282   
     ...       ...   ...        ...      ...    ...  ...    ...       ...   
     5456  0.98913  3.41       0.33     13.7  white    0      1  0.028814   
     5466  0.99370  3.09       0.59     11.0  white    0      1  0.048333   
     5481  0.99240  3.57       0.85     13.0    red    1      0  0.154630   
     5491  0.99165  3.36       0.49     12.3  white    0      1  0.049275   
     5494  0.99096  3.07       0.72     11.3  white    0      1  0.019231   
     
           new_col2  new_col3  
     0     0.107143     6.295  
     1     0.238095     9.410  
     2     0.152174     8.110  
     3     0.268519     7.210  
     4     0.179775     8.200  
     ...        ...       ...  
     5456  0.260163     6.070  
     5466  0.369128     6.290  
     5481  0.139785     6.235  
     5491  0.229730     7.240  
     5494  0.333333     7.950  
     
     [8607 rows x 19 columns],
          index  fixed acidity  volatile acidity  citric acid  residual sugar  \
     0        0            9.0              0.31         0.48            6.60   
     1        1           13.3              0.43         0.58            1.90   
     2        2            6.5              0.28         0.27            5.20   
     3        3            7.2              0.15         0.39            1.80   
     4        4            6.8              0.26         0.26            2.00   
     ..     ...            ...               ...          ...             ...   
     995    995            7.1              0.59         0.02            2.30   
     996    996            8.7              0.15         0.30            1.60   
     997    997            8.8              0.66         0.26            1.70   
     998    998            7.0              0.42         0.19            2.30   
     999    999            8.5              0.21         0.26            9.25   
     
          chlorides  free sulfur dioxide  total sulfur dioxide  density    pH  \
     0        0.043                 11.0                  73.0  0.99380  2.90   
     1        0.070                 15.0                  40.0  1.00040  3.06   
     2        0.040                 44.0                 179.0  0.99480  3.19   
     3        0.043                 21.0                 159.0  0.99480  3.52   
     4        0.019                 23.5                  72.0  0.99041  3.16   
     ..         ...                  ...                   ...      ...   ...   
     995      0.082                 24.0                  94.0  0.99744  3.55   
     996      0.046                 29.0                 130.0  0.99420  3.22   
     997      0.074                  4.0                  23.0  0.99710  3.15   
     998      0.071                 18.0                  36.0  0.99476  3.39   
     999      0.034                 73.0                 142.0  0.99450  3.05   
     
          sulphates  alcohol   type  red  white  
     0         0.38     11.6  white    0      1  
     1         0.49      9.0    red    1      0  
     2         0.69      9.4  white    0      1  
     3         0.47     10.0  white    0      1  
     4         0.47     11.8  white    0      1  
     ..         ...      ...    ...  ...    ...  
     995       0.53      9.7    red    1      0  
     996       0.38      9.8  white    0      1  
     997       0.74      9.2    red    1      0  
     998       0.56     10.9    red    1      0  
     999       0.37     11.4  white    0      1  
     
     [1000 rows x 15 columns],
          index  quality
     0        0        0
     1        1        0
     2        2        0
     3        3        0
     4        4        0
     ..     ...      ...
     995    995        0
     996    996        0
     997    997        0
     998    998        0
     999    999        0
     
     [1000 rows x 2 columns])




```python
train
```





  <div id="df-460733cb-7c16-4b56-b6af-1601cc8ca37f">
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
      <th>index</th>
      <th>quality</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
      <th>new_col</th>
      <th>new_col2</th>
      <th>new_col3</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>5</td>
      <td>0.148760</td>
      <td>0.410000</td>
      <td>0.036145</td>
      <td>0.095092</td>
      <td>0.054908</td>
      <td>0.027778</td>
      <td>0.179724</td>
      <td>0.139001</td>
      <td>0.551181</td>
      <td>0.123596</td>
      <td>0.318841</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.574215</td>
      <td>0.100202</td>
      <td>0.179466</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>5</td>
      <td>0.413223</td>
      <td>0.353333</td>
      <td>0.084337</td>
      <td>0.027607</td>
      <td>0.096506</td>
      <td>0.031250</td>
      <td>0.082949</td>
      <td>0.188741</td>
      <td>0.354331</td>
      <td>0.207865</td>
      <td>0.217391</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.295792</td>
      <td>0.257310</td>
      <td>0.435318</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>5</td>
      <td>0.338843</td>
      <td>0.086667</td>
      <td>0.234940</td>
      <td>0.021472</td>
      <td>0.079867</td>
      <td>0.069444</td>
      <td>0.304147</td>
      <td>0.089647</td>
      <td>0.244094</td>
      <td>0.168539</td>
      <td>0.420290</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.078620</td>
      <td>0.154228</td>
      <td>0.328542</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>6</td>
      <td>0.264463</td>
      <td>0.086667</td>
      <td>0.186747</td>
      <td>0.082822</td>
      <td>0.061564</td>
      <td>0.097222</td>
      <td>0.235023</td>
      <td>0.130904</td>
      <td>0.409449</td>
      <td>0.157303</td>
      <td>0.405797</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.095988</td>
      <td>0.293810</td>
      <td>0.254620</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>6</td>
      <td>0.330579</td>
      <td>0.213333</td>
      <td>0.156627</td>
      <td>0.136503</td>
      <td>0.083195</td>
      <td>0.107639</td>
      <td>0.396313</td>
      <td>0.161751</td>
      <td>0.236220</td>
      <td>0.117978</td>
      <td>0.420290</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.204138</td>
      <td>0.187342</td>
      <td>0.335934</td>
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
      <th>5481</th>
      <td>5481</td>
      <td>7</td>
      <td>0.132231</td>
      <td>0.503333</td>
      <td>0.048193</td>
      <td>0.009202</td>
      <td>0.061564</td>
      <td>0.041667</td>
      <td>0.200461</td>
      <td>0.101986</td>
      <td>0.653543</td>
      <td>0.353933</td>
      <td>0.724638</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.729321</td>
      <td>0.139364</td>
      <td>0.174538</td>
    </tr>
    <tr>
      <th>5482</th>
      <td>5482</td>
      <td>4</td>
      <td>0.198347</td>
      <td>0.233333</td>
      <td>0.295181</td>
      <td>0.088957</td>
      <td>0.059900</td>
      <td>0.038194</td>
      <td>0.251152</td>
      <td>0.177174</td>
      <td>0.417323</td>
      <td>0.196629</td>
      <td>0.144928</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.295979</td>
      <td>0.096849</td>
      <td>0.206982</td>
    </tr>
    <tr>
      <th>5485</th>
      <td>5485</td>
      <td>4</td>
      <td>0.206612</td>
      <td>0.133333</td>
      <td>0.132530</td>
      <td>0.136503</td>
      <td>0.051581</td>
      <td>0.100694</td>
      <td>0.241935</td>
      <td>0.120879</td>
      <td>0.244094</td>
      <td>0.050562</td>
      <td>0.405797</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.169391</td>
      <td>0.295911</td>
      <td>0.202875</td>
    </tr>
    <tr>
      <th>5491</th>
      <td>5491</td>
      <td>7</td>
      <td>0.256198</td>
      <td>0.173333</td>
      <td>0.180723</td>
      <td>0.062883</td>
      <td>0.033278</td>
      <td>0.114583</td>
      <td>0.327189</td>
      <td>0.087527</td>
      <td>0.488189</td>
      <td>0.151685</td>
      <td>0.623188</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.193940</td>
      <td>0.247274</td>
      <td>0.257084</td>
    </tr>
    <tr>
      <th>5494</th>
      <td>5494</td>
      <td>7</td>
      <td>0.330579</td>
      <td>0.046667</td>
      <td>0.204819</td>
      <td>0.007669</td>
      <td>0.043261</td>
      <td>0.104167</td>
      <td>0.200461</td>
      <td>0.074224</td>
      <td>0.259843</td>
      <td>0.280899</td>
      <td>0.478261</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.041262</td>
      <td>0.371570</td>
      <td>0.315400</td>
    </tr>
  </tbody>
</table>
<p>6759 rows × 19 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-460733cb-7c16-4b56-b6af-1601cc8ca37f')"
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
          document.querySelector('#df-460733cb-7c16-4b56-b6af-1601cc8ca37f button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-460733cb-7c16-4b56-b6af-1601cc8ca37f');
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
test
```





  <div id="df-30b53ba3-ff0b-42ee-a5da-bc2881c98b24">
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
      <th>index</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
      <th>new_col</th>
      <th>new_col2</th>
      <th>new_col3</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0.429752</td>
      <td>0.153333</td>
      <td>0.289157</td>
      <td>0.092025</td>
      <td>0.056572</td>
      <td>0.034722</td>
      <td>0.154378</td>
      <td>0.128976</td>
      <td>0.125984</td>
      <td>0.089888</td>
      <td>0.521739</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.113120</td>
      <td>0.172815</td>
      <td>0.416000</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>0.785124</td>
      <td>0.233333</td>
      <td>0.349398</td>
      <td>0.019939</td>
      <td>0.101498</td>
      <td>0.048611</td>
      <td>0.078341</td>
      <td>0.256217</td>
      <td>0.251969</td>
      <td>0.151685</td>
      <td>0.144928</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.102319</td>
      <td>0.475767</td>
      <td>0.788211</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>0.223140</td>
      <td>0.133333</td>
      <td>0.162651</td>
      <td>0.070552</td>
      <td>0.051581</td>
      <td>0.149306</td>
      <td>0.398618</td>
      <td>0.148255</td>
      <td>0.354331</td>
      <td>0.264045</td>
      <td>0.202899</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.157233</td>
      <td>0.301288</td>
      <td>0.202947</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>0.280992</td>
      <td>0.046667</td>
      <td>0.234940</td>
      <td>0.018405</td>
      <td>0.056572</td>
      <td>0.069444</td>
      <td>0.352535</td>
      <td>0.148255</td>
      <td>0.614173</td>
      <td>0.140449</td>
      <td>0.289855</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.043567</td>
      <td>0.147682</td>
      <td>0.250947</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>0.247934</td>
      <td>0.120000</td>
      <td>0.156627</td>
      <td>0.021472</td>
      <td>0.016639</td>
      <td>0.078125</td>
      <td>0.152074</td>
      <td>0.063621</td>
      <td>0.330709</td>
      <td>0.140449</td>
      <td>0.550725</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.132492</td>
      <td>0.410115</td>
      <td>0.226526</td>
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
    </tr>
    <tr>
      <th>995</th>
      <td>995</td>
      <td>0.272727</td>
      <td>0.340000</td>
      <td>0.012048</td>
      <td>0.026074</td>
      <td>0.121464</td>
      <td>0.079861</td>
      <td>0.202765</td>
      <td>0.199152</td>
      <td>0.637795</td>
      <td>0.174157</td>
      <td>0.246377</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.361746</td>
      <td>0.314131</td>
      <td>0.279579</td>
    </tr>
    <tr>
      <th>996</th>
      <td>996</td>
      <td>0.404959</td>
      <td>0.046667</td>
      <td>0.180723</td>
      <td>0.015337</td>
      <td>0.061564</td>
      <td>0.097222</td>
      <td>0.285714</td>
      <td>0.136688</td>
      <td>0.377953</td>
      <td>0.089888</td>
      <td>0.260870</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.025211</td>
      <td>0.270585</td>
      <td>0.377263</td>
    </tr>
    <tr>
      <th>997</th>
      <td>997</td>
      <td>0.413223</td>
      <td>0.386667</td>
      <td>0.156627</td>
      <td>0.016871</td>
      <td>0.108153</td>
      <td>0.010417</td>
      <td>0.039171</td>
      <td>0.192597</td>
      <td>0.322835</td>
      <td>0.292135</td>
      <td>0.173913</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.320362</td>
      <td>0.204186</td>
      <td>0.428632</td>
    </tr>
    <tr>
      <th>998</th>
      <td>998</td>
      <td>0.264463</td>
      <td>0.226667</td>
      <td>0.114458</td>
      <td>0.026074</td>
      <td>0.103161</td>
      <td>0.059028</td>
      <td>0.069124</td>
      <td>0.147484</td>
      <td>0.511811</td>
      <td>0.191011</td>
      <td>0.420290</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.243711</td>
      <td>0.644588</td>
      <td>0.256842</td>
    </tr>
    <tr>
      <th>999</th>
      <td>999</td>
      <td>0.388430</td>
      <td>0.086667</td>
      <td>0.156627</td>
      <td>0.132669</td>
      <td>0.041597</td>
      <td>0.250000</td>
      <td>0.313364</td>
      <td>0.142472</td>
      <td>0.244094</td>
      <td>0.084270</td>
      <td>0.492754</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.063356</td>
      <td>0.663610</td>
      <td>0.365474</td>
    </tr>
  </tbody>
</table>
<p>1000 rows × 18 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-30b53ba3-ff0b-42ee-a5da-bc2881c98b24')"
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
          document.querySelector('#df-30b53ba3-ff0b-42ee-a5da-bc2881c98b24 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-30b53ba3-ff0b-42ee-a5da-bc2881c98b24');
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
data_preprocessing()
test_preprocessing()
scaling()
```

    3      26
    4     372
    5    1788
    6    2416
    7    3696
    8     304
    9       5
    Name: quality, dtype: int64



```python
features = train.columns[2:].drop(['type'])
# features = train.columns[2:].drop(['type','sulphates','total sulfur dioxide','residual sugar','free sulfur dioxide','citric acid','pH','fixed acidity'])
# features = train.columns[2:].drop(['type','total sulfur dioxide','pH'])
# features = train.columns[2:].drop(['type','new_col',	'new_col2'	,'new_col3'])
# features = train.columns[2:].drop(['type','white','red'])
features
```




    Index(['fixed acidity', 'volatile acidity', 'citric acid', 'residual sugar',
           'chlorides', 'free sulfur dioxide', 'total sulfur dioxide', 'density',
           'pH', 'sulphates', 'alcohol', 'red', 'white', 'new_col', 'new_col2',
           'new_col3'],
          dtype='object')




```python
X = train[features]
y = train['quality']
```


```python
from sklearn.model_selection import StratifiedKFold
kfold = StratifiedKFold(n_splits=5,shuffle=True,random_state=42)

models = []
accs = []
for train_idx, valid_idx in kfold.split(X,y):
  X_train,X_valid = X.iloc[train_idx],X.iloc[valid_idx]
  y_train,y_valid = y.iloc[train_idx],y.iloc[valid_idx]

  model = RandomForestClassifier()

  model.fit(X_train,y_train)
  
  #추가
  models.append(model)

  y_pred = model.predict(X_valid)
  accs.append(ACC(y_valid,y_pred))
  make_plot()

  pred_ = pd.DataFrame(y_pred,y_valid).reset_index()
  pred_.columns = ['valid','pred']
  pred_['T/F'] = pred_['valid']==pred_['pred']
  print(pred_[pred_['T/F']==False].value_counts().sort_index())
```


    
![png](%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_files/%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_30_0.png)
    


    valid  pred  T/F  
    3      5     False      3
           6     False      2
    4      5     False     10
           6     False      6
    5      4     False      5
           6     False    105
           7     False     10
    6      4     False      3
           5     False     64
           7     False     55
    7      5     False      4
           6     False     26
    8      6     False      6
    9      7     False      1
    dtype: int64



    
![png](%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_files/%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_30_2.png)
    


    valid  pred  T/F  
    3      5     False      1
           6     False      3
           7     False      1
    4      5     False      4
           6     False      8
    5      4     False      3
           6     False    104
           7     False      5
    6      4     False      1
           5     False     73
           7     False     61
           8     False      2
    7      5     False      2
           6     False     26
    8      7     False      2
    9      6     False      1
    dtype: int64



    
![png](%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_files/%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_30_4.png)
    


    valid  pred  T/F  
    3      5     False     3
           6     False     2
    4      5     False    10
           6     False     4
           7     False     2
    5      4     False     5
           6     False    85
           7     False     8
    6      4     False     2
           5     False    96
           7     False    48
    7      5     False     2
           6     False    16
    8      7     False     2
    9      7     False     1
    dtype: int64



    
![png](%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_files/%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_30_6.png)
    


    valid  pred  T/F  
    3      5     False     5
           6     False     1
    4      5     False    10
           6     False     6
    5      4     False     4
           6     False    96
           7     False     7
    6      4     False     2
           5     False    99
           7     False    44
    7      5     False     2
           6     False    22
    8      7     False    10
    9      7     False     1
    dtype: int64



    
![png](%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_files/%EC%99%80%EC%9D%B8_%EB%B6%84%EB%A5%98_%EA%B2%BD%EC%A7%84%EB%8C%80%ED%9A%8C_30_8.png)
    


    valid  pred  T/F  
    3      5     False     3
           6     False     2
    4      5     False     4
           6     False     4
    5      4     False     3
           6     False    88
           7     False     5
    6      4     False     3
           5     False    78
           7     False    61
           8     False     1
    7      6     False    24
    8      6     False     2
           7     False     6
    9      7     False     1
    dtype: int64


# 1.모델링


```python
train,test,submission = data_preprocessing()
test = test_preprocessing(test)
train,test = scaling(train,test)
```

    3      26
    4     372
    5    1788
    6    2416
    7    3696
    8     304
    9       5
    Name: quality, dtype: int64



```python
features = train.columns[2:].drop(['type'])
# features = train.columns[2:].drop(['type','sulphates','total sulfur dioxide','residual sugar','free sulfur dioxide','citric acid','pH','fixed acidity'])
# features = train.columns[2:].drop(['type','total sulfur dioxide','pH'])
# features = train.columns[2:].drop(['type','new_col',	'new_col2'	,'new_col3'])
# features = train.columns[2:].drop(['type','white','red'])
features
```




    Index(['fixed acidity', 'volatile acidity', 'citric acid', 'residual sugar',
           'chlorides', 'free sulfur dioxide', 'total sulfur dioxide', 'density',
           'pH', 'sulphates', 'alcohol', 'red', 'white', 'new_col', 'new_col2',
           'new_col3'],
          dtype='object')




```python
test
```





  <div id="df-002a3f9b-c42c-4ef8-ae58-456f9dc7cc7d">
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
      <th>index</th>
      <th>fixed acidity</th>
      <th>volatile acidity</th>
      <th>citric acid</th>
      <th>residual sugar</th>
      <th>chlorides</th>
      <th>free sulfur dioxide</th>
      <th>total sulfur dioxide</th>
      <th>density</th>
      <th>pH</th>
      <th>sulphates</th>
      <th>alcohol</th>
      <th>type</th>
      <th>red</th>
      <th>white</th>
      <th>new_col</th>
      <th>new_col2</th>
      <th>new_col3</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0.429752</td>
      <td>0.153333</td>
      <td>0.289157</td>
      <td>0.092025</td>
      <td>0.056572</td>
      <td>0.034722</td>
      <td>0.154378</td>
      <td>0.128976</td>
      <td>0.125984</td>
      <td>0.089888</td>
      <td>0.521739</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.113120</td>
      <td>0.172815</td>
      <td>0.416000</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1</td>
      <td>0.785124</td>
      <td>0.233333</td>
      <td>0.349398</td>
      <td>0.019939</td>
      <td>0.101498</td>
      <td>0.048611</td>
      <td>0.078341</td>
      <td>0.256217</td>
      <td>0.251969</td>
      <td>0.151685</td>
      <td>0.144928</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.102319</td>
      <td>0.475767</td>
      <td>0.788211</td>
    </tr>
    <tr>
      <th>2</th>
      <td>2</td>
      <td>0.223140</td>
      <td>0.133333</td>
      <td>0.162651</td>
      <td>0.070552</td>
      <td>0.051581</td>
      <td>0.149306</td>
      <td>0.398618</td>
      <td>0.148255</td>
      <td>0.354331</td>
      <td>0.264045</td>
      <td>0.202899</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.157233</td>
      <td>0.301288</td>
      <td>0.202947</td>
    </tr>
    <tr>
      <th>3</th>
      <td>3</td>
      <td>0.280992</td>
      <td>0.046667</td>
      <td>0.234940</td>
      <td>0.018405</td>
      <td>0.056572</td>
      <td>0.069444</td>
      <td>0.352535</td>
      <td>0.148255</td>
      <td>0.614173</td>
      <td>0.140449</td>
      <td>0.289855</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.043567</td>
      <td>0.147682</td>
      <td>0.250947</td>
    </tr>
    <tr>
      <th>4</th>
      <td>4</td>
      <td>0.247934</td>
      <td>0.120000</td>
      <td>0.156627</td>
      <td>0.021472</td>
      <td>0.016639</td>
      <td>0.078125</td>
      <td>0.152074</td>
      <td>0.063621</td>
      <td>0.330709</td>
      <td>0.140449</td>
      <td>0.550725</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.132492</td>
      <td>0.410115</td>
      <td>0.226526</td>
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
    </tr>
    <tr>
      <th>995</th>
      <td>995</td>
      <td>0.272727</td>
      <td>0.340000</td>
      <td>0.012048</td>
      <td>0.026074</td>
      <td>0.121464</td>
      <td>0.079861</td>
      <td>0.202765</td>
      <td>0.199152</td>
      <td>0.637795</td>
      <td>0.174157</td>
      <td>0.246377</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.361746</td>
      <td>0.314131</td>
      <td>0.279579</td>
    </tr>
    <tr>
      <th>996</th>
      <td>996</td>
      <td>0.404959</td>
      <td>0.046667</td>
      <td>0.180723</td>
      <td>0.015337</td>
      <td>0.061564</td>
      <td>0.097222</td>
      <td>0.285714</td>
      <td>0.136688</td>
      <td>0.377953</td>
      <td>0.089888</td>
      <td>0.260870</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.025211</td>
      <td>0.270585</td>
      <td>0.377263</td>
    </tr>
    <tr>
      <th>997</th>
      <td>997</td>
      <td>0.413223</td>
      <td>0.386667</td>
      <td>0.156627</td>
      <td>0.016871</td>
      <td>0.108153</td>
      <td>0.010417</td>
      <td>0.039171</td>
      <td>0.192597</td>
      <td>0.322835</td>
      <td>0.292135</td>
      <td>0.173913</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.320362</td>
      <td>0.204186</td>
      <td>0.428632</td>
    </tr>
    <tr>
      <th>998</th>
      <td>998</td>
      <td>0.264463</td>
      <td>0.226667</td>
      <td>0.114458</td>
      <td>0.026074</td>
      <td>0.103161</td>
      <td>0.059028</td>
      <td>0.069124</td>
      <td>0.147484</td>
      <td>0.511811</td>
      <td>0.191011</td>
      <td>0.420290</td>
      <td>red</td>
      <td>1</td>
      <td>0</td>
      <td>0.243711</td>
      <td>0.644588</td>
      <td>0.256842</td>
    </tr>
    <tr>
      <th>999</th>
      <td>999</td>
      <td>0.388430</td>
      <td>0.086667</td>
      <td>0.156627</td>
      <td>0.132669</td>
      <td>0.041597</td>
      <td>0.250000</td>
      <td>0.313364</td>
      <td>0.142472</td>
      <td>0.244094</td>
      <td>0.084270</td>
      <td>0.492754</td>
      <td>white</td>
      <td>0</td>
      <td>1</td>
      <td>0.063356</td>
      <td>0.663610</td>
      <td>0.365474</td>
    </tr>
  </tbody>
</table>
<p>1000 rows × 18 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-002a3f9b-c42c-4ef8-ae58-456f9dc7cc7d')"
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
          document.querySelector('#df-002a3f9b-c42c-4ef8-ae58-456f9dc7cc7d button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-002a3f9b-c42c-4ef8-ae58-456f9dc7cc7d');
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
X = train[features]
y = train['quality']
```


```python
from sklearn.ensemble import RandomForestClassifier
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.ensemble import ExtraTreesClassifier
```


```python
models = []

rfc = RandomForestClassifier()

models.append(rfc)

gbc = GradientBoostingClassifier()

models.append(gbc)

etc = ExtraTreesClassifier()

models.append(etc)
```


```python
models
```




    [RandomForestClassifier(),
     GradientBoostingClassifier(),
     ExtraTreesClassifier()]




```python
grid_n_estimator = [100,150, 300]
grid_ratio = [.1, .25, .5, .75, 1.0]
grid_learn = [.01, .03, .05, .1, .25]
grid_max_depth = [2,3, 4, 6, 8, None]
grid_min_samples = [5, 10, .03, .05, .10]
grid_criterion = ['gini', 'entropy']
grid_bool = [True, False]
grid_seed = [0]
```


```python
best_models = {}
params=[
    {
        #RandomForestClassifier - http://scikit-learn.org/stable/modules/generated/sklearn.ensemble.RandomForestClassifier.html#sklearn.ensemble.RandomForestClassifier
        'n_estimators': grid_n_estimator, #default=10
        'criterion': grid_criterion, #default=”gini”
        'max_depth': grid_max_depth, #default=None
        'oob_score': [True,False], #default=False -- 12/31/17 set to reduce runtime -- The best parameter for RandomForestClassifier is {'criterion': 'entropy', 'max_depth': 6, 'n_estimators': 100, 'oob_score': True, 'random_state': 0} with a runtime of 146.35 seconds.
        'random_state': grid_seed
    },
    {   
        #GradientBoostingClassifier - http://scikit-learn.org/stable/modules/generated/sklearn.ensemble.GradientBoostingClassifier.html#sklearn.ensemble.GradientBoostingClassifier
        #'loss': ['deviance', 'exponential'], #default=’deviance’
        'learning_rate': [0.1,0.05], #default=0.1 -- 12/31/17 set to reduce runtime -- The best parameter for GradientBoostingClassifier is {'learning_rate': 0.05, 'max_depth': 2, 'n_estimators': 300, 'random_state': 0} with a runtime of 264.45 seconds.
        'n_estimators': [100,300], #default=100 -- 12/31/17 set to reduce runtime -- The best parameter for GradientBoostingClassifier is {'learning_rate': 0.05, 'max_depth': 2, 'n_estimators': 300, 'random_state': 0} with a runtime of 264.45 seconds.
        #'criterion': ['friedman_mse', 'mse', 'mae'], #default=”friedman_mse”
        'max_depth': [2,3], #default=3   
        'random_state': grid_seed
    },
    {
      'n_estimators': grid_n_estimator, #default=10
      'criterion': grid_criterion, #default=”gini”
      'max_depth': [2,3, 4, None], #default=None
      'random_state': grid_seed  
    }
    ]

for i,model in enumerate(models):
  model = GridSearchCV(model,param_grid = params[i], cv=7, return_train_score = True, verbose=2)

  model.fit(X,y)

  best_models[i] = model.best_estimator_

```


```python
best_models
```




    {0: RandomForestClassifier(n_estimators=300, oob_score=True, random_state=0),
     1: GradientBoostingClassifier(n_estimators=300, random_state=0),
     2: ExtraTreesClassifier(criterion='entropy', n_estimators=300, random_state=0)}




```python
pred0 = best_models[0].predict(test[features])
pred1 = best_models[1].predict(test[features])
pred2 = best_models[2].predict(test[features])
pred = pd.DataFrame({'pred0':pred0,'pred1':pred1,'pred2':pred2})
pred
```





  <div id="df-a7ec8fe5-0d5c-4dfd-b665-8642a372d82d">
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
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
    </tr>
    <tr>
      <th>1</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>2</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
    </tr>
    <tr>
      <th>3</th>
      <td>5</td>
      <td>6</td>
      <td>5</td>
    </tr>
    <tr>
      <th>4</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>995</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
    </tr>
    <tr>
      <th>996</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>997</th>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>998</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>999</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
  </tbody>
</table>
<p>1000 rows × 3 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-a7ec8fe5-0d5c-4dfd-b665-8642a372d82d')"
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
          document.querySelector('#df-a7ec8fe5-0d5c-4dfd-b665-8642a372d82d button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-a7ec8fe5-0d5c-4dfd-b665-8642a372d82d');
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
pred['pred_hard'] = pred.mode(axis=1)[0].astype(int)
pred
```





  <div id="df-b2b06772-daa1-4bb1-911f-e5a7e91a4242">
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
      <th>pred_hard</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>1</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>2</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>3</th>
      <td>5</td>
      <td>6</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>4</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>...</th>
      <td>...</td>
      <td>...</td>
      <td>...</td>
      <td>...</td>
    </tr>
    <tr>
      <th>995</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>996</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>997</th>
      <td>5</td>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>998</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>999</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
  </tbody>
</table>
<p>1000 rows × 4 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-b2b06772-daa1-4bb1-911f-e5a7e91a4242')"
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
          document.querySelector('#df-b2b06772-daa1-4bb1-911f-e5a7e91a4242 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-b2b06772-daa1-4bb1-911f-e5a7e91a4242');
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
pred0 = best_models[0].predict_proba(test[features])
pred1 = best_models[1].predict_proba(test[features])
pred2 = best_models[2].predict_proba(test[features])
pred_soft = pd.DataFrame(pred0+pred1+pred2/3)
pred_soft.head()
```





  <div id="df-ddb87f59-ab0f-4aa3-8800-38e71f1593d9">
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
      <th>2</th>
      <th>3</th>
      <th>4</th>
      <th>5</th>
      <th>6</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>1.556208e-02</td>
      <td>0.233188</td>
      <td>0.584622</td>
      <td>0.831922</td>
      <td>0.615731</td>
      <td>0.052308</td>
      <td>1.165414e-08</td>
    </tr>
    <tr>
      <th>1</th>
      <td>1.111334e-02</td>
      <td>0.309182</td>
      <td>0.622020</td>
      <td>1.293987</td>
      <td>0.093276</td>
      <td>0.003755</td>
      <td>1.073159e-09</td>
    </tr>
    <tr>
      <th>2</th>
      <td>3.333911e-03</td>
      <td>0.061468</td>
      <td>0.947784</td>
      <td>1.156639</td>
      <td>0.146282</td>
      <td>0.017827</td>
      <td>8.611661e-11</td>
    </tr>
    <tr>
      <th>3</th>
      <td>1.035068e-06</td>
      <td>0.101571</td>
      <td>1.034493</td>
      <td>0.979610</td>
      <td>0.207639</td>
      <td>0.010019</td>
      <td>2.414811e-08</td>
    </tr>
    <tr>
      <th>4</th>
      <td>2.141515e-22</td>
      <td>0.047925</td>
      <td>0.109071</td>
      <td>0.922461</td>
      <td>1.193087</td>
      <td>0.048566</td>
      <td>1.222222e-02</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-ddb87f59-ab0f-4aa3-8800-38e71f1593d9')"
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
          document.querySelector('#df-ddb87f59-ab0f-4aa3-8800-38e71f1593d9 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-ddb87f59-ab0f-4aa3-8800-38e71f1593d9');
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
pred['pred_soft']= pd.DataFrame(np.argmax(np.array(pred_soft), axis=1))+3
pred
```





  <div id="df-3f90befc-5e79-440f-8602-b7e7e5ed1d8b">
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
      <th>pred_hard</th>
      <th>pred_soft</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>1</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>2</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>3</th>
      <td>5</td>
      <td>6</td>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>4</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
      <td>6</td>
      <td>7</td>
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
      <th>995</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
      <td>6</td>
      <td>5</td>
    </tr>
    <tr>
      <th>996</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>997</th>
      <td>5</td>
      <td>5</td>
      <td>5</td>
      <td>5</td>
      <td>5</td>
    </tr>
    <tr>
      <th>998</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
    <tr>
      <th>999</th>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
      <td>6</td>
    </tr>
  </tbody>
</table>
<p>1000 rows × 5 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-3f90befc-5e79-440f-8602-b7e7e5ed1d8b')"
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
          document.querySelector('#df-3f90befc-5e79-440f-8602-b7e7e5ed1d8b button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-3f90befc-5e79-440f-8602-b7e7e5ed1d8b');
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
pred[pred['pred_hard']!=pred['pred_soft']]
```





  <div id="df-f7141611-c81a-491f-b535-afefa2fd9d6b">
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
      <th>pred_hard</th>
      <th>pred_soft</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>11</th>
      <td>5</td>
      <td>6</td>
      <td>5</td>
      <td>5</td>
      <td>6</td>
    </tr>
    <tr>
      <th>28</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
      <td>6</td>
      <td>5</td>
    </tr>
    <tr>
      <th>37</th>
      <td>6</td>
      <td>8</td>
      <td>5</td>
      <td>5</td>
      <td>8</td>
    </tr>
    <tr>
      <th>53</th>
      <td>4</td>
      <td>5</td>
      <td>4</td>
      <td>4</td>
      <td>5</td>
    </tr>
    <tr>
      <th>80</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
      <td>6</td>
      <td>7</td>
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
      <th>938</th>
      <td>4</td>
      <td>5</td>
      <td>4</td>
      <td>4</td>
      <td>5</td>
    </tr>
    <tr>
      <th>939</th>
      <td>6</td>
      <td>7</td>
      <td>6</td>
      <td>6</td>
      <td>7</td>
    </tr>
    <tr>
      <th>961</th>
      <td>6</td>
      <td>3</td>
      <td>6</td>
      <td>6</td>
      <td>3</td>
    </tr>
    <tr>
      <th>967</th>
      <td>6</td>
      <td>5</td>
      <td>6</td>
      <td>6</td>
      <td>5</td>
    </tr>
    <tr>
      <th>980</th>
      <td>6</td>
      <td>3</td>
      <td>6</td>
      <td>6</td>
      <td>3</td>
    </tr>
  </tbody>
</table>
<p>90 rows × 5 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-f7141611-c81a-491f-b535-afefa2fd9d6b')"
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
          document.querySelector('#df-f7141611-c81a-491f-b535-afefa2fd9d6b button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-f7141611-c81a-491f-b535-afefa2fd9d6b');
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
submission['quality'] = pred['pred_hard']
submission.to_csv('submission_hard.csv',index=False)
```


```python
submission['quality'] = pred['pred_soft']
submission.to_csv('submission_soft.csv',index=False)
```
