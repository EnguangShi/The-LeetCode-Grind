# String

### 反转字符串344

``````java
class Solution {
    public void reverseString(char[] s) {
        int start = 0;
        int end = s.length - 1;
        while (l < r) {
            char tmp = s[start];
          	s[start] = s[end];
          	s[end] = tmp;
            l++;
            r--;
        }
    }
}
``````

Note:

Runtime O(n), Space O(1)

思路：双指针首尾对调，使用while loop

### 反转字符串二541

``````java
class Solution {
    public String reverseStr(String s, int k) {
        char[] ch = s.toCharArray();  // 记忆.toCharArray()
        for(int i = 0; i < ch.length; i = i + 2 * k) {
            int start = i;
            int end = Math.min(start + k - 1, ch.length - 1);  // 记忆Math.min()
            while(start < end) {
                char tmp = ch[start];
                ch[start] = ch[end];
                ch[end] = tmp;
                start ++;
                end --;
            }
        }
        return new String(ch);  // 记忆new String()可转换Array成String
    }
}
``````

Note:

O(n)

思路：

- for loop中每次使i增加2k，用i < length判断是否结束循环

- 使用上题的方法用双指针首尾对调反转字符串中需要反转的部分

- 三种情况

  - 最后一次iteration剩余字符数量<k：
    - 全部reverse
    - `end = length - 1`(因为是字符串最后一个字符的index)
  - 最后一次iteration剩余字符数量=k：
    - 全部reverse == reverse前k个
    - `end = start + k - 1 = length - 1`
  - 最后一次iteration剩余字符数量>k：reverse前k个
    - reverse前k个
    - `end = start + k - 1`

  所以`end = Math.min(length - 1, start + k - 1)`

### 替换空格剑指05

``````java
class Solution {
    public String replaceSpace(String s) {
        StringBuilder sb = new StringBuilder();  // 记忆：StringBuilder可以append char
        for(int i = 0; i < s.length(); i++) {  // 记忆：string的length()带括号
            if(s.charAt(i) == ' ') {
                sb.append("%20");
            } else {
                sb.append(s.charAt(i));
            }
        }
        return sb.toString();  // 记忆：.toString()
    }
}

// 不用StringBuilder
class Solution {
    public String replaceSpace(String s) {
        String str = new String();
        for(int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ' ') {
                str = str + "%20";
            } else {
                str = str + s.charAt(i);
            }
        }
        return str;
    }
}
``````

Note:

O(n)

### 翻转字符串里的单词151

``````java
class Solution {
    public String reverseWords(String s) {
        StringBuilder sb = removeSpace(s);
        reverseString(sb, 0, sb.length() - 1);
        reverseEachWord(sb);
        return sb.toString();
    }
    
    private StringBuilder removeSpace(String s){
        int start = 0;
        int end = s.length() - 1;
        while (s.charAt(start) == ' ') {
            start ++;
        }
        while (s.charAt(end) == ' ') {
            end --;
        }
        StringBuilder sb = new StringBuilder();
        while (start <= end) {
            char c = s.charAt(start);
            if (c != ' ' || sb.charAt(sb.length() - 1) != ' ') {
                sb.append(s.charAt(start));
            }
            start ++;
        }
        return sb;
    }
    
    private void reverseString(StringBuilder sb, int start, int end) {
        while (start < end){
            char tmp = sb.charAt(start);
            sb.setCharAt(start, sb.charAt(end));  // 记忆.setCharAt()
            sb.setCharAt(end, tmp);
            start ++;
            end --;
        }
    }
    
    private void reverseEachWord(StringBuilder sb) {
        int start = 0;
        int end = 1;
        while (start < sb.length()) {
            while (end < sb.length() && sb.charAt(end) != ' ') {
                end ++;
            }
            reverseString(sb, start, end - 1);
            start = end + 1;
            end = start + 1;
        }
    }
}
``````

Note:

O(n)

思路：

