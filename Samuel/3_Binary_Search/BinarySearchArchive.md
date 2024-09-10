# Binary Search

### 278. First Bad Version

You are a product manager and currently leading a team to develop a new product. Unfortunately, the latest version of your product fails the quality check. Since each version is developed based on the previous version, all the versions after a bad version are also bad.

Suppose you have `n` versions `[1, 2, ..., n]` and you want to find out the first bad one, which causes all the following ones to be bad.

You are given an API `bool isBadVersion(version)` which returns whether `version` is bad. Implement a function to find the first bad version. You should minimize the number of calls to the API.

**Example 1:**

```
Input: n = 5, bad = 4
Output: 4
Explanation:
call isBadVersion(3) -> false
call isBadVersion(5) -> true
call isBadVersion(4) -> true
Then 4 is the first bad version.
```

**Example 2:**

```
Input: n = 1, bad = 1
Output: 1
```

**思路：**

二分法

```java
/* The isBadVersion API is defined in the parent class VersionControl.
      boolean isBadVersion(int version); */

public class Solution extends VersionControl {
    public int firstBadVersion(int n) {
        int left = 1;
        int right = n;

        while (left <= right) {
            int mid = (left + right) / 2;  // (left + right)会integer overflow

            if (isBadVersion(mid)) {
                right = mid - 1;
            } else {
                left = mid + 1;
            }
        }
        return left;
    }
}
```

### 33. Search in Rotated Sorted Array**

**Example 1:**

```
Input: nums = [4,5,6,7,0,1,2], target = 0
Output: 4
```

**Example 2:**

```
Input: nums = [4,5,6,7,0,1,2], target = 3
Output: -1
```

**Example 3:**

```
Input: nums = [1], target = 0
Output: -1
```

**思路：**

二分法的思路是，如果target在左边，就去左边找，如果target在右边，就去右边找。

对于普通的sorted array，可以通过对比target和nums[mid]来判断target在哪边。

对于rotated sorted array，不能仅仅通过对比target和nums[mid]来判断，比如：

34567012，target=1，如果通过1<6确定1在左边就错了。

正确方法是：对于左边3456，我们要检查1是否在3和6中间。因为不在，所以必在右边7012中。

但是这种检查成立的前提是3456是sorted的。

对于一个rotated sorted array，左半部份和右半部分一定有一个是sorted的：

67012345：右半部分是sorted

34567012：左半部份是sorted

所以我们只要确定sorted的是哪半边，就可以去那半边通过上面的正确方法找target。

如果在，就继续把这半边分成两半找，如果不在，就把另外半边分成两半找。

```java
class Solution {
    public int search(int[] nums, int target) {
        int left = 0;
        int right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            }

            if (nums[left] <= nums[mid]) {  // the left part is sorted
                // if target is in left part
                if (target >= nums[left] && target < nums[mid]) {
                    right = mid - 1;
                // if target is in right part
                } else {
                    left = mid + 1;
                }
            } else {  // the right part is sorted
                // if target is in right part
                if (target <= nums[right] && target > nums[mid]) {
                    left = mid + 1;
                // if target is in left part
                } else {
                    right = mid - 1;
                }
            }
        }
        return -1;
    }
}
```

### 981. Time Based Key-Value Store*

**Example 1:**

```
Input
["TimeMap", "set", "get", "get", "set", "get", "get"]
[[], ["foo", "bar", 1], ["foo", 1], ["foo", 3], ["foo", "bar2", 4], ["foo", 4], ["foo", 5]]
Output
[null, null, "bar", "bar", null, "bar2", "bar2"]

Explanation
TimeMap timeMap = new TimeMap();
timeMap.set("foo", "bar", 1);  // store the key "foo" and value "bar" along with timestamp = 1.
timeMap.get("foo", 1);         // return "bar"
timeMap.get("foo", 3);         // return "bar", since there is no value corresponding to foo at timestamp 3 and timestamp 2, then the only value is at timestamp 1 is "bar".
timeMap.set("foo", "bar2", 4); // store the key "foo" and value "bar2" along with timestamp = 4.
timeMap.get("foo", 4);         // return "bar2"
timeMap.get("foo", 5);         // return "bar2"
```

**思路：**

如果在timestamp = 1, 3, 5时分别set，如果此时要get 4时的value，应该get到3时的value

二分法找的话，找到3，发现3比4小，此时应该记录3的value

对于1, 3, 5, 7, 9, 11, 13, 15, 17, 19, 21如果要get 20时的value，先找到11，记录11的value，再找到17，用17的value替换11的value，再找到19，用19的value替换17的value，最后结束循环，输出19的value

