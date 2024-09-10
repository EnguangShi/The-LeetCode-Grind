# Linked List

.add(index, value)：会将value插入到指定index

.toArray(new int[length])：会转换linked list为array

### 链表创建

``````java
public class ListNode {
  int val;
  ListNode next;
  
  public ListNode(){};
  
  public ListNode(int val) {
    this.val = val;
  }
  
  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }
}
``````

Note:

三个constructor应付三种不同类型的node

### 移除链表元素203

``````java
class Solution {
    public ListNode removeElements(ListNode head, int val) {
        ListNode dummy = new ListNode(-1, head);
        ListNode prev = dummy;
        ListNode curr = head;
        while (curr != null) {
            if (curr.val == val) {
                prev.next = curr.next;
            } else {
                prev = curr;  // 如果curr不被删除，则prev和curr都往前走
            }
            curr = curr.next;  // // 如果curr被删除，则prev不变，curr往前走
        }
        return dummy.next;
    }
}
``````

![203_链表删除元素1](https://img-blog.csdnimg.cn/20210316095351161.png)

Note:

O(n)

考虑：

- 链表为空的情况

- head如何删除：使用dummy使其指向head

记忆：使用prev和curr而不是只使用curr

### 设计链表707

``````java
public class ListNode {
  int val;
  ListNode next;
  
  public ListNode(){};
  
  public ListNode(int val) {
    this.val = val;
  }
  
  public ListNode(int val, ListNode next) {
    this.val = val;
    this.next = next;
  }
}

class MyLinkedList {
    int size;
    ListNode head;
    
    public MyLinkedList() {
        size = 0;
        head = new ListNode(-1);  // dummy head
    }
    
    public int get(int index) {
        if (index < 0 || index >= size) {
            return -1;
        }
      
        ListNode curr = head;
        for (int i = 0; i < index + 1; i++) {
            curr = curr.next;
        }
        return curr.val;
    }
    
    public void addAtHead(int val) {
        addAtIndex(0, val);
    }
    
    public void addAtTail(int val) {
        addAtIndex(size, val);
    }
    
    public void addAtIndex(int index, int val) {
        if(index < 0 || index > size) {
            return;
        }
        
        ListNode curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.next;
        }
        ListNode newNode = new ListNode(val, curr.next);
        curr.next = newNode;
        size ++;
    }
    
    public void deleteAtIndex(int index) {
        if (index < 0 || index > size - 1) {
            return;
        }
        
        ListNode curr = head;
        for(int i = 0; i < index; i++) {
            curr = curr.next;
        }
        curr.next = curr.next.next;
        size --;
    }
}
``````

### 反转链表206

``````java
// Iterative
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode slow = null;
        ListNode fast = head;
        ListNode tmp = null;
        while (fast != null) {
            tmp = fast.next;
            fast.next = slow;
            slow = fast;
            fast = tmp;
        }
        return slow;
    }
}
``````

Note: 

记忆：slow一开始指向null而不是head（指向dummy head)

``````java
// Recursive
class Solution {
    public ListNode reverseList(ListNode head) {
        if (head == null || head.next == null) {
            return head;  // base case: 没有node时return null，只有最后一个node时return它本身
        }
        ListNode reversedHead = reverseList(head.next);  // reversedHead->最后一个node
        head.next.next = head;
        head.next = null;
        return reversedHead;  // reversedHead->最后一个node
    }
}
``````

Note:

从头至尾reverseList的return value都是反转后的head，也就是反转前的tail

考虑：

Base case：

- 只剩一个node：一个node反转为它本身
- 没有node：反转为null

### 两两交换链表中的节点24

``````java
class Solution {
    public ListNode swapPairs(ListNode head) {
        ListNode dummy = new ListNode(-1, head);
        ListNode curr = dummy;
        
        ListNode tmp1 = null;
        ListNode tmp2 = null;
      	// curr.next == null时，curr为最后一个node
      	// curr.next.next == null时，curr为倒数第二个node
      	// 这两种情况都不需再对后面的node进行操作
        while (curr.next != null && curr.next.next != null) {
            tmp1 = curr.next;
            curr.next = curr.next.next;
            tmp2 = curr.next.next;
            curr.next.next = tmp1;
            curr.next.next.next = tmp2;
            curr = curr.next.next;
        }
        return dummy.next;
    }
}
``````

Note:

O(n)

一定要创建dummy head和画图看指针操作

考虑：

何时不用继续循环：

- 目前是最后一个node：接下来没有需要交换的了
- 目前是倒数第二个node：最后一个node为单数，也不需要交换

### 删除链表的倒数第N个节点19

``````java
class Solution {
    public ListNode removeNthFromEnd(ListNode head, int n) {
        ListNode dummy = new ListNode(-1, head);
        ListNode slow = dummy;
        ListNode fast = dummy;
        
        int i = 0;
        while(fast.next != null) {
            if(i < n) {
                fast = fast.next;
                i++;
            } else {
                slow = slow.next;
                fast = fast.next;
            }
        }
        
        slow.next = slow.next.next;
        return dummy.next;
    }
}
``````

Note:

