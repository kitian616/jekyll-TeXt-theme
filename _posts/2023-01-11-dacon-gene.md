---
layout: single
title:  "[Dacon] 유전체 정보 분류 경진대회"
categories: Dacon
tag: [python, Dacon, ml]
toc: true
toc_sticky: true
author_profile: true
---
데이콘 유전체 정보 품종 분류 AI 경진대회

# Import


```python
import pandas as pd
import random
import os
import numpy as np
import matplotlib.pyplot as plt

from sklearn import preprocessing
from sklearn.ensemble import RandomForestClassifier
```


```python
class CFG:
    SEED = 42
```


```python
def seed_everything(seed):
    random.seed(seed)
    os.environ['PYTHONHASHSEED'] = str(seed)
    np.random.seed(seed)
seed_everything(CFG.SEED) # Seed 고정
```

# Data Load


```python
from google.colab import drive
drive.mount('/content/drive')
```

    Mounted at /content/drive



```python
train = pd.read_csv('/content/drive/MyDrive/data/유전체 정보 품종 분류/train.csv')
test = pd.read_csv('/content/drive/MyDrive/data/유전체 정보 품종 분류/test.csv')
```


```python
train.head()
```





  <div id="df-2002deb0-aae0-4aea-ae5f-7685e95e1f75">
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
      <th>father</th>
      <th>mother</th>
      <th>gender</th>
      <th>trait</th>
      <th>SNP_01</th>
      <th>SNP_02</th>
      <th>SNP_03</th>
      <th>SNP_04</th>
      <th>SNP_05</th>
      <th>...</th>
      <th>SNP_07</th>
      <th>SNP_08</th>
      <th>SNP_09</th>
      <th>SNP_10</th>
      <th>SNP_11</th>
      <th>SNP_12</th>
      <th>SNP_13</th>
      <th>SNP_14</th>
      <th>SNP_15</th>
      <th>class</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>TRAIN_000</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>G G</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
      <td>C A</td>
      <td>...</td>
      <td>A A</td>
      <td>G G</td>
      <td>A A</td>
      <td>G G</td>
      <td>A G</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>B</td>
    </tr>
    <tr>
      <th>1</th>
      <td>TRAIN_001</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>A G</td>
      <td>A G</td>
      <td>C A</td>
      <td>A A</td>
      <td>A A</td>
      <td>...</td>
      <td>A A</td>
      <td>G A</td>
      <td>A A</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
      <td>G G</td>
      <td>A A</td>
      <td>A A</td>
      <td>C</td>
    </tr>
    <tr>
      <th>2</th>
      <td>TRAIN_002</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>G G</td>
      <td>G G</td>
      <td>A A</td>
      <td>G A</td>
      <td>C C</td>
      <td>...</td>
      <td>A A</td>
      <td>G A</td>
      <td>G A</td>
      <td>A G</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>B</td>
    </tr>
    <tr>
      <th>3</th>
      <td>TRAIN_003</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>A A</td>
      <td>G G</td>
      <td>A A</td>
      <td>G A</td>
      <td>A A</td>
      <td>...</td>
      <td>G G</td>
      <td>A A</td>
      <td>G G</td>
      <td>A G</td>
      <td>G G</td>
      <td>G G</td>
      <td>G G</td>
      <td>A A</td>
      <td>G G</td>
      <td>A</td>
    </tr>
    <tr>
      <th>4</th>
      <td>TRAIN_004</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>G G</td>
      <td>G G</td>
      <td>C C</td>
      <td>A A</td>
      <td>C C</td>
      <td>...</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>G G</td>
      <td>A A</td>
      <td>A A</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
      <td>C</td>
    </tr>
  </tbody>
</table>
<p>5 rows × 21 columns</p>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-2002deb0-aae0-4aea-ae5f-7685e95e1f75')"
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
          document.querySelector('#df-2002deb0-aae0-4aea-ae5f-7685e95e1f75 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-2002deb0-aae0-4aea-ae5f-7685e95e1f75');
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
def get_x_y(df):
    if 'class' in df.columns:
        df_x = df.drop(columns=['id', 'class'])
        df_y = df['class']
        return df_x, df_y
    else:
        df_x = df.drop(columns=['id'])
        return df_x
