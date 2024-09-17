# Binary Tree

![Screen Shot 2022-11-19 at 1.58.28 PM](/Users/samuel/Library/Application Support/typora-user-images/Screen Shot 2022-11-19 at 1.58.28 PM.png)

**构建二叉树：**

``````java
public class TreeNode {
    int val;
  	TreeNode left;
  	TreeNode right;
  	TreeNode() {}
  	TreeNode(int val) { this.val = val; }
  	TreeNode(int val, TreeNode left, TreeNode right) {
    		this.val = val;
    		this.left = left;
    		this.right = right;
  	}
}
``````

**前中后序遍历框架（递归）：**

这里用前序作为例子，中序后序只需改变对root操作的位置即可

``````java
// 前序遍历对每个元素做某种操作
class Solution {
    public void preorderRecursion(TreeNode root) {
        if (root == null) {
            return;
        }
        // 这里放对root的操作
        preorderRecursion(root.left);
        preorderRecursion(root.right);
    }
}
``````

``````java
// 前序遍历一棵树构造另一棵树
class Solution {
    public TreeNode preorderConstruct(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode root = new TreeNode(root.val)  // 这里可以对root进行操作
        root.left = preorderConstruct(root.left);
        root.right = preorderConstruct(root.right);
      	return root;
    }
}
``````

**前序遍历框架（迭代）：**

``````java
class Solution {
    public void preorderIteration(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if (root == null) {
            return null;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // 这里对node进行操作，放进result中
            if (node.right != null) {
                stack.push(node.right);
            }  // 先放右孩子，让右孩子比左孩子后出stack
            if (node.left != null) {
                stack.push(node.left);
            }
        }
      	// result中的顺序是按中左右遍历的
        return result;
    }
}
``````

**后序遍历框架（迭代）：**

``````java
class Solution {
    public void postorderIteration(TreeNode root) {
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if (root == null) {
            return null;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            // 这里对node进行操作，放进result中
            if (node.left != null) {
                stack.push(node.left);
            }  // 先放左孩子，让左孩子比右孩子后出stack
            if (node.right != null) {
                stack.push(node.right);
            }
        }
      	// 目前result中的顺序是按中右左遍历的
      	Collections.reverse(result)
        // Collections.reverse()后是左右中
        return result;
    }
}
``````

**层序遍历框架：**

``````java
class Solution {
    public void levelOrder(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        while (!deque.isEmpty()) {
          	int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.remove();
              	// 这里放对node的操作
                if (node.left != null) {
                    deque.add(node.left);
                }
                if (node.right != null) {
                    deque.add(node.right);
                }
            }
        }
    }
}
``````

递归函数什么时候需要返回值？什么时候不需要返回值？这里总结如下三点：

- 如果需要搜索整棵二叉树且不用处理递归返回值，递归函数就不要返回值。（113. 路径总和ii）
- 如果需要搜索整棵二叉树且需要处理递归返回值，递归函数就需要返回值。 （236.二叉树的最近公共祖先）
- 如果要搜索其中一条符合条件的路径，那么递归一定需要返回值，因为遇到符合条件的路径了就要及时返回。（112. 路径总和）

### 二叉树的前序，中序，后序遍历144、94、145

**Recursion:**

``````java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        preorder(root, result);
        return result;
    }

    public void preorder(TreeNode root, List<Integer> result) {
        if (root == null) {
            return;
        }
        result.add(root.val);
        preorder(root.left, result);
        preorder(root.right, result);
    }
}
``````

思路：

- recursion: 在主函数中构建一个新的recursion函数，把result传进去，保证result可被更改

**Iteration:**

``````java
class Solution {
    public List<Integer> preorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if (root == null) {
            return result;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.right != null) {
                stack.push(node.right);
            }
            if (node.left != null) {
                stack.push(node.left);
            }
        }
        return result;
    }
}

class Solution {
    public List<Integer> postorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<Integer>();
        Stack<TreeNode> stack = new Stack<TreeNode>();
        if (root == null) {
            return result;
        }
        stack.push(root);
        while (!stack.isEmpty()) {
            TreeNode node = stack.pop();
            result.add(node.val);
            if (node.left != null) {
                stack.push(node.left);
            }
            if (node.right != null) {
                stack.push(node.right);
            }
        }
        Collections.reverse(result);  // reverse array的库函数
        return result;
    }
}

