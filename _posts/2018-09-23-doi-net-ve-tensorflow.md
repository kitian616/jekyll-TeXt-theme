---
title: Đôi nét về TensorFlow
key: 20180923
tags: ML TensorFlow
comments: true
---

Trước khi nói về TensorFlow, mình muốn kể cho các bạn nghe một chút về hành trình của mình đến với TensorFlow. Từ năm thứ 3 đại học, lúc đó mình vẫn còn đang loay hoay tìm kiếm một hướng đi cho mình trên lĩnh vực Công nghệ thông tin rộng lớn, mình đã từng học qua PHP, đã từng học qua Android, đã từng lập trình C, từng muốn trở thành kỹ sư phần mềm hệ thống,... Tuy nhiên, thực sự mình vẫn chưa thể tìm được một thứ có thể khiến cho mình yêu thích thực sự. Vào thời điểm đó, Machine Learning cũng đã không còn quá xa lạ nữa nhưng thật sự là ở Việt Nam, nó vẫn là một chủ đề khá mới mẻ, mình bắt đầu nghiên cứu xem nó có phù hợp với mình hay không, mình theo dõi khóa học Machine Learning của thầy Andrew Ng trên Coursera một cách nghiêm túc, và có thể nói là mình chưa bao giờ theo một khóa học nào theo cách nghiêm túc như vậy, mình cũng không hiểu vì sao, lúc này mình đã nhận thấy sự gắn kết của mình với Machine Learning. Mặc dù vậy, vào thời điểm đó, sự hiểu biết của mình về Deep Learning cũng khá mơ hồ vì trong khóa học chỉ đề cập đến một chút về mạng neurons mà thôi. Cho đến khi mình thực hiện đề tài niên luận cùng với một bạn cùng lớp của mình thì lúc đó mình mới cảm thấy mình phải học hỏi nhiều thứ, cùng thời điểm đó, mình bị thu hút bởi một thuật toán Object Detection gọi là YOLO (You Only Look Once), nhiều khái niệm bắt đầu hình thành và mình cũng có mục tiêu để theo đuổi tiếp theo, một dự án Deep Learning cho luận văn tốt nghiệp.

Vào khoảng cuối tháng 05/2018, mình tham gia thực tập tại FPT Software, đây có lẽ là bước ngoặt lớn của mình với Deep Learning, mình dành hầu hết thời gian thực tập cho việc cài đặt YOLO và cũng từ đây, nhiều thắc mắc của mình vỡ lẽ. Mình thật sự phải cảm ơn anh Ayoosh Kathuria, tác giả của loạt bài How to implement a YOLO (v3) object detector from scratch in PyTorch, trong bài viết, anh ấy có nói một câu khiến mình rất tâm đắc

> The best way to go about learning object detection is to implement the algorithms by yourself, from scratch.

Chính câu nói này đã khiến mình mình quyết định làm một loạt bài hướng dẫn về TensorFlow, vì mình hiểu, có rất nhiều bạn đang tìm hiểu về TensorFlow và đang gặp phải những vấn đề mà mình gặp phải, những bài viết này rất có thể sẽ hữu ích cho các bạn, chúng ta nên có một cộng đồng lớn những developers đang làm việc với Deep Learning nói chung hay TensorFlow nói riêng, những người đi trước sẽ nâng bước cho người đi sau và dĩ nhiên, mình vẫn phải học hỏi rất nhiều từ cộng đồng này.

Đó là một chút tâm sự, giờ thì chúng ta quay lại với TensorFlow. TensorFlow là một framework nguồn mở cho Machine Learning, đặc biệt là Deep Learning, nó cung cấp đầy đủ các công cụ để xây dựng một thuật toán Machine Learning từ nhỏ bé cho đến cực lớn. Không những vậy, đi kèm với TensorFlow còn có một số công cụ vô cùng tuyệt vời:

- TensorBoard: công cụ giúp minh họa các đồ thị tính toán (computational graph), sự thay đổi giá trị của các hàm tính toán (loss, accuracy,...) dưới dạng biểu đồ.
- TensorFlow Serving: công cụ giúp triển khai các mô hình Machine Learning viết bằng TensorFlow thành một sản phẩm thực sự.
- Các API giúp cho việc sử dụng TensorFlow dễ dàng hơn được phát triển bởi những nhà nghiên cứu về Machine Learning trên toàn thế giới (TensorFlow High Level API, TF-Slim, TensorFlow Object Detection API)
- Tập hợp code mẫu giúp cho những người mới học như chúng ta dễ tiếp cận hơn.

Nói thì nghe có vẻ phấn khởi như vậy nhưng khi tiếp xúc thật sự thì TensorFlow không dễ nhằn một chút nào, mình vẫn còn nhớ những ngày đầu mình tiếp xúc với TensorFlow, cài đặt môi trường cũng là một điều khó khăn, mình phải bỏ mấy ngày trời để tìm cách build TensorFlow để nó hoạt động trơn tru nhất trên máy của mình, nghĩ lại cũng khôi hài lắm.

Thứ khó khăn nhất khi mới bắt đầu tìm hiểu về TensorFlow có lẽ là cơ chế hoạt động của nó. Mình còn nhớ những dòng code đầu tiên mà mình gõ trên TensorFlow, nó kiểu kiểu thế này:

{% include image.html src="/assets/images/post/duong-lu-dien-tensorflow-get-started.png" caption="Những dòng code đầu tiên của mình với TensorFlow" %}

Cái quái gì thế này, số 3 với số 4 mình mới gõ đâu mất rồi, hiện lên cái gì không vậy. Chẳng những vậy, một cái chương trình cộng 2 số đơn giản trên Python giờ biến thành một nùi code mà mình chẳng hiểu là nó viết thế để làm gì cho rườm rà vậy.

```python
import tensorflow as tf

a = tf.constant(3.0, dtype=tf.float32, name='a')
b = tf.constant(4.0, dtype=tf.float32, name='b')
sum = tf.add(a, b, name='sum')


with tf.Session() as sess:
    result = sess.run(sum)
    print(result)
```

Đó là do mình chưa nắm được cơ chế của nó, TensorFlow hoạt động theo cơ chế đồ thị tính toán tĩnh (static computational graph), nghĩa là ta phải xây dựng lên một đồ thị tính toán trước, sau đó mới thực hiện các tính toán trên đó. Ba dòng

```python
a = tf.constant(3.0, dtype=tf.float32, name='a')
b = tf.constant(4.0, dtype=tf.float32, name='b')
sum = tf.add(a, b, name='sum')
```

tạo ra một đồ thị tính toán như sau:

{% include image.html src="/assets/images/post/duong-lu-dien-tensorflow-add-graph.png" caption="Đồ thị tính toán cho bài toán cộng 2 số trên TensorFlow" %}

Nó khác với các framework khác như PyTorch sử dụng cơ chế đồ thị tính toán động (dynamic computational graph), các nút của đồ thị được xây dựng ngay trong quá trình thực thi chương trình.

{% include image.html src="/assets/images/post/duong-lu-dien-pytorch-dynamic-graph.gif" caption="Cơ chế đồ thị tính toán động của PyTorch" credit="http://pytorch.org/about/" %}