```


```python
train_x, train_y = get_x_y(train)
test_x = get_x_y(test)
```


```python
train_x.head()
```





  <div id="df-273a67e2-01b7-4aa7-aa17-01c4763cf8cb">
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
      <th>father</th>
      <th>mother</th>
      <th>gender</th>
      <th>trait</th>
      <th>SNP_01</th>
      <th>SNP_02</th>
      <th>SNP_03</th>
      <th>SNP_04</th>
      <th>SNP_05</th>
      <th>SNP_06</th>
      <th>SNP_07</th>
      <th>SNP_08</th>
      <th>SNP_09</th>
      <th>SNP_10</th>
      <th>SNP_11</th>
      <th>SNP_12</th>
      <th>SNP_13</th>
      <th>SNP_14</th>
      <th>SNP_15</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>G G</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
      <td>C A</td>
      <td>A A</td>
      <td>A A</td>
      <td>G G</td>
      <td>A A</td>
      <td>G G</td>
      <td>A G</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>A G</td>
      <td>A G</td>
      <td>C A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
      <td>A A</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
      <td>G G</td>
      <td>A A</td>
      <td>A A</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>G G</td>
      <td>G G</td>
      <td>A A</td>
      <td>G A</td>
      <td>C C</td>
      <td>G G</td>
      <td>A A</td>
      <td>G A</td>
      <td>G A</td>
      <td>A G</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
    </tr>
    <tr>
      <th>3</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>A A</td>
      <td>G G</td>
      <td>A A</td>
      <td>G A</td>
      <td>A A</td>
      <td>G G</td>
      <td>G G</td>
      <td>A A</td>
      <td>G G</td>
      <td>A G</td>
      <td>G G</td>
      <td>G G</td>
      <td>G G</td>
      <td>A A</td>
      <td>G G</td>
    </tr>
    <tr>
      <th>4</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>G G</td>
      <td>G G</td>
      <td>C C</td>
      <td>A A</td>
      <td>C C</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>A A</td>
      <td>G G</td>
      <td>A A</td>
      <td>A A</td>
      <td>A G</td>
      <td>A A</td>
      <td>G A</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-273a67e2-01b7-4aa7-aa17-01c4763cf8cb')"
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
          document.querySelector('#df-273a67e2-01b7-4aa7-aa17-01c4763cf8cb button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-273a67e2-01b7-4aa7-aa17-01c4763cf8cb');
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




# Data Pre-processing
## Label-Encoding


```python
class_le = preprocessing.LabelEncoder()
snp_le = preprocessing.LabelEncoder()
snp_col = [f'SNP_{str(x).zfill(2)}' for x in range(1,16)]
```


```python
snp_col
```




    ['SNP_01',
     'SNP_02',
     'SNP_03',
     'SNP_04',
     'SNP_05',
     'SNP_06',
     'SNP_07',
     'SNP_08',
     'SNP_09',
     'SNP_10',
     'SNP_11',
     'SNP_12',
     'SNP_13',
     'SNP_14',
     'SNP_15']




```python
snp_data = []
for col in snp_col:
    snp_data += list(train_x[col].values)
```


```python
train_y = class_le.fit_transform(train_y)
snp_le.fit(snp_data)
```




    LabelEncoder()




```python
for col in train_x.columns:
    if col in snp_col:
        train_x[col] = snp_le.transform(train_x[col])
        test_x[col] = snp_le.transform(test_x[col])
