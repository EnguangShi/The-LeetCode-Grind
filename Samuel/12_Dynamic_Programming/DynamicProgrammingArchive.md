# Dynamic Programming

### Amazon's Regex Matching

The developers at Amazon are developing a regex matching library for their NLP use cases. A prototype regex matching has the following requirements:

The regex expression contains lowercase English letters, '(', ')', '.', and '*'.

'.' matches with exactly one lowercase English letter.

A regular expression followed by '*' matches with zero or more occurrences of the regular expression.

If an expression is enclosed in parentheses '(' and ')', it is treated as one expression and any asterisk '*' applies to the whole expression. It is guaranteed that no expression enclosed within parenthesis contains any '*' but is always followed by '*'. Also, there is no nested brackets sequence in the given regex expression for the prototype.

For example,

Regex "(ab)*d" matches with the strings "d", "ababd", "abd", but not with the strings "abbd", "abab".

Regex "ab*d" matches with the strings "abbbd", "ad", "abd", but not with strings "ababd".

Regex "a(b.d)*" matches with the strings "abcdbcd", "abcdbed", "abed", "a" but not with strings "bcd", "abd".

Regex "(.)*" matches with the strings "a", "aa", "aaa", "b", "bb" and many more but not "ac", "and", or "bcd" for example.

Given an array, arr, of length k containing strings consisting of lowercase English letters only and a string regex of length n, for each of them find whether the given regex matches with the string or not and report an array of strings "YES" or "NO" respectively.

**Function Description**

Complete the function `isRegexMatching` in the editor.

`isRegexMatching` has the following parameters:

- regex: A regex
- arr[k]: An array of strings

**Returns**

string[]: An array of strings of size k where the ith string is "YES" if the ith string in arr matches with regex, otherwise it is "NO".

```java
// 看不懂先去看leetcode 10，如果leetcode 10也看不懂先去看CPSC320 动态规划教案
public class RegexMatchingDP {

    public static void main(String[] args) {
        String regex = "()*";
        String[] arr = {"", "d"};
        String[] result = isRegexMatching(regex, arr);
        for (String res : result) {
            System.out.println(res);
        }
    }

    
    public static String[] isRegexMatching(String regex, String[] arr) {
      // write your code here
      String[] result = new String[arr.length];
      for (int i = 0; i < arr.length; i++) {
        if (isRegex(arr[i], regex)) {
          result[i] = "YES";
        } else {
          result[i] = "NO";
        }
      }
      return result;
    }

    private static boolean isRegex(String str, String regex) {
      boolean[][] dp = new boolean[str.length() + 1][regex.length() + 1];
        
      dp[0][0] = true;
        
      for (int i = 0; i < regex.length(); i++) {
        if (regex.charAt(i) == '*' && regex.charAt(i - 1) != ')') {
          if (dp[0][i - 1]) {
            dp[0][i + 1] = true;
          }
            
        } else if (regex.charAt(i) == '*' && regex.charAt(i - 1) == ')') {
          for (int j = i - 2; j >= 0; j--) {
            if (regex.charAt(j) == '(' && dp[0][j]) {
              dp[0][i + 1] = true;
            }
          }
        }
      }

      for (int i = 0; i < str.length(); i++) {
        for (int j = 0; j < regex.length(); j++) {
            
          if (regex.charAt(j) == '(' || regex.charAt(j) == ')') {
            continue;
          }
        
          if (regex.charAt(j) == '.' || str.charAt(i) == regex.charAt(j)) {
            dp[i + 1][j + 1] = dp[i][j];
          }
            
          if (regex.charAt(j) == '*') {
              
            if(regex.charAt(j - 1) == '.') {
              dp[i + 1][j + 1] = dp[i][j - 1] || (dp[i][j + 1] && str.charAt(i) == str.charAt(i - 1)) || dp[i + 1][j - 1];
            
            } else if (j >= 3 && regex.substring(j - 3, j).equals("(.)")) {
              dp[i + 1][j + 1] = dp[i][j - 3] || (dp[i][j + 1] && str.charAt(i) == str.charAt(i - 1)) || dp[i + 1][j - 3];
                
            } else {
                
              if (regex.charAt(j - 1) != ')') {
                    
                if (str.charAt(i) == regex.charAt(j - 1)) {
                  dp[i + 1][j + 1] = dp[i][j - 1] || dp[i][j + 1] || dp[i + 1][j - 1];
                      
                } else {
                  dp[i + 1][j + 1] = dp[i + 1][j - 1];
                }

              } else {
                    
                int n = 0;
                for (int k = j - 2; regex.charAt(k) != '('; k--) {
                  n++;
                }
                int subStrStart = i + 1 - n >= 0 ? i + 1 - n : 0;
                int subRegexStart = j - 1 - n >= 0 ? j - 1 - n : 0;
                String subStr = str.substring(subStrStart, i + 1);
                String subRegex = regex.substring(subRegexStart, j - 1);
                    
                if (isRegex(subStr, subRegex)) {
                  dp[i + 1][j + 1] = dp[i + 1 - n][j - 2 - n] || dp[i + 1 - n][j + 1] || dp[i + 1][j - 2 - n];
                      
                } else {
                  dp[i + 1][j + 1] = dp[i + 1][j - 2 - n];
                }
              }
            }
          }
        }
      }
      return dp[str.length()][regex.length()];
    }
}
```

