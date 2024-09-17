# Array

### 二分查找704

``````java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;
        while (left <= right) {  // when [only1element], left == right
            int mid = (left + right) / 2;
            if (nums[mid] == target)
                return mid;
            else if (nums[mid] < target)
                left = mid + 1;
            else if (nums[mid] > target)
                right = mid - 1;
        }
        return -1;
    }
}
``````

Note:

O(logn)

考虑：

- 分到最后只有一个元素的情况：还应在循环内，所以while(left<=right)
- mid的值 = (left + right)/2
  - 当array size为奇数时：mid为中间值
  - 偶数时：mid为中间偏左值
  - 一个元素时：mid = 该元素的index

### 移除元素27

``````java
class Solution {
    public int removeElement(int[] nums, int val) {
        int slow = 0;
        for (int fast = 0; fast < nums.length; fast++) {
            if (nums[fast] != val) {
                nums[slow] = nums[fast];
                slow++;
            }
        }
        return slow;  // slow only increase when it doesn't find target value to delete, so slow index = count
    }
}
``````

![27.移除元素-双指针法](https://tva1.sinaimg.cn/large/008eGmZEly1gntrds6r59g30du09mnpd.gif)

Note:

O(n)

需要来回对数组进行操作：使用快慢双指针节省runtime

### 有序数组的平方977

``````java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length-1;
        int index = nums.length-1;
        int[] result = new int[nums.length];
        while(left <= right) {
            if (nums[left] * nums[left] >= nums[right] * nums[right]) {
                // means after assigning a value, index--
                result[index--] = nums[left] * nums[left];
                left++;
            } else {
                result[index--] = nums[right] * nums[right];
                right--;
            }
        }
        return result;
    }
}
``````

