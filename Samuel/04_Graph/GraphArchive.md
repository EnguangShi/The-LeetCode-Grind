# Graph

### 733. Flood Fill*

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/06/01/flood1-grid.jpg)

```
Input: image = [[1,1,1],[1,1,0],[1,0,1]], sr = 1, sc = 1, color = 2
Output: [[2,2,2],[2,2,0],[2,0,1]]
Explanation: From the center of the image with position (sr, sc) = (1, 1) (i.e., the red pixel), all pixels connected by a path of the same color as the starting pixel (i.e., the blue pixels) are colored with the new color.
Note the bottom corner is not colored 2, because it is not 4-directionally connected to the starting pixel.
```

**思路：**

DFS Recursion

```java
class Solution {
    public int[][] floodFill(int[][] image, int sr, int sc, int color) {
        if (image[sr][sc] == color) return image;
        int cur = image[sr][sc];
        fill(image, sr, sc, color, cur);
        return image;
    }

    private void fill(int[][] image, int sr, int sc, int color, int cur) {
        if (sr < 0 || sc < 0 || sr >= image.length || sc >= image[0].length) return;
        if (image[sr][sc] != cur) return;
        image[sr][sc] = color;
        fill(image, sr + 1, sc, color, cur);
        fill(image, sr - 1, sc, color, cur);
        fill(image, sr, sc + 1, color, cur);
        fill(image, sr, sc - 1, color, cur);
    }
}
```

### 542. 01 Matrix***

Given an `m x n` binary matrix `mat`, return *the distance of the nearest* `0` *for each cell*.

The distance between two adjacent cells is `1`.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2021/04/24/01-1-grid.jpg)

```
Input: mat = [[0,0,0],[0,1,0],[0,0,0]]
Output: [[0,0,0],[0,1,0],[0,0,0]]
```

**Example 2:**

![img](https://assets.leetcode.com/uploads/2021/04/24/01-2-grid.jpg)

```
Input: mat = [[0,0,0],[0,1,0],[1,1,1]]
Output: [[0,0,0],[0,1,0],[1,2,1]]
```

**思路：**

 把原本matrix中每个1换成Integer.MAX_VALUE，先假设他们每个1到最近的0都是MAX距离

先把所有的0的位置放入一个queue，然后进行Multi-source BFS，相当于从所有的0开始一层层往外看。

从queue remove一个格子，假设为格子A。因为格子A存储的是A到最近的0的距离，那么他上下左右的邻居B们经过A到0的距离应该是A+1，如果格子B的值>A+1，就把它更新成A+1，然后把格子B的值加入queue。

```java
class Solution {
    public int[][] updateMatrix(int[][] mat) {
        Queue<int[]> queue = new LinkedList<>();
        int rows = mat.length;
        int cols = mat[0].length;
        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] == 0) {
                    queue.add(new int[]{i, j});
                } else {
                    mat[i][j] = Integer.MAX_VALUE;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] cur = queue.remove();
            int row = cur[0];
            int col = cur[1];
            if (row + 1 < rows) {
                if (mat[row][col] + 1 < mat[row + 1][col]) {
                    mat[row + 1][col] = mat[row][col] + 1;
                    queue.add(new int[]{row + 1, col});
                }
            }
            if (row - 1 >= 0) {
                if (mat[row][col] + 1 < mat[row - 1][col]) {
                    mat[row - 1][col] = mat[row][col] + 1;
                    queue.add(new int[]{row - 1, col});
                } 
            }
            if (col + 1 < cols) {
                if (mat[row][col] + 1 < mat[row][col + 1]) {
                    mat[row][col + 1] = mat[row][col] + 1;
                    queue.add(new int[]{row, col + 1});
                }
            }
            if (col - 1 >= 0) {
                if (mat[row][col] + 1 < mat[row][col - 1]) {
                    mat[row][col - 1] = mat[row][col] + 1;
                    queue.add(new int[]{row, col - 1});
                }
            }
        }
        return mat;
    }
}
```

### 133. Clone Graph**

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/11/04/133_clone_graph_question.png)

**思路：**

DFS recursion遍历每个node，把它和克隆作为key value放入hashMap，并且对没有拜访过的邻居继续使用dfs，每个领居都找到它的克隆，放入该克隆领居的领居列表。

```java
/*
// Definition for a Node.
class Node {
    public int val;
    public List<Node> neighbors;
    public Node() {
        val = 0;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val) {
        val = _val;
        neighbors = new ArrayList<Node>();
    }
    public Node(int _val, ArrayList<Node> _neighbors) {
        val = _val;
        neighbors = _neighbors;
    }
}
*/

class Solution {
    HashMap<Node, Node> cloneMap = new HashMap<>();

    public Node cloneGraph(Node node) {
        if (node == null) {
            return null;
        }
        dfs(node);
        return cloneMap.get(node);
    }

    private void dfs(Node original) {
        Node cloned = new Node(original.val);
        cloneMap.put(original, cloned);
        List<Node> originalNeighbors = original.neighbors;
        List<Node> clonedNeighbors = cloned.neighbors;
        
        for (Node originalNeighbor : originalNeighbors) {
            if (!cloneMap.containsKey(originalNeighbor)) {
                dfs(originalNeighbor);
            }
            clonedNeighbors.add(cloneMap.get(originalNeighbor));
        }
    }
}
```

### 207. Course Schedule**

There are a total of `numCourses` courses you have to take, labeled from `0` to `numCourses - 1`. You are given an array `prerequisites` where `prerequisites[i] = [ai, bi]` indicates that you **must** take course `bi` first if you want to take course `ai`.

- For example, the pair `[0, 1]`, indicates that to take course `0` you have to first take course `1`.

Return `true` if you can finish all courses. Otherwise, return `false`.

