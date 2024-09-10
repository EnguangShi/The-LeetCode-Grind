# Backtracking

回溯算法模版框架：

``````java
void backtracking(参数) {
    if (终止条件) {
        存放结果;
        return;
    }

    for (选择：本层集合中元素（树中节点孩子的数量就是集合的大小）) {
        处理节点;
        backtracking(路径，选择列表); // 递归
        回溯，撤销处理结果
    }
}
``````

![回溯算法理论基础](https://code-thinking-1253855093.file.myqcloud.com/pics/20210130173631174.png)

回溯和递归是相辅相成的。

回溯法其实就是暴力查找，并不是什么高效的算法。

回溯算法能解决如下问题：

- 组合问题：N个数里面按一定规则找出k个数的集合
- 排列问题：N个数按一定规则全排列，有几种排列方式
- 切割问题：一个字符串按一定规则有几种切割方式
- 子集问题：一个N个数的集合里有多少符合条件的子集
- 棋盘问题：N皇后，解数独等等

### 组合问题77

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();  // 用LinkedList方便往里加新的元素和删最后一个元素
    public List<List<Integer>> combine(int n, int k) {
        backtracking(n, k, 1);
        return result;
    }

    private void backtracking(int n, int k, int start) {
        if (path.size() == k) {  // 如果有两个元素
            result.add(new ArrayList<>(path)); // 复制一份path成为ArrayList，加入result list中成为list中list
            return;
        }

        for (int i = start; i <= n; i++) {
            path.add(i);
            backtracking(n, k, i + 1);
            path.removeLast();  // [1,2] -> remove 2 -> [1,3] -> remove 3 -> [1,4] -> remove 4 -> remove 1 -> [2,3] ...
        }
    }
}
``````

思路：

![77.组合2](https://code-thinking-1253855093.file.myqcloud.com/pics/20201123195328976.png)

回溯算法的核心就是for loop里那三行，仔细看一遍代码吧

**优化：**

思路：

![77.组合4](https://code-thinking-1253855093.file.myqcloud.com/pics/20210130194335207.png)

如图所示，我们需要从1,2,3,4中取4个数，那么先取1，然后可以取2，再3，再4，这样总共有4个数。如果取2，只能取3和4，这样总共只有3个数，不满足取4个数的条件。

所以我们可以这样优化：

假设要在4个数里选3个，目前没有已选的数，那么可以i = 1, 2都可以，因为1可以1,2,3，1,2,4，1,3,4，2可以2,3,4。

1. 已经选择的元素个数：path.size() = 0;

2. 所需要的元素个数为: k - path.size() = 3 - 0 = 3;

3. n - (k - path.size()) = 4 - 3 = 1

4. i <= n - (k - path.size()) + 1 = 1 + 1 = 2

5. 所以

   `for (int i = startIndex; i <= n - (k - path.size()) + 1; i++)`即为选择i = 1和i = 2

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combine(int n, int k) {
        combineHelper(n, k, 1);
        return result;
    }

    private void combineHelper(int n, int k, int startIndex){
        //终止条件
        if (path.size() == k){
            result.add(new ArrayList<>(path));
            return;
        }
        for (int i = startIndex; i <= n - (k - path.size()) + 1; i++){
            path.add(i);
            combineHelper(n, k, i + 1);
            path.removeLast();
        }
    }
}
``````

### 组合总和三216

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combinationSum3(int k, int n) {
        backtracking(k, n, 1);
        return result;
    }

    private void backtracking(int k, int n, int start) {
        if (path.size() == k) {
            int sum = 0;
            for (int i = 0; i <= path.size() - 1; i++) {
                sum += path.get(i);
                
                // optimization
                if (sum > n) {
                    return;
                }

            }
            if (sum == n) {
                result.add(new ArrayList<>(path));
            }
            return;
        }

        for (int i = start; i <= 9 - (k - path.size()) + 1; i++) {  // 剪枝
            path.add(i);
            backtracking(k, n, i + 1);
            path.removeLast();
        }
    }
}
``````

思路：和上题很类似。

### 电话号码的字母组合17

``````java
class Solution {
    StringBuilder path = new StringBuilder();  // SB可以append char
    List<String> result = new ArrayList<>();

    public List<String> letterCombinations(String digits) {
        if (digits.length() == 0) {  // ""这个字符串不是null，而是长度为0
            return result;  // new ArrayList<>()即为[]
        }
        String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};  // 记忆创建数组的方式
        backtracking(digits, numString, 0);
        return result;
    }

    private void backtracking(String digits, String[] numString, int start) {
        if (path.length() == digits.length()) {
            result.add(path.toString());
            return;
        }

        int digit = digits.charAt(start) - '0';  // 记忆把char转换成int的方式，要减去'0'
        for (int i = 0; i <= numString[digit].length() - 1; i++) {
            path.append(numString[digit].charAt(i));
            backtracking(digits, numString, start + 1);
            path.deleteCharAt(path.length() - 1);
        }
    }
}
``````