```


```python
train_x.head()
```





  <div id="df-8bb0dee1-1ba7-44b1-8cb5-0a2bacd1fb72">
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
      <th>father</th>
      <th>mother</th>
      <th>gender</th>
      <th>trait</th>
      <th>SNP_01</th>
      <th>SNP_02</th>
      <th>SNP_03</th>
      <th>SNP_04</th>
      <th>SNP_05</th>
      <th>SNP_06</th>
      <th>SNP_07</th>
      <th>SNP_08</th>
      <th>SNP_09</th>
      <th>SNP_10</th>
      <th>SNP_11</th>
      <th>SNP_12</th>
      <th>SNP_13</th>
      <th>SNP_14</th>
      <th>SNP_15</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
      <td>2</td>
      <td>0</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
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
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>2</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
      <td>5</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
      <td>3</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
      <td>4</td>
      <td>1</td>
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
      <td>1</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>5</td>
      <td>5</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>5</td>
      <td>5</td>
      <td>5</td>
      <td>0</td>
      <td>5</td>
    </tr>
    <tr>
      <th>4</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>5</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-8bb0dee1-1ba7-44b1-8cb5-0a2bacd1fb72')"
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
          document.querySelector('#df-8bb0dee1-1ba7-44b1-8cb5-0a2bacd1fb72 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-8bb0dee1-1ba7-44b1-8cb5-0a2bacd1fb72');
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
from sklearn.model_selection import train_test_split

x_train,x_valid,y_train,y_valid = train_test_split(train_x,train_y,test_size = 0.2,random_state=42)
```


```python
x_train.head()
```





  <div id="df-0d36f4e5-c214-4180-b108-6fc69f0c035f">
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
      <th>father</th>
      <th>mother</th>
      <th>gender</th>
      <th>trait</th>
      <th>SNP_01</th>
      <th>SNP_02</th>
      <th>SNP_03</th>
      <th>SNP_04</th>
      <th>SNP_05</th>
      <th>SNP_06</th>
      <th>SNP_07</th>
      <th>SNP_08</th>
      <th>SNP_09</th>
      <th>SNP_10</th>
      <th>SNP_11</th>
      <th>SNP_12</th>
      <th>SNP_13</th>
      <th>SNP_14</th>
      <th>SNP_15</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>179</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>1</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
      <td>2</td>
      <td>1</td>
      <td>4</td>
      <td>0</td>
      <td>4</td>
      <td>1</td>
      <td>1</td>
      <td>5</td>
      <td>5</td>
      <td>3</td>
      <td>5</td>
    </tr>
    <tr>
      <th>115</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>1</td>
      <td>2</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>4</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>4</td>
      <td>1</td>
      <td>0</td>
      <td>0</td>
    </tr>
    <tr>
      <th>96</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>1</td>
      <td>1</td>
      <td>3</td>
      <td>4</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
      <td>5</td>
      <td>2</td>
      <td>4</td>
    </tr>
    <tr>
      <th>233</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>5</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>1</td>
      <td>0</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>4</td>
    </tr>
    <tr>
      <th>60</th>
      <td>0</td>
      <td>0</td>
      <td>0</td>
      <td>2</td>
      <td>5</td>
      <td>1</td>
      <td>3</td>
      <td>0</td>
      <td>3</td>
      <td>5</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>5</td>
      <td>1</td>
      <td>0</td>
      <td>5</td>
      <td>0</td>
      <td>0</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-0d36f4e5-c214-4180-b108-6fc69f0c035f')"
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
          document.querySelector('#df-0d36f4e5-c214-4180-b108-6fc69f0c035f button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-0d36f4e5-c214-4180-b108-6fc69f0c035f');
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
print(train_x.shape)
print(x_train.shape)
```

    (262, 19)
    (209, 19)



```python
def data_preprocessing():
  train = pd.read_csv('/content/drive/MyDrive/data/유전체 정보 품종 분류/train.csv')
  test = pd.read_csv('/content/drive/MyDrive/data/유전체 정보 품종 분류/test.csv')

  train_x, train_y = get_x_y(train)
  test_x = get_x_y(test)
  
  class_le = preprocessing.LabelEncoder()
  snp_le = preprocessing.LabelEncoder()
  snp_col = [f'SNP_{str(x).zfill(2)}' for x in range(1,16)]

  snp_data = []
  for col in snp_col:
      snp_data += list(train_x[col].values)

  train_y = class_le.fit_transform(train_y)
  snp_le.fit(snp_data)

  for col in train_x.columns:
      if col in snp_col:
          train_x[col] = snp_le.transform(train_x[col])
          test_x[col] = snp_le.transform(test_x[col])

  x_train,x_valid,y_train,y_valid = train_test_split(train_x,train_y,test_size = 0.2,random_state=42)

  return x_train,x_valid,y_train,y_valid,test_x