```java
class Pair {
    int timestamp;
    String val;

    // constructor
    Pair(int timestamp, String val) {
        this.timestamp = timestamp;
        this.val = val;
    }
}

class TimeMap {
    HashMap<String, ArrayList<Pair>> hashMap;

    // constructor
    public TimeMap() {
        hashMap = new HashMap<>();
    }
    
    public void set(String key, String value, int timestamp) {
        if (hashMap.containsKey(key)) {
            hashMap.get(key).add(new Pair(timestamp, value));
        } else {
            ArrayList<Pair> arr = new ArrayList<>();
            arr.add(new Pair(timestamp, value));
            hashMap.put(key, arr);
        }
    }
    
    public String get(String key, int timestamp) {

        String cand = "";

        if (hashMap.containsKey(key)) {
            ArrayList<Pair> arr = hashMap.get(key);
            int low = 0, high = arr.size() - 1;

            while (low <= high) {
                int mid = (low + high) / 2;
                int midTime = arr.get(mid).timestamp;
                if (midTime == timestamp) {
                    return arr.get(mid).val;
                } else if (midTime < timestamp) {
                    cand = arr.get(mid).val;
                    low = mid + 1;
                } else {
                    high = mid - 1;
                }
            }
        }

        return cand;
    }
}

/**
 * Your TimeMap object will be instantiated and called as such:
 * TimeMap obj = new TimeMap();
 * obj.set(key,value,timestamp);
 * String param_2 = obj.get(key,timestamp);
 */
```

### 74. Search a 2D Matrix

You are given an `m x n` integer matrix `matrix` with the following two properties:

- Each row is sorted in non-decreasing order.
- The first integer of each row is greater than the last integer of the previous row.

Given an integer `target`, return `true` *if* `target` *is in* `matrix` *or* `false` *otherwise*.

You must write a solution in `O(log(m * n))` time complexity.

 

**Example 1:**

![img](https://assets.leetcode.com/uploads/2020/10/05/mat.jpg)

```
Input: matrix = [[1,3,5,7],[10,11,16,20],[23,30,34,60]], target = 3
Output: true
```

**思路：**

比基础的binary search多一步转换index

```java
class Solution {
    public boolean searchMatrix(int[][] matrix, int target) {
        int m = matrix.length;
        int n = matrix[0].length;
        int len = m * n;

        int left = 0;
        int right = len - 1;
        while (left <= right) {
          
          	// 转换mid为first和second
            int mid = (left + right) / 2;
            int first = mid / n;
            int second = mid % n;
          
            if (matrix[first][second] == target) {
                return true;
            } else if (matrix[first][second] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }
        return false;
    }
}
```

### 153. Find Minimum in Rotated Sorted Array*

Suppose an array of length `n` sorted in ascending order is **rotated** between `1` and `n` times. For example, the array `nums = [0,1,2,4,5,6,7]` might become:

- `[4,5,6,7,0,1,2]` if it was rotated `4` times.
- `[0,1,2,4,5,6,7]` if it was rotated `7` times.

Notice that **rotating** an array `[a[0], a[1], a[2], ..., a[n-1]]` 1 time results in the array `[a[n-1], a[0], a[1], a[2], ..., a[n-2]]`.

Given the sorted rotated array `nums` of **unique** elements, return *the minimum element of this array*.

You must write an algorithm that runs in `O(log n) time.`

 

**Example 1:**

```
Input: nums = [3,4,5,1,2]
Output: 1
Explanation: The original array was [1,2,3,4,5] rotated 3 times.
```

**思路：**

​	// 012345678	0<4<8

​        // 801234567	8>3<7

​        // 780123456	7>2<6

​        // 678912345	6>1<5

​        // 567801234	5>0<4

​        // 456780123	4<8>3

​        // 345678012	3<7>2

​        // 234567801	2<6>1

​        // 123456780	1<5>0

把数列分成左半边和右半边，哪半边的最左端值大于最右端值，最小值就在哪半边，用binary search缩小范围

除了012345678这种情况，左右半边的最左端值都小于最右端值，这种情况最小值在第一位，也就是0

等到只有两个值的时候，直接输出更小的那个值即可

```java
class Solution {
    public int findMin(int[] nums) {
        int left = 0;
        int right = nums.length - 1;
        while (left < right - 1) {
            int mid = (left + right) / 2;
            if (nums[left] < nums[mid] && nums[mid] < nums[right]) {
                return nums[left];
            } else if (nums[left] > nums[mid] && nums[mid] < nums[right]) {
                right = mid;
            } else if (nums[left] < nums[mid] && nums[mid] > nums[right]) {
                left = mid;
            }
        }
        return nums[left] < nums[right] ? nums[left] : nums[right];
    }
}
```

### 