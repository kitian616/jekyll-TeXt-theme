## 面向对象和面向过程

1. 面向过程：是一种是事件为中心的编程思想。就是分析出解决问题所需的步骤，然后用函数把这写步骤实现，并按顺序调用。
2. 面向对象：是以“对象”为中心的编程思想。

1. 举个例子，人把冰箱打开，把大象放进去，再把冰箱关闭，这一连串的事件顺序进行，就是面向过程。而用面向对象的思想，我们并不关注事件，而是关注“人”这个对象，“人”这个对象可以做什么？可以打开冰箱，可以关闭冰箱，可以放大象。
2. 也因此有面向对象三大特性：封装、继承、多态。因为一切皆对象，所以一切都需要“封装”成类。“继承”让我们设计相似的东西的时候更方便，而“多态”让我们使用类似的东西的时候可以不用去思考它们微弱的不同。我们关心的不是过程，而是接口，而接口来自对象，故名为面向对象。

 

 

 

 

## 封装、继承、多态

封装

- 隐藏了类的内部实现机制，可以在不影响使用的情况下改变类的内部结构，同时也保护了数据。对外界而已它的内部细节是隐藏的，暴露给外界的只是它的访问方法。

继承

- 是为了重用父类代码。两个类若存在IS-A的关系就可以使用继承。让我们设计相似的东西的时候更方便。同时继承也为实现多态做了铺垫。

多态

- 指程序中定义的**引用变量所指向的具体类型在程序运行期间才确定**，这样不用修改源代码，就可以让引用变量绑定到各种不同的类实现上，让程序可以选择多个运行状态，这就是多态性。
- 代码层面上就是指“**父类引用指向子类对象，子类对象会向上转型为父类**，调用方法时会调用子类的实现，而不是父类的实现；但要注意，父类类型的引用可以调用父类中定义的所有属性和方法，对于只存在与子类中的方法和属性它就望尘莫及了”

子父类同名函数、同名变量调用：

