"""
    For COMP5213 final project, to compute coherence and compactness
    To compute compactness, need to download pretrained word2vec model:
    GoogleNews-vectors-negative300.bin from https://drive.google.com/file/d/0B7XkCwpI5KDYNlNUTTlSS21pQmM/edit?usp=sharing

    To adapt this code:
    1. docs: store all documents in an array
        each element in docs is an array of word id, denoting an document
    2. vocab: dictionary, mapping from word to id
        id2word: dictionary, mapping from id to word
    3. topics: an array of topics
        each element in topics is an array of word id, denoting a topic
        the number of words for denoting the topic is specified by M
        In this experiment, M is set to 4, only using top 4 words to denote a topic

    by Xiaopeng LI
    25/04/2017@HKUST
"""

import math
import sys
import gensim

# ====================== BEGIN data preparation part ===============
# ====================== should adapt ========================
num_docs = 12502
docs = [None]*num_docs
ind = -1
for line in open("mult.dat"):
    ind += 1
    progress = 1.0*ind/num_docs*100
    sys.stdout.write('\r[%-10s] %0.2f%%' % ('#' * int(progress/10), progress))
    sys.stdout.flush()

    arr = line.strip().split()
    doc = []
    for w in arr[1:]:
        wid = int(w.split(":")[0])
        doc.append(wid)
    docs[ind] = doc

vocab = {}
id2word = {}
for line in open("vocab.dat"):
    w, id = line.strip().split(",")
    id = int(id)
    vocab[w] = id
    id2word[id] = w

num_topics = 260
topics = [None]*num_topics
M = 4
with open("pmc2kTopics3.nhdp.txt") as fid:
    lines = fid.readlines()
    lines = [x.strip() for x in lines]
    i = 0
    while(i<len(lines)):
        if lines[i]=="*** This node ***":
            topicid = int(lines[i+1].split()[2]) - 1
            topic = []
            for j in range(M):
                topic.append(lines[i+2+j])
            print("Topic #%d: %s" % (topicid, " ".join(topic)))
            topic = [vocab[t] for t in topic]
            topics[topicid] = topic
        i += 1

# ====================== END data preparation part ===============

def coocurr(w1, w2, docs):
    cn_w1 = 0
    cn_co = 0
    for doc in docs:
        if w1 in doc:
            cn_w1 += 1
            if w2 in doc:
                cn_co += 1
    return (cn_w1, cn_co)

def coherence(topic, docs):
    M = len(topic)
    sum_co = 0
    for i in range(1,M):
        for j in range(i):
            cn_wj, cn_co = coocurr(topic[j], topic[i], docs)
            sum_co += math.log((cn_co+1)/float(cn_wj))
    return sum_co

print("computing coherence score...")
sum_co = 0
ind = -1
for topic in topics:
    ind += 1
    progress = 1.0*ind/num_topics*100
    sys.stdout.write('\r[%-10s] %0.2f%%' % ('#' * int(progress/10), progress))
    sys.stdout.flush()

    sum_co += coherence(topic, docs)

ave_coherence = float(sum_co)/num_topics
print("Average coherence score=%f" % ave_coherence)

def compactness(topic, word2vec):
    M = len(topic)
    sum_co = 0
    for i in range(1,M):
        for j in range(i):
            wi = id2word[topic[i]]
            wj = id2word[topic[j]]
            if wi in word2vec.vocab and wj in word2vec.vocab:
                sum_co += model.similarity(wi, wj)
    ave_co = 2.0/(M*(M-1)) * sum_co
    return ave_co

print("computing compactness score...")
# Load Google's pre-trained Word2Vec model.
model = gensim.models.KeyedVectors.load_word2vec_format('./model/GoogleNews-vectors-negative300.bin', binary=True)  
sum_co = 0
ind = -1
for topic in topics:
    ind += 1
    progress = 1.0*ind/num_topics*100
    sys.stdout.write('\r[%-10s] %0.2f%%' % ('#' * int(progress/10), progress))
    sys.stdout.flush()

    sum_co += compactness(topic, model)

ave_compactness = float(sum_co)/num_topics
print("Average compactness score=%f" % ave_compactness)
