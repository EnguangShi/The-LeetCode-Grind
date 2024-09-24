# Java Cheatsheet

## Array & ArrayList

### Array

创建：`int[] array = new int[5];` or `int[] numbers = new int[]{1, 2, 3, 4, 5};`

- `Arrays.sort(array)`: Sorts the specified array into ascending numerical order.
- `Arrays.equals(array1, array2)`: Returns `true` if the two arrays are equal to each other.
- `Arrays.asList(1,2,3)`: Returns a fixed-size list backed by the specified array.
- `Arrays.toString(array)`: Returns a string representation of the contents of the specified array.
- int[] nums = {1, 2, 3, 4, 5, 6, 7, 8, 9}  `Arrays.copyOfRange(nums, 0, 5)`: Returns {1, 2, 3, 4, 5}

### ArrayList

创建：`ArrayList<Integer> arrayList = new ArrayList<>();`

- `add(E e)`: Appends the specified element to the end of the list. RETURNS true OR false indicating whether the add is success or not.
- `get(int index)`: Returns the element at the specified position.
- `set(int index, E element)`: Replaces the element at the specified position.
- `remove(int index)`: Removes the element at the specified position.
- `size()`: Returns the number of elements.
- `isEmpty()`: Returns `true` if the list contains no elements.
- `contains(Object o)`: Returns `true` if the list contains the specified element.
- `clear()`: Removes all of the elements.
- `indexOf(Object o)`: Returns the index of the first occurrence of the specified element, or -1 if the list does not contain the element.
- `int[] arr = list.stream().mapToInt(i -> i).toArray();`: convert `ArrayList<Integer>` to `int[]`
- `toArray(new String[0])`: convert other types of ArrayList to array
- `Collections.reverse(arr)`: reverse arr

与Array的区别：

`int[] array = new int[5]` is fix-sized, 

`List<Integer> arrayList = new ArrayList<>()` is dynamically-sized

与List的区别：

```java
ArrayList<Integer> arrayList = new ArrayList<>();  // arrayList can use both ArrayList's and List's methods
arrayList = new LinkedList<>(); // This will cause a compile-time error, because both ArrayList and LinkedList both implements List

List<Integer> list = new ArrayList<>(); // list is of List type but an instance of the ArrayList class, it can only use List methods, not ArrayList's
list = new LinkedList<>(); // This is perfectly valid.
```



## HashMap & HashSet

### HashMap (stores key-value pair)

***用于存储key-value pairs, 可以检查某key存不存在，或者根据key找value***

创建：`HashMap<Integer, Integer> map = new HashMap<>();`

- map.put(key, value)

- map.get(key) -> value  重要，用于查找某key是否存在，并根据key找value

- map.remove(key)

- map.containsKey(key) -> boolean

- map.containsValue(key) -> boolean

- map.clear()

- map.size()

- map.keySet() -> set of keys

- map.entrySet() -> set of entries

  `for(HashMap.Entry<Integer,Integer> entry : map.entrySet())`

- entry.getKey() -> get the key of one entry

- entry.getValue() -> get the value of one entry

- map.values() -> Collection of values, can be used to create a new HashSet

  `HashSet<Integer> set = new HashSet<>(map.values());` 

- map.put(key, map.getOrDefault(key, 0) + 1); 如果不存在则加入(key,1), 存在则把value++

- map1.equals(map2): 如果两个map的key以及它们的value都一样，return true

### HashSet (stores only values)

***用于存储元素并且能用O(1)查找某元素是否存在***

创建：

`HashSet<Integer> set = new HashSet<>();` 

`HashSet<Integer> set = new HashSet<>(map.values());` 

- set.add() -> return true如果加进去，false如果已有重复元素
- set.contains() -> boolean  重要，用于查找某元素是否存在
- set.remove()
- set.clear()
- set.size()



## String

创建：Sting s = new String();

- s.substring(start, end)
- s.toCharArray();
- s.startsWith("a");
- s.endsWith("z");
- s.toLowerCase();
- s.indexOf('#', i): 找到index i后面的第一个字符'#'的位置
- String.valueOf(i): convert int to String
- Integer.valueOf(s): convert String to int