1. 去除首尾及中间多余空格
2. 整个字符串反转
3. 再反转每个单词

简单版：

``````java
class Solution {
    public String reverseWords(String s) {
        String str = s.trim();  // 删首尾空格 
        String[] arr = str.split("\\s+");  // slice the input string s on the given regular expression. That regular expression simply says: "one or more whitespaces".
        
        StringBuilder newStr = new StringBuilder();
        
        for (int i = arr.length - 1; i >= 0; i--) {          
            newStr.append(arr[i]);
            newStr.append(" ");
        }
        return newStr.toString().trim();
    }
}
``````

### 左旋转字符串剑指58

``````java
class Solution {
    public String reverseLeftWords(String s, int n) {
        int left = 0;
        int right = s.length() - 1;
        char[] ch = s.toCharArray();
        ch = reverseString(ch, left, right);

        int leftLeft = 0;
        int leftRight = s.length() - 1 - n;
        int rightLeft = s.length() - n;
        int rightRight = s.length() - 1;
        ch = reverseString(ch, leftLeft, leftRight);
        ch = reverseString(ch, rightLeft, rightRight);

        return new String(ch);
    }

    private char[] reverseString(char[] ch, int left, int right) {
        while (left < right) {
            char tmp = ch[left];
            ch[left] = ch[right];
            ch[right] = tmp;
            left ++;
            right --;
        }
        return ch;
    }
}
``````

Note:

O(n)

思路：

先反转整个字符串，再反转前半段和后半段，达到左右位置互换的效果。节省空间

### 125. Valid Palindrome

**Example 1:**

```
Input: s = "A man, a plan, a canal: Panama"
Output: true
Explanation: "amanaplanacanalpanama" is a palindrome.
```

**Example 2:**

```
Input: s = "race a car"
Output: false
Explanation: "raceacar" is not a palindrome.
```

**Example 3:**

```
Input: s = " "
Output: true
Explanation: s is an empty string "" after removing non-alphanumeric characters.
Since an empty string reads the same forward and backward, it is a palindrome.
```

**思路：**

双指针一个指头一个指尾，遇到不是alphanumeric的就跳过，在中间碰头

```java
class Solution {
    public boolean isPalindrome(String s) {
        int left = 0;
        int right = s.length() - 1;
        s = s.toLowerCase();
        while (left < right) {
            while (left < right && !isValid(s.charAt(left))) {
                left++;
            }
            while (left < right && !isValid(s.charAt(right))) {
                right--;
            }
            char leftChar = s.charAt(left);
            char rightChar = s.charAt(right);
            if (leftChar == rightChar) {
                left++;
                right--;
                continue;
            } else {
                return false;
            }
        }
        return true;
    }

    private boolean isValid(char c) {
        return ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'z'));
    }
}
```

### 242. Valid Anagram

**Example 1:**

```
Input: s = "anagram", t = "nagaram"
Output: true
```

**Example 2:**

```
Input: s = "rat", t = "car"
Output: false
```

**思路：**

用一个长度为26的array为HashMap，index为字母在26个字母中的位置，element存储每个字母出现的次数。

```java
class Solution {
    public boolean isAnagram(String s, String t) {
        int[] alphabet = new int[26];
        for (int i = 0; i < s.length(); i++) { 
            alphabet[s.charAt(i) - 'a']++;
        }
        for (int i = 0; i < t.length(); i++) {
            alphabet[t.charAt(i) - 'a']--;
        }
        for (int i : alphabet) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
```

### 409. Longest Palindrome

**Example 1:**

```
Input: s = "abccccdd"
Output: 7
Explanation: One longest palindrome that can be built is "dccaccd", whose length is 7.
```

**Example 2:**

```
Input: s = "a"
Output: 1
Explanation: The longest palindrome that can be built is "a", whose length is 1.
```

**思路：**