**Example 1:**

```
Input: numCourses = 2, prerequisites = [[1,0]]
Output: true
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0. So it is possible.
```

**Example 2:**

```
Input: numCourses = 2, prerequisites = [[1,0],[0,1]]
Output: false
Explanation: There are a total of 2 courses to take. 
To take course 1 you should have finished course 0, and to take course 0 you should also have finished course 1. So it is impossible.
```

**思路：**

DAGs: Directed Acyclic Graph 
本题可约化为： 课程安排图是否是 有向无环图(DAG)。即课程间规定了前置条件，但不能构成任何环路。

通过Topological Sort Algorithm来判断是否是DAG：

1. Find each vertex’s in-degree (in-degree就是有多少个其他节点指向该节点)
2. Initialize a queue to contain all in-degree zero vertices 
3. While there are vertices in the queue
   3.1 Dequeue a vertex v (with in-degree zero) and output it 
   3.2 Reduce the in-degree of all vertices v has an edge to 
   3.3 Enqueue any of these that now have in-degree zero

若整个课程安排图是有向无环图（即可以安排），则所有节点一定都入队并出队过，即完成Topological Sort。换个角度说，若课程安排图中存在环，一定有节点的入度始终不为 000。
因此，Topological Sort中queue的poll次数等于课程个数，返回 numCourses == 0 判断课程是否可以成功安排。

```java
class Solution {
    public boolean canFinish(int numCourses, int[][] prerequisites) {
        // 记录每门课有多少prereqs: [1, 0]: course 0 has 1 prereq, course 1 has 0 prereq
        int[] indegrees = new int[numCourses];
        // 记录每门课的prereq: [[1,2], [0,2], [0,1]]: course 1,2是0的prereq, ...
        List<Integer> [] adjacency = new List[numCourses];
        // queue用来进行Topological Sort
        Queue<Integer> queue = new LinkedList<>();

        // 初始化adjacency表
        for (int i = 0; i < numCourses; i++) {
            adjacency[i] = new ArrayList<>();
        }

        // 记录每门课的prereq数量
        // 以及每门课指向的课有哪些（上完这门能上的课有哪些）
        for (int[] p : prerequisites) {
            indegrees[p[0]]++;
            adjacency[p[1]].add(p[0]);
        }

        // 把indegree == 0的课先放入queue
        for (int i = 0; i < numCourses; i++) {
            if (indegrees[i] == 0) queue.add(i);
        }

        // BFS Topological Sort
        // 弹出一门课的同时，让把它作为prereq的课的indegree--, 意思该课需要的prereq少了一个 
        // 每上完一门课让numCourses--, 代表还要上的课少了一门
        while (!queue.isEmpty()) {
            int course = queue.poll();
            numCourses--;
            for (int prereq : adjacency[course]) {
                indegrees[prereq]--;
                if (indegrees[prereq] == 0) queue.add(prereq);
            }
        }
        // 如果从不需要prereq的课开始，一门门上，numCourses不是0代表最后无法把所有课上完，是0代表上完了
        return numCourses == 0;
    }
}
```

### 994. Rotting Oranges**

You are given an `m x n` `grid` where each cell can have one of three values:

- `0` representing an empty cell,
- `1` representing a fresh orange, or
- `2` representing a rotten orange.

Every minute, any fresh orange that is **4-directionally adjacent** to a rotten orange becomes rotten.

Return *the minimum number of minutes that must elapse until no cell has a fresh orange*. If *this is impossible, return* `-1`.

**Example 1:**

![img](https://assets.leetcode.com/uploads/2019/02/16/oranges.png)

```
Input: grid = [[2,1,1],[1,1,0],[0,1,1]]
Output: 4
```

**Example 2:**

```
Input: grid = [[2,1,1],[0,1,1],[1,0,1]]
Output: -1
Explanation: The orange in the bottom left corner (row 2, column 0) is never rotten, because rotting only happens 4-directionally.
```

**思路：**

BFS，记录加入到queue中的橘子的轮数，就是minutes数

```java
class Solution {
    public int orangesRotting(int[][] grid) {
        int m = grid.length;
        int n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int rottenOrangeNow = 0;
        int rottenOrangeNext = 0;
        int minute = 0;
        int orangeCount = 0;
        int rottenCount = 0;

        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (grid[i][j] != 0) {
                    orangeCount++;
                }
                if (grid[i][j] == 2) {
                    queue.add(new int[]{i, j});
                    rottenOrangeNow++;
                }
            }
        }

        while (!queue.isEmpty()) {
            int[] curr = queue.poll();
            rottenOrangeNow--;
            rottenCount++;
            int x = curr[0];
            int y = curr[1];
            if (x - 1 >= 0 && grid[x-1][y] == 1) {
                queue.add(new int[]{x - 1, y});
                grid[x-1][y] = 2;
                rottenOrangeNext++;
            }
            if (x + 1 < m && grid[x+1][y] == 1) {
                queue.add(new int[]{x + 1, y});
                grid[x+1][y] = 2;
                rottenOrangeNext++;
            }
            if (y - 1 >= 0 && grid[x][y-1] == 1) {
                queue.add(new int[]{x, y - 1});
                grid[x][y-1] = 2;
                rottenOrangeNext++;
            }
            if (y + 1 < n && grid[x][y+1] == 1) {
                queue.add(new int[]{x, y + 1});
                grid[x][y+1] = 2;
                rottenOrangeNext++;
            }

            if (rottenOrangeNow == 0) {
                if (rottenOrangeNext == 0) break;
                minute++;
                rottenOrangeNow = rottenOrangeNext;
                rottenOrangeNext = 0;
            }
        }

        if (rottenCount != orangeCount) return -1;
        return minute;
    }
}
```

### 