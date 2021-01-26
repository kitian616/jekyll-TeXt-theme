---
layout: article
title: "Linear Regression - The simplest model"
show_title: true
tags: [ML, models]
---

In this article, we will talk about the simplest model we can apply to our data, a linear regression `LinearRegression()`. Remember, we talk about regression when our target variable is continuous, so that we are not predicting categories as in our `LogisticClassifier()`.

### Loading the dataset

[//]: # (Using RedCarpet as the syntax highlighter: Put the programming language after ```)
[//]: # (I prefer the ``` python method than the liquid  highlight python - endhighlight one. Much more readable in Vim + less stupid)
``` python
from sklearn.datasets import load_iris# Load Iris Data

iris = load_iris()
iris_df = pd.DataFrame(data= iris.data, columns= iris.feature_names)
target_df = pd.DataFrame(data= iris.target, columns= ['species'])


target_df['species'] = target_df['species'].map({0:"setosa", 1:"versicolor", 2:"virginica"})        # apply map converter (dicts), alternatively: apply(function)  

iris_df = pd.concat([iris_df, target_df], axis= 1)      # concatenate the DataFrames 
```

we can get now a brief visual of the columns.

`iris_df.describe()`

.describe() generates descriptive statistics that summarize the central tendency, dispersion and shape of a dataset’s distribution, excluding `NaN` values.

`iris_df.info()`

.info() prints a concise summary of a DataFrame.

`sns.pairplot(iris_df, hue= 'species')`

### Problem Statement: Predict the sepal length (cm) of the iris flowers

Now, our objective is to predict the target `sepal length (cm)` using the rest of features. To do so, we have labeled the `species` column into a categorical variable, so our model can understand that in a non-number way. Remember, not all the models let us to do this technique.

``` python
# Converting Objects to Numerical dtype
iris_df.drop('species', axis= 1, inplace= True)
target_df = pd.DataFrame(columns= ['species'], data= iris.target)
iris_df = pd.concat([iris_df, target_df], axis= 1)# Variables
X= iris_df.drop(labels= 'sepal length (cm)', axis= 1)
y= iris_df['sepal length (cm)']

# Splitting the Dataset
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size= 0.33, random_state= 101)

# Instantiating LinearRegression() Model
lr = LinearRegression()

# Training/Fitting the Model
lr.fit(X_train, y_train)

# Making Predictions
lr.predict(X_test)
pred = lr.predict(X_test)

# Evaluating Model's Performance
print('Mean Absolute Error:', mean_absolute_error(y_test, pred))
print('Mean Squared Error:', mean_squared_error(y_test, pred))
print('Mean Root Squared Error:', np.sqrt(mean_squared_error(y_test, pred)))
```

The results are:

```
Mean Absolute Error: 0.26498350887555133
Mean Squared Error: 0.10652500975036944
Mean Root Squared Error: 0.3263816933444176
```

    
