# Binary Search Tree

### 二叉搜索树中的搜索700

``````java
class Solution {
    public TreeNode searchBST(TreeNode root, int val) {
        if (root == null || root.val == val) {
            return root;
        } else if (root.val < val) {
            return searchBST(root.right, val);
        } else {
            return searchBST(root.left, val);
        }
    }
}
``````

简单

### 验证二叉搜索树98

``````java
class Solution {
    TreeNode max;  // 写在这里是global变量
    public boolean isValidBST(TreeNode root) {
        if (root == null) {
            return true;
        }
        // 左
        boolean left = isValidBST(root.left);
      	// 如果这里left是false，返回false，否则继续往下
        if (!left) {
            return false;
        }
        // 中：这里写对节点的操作
        if (max != null && root.val <= max.val) {
            return false;
        }
        max = root;  // 第一个max为最左节点，然后依次往右，与中序遍历顺序一致。所以后面的max应该永远大于前面的max
        // 右
        boolean right = isValidBST(root.right);
        if (!right) {
            return false;
        }
        return true;
    }
}
``````

思路：

二叉搜索树用中序遍历，可以从按照从小到大遍历节点！每次遇到一个节点都把该节点的值存入一个变量，然后每次判断这个变量是否大于之前的值。

### 二叉搜索树的最小绝对差530

``````java
class Solution {
    int min = Integer.MAX_VALUE;  // 记忆：定义一个最大值
    TreeNode pre;
    
    public int getMinimumDifference(TreeNode root) {
        if (root == null) {
            return 0;
        }
        getMinimum(root);
        return min;
    }

    private void getMinimum(TreeNode root) {
        if (root == null) {
            return;
        }
      
        getMinimum(root.left);
      
        if (pre != null) {
            min = root.val - pre.val < min ? root.val - pre.val : min;
        }
        pre = root;
      
        getMinimum(root.right);
    }
}
``````

思路：

二叉搜索树用中序遍历，因为每个点的值是按顺序增大的，每个点和上个点的差值都有可能是最小差值。

用一个global变量记录上一个点，再用一个global变量记录最小差值，其余交给中序遍历。

### 二叉搜索树中的众数501

``````java
class Solution {
    // 注意：global variable不要给初始值，去函数里再给初始值，不然会一直输出初始值
    // 注意：ArrayList<Integer>与int[]不是同一个type
    //      ArrayList是动态的，不需要定义长度；Array是固定长度的
    ArrayList<Integer> mode;  
    TreeNode pre;
    int max;
    int count;
    public int[] findMode(TreeNode root) {
        mode = new ArrayList<>();
        max = 0;
        count = 0;
        if (root == null) {
            return null;
        }
        constructModeArray(root);
        int[] res = new int[mode.size()];
        for (int i = 0; i < mode.size(); i++) {
            res[i] = mode.get(i);
        }
        return res;
    }

    private void constructModeArray(TreeNode root) {
        if (root == null) {
            return;
        }

        constructModeArray(root.left);

        if (pre == null || root.val != pre.val) {
            count = 0;
            count++;
        } else {
            count++;
        }

        if (count > max) {
            mode.clear();
            mode.add(root.val);  // 注意：ArrayList没有mode[0]这种方法，只有get, add
            max = count;
        } else if (count == max) {
            mode.add(root.val);
        }
        
        pre = root;

        constructModeArray(root.right);
    }
}
``````

思路：

1. 把二叉搜索树当做一个单调递增的array来思考
2. 用pre储存前一个数，用max储存最大次数，用count储存现在数出现的次数
3. 当前数与前一个不同（或者当前数是第一个），count变成1；当前数和前一个数相同，count++
4. 当count大于max时，删除array里所有的元素，加入现在的数，然后更新max=count
5. 当count等于max时，把现在的数加入array

### 二叉搜索树的最近公共祖先235

``````java
class Solution {
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        // 如果当前node的值比[p, q]大，往左找
        if (root.val > p.val && root.val > q.val) {
            return lowestCommonAncestor(root.left, p, q);
        }
        // 如果当前node的值比[p, q]小，往右找
        if (root.val < p.val && root.val < q.val) {
            return lowestCommonAncestor(root.right, p, q);
        }
        // 如果当前值在[p, q]中间，该node一定为最近公共祖先，直接返回
        return root;
    }
}
``````

思路：

首先，画一棵树。

观察发现，对于某个node来说，它是它左子树（加上自己）中的任意一个node和它右子树（加上自己）中的任意一个node的最近公共祖先。

因为对于二叉搜索树来说，左子树的所有node小于该node，右子树的所有node大于该node，所以该node的范围一定处于左子树中的那个node和右子树中的那个node之间，也就是[p, q]之间。

从上到下遍历，第一个处于[p, q]之间的node即为公共祖先，并且是最近公共祖先（不知怎么证明，但是可以举例子看）

所以代码这么写：

1. 看当前node的值是否在[p, q]内，如果在，直接返回该值。如果比[p, q]大，说明要找的node在左子树里，反之在右子树。
2. 往左或右继续遍历，判断下一个node是否在[p, q]内。如果在，返回该值，如果不在，继续往左或右找。

### 二叉搜索树中的插入操作701

``````java
class Solution {
    public TreeNode insertIntoBST(TreeNode root, int val) {
        if (root == null) {
            TreeNode node = new TreeNode(val);
            return node;
        }
        if (val < root.val) {
            root.left = insertIntoBST(root.left, val);
        }
        if (val > root.val) {
            root.right = insertIntoBST(root.right, val);
        }
        return root;
    }
}
``````