把出现一次的字母放进HashSet，出现两次的从HashSet里去掉，再把数量+2，如果结束后HashSet里还有东西，就可以放在Palindrome中间一位，所以数量再+1

```java
class Solution {
    public int longestPalindrome(String s) {
        HashSet<Character> set = new HashSet<>();
        StringBuilder sb = new StringBuilder();
        sb.append(s);
        int max = 0;
        lor (int i = 0; i < sb.length(); i++) {
            if (set.contains(sb.charAt(i))) {
                set.remove(sb.charAt(i));
                max += 2;
            } else {
                set.add(sb.charAt(i));
            }
        }
        if (!set.isEmpty()) {
            max += 1;
        }
        return max;
    }
}
```

### 76. Minimum Window Substring***

**Example 1:**

```
Input: s = "ADOBECODEBANC", t = "ABC"
Output: "BANC"
Explanation: The minimum window substring "BANC" includes 'A', 'B', and 'C' from string t.
```

**Example 2:**

```
Input: s = "a", t = "a"
Output: "a"
Explanation: The entire string s is the minimum window.
```

**Example 3:**

```
Input: s = "a", t = "aa"
Output: ""
Explanation: Both 'a's from t must be included in the window.
Since the largest window of s only has one 'a', return empty string.
```

**思路：**

这一题是滑动窗口的题目，在窗口滑动的过程中不断的包含字符串 T，直到完全包含字符串 T 的字符以后，记下左右窗口的位置和窗口大小。然后把窗口从左边缩小，直到不完全包含字符串T。再从右边把窗口放大，每次都不断更新这个符合条件的窗口和窗口大小的最小值。最后输出结果即可。

```java
class Solution {
    public String minWindow(String s, String t) {
        String result = "";
        HashMap<Character, Integer> tMap = new HashMap<>();
        for (int k = 0; k < t.length(); k++) {
            char curr = t.charAt(k);
            tMap.put(curr, tMap.getOrDefault(curr, 0) + 1);
        }

        HashMap<Character, Integer> sMap = new HashMap<>();
        int i = 0, j = 0, min = Integer.MAX_VALUE;
        int found = 0;
        while (j < s.length()) {
            if (found < t.length()) {
                if (tMap.containsKey(s.charAt(j))) {
                    sMap.put(s.charAt(j), sMap.getOrDefault(s.charAt(j), 0) + 1);
                    if (sMap.get(s.charAt(j)) <= tMap.get(s.charAt(j))) {
                        found++;
                    }
                }
                j++;
            }
            
            while (found == t.length()) {
                if (j - i < min) {  // 因为上面j++了，所以这里不是j - i + 1
                    min = j - i;
                    result = s.substring(i, j);
                }

                if (tMap.containsKey(s.charAt(i))) {
                    sMap.put(s.charAt(i), sMap.get(s.charAt(i)) - 1);
                    if (sMap.get(s.charAt(i)) < tMap.get(s.charAt(i))) {
                        found--;
                    }
                }
                i++;
            }
        }
        return result;
    }
}
```

### 8. String to Integer (atoi)

1. Read in and ignore any leading whitespace.
2. Check if the next character (if not already at the end of the string) is `'-'` or `'+'`. Read this character in if it is either. This determines if the final result is negative or positive respectively. Assume the result is positive if neither is present.
3. Read in next the characters until the next non-digit character or the end of the input is reached. The rest of the string is ignored.
4. Convert these digits into an integer (i.e. `"123" -> 123`, `"0032" -> 32`). If no digits were read, then the integer is `0`. Change the sign as necessary (from step 2).
5. If the integer is out of the 32-bit signed integer range `[-231, 231 - 1]`, then clamp the integer so that it remains in the range. Specifically, integers less than `-231` should be clamped to `-231`, and integers greater than `231 - 1` should be clamped to `231 - 1`.
6. Return the integer as the final result.

**思路：**

