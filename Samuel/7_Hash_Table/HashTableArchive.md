# Hash Table

Java HashMap (stores key-value pair)

- put(key, value)
- get(key) -> value  重要，用于查找某key是否存在，并根据key找value
- remove(key)
- clear()
- size()
- keySet() -> set of keys

Java HashSet (stores only values)

- add()
- contains() -> boolean  重要，用于查找某元素是否存在
- remove()
- clear()
- size()

**什么时候使用哈希法**，当我们需要查询一个元素是否出现过，或者一个元素是否在集合里的时候，就要第一时间想到哈希法。

### 有效的字母异位词242

``````java
class Solution {
    public boolean isAnagram(String s, String t) {
        int[] record = new int[26];
        // toCharArray() turns a string into a char array
        // charAt(i) returns the char at ith or the string
        char[] sCharArray = s.toCharArray();
        char[] tCharArray = t.toCharArray();
        
        for(char c : sCharArray) {
            // using char as int, return its ASCII
            // when c == 'a', c - 'a' = 0
            record[c - 'a'] += 1;
        }
        for(char c : tCharArray) {
            record[c - 'a'] -= 1;
        }
        
        for(int i : record) {
            if (i != 0) {
                return false;
            }
        }
        return true;
    }
}
``````

Note:

O(n)

记忆：

.toCharArray()

.charAt()

考虑：

字母异位词中，每个字母的数量相同，所以只需要用hashTable记录26个字母的数量即可，每个字母按a:0, b:1, c:2, ...的方法hash即可

### 两个数组的交集349

``````java
class Solution {
    public int[] intersection(int[] nums1, int[] nums2) {
        
        HashSet<Integer> set1 = new HashSet<Integer>();
        HashSet<Integer> resSet = new HashSet<Integer>();
        
        for (int i : nums1) {
            set1.add(i);
        }
        for (int i : nums2) {
            if (set1.contains(i)) {
                resSet.add(i);
            }
        }
        
      	// convert a set to an array
        int[] output = new int[resSet.size()];
        int index = 0;
        for (int i : resSet) {
            output[index] = i;
            index ++;
        }
        
        return output;
    }
}
``````

Note:

O(n+m)

N/A

### 快乐数202

``````java
class Solution {
    public boolean isHappy(int n) {
        HashSet<Integer> record = new HashSet<Integer>();
        while (n != 1 && !record.contains(n)) {
            record.add(n);
            n = getSumOfSquaredDigits(n);
        }
        return n == 1;
    }
    
    private int getSumOfSquaredDigits(int n) {
        int res = 0;
        while (n != 0) {
            int temp = n % 10;
            res = res + temp * temp;
            n = n / 10;
        }
        return res;
    }
}
``````

Note:

runtime太难

记忆：

- 记住如何提取一个数的每个digit
- int n will have logn digits: for calculating runtime

### 两数之和1

``````java
class Solution {
    public int[] twoSum(int[] nums, int target) {
        HashMap<Integer, Integer> record = new HashMap<Integer, Integer>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (record.get(target - nums[i]) != null) {
                res[0] = record.get(target - nums[i]);
                res[1] = i;
                return res;
            }
            record.put(nums[i], i);
        }
        return null;
    }
}
``````

Note:

O(n)

考虑：

- 如果用HashSet的话，只能存array element value，不能获取index，所以要用HashMap
- HashMap的key为array element的value，value为index，因为我们要寻找的是array element的value所以把它作为key

### 四数相加454

``````java
class Solution {
    public int fourSumCount(int[] nums1, int[] nums2, int[] nums3, int[] nums4) {
        HashMap<Integer, Integer> record = new HashMap<Integer, Integer>();
        for (int i : nums1) {  // 记住这种for loop写法
            for (int j : nums2) {
                if(record.containsKey(i+j)) {
                    record.put(i+j, record.get(i+j) + 1);
                } else {
                    record.put(i+j, 1);
                }
            }
        }
        
        int res = 0;
        for (int i : nums3) {
            for (int j : nums4) {
                if (record.containsKey(-(i+j))) {
                    res += record.get(-(i+j));
                }
            }
        }
        return res;
    }
}
``````

