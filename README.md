# Collections Library for Java

## Features

- **BiMap**: An implementation of a bidirectional map, allowing unique key-value and value-key mappings.
- **ImmutableCollection**: A simple implementation of an immutable collection, ensuring immutability after creation.
- **Multiset**: An implementation of a multiset (bag) that allows storing elements along with their occurrence counts.
- **Range**: A versatile range class supporting open, closed, open-closed, and closed-open ranges of values.
- **SizeLimitedQueue**: A queue with a fixed size limit that automatically removes the oldest element when the limit is reached.

## Why Use this Library?

- Convenient and efficient data structures to solve common programming problems.
- Provides specialized functionality not available in the standard Java collections.
- Promotes code readability and maintainability.

## Getting Started

To use this library, simply import the desired classes into your Java project. Each class is self-contained and can be used independently. Refer to the code examples for guidance on how to use each data structure effectively.

## Examples

```java
// BiMap example
BiMap<String, Integer> ageMap = new BiMap<>();
ageMap.put("John", 25);
ageMap.put("Alice", 30);
System.out.println(ageMap.get("John"));  // Output: 25
System.out.println(ageMap.inverse().get(30));  // Output: Alice

// ImmutableCollection example
ImmutableCollection<String> names = ImmutableCollection.of("John", "Alice", "Bob");
System.out.println(names.contains("Alice"));  // Output: true

// Multiset example
Multiset<String> bag = new Multiset<>();
bag.add("Apple");
bag.add("Apple");
bag.add("Orange");
System.out.println(bag.count("Apple"));  // Output: 2

// Range example
Range<Integer> range = Range.closed(1, 10);
System.out.println(range.contains(5));  // Output: true

// SizeLimitedQueue example
SizeLimitedQueue<Integer> queue = new SizeLimitedQueue<>(3);
queue.add(1);
queue.add(2);
queue.add(3);
queue.add(4);
System.out.println(queue);  // Output: [2, 3, 4]
