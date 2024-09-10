# Greedy

### 分发饼干455

``````java
class Solution {
    public int findContentChildren(int[] g, int[] s) {
        // 记忆：倒序排列array，但只能用在non-primitive type array上，int[]不能用
        // Arrays.sort(g, Collections.reverseOrder());
        // Arrays.sort(s, Collections.reverseOrder());

        // 记忆：对于int[]，只能用Arrays.sort()正序排列，并不存在倒序排列的方法，或者反转int[]的方法
        Arrays.sort(g);
        Arrays.sort(s);
        // 记录几个小孩被满足了
        int result = 0;
        // 从大到小loop小孩
        int j = s.length - 1;
        for (int i = g.length - 1; i >= 0; i--) {
            // 饼干没有用完 && 如果小孩能被饼干满足 
            // 记忆：把j >= 0的判断放在前面，不然后面的会超出范围
            if (j >= 0 && g[i] <= s[j]) {
                // 满足的小孩多了一个
                result++;
                // 饼干少了一个
                j--;
            }
            // 如果小孩无法被这个饼干满足，由于后面的饼干更小，也不可能满足这个小孩，所以跳过该小孩(i++)，但是不跳过该饼干(j不++)，因为这个饼干可能可以满足后面更小的小孩
        }
        return result;
    }
}
``````

思路：

对于每个饼干，我们要找到小于等于它但是最接近它的小孩

对于每个小孩，我们要找到大于等于他但是最接近他的饼干

可以外层loop饼干或小孩，内层loop另一个，这样runtime就是O(n^2)

有没有办法省去内层的loop从而节省runtime呢？

可以，这样就既要sort饼干，也要sort小孩：

假设饼干和小孩都从小到大排列，每次配对成功饼干和小孩，就移动两个array的index，这样不需要两个for loop：

饼干：3，2，1

小孩：4，2

对于第一个饼干3，满足不了小孩4，所以跳过该小孩，因为后续的饼干都比3小，比饼干3更不可能满足小孩4

但是不跳过该饼干，因为饼干3还能满足后续比4小的小孩

所以匹配成功，则同时移动两个index

匹配不成功，则只移动小孩的index

对于这种情况，外层for loop只能是小孩，因为小孩的index每次必移动，所以它应该是用for loop来控制。饼干的index不是每次必移动，所以在for loop内用if判断小孩和饼干是否配对，配对成功则移动饼干的index，不成功则不移动

### 摆动序列376

``````java
class Solution {
    public int wiggleMaxLength(int[] nums) {
        int count = nums.length;
        if (count <= 1) {
            return count;
        }
        // pre = 1: last was ascending; pre = -1: last was descending
        int pre = 0;
        // cur = 1: this is ascending; cur = -1: this is descending
        int cur = 0;
        for (int i = 1; i < nums.length; i++) {
            // 如果当前值大于上一个值
            if (nums[i] > nums[i - 1]) {
                // 1代表增加
                cur = 1;
                // 并且上一个值大于上上一个值，则上上，上和当前值是单调递增
                if (cur == pre) {
                    // 删除中间的，即上一个值
                    count--;
                // 如果不是单调递增
                } else {
                    // 不删任何东西，并且更新pre为增加
                    pre = cur;
                }
            // 如果当前值小于上一个值，和之前的if中的内容相反
            } else if (nums[i] < nums[i - 1]) {
                cur = -1;
                if (cur == pre) {
                    count--;
                } else {
                    pre = cur;
                }
            // 如果当前值等于上一个值
            } else {  // nums[i] == nums[i - 1]
                // 删除上一个值
                count--;
                // 不更新pre，保留之前pre的状态，用来判断和下一个值是否形成3个值单调递增或者递减
            }
        }
        return count;
    }
}
``````

思路：

![20201124174327597](https://code-thinking-1253855093.file.myqcloud.com/pics/20201124174327597.png)

画图！删除超过3个值的单调递增坡上中间的节点，方法是判断当前斜坡和之前斜坡是否一样，如果是一样的斜坡就删除中间的节点。

![image-20230716153250031](/Users/samuel/Library/Application Support/typora-user-images/image-20230716153250031.png)

如果是平的坡，则一定要删除，比如2和5

如果是平的坡后有斜坡，那么判断它是否与平坡之前的坡一样，比如4-5和6-7

如果一样，则删除6节点

### 最大子序和53

``````java
class Solution {
    public int maxSubArray(int[] nums) {
        int sum = 0;
        int result = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            sum = sum + nums[i];
            result = Math.max(result, sum);
            if (sum < 0) {
                sum = 0;
            }
        }
        return result;
    }
}
``````

思路：