Note:

O(n^2)

思路：其实就是另一个版本的两数之和

- 两数之和是[a, b, c, d]和[a, b, c, d]（它本身）中各有一项加起来为n，去除掉相同两项加起来为n的情况
- 四数相加是[a1, b1], [a2, b2], [a3, b3], [a4, b4]中各有一项加起来为n，可以简化为[a1+a2, a1+b2, b1+a2, b1+b2]和[a3+a4, a3+b4, b3+a4, b3+b4]中各有一项加起来为n

考虑：

- 为何不用HashSet: 因为前两个array的两数之和可能出现重复，对于HashSet来说不unique，所以用HashMap，key存两数之和，value存其出现次数

### 赎金信383

``````java
class Solution {
    public boolean canConstruct(String ransomNote, String magazine) {
        int[] record = new int[26];
        for (char c : magazine.toCharArray()) {
            record [c - 'a'] ++;
        }
        
        for (char c : ransomNote.toCharArray()) {
            record [c - 'a'] --;
            if (record[c - 'a'] < 0) {
                return false;
            }
        }
        return true;
    }
}
``````

Note:

思路：

- 使用int[26] array存取magazine中每个字母出现的次数
- 遍历ransom，在array中寻找是否存在，若存在即扣除一次

考虑：

- 为何不用HashMap是因为map的空间消耗大于array

### 三数之和15

``````java
class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> result = new ArrayList<>();  // List is abstract, 需要initialize成ArrayList<>
        Arrays.sort(nums);  // 记忆sort()
      
        for(int i = 0; i < nums.length; i++) {
            int left = i + 1;
            int right = nums.length - 1;
            
            if(i > 0 && nums[i] == nums[i-1]) {  // i > 0 防止 i - 1 < 0
                continue;
            }
            
            while(left < right){
                int sum = nums[i] + nums[left] + nums[right];
                if(sum == 0){
                    result.add(Arrays.asList(nums[i], nums[left], nums[right]));  // 记忆asList()
                    
                    // 此处为left和right的去重
                    // 注意要判断left < right
                    while(left < right && nums[left] == nums[left+1]){
                        left ++;
                    }
                    while(left < right && nums[right] == nums[right-1]){
                        right --;
                    }
                    left ++;
                    right --;
                }
                
                if (sum < 0) {
                    left ++;
                }
                
                if (sum > 0) {
                    right --;
                }
            }   
        }
        return result;
    }
}
``````

Note:

O(n^2)

记忆：

- 只有两数之和适合用Hash
- 三数之和用双指针

思路：

### 四数之和18

``````java
class Solution {
    public List<List<Integer>> fourSum(int[] nums, int target) {
        Arrays.sort(nums);
        List<List<Integer>> result = new ArrayList<>();
        for(int i = 0; i < nums.length; i++) {
            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }
            for (int j = i + 1; j < nums.length; j++) {
                if (j > i + 1 && nums[j] == nums[j-1]) {
                    continue;
                }
                int left = j + 1;
                int right = nums.length - 1;
                while (left < right) {
                    long sum = (long) nums[i] + nums[j] + nums[left] + nums[right];  // 记忆转换int为long的方法，以免sum超出int的范围
                    if (sum == target) {
                        result.add(Arrays.asList(nums[i], nums[j], nums[left], nums[right]));
                        while (left < right && nums[left] == nums[left + 1]) {
                            left ++;
                        }
                        while (left < right && nums[right] == nums[right - 1]) {
                            right --;
                        }
                        left ++;
                        right --;
                    }
                    if (sum < target) {
                        left ++;
                    }
                    if (sum > target) {
                        right --;
                    }
                }
            }
        }
        return result;
    }
}
``````

Note:

O(n^3)