思路：

用给定的值按照值的大小往下遍历BST，遇到了空节点放入即可

![701.二叉搜索树中的插入操作](https://code-thinking.cdn.bcebos.com/gifs/701.%E4%BA%8C%E5%8F%89%E6%90%9C%E7%B4%A2%E6%A0%91%E4%B8%AD%E7%9A%84%E6%8F%92%E5%85%A5%E6%93%8D%E4%BD%9C.gif)

### 删除二叉搜索树中的节点450

``````java
class Solution {
    public TreeNode deleteNode(TreeNode root, int key) {
        if (root == null) {
            return null;
        }

        if (key < root.val) {
            root.left = deleteNode(root.left, key);
        } else if (key > root.val) {
            root.right = deleteNode(root.right, key);
        } else {
            if (root.left == null) {  // 左无孩子则把右搬上去（如果左右都无，则搬上去null）
                return root.right;
            } else if (root.right == null) {  // 右无孩子则把左搬上去
                return root.left;
            } else {  // 如果有两个孩子，则把任意一个子树移上来，这里移左子树
                TreeNode node = root.left;
                // 然后把左子树下方最右侧连接上被删除的节点的右子树
                while (node.right != null) {
                    node = node.right;
                }
                node.right = root.right;
                return root.left;
            }
        }
        return root;
    }
}
``````

思路：

按node大小遍历节点，找到要删除的节点后，根据有无孩子来分类讨论。

### 修建二叉搜索树669

``````java
class Solution {
    public TreeNode trimBST(TreeNode root, int low, int high) {
        if (root == null) {
            return null;
        }
        if (root.val < low) {
            return trimBST(root.right, low, high);  // 这里不能return root.right，而是要return被修剪过的root.right。
        }
        if (root.val > high) {
            return trimBST(root.left, low, high);
        }
        root.left = trimBST(root.left, low, high);
        root.right = trimBST(root.right, low, high);
        return root;
    }
}
``````

思路：

如果一个节点小于low，需要删除，那么它的左子树一定都小于low，也可以删除，但是右子树一定都大于low，需要保留，将右子树放到被删除节点的位置即可。

如果一个节点大于high，同理。

注意：

我们放于被删除节点的子树的内部也需要被修剪，所以不能return root.right，而是要return被修剪过的root.right。

### 将有序数组转换为AVL二叉搜索树108

``````java
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        return buildTree(nums, 0, nums.length);
    }
    private TreeNode buildTree(int[] nums, int start, int end) {
        if (start >= end) { 
            return null;
        }
        int mid = start + (end - start) / 2;
        TreeNode root = new TreeNode(nums[mid]);
        root.left = buildTree(nums, start, mid);
        root.right = buildTree(nums, mid + 1, end);
        return root;
    }
}

// 或
class Solution {
    public TreeNode sortedArrayToBST(int[] nums) {
        int len = nums.length;
        if (len == 0) return null;
        TreeNode root = new TreeNode(nums[len / 2]);
        root.left = sortedArrayToBST(Arrays.copyOfRange(nums, 0, len / 2));
        root.right = sortedArrayToBST(Arrays.copyOfRange(nums, len / 2 + 1, len));
        return root;
    }
}
``````

思路：

recursive构建树和子树，纪录数组index start和end

注意：

AVL树中任意节点左右两个子树的高度差不超过1，所以

![image-20230609210555657](/Users/samuel/Library/Application Support/typora-user-images/image-20230609210555657.png)

不是AVL树，因为3的左子树高度为2，右子树高度为0

### 把二叉搜索树转换为累加树

``````java
class Solution {
    int sum;  // 这里放一个sum使其能被两个函数都使用
    public TreeNode convertBST(TreeNode root) {
        sum = 0;
        convert(root);
        return root;
    }

    private void convert(TreeNode root) {
        if (root == null) {
            return;
        }
        convert(root.right);
        root.val = root.val + sum;
        sum = root.val;
        convert(root.left);
    }
}
``````

思路：

![538.把二叉搜索树转换为累加树](https://code-thinking-1253855093.file.myqcloud.com/pics/20201023160751832.png)

根据树的特征，可以看出需要通过右中左的方式遍历来累加。

用一个global variable纪录累加的值，用一个recursion函数实现。因为不用构建新的二叉树，所以函数不用return TreeNode。

### 230. Kth Smallest Element in a BST

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/01/28/kthtree1.jpg)

```
Input: root = [3,1,4,null,2], k = 1
Output: 1
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/01/28/kthtree2.jpg)

```
Input: root = [5,3,6,2,4,null,null,1], k = 3
Output: 3
```

**思路：**
中序遍历，recursion

设定一个count = 0，每次左中右遍历到中时count++直到等于k，return当前root的值

recursion的返回值为-1说明还没找到第k个最小值，否则说明找到了，就继续向上返回。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode() {}
 *     TreeNode(int val) { this.val = val; }
 *     TreeNode(int val, TreeNode left, TreeNode right) {
 *         this.val = val;
 *         this.left = left;
 *         this.right = right;
 *     }
 * }
 */
class Solution {
    int count = 0;
    public int kthSmallest(TreeNode root, int k) {
        if (root == null) return -1;
        int left = kthSmallest(root.left, k);
        if (left != -1) return left;
        count++;
        if (k == count) return root.val;
        int right = kthSmallest(root.right, k);
        if (right != -1) return right;
        return -1;
    }
}
```

### 