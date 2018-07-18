# Machine Learning
- [Class 1: Forming of learning problem](#class-1-forming-of-learning-problem) 
- [Class 2: Decision Trees](#class-2-decision-trees)
- [Class 3: k-Nearest Neighbor & Instance-based Learning](#class-3-k-nearest-neighbor--instance-based-learning)
- [Class 4: Evaluation](#class-4-evaluation)
- [Class 5: Linear Regression](#class-5-linear-regression)
- [Class 6: Linear Classification: Perceptron](#class-6-linear-classification-perceptron)
- [Class 7: Logistic Regression](#class-7-logistic-regression)
## Class 1: Forming of learning problem
### Train set & Test set
- We generally assume that the training and test examples are independently drawn from the same overall distribution of data
- We call this "i.i.d" which stands for "independent and identically distributed"
- If examples are not independent, requires **collective classificartion**
- If test distribution is different, requires **transfer learning**
### ML in a Nutshell
- Three components:  
1. **Representarepresentation**
2. **Optimization**  
3. **Evaluation**
### ML in practice
- Understand domain, prior knowledge, and goals
- Data integration, selection, cleaning, pre-processing, etc.
- Learn models
- Interpret results
- Consolidate and deploy discovered knowledge
### Lessons Learned about Learning
- Different search methods' advantages and disadvantages
## Class 2: Decision Trees
### Function Approximation
#### Problem Setting
- Set of possible instances X
- Set of possible labels Y
- Unknown target function f:X-Y
- Set of function hypotheses H={h|h:X-Y}
#### Input:
- Training examples of unknown target function f  
*{(x1,y1),(x2,y2),...,(xn,yn)}*
#### Output:
- Hypothesis h belongs to H best approximates f
### Decision Tree Learning
#### Problem Setting:
- Set of possible instances X(contains feature vectors)
*such as <Humidity=low,Wind=weak,Outlook=rain,Temp=hot>*
- Unknown target function f:X-Y(Y is discrete)
- Set of function hypotheses H
*Each hypotheses h is a decision tree*
*trees sorts x to leaf, which assigns y*
![Decision Tree Example](https://image.slidesharecdn.com/l7decision-treetable-130318112451-phpapp01/95/l7-decision-tree-table-21-638.jpg?cb=1363605932)
### Stages of batch machine learning
#### Given __labeled__ data
- X & Y
#### Train the model:
- model<-classifier.train(X,Y)
#### Apply the model to new data:
- y<-model.predict(x)
### Decision Tree Induced Partition
- What a dicision tree really do is dividing the instance(feature) space into axis-parallel hyper-rectangles
- Each rectanglar region is labeled with one label
### Expressiveness
- Decision trees can represent any boolean function of the input attributes
- In the worst case, the tree will require exponentially many nodes
- As the depth increases, the hypothesis space grows
### Preference bias: Ockham's razor
- Entities are not to be multiplied beyond necessity
- In this case, we are trying to find the simplist tree that fits the features
### Basic Algorithm for Top-Down Induction of Decision Trees
ID3 Algorithm:
```
node = root of decision tree
Main loop:  
1. A <- the "best" decision attribute for the next node
2. Assign A as decision attribute for node
3. For each value of A, create a new descendant of node
4. Sort traning examples to leaf nodes
5. If training examples are perfectly classified, stop.
Else, recurse over new leaf nodes
```
How do we choose which attributes are best?
### Choosing the Best Attribute
**Key problem:** Choosing which attribute to split a given set of examples
#### Some possibilities are:
 1. **Ramdom**
 2. **Least-Values**: Attribute with the smallest number of possible values
 3. **Most-Values**: Attribute with the largest number of possible values
 4. **Max-Gain**: Attribute with the largest expected information gained(*ID3* used)

 **Idea:** a good attribute splits the examples into subsets that are ideally "all positive" or "all negative"
 ### Information Gain
 #### Impurity/Entropy(informal)
 - **Entropy:** Measures the level of impurity in a group
 ![Compute entropy](http://www.saedsayad.com/images/Entropy_3.png)  
It is the expected number of bits needed to encode a randomly drawn value of X(under most efficient code)
*From information theory(Huffman code)*
![](https://i.stack.imgur.com/1fEJE.png)  
Information Gain = entropy(parent)-[(weighted)average entropy(children)]

## Class 3: k-Nearest Neighbor & Instance-based Learning
### Stages of batch machine learning
#### Given __labeled__ data
- X & Y
#### Train the model:
- model<-classifier.train(X,Y)
#### Apply the model to new data:
- y<-model.predict(x)
## Class 4: Evaluation
### Classification Metrics
accuracy=# correct predictions/# test instance
error=1-accuracy
### Confusion Matrix
- Given a dataset of P positive instances and N negative instances

|Predicted|class|matrix
| ------ | ------ | ----|
|   | Yes | NO |
| Yes | TP | FN |
| No  | FP | TN |
- accuracy=(TP+TN)/(P+N)
- precision=TP/(TP+FP)
- recall=TP/(TP+FN)

### Training Data and Test Data
- Traning data: data used to build the model
- Test data: new data, not used in the training
- Need to avoid overfitting
### Comparing Classifiers
Idea:divide full data set into n parts
![n-fold cross validation](http://chrisjmccormick.files.wordpress.com/2013/07/10_fold_cv.png)  
### Optimizing Model Parameters
loop for t trials
1. randomize data set
2. perform k-fold CV(Compute learning curve over each training/testing split)   

Finally, compute statistics over t*k test performance
### Learning Curve
- Shows performance versus the # training examples
- Compute learning curve over each training/testing split (taking average to have the learning curve)

## Class 5: Linear Regression
from intro:
![Comparison of Learning Methods](https://image.slidesharecdn.com/hutter1-120821071909-phpapp02/95/introduction-to-statistical-machine-learning-65-728.jpg?cb=1345533592)
### Regression
Given:
- Data X
- Corresponding labels Y
- Trying to figure out a curve fitting the data
### Linear Regression
- Hypothesis
y'=a0x0+a1x1+a2x2+...+adxd=**a*****x**
Assume x0=1
- Fit model by minimizing sum of squared errors
- Cost Function (Objective function)
J(**a**)=(average of squares when the predictor is y'=**a*****x**)/2
- Fit by solving **min** J(**a**)
### Intuition Behind Cost Function
There is only one minimum.
![](https://qph.ec.quoracdn.net/main-qimg-7e46f059685ebb2d7c6b365f0fff9114)
### Basic Search Procedure
- Choose initial value for **a**
- Before we reach a minimum:
Choose a new value for **a** to reduce J(**a**)
### Gradient Descent	 

1.Initialize **a**
2.Repeat until convergence:
**a**=**a**- α *gradient(J(**a**))

α is called the learning rate (small,e.g. 0.5)
- Assume convergence when ||**a**'-**a**||(2-norm)<e
- To achieve simultaneous update:   
At the start of each GD iteration, compute **a*****x**   
Then use this stored value in the update step loop
### Choosing α
To see if gradient descent is working, print out the function value each iteration

### Regularization
#### Quality of fitting
avoid:
- underfitting(high bias) 
- overfitting(high variance) = model changed largely if new data added
#### Regularized Linear Regression
![](http://www.holehouse.org/mlclass/07_Regularization_files/Image%20[5].png)
lamda is the regularization coefficent
Gradient update:
![](http://image.xlgps.com/upload/d/cb/dcb2e44a54ae7ab431afab2dc15deb13.png)
**The regularization part pulls the vector Theta (a) to zero vector.**

## Class 6: Linear Classification: Perceptron
### Linear Classifiers
- A **hyperplane** partitions **R** into two half-spaces defined by the normal vector. **Assumed to pass through origin**
- **Linear classifiers**: represent decision boundary by hyperplane
### The perceptron
![](data:image/png;base64,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)   
- Update rule:   
![](https://i.stack.imgur.com/wCRmt.png)
### The Perceptron Cost Function
- It has 0/1 loss, can't use gradient method.
- New cost function: average of max(0,-y*x*theta)
- The new one has nice gradient.
### Perceptron Algorithm
- **Online learning** - the learning mode where the model update is performed each time a single observation is received (**update everytime**)
- **Batch learning** - the learning mode where the model update is performed after observing the entire training set (**compute average update then update**)
## Improving the Perceptron
For online training:
- The perceptron produces many theta's during training
- The algprithm simply uses the final one
- **Idea 1**: Use a combiantion of multiple perceptrons (i.e. neural networks)
- **Idea 2**: Use the intermediate theta's (i.e. voted perceptron & averaged perceptron)

## Class 7: Logistic Regression
### Classification Based on Probability
- Give the probability of the instance being that class
- Since the probability should be between 0 and 1, we use the logistic/sigmoid function
- h(x) estimated p(y=1|x,theta)
- ![](https://i.ytimg.com/vi/ThmZU3dTIDo/maxresdefault.jpg)
# Interpretation
- log(p(y=1|**x**,**theta**)/p(y=0|**x**,**theta**))=**theta*****x**
- Hence we can find which feature xi is more important
**Note**: the odds in favor of an event is the quantity p/(1-p)
Hence the log odds is a linear function of x in logistic regression
### Deriving the cost Function via Maximum Likelihood Estimation
- LIkelihood of data is given by: l(theta)=Π(p(yi|xi;theta))
- theta=argmax l(theta)=argmax log(l(theta))
- ![](http://www.holehouse.org/mlclass/06_Logistic_Regression_files/Image%20[15].png)
- We could still regularize the cost function
### Multi-class Classification