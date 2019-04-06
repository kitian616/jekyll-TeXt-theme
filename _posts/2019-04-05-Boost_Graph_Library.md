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

<a data-flickr-embed="true"  href="https://www.flickr.com/photos/147825726@N02/46824988074/in/dateposted-public/" title="adjacency_list"><img src="https://live.staticflickr.com/7830/46824988074_394c5d070b_b.jpg" width="1024" height="309" alt="adjacency_list"></a><script async src="//embedr.flickr.com/assets/client-code.js" charset="utf-8"></script>

Options for ```OutEdgeList``` and ```VertexList``` (see Section [Choosing the `Edgelist` and `VertexList`](https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/using_adjacency_list.html#sec:choosing-graph-type)):

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

### example

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
### adjacent_vertices()
Provides direct access to the adjacent vertices. This function returns a pair of ```adjacency iterators```. Dereferencing an adjacency iterator gives a vertex descriptor for an adjacent vertex.

```c++
template <class Graph> struct exercise_vertex {
   //...
   void operator()(Vertex v) const
   {
     //...
     std::cout << "adjacent vertices: ";
     typename graph_traits<Graph>::adjacency_iterator ai;
     typename graph_traits<Graph>::adjacency_iterator ai_end;
     for (boost::tie(ai, ai_end) = adjacent_vertices(v, g);
          ai != ai_end; ++ai)
       std::cout << index[*ai] <<  " ";
     std::cout << std::endl;
   }
   //...
 };

```

## Accessing the Edge Set

### edges()
this returns a pair of iterators, but in this case the iterators are edge iterators.

### source() and target()
return the two vertices that are connected by the edge. Instead of explicitly creating a ```std::pair``` for the iterators, this time we will use the ```boost::tie()``` helper function.

### boost::tie()
This handy function can be used to assign the parts of a ```std::pair``` into two separate variables, in this case ```ei``` and ```ei_end```. This is usually more convenient than creating a ```std::pair``` and is our method of choice for the BGL.

### an example

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

### out_edges() and in_edges()
The ```out_edges()``` function takes two arguments: the first argument is the ```vertex``` and the second is the graph object. The function returns a pair of iterators which provide access to all of the out-edges of a vertex (similar to how the ```vertices()``` function returned a pair of iterators). .The iterators are called ```out-edge iterators``` and dereferencing one of these iterators gives an ```edge descriptor``` object.

The ```in_edges()``` function of the ```BidirectionalGraph``` interface provides access to all the in-edges of a vertex through ```in-edge iterators```. The ```in_edges()``` function is ==only available== for the ```adjacency_list``` if ```bidirectionalS``` is supplied for the ```Directed``` template parameter. There is an extra cost in space when ```bidirectionalS``` is specified instead of ```directedS```.

## Adding Some Color to your Graph

This sections teach you how to attach properties to your graph.

- [Internal Properties](<https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/using_adjacency_list.html#sec:adjacency-list-properties>): a property to be used throughout a graph object's lifespan

- Externally stored property: needed for the duration of a single algorithm, and it would be better to have the property stored separately from the graph object.

## Extending Algorithms with Visitors

```record_predecessors```

The predecessor visitor will then only be responsible for what parent to record. To implement this, we create a `record_predecessors` class and template it on the predecessor property map `PredecessorMap`.