```

# Data Engineering

## Feature Seletion

### Feature Importance


```python
model = RandomForestClassifier(random_state=CFG.SEED)
model.fit(x_train, y_train)
```




    RandomForestClassifier(random_state=42)




```python
from sklearn.metrics import f1_score

y_pred = model.predict(x_valid)
#f1_score(y_pred,y_valid, average='macro')

```


```python
def make_plot():
  f1_sco = f1_score(y_pred,y_valid,average='macro')

  
  validation = pd.DataFrame({'y_valid':y_valid,'y_pred':y_pred})

  validation_count = pd.DataFrame(validation['y_valid'].value_counts().sort_index())
  validation_count.loc[validation['y_pred'].value_counts().sort_index().index,'y_pred']=validation['y_pred'].value_counts().sort_index().values
  validation_count = validation_count.fillna(0)

  x = validation_count.index
  y_valid_count = validation_count['y_valid']
  y_pred_count = validation_count['y_pred']

  width = 0.35

  plt.figure(dpi=100)

  plt.title('F1-score: '+str(f1_sco)[:6])
  plt.xlabel('quality')
  plt.ylabel('count')

  p1 = plt.bar([idx-width/2 for idx in x],y_valid_count,width,label = 'real')
  p2 = plt.bar([idx+width/2 for idx in x],y_pred_count,width,label = 'pred')

  plt.legend()
  plt.show()
```


```python
make_plot()
```


    
![png](https://user-images.githubusercontent.com/122070432/212539548-f58a9b73-c64d-4113-87b9-a48c42b215ff.png)
    


클래스 1과 2에서 혼동하는 모습


```python
feature_imp = pd.DataFrame(model.feature_importances_,index = train_x.columns,columns = ['importance'])
feature_imp.sort_values(by='importance',ascending=False)
```





  <div id="df-2eb3d69b-f77c-4de9-847f-e34687666ee8">
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
      <th>importance</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>trait</th>
      <td>0.149997</td>
    </tr>
    <tr>
      <th>SNP_05</th>
      <td>0.110934</td>
    </tr>
    <tr>
      <th>SNP_10</th>
      <td>0.098379</td>
    </tr>
    <tr>
      <th>SNP_07</th>
      <td>0.087005</td>
    </tr>
    <tr>
      <th>SNP_08</th>
      <td>0.083492</td>
    </tr>
    <tr>
      <th>SNP_04</th>
      <td>0.071618</td>
    </tr>
    <tr>
      <th>SNP_12</th>
      <td>0.056224</td>
    </tr>
    <tr>
      <th>SNP_14</th>
      <td>0.047945</td>
    </tr>
    <tr>
      <th>SNP_03</th>
      <td>0.047389</td>
    </tr>
    <tr>
      <th>SNP_02</th>
      <td>0.046950</td>
    </tr>
    <tr>
      <th>SNP_01</th>
      <td>0.046350</td>
    </tr>
    <tr>
      <th>SNP_15</th>
      <td>0.038371</td>
    </tr>
    <tr>
      <th>SNP_11</th>
      <td>0.035263</td>
    </tr>
    <tr>
      <th>SNP_13</th>
      <td>0.034316</td>
    </tr>
    <tr>
      <th>SNP_09</th>
      <td>0.028967</td>
    </tr>
    <tr>
      <th>SNP_06</th>
      <td>0.016798</td>
    </tr>
    <tr>
      <th>mother</th>
      <td>0.000000</td>
    </tr>
    <tr>
      <th>gender</th>
      <td>0.000000</td>
    </tr>
    <tr>
      <th>father</th>
      <td>0.000000</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-2eb3d69b-f77c-4de9-847f-e34687666ee8')"
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
          document.querySelector('#df-2eb3d69b-f77c-4de9-847f-e34687666ee8 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-2eb3d69b-f77c-4de9-847f-e34687666ee8');
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




상관계수가 낮은 feature들이 많은 것을 알 수 있다. 


그러한 feature 들을 제외하고 학습한 뒤 성능을 확인해보자


```python
# 상관계수가 높은 상위 5개 feature
feature_5 = feature_imp.sort_values(by='importance',ascending=False).index[:5]
```


```python
feature_5
```




    Index(['trait', 'SNP_05', 'SNP_10', 'SNP_07', 'SNP_08'], dtype='object')




```python
x_train,x_valid,y_train,y_valid,test_x = data_preprocessing()

