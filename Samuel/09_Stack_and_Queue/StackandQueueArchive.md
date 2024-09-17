# Stack and Queue

## 

``````java
Stack<Integer> stack = new Stack<>();
stack.push(42);
stack.peek();  // return 42, without popping 42
stack.pop();  // pop and return 42
stack.isEmpty();  // return true

Queue<Integer> queue = new LinkedList<>();
queue.add(42);
queue.peek();  // return 42, without removing 42
queue.remove();  // remove and return 42, poll() does the same except it returns null instead of an exception when the queue is empty
queue.isEmpty();  // return true
``````

Stack: 一头可进可出

Queue: 两头一进一出

Deque (Double-ended queue): 两头都可进可出

可用Deque实现Stack和Queue

Priority Queue: 元素按大小排序的Queue

### 用栈实现队列232

``````java
class MyQueue {
    
    Stack<Integer> stackIn;
    Stack<Integer> stackOut;
    
    public MyQueue() {
        stackIn = new Stack<>();
        stackOut = new Stack<>();
    }
    
    public void push(int x) {
        stackIn.push(x);
    }
    
    public int pop() {
        // 当StackOut为空时，把StackIn的所有元素放进StackOut
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
            stackOut.push(stackIn.pop());
            }
        }
        // pop stackOut顶端元素（StackOut不为空时，直接pop其顶端元素）
        return stackOut.pop();
    }
    
    public int peek() {
        if (stackOut.isEmpty()) {
            while (!stackIn.isEmpty()) {
            stackOut.push(stackIn.pop());
            }
        }
        return stackOut.peek();
    }
    
    public boolean empty() {
        return stackIn.isEmpty() && stackOut.isEmpty();
    }
}

/**
 * Your MyQueue object will be instantiated and called as such:
 * MyQueue obj = new MyQueue();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.peek();
 * boolean param_4 = obj.empty();
 */
``````

思路：

使用两个Stack实现模拟Queue的效果，一个Stack负责push，另一个负责pop

### 用队列实现栈225

``````java
class MyStack {

    Queue<Integer> queue;
    Queue<Integer> anotherQueue;
    
    public MyStack() {
        //Queue是Interface，要用LinkedList作为subclass
        queue = new LinkedList<>();
        anotherQueue = new LinkedList<>();
    }
    
    public void push(int x) {
        // 先把queue里的全放进anotherQueue
        while (!queue.isEmpty()) {
            anotherQueue.add(queue.remove());
        }
        // 把要push的放进空的queue的首位
        queue.add(x);
        // 再把anotherQueue中的全放回queue
        while (!anotherQueue.isEmpty()) {
            queue.add(anotherQueue.remove());
        }
    }
    
    public int pop() {
        return queue.remove();
    }
    
    public int top() {
        return queue.peek();
    }
    
    public boolean empty() {
        return queue.isEmpty();
    }
}

/**
 * Your MyStack object will be instantiated and called as such:
 * MyStack obj = new MyStack();
 * obj.push(x);
 * int param_2 = obj.pop();
 * int param_3 = obj.top();
 * boolean param_4 = obj.empty();
 */
``````

思路：

需要始终使queue保持刚刚pushed in的元素的位置在最前面。需要用另一个queue暂存前面所有的元素，等该元素被pushed in后，再把暂存的元素加到后面

### 有效的括号20

``````java
class Solution {
    public boolean isValid(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            // 如果该字符为左括号：
            if (ch == '(') {
                stack.push(')');
            } else if (ch == '{') {
                stack.push('}');
            } else if (ch == '[') {
                stack.push(']');
            // 如果该字符为右括号：
            // 注意需要判断stack是否为空，否则peek()会return exception。例子：')'
            } else if (!stack.isEmpty() && ch == stack.peek()) {
                stack.pop();
            // 如果stack为空，说明有多余的右括号，例子：'()]'
            } else {
                return false;
            }
        }
        // 如果stack清零，说明每个括号都找到了另一半
        return stack.isEmpty();
    }
}
``````

O(n)

思路：用stack存入还未配对的左括号，等右括号进来时，如果stack顶端是对应的左括号，则可以配对，一起离开stack，如果最后stack可被成功清零，说明括号组合是有效的

### 删除字符串中的所有相邻重复项1047

``````java
class Solution {
    public String removeDuplicates(String s) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if (!stack.isEmpty() && ch == stack.peek()) {
                stack.pop();
            } else {
                stack.push(ch);
            }
        }
        String str = "";
        while (!stack.isEmpty()) {
            str = stack.pop() + str;  // 记忆这种建立string的方式
        }
        return str;
    }
}
``````