class Solution {
    public List<Integer> inorderTraversal(TreeNode root) {
        List<Integer> result = new ArrayList<>();
        if (root == null){
            return result;
        }
        Stack<TreeNode> stack = new Stack<>();
        TreeNode cur = root;
        while (cur != null || !stack.isEmpty()){
           if (cur != null){
               stack.push(cur);
               cur = cur.left;
           }else{
               cur = stack.pop();
               result.add(cur.val);
               cur = cur.right;
           }
        }
        return result;
    }
}
``````

思路：

**preorder:**

把根node放入stack

在一个判断stack有没有变空的while loop中：

1. pop一个node
2. 然后放入这个node的左孩子右孩子

**postorder:**

![前序到后序](https://img-blog.csdnimg.cn/20200808200338924.png)

**inorder (能理解但是考到会想不出):**

![image-20230608171202182](/Users/samuel/Library/Application Support/typora-user-images/image-20230608171202182.png)

### 二叉树的统一迭代法

// TODO

### 二叉树的层序遍历102

``````java
class Solution {
    public List<List<Integer>> levelOrder(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<TreeNode>();
        List<List<Integer>> result = new ArrayList<>();
        if (root == null) {
            return result;
        }
        queue.add(root);
        while (!queue.isEmpty()) {
            List<Integer> innerList = new ArrayList<>();
            int len = queue.size();  // 这里queue的长度决定里层array的长度
            
            while (len > 0) {
                9
                innerList.add(node.val);
                if (node.left != null) {
                    queue.add(node.left);
                }
                if (node.right != null) {
                    queue.add(node.right);
                }
                len--;
            }

            result.add(innerList);
        }
        return result;
    }
}
``````

思路：用queue，看清楚是要双层array还是单层。如果双层需要两个while，外侧的while决定有几个里层array，内侧的while决定里层array的长度

### 翻转二叉树226

``````java
class Solution {
    public TreeNode invertTree(TreeNode root) {
        if (root == null) {
            return root;
        }
        TreeNode temp = root.left;
        root.left = root.right;
        root.right = temp;
        invertTree(root.left);
        invertTree(root.right);
        return root;
    }
}
``````

思路：用preorder traversal遍历每个元素，交换它的left child和right child

### 对称二叉树101

``````java
class Solution {
    public boolean isSymmetric(TreeNode root) {
        return compare(root.left, root.right);
    }

    // compare用于比较二者是否轴对称
    private boolean compare(TreeNode left, TreeNode right) {
        if (left == null && right != null) {
            return false;
        }
        if (left != null && right == null) {
            return false;
        }
        if (left == null && right == null) {
            return true;
        }
        if (left.val != right.val) {  // 数值不相等，不对称
            return false;
        }
        boolean compareOutside = compare(left.left, right.right);  // 比外侧
        boolean compareInside = compare(left.right, right.left);  // 比内侧
        return compareOutside && compareInside;
    }
}
``````

思路：需要构建一个函数，用于比较两个tree是否对称。

### 二叉树的最大深度104

递归法容易，但是推荐迭代法，因为更直观地使用了层序遍历。

``````java
// 递归法
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int leftDepth = maxDepth(root.left);
        int rightDepth = maxDepth(root.right);
        return Math.max(leftDepth, rightDepth) + 1;
    }
}
``````

思路：递归法，分别求左子树和右子树的深度，然后取更深的那个+1

记忆：`Math.max`

``````java
// 迭代法
class Solution {
    public int maxDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        int depth = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.remove();
                if (node.left != null) {
                    deque.add(node.left);
                }
                if (node.right != null) {
                    deque.add(node.right);
                }
            }
            depth++;
        }
        return depth;
    }
}
``````

记忆：Deque作为queue使用时可用add, remove，作为stack使用时可用pop, push

思路：迭代法，层序遍历：先把root放进queue里，再创建一个while loop，在queue变空时结束。while loop中创建一个for loop，根据当前queue中的node数量，决定for loop的次数。for loop每次remove一个queue中的元素并且加入它的左右孩子。while loop的次数便是层数，所以每次while loop，depth++；

### 二叉树的最小深度111

迭代法容易。

