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