O(L), L = length of the linked list

双指针法：如果要删除倒数第n个节点，让fast移动n步，然后让fast和slow同时移动，直到fast指向链表末尾。删掉slow所指向的节点就可以了。

### 链表相交160

``````java
public class Solution {
    public ListNode getIntersectionNode(ListNode headA, ListNode headB) {
        
        // 求链表A, B的长度
        ListNode currA = headA;
        ListNode currB = headB;
        int lenA = 0;
        int lenB = 0;
        while(currA != null) {
            currA = currA.next;
            lenA++;
        }
        while(currB != null) {
            currB = currB.next;
            lenB++;
        }
        
        currA = headA;
        currB = headB;
        // let lenA be the length of the longer linked list, and currA be the head
        if (lenB > lenA) {
            // 1. swap (lenA, lenB);
            int tmpLen = lenA;
            lenA = lenB;
            lenB = tmpLen;
            // 2. swap (currA, currB);
            ListNode tmpNode = currA;
            currA = currB;
            currB = tmpNode;
        }
        
        // 求长度差
        int gap = lenA - lenB;
        // 让A, B末尾对齐
        for(int i = 0; i < gap; i++) {
            currA = currA.next;
        }
        
        // 遍历A, B，相同则返回
        while(currA != null) {
            if (currA == currB) {
                return currA;
            }
            currA = currA.next;
            currB = currB.next;
        }
        
        // 无结果，二者不相交
        return null;
    }
}
``````

![面试题02.07.链表相交_2](https://code-thinking.cdn.bcebos.com/pics/%E9%9D%A2%E8%AF%95%E9%A2%9802.07.%E9%93%BE%E8%A1%A8%E7%9B%B8%E4%BA%A4_2.png)

Note:

O(lenA+lenB)

考虑：

需要确认A是更长还是B是更长

### 环形链表142

``````java
public class Solution {
    public ListNode detectCycle(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                slow = head;
                while (slow != fast) {
                    fast = fast.next;
                    slow = slow.next;
                }
                return fast;
            }
        }
        return null;
    }
}
``````

![142.环形链表II（求入口）](https://tva1.sinaimg.cn/large/008eGmZEly1goo58gauidg30fw0bi4qr.gif)

Note:

O(n)

这题思路太难想到，还是看代码随想录吧

### 21. Merge Two Sorted Lists

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/03/merge_ex1.jpg)

```
Input: list1 = [1,2,4], list2 = [1,3,4]
Output: [1,1,2,3,4,4]
```

**思路：**

弄个dummynode叫prev然后两个list中哪个node小就让prev指向它然后它变成prev再继续往后看

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
        if (list1 == null) {
            return list2;
        }
        if (list2 == null) {
            return list1;
        }

        ListNode dummy = new ListNode();
        ListNode prev = dummy;

        while (list1 != null && list2 != null) {
            if (list1.val < list2.val) {
                prev.next = list1;
                list1 = list1.next;
            } else {
                prev.next = list2;
                list2 = list2.next;
            }
            prev = prev.next;
        }

        if (list1 == null) {
            prev.next = list2;
        } else if (list2 == null) {
            prev.next = list1;
        }

        return dummy.next;
    }
}
```

### 141. Linked List Cycle

**Example 1:**

![img](https://assets.leetcode.com/uploads/2018/12/07/circularlinkedlist.png)

```
Input: head = [3,2,0,-4], pos = 1
Output: true
Explanation: There is a cycle in the linked list, where the tail connects to the 1st node (0-indexed).
```

**思路：**

用两个指针，一快一慢同时出发，如果相遇说明有循环，如果某个指针指到了null说明无循环。

```java
/**
 * Definition for singly-linked list.
 * class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) {
 *         val = x;
 *         next = null;
 *     }
 * }
 */
public class Solution {
    public boolean hasCycle(ListNode head) {
        if (head == null) {
            return false;
        }
        
        ListNode fast = head;
        ListNode slow = head;
        while (fast.next != null && fast.next.next != null && slow.next != null) {
            fast = fast.next.next;
            slow = slow.next;
            if (fast == slow) {
                return true;
            }
        }
        return false;
    }
}
```

### 206. Reverse Linked List

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/19/rev1ex1.jpg)

```
Input: head = [1,2,3,4,5]
Output: [5,4,3,2,1]
```

**思路：**

用个dummy head，然后换换换

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode reverseList(ListNode head) {
        ListNode prev = null;
        ListNode curr = head;

        while(curr != null) {
            ListNode next = curr.next;
            curr.next = prev;
            prev = curr;
            curr = next;
        }

        return prev;
    }
}
```

### 876. Middle of the Linked List

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/07/23/lc-midlist1.jpg)

```
Input: head = [1,2,3,4,5]
Output: [3,4,5]
Explanation: The middle node of the list is node 3.
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/07/23/lc-midlist2.jpg)

```
Input: head = [1,2,3,4,5,6]
Output: [4,5,6]
Explanation: Since the list has two middle nodes with values 3 and 4, we return the second one.
```

**思路：**

快的是慢的两倍速，快的到终点时慢的在中间。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode middleNode(ListNode head) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode fast = head;
        ListNode slow = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        return slow;
    }
}
```