``````java
// 递归法
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        int result;
        int leftDepth = minDepth(root.left);
        int rightDepth = minDepth(root.right);
        if (leftDepth == 0 && rightDepth == 0){
            result = 1;
        } else if (leftDepth == 0 || rightDepth == 0){
            result = Math.max(leftDepth, rightDepth) + 1;
        } else {
            result = Math.min(leftDepth, rightDepth) + 1;
        }
        return result;
    }
}
``````

思路：并不和上题类似，求左右深度，然后取更小的那个值+1。如果按和上题类似的思路，若root只有右孩子，没有左孩子，那么左右最小值为0，得出最小深度为1。见图

![111.二叉树的最小深度](https://img-blog.csdnimg.cn/20210203155800503.png)

因为最小深度的定义是从root到最近的叶子结点的深度。

所以在if判断中：

1. 当左右孩子都为null时，当前树的最小深度=1 （以node4为例）
2. 当左右孩子有一个为null时，当前树的最小深度等于不为null的那个子树的最小深度（以node1为例）
3. 当左右孩子都不为null时，取左右子树更浅的那个深度+1（以node2为例）

``````java
// 迭代法
class Solution {
    public int minDepth(TreeNode root) {
        if (root == null) {
            return 0;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        int depth = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            depth++;
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.remove();
                if (node.left == null && node.right == null) {
                    return depth;
                }
                if (node.left != null) {
                    deque.add(node.left);
                }
                if (node.right != null) {
                    deque.add(node.right);
                }
            }
        }
        return depth;
    }
}
``````

思路：当检测到一个node的左右孩子都是null时，直接返回当时的深度，即为最小深度。

### 完全二叉树的节点个数222

``````java
// 迭代法
class Solution {
    public int countNodes(TreeNode root) {
        int result = 0;
        if (root == null) {
            return result;
        }
        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        result = 1;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.remove();
                if (node.left != null) {
                    deque.add(node.left);
                    result++;
                }
                if (node.right != null) {
                    deque.add(node.right);
                    result++;
                }
            }
        }
        return result;
    }
}
``````

思路：很简单，层序遍历，每遍历到一个元素，数量+1

``````java
// 递归法
class Solution {
    public int countNodes(TreeNode root) {
        if(root == null) {
            return 0;
        }
        return countNodes(root.left) + countNodes(root.right) + 1;
    }
}
``````

思路：很简单，数左右子树的元素个数，再加上1为当前树的元素个数

### 平衡二叉树110

``````java
class Solution {
    public boolean isBalanced(TreeNode root) {
        return getHeight(root) != -1;
    }
    
    // return -1 if tree is not an AVL tree
    // return the height of the tree if it's an AVL tree
    private int getHeight(TreeNode root) {
        if (root == null) {
            return 0;
        }

        int leftHeight = getHeight(root.left);
        if (leftHeight == -1) {
            return -1;
        }
        int rightHeight = getHeight(root.right);
        if (rightHeight == -1) {
            return -1;
        }

        if (Math.abs(leftHeight - rightHeight) > 1) {
            return -1;
        } else {
            return Math.max(leftHeight, rightHeight) + 1;
        }
    }
}
// 或
class Solution {
    public boolean isBalanced(TreeNode root) {
        if (root == null) return true;
        int leftHeight = getHeight(root.left);
        int rightHeight = getHeight(root.right);
        int diff = Math.abs(leftHeight - rightHeight);
        if (diff > 1) return false;
        boolean left = isBalanced(root.left);
        boolean right = isBalanced(root.right);
        return left && right;
    }

    private int getHeight(TreeNode root) {
        if (root == null) return 0;
        int left = getHeight(root.left);
        int right = getHeight(root.right);
        return Math.max(left, right) + 1;
    }
}
``````

思路：

1. balanced tree中的每个子树也是balanced tree

2. 判断balanced tree的方式是看两个子树的高度差是否小于等于1

构造一个`getHeight()`，用于判断以上条件

1. 若左子树或右子树不为balanced（左子树或右子树的height==-1），return -1
2. 否则，return该树的height（左右子树更高的那个高度+1）

### 二叉树的所有路径257