### [70. Climbing Stairs](https://leetcode.com/problems/climbing-stairs/)

**Example 1:**

```
Input: n = 2
Output: 2
Explanation: There are two ways to climb to the top.
1. 1 step + 1 step
2. 2 steps
```

**Example 2:**

```
Input: n = 3
Output: 3
Explanation: There are three ways to climb to the top.
1. 1 step + 1 step + 1 step
2. 1 step + 2 steps
3. 2 steps + 1 step
```

```java
class Solution {
    public int climbStairs(int n) {
        int[] dp = new int[n + 1];
        dp[0] = 1;
        dp[1] = 1;

        if (n > 1) {
            for (int i = 2; i <= n; i++) {
                dp[i] = dp[i - 1] + dp[i - 2];
            }
        } 

        return dp[n];
    }
}
```

### [322. Coin Change](https://leetcode.com/problems/coin-change/)

**Example 1:**

```
Input: coins = [1,2,5], amount = 11
Output: 3
Explanation: 11 = 5 + 5 + 1
```

**Example 2:**

```
Input: coins = [2], amount = 3
Output: -1
```

**Example 3:**

```
Input: coins = [1], amount = 0
Output: 0
```

```java
class Solution {
    public int coinChange(int[] coins, int amount) {
        int[] dp = new int[amount + 1];
        dp[0] = 0;

        for (int i = 1; i <= amount; i++) {
            dp[i] = Integer.MAX_VALUE;
            for (int coin : coins) {
                if (i - coin >= 0 && dp[i - coin] != Integer.MAX_VALUE) {
                    dp[i] = Math.min(dp[i], dp[i - coin] + 1);
                }
            }
        }

        return dp[amount] == Integer.MAX_VALUE ? -1 : dp[amount];
    }
}
```

### 01背包

n个物品，每个物品有重量和价值，问在最大承重为m的背包内怎么放让价值最大

`dp[i][j]`的定义：编号为[0, i]物品任取几个放在承重为j的背包的最大价值

**递推公式**：

对于`dp[i][j]`：

不放物品i：`dp[i][j] = dp[i-1][j]` ：当前最大价值等于物品i之前所有物品能放在容量为j的背包的最大价值

放物品i：`dp[i][j] = dp[i-1][j-物品i的重量] + 物品i的价值`：当前最大价值等于物品i之前所有物品能放在容量减去i的重量的背包中的最大价值，再加上物品i的价值

取上述两个的最大值：

`dp[i][j] = max(dp[i-1][j], dp[i-1][j-物品i的重量] + 物品i的价值)`

**如何初始化**dp数组：

| `dp[i][j]` | j = 0 | j = 1 | j = 2 | j = 3                                                        |
| ---------- | ----- | ----- | ----- | ------------------------------------------------------------ |
| i = 0      | ——    | ——    | ——    | ——                                                           |
| i = 1      | ｜    |       | ↘️     | ⬇️                                                            |
| i = 2      | ｜    |       |       | 此处值由`[i-1][j]` (上方的格子)或`[i-1][j-某数]`(左上的格子)推出 |

所以划横线的地方(i = 0, or j = 0)要初始化，其余的格子都能被递推算出

`dp[i][0]`，背包容量为0，无论是选取哪些物品，背包价值总和一定为0。

`dp[0][j]`，即：存放编号0的物品的时候，各个容量的背包所能存放的最大价值。只要容量大于物品0的重量，最大价值就是物品0的价值。

`dp[i][j]`，其余的格子都可以通过上方和左上方的格子求出，所以初始化成任意值都可以。此处可以初始化成0。

**遍历顺序**：

因为是二维dp数组，两层for循环需要考虑第一层和第二层分别遍历i(物品)还是j(容量)

```java
for () {
  for () {
    
  }
}
```

如果是第一层物品，第二层容量，那么假设对于物品1，遍历了容量1，容量2，容量3：

![动态规划-背包问题5](https://code-thinking-1253855093.file.myqcloud.com/pics/202101101032124.png)

此时物品1容量3可以通过上方和左上方的格子推导出结果，所以**第一层物品，第二层容量**的遍历顺序是ok的

如果是第一层容量，第二层物品，那么假设遍历完了容量1，容量2的物品，现在遍历到容量3的物品1：

![动态规划-背包问题6](https://code-thinking-1253855093.file.myqcloud.com/pics/20210110103244701.png)

此时物品1容量3**也**可以通过上方和左上方的格子推导出结果，所以**第一层容量，第二层物品**的遍历顺序**也**是ok的

**代码：**

```java
public class Main {
    public static void main(String[] args) {
        int n = 4; // Number of items
        int bagweight = 10; // Capacity of the bag

        int[] weight = {2, 3, 4, 5};
        int[] value = {3, 4, 5, 6};

        int[][] dp = new int[n][bagweight + 1];

        for (int j = weight[0]; j <= bagweight; j++) {
            dp[0][j] = value[0];
        }

        for (int i = 1; i < n; i++) {
            for (int j = 0; j <= bagweight; j++) {
                if (j < weight[i]) {
                    dp[i][j] = dp[i - 1][j];
                } else {
                    dp[i][j] = Math.max(dp[i - 1][j], dp[i - 1][j - weight[i]] + value[i]);
                }
            }
        }

        System.out.println(dp[n - 1][bagweight]);
    }
}
```

