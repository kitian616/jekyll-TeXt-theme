---
title: '记录一场面试 | MSRA SIG'
date: 2020-11-20 23:21:43
tags: [自我回顾,生活琐碎]
---
![](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/1.png)

本来不打算写，因为也很大概率不通过。但毕竟也是人生中第一段工作面试，恰好今晚上nlp组的面试突然被咕咕了(,,ԾㅂԾ,,)。
<!--more-->

我是认为自己能力不够➕原有的方向过于小众，企业的研究岗可能不会要我。原打算转个方向努力申个外校的实验室(这又是另一段故事了)，努力开启新的研究生涯。

但是转机的一切，还是要从M同学从10月就开始准备算法实习开始说起。

M同学从tx的子公司投到wxg，再从百度搜索投到抖音推荐组，没有一个组不想要他。17号时他突发奇想打算投MSRA（M: “反正都是要投的”），于是当天投，三个组的mentor就直接给他打了电话，隔天就进行了两个面试，第三天就接了offer，第四天就开始考虑该拒哪个组了。

18号晚上，M同学对我说: szg再一次劝你投MSRA。

19号中午2点半，原在MSRA实习的Z学长看到了某推荐组招实习的通知，再次劝我去。

于是我的简历就到了SIG的手上。

19号下午3点过，当我推着行李箱走在回家的路上，还没到达地铁口，我接到了来自白金的电话:

- “您好，请问是Wang Xing吗？我们这里有人推荐你，你是想来MSRA实习吗？”
- “你能做个介绍吗？什么时候能入职？”
- “项目经历”
- “你在项目里担任了什么角色”
- “你什么时候有时间？我们可以约个正式面试”

于是正式面试定在了20号的下午1点-2点。紧张死辽，当天晚上就回去复习算法和论文。

---

# 正式面试

- “自我介绍”
- “具体说一下的项目”
    - 大概问了一下：为什么要这样做？你们的创新性在哪？你做了哪些工作？
    - 因为我原有的项目都不是DL的，感觉她也不是很理解
- 算法题：（算法题我都是胡诌的，没刷过）
    - 链表反转
        ```java
        Input: L0→L1→…→Ln-1→Ln  12345
        Output: L0→Ln→L1→Ln-1→L2→Ln-2→… 15243

        public ListNode reverse(ListNode head) {
            if (head == null || head.next == null)
                return head;
            
            ListNode dummy = new ListNode(0);
            dummy.next = head;
            ListNode slow = dummy, fast = dummy;
            
            while(fast != null && fast.next != null) {
                slow = slow.next;
                fast = fast.next.next;
            }
            
            ListNode mid = slow.next;
            ListNode node = mid, newNode = null;
            
            //反转
            while(node != null){
                ListNode next = node.next;
                node.next = newNode;
                newNode = node;
                node = next;
            }
            
            node = mid;
            while(head.next != mid) {
                ListNode head_next = head.next;
                ListNode node_next = node.next;
                head.next = node;
                node.next = head_next;
                node = node_next;
                head = head_next;
            }
            
            head.next = null;
            return head;
        }
        ```

    - 乱序数组里，找出最小的缺失正整数

        ```java
        Input: [1,0,-1]
        Output: 2

        Input: [1,2,5,4]
        Output: 3
        ```

- “最后我想再问一下，你做过深度学习吗”
    - 在南大nlp夏令营的时候有一段垃圾邮件分类的经历，这里再次感谢M同学。
- 你有什么想问我的吗
    - 你们组是做什么方向的
    - 偏研究还是偏业务？
    - 对我面试的评价

---

感觉姐姐还是很可爱的(●'◡'●)。过不了就算啦，还是感谢有这一段经历。

---

# 结果 (写于2020年12月1日)

最后还是不出意外的没有过T T。

![](https://xtopia-1258297046.cos.ap-shanghai.myqcloud.com/B59E4483759CED4581FBA3F2D0DCDDDB.png)
