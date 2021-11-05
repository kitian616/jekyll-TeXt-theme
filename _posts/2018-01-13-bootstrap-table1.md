---
title: Bootstrap-Table初使用
date: 2018-01-13
categories: 
tags: [web]
---

温故而知新。学习使用[Bootstrap Table](http://bootstrap-table.wenzhixin.net.cn/zh-cn/)已经完整的完成了一个项目，这里还是简单对**Bootstrap-Table**的使用做一个简单的介绍以及实例的演示。

什么是[Bootstrap Table](http://bootstrap-table.wenzhixin.net.cn/zh-cn/)，自行查看官网介绍。刚接触可能一头雾水，建议直接从官方文档的[开始使用](http://bootstrap-table.wenzhixin.net.cn/zh-cn/getting-started/)开始看，后面再看一些例子，最后详细读文档部分，学习使用它，自定义一些自己想要实现的功能。

最开始使用**Bootstrap Table**的原因是做后台数据管理使用。后台的一些业务数据习惯通过表格的形式展现出来，以便于做一些增删改查的操作。如果有一个通用的模块直接能在前台渲染出表格，服务器端只需要传输基础数据就可以了，那就太方便了。于是就找到了**Bootstrap Table**。

### 1. 引入文件
首先要引入Bootstrap Table的相关文件，下载源码引入或者直接引入CDNJS提供的外链地址，不过建议还是下载源码，源码文件结构如下：

```html
bootstrap-table/
├── dist/
│   ├── extensions/
│   ├── locale/
│   ├── bootstrap-table.css
│   └── bootstrap-table.js
├── docs/
└── src/
    ├── extensions/
    ├── locale/
    ├── bootstrap-table.css
    └── bootstrap-table.js
```

引入`dist`目录下的`bootstrap-table.css`以及`bootstrap-table.js`，如果使用中文，还要应用`locale/bootstrap-table-zh-CN.js`，**注意每个文件均提供了min版本以及正常版本，建议在开发测试阶段引入正常版本，便于调试。**

当然最后不要忘了引入`bootstrap`以及`jQuery`的文件。最后在页面**head**部分引入css文件如下：

```html
<link rel="stylesheet" href="/bootstrap/dist/css/bootstrap.css">
<link rel="stylesheet" href="/bootstrap-table/dist/bootstrap-table.css">
```

在页面**body**最后引入js文件如下：**（注意：1. 顺序不要乱；2. 根目录依据自己项目而定）**

```html
<script src="/jquery/dist/jquery.js"></script>
<script src="/bootstrap/dist/js/bootstrap.js"></script>
<script src="/bootstrap-table/dist/bootstrap-table.js"></script>
<script src="/bootstrap-table/dist/locale/bootstrap-table-zh-CN.js"></script>
```

### 2. 使用方式介绍
根据官方文档介绍，使用方式有两种，一种：通过 data 属性的方式；另一种：通过 JavaScript 的方式。说白了就是把控制代码写在HTML里还是写在JS里。在实际项目的开发中，大多数是将控制代码写入JS里，这样更方便灵活，数据通过ajax异步请求的方式获取，数据格式为**json**。所以下面我就介绍最后这一种的实现方式。也就是官方提供的这段示例代码：

```js
$('#table').bootstrapTable({
    url: 'data1.json',
    columns: [{
        field: 'id',
        title: 'Item ID'
    }, {
        field: 'name',
        title: 'Item Name'
    }, {
        field: 'price',
        title: 'Item Price'
    }, ]
});
```

### 3. 具体功能具体分析
官方文档的[开始使用](http://bootstrap-table.wenzhixin.net.cn/zh-cn/getting-started/)部分基本上就上面那些内容。接下来就是[文档部分](http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/)了，该部分详细的讲解了Bootstrap table每个参数每个方法事件的使用。

这里我不准备按照官方参数说明一个一个来叙述，那样没有什么效果，因为官方文档已经做了简要介绍，再啰嗦一遍不会用还是不会用。所以这里以我项目里实际的例子来叙述，后台使用的是Node.js作为服务端。

#### 3.1 请求获取json数据
前面说了使用Bootstrap table有两种方式，一种是把代码写在html里，一种是把代码写在js里。这里的示例都是采用第二种方式（下面就不再贴出HTML的代码里）。HTML里的代码就这么一行，如下：

```html
<table id="table"></table>
```

table的数据都是从后台异步获取，这也是最常用的一种方式，通常在项目开发中，数据基本上是来自后台。下面看一段js代码：

```js
$('#table').bootstrapTable({
      url: '/admin/staff/ajax/table',
      height: 550,
      toolbar: '#toolbar',
      search: true,
      showRefresh: true,
      showExport: true,
      ajaxOptions: {global: false},
      pagination: true,
      sidePagination: 'client',
      pageNumber: 1,
      pageSize: 10,
      pageList: '[10, 20, 50, ALL]',
      sortOrder: 'desc',
      sortName: 'uid',
      columns: [{
        field: 'uid',
        title: 'id',
        visible: false
      }, {
        field: 'userid',
        title: '员工号'
      }, {
        field: 'username',
        title: '姓名'
      }, {
        field: 'department',
        title: '部门'
      }, {
        field: 'office',
        title: '科室'
      }, {
        field: 'produce',
        title: '工序'
      }, {
        field: 'team',
        title: '班组'
      }, {
        field: 'operate',
        title: '操作',
        align: 'center',
        valign: 'middle',
        events: operateEvents,
        formatter: operateFormatter
      }]
    });
```

上面是我一个练手项目的实际代码，功能是对员工信息的一个处理。[官方文档](http://bootstrap-table.wenzhixin.net.cn/zh-cn/documentation/)里分为表格参数和列参数。上面代码中例如`url`,`height`等是表格参数，其中表格参数`columns`的值是一个数组，数组里每个元素都是一个对象，对象里的一些参数属于列参数。具体参数有哪些，大致功能是什么查看文档即可。

其中`url`表格参数定义的是异步请求的地址，例如上面`/admin/staff/ajax/table`意思就是要从这个地址来获取表格所需要的`json`数据。那么现在来看看后台取到的数据格式。由于后台使用Node，那么又是一段`js`代码如下：

```js
// 人员管理-ajax返回bootstrap-table数据
router.get('/admin/staff/ajax/table', function (req, res, next) {
  var adminid = req.session.adminInfo.adminid;
  dbSelect.getUsersByAdminNoPage(adminid, function (err, rows, fields) {
    if (!err) {
      res.json(rows);
    } else {
      errHandle(res, 'db return err', err);
    }
  });
});
```

后台代码可以不用太关心，因为不同的编程语言后台实现是不一样的。直接看下返回的数据格式：

```
[{
    "uid": 181,
    "userid": "1000000",
    "username": "赵彦飞",
    "department": "Cell制造部",
    "office": "Cell检测科",
    "produce": "Q Boxing",
    "team": "A"
},{
    "uid": 257,
    "userid": "盛雪增",
    "username": "1820",
    "department": "Cell制造部",
    "office": "Cell检测科",
    "produce": "Q Boxing",
    "team": "A"
}]
```

返回的数据是个数组，每一个对象对应一行数据，字段名对应列参数里的`field`，都匹配上了，那么数据就能显示出来。最终效果如下：

![](/image/about_web/2017-05-27-16-08-52.jpg)

#### 3.2 操作功能（修改&删除）

![](/image/about_web/2017-05-27-16-13-35.jpg)

可能你会发现，列参数里最后一列的操作是从哪里来的，返回的json里并不存在这个数据，点击之后需要有一些数据处理的操作。弄明白这个需要了解`events`和`formatter`两个列参数的用法。

`formatter`是格式化表格数据的一个参数，返回一个function，还是上面那个表格，定义一个`operateFormatter`函数，如下：

```js
function operateFormatter(value, row, index) {
    return [
      '<a class="op" href="javascript:void(0)" title="修改">',
      '<i class="glyphicon glyphicon-pencil text-primary"></i></a>  ',
      '<a class="op" href="javascript:void(0)" title="删除">',
      '<i class="glyphicon glyphicon-trash text-danger"></i></a>'
    ].join('');
  }
```

当然你可以把函数直接写在列参数下。函数的意思就是直接返回两个链接当作`operate`这个`field`的值。有了样式，那么点击动作同样需要定义，那么参数`events`就起作用了，代码如下：

```js
window.operateEvents = {
    'click [title=修改]': function (e, value, row, index) {
      alert('编辑功能暂不开放');
    },
    'click [title=删除]': function (e, value, row, index) {
      $('#delModal').modal('show');
      clickId = row.uid;
    }
  };
```

#### 3.3 分页功能
当表格数据比较多时，那么就会用到分页的功能，分页分为前台分页和后台分页。什么是前台分页？什么是后台分页？前台分页指的是一次性加载全部的数据到客户端，然后进行分页；后台分页指的是分页请求服务器资源。这两种分页方式各有利弊，不多介绍，这里给个参考值，数据量不上万，建议使用前台分页，一次性加载完毕，其他操作就是前台处理了。

确定使用什么类型分类首先定义表格参数`sidePagination`，不设置的话，默认值是`client`，也就是前台分页模式；如果设置为`server`就是后台分页模式。

我自己写的项目里目前均是通过前台分页的模式来实现的，最初的考虑是前台一次性加载所需的数据保存在浏览器端更便于前台的一些操作，翻页和浏览的过程中也无需再向服务器端请求了。这种方式实现起来，后台以及数据库请求的代码逻辑较简单。

```js
pageNumber: 1,
pageSize: 10,
pageList: '[10, 20, 50, ALL]',
pagination: true,
paginationLoopz: true
```

这些表格参数是控制分页的，详情翻翻文档，不难理解，显示出来的样式如下图：

![](/image/about_web/2017-05-28-14-19-20.jpg)

#### 3.4 自动分页功能
分页很简单，样式功能基本上都定义好了，只要使用对应的表格参数即可。那么如何让它自动翻页呢？因为在做这个项目的时候，接到一个前台需求，那就是表格数据直接放在大厅屏幕上，上面的数据需要轮回的滚动，这样就没有了人去点击下一页了。

表格数据怎么自动滚动呢？如果把人为去点的动作，直接设置成自动点击就可以了。于是写个function如下：

```javascript
// 定时翻页
function changePage(){
    $pageNext.click();
}
setInterval('changePage()', 1000*3);
```

改变页面，用JQuery定义**下一页**按钮，每隔3秒执行一下`click`函数就可以了。还有一个问题就是我们要在表格加载完成之后，才能去定义`$pageNext`这个元素，所以还要使用一个表格事件才能生效，如下代码：

```js
$table.on('post-body.bs.table',function(){
    $pageNext = $('.page-next');
});
```

`post-body.bs.table`这个事件就是渲染完成之后触发的（Fires after the table body is rendered and available in the DOM），这个时候再合适不过了。

#### 3.5 数据预处理自定义
数据预处理指的是在从服务器处取得原始数据后，我们提前先进行加工处理，然后渲染到前台table中。我自己最常用的一个功能就是给数据添加编号，就像每次在Excel中总是喜欢在最前面一列加上编号1,2,3,4,5...

这里就用这个例子来展示一下实现方式，数据预处理我们要用到`responseHandler`这个表格参数。

```javascript
responseHandler: function(res) {
    for (var i = 0; i < res.length; i++) {
        res[i].rid = i + 1;
    }
    return res;
},
```

输入一个参数res，也就是服务器返回的那个对象，处理完后也返回res这个对象，通常情况下，res是个数组，数组元素是对象，所以上面代码就是在每个数组中每个对象的前面加上一个字段rid，rid从1开始累加，就是编号了。 如下图所示：

![](/image/about_web/2018-01-13-21-44-04.png)

#### 3.6 表格数据自定义
看到上图表格，优先级字段那块显示的1234，是不是觉得还挺酷的，怎么实现的呢？从上面了解的情况，我们知道，表格的数据都是来源于后台的数据表，而数据表中存储的都是原始数据，是不带样式的，而表格中如果想要显示一些带样式的数据，需要用到列参数的`formatter`参数。

优先级这块，后台直接储存的是**[重要紧急，重要不紧急，不重要紧急，不重要不紧急]** 四类，而这里我用1，2，3，4来替代了。使用`formatter`参数如下代码：

```js
formatter: function(value, row, index) {
    var msg = '';
    switch (value) {
        case '重要紧急':
            msg = '<lable class="label label-danger" title="' + value + '">1</lable>';
            break;
        case '重要不紧急':
            msg = '<lable class="label label-primary" title="' + value + '">2</lable>';
            break;
        case '不重要紧急':
            msg = '<lable class="label label-warning" title="' + value + '">3</lable>';
            break;
        case '不重要不紧急':
            msg = '<lable class="label label-success" title="' + value + '">4</lable>';
            break;
    }
    return msg;
}
```

注意这是列参数，而非表格参数，每一列都可以使用它自定义显示样式。

#### 3.7 详细展开功能
上面介绍了表格数据自定义功能，它是一个列参数，可以表格数据的样式。这里介绍一个表格参数，针对整个表格有效的格式化功能。如下图所示，每一列前面都有个加号用来展开详细信息。

![](/image/about_web/2018-01-23-11-24-35.jpg)

那就是参数`detailView`和`detailFormatter`的作用，简单设置这两个参数如下所示:

```js
detailView: true,
detailFormatter: function () {
    return 'somethings';
},
```

很好理解，第一个参数打开详细展开视图，也就是前面会多一个加号；第二个参数是格式化详细展开的内容，这里返回`somethings`字符串，那么每一行点开都是显示`somethings`的字样，如下图所示:

![](/image/about_web/2018-01-23-11-28-43.jpg)

大概了解了这块功能，现在我们实现一个实际的需求。如果后台要显示的字段很多，全部在前台表格中显示会显得太拥挤，也没有主次，于是可以考虑将一部分次要内容放在详细展开这里显示，比如一些备注信息等等。

参考：
- [JS组件系列——BootstrapTable 行内编辑解决方案：x-editable - 懒得安分 - 博客园](http://www.cnblogs.com/landeanfen/p/5821192.html)