### StringBuilder

***用于把String当作Array来处理***

创建：`StringBuilder sb = new StringBuilder();`

- sb.append("something"): 这里something可以是int
- sb.length()
- sb.charAt(i)
- sb.setCharAt(i, 'c')
- sb.deleteCharAt(i)
- sb.substring(0, 5) -> takes out a **String** with chars of index 01234
- sb.reverse()
- sb.delete(int start, int end)
- sb.toString()



## Stack & Queue & LinkedList

### Stack

***一头可进可出，只有头部的元素可以进行操作***

``````java
Stack<Integer> stack = new Stack<>();
stack.push(42);
stack.peek();  // return 42, without popping 42
stack.pop();  // pop and return 42
stack.isEmpty();  // return true
``````

缺点：只能access头部元素，无法更改内部的其他元素

### Queue

***头尾一进一出，只有头尾的元素可以进行操作***

```java
Queue<Integer> queue = new LinkedList<>();
queue.add(42);
queue.peek();  // return 42, without removing 42
queue.remove();  // remove and return 42, poll() does the same except it returns null instead of an exception when the queue is empty
queue.isEmpty();  // return true
```

缺点：只能access两头的元素，并且一头只能出，一头只能进

### Deque

***头尾都可进可出，只有头尾的元素可以进行操作***

```java
Deque<Integer> deque = new LinkedList<>();
deque.addFirst();
deque.removeFirst();
deque.addLast();
deque.removeLast();
```

缺点：只能access两头的元素

可用Deque实现Stack和Queue

### Singly Linked List

***通过头部access链表中的元素，每个元素都可进行操作***

```java
static class Node {
    int data;
    Node next;

    Node(int data) {
        this.data = data;
        this.next = null;
    }
}
```

缺点：access中间和尾部的元素需要O(n)

### Doubly Linked List

***通过头尾access链表中的元素，每个元素都可进行操作***

```java
static class Node {
    int data;
    Node prev, next;

    Node(int data) {
        this.data = data;
        this.prev = null;
        this.next = null;
    }
}
```

缺点：access中间的元素需要O(n)

### Linked List inside Hash Map

***通过HashMap access链表中的元素，每个元素都可进行操作***

![image-20230916162510564](/Users/samuel/Library/Application Support/typora-user-images/image-20230916162510564.png)

```java
class LinkedHM {
    class Node {
        int key, value;
        Node prev, next;
        Node(int k, int v) {
            key = k;
            value = v;
        }
    }

    private HashMap<Integer, Node> map = new HashMap<>();
    private Node head, tail;

    public LinkedHM() {
        head = new Node(-1, -1);  // dummy head
        tail = new Node(-1, -1);  // dummy tail
        head.next = tail;
        tail.prev = head;
    }

  	// write your own function here:
  
  	public int put(int key, value) {
        Node newNode = new Node(key, value);
        map.put(key, newNode);
        insertToHead(newNode)
    }
  	
  	public int get(int key) {
      	Node curr = map.get(key);
      	return curr.value;
    }
  
    public void remove(Node node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    public void insertToHead(Node node) {
        node.next = head.next;
        node.prev = head;
        head.next.prev = node;
        head.next = node;
    }
}
```

优点：access每个元素都是O(1)，且不止头尾，每个元素都可进行操作

### Priority Queue

***元素自动按大小排序的Queue***

```java
// 默认从小到大排序，先输出的是最小值
PriorityQueue<Integer> minHeap = new PriorityQueue<>();
// 从大到小，先输出的是最大值
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);

// 加入
pq.add(1);
// 弹出
pq.poll();
```



## iterator()

Get the iterator:

`Iterator<Integer> iterator = arr.iterator();`

`hasNext()` means the iterator has a current value 

`next()` will return the current value and go to next element

`remove()` will remove the current value from the collection