### 328. Odd Even Linked List

**思路：**
一个指针负责odd，一个指针负责even，在循环中不断把even放到最后，让odd指向下一个odd

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode oddEvenList(ListNode head) {
        if (head == null || head.next == null || head.next.next == null) {
            return head;
        }
        ListNode odd = head;
        ListNode even = head.next;
        ListNode firstEven = even;
        ListNode end = head;
        
        while (end.next != null) {
            end = end.next;
        }

        do {
            odd.next = even.next;
            end.next = even;
            end = even;
            end.next = null;
            odd = odd.next;
            even = odd.next;
        } while (odd != firstEven && odd.next != firstEven);

        return head;
    }
}
```

### 148. Sort List**

**思路：**


要实现时间复杂度为O(nlogn)且空间复杂度为O(1)的链表排序，可以使用归并排序（Merge Sort）。归并排序非常适合链表这种数据结构，因为它通过重新组织已有节点的指针来排序，而不是像数组排序那样需要额外的空间来创建数组副本。

归并排序的基本思路是将链表分成两半，递归地对每一半进行排序，然后合并两个已排序的链表。对于链表，这意味着需要完成以下几个步骤：

1. **分割链表**：找到链表的中点，并将链表从中间断开为两个链表。
2. **递归排序**：递归地对两个子链表进行排序。
3. **合并排序好的链表**：将两个已排序的子链表合并成一个单一的、排序好的链表。

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode sortList(ListNode head) {
        // 基准情况：如果链表为空或者只有一个节点，则不需要排序
        if (head == null || head.next == null) {
            return head;
        }
        
        // 找到链表的中点，并从中间断开
        ListNode prev = null, slow = head, fast = head;
        while (fast != null && fast.next != null) {
            prev = slow;
            slow = slow.next;
            fast = fast.next.next;
        }
        prev.next = null; // 断开链表
        
        // 递归地对两半链表进行排序
        ListNode l1 = sortList(head);
        ListNode l2 = sortList(slow);
        
        // 合并两个排序好的链表
        return merge(l1, l2);
    }
    
    // 合并两个排序好的链表
    private ListNode merge(ListNode l1, ListNode l2) {
        ListNode dummy = new ListNode(0);
        ListNode curr = dummy;
        while (l1 != null && l2 != null) {
            if (l1.val < l2.val) {
                curr.next = l1;
                l1 = l1.next;
            } else {
                curr.next = l2;
                l2 = l2.next;
            }
            curr = curr.next;
        }
        // 处理任一列表剩余的节点
        if (l1 != null) {
            curr.next = l1;
        } else if (l2 != null) {
            curr.next = l2;
        }
        return dummy.next;
    }
}
```

### 143. Reorder List*

**思路：**

1. 先找到中点： 1->2->3(this)->4或1->2->3(this)->4->5
2. 把中点后的翻转：1->2->3<-4或1->2->3<-4<-5
3. 让1指向5，5指向2，然后1变成2，5变成4，重复该步骤

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public void reorderList(ListNode head) {
        ListNode slow = head;
        ListNode fast = head;
        while (fast != null && fast.next != null) {
            slow = slow.next;
            fast = fast.next.next;
        }
        ListNode left = head;
        ListNode right = reverse(slow);
        while (left.next != right && left != right) {
            ListNode leftNext = left.next;
            ListNode rightNext = right.next;
            left.next = right;
            right.next = leftNext;
            left = leftNext;
            right = rightNext;
        }
    }

    private ListNode reverse(ListNode head) {
        ListNode prev = null;
        while (head != null) {
            ListNode next = head.next;
            head.next = prev;
            prev = head;
            head = next;
        }
        return prev;
    }
}
```

### 61. Rotate List

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/rotate1.jpg)

```
Input: head = [1,2,3,4,5], k = 2
Output: [4,5,1,2,3]
```

**思路：**

1. 假设是12345，k=2要变成45123，要找到3，4，5，让3指向null，让5指向head，然后return 4
2. 先数几个node
3. k = k % node的数量 (k = 7和k = 2效果是一样的)
4. 先让指针1往前走k格，然后指针2从头开始和指针1一起走，直到指针1到达tail，此时指针1指向5，指针2指向3，再把指针3的下一个node保存用来return

```java
/**
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode() {}
 *     ListNode(int val) { this.val = val; }
 *     ListNode(int val, ListNode next) { this.val = val; this.next = next; }
 * }
 */
class Solution {
    public ListNode rotateRight(ListNode head, int k) {
        if (head == null || head.next == null) {
            return head;
        }

        ListNode node = head;
        int count = 0;
        while (node != null) {
            node = node.next;
            count++;
        }
        k = k % count;

        ListNode stop = head;
        for (int i = 0; i < k; i++) {
            stop = stop.next;
        }

        ListNode end = head;
        while (stop.next != null) {
            stop = stop.next;
            end = end.next;
        }

        stop.next = head;
        ListNode newHead = end.next;
        end.next = null;
        return newHead;
    }
}
```

### 