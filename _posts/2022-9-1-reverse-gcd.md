---
layout: article
title: 【码蹄集】Reverse GCD
permalink: /article/reverse-gcd.html
key: reverse-gcd
tags: 
  - 数论
  - 码蹄集
author: Shao Mingyue
show_author_profile: true
---

URL: [原题链接](https://matiji.net/exam/brushquestion/583/3846/4C6668FEB8CFD6520DE73B365B31D1A4)  
id: 1  
题号: MT3583  
题库: Matiji  

<!--more-->

## Problem

有$n$个数，给出他们两两的gcd（包括自己和自己）$n^2$个，求这$n$个数。

### Input Format

第一行输入一个整数$n(1 \le n \le 500)$

第二行输入$n^2$个整数$a_1\cdots a_{n^2}(1\le a_i \le 5\times 10^4)$

### Output Format

打印$n$个整数，输出递增序列

### Example Demonstration

| Format | Example |
| ------ | ------- |
| Input  | 2       |
|        | 1 1 3 5 |
| Output | 3 5     |

---

## Analysis

最大的数字肯定是$n$个数字中最大的一个，因为要与自身计算GCD的结果就是这个数本身

因此，以此从所有公因数的数组中找到最大的一个数，作为当前找到的最大的数

将当前最大的数与此前找到过的最大的数计算GCD，将公因数数组中排除这些数，表示当前最大数与所有已知数的最大公因数都计算完毕了，剩下的数就是未找到的数之间计算GCD的结果

注意，在计算当前最大数与已知其他数的公因数时，需要计算两次，因为任意一对数之间都计算了两次最大公因数

## Solution

```java
import java.util.*;

public class w15p6 {

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        w15p6(input);
    }

    public static void w15p6(Scanner input) {				        
        // Map用于存放输入数据中不同的数及其出现的次数
				Map<Integer, Integer> map = new HashMap<>();

				// 输入数据的过程
				int n = input.nextInt();
        for (int i = 0; i < n * n; i++) {
            int in = input.nextInt();
            if (map.containsKey(in))
                map.put(in, map.get(in) + 1);
            else
                map.put(in, 1);
        }

				// result数组用于存放计算过程中的最大公因数
				// 由于result数组初始化为0，恰好
        int[] result = new int[n];
        for (int i = 0; i < n; i++) {
						// 从所有公因数中找到最大的作为当前最大公因数，放入result数组中
            result[i] = getKey(map);
						// 存放后，将这个数移除到所有出现的公因数中，表示移除这个数与自身进行的最大公因数计算结果
						// 其他数与这个数的最大公因数计算结果在其他数被找到时计算并移除
						// 仅移除1个，如果这个数出现次数为0，则将key从Map中清除
            map.put(result[i], map.get(result[i]) - 1);
            if (map.get(result[i]) == 0) {
                map.remove(result[i]);
            }
            for (int j = 0; j < i; j++) {
								// 遍历所有此前已找到的数
								// 计算此前已找到的数与当前找到的数的公因数
                int x = gcd(result[i], result[j]);
								// 将这个公因数从Map中移除两次，因为一对数之间有两次最大公因数的计算
                if (map.containsKey(x)) {
                    map.put(x, map.get(x) - 1);
                    if (map.get(x) == 0)
                        map.remove(x);
                }
                if (map.containsKey(x)) {
                    map.put(x, map.get(x) - 1);
                    if (map.get(x) == 0)
                        map.remove(x);
                }
            }
        }
				// 倒序输出
        for (int i = n - 1; i >= 0 ; i--) {
            System.out.print(result[i]);
            if (i != 0)
                System.out.print(" ");
        }

    }

    public static int getKey(Map<Integer, Integer> map) {
				// 从存放最大公因数Map中找到最大的公因数，作为当前最大数
        Set<Integer> set = map.keySet();
        Object arr[] = set.toArray();
        Arrays.sort(arr);
        return (int)arr[arr.length - 1];
    }

    public static int gcd(int a,int b) {
        return b == 0 ? a : gcd(b, a % b);
    }

}
```