```java
import java.util.Iterator;
import java.util.LinkedList;

public class Main {
    public static void main(String[] args) {
        // Create a LinkedList
        LinkedList<Integer> numbers = new LinkedList<>();
        numbers.add(1);
        numbers.add(2);
        numbers.add(3);

        // Get the iterator
        Iterator<Integer> iterator = numbers.iterator();

        // Use the iterator to iterate over the list
        while (iterator.hasNext()) {
            Integer number = iterator.next();
            System.out.println(number);

            // Example of removing an element using the iterator
            if (number.equals(2)) {
                iterator.remove();
            }
        }

        // Print the LinkedList after removal
        System.out.println(numbers); // Outputs: [1, 3]
    }
}

```



## Binary Search

```java
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
```

对于遍历一个sorted的数组可以节省runtime

比如123456789这个数组，要找8这个值，

或者对于1245789这个数组，要找大于5的最小值



## Graph

- indegree: 每个节点被几条边指向。如果一个tree中某节点的indegree是1（只有一条边指向它），那么它一定是leaf node

### BFS：广度优先搜索

```java
// BFS算法实现
void BFS(int startVertex) {
    boolean[] visited = new boolean[numVertices];
    Queue<Integer> queue = new LinkedList<>();

    visited[startVertex] = true;
    queue.add(startVertex);

    while (!queue.isEmpty()) {
        int v = queue.poll();
        System.out.print(v + " ");

        for (int adj : adjLists[v]) {
            if (!visited[adj]) {
                visited[adj] = true;
                queue.add(adj);
            }
        }
    }
}
```

先把第一个节点放入queue，然后弹出它的同时放入它的邻居。每放入一个邻居就登记一下这个邻居已经被放入了

之后重复，每弹出一个就放入该节点的没被登记过的邻居，并且登记该邻居

直到queue里没有元素

所有节点被弹出的顺序就是BFS

### DFS：深度优先搜索

```java
// DFS算法实现
void DFS(int vertex) {
    boolean[] visited = new boolean[numVertices];
    DFSUtil(vertex, visited);
}

// DFS递归辅助函数
private void DFSUtil(int v, boolean[] visited) {
    visited[v] = true;
    System.out.print(v + " ");

    for (int adj : adjLists[v]) {
        if (!visited[adj]) {
            DFSUtil(adj, visited);
        }
    }
}
```

对于第一个节点，执行该recursion：

先登记一下这个节点

然后用for loop遍历这个节点的邻居，对于每个邻居：

如果没有登记过，就对该邻居执行recursion

所有节点被拜访的顺序就是DFS

### 用Adjacent List表示Graph

使用一个 HashMap 来存储邻接表，其中Key是节点，Value是与该节点相邻的节点列表。

```java
Map<String, Set<String>> adj = new HashMap<>();
```

添加nodeA和nodeB之间的边

```java
adj.putIfAbsent(nodeA, new ArrayList<>());
adj.putIfAbsent(nodeB, new ArrayList<>());
adj.get(nodeA).add(nodeB);
adj.get(nodeB).add(nodeA);
```

## Dynamic Programming

### 01背包

n个物品，每个物品有重量和价值，问在最大承重为m的背包内怎么放让价值最大

`dp[i][j]`的定义：编号为[0, i]物品任取几个放在承重为j的背包的最大价值

#### 第一步：确定递推公式

对于`dp[i][j]`：

不放物品i：`dp[i][j] = dp[i-1][j]` ：当前最大价值等于物品i之前所有物品能放在容量为j的背包的最大价值

放物品i：`dp[i][j] = dp[i-1][j-物品i的重量] + 物品i的价值`：当前最大价值等于物品i之前所有物品能放在容量减去i的重量的背包中的最大价值，再加上物品i的价值

取上述两个的最大值：

`dp[i][j] = max(dp[i-1][j], dp[i-1][j-物品i的重量] + 物品i的价值)`

#### 第二步：如何初始化dp数组：

| `dp[i][j]` | j = 0 | j = 1 | j = 2 | j = 3                                                        |
| ---------- | ----- | ----- | ----- | ------------------------------------------------------------ |
| i = 0      | ——    | ——    | ——    | ——                                                           |
| i = 1      | ｜    |       | ↘️     | ⬇️                                                            |
| i = 2      | ｜    |       |       | 此处值由`[i-1][j]` (上方的格子)或`[i-1][j-某数]`(左上的格子)推出 |