如果当前总和是负的，无论后面怎么加都会拉低后面的总和，所以就不能以当前的值开始。

就算后面一位是负的，如果加上后面一位还是正的，就继续往后加，因为有可能后面会出现一个绝对值更大的正值抵消掉负值。

用一个变量记录历史最大和，最后输出。

![53.最大子序和](https://code-thinking.cdn.bcebos.com/gifs/53.%E6%9C%80%E5%A4%A7%E5%AD%90%E5%BA%8F%E5%92%8C.gif)

### 买卖股票的最佳时机二122

``````java
class Solution {
    public int maxProfit(int[] prices) {
        int profit = 0;
        int pre = -1;
        for (int i = 1; i < prices.length; i++) {
            int diff = prices[i] - prices[i - 1];
            if (diff > 0) {
                profit += diff;
            }
        } 
        return profit;
    }
}
``````

思路：

画折线图，发现只要把所有相邻两天正的利润相加即可。

### 跳跃游戏55

``````java
class Solution {
    public boolean canJump(int[] nums) {
        if (nums.length == 1) {
            return true;
        }
        int check = 0;
        for (int i = nums.length - 2; i >= 0 ; i--) {
            if (nums[i] <= check) {
                check++;
            } else {
                check = 0;
            }
        }
        return check == 0;
    }
}
``````

思路：

只要保证不会跳到0上，就一定能到终点。但是最后一位可以是0，因为就算跳上来也已经到终点了。

所以：3, 2, 1, 0, x 不行，0前每一位都跳不过0

1. 从数组倒数第二位往前遍历：

2. 如果遍历完整个数组，从数组的开始，到0那一位，每一位都满足值<=到0的距离，则跳不过0

3. 相反，只要0前面有一位不满足这个条件，就跳得过这个0，这样就要继续往前找其他的0，重复2，3

### 跳跃游戏二45

``````java
class Solution {
    public int jump(int[] nums) {
        // The starting range of the first jump is [0, 0]
        int answer = 0;
        int curEnd = 0;
        int curFar = 0;
        
        for (int i = 0; i < nums.length - 1; i++) {
            // Update the farthest reachable index of this jump.
            curFar = Math.max(curFar, i + nums[i]);

            // If we finish the starting range of this jump,
            // Move on to the starting range of the next jump.
            if (i == curEnd) {
                answer++;
                curEnd = curFar;
            }
        }
        
        return answer;
    }
}
``````

思路：看Editorial

### K次取反后最大化的数组和1005

``````java
class Solution {
    public int largestSumAfterKNegations(int[] nums, int k) {
        Arrays.sort(nums);
        int sum = 0;
        int smallest = Integer.MAX_VALUE;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] < 0) {
                if (k > 0) {
                    sum -= nums[i];
                    k--;
                    if (-nums[i] < smallest) {
                        smallest = -nums[i];
                    }
                } else {
                    sum += nums[i];
                }
            } else {
                if (nums[i] < smallest) {
                    smallest = nums[i];
                }
                sum += nums[i];
            }
        }
        if (k % 2 == 1) {
            return sum - 2 * smallest;
        } else {
            return sum;
        }

    }
}
``````

思路：先从小到大排列，然后从小到大尽量全部取反负数，如果负数全部取反后K还有剩余，此时数组中已经全部都是正数，则找到最小的正数A。如果K是偶数，可以重复对一个数取反最后保持总和不变。如果K是奇数，则对最小正数A取反，总和减去2A。

### 加油站134

``````java
class Solution {
    public int canCompleteCircuit(int[] gas, int[] cost) {
        int sum = 0;
        int curSum = 0;
        int start = 0;
        for (int i = 0; i < gas.length; i++) {
            int gain = gas[i] - cost[i];
            sum += gain;
            curSum += gain;
            if (curSum < 0) {
                curSum = 0;
                start = i + 1;
            }
        }
        if (sum >= 0) {
            return start;
        } else {
            return -1;
        }
    }
}
``````

思路：

总体思路：

如果整条路径gas总和-cost总和>=0，说明一定能到终点，因为：

假设我们累加gas-cost，每遇到总和为负值的情况，就分一个区间，这样最后把路径分为了n个区间：

前n-1个区间的gas-cost总和全都<0，因为这是我们分区间的方法。那么最后一个区间的总和一定大于的前面n-1个区间总和的绝对值。因为：前n-1总和 + 最后一个区间总和 >= 0。

所以，把最后一个区间的第一个加油站作为起始点则一定能走完一圈。

具体思路：