``````java
// 递归法，前序遍历，中左右
class Solution {
    public List<String> binaryTreePaths(TreeNode root) {
        List<String> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }
        constructPaths(root, "", paths);
        return paths;
    }

    private void constructPaths (TreeNode root, String path, List<String> paths) {
        if (root != null) {
            path += Integer.toString(root.val);  // add current node to path
          
          	// Base Case
            if (root.left == null && root.right == null) {  // if reach a leaf
                paths.add(path);  // add path to paths
              
            } else {  // 不是leaf, path未结束
                path += "->";
                constructPaths(root.left, path, paths);
                constructPaths(root.right, path, paths);
            }
        }
    }
}
``````

思路：

使用recursion function `constructPaths()`

当前node不为leaf时：

1. 将`"${root.val}->"`加入`path`，所以`path`中存放从root到当前node的路径
2. `paths`不变（存放其他已到达leaf的路径）

当前node为leaf时：

1. 将`"${root.val}"`加入`path`，所以`path`中存放从root到当前leaf的路径

2. 将`path`加入`paths`（存放其他已到达leaf的路径以及当前这个到达leaf的路径）

### 左叶子之和404

``````java
// 递归法
class Solution {
    public int sumOfLeftLeaves(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return calculateSum(root, false);
    }

    // return sum of all left leaf for current node
    private int calculateSum(TreeNode root, boolean isLeft) {
        // Base case: this is a leaf
        if (root.left == null && root.right == null) {
            return isLeft? root.val : 0;
        // this is not a leaf
        } else {
            int total = 0;
            if (root.left != null) {
                total += calculateSum(root.left, true);
            }
            if (root.right != null) {
                total += calculateSum(root.right, false);
            }
            return total;
        }
    }
}
``````

思路：

建立一个recursion函数，返回值为该subtree中的左叶子总和。

用一个函数中的boolean参数存储该node是否为左node，然后函数内判断是否为叶子。

注意：

递归法中不要用`root.left != null && root.left.left == null && root.left.right == null`判断是否为左叶子，会弄乱递归逻辑。

``````java
// 迭代法
class Solution { 
	private boolean isLeaf(TreeNode node) {
    return node != null && node.left == null && node.right == null;
  }

  public int sumOfLeftLeaves(TreeNode root) {

    if (root == null) {
      return 0;
    }

    int total = 0;
    Deque<TreeNode> stack = new ArrayDeque<>();
    stack.push(root);

    while (!stack.isEmpty()) {
      TreeNode subRoot = stack.pop();
      // Check if the left node is a leaf node.
      if (isLeaf(subRoot.left)) {
        total += subRoot.left.val;
      }
      // If the right node exists, put it on the stack.
      if (subRoot.right != null) {    
        stack.push(subRoot.right);
      }
      // If the left node exists, put it on the stack.
      if (subRoot.left != null) {
        stack.push(subRoot.left);
      }
    }

    return total;
  }
}
``````

思路：用迭代法的前序遍历，判断每个node的左孩子是否为leaf，如果是，则把值放入total

### 找树左下角的值513

``````java
// 迭代法，层序遍历
class Solution {
    public int findBottomLeftValue(TreeNode root) {
        if (root == null) {
            return 0;
        }

        Deque<TreeNode> deque = new LinkedList<>();
        deque.add(root);
        int bottomLeft = 0;
        while (!deque.isEmpty()) {
            int size = deque.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = deque.remove();
                if (i == 0) {
                    bottomLeft = node.val;
                }
                if (node.left != null) {
                    deque.add(node.left);
                }
                if (node.right != null) {
                    deque.add(node.right);
                }
            }
        }
        return bottomLeft;
    }
}
``````

思路：用层序遍历，每次遍历到每层最左边的一个节点时，更新变量的值，遍历完后该变量就是最下面一层最左边节点的值

### 路径总和112

``````java
class Solution {
    // 返回该subtree中是否存在节点总和为targetSum的path
    public boolean hasPathSum(TreeNode root, int targetSum) {
      
        // Base Case
        if (root == null) {
            return false;  // root为null时，说明所有leaf都找过了却找不到，因为只要找到了，就已经return true了
          
        } else {
            if (root.left == null && root.right == null) {
                return targetSum == root.val;  // 走到leaf节点时，targetSum等于该节点的值，代表这条path总和为起初的targetSum
            }
          
            // 每往下走一节，减去该节点的值
            // 左右子树只要有一个找到了，返回true，都没找到返回false 
            return hasPathSum(root.left, targetSum - root.val) || hasPathSum(root.right, targetSum - root.val);  
        }
    }
}
``````