所以划横线的地方(i = 0, or j = 0)要初始化，其余的格子都能被递推算出

`dp[i][0]`，背包容量为0，无论是选取哪些物品，背包价值总和一定为0。

`dp[0][j]`，即：存放编号0的物品的时候，各个容量的背包所能存放的最大价值。只要容量大于物品0的重量，最大价值就是物品0的价值。

`dp[i][j]`，其余的格子都可以通过上方和左上方的格子求出，所以初始化成任意值都可以。此处可以初始化成0。

#### 第三步：确定遍历顺序：

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



## Java Knowledge

### Tips

1. `int mid = left + (right - left) / 2;`好过`int mid = (left + right) / 2;`: 后者会有`left + right`超过int范围的风险
1. Math.max(num1, num2)返回更大的那個

### Primitive data

1. Primitive data types in Java cannot be compared to `null`

   Primitive types includes `byte`, `short`, `int`, `long`, `float`, `double`, `char`, `boolean`,

   ```java
   sb.charAt(i) != null  // wrong because char can not be compared to null
   ```

2. Primitive types have default values (e.g., 0 for numeric types, false for boolean, '\u0000' for char). Non-primitive types have a default value of `null`, which means they are initially unassigned.

3. `.length` is only used by Arrays, `.length()` is used by Strings and StringBuilder

4. `==` is used by primitive types for comparison

5. `.equals()` should be used by Strings and Objects for comparison

6. Angle brackets (`<` and `>`) must be used with classes, not primitive types. 

   ```java
   // HashMap<char, int> map = new HashMap<>(); wrong
   HashMap<Character, Integer> map = new HashMap<>();
   char c = 'c';
   int i = 1;
   map.put(c, i);
   ```

7. Mutable types: Can change after creation (e.g., `ArrayList`, custom objects).

   Immutable types: Can't change once created (e.g., `String`, `Integer`).

   Primitives (`int`, `boolean`): Always immutable, value cannot change.

   For changeable integers: Use `int[]`, custom class, or `AtomicInteger`.

### String and Integer convert to each other

1. `String.valueOf(int)`: convert `int` to `String`
2. `Integer.parseInt(str)`: convert `String` to `int`

### .compare()

1. `Arrays.sort(points, (a, b) -> Integer.compare(a[0], b[0]))`

   1. **升序排序**:

      - 当我们使用 `Integer.compare(a[0], b[0])`，这个方法会比较两个整数 `a[0]` 和 `b[0]`（即点的 x 坐标）。
      - 如果 `a[0]` 小于 `b[0]`，`Integer.compare(a[0], b[0])` 返回负数，表示 `a` 应该在 `b` 之前。
      - 如果 `a[0]` 等于 `b[0]`，返回 0，表示 `a` 和 `b` 的顺序不变。
      - 如果 `a[0]` 大于 `b[0]`，返回正数，表示 `a` 应该在 `b` 之后。

      因此，这种比较方式导致 `points` 数组按照 x 坐标的升序排列。

   2. **如果使用 `Integer.compare(b[0], a[0])`**:

      - 这会导致排序逻辑反转。
      - 当 `b[0]` 小于 `a[0]` 时，`Integer.compare(b[0], a[0])` 返回负数，表示 `b` 应该在 `a` 之前，即大的 x 坐标的点会排在前面。
      - 因此，这会导致 `points` 数组按照 x 坐标的降序排列。

### `extends` and `implements`

1. **`extends`**:

   - Used exclusively for **class-to-class** or **interface-to-interface** inheritance.
   - When a class `extends` another class, it inherits the properties and methods from that class. This is the primary way Java handles inheritance for classes.
   - When an interface `extends` another interface, it adds or inherits the abstract methods from the parent interface.
   - A class cannot `extend` more than one class (no multiple inheritance in Java for classes). However, an interface can `extend` multiple interfaces.

   Examples:

   ```
   javaCopy code
   // Class to Class
   class Animal {}
   class Dog extends Animal {} // Dog inherits from Animal
   
   // Interface to Interface
   interface A {}
   interface B extends A {} // B inherits abstract methods from A
   ```