O(n^2)

思路和上一题一样

### 逆波兰表达式求值150

``````java
class Solution {
    public int evalRPN(String[] tokens) {
        // 因为+-*/不需要放进stack，所以用一个Integer stack
        Stack<Integer> stack = new Stack<>();
        for (String token: tokens) {
            int second;
            int first;
            int result;
            if ("+".equals(token)) {
                second = stack.pop();
                first = stack.pop();
                result = first + second;
                stack.push(result);
            } else if ("-".equals(token)) {
                second = stack.pop();
                first = stack.pop();
                result = first - second;
                stack.push(result);
            } else if ("*".equals(token)) {
                second = stack.pop();
                first = stack.pop();
                result = first * second;
                stack.push(result);
            } else if ("/".equals(token)) {
                second = stack.pop();
                first = stack.pop();
                result = first / second;
                stack.push(result);
            } else {
                stack.push(Integer.valueOf(token));  // 记忆这个string转int的方法
            }
        }
        return stack.pop();
    }
}
``````

思路：所有关于括号运算的题都可以试试使用stack，思路和前两题相似

### 滑动窗口最大值239

// TODO: 用了Monotonic Queue单调队列

### 前k个高频元素347

``````java
class Solution {
    public int[] topKFrequent(int[] nums, int k) {
        Map<Integer,Integer> map = new HashMap<>();  // key为数组元素值,val为对应出现次数
        for(int num:nums){
            map.put(num,map.getOrDefault(num,0)+1);  // getOrDefault return key‘s value, if no key, use default value
        }
        // 在优先队列中存储二元组[num, cnt],cnt表示元素值num在数组中的出现次数
        PriorityQueue<int[]> pq = new PriorityQueue<>((x, y) -> y[1] - x[1]);  // （x, y) -> y[1] - x[1]是一个comparator，代表用存入的[num, cnt]的cnt进行从大到小排序，括号内什么都不加默认是从小到大
        for(Map.Entry<Integer,Integer> entry : map.entrySet()){
            pq.add(new int[]{entry.getKey(),entry.getValue()});  // new int[]{这里面填array中的东西}
        }
        int[] ans = new int[k];
        for(int i=0; i<k; i++){  // 依次从队头弹出k个,就是出现频率前k高的元素
            ans[i] = pq.poll()[0];  // poll()弹出队列头的元素
        }
        return ans; 
    }
}
``````

注意：学习如何构建map和priorityQueue

思路：所有选出前k个最大的/最小的东西都可以使用Priority Queue，因为Priority Queue在构建的时候会自动进行排序

Priority Queue的作用是把元素按从大到小或从小到大放入一个Queue，这题中把每个元素的出现频率从大到小放入priorityQueue，再输出前k个，思路不难，重点在于想到要使用Priority Queue

### 155. Min Stack**

Design a stack that supports push, pop, top, and retrieving the minimum element in constant time.

Implement the `MinStack` class:

- `MinStack()` initializes the stack object.
- `void push(int val)` pushes the element `val` onto the stack.
- `void pop()` removes the element on the top of the stack.
- `int top()` gets the top element of the stack.
- `int getMin()` retrieves the minimum element in the stack.

You must implement a solution with `O(1)` time complexity for each function.

 

**Example 1:**

```
Input
["MinStack","push","push","push","getMin","pop","top","getMin"]
[[],[-2],[0],[-3],[],[],[],[]]

Output
[null,null,null,null,-3,null,0,-2]

Explanation
MinStack minStack = new MinStack();
minStack.push(-2);
minStack.push(0);
minStack.push(-3);
minStack.getMin(); // return -3
minStack.pop();
minStack.top();    // return 0
minStack.getMin(); // return -2
```

**思路：**
用两个stack，一个和正常的stack一样保存数字，一个minStack用于保存正常stack中相应位置的每个数字往stack内部范围的最小值，比如：

stack: 内 7342650 外

minStack: 内 7332220 外

在push和pop的时候，两个stack同时加入或者弹出数字，这样，getMin时只要return minStack最外面的数字即是min

注意：

不要用一个global的min变量来保存当前最小值。比如：

`int min = Integer.MAX_VALUE;`

如果stack 7342, minStack 7332，pop了2变成734和733，如果push 3会变成7343和7332，而不是7343和7333，因为此时min仍为2。

需要在push的时候检查，当前push进的值是否小于minStack最外面的值，如果小，则把当前push的值加入minStack，如果大，则重复加入minStack最外面的值。所以此时应该加入733最外面的3变为7333