思路：

考虑递归法，`hasPathSum()`函数返回值为一个boolean，判断该子树是否存在节点总和为targetSum的path。

如果targetSum在递归中不变，那么递归没有意义，因为只有当位于根节点时，我们才需要判断是否存在一条path使其总和等于该定值。

所以递归到其他节点时应该改变targetSum的值，使其等于原本的targetSum减去该节点所有parent nodes的值。

### 路径总和ii113

``````java
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
    public List<List<Integer>> pathSum(TreeNode root, int targetSum) {
        List<List<Integer>> paths = new ArrayList<>();
        if (root == null) {
            return paths;
        }
        List<Integer> path = new ArrayList<>();
        recurseTree(root, targetSum, path, paths);
        return paths;
    }

    private void recurseTree(TreeNode root, int remainingSum, List<Integer> path, List<List<Integer>> paths) {
        if (root == null) {
            return;
        }

        path.add(root.val);
        remainingSum -= root.val;

        if (root.left == null && root.right == null) {
            if (remainingSum == 0) {
                paths.add(new ArrayList<>(path));
            }
        }
        
      	// 不用判断root.left, root.right是否为null，因为最上面判断过了
        recurseTree(root.left, remainingSum, path, paths);
        recurseTree(root.right, remainingSum, path, paths);
        // 回溯：当该subtree全部被traverse完后，remove path中最后一个值，which is当前的node value
        path.remove(path.size() - 1);
    }
}
``````

思路：

与上题类似，不过多了一步回溯：

回溯例子：