先去掉开头所有的空格，然后再判断下一个字符是否是'-'或'+'，是'-'就把最后的结果加上-号，是'+'就不变然后再判断后续所有字符是否是数字，判断的过程中不断更新result，如果遇到不是数字的字符就直接结束。

```java
class Solution {
  public int myAtoi(String str) {
    int len = str.length();
    if (len == 0){
        return 0;
    }
    int index = 0;
    while (index < len && str.charAt(index) == ' '){
        index++;
    }
    boolean isNegative = false;
    if (index < len) {
      if (str.charAt(index) == '-') {
        isNegative = true;
        index++;
      } else if (str.charAt(index) == '+'){
          index++;
      }
    }
    int result = 0;

    while (index < len && isDigit(str.charAt(index))) {
      int digit = str.charAt(index) - '0';
      if (result > (Integer.MAX_VALUE / 10) || (result == (Integer.MAX_VALUE / 10) && digit > 7)){
          return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
      }
      result = (result * 10) + digit;
      index++;
    }
    return isNegative ? -result : result;
  }
  
  private boolean isDigit(char ch) {
    return ch >= '0' && ch <= '9';
  }
}
```

### 438. Find All Anagrams in a String*

**Example 1:**

```
Input: s = "cbaebabacd", p = "abc"
Output: [0,6]
Explanation:
The substring with start index = 0 is "cba", which is an anagram of "abc".
The substring with start index = 6 is "bac", which is an anagram of "abc".
```

**Example 2:**

```
Input: s = "abab", p = "ab"
Output: [0,1,2]
Explanation:
The substring with start index = 0 is "ab", which is an anagram of "ab".
The substring with start index = 1 is "ba", which is an anagram of "ab".
The substring with start index = 2 is "ab", which is an anagram of "ab".
```

**思路：**

用一个map存储p中每个字母出现的次数

用另一个map存储s的滑动窗口中每个字母出现的次数

如果两个map里存储的一样，就输出滑动窗口的左边界index

滑动窗口每次左边界和右边界都+1

```java
class Solution {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> result = new ArrayList<>();
        if (s.length() < p.length()) {
            return result;
        }

        int Pmap[] = new int[26];
        int Smap[] = new int[26];
        for (int i = 0; i < p.length(); i++) {
            Pmap[p.charAt(i) - 'a']++;
            Smap[s.charAt(i) - 'a']++;
        }

        int start = 0;
        int end = p.length();
        if (Arrays.equals(Pmap, Smap)) {
            result.add(start);
        }

        while (end < s.length()) {
            Smap[s.charAt(start) - 'a']--;
            Smap[s.charAt(end) - 'a']++;
            start++;
            end++;
            
            if (Arrays.equals(Pmap, Smap)) {
                result.add(start);
            }
        }

        return result;
    }
}
```

### 49. Group Anagrams**

**Example 1:**

```
Input: strs = ["eat","tea","tan","ate","nat","bat"]
Output: [["bat"],["nat","tan"],["ate","eat","tea"]]
```

**Example 2:**

```
Input: strs = [""]
Output: [[""]]
```

**Example 3:**

```
Input: strs = ["a"]
Output: [["a"]]
```

**思路：**

Anagrams sort完之后应该是一样的，例如"eat"和"ate"，sort完之后都是"aet"

建立一个map，把sort过的string作为key，ArrayList\<String\>作为value，每次遇到sort完和key相同的string都存进value里的ArrayList

比如：

"aet": ["eat", "tea", "ate"]

"ant": ["nat", "tan"]

"abt": ["bat"]

```
class Solution {
    public List<List<String>> groupAnagrams(String[] strs) {
        HashMap<String, ArrayList<String>> map = new HashMap<>();
        for (int i = 0; i < strs.length; i++) {
            String curr = strs[i];
            char[] arr = curr.toCharArray();
            Arrays.sort(arr);
            String sorted = Arrays.toString(arr);
            if (map.containsKey(sorted)) {
                map.get(sorted).add(curr);
            } else {
                map.put(sorted, new ArrayList<>());
                map.get(sorted).add(curr);
            }
        }
        return new ArrayList<>(map.values());
    }
}
```