2. **`implements`**:

   - Used by **classes** to adopt **interfaces**.
   - When a class `implements` an interface, it's essentially making a contract that it will provide concrete implementations for all the abstract methods defined in that interface.
   - A class can `implement` multiple interfaces. This is Java's way of compensating for the absence of multiple inheritance for classes.

   Example:

   ```
   javaCopy code
   interface Eater {
       void eat();
   }
   
   interface Runner {
       void run();
   }
   
   class Human implements Eater, Runner {
       @Override
       public void eat() {
           // Implementation here
       }
       
       @Override
       public void run() {
           // Implementation here
       }
   }
   ```

In simpler terms:

- `extends` is about inheritance. For classes, it's about "being a type of" something. For interfaces, it's about expanding upon other interfaces.
- `implements` is a commitment. A class that implements an interface is making a promise to provide functionality specified by that interface.

So, when you see a class that `extends` another class, think of it as a "child" inheriting characteristics from its "parent". When you see a class that `implements` an interface, envision it as a class committing to certain behaviors specified by that interface.

### HashMap's implementation

- Implements Map interface
- key is hashed using hashCode() method
- it is a dynamic array that grows as needed (when the threshold = capacity * load factor is exceeded, double the size, rehash the entries)

### Override vs Overload

- Override: subclass method overrides the same method (same name, same parameters, same return type) in its superclass (Runtime Ploymorphism: method is determined at runtime)
- Overload: >=2 methods in the same class have the same name but different parameters. (Compile-time Polymorphism: method determined at compile-time)

### final keyword

- final variable: value cannot be changed
- final method: cannot be overridden by subclasses
- final class: cannot have subclasses

### static keyword

- static variable: belongs to the Class, not the instances. So the value of a static variable is shared among all instances. (e.g. all mathClassroom instances share the same mathTeacher static variable)
- non-static variable: belongs to an instance. Different instances can have different values. (e.g. all mathClassroom instances can have different capacity)

- static method: can be called without creating an instance: `MyClass.staticMethod()` (e.g. Math.sqrt())
- non-static method: must be called with an instance: `obj.instanceMethod` (e.g. all other methods)

### Inheritance, Composition, Aggregation

- Inheritance: subclass extends superclass (Car extends Vehicle)
- Composition: an instance of a class is a component of another class (Car has an Engine)
- Aggregation: a class can be a component of another class or exist independently (Library has Books, Books can also exist independently)

### == and .equals()

- == is for primitive types (int, char, ...) to compare values, or for Objects (String) to compare reference (if 2 objects has different reference but same value, they != each other)
- .equals() in the Object class is also for comparing reference, but many classes (like String) override .equals() to compare the actual data, not the reference

### Abstract Class & Interface

- Superclass:
  - Can be instantiated
  - Can only have concrete methods
  - One subclass can only extend one superclass due to single inheritance

- Abstract Class: 
  - Cannot be instantiated. 
  - Can have implemented concrete methods and unimplemented abstract methods. 
  - One subclass can only extend one abstract class due to single inheritance
- Interface: 
  - Can not be instantiated too. 
  - Can only have unimplemented methods with method names and return type. Or Default methods (invoke using instances, can be overridden) and Static methods (invoked on the interface itself, not instances, cannot be overridden)
  - One class can implement multiple interfaces.

### Design Patterns

**Singleton**: a class only has 1 instance

private constructor, private static instance, public static getter to return the instance

**Observer**: one-to-many relationship: when the subject changes state, all related observers will be notified

**Composite**: like a tree or a file system: a tree node can have another tree node or a leaf as its children

**Iterator**: traverse a collection without exposing its implementation (create an iterator on a collection, call hasNext or next on this iterator)

### Lock

a thread has a lock on an object, only it can access the object. Other threads must wait until the lock is released by the first thread

### Big O

O(n): the time complexity or space complexity of an algorithm at most grows linearly with the size of the input, which is n

### LinkedList & ArrayList

ArrayList:

- dynamic array -> fills up -> create a larger and copies everything in
- adding and deleting -> O(n), shift everything
- get -> O(1) because array