思路：

假设给的字符串是"234"，在backtracking函数中使用for loop循环2,3,4是错的，应该用递归的方法来让start + 1从而循环2,3,4

记忆：

1. StringBuilder可以append char

2. ""这个字符串不是null，而是长度为0

3. new ArrayList<>()即为[]

4. `String[] numString = {"", "", "abc", "def", "ghi", "jkl", "mno", "pqrs", "tuv", "wxyz"};`  

   记忆创建数组的方式

5. `int digit = '2' - '0';`  

   记忆把char转换成int的方式，要减去'0'

### 组合总和39

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
        backtracking(candidates, target, 0, 0);
        return result;
    }

    private void backtracking(int[] candidates, int target, int start, int sum) {
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }
        if (sum > target) {
            return;
        }

        for (int i = start; i <= candidates.length - 1; i++) {
            path.add(candidates[i]);
            backtracking(candidates, target, i, sum + candidates[i]);  // 这里不用i+1因为可以重复用当前的数
            path.removeLast();
        }
    }
}
``````

思路：

和组合总和三类似，不过由于可以选择重复的数，当sum>target时return即可保证不会一直选择重复的数。

sum可以作为backtracking的parameter一直更新数值

**优化**

可以把sum > target就return移到更早的位置，比如进入新的一层backtracking之前。

这样由于判断sum > target成功时就break跳出for loop，这样这一层后续的元素都不会被循环到。也就剪去了更多的枝。

但是因为这一层后续的元素都不会被循环到，所以要保证后续的元素也都是使sum > target的，也就是必须比当前的元素大。所以我们需要先把candidates转换成一个单调递增的Array才能满足这点。

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combinationSum(int[] candidates, int target) {
      
      	// 增加
      	Arrays.sort(candidates);
      
        backtracking(candidates, target, 0, 0);
        return result;
    }

    private void backtracking(int[] candidates, int target, int start, int sum) {
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }
      
      	// 删除
        // if (sum > target) {
        //     return;
        // }

        for (int i = start; i <= candidates.length - 1; i++) {
            path.add(candidates[i]);
          
          	// 增加
          	if (sum + candidates[i] > target) {
              break;
            }
          
            backtracking(candidates, target, i, sum + candidates[i]);
            path.removeLast();
        }
    }
}
``````



### 组合总和二40

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> combinationSum2(int[] candidates, int target) {
        Arrays.sort(candidates);
        backtracking(candidates, target, 0, 0);
        return result;
    }

    private void backtracking(int[] candidates, int target, int sum, int start) {
        if (sum == target) {
            result.add(new ArrayList<>(path));
            return;
        }

        int cur = 0;
        for (int i = start; i <= candidates.length - 1; i++) {
          	// 检查当前元素是否和上个元素相同，因为是有序数组，如果当前元素是重复的，那么它一定与上个元素相同。
            if (candidates[i] != cur) {
              
              	// 剪枝
                if (sum + candidates[i] > target) {
                    break;
                }
              
                path.add(candidates[i]);
                System.out.println(path);
                backtracking(candidates, target, sum + candidates[i], i + 1);
                path.removeLast();
            }
          	// 记录当前元素
            cur = candidates[i];
        }
    }
}
``````

思路：

举例子，对于[1,1,1,2]，如果target=3，那么解为[[1,1,1],[1,2]]，所以在对1,1,1,2进行循环时，遇到第二个1和第三个1应该跳过，因为只应该有一个[1,2]的解，而不是三个。

但是循环到第一个1之后，进入下一层循环，对1,1,2进行循环，此时的1不需要跳过，因为并没有规定解中不能出现重复的数字，例如[1,1,1]中的1就是重复的。

我们跳过重复的1的方法是，在同一层for loop中，始终将当前元素和上一个元素对比，如果相同则不做任何操作，只有不同时才做操作。

