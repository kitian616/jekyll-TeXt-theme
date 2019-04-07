---
title: Maximum Flow and Matching Algorithms in BGL
tags: BGL, flow algorithms
aside:
toc:  true
---

# Contents

This blog contains the introduction for 4 algorithms, which are:

- [`edmonds_karp_max_flow`](https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/edmonds_karp_max_flow.html)
- [`push_relabel_max_flow`](https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/push_relabel_max_flow.html)
- [`boykov_kolmogorov_max_flow`](https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/boykov_kolmogorov_max_flow.html)
- [`edmonds_maximum_cardinality_matching`](https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/maximum_matching.html)

There are some special requirements on the input graph and property map parameters for this algorithm, and it is highly suggested starting from the basic Edmond and Karp algorithm first.

#edmonds_karp_max_flow

[Link](<https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/edmonds_karp_max_flow.html>)

## Requirements  

Requirements on the input graph and property map parameters:

- Directed graph *G=(V,E)* that represents the network must be augmented to include the reverse edge for every edge in *E*. That is, the input graph should be $G_{in}= (V, \left( E\ U \ E ^T \right)$.
- The `ReverseEdgeMap` argument `rev` must map each edge in the original graph to its reverse edge, that is *(u,v) -> (v,u)* for all *(u,v)* in *E*.
- The `CapacityEdgeMap` argument `cap` must map each edge in *E* to a positive number, and each edge in $E^T​$ to 0.

## Example

[edmonds-karp-eg.cpp](<https://www.boost.org/doc/libs/1_69_0/libs/graph/example/edmonds-karp-eg.cpp>)

```c++
//=======================================================================
// Copyright 2001 Jeremy G. Siek, Andrew Lumsdaine, Lie-Quan Lee, 
//
// Distributed under the Boost Software License, Version 1.0. (See
// accompanying file LICENSE_1_0.txt or copy at
// http://www.boost.org/LICENSE_1_0.txt)
//=======================================================================
#include <boost/config.hpp>
#include <iostream>
#include <string>
#include <boost/graph/edmonds_karp_max_flow.hpp>
#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/read_dimacs.hpp>
#include <boost/graph/graph_utility.hpp>

// Use a DIMACS network flow file as stdin.
// edmonds-karp-eg < max_flow.dat
//
// Sample output:
//  c  The total flow:
//  s 13
//
//  c flow values:
//  f 0 6 3
//  f 0 1 6
//  f 0 2 4
//  f 1 5 1
//  f 1 0 0
//  f 1 3 5
//  f 2 4 4
//  f 2 3 0
//  f 2 0 0
//  f 3 7 5
//  f 3 2 0
//  f 3 1 0
//  f 4 5 4
//  f 4 6 0
//  f 5 4 0
//  f 5 7 5
//  f 6 7 3
//  f 6 4 0
//  f 7 6 0
//  f 7 5 0

int
main()
{
  using namespace boost;

  typedef adjacency_list_traits < vecS, vecS, directedS > Traits;
  typedef adjacency_list < listS, vecS, directedS,
    property < vertex_name_t, std::string >,
    property < edge_capacity_t, long,
    property < edge_residual_capacity_t, long,
    property < edge_reverse_t, Traits::edge_descriptor > > > > Graph;

  Graph g;

  property_map < Graph, edge_capacity_t >::type
    capacity = get(edge_capacity, g);
  property_map < Graph, edge_reverse_t >::type rev = get(edge_reverse, g);
  property_map < Graph, edge_residual_capacity_t >::type
    residual_capacity = get(edge_residual_capacity, g);

  Traits::vertex_descriptor s, t;
  read_dimacs_max_flow(g, capacity, rev, s, t);
  // https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/read_dimacs.html

#if defined(BOOST_MSVC) && BOOST_MSVC <= 1300
  std::vector<default_color_type> color(num_vertices(g));
  std::vector<Traits::edge_descriptor> pred(num_vertices(g));
  long flow = edmonds_karp_max_flow
    (g, s, t, capacity, residual_capacity, rev, &color[0], &pred[0]);
#else
  long flow = edmonds_karp_max_flow(g, s, t);
#endif

  std::cout << "c  The total flow:" << std::endl;
  std::cout << "s " << flow << std::endl << std::endl;

  std::cout << "c flow values:" << std::endl;
  graph_traits < Graph >::vertex_iterator u_iter, u_end;
  graph_traits < Graph >::out_edge_iterator ei, e_end;
  for (boost::tie(u_iter, u_end) = vertices(g); u_iter != u_end; ++u_iter)
    for (boost::tie(ei, e_end) = out_edges(*u_iter, g); ei != e_end; ++ei)
      if (capacity[*ei] > 0)
        std::cout << "f " << *u_iter << " " << target(*ei, g) << " "
          << (capacity[*ei] - residual_capacity[*ei]) << std::endl;

  return EXIT_SUCCESS;
}
```



# push_relabel_max_flow

[link](<https://www.boost.org/doc/libs/1_69_0/libs/graph/doc/push_relabel_max_flow.html>)

- Calculates the maximum flow of a network
- The calculated maximum flow will be the return value of the function. 
- The function also calculates the flow values *f(u,v)* for all *(u,v)* in *E*, which are returned in the form of the residual capacity *r(u,v) = c(u,v) - f(u,v)*.



# read_dimacs_max_flow

Read a BGL graph object from a max-flow or min-cut problem description in extended dimacs format.

For each edge found in the file an additional ```reverse_edge``` is added and set in the ```reverse_edge map```. For max-flow problems, ```source``` and ```sink``` vertex descriptors are set according to the dimacs file.

## Example

```c++
#include <boost/config.hpp>
#include <iostream>
#include <string>
#include <boost/graph/adjacency_list.hpp>
#include <boost/graph/read_dimacs.hpp>
#include <boost/graph/write_dimacs.hpp>

/*************************************
*
* example which reads in a max-flow problem from std::cin, augments all paths from 
* source->NODE->sink and writes the graph back to std::cout
*
**************************************/

template <typename EdgeCapacityMap>
struct zero_edge_capacity{
  
  zero_edge_capacity() { }
  zero_edge_capacity(EdgeCapacityMap cap_map):m_cap_map(cap_map){};

  template <typename Edge>
      bool operator() (const Edge& e) const {
    return  get(m_cap_map, e) == 0 ;
      }

      EdgeCapacityMap m_cap_map;
};

int main()
{
  using namespace boost;
  typedef adjacency_list_traits < vecS, vecS, directedS > Traits;
  typedef adjacency_list < vecS, vecS, directedS,
  no_property,
  property < edge_capacity_t, long,
  property < edge_reverse_t, Traits::edge_descriptor > > > Graph;
  
  typedef graph_traits<Graph>::out_edge_iterator out_edge_iterator;
  typedef graph_traits<Graph>::edge_descriptor edge_descriptor;
  typedef graph_traits<Graph>::vertex_descriptor vertex_descriptor;
  
  Graph g;

  typedef property_map < Graph, edge_capacity_t >::type tCapMap;
  typedef tCapMap::value_type tCapMapValue;
  
  typedef property_map < Graph, edge_reverse_t >::type tRevEdgeMap;
  
  tCapMap capacity = get(edge_capacity, g);
  tRevEdgeMap rev = get(edge_reverse, g);
  
  vertex_descriptor s, t;
  /*reading the graph from stdin*/
  read_dimacs_max_flow(g, capacity, rev, s, t, std::cin);
  
  /*process graph*/
  tCapMapValue augmented_flow = 0;
  
  //we take the source node and check for each outgoing edge e which has a target(p) if we can augment that path
  out_edge_iterator oei,oe_end;
  for(boost::tie(oei, oe_end) = out_edges(s, g); oei != oe_end; ++oei){
    edge_descriptor from_source = *oei;
    vertex_descriptor v = target(from_source, g);
    edge_descriptor to_sink;
    bool is_there;
    boost::tie(to_sink, is_there) = edge(v, t, g);
    if( is_there ){
      if( get(capacity, to_sink) > get(capacity, from_source) ){ 
        tCapMapValue to_augment = get(capacity, from_source);
        capacity[from_source] = 0;
        capacity[to_sink] -= to_augment;
        augmented_flow += to_augment;
      }else{
        tCapMapValue to_augment = get(capacity, to_sink);
        capacity[to_sink] = 0;
        capacity[from_source] -= to_augment;
        augmented_flow += to_augment;
      }
    }
  }

  //remove edges with zero capacity (most of them are the reverse edges)
  zero_edge_capacity<tCapMap> filter(capacity);
  remove_edge_if(filter, g);
  
  /*write the graph back to stdout */
  write_dimacs_max_flow(g, capacity, identity_property_map(),s, t, std::cout);
  //print flow we augmented to std::cerr
  std::cerr << "removed " << augmented_flow << " from SOURCE->NODE->SINK connects" <<std::endl;
  return 0;
}
```