![zi()  fu t  = new  System. out. println (t . num)  t . method()  ; //&ütfilß ](data:image/png;base64,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)

1. 成员函数（非静态）：运行看右边
2. 成员函数（静态）：运行看左边
3. 成员变量：运行看左边

**多态如何实现的？**用的反射

**什么是反射？**

1. Java的反射机制允许我们动态的调用某个对象的方法、构造函数、获取某个对象的属性等；
2. 无需在编码的时候确定调用的对象。

**反射如何实现？**

1、先获取这个类的class实例，比如:

Class<?> myClass =Class.**forName**("myClassName");

2、然后通过这个类实例获得一个类对象，比如：

Object myClassObject = myClass.newInstance();

3、然后调用Class类的对象的**getMethod**获取method对象;

4、获取method对象后调用method.**invoke**方法获取这个类的field、method、construct等，在这一步中，JVM默认如果调用次数小于15次，会调用native方法实现反射，累积调用大于15次之后，会由java代码创建出字节码来实现反射。

 

 

## Java中有哪些集合

实现了Collection接口的集合类：

1. Collection<--List<--Vector

2. Collection<--List<--ArrayList

3. Collection<--List<--LinkedList

4. Collection<--Set<--HashSet 

5. 1. HashSet的存储方式是把HashMap中的Key作为Set的对应存储项。

6. Collection<--Set<--HashSet<--LinkedHashSet

7. Collection<--Set<--SortedSet<--TreeSet

8. 1. 自己实现Comparable接口后定义排序规则，就能自动排序，元素具有唯一性 https://www.cnblogs.com/yzssoft/p/7127894.html 

实现了Map接口，和Collection接口没关系，但都属于集合类的一部分：

1. HashMap
2. HashTable
3. LinkedHashMap
4. TreeMap
5. SynchronizedMap
6. ConcurrentHashMap

 

 

 

## final关键字

被final声明的对象即表示“我不想这个对象再被改变”，因此：

1. 被final声明的方法：这个方法不可以被子类重写
2. 被final声明的类：这个类不能被继承
3. 被final声明的变量：引用不能改变，常和static关键字一起使用作为常量

final关键字的好处：

1. final关键字提高了性能。JVM和Java应用都会缓存final变量。
2. final变量可以安全的在多线程环境下进行共享，而不需要额外的同步开销。
3. 使用final关键字，JVM会对方法、变量及类进行优化。

 

 

 

 

 

## static关键字

1. static用来修饰成员变量和成员方法，也可以形成静态static代码块。
2. static对象可以在它的任何对象创建之前访问，无需引用任何对象。
3. 因此主要作用是**构造****全局变量和全局方法**

 

 

## Java数组和链表的区别

基于空间的考虑：

- 数组的存储空间是静态，连续分布的，初始化的过大造成空间浪费，过小又将使空间溢出机会增多。而链表的存储空间是动态分布的，只要内存空间尚有空闲，就不会产生溢出；链表中每个节点出了数据域外，还有链域（指向下一个节点），这样空间利用率就会变高。
- 数组从栈中分配空间，对于程序员方便快速，但是自由度小。链表从堆中分配空间，自由度大但是申请管理比较麻烦。
- 数组中的数据在内存中按顺序存储的，而链表是随机存储的。

基于时间的考虑：

**数组查询快，插入与删除慢，单链表查询慢，插入与删除快**。细说的话：数组中任意节点都可以在O（1）内直接存储访问，而链表中的节点，需从头指针顺着链表扫描才能获取到；而链表任意位置进行插入和删除，都只需要修改指针，而数组中插入删除节点，平均要移动一半的节点。

 

 

 

 

 

## Java数据类型

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:15:16-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

1、Boolean\byte\char都是一个字节

2、int是4个字节，负2的31次方到正2的31次方减1

3、负数的表示，利用补码，比如-7。7的二进制代码取反再加1![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:15:55-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:16:20-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

浮点数是不精确的，如果算钱，要用Java提供的BigDecimal方法

## Primitive type和Object![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:18:27-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:19:17-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

两个Object即使值相等，用==判断也是false，因为不是同一个Object

Object的值判断，用Objects.equals(a,b)方法

## 装箱和拆箱![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:20:02-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:20:44-2022-03-27-20:20:17-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

1、第一行，JVM自动帮我们把左边进行了Unboxing，取出了里面的值与2进行比较

2、第二行，左边新建了一个箱子装了2，右边又新建了一个箱子装了2，显然这两个箱子不是同一个箱子。

3、第三行，Integer.valueOf(2)，左边和右边，系统都自动给了我们一个箱子。跟进valueOf()的代码里会发现。如果是-128到127之间，系统会给我们同一个箱子，超出了这个范围，系统会给新箱子，新箱子是不同的

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:21:07-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

4、第四行，.intValue()是拆箱，拿出了箱子里的值

5、第五行，Object.equals()方法是判断值是否相等（类型也必须相等啊）

 

整数在内存中是怎么表示的？浮点数是怎么表示的？

整数是用补码表示，浮点数是类似科学计数法的方法表示

 

什么是Big-endian/Little-endian，什么是对齐？

比如整数存入内存，到底是高位放高地址还是低位放高地址，这两种顺序就是Big-endian/Little-endian。

数据在内存中是需要对齐的。比如一个char和一个int，放入一个结构体，那么这个结构体是5个字节吗？不是的，char后面补上3个字节，对齐int。

 

介绍一下Java的数据类型？

Java有primitive Type和Object，然后primitive type 有啥，Object有啥

 

什么是值传递，什么是引用传递？

在Java中，primitive type都是值传递，Object都是引用传递。Java中的Object全是引用

 

String s= new String("test");创建了多少个对象？

创建了2个对象，引号test就会创建一个String，然后 new String又创建了一个String。左边s引用指向右边new出来的对象

 

equals和hashCode的关系？

hashCode相等是equals的必要条件。即hashCode相等不一定equals为true，equals为true则hashCode一定相等

 

什么是序列化和反序列化？

对象是内存中的，当我们把对象存入硬盘或变成字节流，这就是序列化。读到或者收到字节流还原成内存中的对象就是反序列化。Java中实现Serializable接口后会自动的帮我们做序列化和反序列化。通常我们序列化也不是变成字节流，而是存数据库中。所以序列化都是数据库帮我们做了。网络传输也是转成JSON对象，也不是序列化成字节流传输。

 

二叉树如何序列化？

这类问题我们要理解为，把二叉树的结构和里面的值变成一个String，节点末尾一定要加标记

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:21:36-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

## 访问控制符

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:21:58-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

## hashCode方法

Java中的hashCode方法就是根据一定的规则将与对象相关的信息（比如对象的存储地址，对象的 字段等）映射成一个数值，这个数值称作为散列值

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:22:23-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

1、下面如果为true，就可以推出上面为true

##  

## hashCode的作用

1、在Java集合中有两类，一类是List，一类是Set。他们之间的区别就在于List集合中的元素是有序的，且可以重复，而Set集合中元素是无序不可重复的。对于List好处理，但是对于Set而言我们要如何来保证元素不重复呢？通过迭代来equals()是否相等。数据量小还可以接受，当我们的数据量大的时候效率可想而知

2、当集合要添加新的对象时，先调用这个对象的 hashCode方法，得到对应的hashcode值，实际上在HashMap的具体实现中会用一个table保存已经存进去的对象的hashcode 值，如果table中没有该hashcode值，它就可以直接存进去，不用再进行任何比较了；如果存在该hashcode值， 就调用它的equals方法与新元素进行比较，相同的话就不存了，不相同就散列其它的地址

3、所以hashCode在上面扮演的角色为快速寻域（寻找某个对象在集合中区域位置）

 

在重写equals方法的同时，必须重写hashCode方法。为什么这么说呢？

1、让equals方法和hashCode方法始终在逻辑上保持一致性

2、即让equals认为相等的两个对象，这两个对象同时调用hashCode方法，返回的值也是一样的

 

## HashMap实现原理

来自 <https://www.cnblogs.com/chengxiao/p/6059914.html> 

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:23:01-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

Entry的内部结构！！！有4个变量！！！，不止key和value

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:23:20-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

### 概述：

1、HashMap由数组+链表组成的，数组是HashMap的主体，链表则是主要为了解决哈希冲突而存在的，即链地址法。HashMap的主干是一个Entry数组。Entry是HashMap的基本组成单元，每一个Entry包含一个key-value键值对和一个hash值和一个指向下一个Entry的next指针。

2、如果定位到的数组位置不含链表（当前entry的next指向null）,那么对于查找，添加等操作很快，仅需一次寻址即可

3、如果定位到的数组包含链表，对于添加操作，其时间复杂度依然为O(1)，操作是创建新节点，把该新节点插入到链表中的头部，该新节点的next指针指向原来的头结点 ，即需要简单改变引用链即可，而对于查找操作来讲，此时就需要遍历链表，然后通过key对象的equals方法逐一比对查找。

4、所以，性能考虑，HashMap中的链表出现越少，性能才会越好。

6、当发生哈希冲突并且size大于阈值的时候，需要进行数组扩容，扩容时，需要新建一个长度为之前数组2倍的新的数组，然后将当前的Entry数组中的元素全部传输过去，扩容后的新数组长度为之前的2倍，所以扩容相对来说是个耗资源的操作

7、如果key为null，就会插入到table[0]的位置也就是数组头。如果key=null，则hash值直接赋0

8、存key时，如果链中存在该key，则用传入的value覆盖掉旧的value，同时把旧的value返回：这就是为什么HashMap不能有两个相同的key的原因。

9、计算hash值之后，如何通过hash值均匀的存到数组里！！！！！！！！！？当然是取模，但取模消耗大，因此HashMap用的&运算符（按位与操作）来实现的：hashCode & (length-1)。

10、这里就隐含了为什么数组长度length一定要是2的n次方。当length不是2的n次方的时候，length-1的二进制最后一位肯定是0，在&操作时，一个为0，无论另一个为1还是0，最终&操作结果都是0，这就造成了结果的二进制的最后一位都是0，这就导致了所有数据都存储在2的倍数位上，所以说，所以说当length = 2^n时，不同的hash值发生碰撞的概率比较小，这样就会使得数据在table数组中分布较均匀，查询速度也较快。

 

### 我们重新来理一下存储的步骤：

　　1. 传入key和value，判断key是否为null，如果为null，则调用putForNullKey，以null作为key存储到哈希表中； 

　　2. 然后计算key的hash值，根据hash值搜索在哈希表table中的索引位置，若当前索引位置不为null，则对该位置的Entry链表进行遍历，如果链中存在该key，则用传入的value覆盖掉旧的value，同时把旧的value返回，结束；

　　3. 否则调用addEntry，用key-value创建一个新的节点，并把该节点插入到该索引对应的链表的头部

 

读的步骤：

读取的步骤比较简单，调用hash（key）求得key的hash值，然后调用indexFor（hash）求得hash值对应的table的索引位置，然后遍历索引位置的链表，如果存在key，则把key对应的Entry返回，否则返回null。

 

 

 

## JDK1.8之前和之后HashMap区别

- 在JDK1.8以前版本中，HashMap的实现是数组+链表，它的缺点是即使哈希函数选择的再好，也很难达到元素百分百均匀分布，而且当HashMap中有大量元素都存到同一个桶中时，这个桶会有一个很长的链表，此时遍历的时间复杂度就是O(n)，当然这是最糟糕的情况。
- 在JDK1.8及以后的版本中引入了红黑树结构，HashMap的实现就变成了数组+链表或数组+红黑树。添加元素时，若桶中链表个数超过8，链表会转换成红黑树；删除元素、扩容时，若桶中结构为红黑树并且树中元素个数较少时会进行修剪或直接还原成链表结构，以提高后续操作性能；遍历、查找时，由于使用红黑树结构，红黑树遍历的时间复杂度为 O(logn)，所以性能得到提升。

 

 

## HashMap多线程下会发生什么问题

**多线程并发下，在****HashMap****扩容的时候可能会形成环形链表**。至于具体过程？不用深究了吧，连Sun公司都不觉得这是个问题，要并发就用ConcurrentHashMap呀！

 

 

 

 

## HashTable与HashMap区别

1、Hashtable 中的方法是同步的，而HashMap中的方法在缺省情况下是非同步的。 

2、Hashtable中，key和value都不允许出现null值。在HashMap中，null可以作为键，这样的键只有一个；可以有一个或多个键所对应的值为null。

3、并发性不如ConcurrentHashMap，因为ConcurrentHashMap引入了分段锁。Hashtable不建议在新代码中使用，不需要线程安全的场合可以用HashMap替换，需要线程安全的场合可以用ConcurrentHashMap替换。

 

## HashSet 和 HashMap

1、它们底层的 Hash 存储机制完全一样，甚至 HashSet 本身就采用 HashMap 来实现的

 

 

 

 

## 接口与抽象类

**一句话解释：**

抽象类就是比普通类多了一些抽象方法而已，其他部分和普通类完全一样；而接口是特殊的抽象类。

**作用上看：**

1、接口与抽象类结构有点像，但功能完全不同

2、接口是强调合约、约束关系，即你要与我合作，必须实现我的功能；抽象类没这个功能

**语法上看：**

1. 都不能被实例化
2. 接口是特殊的抽象类
3. 接口不能有实现，Java8中可以有添加default关键字的默认实现和静态方法实现。
4. 接口中的成员变量必须是public static     final修饰（编译器默认会添加上），因此是常量
5. 一个类可以实现多个接口但只能继承一个抽象类

 

什么是接口？

1、从表现来说：定义了很多函数，但是这些函数都没有实现，这就是接口。从作用来说：起到一个合约规范的作用。我要告诉你和我打交道的东西有什么约束

2、接口中的方法只能用public和abstract修饰或者不修饰

3、接口中的属性默认都是public static final，因此是常量

 

final的作用？

表示我这个东西不希望被修改了

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:23:59-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

1、第一个“否”

2、第二个“是”

 

## 实现Iterable接口

1、实现Iterable接口，可以让我们一个一个拿出元素

2、如下，因为LinkedList<>实现了Iterable接口，所以我们才能用for each语法遍历出里面一个一个的对象

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:24:22-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:24:35-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:24:51-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

1、子类（派生类）只能增加或修改基类里的内容，不能减少

2、因此，子类的private（父类对应的方法是private）改为public可以，但子类的public(父类对应的方法是public)改为private不行

3、如果有@Override注释，则子类权限必须与基类一致

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:25:18-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:25:30-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

## HashTable、synchronizedMap和ConcurrentHashMap

HashTable、synchronizedMap效率低下

- 现在基本不用HashTable。HashTable容器使用synchronized来保证线程安全，但是锁的是整个hash表，当一个线程使用 put     方法时，另一个线程不但不可以使用 put 方法，连 get 方法都不可以。

- synchronizedMap比HashTable强一分钱，synchronizedMap提供一个不同步的基类和一个同步的包装。允许需要同步的用户可以拥有同步，而不需要同步的用户则不必为同步付出代价，get方法与HashTable一样锁住整个hash表，区别是get()和put()之类的简单操作可以在不需要额外同步的情况下安全地完成。但多个操作组成的操作序列却可能导致数据争用，总之就是不好用。

ConcurrentHashMap效率高，因为用了分段锁（JDK8之前），16个

- HashTable容器在竞争激烈的并发环境下表现出效率低下的原因是所有访问HashTable的线程都必须竞争同一把锁
- 那假如容器里有多把 锁，每一把锁用于锁容器其中一部分数据，那么当多线程访问容器里不同数据段的数据时，线程间就不会存在锁竞争，从而可以有效的提高并发访问效率
- 这就是 ConcurrentHashMap所使用的锁分段技术，首先将数据分成一段一段的存储，默认分成16个段，然后给每一段数据配一把锁，当一个线程占用锁访问其中一个段数据的时候，其他段的数据也能被其他线程访问。
- 上面说到的16个线程指的是写线程，而读操作大部分时候都不需要用到锁。只有在size等操作时才需要锁住整个hash表。

 

 

 

## ConcurrentHashMap JDK1.8

基本结构：Node<K,V>数组+链表（红黑树）的结构。

1. 而对于锁的粒度，调整为对每个数组元素加锁（Node），即没有分段锁了，而是Node锁，粒度更小。
2. 使用CAS操作来确保Node的一些操作的原子性，这种方式代替了锁。
3. ConcurrentHashMap在线程安全的基础上提供了更好的写并发能力，但同时降低了读一致性。ConcurrentHashMap的get操作上面并没有加锁。所以在多线程操作的过程中，并不能完全的保证一致性。这里和1.7当中类似，是弱一致性的体现。
4. 代码中使用synchronized而不是ReentrantLock，说明JDK8中synchronized有了足够的优化。
5. 然后是定位节点的hash算法被简化了，这样带来的弊端是Hash冲突会加剧。

1. 因此在链表节点数量大于8时，会将链表转化为红黑树进行存储。这样一来，查询的时间复杂度就会由原先的O(n)变为O(logN)。![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:25:56-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)

- ConcurrentHashMap的设计与实现非常精巧，大量的利用了volatile，final，CAS等lock-free技术来减少锁竞争对于性能的影响。
- HashEntry中的value以及next都被volatile修饰，这样在多线程读写过程中能够保持它们的可见性。![未命名图片](https://cdn.jsdelivr.net/gh/wholon/image@main/2022-03-27-20:26:19-%E6%9C%AA%E5%91%BD%E5%90%8D%E5%9B%BE%E7%89%87.png)