x_train = x_train[feature_5]
x_valid = x_valid[feature_5]

# 정확한 비교를 위해 똑같은 랜덤포레스트 모델 사용
model = RandomForestClassifier(random_state=CFG.SEED)
model.fit(x_train, y_train)
y_pred = model.predict(x_valid)
make_plot()
```


    
![png](https://user-images.githubusercontent.com/122070432/212539550-a449400d-1bda-421d-991c-ac8d0293ffcd.png)
    


학습에 포함시킬 feature 개수를 찾아보자


```python
score_list = []
for i in range(len(feature_imp)-1,0,-1):
  feature_tmp = feature_imp.sort_values(by='importance',ascending=False).index[:i]
  x_train,x_valid,y_train,y_valid,test_x = data_preprocessing()

  x_train = x_train[feature_tmp]
  x_valid = x_valid[feature_tmp]

  # 정확한 비교를 위해 똑같은 랜덤포레스트 모델 사용
  model = RandomForestClassifier(random_state=CFG.SEED)
  model.fit(x_train, y_train)

  y_pred = model.predict(x_valid)
  score_list.append((i+1,f1_score(y_pred,y_valid, average='macro')))
```


```python
score_list
```




    [(19, 0.9821551132463967),
     (18, 0.9821551132463967),
     (17, 1.0),
     (16, 0.9638888888888889),
     (15, 0.9451090781140042),
     (14, 0.9821551132463967),
     (13, 0.9638888888888889),
     (12, 0.9638888888888889),
     (11, 0.9638888888888889),
     (10, 0.9451090781140042),
     (9, 0.9464653397391901),
     (8, 0.9464653397391901),
     (7, 0.9464653397391901),
     (6, 0.9257142857142857),
     (5, 0.9646739130434782),
     (4, 0.9277777777777777),
     (3, 0.7947368421052632),
     (2, 0.5806451612903226)]



상위 17개의 feature을 사용하는 것이 가장 성능이 좋은 것으로 보임


```python
feature_tmp = feature_imp.sort_values(by='importance',ascending=False).index[:16]
x_train,x_valid,y_train,y_valid,test_x = data_preprocessing()

x_train = x_train[feature_tmp]
x_valid = x_valid[feature_tmp]


model = RandomForestClassifier(random_state=CFG.SEED)
model.fit(x_train, y_train)

y_pred = model.predict(x_valid)
```


```python
f1_score(y_pred,y_valid, average='macro')
```




    1.0



validation 셋에 대한 성능이 좋게 나왔으므로 전체데이터에 대한 학습을 통해 제출


```python
# 제출 함수
def submit(file_name,pred):
  submit = pd.read_csv('/content/drive/MyDrive/data/유전체 정보 품종 분류/sample_submission.csv')
  submit['class'] = class_le.inverse_transform(pred)
  submit.to_csv(f'/content/drive/MyDrive/Colab Notebooks/dacon/{file_name}.csv', index=False)
  print('Done')
```


```python
model = RandomForestClassifier(random_state=CFG.SEED)
model.fit(train_x[feature_tmp], train_y)

pred = model.predict(test_x[feature_tmp])
submit('rf_17_feature',pred)
```

    Done


결과를 확인해보았으나 베이스라인코드의 제출결과와 같은 결과가 나옴

릿지 모델에도 적용


```python
from sklearn.linear_model import RidgeClassifier

x_train,x_valid,y_train,y_valid,test_x = data_preprocessing()

model = RidgeClassifier(random_state=CFG.SEED)
model.fit(x_train, y_train)

