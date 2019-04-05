---
title: Notes for using Boost Graph Library (BGL)!
tags: BGL C++
aside:
toc:  true
---

# Introduction to the BGL

[The Boost Graph Library (BGL)][32567fb2]

  [32567fb2]: https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/index.html "BGL"

  The BGL currently provides two graph classes and an edge list adaptor:
  - adjacency_list-- "swiss army knife", highly parameterised and optimised for  different situations.
  - adjacency_matrix
  - edge_list

# A Quick Tour of the BGL
[Link](https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/quick_tour.html)

## Constructing a Graph

We will use the BGL ```adjacency_list```, a template class with 6 template parameters.

![adjacency_list](/assets/adjacency_list.jpg)

Options for ```OutEdgeList``` and ```VertexList```:

- ```vecS``` selects ```std::vector```.
- ```listS``` selects ```std::list```.
- ```slistS``` selects ```std::slist```.
- ```setS``` selects ```std::set```.
- ```multisetS``` selects ```std::multiset```.
- ```hash_setS``` selects ```boost::unordered_set```.