### 424. Longest Repeating Character Replacement***

**Example 1:**

```
Input: s = "ABAB", k = 2
Output: 4
Explanation: Replace the two 'A's with two 'B's or vice versa.
```

**Example 2:**

```
Input: s = "AABABBA", k = 1
Output: 4
Explanation: Replace the one 'A' in the middle with 'B' and form "AABBBBA".
The substring "BBBB" has the longest repeating letters, which is 4.
There may exists other ways to achieve this answer too.
```

**思路：**

用滑动窗口，每次循环右边界都++，并且判断当前窗口长度是否为最大，如果是则更新结果。

每次右边界++后，都要判断右边的元素加进窗口后，是否可以使窗口满足题目条件（替换k个字母可以让所有字母相同），如果满足，左边界不动，窗口最大长度变大。如果不满足，左边界++，窗口最大长度不变。

比如AABABBB, k = 1: 

直到窗口为AABA时，窗口一直都是满足题目条件的，循环记录了最大窗口长度为4

然后右边界++变为AABAB，不满足条件，所以左边界++变为ABAB。

然后右边界++变为ABABB，不满足条件，所以左边界++变为BABB。

此时BABB虽然满足条件，但是不需要判断和记录，因为之前窗口为AABA时已经记录了最大值4。

然后右边界++变为BABBB，满足条件，记录最大窗口长度为5。

**如何判断当前窗口是否满足条件：**

假设AABA, k = 1:

用一个变量maxFreq记录出现最多次的字母(A)出现的次数为3，因为maxFreq(3) + k(1) >= 当前窗口的长度(4)，满足条件

假设为AABAB, k = 1:

因为maxFreq(3) + k(1) < 当前窗口的长度(5)，所以不满足

假设为BABBB, k = 1:

此时出现最多次的字母变成了B，出现的次数为4，于是更新maxFreq为4

**如何记录每个字母出现的次数：**

用int[] map = new int[26]来记录每个字母出现的次数，如果当前新加进窗口的字母出现的次数超过maxFreq，则更新maxFreq

**为什么从AABA变成ABAB时，不把maxFreq从3更新成2：**

当窗口为AABA时，maxFreq=3，窗口长度=4，满足条件所以我们记录了4，于是我们接下来要找窗口长度=5

```java
class Solution {
    public int characterReplacement(String s, int k) {
        
        int[] map = new int[26];
        int maxLen = 0;
        int maxFreq = 0;
        int i = 0;

        for (int j = 0; j < s.length(); j++) {

            map[s.charAt(j) - 'A']++;
            maxFreq = Math.max(maxFreq, map[s.charAt(j) - 'A']);

            if (maxFreq + k < j - i + 1) {
                map[s.charAt(i) - 'A']--;
                i++;
            } else {
              	maxLen = Math.max(maxLen, j - i + 1);
            }
        }

        return maxLen;
    }
}
```

### 179. Largest Number**

**Example 1:**

```
Input: nums = [10,2]
Output: "210"
```

**Example 2:**

```
Input: nums = [3,30,34,5,9]
Output: "9534330"
```

**思路：**

先转换成string array

再根据`Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b))`来sort array

比如a = "3", b = "30", b + a = "303", a + b = "330":

如果左面的b + a小于右面的a + b，说明左面的a应该再右面的b之前：

303 < 330, 说明3应该在30之前

```java
class Solution {
    public String largestNumber(int[] nums) {
        String[] strs = new String[nums.length];
        for(int i = 0;i < nums.length; i++){
            strs[i] = String.valueOf(nums[i]);
        }
        Arrays.sort(strs, (a, b) -> (b + a).compareTo(a + b));
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            sb.append(str);
        }
        String result = sb.toString();
        return result.startsWith("0") ? "0" : result;
    }
}
```

### 