pd.DataFrame(abs(model.coef_))
```





  <div id="df-099cfbe4-314d-4f20-9302-72e61168f5a9">
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
      <th>7</th>
      <th>8</th>
      <th>9</th>
      <th>10</th>
      <th>11</th>
      <th>12</th>
      <th>13</th>
      <th>14</th>
      <th>15</th>
      <th>16</th>
      <th>17</th>
      <th>18</th>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th>0</th>
      <td>0.0</td>
      <td>0.0</td>
      <td>0.0</td>
      <td>1.515546</td>
      <td>0.008958</td>
      <td>0.010171</td>
      <td>0.011026</td>
      <td>0.011176</td>
      <td>0.006956</td>
      <td>0.002418</td>
      <td>0.023188</td>
      <td>0.007393</td>
      <td>0.013038</td>
      <td>0.007025</td>
      <td>0.013207</td>
      <td>0.012130</td>
      <td>0.006696</td>
      <td>0.019875</td>
      <td>0.005807</td>
    </tr>
    <tr>
      <th>1</th>
      <td>0.0</td>
      <td>0.0</td>
      <td>0.0</td>
      <td>0.352045</td>
      <td>0.040708</td>
      <td>0.055363</td>
      <td>0.086292</td>
      <td>0.110782</td>
      <td>0.237609</td>
      <td>0.005510</td>
      <td>0.124689</td>
      <td>0.114634</td>
      <td>0.083659</td>
      <td>0.115097</td>
      <td>0.043387</td>
      <td>0.019054</td>
      <td>0.052472</td>
      <td>0.110547</td>
      <td>0.042401</td>
    </tr>
    <tr>
      <th>2</th>
      <td>0.0</td>
      <td>0.0</td>
      <td>0.0</td>
      <td>1.867591</td>
      <td>0.031750</td>
      <td>0.065533</td>
      <td>0.075266</td>
      <td>0.121959</td>
      <td>0.230653</td>
      <td>0.003092</td>
      <td>0.101501</td>
      <td>0.107241</td>
      <td>0.096697</td>
      <td>0.108072</td>
      <td>0.056594</td>
      <td>0.006924</td>
      <td>0.045776</td>
      <td>0.090672</td>
      <td>0.036594</td>
    </tr>
  </tbody>
</table>
</div>
      <button class="colab-df-convert" onclick="convertToInteractive('df-099cfbe4-314d-4f20-9302-72e61168f5a9')"
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
          document.querySelector('#df-099cfbe4-314d-4f20-9302-72e61168f5a9 button.colab-df-convert');
        buttonEl.style.display =
          google.colab.kernel.accessAllowed ? 'block' : 'none';

        async function convertToInteractive(key) {
          const element = document.querySelector('#df-099cfbe4-314d-4f20-9302-72e61168f5a9');
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
average_feature_effects = abs(model.coef_ )* np.asarray(x_train.mean(axis=0)).ravel()
feature_list = []
for i in range(3):
  # 각 클래스 별 상관관계가 높은 feature 상위 5개씩 학습할 feature에 추가
  tmp = np.argsort(average_feature_effects[i])[-10:][::-1]
  for j in tmp:
    feature_list.append(j)
feature_list = list(set(feature_list))

x_train,x_valid,y_train,y_valid,test_x = data_preprocessing()

x_train = x_train[x_train.columns[feature_list]]
x_valid = x_valid[x_valid.columns[feature_list]]

model = RidgeClassifier(random_state=CFG.SEED)
model.fit(x_train, y_train)

y_pred = model.predict(x_valid)
f1_score(y_pred,y_valid, average='macro')
```




    0.9821551132463967




```python
make_plot()
```


    
![png](https://user-images.githubusercontent.com/122070432/212539551-6e165c96-cb87-49e6-a2a5-bc48bac4aca0.png)
    


validation 셋에 대한 성능은 잘 나오지 않지만 제출결과 확인을 위해 제출


```python
model = RidgeClassifier(random_state=CFG.SEED)
model.fit(train_x[train_x.columns[feature_list]], train_y)

pred = model.predict(test_x[test_x.columns[feature_list]])
submit('ridge_10_feature',pred)
```

    Done


98점으로 약 2점가량 향상된 점수를 보임!

릿지모델을 통한 feature selection으로 성능향상을 노려봐야 할 것 같음
