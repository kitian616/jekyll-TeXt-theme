---
layout: article
title: Scikit-Learn API functions
tags: Python
sidebar:
  nav: docs-en
aside:
  toc: true
---

# Remarks
본 포스팅은 **Hands-On Machine Learning with Scikit-Learn & TensorFlow ([Auérlien Géron](https://github.com/ageron/handson-ml), [박해선(역)](https://github.com/rickiepark/handson-ml), 한빛미디어)** 를 기반으로 작성되었습니다.

---
# Import libraries
{% highlight python %}
import numpy as np
import pandas as pd
import matplotlib.pyplot as plot
%matplotlib inline
{% endhighlight %}

# I. Get the data
## 1. Read CSV
```Python
housing = pd.read_csv(csv_path)
```

## 2. Abstract
```Python
housing.head()
housing.info()
housing.describe()
housing.hist(bins=50, figsize=(20, 15))
housing['ocean_proximity'].value_couns()
```

## 3. Split stratified train and test set
```Python
from sklearn.model_selection import train_test_split

# In case, you should make strata
housing['income_cat'] = np.ceil(housing['median_income'] / 1.5)
housing['income_cat'].where(housing['income_cat'] < 5, 5.0, inplace=True)

train_set, test_set = train_test_split(housing, housing['income_cat'], test_size=0.2, random_state=42)
train_set.drop('income_cat', axis=1, inplace=True);  test_set.drop('income_cat', axis=1, inplace=True)
```

# II. Discover and visualize the data to gain insights
## 1. Detail plotting
```Python
ax = housing.plot(kind='scatter', x='longitude', y='latitude', alpha=0.4,
                  s=housing['population'], label='Population', c='median_house_value',
                  cmap=plt.get_cmap('jet'), colorbar=True, sharex=False)
ax.set(xlabel='Longitude', ylabel='Latitude')
```

## 2. Correlation analysis
```Python
from pandas.plotting import scatter_matrix

corr_mat = housing.corr()
cc = corr_mat['median_house_value'].sort_values(ascending=False)
attr = cc[:5]  # Top 5 correlation attributes
scatter_matrix(housing[attr])
```

## 3. Combine attributes
```Python
housing['rooms_per_household'] = housing['total_rooms'] / housing['households']
cc = corr_mat['median_house_value'].sort_values(ascending=False)  # Check more higher correlation
```

# III. Prepare the data for Machine Learning algorithms
## 1. Split attributes(features) and labels
```Python
housing = train_set.drop('median_house_value', axis=1)
housing_labels = train_set['median_house_value'].copy()
```

## 2. Process the missing values
```Python
sample_incomplete_rows = housing[housing.isnull().any(axis=1)]  # Check first

# Option 1
sample_incomplete_rows.dropna(subset=['total_bedrooms'])
# Option 2
sample_incomplete_rows.drop('total_bedrooms', axis=1)
# Option 3 (※ After splitting num, cat attributes)
from sklearn.impute import SimpleImputer
imputer = SimpleImputer(strategy='median')  # Storing the median is important!
X = imputer.fit_transform(housing_num)
imputer.statistics_  # Check median
housing_tr = pd.DataFrame(X, columns=num_attr, index=housing.index)
```

## 3. Split number and categorical attributes
```Python
cat_attr = ['ocean_proxmimy', ...]
num_attr = [attr for attr in housing.columns if attr not in cat_attr]
housing_cat = housing.drop(cat_attr)
housing_num = housing[num_attr]
```

## 4. Preprocess pipeline
```Python
from sklearn.compose import ColumnTransformer
from sklearn.base import BaseEstimator, TransformerMixin
from sklearn.pipeline import pipeline
from sklearn.preprocessing import StandardScaler
from functools import partial
from enum import IntEnum

idx_list = list(X_train)
Idx2enum = lambda: IntEnum('Idx', ' '.join(idx_list), start=0)
Idx = Idx2enum()

class AttributeAdder(BaseEstimator, TransformerMixin):
    def __init__(self, add_bedrooms_per_room=True):
        self.add_bedrooms_per_room = add_bedrooms_per_room
    def fit(self, X, y=None):
        return self
    def transform(self, X, y=None):
        rooms_per_houhold = X[:, Idx.total_rooms] / X[:, Idx.households]
        X = np.c_[X, rooms_per_household]
        if self.add_bedrooms_per_room:
            bedrooms_per_room = X[:, Idx.total_bedrooms] / X[:, Idx.total_rooms]
            idx_list.extend(['rooms_per_household', 'bedrooms_per_room'])
            return np.c_[X, bedrooms_per_room]
        idx_list.extend(['rooms_per_houhold'])
        return X

num_pipeline = Pipeline([
  ('imputer', SimpleImputer()),  # DataFrame → ndarray
  ('attr_adder', AttributeAdder()),
  ('std_scaler', StandardScaler())
  ])

full_pipeline = ColumnTransformer([
  ('num_pipeline', num_pipeline, num_attr),
  ('cat_encoder', OneHotEncoder(categories='auto'), cat_attr)
  ])

X_train_pp = full_pipeline.fit_transform(X_train)
idx = Idx2enum()
```

# IV. Select and train a model
## 1. Linear regression
```Python
from sklearn.linear_model import LinearRegression

lin_reg = LinearRegression()
lin_reg.fit(housing_prepared, housing_labels)
predictions = lin_reg.predict(housing_prepared)
```

## 2. Decision tree
```Python
from sklearn.tree import DecisionTreeRegressor

tree_reg = DecisionTreeRegressor(random_state=42)
tree_reg.fit(housing_prepared, housing_labels)
predictions = tree_reg.predict(housing_prepared)
```

## 3. RandomForest
```Python
from sklearn.ensemble import RandomForestRegressor

forest_reg = RandomForestRegressor(n_estimators=10, random_state=42)
forest_reg.fit(housing_prepared, housing_labels)
predictions = forest_reg.predict(housing_prepared)
```

## 4. SVM
```Python
from sklearn.svm import SVR

svm_reg = SVR(kernel='linear')  # 'rbf'
svm_reg.fit(housing_prepared, housing_labels)
predictions = svm_reg.predict(housing_prepared)
```


## 5. Metrics
```Python
from sklearn.metrics import mean_squared_error, mean_absolute_error

lin_mse = mean_squared_error(housing_labels, predictions)
lin_rmse = np.sqrt(lin_mse)
lin_mae = mean_absolute_error(housing_labels, predictions)
```

# V. Fine-tune the model
## 1. Cross validation
```Python
from sklearn.model_selection import cross_val_score

scores = cross_val_score(tree_reg, housing_prepared, housing_labels, scoring='neg_mean_squared_error', cv=10)
tree_rmse_scores = np.sqrt(-scores)

def display_scores(scores):
    print('Scores:', scores())
    print("Mean:", scores.mean())
    print("Standard deviation:", scores.std())

display_scores(tree_rmse_scores)
```

## 2. Grid / Random search
### 1) Grid search
```Python
from sklearn.model_selection import GridSearchCV

param_grid = [
    {'n_estimators': [3, 10, 30], 'max_features': [2, 4, 6, 8]},
    {'bootstrap': [False], 'n_estimators': [3, 10], 'max_features': [2, 3, 4]},
  ]

forest_reg = RandomForestRegressor(random_state=42)
grid_search = GridSearchCV(forest_reg, param_grid, cv=5,
                           scoring='neg_mean_squared_error', return_train_score=True)
grid_search.fit(housing_prepared, housing_labels)
```

### 2) Random search
```Python
from sklearn.model_selection import RandomSearchCV
from scipy.stats import randint

param_dist = {
  'n_estimators': randint(low=1, high=200),
  'max_features': randint(low=1, high=8)
}
forest_reg = RandomForestRegressor(random_state=42)
rnd_search = RandomizedSearchCV(forest_reg, param_distributions=param_dist, n_iter=10, cv=5,
                                scoring='neg_mean_squared_error', random_state=42)
rnd_search.fit(housing_prepared, housing_labels)
```

### 3) Evaluation
```Python
grid_search.best_params_  # equal to rnd_search
grid_search.best_estimator_
cvres = grid_search.cv_results_
for mean_score, params in zip(cvres['mean_test_score'], cvres['params']):
    print(np.sqrt(-mean_score), params)
pd.DataFrame(grid_search.cv_results_)
```

## 3. Feature importances
```Python
feature_importances = grid_search.best_estimator_.feature_importances_

extra_attribs = ["rooms_per_hhold", "bedrooms_per_room"]
cat_encoder = full_pipeline.named_transformers_["cat_encoder"]
cat_one_hot_attr = list(cat_encoder.categories_[0])
attributes = num_attr + extra_attr + cat_one_hot_attr
sorted(zip(feature_importances, attributes), reverse=True)
```

## 4. Final scores
### 1) Final expectation value
```Python
final_model = grid_search.best_estimator_

X_test = test_set.drop('median_house_value', axis=1)
y_test = test_set['median_house_value'].copy()

X_test_prepared = full_pipeline.transform(X_test)
final_predictions = final_model.predict(X_test_prepared)
final_rmse = np.sqrt(mean_squared_error(y_test, final_predictions))
```

### 2) 95% confidence interval for the test RMSE
```Python
from scipy import stats

# t-scores
confidence = 0.95
squared_errors = (final_predictions - y_test) ** 2
mean = squared_errors.mean()
m = len(squared_errors)
np.sqrt(stats.t.interval(confidence, m - 1), loc=np.mean(squared_errors),
        scale=stats.sem(squared_errors))

# z-scores
zscore = stats.norm.ppf((1 + confidence) / 2)
zmargin = zscore * squared_errors.std(ddof=1) / np.sqrt(m)
np.sqrt(mean - zmargin), np.sqrt(mean + zmargin)
```

# VI. Model persistence
## 1. Use joblib
```Python
import joblib

joblib.dump(grid_search.best_estimator_, 'model_name.pkl')
model_loaded = joblib.load('model_name.pkl')
```


# Visualization
## 1. Set plot parameters
```Python
import matplotlib.pyplot as plt

plt.rcParams['axes.labelsize'] = 14
plt.rcParams['xtick.labelsize'] = 12
plt.rcParams['ytick.labelsize'] = 12

matplotlib.rc('font', family='NanumBarunGothic')  # 한글폰트 지원
plt.rcParams['axes.unicode_minus'] = False
```

## 2. Save figure
```Python
if os.path.isdir(dir_path):
    plt.savefig(img_path)
```