![img](https://code-thinking.cdn.bcebos.com/gifs/977.%E6%9C%89%E5%BA%8F%E6%95%B0%E7%BB%84%E7%9A%84%E5%B9%B3%E6%96%B9.gif)

Note:

O(n)

需要两端来回比大小：用阶梯双指针

### 长度最小的子数组209

``````java
class Solution {
    public int minSubArrayLen(int target, int[] nums) {
        int left = 0;
        int right = 0;
        int sum = 0;
        int result = Integer.MAX_VALUE;
        while(right < nums.length) {
            sum = sum + nums[right];
            while (sum >= target) {
                // right - left + 1 = length of [left ... right]
                result = result < right - left + 1 ? result : right - left + 1;  
                sum = sum - nums[left];
                left++;
            }
            right++;
        }
        // if result == Integer.MAX_VALUE, then no result found, return 0
        return result == Integer.MAX_VALUE ? 0 : result;
    }
}
``````

s=7， 数组是 [2，3，1，2，4，3]：

![209.长度最小的子数组](https://code-thinking.cdn.bcebos.com/gifs/209.%E9%95%BF%E5%BA%A6%E6%9C%80%E5%B0%8F%E7%9A%84%E5%AD%90%E6%95%B0%E7%BB%84.gif)

Note:

O(n)

动态寻找满足某条件的数组的一部分：滑动窗口

记忆：滑动窗口如何实现：两个loop嵌套，外层loop控制满足a条件下，窗口右侧不断拓展，内层loop控制满足b条件下，窗口左侧不断收缩

### 螺旋矩阵59

``````java
class Solution {
    public int[][] generateMatrix(int n) {
        int[][] res = new int[n][n];
        int loop = n / 2;  // 循环次数
        int start = 0;  // 每次循环的开始点(start, start)
        int count = 1;  // 填入的数字
        int offset = 1;  // 每次循环中每一条边最后收缩的位数
        int i,j;
      	// 转一圈
        while (loop > 0) {
          	// 上
            for (j = start; j < n - offset; j++) {
                res[start][j] = count;
                count ++;
            }
          	// 右
            for (i = start; i < n - offset; i++) {
                res[i][j] = count;
                count ++;
            }
          	// 下
            for (; j > start; j--) {
                res[i][j] = count;
                count ++;
            }
          	// 左
            for(; i > start; i--) {
                res[i][j] = count;
                count ++;
            }
          	// 起始位置往右下移
            start ++;
          	// 收缩的位数多一位
            offset ++;
          	// 减少一圈
            loop --;
        }
      	// 如果n为奇数，最中间会有一格循环覆盖不到，需要单独赋值
        if (n % 2 == 1) {
            res[n / 2][n / 2] = count;
        }
        return res;
    }
}
``````

![螺旋矩阵](https://img-blog.csdnimg.cn/2020121623550681.png)

Note: 

O(n^2)

记忆：外层loop为循环圈数，内层4个loops为循环圈内的4条边中的元素数量

### 57. Insert Interval*

**Example 1:**

```
Input: intervals = [[1,3],[6,9]], newInterval = [2,5]
Output: [[1,5],[6,9]]
```

**Example 2:**

```
Input: intervals = [[1,2],[3,5],[6,7],[8,10],[12,16]], newInterval = [4,8]
Output: [[1,2],[3,10],[12,16]]
Explanation: Because the new interval [4,8] overlaps with [3,5],[6,7],[8,10].
```

**思路：**

看代码

```java
class Solution {
    public int[][] insert(int[][] intervals, int[] newInterval) {
        ArrayList<int[]> result = new ArrayList<>();
        for (int[] interval : intervals) {
            if (interval[1] < newInterval[0]) {
                result.add(interval);
            } else if (interval[0] > newInterval[1]) {
                result.add(newInterval);
                newInterval = interval;
            } else {
                newInterval[0] = Math.min(newInterval[0], interval[0]);
                newInterval[1] = Math.max(newInterval[1], interval[1]);
            }
        }
        result.add(newInterval);
        return result.toArray(new int[result.size()][]);
    }
}
```

### 238. Product of Array Except Self**

**Example 1:**

```
Input: nums = [1,2,3,4]
Output: [24,12,8,6]
```

**Example 2:**

```
Input: nums = [-1,1,0,-3,3]
Output: [0,0,9,0,0]
```

**思路：**

Prefix product

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696482361.mov"></video>

```java
class Solution {
    public int[] productExceptSelf(int[] nums) {
        int[] result = new int[nums.length];
        result[0] = 1;
        int product = 1;
        for (int i = 1; i < nums.length; i++) {
            result[i] = product * nums[i-1];
            product = result[i];
        }
        int after = 1;
        for (int i = nums.length - 2; i >= 0; i--) {
            after = after * nums[i+1];
            result[i] = result[i] * after;
        }
        return result;
    }
}
```

### 39. Combination Sum*

**Example 1:**

```
Input: candidates = [2,3,6,7], target = 7
Output: [[2,2,3],[7]]
Explanation:
2 and 3 are candidates, and 2 + 2 + 3 = 7. Note that 2 can be used multiple times.
7 is a candidate, and 7 = 7.
These are the only two combinations.
```

**Example 2:**

```
Input: candidates = [2,3,5], target = 8
Output: [[2,2,2,2],[2,3,3],[3,5]]
```

**思路：**

backtracking

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696544685.mov"></video>

```java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    List<Integer> sumToTarget = new ArrayList<>();
    int sum = 0;

    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtracking(candidates, target, 0);
        return result;
    }

    private void backtracking(int[] candidates, int target, int index) {
        if (sum == target) {
            result.add(new ArrayList<>(sumToTarget));  // new ArrayList<>(sumToTarget)的作用是复制一份sumToTarget到一个新的ArrayList，这样在后续对sumToTarget进行更改的时候就不会把result中的sumToTarget的值改掉了
            return;
        } else if (sum > target) {  // 这里的作用是防止一直无限往后加
            return;
        }

        for (int i = index; i < candidates.length; i++) {
            sumToTarget.add(candidates[i]);
            sum += candidates[i];
            backtracking(candidates, target, i);
            sum -= candidates[i];
            sumToTarget.remove(sumToTarget.size() - 1);
        }
    }
}
```

为什么需要new ArrayList<>(sumToTarget):

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696545563.mov"></video>

### 169. Majority Element*

The majority element is the element that appears more than `⌊n / 2⌋` times. You may assume that the majority element always exists in the array.

**Example 1:**

```
Input: nums = [3,2,3]
Output: 3
```

**Example 2:**

```
Input: nums = [2,2,1,1,1,2,2]
Output: 2
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696550146.mov"></video>

```java
class Solution {
    public int majorityElement(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
            } else {
                map.put(num, map.get(num) + 1);
            }
            if (map.get(num) > nums.length / 2) {
                return num;
            }
        }
        return 0;
    }
}
```

### 75. Sort Colors*

**Example 1:**

```
Input: nums = [2,0,2,1,1,0]
Output: [0,0,1,1,2,2]
```

**Example 2:**

```
Input: nums = [2,0,1]
Output: [0,1,2]
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696552783.MP4"></video>

```java
class Solution {
    public void sortColors(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        for (int i = 0; i <= right; i++) {
            if (nums[i] == 0) {
                swap(nums, i, left);
                left++;
            } else if (nums[i] == 2) {
                swap(nums, i, right);
                right--;
                i--;
            }
        }
    }

    private void swap(int[] nums, int i, int j) {
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
```

为什么只有`==2`的时候需要`i--`：

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696553494.MP4"></video>

### 128. Longest Consecutive Sequence**

**Example 1:**

```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2:**

```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696557954.MP4"></video>

```java
class Solution {
    public int longestConsecutive(int[] nums) {
        HashSet<Integer> set = new HashSet<>();
        int max = 0;
        for (int num : nums) {
            set.add(num);
        }

        for (int num : nums) {
            int count = 1;

            int left = num - 1;
            while (set.contains(left)) {
                count++;
                set.remove(left);
                left--;
            }

            int right = num + 1;
            while (set.contains(right)) {
                count++;
                set.remove(right);
                right++;
            }

            max = Math.max(max, count);
        }

        return max;
    }
}
```

### 189. Rotate Array*

**Example 1:**

```
Input: nums = [100,4,200,1,3,2]
Output: 4
Explanation: The longest consecutive elements sequence is [1, 2, 3, 4]. Therefore its length is 4.
```

**Example 2:**

```
Input: nums = [0,3,7,2,5,8,4,6,0,1]
Output: 9
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696570132.MP4"></video>

补充：当length = 7时，k = 3时的效果应该与k = 10一样，所以需要`k = k % nums.length`

```java
class Solution {
    public void rotate(int[] nums, int k) {
        k = k % nums.length;
        int start = nums.length - k;
        int[] result = new int[nums.length];
        for (int i = start; i < start + nums.length; i++) {
            int index = i % nums.length;
            result[i - start] = nums[index];
        }
        for (int i = 0; i < nums.length; i++) {
            nums[i] = result[i];
        }
    }
}
```

### 525. Contiguous Array**

**Example 1:**

```
Input: nums = [0,1]
Output: 2
Explanation: [0, 1] is the longest contiguous subarray with an equal number of 0 and 1.
```

**Example 2:**

```
Input: nums = [0,1,0]
Output: 2
Explanation: [0, 1] (or [1, 0]) is a longest contiguous subarray with equal number of 0 and 1.
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696640018.mov"></video>

```
class Solution {
    public int findMaxLength(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int count = 0;
        map.put(count, -1);
        int max = 0;
        for (int i = 0; i < nums.length; i++) {
            count = nums[i] == 0 ? count - 1 : count + 1;
            if (!map.containsKey(count)) {
                map.put(count, i);
            } else {
                int length = i - map.get(count);
                max = Math.max(max, length);
            }
        }
        return max;
    }
}
```

### 560. Subarray Sum Equals K**

**Example 1:**

```
Input: nums = [1,1,1], k = 2
Output: 2
```

**Example 2:**

```
Input: nums = [1,2,3], k = 3
Output: 2
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696647521.mov"></video>

```java
class Solution {
    public int subarraySum(int[] nums, int k) {
        int sum = 0;
        int[] sumArr = new int[nums.length];
        
        for (int i = 0; i < nums.length; i++) {
            sum += nums[i];
            sumArr[i] = sum;
        }
        HashMap<Integer, Integer> map = new HashMap<>();
        map.put(0, 1);
        int count = 0;
        for (int sumElement : sumArr) {
            if (map.containsKey(sumElement - k)) {
                count += map.get(sumElement - k);
            }
            if (!map.containsKey(sumElement)) {
                map.put(sumElement, 1);
            } else {
                map.put(sumElement, map.get(sumElement) + 1);
            }
        }
        return count;
    }
}
```

### 283. Move Zeroes*

**Example 1:**

```
Input: nums = [0,1,0,3,12]
Output: [1,3,12,0,0]
```

**Example 2:**

```
Input: nums = [0]
Output: [0]
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696649711.MP4"></video>

```java
class Solution {
    public void moveZeroes(int[] nums) {
        int index = 0;
        for (int num : nums) {
            if (num != 0) {
                nums[index] = num;
                index++;
            }
        }

        for (int i = index; i < nums.length; i++) {
            nums[i] = 0;
        }
    }
}
```

### 239. Sliding Window Maximum***

**Example 1:**

```
Input: nums = [1,3,-1,-3,5,3,6,7], k = 3
Output: [3,3,5,5,6,7]
Explanation: 
Window position                Max
---------------               -----
[1  3  -1] -3  5  3  6  7       3
 1 [3  -1  -3] 5  3  6  7       3
 1  3 [-1  -3  5] 3  6  7       5
 1  3  -1 [-3  5  3] 6  7       5
 1  3  -1  -3 [5  3  6] 7       6
 1  3  -1  -3  5 [3  6  7]      7
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1696664243.MP4"></video>

Runtime:

每个元素会被add into deque一次和remove from deque一次，所以是O(2n) = O(n)

```java
class Solution {
    public int[] maxSlidingWindow(int[] nums, int k) {
        Deque<Integer> deque = new LinkedList<>();
        int[] result = new int[nums.length - k + 1];
        for (int i = 0; i < nums.length; i++) {
            while (deque.size() > 0 && deque.peekFirst() < i - k + 1) {
                deque.removeFirst();
            }
            while (deque.size() > 0 && nums[deque.peekLast()] < nums[i]) {
                deque.removeLast();
            }
            deque.addLast(i);

            if (i >= k - 1) {
                result[i - k + 1] = nums[deque.peekFirst()];
            }
        }

        return result;
    }
}
```

### 977. Squares of a Sorted Array

**Example 1:**

```
Input: nums = [-4,-1,0,3,10]
Output: [0,1,9,16,100]
Explanation: After squaring, the array becomes [16,1,0,9,100].
After sorting, it becomes [0,1,9,16,100].
```

**Example 2:**

```
Input: nums = [-7,-3,2,3,11]
Output: [4,9,9,49,121]
```

**思路：**

左右两个双指针比大小，大的放到result结尾，直到双指针碰到一起

```java
class Solution {
    public int[] sortedSquares(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        int index = nums.length - 1;
        int[] result = new int[nums.length];
        while (left <= right) {
            if (nums[left] * nums[left] > nums[right] * nums[right]) {
                result[index] = nums[left] * nums[left];
                left++;
            } else {
                result[index] = nums[right] * nums[right];
                right--;
            }
            index--;
        }
        return result;
    }
}
```

### 16. 3Sum Closest*

**Example 1:**

```
Input: nums = [-1,2,1,-4], target = 1
Output: 2
Explanation: The sum that is closest to the target is 2. (-1 + 2 + 1 = 2).
```

**Example 2:**

```
Input: nums = [0,0,0], target = 1
Output: 0
Explanation: The sum that is closest to the target is 0. (0 + 0 + 0 = 0).
```

**思路：**

<video src="/Users/samuel/Desktop/LeetCode_Notes/videos/RPReplay_Final1697519442.MP4"></video>

```java
class Solution {
    public int threeSumClosest(int[] nums, int target) {
        Arrays.sort(nums);
        int difference = Integer.MAX_VALUE;
        int closestSum = 0;
        for (int i = 0; i < nums.length; i++) {
            int left = i + 1;
            int right = nums.length - 1;

            while (left < right) {
                int sum = nums[i] + nums[left] + nums[right];
                if (Math.abs(sum - target) < difference) {
                    difference = Math.abs(sum - target);
                    closestSum = sum;
                }
                if (sum < target) {
                    left++;
                } else if (sum > target) {
                    right--;
                } else {
                    return closestSum;
                }
            }
        }
        return closestSum;
    }
}
```

### 