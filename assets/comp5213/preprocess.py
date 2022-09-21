"""
    For COMP5213 final project, to preprocess PMC005 dataset
    
    The code relies on nltk package to do the preprocessing, 
    to install nltk: sudo pip install -U nltk

    The processing are following the steps from https://github.com/kmpoon/hlta
    * Converting letters to lower case
    * Replace non-alphabet characters by space
    * Lemmatization
    * Remove words shorter than 3 characters
    * Remove stop-words

    Overall steps to preprocess data to bag-of-words:
    1. Unzip the dataset "PMC005XXXXXX.txt.tar.gz",
    2. Put the dataset directory "PMC005XXXXXX.txt/" under the same directory
    3. Run this code
    4. Then you can use the package provided in https://github.com/kmpoon/hlta
        to convert text files under extracted/ to bag-of-words representation:
        java -cp HLTA.jar:HLTA-deps.jar tm.text.Convert sample 2000 0 extracted
        (Note that I set the number of concatenations to 0, to only consider unigram,
            n-gram is possible, but take significantly more time)
    
    by Xiaopeng LI
    27/04/2017@HKUST
"""

import nltk
import sys
import re
from nltk.corpus import stopwords
import os
from glob import glob

# specifiy input directory and output directory
PATH = "PMC005XXXXXX.txt/"
outDir = "extracted/"

result = [y for x in os.walk(PATH) for y in glob(os.path.join(x[0], '*.txt'))]

def process(filepath):
    content = ""
    for line in open(filepath):
        if len(content)==0:
            content += line.strip()
        else:
            content += " " + line.strip()
    return content

num_docs = len(result)
index = 0
stops = set(stopwords.words("english"))
wnl = nltk.WordNetLemmatizer()
if not os.path.exists(outDir):
    os.makedirs(outDir)
for doc in result:
    index += 1
    progress = 1.0*index/num_docs*100
    sys.stdout.write('\r[%-10s] %0.2f%%' % ('#' * int(progress/10), progress))
    sys.stdout.flush()

    content = process(doc)
    content = re.sub("[^a-zA-Z]", " ", content)
    content = content.lower()
    tokens = nltk.word_tokenize(content)
    tokens = [wnl.lemmatize(t) for t in tokens]
    tokens = [w for w in tokens if len(w)>3]
    tokens = [w for w in tokens if not w in stops]
    with open(os.path.join(outDir,os.path.basename(doc)),"w") as fid:
        for token in tokens:
            fid.write("%s " % token)

