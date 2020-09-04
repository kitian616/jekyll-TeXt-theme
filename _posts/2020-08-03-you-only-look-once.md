---
title: YOLOv1 - You only look once
date: 2020-04-26
tags: ['Machine Learning', 'Deep Learning', 'Computer Vision']
---

![Cover](../../../assets/images/2020-08-03/image.png)

---

### Giới thiệu

Những năm gần đây, object detection đã trở thành một trong những ứng dụng quan trọng nhất trong Computer Vision. Bạn có thể nhận thấy ứng dụng của nó trong xe tự lái, chẩn đoán bệnh, kiểm tra mùa vụ và vv. Vào năm 2014, hiệu suất của object detection khá chậm so với mức real-time, đơn giản là vì không có một kiến trúc nào đủ đơn giản để xử lý các luồng real-time.

Hiểu rõ vấn đề này, YOLO được sáng chế nhằm mục đích đem đến hiệu suất thời gian thực cho các ứng dụng của cuộc sống. Tính đến nay, nó cũng là xương sống cho các phần như object tracking và robotic. Ngày hôm nay, chúng ta sẽ nhìn sâu hơn về cách YOLO hoạt động, các vấn đề nó có thể giải quyết và đương nhiên là hiệu suất như thế nào.

Trước hết thì chúng ta sẽ tìm hiểu về YOLOv1, các version 2, 3, 4 sẽ được bổ sung ở những bài sau.



### Hạn chế ban đầu

Các cách tiếp cận cũ sử dụng Sliding Window hay Region Proposal để tạo bounding box. Sau đó tất cả bounding box được đưa vào một classification network để phân loại các đối tượng. Tiếp theo đó là các phương thức xử lý khác để bỏ qua những bounding box bị trùng và tinh chỉnh lại bounding box.

Những phương pháp này tốn rất nhiều thời gian ở bước 2 nên chỉ được ứng dụng cho các tác vụ không liên quan đến real-time. Đồng thời việc huấn luyện cũng gặp rất nhiều hạn chế vì phải huấn luyện từng module riêng biệt cho mỗi tác vụ.



### Đánh giá

__Điểm mạnh:__
- YOLO đồng thời phát hiện bounding box và phân loại đối tượng, vì vậy rất tiện lợi cho việc huấn luyện và tối ưu.
- Có tốc độ xử lý real-time.
- Không như Sliding Window và Region Proposal, YOLO suy đoán trên toàn bộ hình ảnh.
- Học các biểu diễn tổng quát (generalizable representations) của đối tượng nên có thể hoạt động trong nhiều ngữ cảnh khác nhau.



### Cách hoạt động

![Cover](../../../assets/images/2020-08-03/yolo_design.jpg)

Network sẽ chia hình ảnh thành một lưới gồm S x S ô. Ở mỗi ô, chúng ta cần xác định `K` bounding box để so sánh với ground truth bounding box, và YOLO sẽ đồng thời kiểm tra `C` đối tượng có trong ô.

Giả sử  `S = 7`, `K = 3` và `C = 5`, chúng ta sẽ nhận được output của network có chiều `7 x 7 x 20`. Output của mỗi ô được định nghĩa như sau:

$$ \small{y^{T} = [
  \langle\underbrace{\left\{p_1(object), x_1, y_1, w_1, h_1\right\}, ..., \left\{p_2(object), x_K, y_K, w_K, h_K\right\}}_{\text{K bounding boxes}}\rangle,\langle\underbrace{P(class_1), P(class_2),..., P(class_C)}_{\text{Probabilities of C classes}}\rangle
  ]} $$

Với thông số:
- $ p_x(object) $: Xác suất có đối tượng trong bounding box
- $ x_K, y_K, w_K, h_K $: Tọa độ tâm, chiều rộng và chiều cao của bounding box
- $ p(class_C) $: Xác suất đối tượng trong bounding box thuộc class C

Theo cách output được định nghĩa, mỗi ô sẽ chỉ trả về một bounding box cho đối tượng có xác suất cao nhất (nếu có) kể cả khi trong ô có 3 đối tượng.




### Kiến trúc của YOLO

![Cover](../../../assets/images/2020-08-03/architecture.png)


#### Base network

Như bạn đã thấy từ hình ở trên, base network của YOLOv1 bao gồm 20 convolutional layer, theo sau đó là các Leaky ReLU activation function và Pooling layer (phiên bản Fast-YOLO chỉ có 9 convolutional layer). Lưu ý rằng các layer có filter 1x1 được thêm vào để giảm không gian đặc trưng (feature space) của các layer phía trước. Nhằm mục đích tái hiện các output, kích thước của image sẽ được tăng lên thành `448 x 448 x 3` thay vì `224 x 224 x 3` và trả về một tensor có không gian `7 x 7 x 1024`.

(YOLO không xử dụng Batch Normalization vì paper được viết năm 2014, 1 năm trước sự khám phá của kĩ thuật này).

Leaky ReLU: $\phi(x) = \begin{cases}
                            \alpha x & x \leq 0 \\ \newline
                            x & x > 0
                          \end{cases}$

Theo paper, base network đã được huấn luyện sử Darknet framework và ImageNet classification dataset. Kết quả là các trọng số đã đạt được độ chính xác lên đến 88% in ImageNet 2012 validation set.


#### Module nhận diện đối tượng

4 layers cuối cùng của kiến trúc YOLO (theo hình) bao gồm 2 convolutional layer và 2 fully-connected layers. 3 layers đầu tiên vẫn sử dụng Leaky ReLU activation function còn layer cuối cùng sử dụng linear activation function. Cuối cùng, network sẽ trả về một tensor có chiều `7 x 7 x 30`.


#### Loss function





### Các thắc mắc thường gặp
