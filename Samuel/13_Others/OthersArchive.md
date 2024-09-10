# Others

## Monotonic Stack

**通常是一维数组，要寻找任一个元素的右边或者左边第一个比自己大或者小的元素的位置，此时我们就要想到可以用单调栈了**。

### 每日温度739

```
class Solution {
    public int[] dailyTemperatures(int[] temperatures) {
        Stack<Integer> stack = new Stack<>();
        int[] res = new int[temperatures.length];

        stack.push(0);
        for(int i = 1; i < temperatures.length; i++) {
            while (!stack.isEmpty() && temperatures[i] > temperatures[stack.peek()]) {
                res[stack.peek()] = i - stack.peek();
                stack.pop();
            }
            stack.push(i);
        }
        return res;
    }
}
```

思路：

越往栈头，存越小的数字。

每遍历到一个数字，和栈头数字对比：

- 如果比栈头数字小，说明它不是第一个比它大的值，把这个新的数字放到栈头。
- 如果比栈头数字大，说明这是第一个比它大的值，把这个数字的位置记录下来，并且推出栈头数字。继续对比栈头，直到栈空或者当前数字比栈头数字小。

由此可知栈里的数字都还没找到右边第一个大于它的数字。每次遍历遇到一个当前数字就依次和栈中数字对比，由于栈是往栈头单调递减，越往后对比越大，只要遇到一个栈头数字大于当前数字，则说明后面的都比当前数字大，就没必要继续对比下去。

### 下一个更大元素一496

```
class Solution {
    public int[] nextGreaterElement(int[] nums1, int[] nums2) {
        HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();
        for (int i = 0; i < nums1.length; i++) {
            hashMap.put(nums1[i], i);
            nums1[i] = -1;
        }
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        for (int i = 1; i < nums2.length; i++) {
            if (nums2[i] > nums2[stack.peek()]) {
                while (!stack.isEmpty() && nums2[i] > nums2[stack.peek()]) {
                    if (hashMap.containsKey(nums2[stack.peek()])) {
                        nums1[hashMap.get(nums2[stack.peek()])] = nums2[i];
                    }
                    stack.pop();
                }
            }
            stack.push(i);
        }
        return nums1;
    }
}
```

思路：

和上题一样，遍历nums2的同时，记录每个值A的右边第一个比它大的值B是多少，然后把值B存入nums1中值A所在的位置。nums1中没被存入的值A说明没有比它大的值。

如果要在nums1中找到nums2中的值A的位置：

用一个hashMap，通过遍历nums1，key存nums1中每个值，value存它在nums1中的index，遍历过一个值后把它变成-1，-1没改成值B说明没有更大的值。

注意：由于nums1并不包含nums2中的所有值，在遍历nums2的同时需要确认该值是否存在在hashMap的key中，如果存在才去改nums1中的值A。

### 下一个更大元素二503

```
class Solution {
    public int[] nextGreaterElements(int[] nums) {
        int[] res = new int[nums.length];
        Arrays.fill(res, -1);  // 记忆：把res的全部元素变成-1
        Deque<Integer> stack = new LinkedList<>();
        stack.push(0);
        for(int i = 1; i < nums.length * 2; i++) {
            int index = i % nums.length;
            if (nums[index] > nums[stack.peek()]) {
                while(!stack.isEmpty() && nums[index] > nums[stack.peek()]) {
                    res[stack.peek()] = nums[index];
                    stack.pop();
                }
            }
            stack.push(index);
        }
        return res;
    }
}
```

思路：

遍历两遍数组，保证每个元素会被遍历到两次，这样对于某个元素来说，如果在循环的数组中有下一个比它更大的值，一定会被遍历到。

## Matrix

### 54. Spiral Matrix**

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral1.jpg)

