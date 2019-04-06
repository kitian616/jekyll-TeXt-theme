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

![adjacency_list](/docs/assets/images/adjacency_list.jpg)

Options for ```OutEdgeList``` and ```VertexList```:

- ```vecS``` selects ```std::vector```.
- ```listS``` selects ```std::list```.
- ```slistS``` selects ```std::slist```.
- ```setS``` selects ```std::set```.
- ```multisetS``` selects ```std::multiset```.
- ```hash_setS``` selects ```boost::unordered_set```.

Basic knowledge before reading the examples:
- The usage of STL ```pair```

For operating edges:
- ```add_edge(first_vertex, second_vertex, graph_name)```

For operating vertices:
- ```add_vertex()```
- ```remove_vertex()```

## Accessing the Vertex Set

### vertices()
Can be used to access all of the vertices in the graph;
This function returns a ```std::pair``` of ```vertex iterators``` (the first iterator points to the "beginning" of the vertices and the second iterator points "past the end").

### graph_traits and iterator
 Dereferencing a vertex iterator gives a vertex object. The type of the vertex iterator is given by the ```graph_traits``` class. Note that different graph classes can have different associated vertex iterator types, which is why we need the ```graph_traits``` class. Given some graph type, the ```graph_traits``` class will provide access to the ```vertex_iterator``` type.

### property_map
```property_map``` class is used to obtain the property map type for a specific property (specified by ```vertex_index_t```, one of the BGL predefined properties) and function call ```get(vertex_index, g)``` returns the actual property map object.

```c++
  int main(int,char*[])
  {
    // ...

    typedef graph_traits<Graph>::vertex_descriptor Vertex;

    // get the property map for vertex indices
    typedef property_map<Graph, vertex_index_t>::type IndexMap;
    IndexMap index = get(vertex_index, g);

    std::cout << "vertices(g) = ";
    typedef graph_traits<Graph>::vertex_iterator vertex_iter;
    std::pair<vertex_iter, vertex_iter> vp;
    for (vp = vertices(g); vp.first != vp.second; ++vp.first) {
      Vertex v = *vp.first;
      std::cout << index[v] <<  " ";
    }
    std::cout << std::endl;
    // ...
    return 0;
}

```

The output is:

```c++
 vertices(g) = 0 1 2 3 4
```

## Accessing the Edge Set

### edges()
this returns a pair of iterators, but in this case the iterators are edge iterators.

### source() and target()
return the two vertices that are connected by the edge. Instead of explicitly creating a ```std::pair``` for the iterators, this time we will use the ```boost::tie()``` helper function. 

### boost::tie()
This handy function can be used to assign the parts of a ```std::pair``` into two separate variables, in this case ```ei``` and ```ei_end```. This is usually more convenient than creating a ```std::pair``` and is our method of choice for the BGL.


### example

```c++
// ...
int main(int,char*[])
{
  // ...
  std::cout << "edges(g) = ";
  graph_traits<Graph>::edge_iterator ei, ei_end;
  for (boost::tie(ei, ei_end) = edges(g); ei != ei_end; ++ei)
      std::cout << "(" << index[source(*ei, g)]
                << "," << index[target(*ei, g)] << ") ";
  std::cout << std::endl;
  // ...
  return 0;
}

```