从第一个加油站开始累加gas-cost到curSum上，如果到达索引i发现curSum变成负数，代表从curSum为0时的那一位到现在这一位的区间总和是负数，那么说明起始点不可能在这个区间，因为无论起始点在区间的哪里，遇到最后一位负数，总会抵消掉前面累积的正数

该区间的一个例子如下：

｜[sum>=0的一组数(2，-1，-1)]，sum<0的一个数(-1)｜：该区间总和为负

Q：为何一定是sum>=0的一组数加sum<0的一个数？

A：如果前面一组数sum<0，那么区间的结尾就会在前面一组数中，而不是索引为i的这一位。

然后清空curSum，继续寻找下一个总和为负的区间，同时更新起点为i+1

用上面的方法遍历完数组后，如果整条路径总和>=0，则输出最新的起点。

如果整条路径总和<0，说明无法找到起点。

### 分发糖果135

``````java
class Solution {
    public int candy(int[] ratings) {
        int[] candy = new int[ratings.length];
        candy[0] = 1;
        candy[ratings.length - 1] = 1;
        for (int i = 1; i < ratings.length; i++) {
            if (ratings[i] > ratings[i - 1]) {
                int moreCandy = candy[i - 1] + 1;
                candy[i] = moreCandy;
            } else {
                candy[i] = 1;
            }
        }
        for (int i = ratings.length - 2; i >= 0; i--) {
            if (ratings[i] > ratings[i + 1]) {
                int moreCandy = candy[i + 1] + 1;
                candy[i] = Math.max(moreCandy, candy[i]);
            } else {
                candy[i] = Math.max(candy[i], 1);
            }
        }
        int sum = 0;
        for (int i = 0; i < candy.length; i++) {
            sum += candy[i];
        }
        return sum;
    }
}
``````

思路：

第一位candy为1，先从左到右比较孩子

- 只要右孩子比左孩子大，则右孩子candy=左孩子candy + 1
- 右孩子不比左孩子大，则右孩子candy = 1

再最后一位candy为1，从右到左用相同方法比较孩子

最后两种方法得出的candy值，每一位取更大的，这样可以保证每一个小孩得分比左右高的时候，既比右边孩子糖多也比左边孩子糖多

![135.分发糖果](https://code-thinking-1253855093.file.myqcloud.com/pics/20201117114916878.png)

### 柠檬水找零860

``````java
class Solution {
    public boolean lemonadeChange(int[] bills) {
        int[] money = new int[2];
        for (int i = 0; i < bills.length; i++) {
            if (bills[i] == 5) {
                money[0]++;
            } else if (bills[i] == 10) {
                money[1]++;
                if (money[0] >= 0) {
                    money[0]--;
                } else {
                    return false;
                }
            } else if (bills[i] == 20) {
                if (money[1] >= 1 && money[0] >= 1) {
                    money[0]--;
                    money[1]--;
                } else if (money[0] >= 3) {
                    money[0] = money[0] - 3;
                } else {
                    return false;
                }
            }
        }
        return true;
    }
}
``````

思路：只有5和10可用于找零，遇到20的顾客，优先找10，如果没有10再找5，因为5更万能。

### 根据身高重建队列406

``````java
class Solution {
    public int[][] reconstructQueue(int[][] people) {
        Arrays.sort(people, (a, b) -> {  // a, b是people中的两个元素
            if (a[0] == b[0]) {  // 如果这两个元素的h一样
                return a[1] - b[1];  // 则根据k升序排列
            }
            return b[0] - a[0];  // 根据h降序排列
        });

        LinkedList<int[]> queue = new LinkedList<>();

        for (int[] person : people) {
            queue.add(person[1], person);  // 加入到k位
        }

        return queue.toArray(new int[people.length][2]);
    }
}
``````

思路：

回归本题，整个插入过程如下：

排序完的people： [[7,0], [7,1], [6,1], [5,0], [5,2]，[4,4]]

插入的过程：

- 插入[7,0]：[[7,0]]
- 插入[7,1]：[[7,0],[7,1]]
- 插入[6,1]：[[7,0],[6,1],[7,1]]
- 插入[5,0]：[[5,0],[7,0],[6,1],[7,1]]
- 插入[5,2]：[[5,0],[7,0],[5,2],[6,1],[7,1]]
- 插入[4,4]：[[5,0],[7,0],[5,2],[6,1],[4,4],[7,1]]

此时就按照题目的要求完成了重新排列。

### 用最少数量的箭引爆气球452

``````java
class Solution {
    public int findMinArrowShots(int[][] points) {
      
        Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]));  // 记忆：用Integer内置比较方法不会溢出
        int count = 1;
        int curEnd = points[0][1];
        System.out.println(curEnd);
        for (int i = 1; i < points.length; i++) {
            if (points[i][0] > curEnd) {
                count++;
                curEnd = points[i][1];
            } else if (points[i][1] < curEnd) {
                curEnd = points[i][1];
            }
        }
        return count;
    }
}
``````