![113.路径总和ii1.png](https://img-blog.csdnimg.cn/20210203160854654.png)

总和为22时加入paths：

[5, 4, 11, 7]	7的subtree遍历完了，删除7

[5, 4, 11]

[5, 4, 11, 2]	2的subtree遍历完了，删除2，paths加入[5, 4, 11, 2]

[5, 4, 11]		11的subtree遍历完了，删除11

[5, 4]			   4的subtree遍历完了，删除4

[5]

[5, 8, 13]		13的subtree遍历完了，删除13

[5, 8]

[5, 8, 4, 5]	   5的subtree遍历完了，删除5，paths加入[5, 8, 4, 5]

[5, 8, 4]

[5, 8, 4, 1]	   1的subtree遍历完了，删除1

[5, 8, 4]		   4的subtree遍历完了，删除4

[5, 8]			   8的subtree遍历完了，删除8

[5]				   5的subtree遍历完了，删除5

[]

### 从中序与后序遍历序列构造二叉树106

``````java
class Solution {
    HashMap<Integer, Integer> map;  // 写在这里是global变量，值可以被函数更改
    public TreeNode buildTree(int[] inorder, int[] postorder) {
        // 制作一个map，用于存放key = inorder value, value = inorder index
        // 方便查找postorder中的value在inorder中的位置
        map = new HashMap<>();
        for (int i = 0; i < inorder.length; i ++) {
            map.put(inorder[i], i);
        }
        return buildSubtree(inorder, 0, inorder.length - 1, postorder, 0, postorder.length - 1);

    }

    public TreeNode buildSubtree(int[] inorder, int inorderStart, int inorderEnd, int[] postorder, int postorderStart, int postorderEnd) {
        if (inorderStart > inorderEnd || postorderStart > postorderEnd) {
            return null;
        }
        int lastOneInPostorder = postorder[postorderEnd];
        TreeNode root = new TreeNode(lastOneInPostorder);
        int rootIndexInInorder = map.get(lastOneInPostorder);
        int lenOfLeftTree = rootIndexInInorder - inorderStart;

        root.left = buildSubtree(inorder, inorderStart, rootIndexInInorder - 1, postorder, postorderStart, postorderStart + lenOfLeftTree - 1);
        root.right = buildSubtree(inorder,  rootIndexInInorder + 1, inorderEnd, postorder, postorderStart + lenOfLeftTree, postorderEnd - 1);

        return root;
    }
}
``````

思路：

首先画一个二叉树，观察中序序列和后序序列的顺序和树中节点位置的规律：

以 后序数组的最后一个元素为切割点，先切中序数组，根据中序数组，反过来再切后序数组。一层一层切下去，每次后序数组最后一个元素就是节点元素。

![106.从中序与后序遍历序列构造二叉树](https://img-blog.csdnimg.cn/20210203154249860.png)

清楚这个规律之后，构造一个recursion函数：

1. 当前的root = 当前postorder最后一个

2. 寻找inorder中root.left与root.right的部分：

   根据当前root在inorder中的位置（该位置用HashMap存储），左边的部分就是root.left，右边的部分就是root.right

3. 寻找postorder中root.left与root.right的部分：

   根据inorder中root.left的长度在postorder从头数起就是root.left的部分，剩余直到最后一个元素之前就是root.right的部分

4. 当前的root.left = recursion

5. 当前的root.right = recursion

### 最大二叉树654

``````java
class Solution {
    public TreeNode constructMaximumBinaryTree(int[] nums) {
        return construct(nums, 0, nums.length - 1);
    }
    
    private TreeNode construct(int[] nums, int left, int right) {
        if (left > right) {
            return null;
        }
        int max = -1;
        int maxIndex = -1;
        for (int i = left; i <= right; i++) {
            if (nums[i] > max) {
                max = nums[i];
                maxIndex = i;
            }
        }
        TreeNode root = new TreeNode(max);
        root.left = construct(nums, left, maxIndex - 1);
        root.right = construct(nums, maxIndex + 1, right);
        return root;
    }
}
``````

思路：和上题类似

注意：

用数组构造二叉树的题目，每次分隔尽量不要定义新的数组，而是通过下标索引直接在原数组上操作，这样可以节约时间和空间上的开销

### 合并二叉树617

``````java
class Solution {
    public TreeNode mergeTrees(TreeNode root1, TreeNode root2) {
        if (root1 == null && root2 == null) {
            return null;
        }
        if (root1 == null) {
            return root2;
        }
        if (root2 == null) {
            return root1;
        }
        TreeNode node = new TreeNode(root1.val + root2.val);
        node.left = mergeTrees(root1.left, root2.left);
        node.right = mergeTrees(root1.right, root2.right);
        return node;
    }
}
``````

思路：同时前序遍历两棵树构造新树

### 199. Binary Tree Right Side View

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/02/14/tree.jpg)

```
Input: root = [1,2,3,null,5,null,4]
Output: [1,3,4]
```

**Example 2:**

```
Input: root = [1,null,3]
Output: [1,3]
```

**Example 3:**

```
Input: root = []
Output: []
```

**思路：**
从右到左的level-order traversal，每次把第一个出queue的元素放进result

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
    public List<Integer> rightSideView(TreeNode root) {
        Queue<TreeNode> queue = new LinkedList<>();
        List<Integer> result = new ArrayList<>();
        if (root == null) return result;
        queue.add(root);
        while (!queue.isEmpty()) {
            int len = queue.size();
            boolean isFirst = true;
            while (len > 0) {
                TreeNode curr = queue.remove();
                if (isFirst) {
                    result.add(curr.val);
                    isFirst = false;
                };
                if (curr.right != null) {
                    queue.add(curr.right);
                }
                if (curr.left != null) {
                    queue.add(curr.left);
                }
                len--;
            }
        }
        return result;
    }
}
```

### [437. Path Sum III](https://leetcode.com/problems/path-sum-iii/)

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/04/09/pathsum3-1-tree.jpg)

```
Input: root = [10,5,-3,3,2,null,11,3,-2,null,1], targetSum = 8
Output: 3
Explanation: The paths that sum to 8 are shown.
```

**Example 2:**

```
Input: root = [5,4,8,11,null,13,4,7,2,null,null,5,1], targetSum = 22
Output: 3
```

**思路**：

假设从根结点到结点 node1的路径上的结点值总和为 sum1 ，在同一条路径上存在不同于结点 node1的结点 node2 ，从根结点到结点 node2的路径上的结点值总和为 sum2 ，则该路径上从结点 node2的子结点到结点 node1的子路径（注意子路径包含 node1 ，不包含结点 node2）上的结点值总和为 sum1 − sum2 。如果 sum1 − sum2 = targetSum，则找到一条结点值总和等于 targetSum 的路径。

基于上述分析，可以记录从根结点出发的路径中的每个结点值总和的出现次数，达到优化时间复杂度的目的。以下将从根结点出发的路径中的每个结点值总和称为「根路径和」。

从根结点开始深度优先搜索，使用哈希表记录每个根路径和的出现次数。对于每一条从根结点出发的路径，按照层数递增的顺序依次访问每个结点，访问每个结点时更新当前结点的根路径和，并在哈希表中将当前结点的根路径和的出现次数加 1。对于访问到的每个结点，如果当前结点的根路径和为 sum，则从哈希表中得到根路径和 sum−targetSum 的出现次数，该出现次数即为以当前结点为结束结点的结点值总和等于 targetSum 的路径数目。在访问当前结点之后，继续访问当前结点的非空结点。

实现方面有两点需要注意。

- 由于结点值总和等于 targetSum 的路径可能从根结点出发，因此需要预处理空路径，空路径的结点值总和等于 0，在搜索之前将根路径和 0 出现 1 次存入哈希表。

- 深度优先搜索的过程中，当访问完一个子树并退出该子树时，需要撤销对根路径和以及哈希表的更新，避免当前子树的根路径和信息对其他子树的结果造成影响。

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

    HashMap<Long, Integer> map;    
    int count;
    
    public int pathSum(TreeNode root, int targetSum) {
        map = new HashMap<>();
        count = 0;
        map.put((long)0, 1);
        traversal(root, 0, targetSum);
        return count;
    }

    private void traversal(TreeNode root, long sum, int targetSum) {
        if (root == null) return;

        sum += root.val;

        if (map.containsKey(sum - targetSum)) {
            count += map.get(sum - targetSum);
        }

        map.put(sum, map.getOrDefault(sum, 0) + 1);
        traversal(root.left, sum, targetSum);
        traversal(root.right, sum, targetSum);

        map.put(sum, map.get(sum) - 1);
    }
}
```