```
Input: matrix = [[1,2,3],[4,5,6],[7,8,9]]
Output: [1,2,3,6,9,8,7,4,5]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/11/13/spiral.jpg)

```
Input: matrix = [[1,2,3,4],[5,6,7,8],[9,10,11,12]]
Output: [1,2,3,4,8,12,11,10,9,5,6,7]
```

**思路：**

用四个变量分别决定左右和上下遍历至的位置

比如对于example 1来说：

left = 0, top = 0, right = 2, down = 2

left0~right2: 123, top++

top1~bottom2: 69, right--

right1~left0: 87, bottom--

bottom1~top1: 4: left++

left1~right1: 5

为什么需要在往左和往上之前加`if (top <= bottom)` 和 `if (left <= right)`:

因为如果目前剩下未遍历的只剩1行，或者1列，往右遍历或者往下遍历完就结束了，不需要再一轮往左和往上。

```java
class Solution {
    public List<Integer> spiralOrder(int[][] matrix) {
        List<Integer> result = new ArrayList<>();
        if (matrix == null || matrix.length == 0) {
            return result;
        }
        
        int rows = matrix.length, cols = matrix[0].length;
        int left = 0, right = cols-1, top = 0, bottom = rows-1;
        
        while (left <= right && top <= bottom) {
            for (int i = left; i <= right; i++) {
                result.add(matrix[top][i]);
            }
            top++;
            
            for (int i = top; i <= bottom; i++) {
                result.add(matrix[i][right]);
            }
            right--;
            
            if (top <= bottom) {
                for (int i = right; i >= left; i--) {
                    result.add(matrix[bottom][i]);
                }
                bottom--;
            }
            
            if (left <= right) {
                for (int i = bottom; i >= top; i--) {
                    result.add(matrix[i][left]);
                }
                left++;
            }
        }
        
        return result;
    }
}
```

### 36. Valid Sudoku*

```
Input: board = 
[["5","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: true
```

**Example 2:**

```
Input: board = 
[["8","3",".",".","7",".",".",".","."]
,["6",".",".","1","9","5",".",".","."]
,[".","9","8",".",".",".",".","6","."]
,["8",".",".",".","6",".",".",".","3"]
,["4",".",".","8",".","3",".",".","1"]
,["7",".",".",".","2",".",".",".","6"]
,[".","6",".",".",".",".","2","8","."]
,[".",".",".","4","1","9",".",".","5"]
,[".",".",".",".","8",".",".","7","9"]]
Output: false
Explanation: Same as Example 1, except with the 5 in the top left corner being modified to 8. Since there are two 8's in the top left 3x3 sub-box, it is invalid.
```

**思路：**

把 "5 in row 1", "5 in col 1", "5 in box 1"加入hashset, 如果遇到重复的就return false

```java
class Solution {
    public boolean isValidSudoku(char[][] board) {
        HashSet<String> set = new HashSet<>();
        if (board.length == 0) {
            return false;
        }

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '.') {
                    continue;
                } else {
                    boolean addToRow = set.add(board[i][j] + " in row " + i);
                    boolean addToCol = set.add(board[i][j] + " in col " + j);
                    int box = j / 3 + 1 + i / 3 * 3;
                    boolean addToBox = set.add(board[i][j] + " in box " + box);
                    if (addToRow && addToCol && addToBox) {
                        continue;
                    } else {
                        return false;
                    }   
                }
            }
        }
        return true;
    }
}
```

### 73. Set Matrix Zeroes

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/08/17/mat1.jpg)

```
Input: matrix = [[1,1,1],[1,0,1],[1,1,1]]
Output: [[1,0,1],[0,0,0],[1,0,1]]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2020/08/17/mat2.jpg)

```
Input: matrix = [[0,1,2,0],[3,4,5,2],[1,3,1,5]]
Output: [[0,0,0,0],[0,4,5,0],[0,3,1,0]]
```

**思路：**

用两个hashset一个存行数，一个存列数

第一遍遍历存，第二遍遍历改

```java
class Solution {
    public void setZeroes(int[][] matrix) {
        HashSet<Integer> row = new HashSet<>();
        HashSet<Integer> col = new HashSet<>();

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    row.add(i);
                    col.add(j);
                }
            }
        }

        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[0].length; j++) {
                if (row.contains(i) || col.contains(j)) {
                    matrix[i][j] = 0;
                }
            }
        }
    }
}
```

### 723. Candy Crush

**思路：**

https://www.bilibili.com/video/BV1Yj411V7Eu/?spm_id_from=333.337.search-card.all.click&vd_source=d1e1b867da2d51076aede0facf6f6e45

```java
class Solution {
    public int[][] candyCrush(int[][] board) {
        while (true) {
            boolean collapse = false;
            
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[0].length; j++) {
                    int curr = board[i][j];  // current node
                    if (i <= board.length - 3) {  // do not check nodes within the last 3 rows 
                        if (curr != 0 && Math.abs(curr) == Math.abs(board[i+1][j]) && Math.abs(curr) == Math.abs(board[i+2][j])) {
                            board[i][j] = -Math.abs(curr);
                            board[i+1][j] = -Math.abs(curr);
                            board[i+2][j] = -Math.abs(curr);
                            collapse = true;
                        }
                    }
                    if (j <= board[0].length - 3) {  // do not check nodes within the last 3 columns
                        if (curr != 0 && Math.abs(curr) == Math.abs(board[i][j+1]) && Math.abs(curr) == Math.abs(board[i][j+2])) {
                            board[i][j] = -Math.abs(curr);
                            board[i][j+1] = -Math.abs(curr);
                            board[i][j+2] = -Math.abs(curr);
                            collapse = true;
                        }
                    }
                }
            }
            
            if (collapse) {
                for (int j = 0; j < board[0].length; j++) {
                    int up = board.length - 1;
                    int down = board.length - 1;
                    boolean found = false;
                    for (int i = board.length - 1; i >= 0; i--) {
                        int curr = board[i][j];
                        if (curr >= 0) {
                            board[down][j] = curr;
                            down--;
                        }
                    }
                    for (int i = down; i >= 0; i--) {
                        board[i][j] = 0;
                    }
                }
                for (int i = 0; i < board.length; i++) {
                    for (int j = 0; j < board[0].length; j++) {
                        System.out.println(board[i][j]);
                    }
                }
            } else {
                return board;
            }
        }
    }
}
```