思路：

从小到大排列气球的左起始点。遍历气球的同时，如果遇到不重叠的气球就count++，同时更新右边界值为新的气球的右边界。如果遇到重叠的气球，并且重叠的气球右边界小于当前右边界值，更新右边界值为重叠的气球右边界。

### 无重叠区间435

``````java
class Solution {
    public int eraseOverlapIntervals(int[][] intervals) {
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));  // 记忆：用Integer内置比较方法不会溢出
        int count = 1;
        int curEnd = intervals[0][1];
        System.out.println(curEnd);
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] >= curEnd) {  // 这里改成>=，因为[1,2]和[2,3]算作不重叠
                count++;
                curEnd = intervals[i][1];
            } else if (intervals[i][1] < curEnd) {
                curEnd = intervals[i][1];
            }
        }
        return intervals.length - count;
    }
}
``````

思路：总区间的数量减去上一题的答案

### 划分字母区间763

``````java
class Solution {
    public List<Integer> partitionLabels(String s) {
        List<Integer> list = new LinkedList<>();
        int[] edge = new int[26];
        char[] chars = s.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            edge[chars[i] - 'a'] = i;
        }
        int max = 0;
        int last = -1;
        for (int i = 0; i < chars.length; i++) {
            max = Math.max(edge[chars[i] - 'a'], max);
            if (max == i) {
                list.add(i - last);
                last = i;
            }
        }
        return list;
    }
}
``````

思路：

先用一个for loop记录下字符串中所有出现过的字母最后一次出现的位置，记录在一个26位长度的array中

再用一个for loop遍历字符串，遇到每个字符就问当前字符最后一次出现的位置在哪里，把最远处不断更新记录在max变量中，直到当前的字符的位置就是之前每个字符最后一次出现位置的最远处，即找到了分界点。

比如：

01234567，这8个位置对应

abbabccc，每个字符分别出现的最远处是

34434777，我们可以发现，前5位的最远处位置是4，在遍历至位置4遇到了b的最远处位置4，这时说明当前区间(abbab)可以被分界了

### 合并区间56

``````java
class Solution {
    public int[][] merge(int[][] intervals) {
        List<int[]> list = new LinkedList<>();
        if (intervals.length == 1) {
            return intervals;
        }
        Arrays.sort(intervals, (a, b) -> Integer.compare(a[0], b[0]));
        int end = intervals[0][1];
        int start = intervals[0][0];
        for (int i = 1; i < intervals.length; i++) {
            if (intervals[i][0] > end) {
                list.add(new int[]{start, end});
                start = intervals[i][0];
            }
            end = Math.max(end, intervals[i][1]);
            if (i == intervals.length - 1) {
                list.add(new int[]{start, end});
            }
        }
        return list.toArray(new int[list.size()][2]);
    }
}
``````

思路：

和452，435类似

先记录第一个区间的start和end

遇到重叠的区间，如果有更大的end值，就更新end

遇到非重叠的区间，就把上次遍历后未更新的[start, end]加入到linked list中，再更新成当前区间的start和end

遇到最后一个区间直接加入[start, end]到linked list中，因为不会有下一次遍历了

### 单调递增的数字

``````java
class Solution {
    public int monotoneIncreasingDigits(int n) {
        int last = 11;
        int num = n;
        int cur = 1;
        int result = 0;
        while (num > 0) {
            int digit = num % 10;
            if (digit > last) {
                digit--;
                result = digit * cur + cur - 1;
            } else {
                result += digit * cur;
            }
            num = num / 10;
            last = digit;
            cur = cur * 10;
        }
        return result;
    }
}
``````

思路：

代码随想录里写的：

例如：98，一旦出现strNum[i - 1] > strNum[i]的情况（非单调递增），首先想让strNum[i - 1]--，然后strNum[i]给为9，这样这个整数就是89，即小于98的最大的单调递增整数。

自己的思路：

首先要知道如何从个位开始提取一个数的每一位：

比如123456789：

123456789 % 10 = 9

123456789 / 10 = 12345678, 12345678 % 10 = 8

12345678 / 10 = 1234567, 1234567 % 10 = 7

......

如果提取出的n位（e.g.百位）> 上一次提取出的n/10位（e.g.十位）

​	把n位的数字-1，并且把n位之后的数字全部变成9

​	例子：911 -> 899

如果n位不>上一次提取出的n/10位

​	把n位的数字*n (百位就乘以100)，并且把它累加到result上