### [863. All Nodes Distance K in Binary Tree](https://leetcode.com/problems/all-nodes-distance-k-in-binary-tree/)

**Example 1:**

![img](https://s3-lc-upload.s3.amazonaws.com/uploads/2018/06/28/sketch0.png)

```
Input: root = [3,5,1,6,2,0,8,null,null,7,4], target = 5, k = 2
Output: [7,4,1]
Explanation: The nodes that are a distance 2 from the target node (with value 5) have values 7, 4, and 1.
```

**思路**：

1. **构建图**：

​	使用 DFS 来构建图。buildGraph 方法会将二叉树转换为无向图。

​	在图中，每个节点都有一个列表，包含它的所有邻居节点。

2. **广度优先搜索（BFS）**：

​	使用 BFS 从目标节点开始搜索，记录每一层的节点。当搜索到第 K 层时，将这一层的所有节点值返回。

​	Queue 用于进行层次遍历，Set 用于记录访问过的节点以防止重复访问。

3. **边界条件**：

​	如果树为空或者 K 为 0 时，会有对应的处理。

```java
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
class Solution {
    Map<TreeNode, List<TreeNode>> graph = new HashMap<>();

    public List<Integer> distanceK(TreeNode root, TreeNode target, int k) {
        buildGraph(null, root);

        Queue<TreeNode> queue = new LinkedList<>();
        HashSet<TreeNode> visited = new HashSet<>();
        queue.add(target);
        visited.add(target);
        
        int distance = 0;

        while (!queue.isEmpty()) {

            if (distance == k) {
                List<Integer> result = new ArrayList<>();
                for (TreeNode node : queue) {
                    result.add(node.val);
                }
                return result;
            }

            int size = queue.size();
            for (int i = 0; i < size; i++) {
                TreeNode node = queue.poll();
                for (TreeNode neighbor : graph.get(node)) {
                    if (!visited.contains(neighbor)) {
                        visited.add(neighbor);
                        queue.add(neighbor);
                    }
                }
            }
            distance++;
        }
        
        return new ArrayList<>();
    }

    private void buildGraph(TreeNode parent, TreeNode child) {
        if (child == null) return;

        if (!graph.containsKey(child)) {
            graph.put(child, new ArrayList<>());
            if (parent != null) {
                graph.get(child).add(parent);
                graph.get(parent).add(child);
            }
            buildGraph(child, child.left);
            buildGraph(child, child.right);
        }
    }
}
```