LinkedList:

- doubly linked list
- adding and deleting -> O(1), change the link
- get -> O(n), traverse one by one

### Java's advantages

1. can run on any device with a JVM, JS (on browser and Node.js), C++ (compile directly to machine code specific to OS)
2. garbage collection helps manage memory allocation (C++ no)
3. Strong Typing helps catch errors eariler at development

### Spring & Spring Boot

Spring: a framework for developing java apps, it supports MVC for building web apps, secure authentication and authorization, testing with JUnit

Spring Boot: built on top of Spring, simplifies the process of developing Spring applications. It provides default configurations.

### Abstraction, Inheritance, Encapsulation, Polymorphism

A: hiding the complex details, only shows the necessary parts

I: a class can inherit attributes and methods from another class

E: it means to prevent certain components in a class to be accessed

P: for the methods with the same name to do different things based on the actual type of the instance (overriding, overloading)

## SQL

### Normalization

Normalization: the process of removing redundancy from data

| name  | dept | address |
| ----- | ---- | ------- |
| sam   | cs   | xxxx    |
| julia | cs   | xxxx    |
| jerry | math | yyyy    |

because address depends on dept, there are redundancy in this table

**1NF First Normal Form**: each row is unique & no column in any row contains multiple values

❌

| name | pet                    | type                         |
| ---- | ---------------------- | :--------------------------- |
| sam  | apa<br />kaka<br />joy | lizard<br />hamster<br />dog |

🙆

| name | pet  | type    |
| ---- | ---- | ------- |
| sam  | apa  | lizard  |
| sam  | kaka | hamster |
| sam  | joy  | dog     |

或者

table1: client id 1; name sam

table2: 

client id 1; pet apa; type lizard;

client id 1; pet kaka; type hamster;

client id 1; pet joy; type dog;

**2NF Second Normal Form**: satisfy 1NF & No non-key attribute is dependent on a subset of the key

House#, Street, City, HouseColor, CityPopulation: 它的key是(House#, Street, City)，决定一栋unique的房
House#, Street, City决定HouseColor
City决定CityPopulation （所以CityPopulation是被subset of the key决定的)

如果有一项attribute是被subset of the key决定的：要改成2NF：

表格1：House#, Street, City, HouseColor

表格2：City, CityPopulation

**3NF Third Normal Form**: satisfy 2NF & No non-key attribute is dependent on another non-key attribute (or there is a transitive dependency(A->B, B->C))

Student ID(primary key), SubjectID, SubjectName （假设一个student只有一个subject）
Student ID决定SubjectID，SubjectID决定SubjectName（A决定B，B决定C)

改成3NF:

表格1: StudentID, SubjectID

表格2: SubjectID, SubjectName

### Indexing

a database optimization technique, speeds up the process of finding certain rows.

when an index is created, the database also creates a data structure for faster lookup of rows

CREATE INDEX index_name ON Table(column)

**Why not creating index for all columns**: 

1. Too much storage space
2. Slow down write: updating rows will need to update index

### SQL command

SELECT DISTINCT productid, productname, productprice
FROM product
WHERE productprice > 100
ORDER BY productprice DESC

SELECT: pick a column
DISTINCT: show one instance of all different values
FROM: specify a table
WHERE: specify the criteria
ORDERBY: sort according to the value of what
DESC: descending



SELECT vendorid, COUNT(*), AVG(productprice)
FROM product
GROUP BY vendorid;

Select venderid, the number of rows for each different vendorid, and the average productprice for each different vendorid

| Vendor ID | Count(*) | Average product price |
| --------- | -------- | --------------------- |
| MK        | 4        | 121.2                 |
| PG        | 2        | 95                    |



SELECT vendorid, categoryid, COUNT(*), AVG(productprice)*
*FROM product
GROUP BY vendorid, categoryid
HAVING COUNT(*) > 1;

HAVING: specify group criteria

### Many to Many

2 Tables: Students and Courses

1 junction tables: Primary Key (StudentID, CourseID); Foreign Key (StudentID), Foreign Key (CourseID)