```java
class MinStack {
    Stack<Integer> stack;
    Stack<Integer> minStack;

    public MinStack() {
        stack = new Stack<>();
        minStack = new Stack<>();
    }
    
    public void push(int val) {
        stack.push(val);

        if (minStack.isEmpty()) {
            minStack.push(val);
        } else {
            if (val < minStack.peek()) {
                minStack.push(val);
            } else {
                minStack.push(minStack.peek());
            }
        }

    }
    
    public void pop() {
        stack.pop();
        minStack.pop();
    }
    
    public int top() {
        return stack.peek();
    }
    
    public int getMin() {
        return minStack.peek();
    }
}

/**
 * Your MinStack object will be instantiated and called as such:
 * MinStack obj = new MinStack();
 * obj.push(val);
 * obj.pop();
 * int param_3 = obj.top();
 * int param_4 = obj.getMin();
 */
```

### 844. Backspace String Compare

**Example 1:**

```
Input: s = "ab#c", t = "ad#c"
Output: true
Explanation: Both s and t become "ac".
```

**Example 2:**

```
Input: s = "ab##", t = "c#d#"
Output: true
Explanation: Both s and t become "".
```

**Example 3:**

```
Input: s = "a#c", t = "b"
Output: false
Explanation: s becomes "c" while t becomes "b".
```

**思路：**

简单

```java
class Solution {
    public boolean backspaceCompare(String s, String t) {
        Stack<Character> stackS = new Stack<>();
        Stack<Character> stackT = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == '#' && !stackS.isEmpty()) {
                stackS.pop();
            } else if (c == '#') {
                continue;
            } else {
                stackS.push(c);
            }
        }
        for (char c : t.toCharArray()) {
            if (c == '#' && !stackT.isEmpty()) {
                stackT.pop();
            } else if (c == '#') {
                continue;
            } else {
                stackT.push(c);
            }
        }
        return String.valueOf(stackS).equals(String.valueOf(stackT));
    }
}
```

### 394. Decode String*

Given an encoded string, return its decoded string.

The encoding rule is: `k[encoded_string]`, where the `encoded_string` inside the square brackets is being repeated exactly `k` times. Note that `k` is guaranteed to be a positive integer.

You may assume that the input string is always valid; there are no extra white spaces, square brackets are well-formed, etc. Furthermore, you may assume that the original data does not contain any digits and that digits are only for those repeat numbers, `k`. For example, there will not be input like `3a` or `2[4]`.

The test cases are generated so that the length of the output will never exceed `105`.

 

**Example 1:**

```
Input: s = "3[a]2[bc]"
Output: "aaabcbc"
```

**Example 2:**

```
Input: s = "3[a2[c]]"
Output: "accaccacc"
```

**Example 3:**

```
Input: s = "2[abc]3[cd]ef"
Output: "abcabccdcdcdef"
```

**思路：**

先往stack里push所有的东西，遇到反括号']'则把它与正括号之间的内容提取出来，然后再把正括号前的数字提取出来，把数字*内容再push回stack，最后把stack里的所有东西放进string builder里，在变成string输出

```java
class Solution {
    public String decodeString(String s) {
        Stack<Character> stack = new Stack<>();
        for (char c : s.toCharArray()) {
            if (c == ']') {

                StringBuilder sb = new StringBuilder();
                while (stack.peek() != '[') {
                    sb.append(stack.pop());
                }
                sb.reverse();

                stack.pop();  // pop the '['

                StringBuilder numSb = new StringBuilder();
                while (!stack.isEmpty() && stack.peek() >= '0' && stack.peek() <= '9') {
                    numSb.append(stack.pop());
                }
                numSb.reverse();
                int num = Integer.parseInt(numSb.toString());

                for (int i = 0; i < num; i++) {
                    for (int j = 0; j < sb.length(); j++) {
                        stack.push(sb.charAt(j));
                    }
                }
            } else {
                stack.push(c);
            }
        }
        
        StringBuilder res = new StringBuilder();
        while (!stack.isEmpty()) {
            res.append(stack.pop());
        }
        res.reverse();
        return res.toString();
    }
}
```

### 735. Asteroid Collision**

We are given an array `asteroids` of integers representing asteroids in a row.

For each asteroid, the absolute value represents its size, and the sign represents its direction (positive meaning right, negative meaning left). Each asteroid moves at the same speed.

Find out the state of the asteroids after all collisions. If two asteroids meet, the smaller one will explode. If both are the same size, both will explode. Two asteroids moving in the same direction will never meet.

 

**Example 1:**

```
Input: asteroids = [5,10,-5]
Output: [5,10]
Explanation: The 10 and -5 collide resulting in 10. The 5 and 10 never collide.
```