![40.组合总和II](https://code-thinking-1253855093.file.myqcloud.com/pics/20230310000918.png)

不要看该图中的used数组，我的答案没有用到used数组而是用了更简单的方法，就看蓝色的解释。

### 分割回文串131

``````java
class Solution {
    List<List<String>> result = new ArrayList<>();
    LinkedList<String> path = new LinkedList<>();
    public List<List<String>> partition(String s) {
        backtracking(s, 0);
        return result;
    }

    private void backtracking(String s, int start) {
        if (start >= s.length()) {  // 如果已经走完了整个string，则说明已找到一组方案
            result.add(new ArrayList<>(path));
        }

        for (int i = start; i <= s.length() - 1; i++) {

          	// 
            String sub = s.substring(start, i + 1);
          	// 通过反转字符串与它本身比较来判断是否是回文串
            StringBuilder sb = new StringBuilder();
            sb.append(sub);
            sb.reverse();
            String reversedSub = sb.toString();
            if (sub.equals(reversedSub)) {  // string的比较要用equals()，因为String是大写字母开头的，这种type是object type，==号比较的是他们的reference而不是value
                path.add(sub);
                backtracking(s, i + 1);
                path.removeLast();
            }
        }
    }
}
``````

思路：

在for循环中，如果发现当前切出来的substring不是回文串，就不需要继续往下走了，比如abaa中，切出来ab，发现不是回文串，那么不管后面是什么，最后的结果都不会全部是回文串。

每次for loop，i会+1，substring(start, i+1)

每次recursion，start会是当前的i+1

![131.分割回文串](https://code-thinking.cdn.bcebos.com/pics/131.%E5%88%86%E5%89%B2%E5%9B%9E%E6%96%87%E4%B8%B2.jpg)

### 复原IP地址93

``````java
class Solution {
    List<String> result = new ArrayList<>();
    StringBuilder path = new StringBuilder();
    public List<String> restoreIpAddresses(String s) {
        backtracking(s, 0, 0);
        return result;
    }

    private void backtracking(String s, int start, int numOfDots) {
      
      	// 注意，此处仅仅为判断是否满足输出的条件，满足即输出到result，不应对path作任何改动。因为此处的改动也会被体现在后续的recursion中。
        if (numOfDots == 4) {
            result.add(path.substring(0, path.length() - 1).toString());
            return;
        }

        for (int i = start; i <= s.length() - 1; i++) {
            String sub;

            // 如果当前已经是IP地址中最后一节，那么substring只能是从当前start到string的结尾
            if (numOfDots == 3) {
                sub = s.substring(start);
            } else {
                sub = s.substring(start, i + 1);
            }

            // 如果有leading zero或者大于256都直接跳出循环
            if (sub.charAt(0)=='0' && sub.length() > 1) {
                break;
            }
          
          	// 这里先检查sub超不超过3位，防止它超出可以转换成Int的范围
            if (sub.length() > 3 || Integer.parseInt(sub) > 255) {
                break;
            }

            path.append(sub + ".");
            backtracking(s, i + 1, numOfDots + 1);
            System.out.println(path);
            System.out.println(sub);
          
          	// 注意：如果在判断path是否能输出时改动了path，return后会影响上一层recursion中这里的path.length()，造成报错
            path.setLength(path.length() - sub.length() - 1);

            // 如果已经是IP的最后一节了，直接截取剩余的数字，比如当s="25525511135"时，目前已有255.255.111的情况下，不需要3和35进行两次for loop了，直接使用35即可。所以for loop进行一次后，直接break
            if (numOfDots == 3) {
                break;
            }
        }
    }
}

``````

思路：这题和上题思路差不多，但是有很多细节需要注意，具体看注释。

![93.复原IP地址](https://code-thinking-1253855093.file.myqcloud.com/pics/20201123203735933-20230310132314109.png)

### 子集78

``````java
class Solution {
    LinkedList<Integer> path = new LinkedList<Integer>();
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> subsets(int[] nums) {
        backtracking(nums, 0);
        return result;
    }

    private void backtracking(int[] nums, int start) {
        // 每次都把当前path加入result
        result.add(new ArrayList<>(path));
        for (int i = start; i <= nums.length - 1; i++) {
            path.add(nums[i]);
            backtracking(nums, i + 1);
            path.removeLast();
        }
    }
}
``````

思路：

和组合问题有所不同的是，组合是收集叶子结点，子集是收集数的所有节点。

![78.子集](https://code-thinking.cdn.bcebos.com/pics/78.%E5%AD%90%E9%9B%86.png)

### 子集二90

``````java
class Solution {
    LinkedList<Integer> path = new LinkedList<Integer>();
    List<List<Integer>> result = new ArrayList<>();
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        Arrays.sort(nums);
        backtracking(nums, 0);
        return result;
    }

    private void backtracking(int[] nums, int start) {
        result.add(new ArrayList<>(path));
        int cur = -11;  // 给cur一个在nums范围之外的值
        for (int i = start; i <= nums.length - 1; i++) {
            // 检查当前元素是否和上个元素相同，因为是有序数组，如果当前元素是重复的，那么它一定与上个元素相同。
            if (nums[i] != cur) {
                path.add(nums[i]);
                backtracking(nums, i + 1);
                path.removeLast();
            }
            cur = nums[i];
        }
    }
}
``````

思路：与组合总和二40相似，在同一层for loop中去重

![90.子集II](https://code-thinking-1253855093.file.myqcloud.com/pics/20201124195411977.png)

### 递增子序列491

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    public List<List<Integer>> findSubsequences(int[] nums) {
        backtracking(nums, 0);
        return result;
    }

    private void backtracking(int[] nums, int start) {
      	// 当有两个及以上的元素时，才记录
        if (path.size() > 1) {
            result.add(new ArrayList<>(path));
        }

        HashSet<Integer> used = new HashSet<>();
        for (int i = start; i <= nums.length - 1; i++) {
            // 如果当前元素已被使用，直接跳过该次for loop，不可跳过整个for loop因为后面还会有没用过的元素
            if (used.contains(nums[i])) {
                continue;
            }
          	// 如果当前元素比之前的元素小，也跳过
            if (path.size() != 0 && nums[i] < path.get(path.size() - 1)) { 
                continue;
            }
            path.add(nums[i]);
            used.add(nums[i]);
            backtracking(nums, i + 1);
            path.removeLast();
        }
    }
}
``````

思路：

和子集78类似，也是要收集所有节点。不过要

1. 除去单个元素的结点，比如4,4,3,3中不能收集[4], [4], [3], [3]，因为这些不是non-decreasing sequence。
2. 除去当前元素比之前元素更小的结点。比如[4, 3]
3. 除去在该次loop中出现过的元素，用HashSet来放置已出现的，因为HashSet自带contains方法

### 全排列46

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    boolean[] used;
    public List<List<Integer>> permute(int[] nums) {
        used = new boolean[nums.length];
        backtracking(nums);
        return result;
    }

    private void backtracking(int[] nums) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

        for (int i = 0; i <= nums.length - 1; i++) {
            // 如果该元素已被使用，则跳过该元素继续for loop
            if (used[i]) {
                continue;
            }
            path.add(nums[i]);
            used[i] = true;
            backtracking(nums);
            used[i] = false;
            path.removeLast();
        }
    }
}
``````

思路：

![46.全排列](https://code-thinking-1253855093.file.myqcloud.com/pics/20211027181706.png)

### 全排列二47

``````java
class Solution {
    List<List<Integer>> result = new ArrayList<>();
    LinkedList<Integer> path = new LinkedList<>();
    boolean[] indexUsed;
    public List<List<Integer>> permuteUnique(int[] nums) {
        indexUsed = new boolean[nums.length];
        backtracking(nums);
        return result;
    }

    private void backtracking(int[] nums) {
        if (path.size() == nums.length) {
            result.add(new ArrayList<>(path));
            return;
        }

      	// 每层for loop定义一个新HashSet，因为是同层for loop去重
        HashSet<Integer> valueUsed = new HashSet<>();
        for (int i = 0; i <= nums.length - 1; i++) {
            if (indexUsed[i]) {
                continue;
            }
          	// 看是否在HashSet中存在过，存在即跳过
            if (valueUsed.contains(nums[i])) {
                continue;
            }
            path.add(nums[i]);
            indexUsed[i] = true;
          	// 不存在即加入HashSet
            valueUsed.add(nums[i]);
            backtracking(nums);
            indexUsed[i] = false;
            path.removeLast();
        }
    }
}
``````

思路：使用了了上一题全排列46跳过已使用元素，但是把used改名为了indexUsed，因为是根据index判断是否使用。

还需要执行同层for loop的去重。这里使用HashSet valueUsed，因为是根据value判断是否使用过。

### 22. Generate Parentheses*

**Example 1:**

```
Input: n = 3
Output: ["((()))","(()())","(())()","()(())","()()()"]
```

**思路：**

用backtracking：

用open记录当前左括号的数量，用close记录当前右括号的数量

写一个backtracking function，当当前字符串open=close=n的时候加入list

里面套两个backtracking functions，第一个是加左括号的情况，第二个是加右括号的情况

当左括号数量小于给定的n时，可以加左括号

比如n=3, '((('的时候就不能再加左括号

当右括号数量小于左括号数量时，可以加右括号

​                           比如'()'的时候就不能再加右括号了

backtracking体现在一个backtracking function里有两个backtracking functions，第一个在当前基础上加左括号，第二个在当前基础上加右括号，其实想当于去掉第一个的左括号加上右括号，这个就是backtracking。

```java
class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> list = new ArrayList<String>();
        backtracking(list, "", 0, 0, n);
        return list;
    }

    private void backtracking(List<String> list, String str, int open, int close, int max) {
        if (open == max && close == max) {
            list.add(str);
        }

        if (open < max) {
            backtracking(list, str+"(", open + 1, close, max);
        }
        if (close < open) {
            backtracking(list, str+")", open, close + 1, max);
        }
    }
}
```

### 