**Example 2:**

```
Input: asteroids = [8,-8]
Output: []
Explanation: The 8 and -8 collide exploding each other.
```

**Example 3:**

```
Input: asteroids = [10,2,-5]
Output: [10]
Explanation: The 2 and -5 collide resulting in -5. The 10 and -5 collide resulting in 10.
```

**思路：**

正数向右撞，负数向左撞

用stack存左边的数，每次右边新来一个数`asteroid`就判断会不会撞

如果stack空空，asteroid不管怎样都放进stack

如果stack不空：`peek = stack.peek()`

​	1. 如果peek < 0，或者asteroid > 0，说明peek往左撞，asteroid往右撞，他们不会撞，所以`stack.push(asteroid)`

​	2. 如果peek > 0，并且asteroid < 0：

​		2.1. 如果peek = -asteroid，说明他们互相抵消，`stack.pop()`，看下一个asteroid

​		2.2. 如果abs(peek) > abs(asteroid)，说明peek把asteroid撞没，不pop也不push

​		2.3. 如果abs(peek) < abs(asteroid)，说明asteroid把peek撞没，`stack.pop()`，

​			2.3.1. 如果stack空空，没有新的peek用来比较，`stack.push(asteroid)`

​			2.3.2. 如果stack不空，保留这个asteroid，并且继续看新的peek会是怎样，回到1和2的判断

```java
class Solution {
    public int[] asteroidCollision(int[] asteroids) {
        Stack<Integer> stack = new Stack<>();
        for (int asteroid : asteroids) {

            if (stack.isEmpty()) {
                stack.push(asteroid);
            } else {
                while(true) {
                    int peek = stack.peek();
                    if (peek < 0 || asteroid > 0) {  // -2, 2
                        stack.push(asteroid);
                        break;
                    } else if (peek == -asteroid) {  // 2, -2
                        stack.pop();
                        break;
                    } else if (peek > -asteroid) {  // 2, -1
                        break;
                    } else if (peek < -asteroid) {  // 2, -3
                        stack.pop();
                        if (stack.isEmpty()) {
                            stack.push(asteroid);
                            break;
                        }
                    }
                }
            }
        }

        int[] result = new int[stack.size()];
        for (int i = stack.size() - 1; i >= 0; i--) {
            result[i] = stack.pop();
        }
        return result;
    }
}
```

### 227. Basic Calculator II*

Given a string `s` which represents an expression, *evaluate this expression and return its value*. 

The integer division should truncate toward zero.

You may assume that the given expression is always valid. All intermediate results will be in the range of `[-231, 231 - 1]`.

**Note:** You are not allowed to use any built-in function which evaluates strings as mathematical expressions, such as `eval()`.

 

**Example 1:**

```
Input: s = "3+2*2"
Output: 7
```

**Example 2:**

```
Input: s = " 3/2 "
Output: 1
```

**Example 3:**

```
Input: s = " 3+5 / 2 "
Output: 5
```

**思路：**

先去除所有空格：`s = s.replaceAll("\\s", "");`

然后把String换成Char Array，for loop每个char：

如果是数字，则把currNum * 10加上该数字 （currNum初始值=0，用于计算和存储当前的数字，因为有可能会有超过1位的数字出现）

如果不是数字（或者上面的数字是最后一位），看上个operator是什么：

​	如果是+或-：把currNum或-currNum push进stack

​	如果是*或/：把stack.pop()的数和currNum相乘或相除

​	然后把当前非数字的这一位（即下一个operator）存入operator变量，用作后续的判断，currNum归零。

```java
class Solution {
    public int calculate(String s) {
        s = s.replaceAll("\\s", "");
        char[] charArr = s.toCharArray();
        Stack<Integer> stack = new Stack<>();

        int currNum = 0;
        char operator = '+';

        for (int i = 0; i < charArr.length; i++) {
            char curr = charArr[i];

            if (curr >= '0' && curr <= '9') {
                currNum = currNum * 10 + curr - '0';
            }
            if (curr < '0' || curr > '9' || i == charArr.length - 1) {
                if (operator == '+') {
                    stack.push(currNum);
                } else if (operator == '-') {
                    stack.push(-currNum);
                } else if (operator == '*') {
                    stack.push(stack.pop() * currNum);
                } else if (operator == '/') {
                    stack.push(stack.pop() / currNum);
                }
                operator = curr;
                currNum = 0;
            }
        }

        int result = 0;
        for (int i = stack.size() - 1; i >= 0 ; i--) {
            result += stack.pop();
        }
        return result;
    }
}
```

### 