### Performance comparison for finding largest values in Java array

Comparing of performance few solutions in Java for finding n largest values in array.

Module provides few implementations of below interface, unit tests and performance test.

```
public interface FindLargestValues
{
    int findMaxValue(int[] valuesInAnyOrder);

    int[] findMaxValues(int[] valuesInAnyOrder, int howMany);
}
```

Simplest solution is one of the fastest - it just uses sorting copy of array and gets n last elements. Known collections with keeping sorted list have worse performance, the closest are colt library and PriorityQueue. Tuning with hand coded sorted linked list backed by array is faster only few percent and only sometimes however is much more complicated. 

```
Statistics from running FindLargestValuesTestPerf
 
3 000 000 iters with FindLargestValuesImpl2 took 3399 ms
3 000 000 iters with FindLargestValuesImpl3 took 6134 ms
3 000 000 iters with FindLargestValuesImpl5 took 3013 ms
3 000 000 iters with FindLargestValuesImpl6 took 14861 ms
3 000 000 iters with FindLargestValuesImpl7 took 2048 ms
3 000 000 iters with FindLargestValuesImpl took 2182 ms
```

Look that FindLargestValuesImpl involve 127% more CPU instructions than FindLargestValuesImpl7 but better utilizes instructions per cycle with 2,45 instead of 1,75 (counted by Linux performance counters).

```
10 000 000 iters with kodstark.ex.lgvalues.FindLargestValuesImpl@523e59ca took 5593 ms

5939,471950 task-clock                #    1,042 CPUs utilized
19683501533 cycles                    #    3,314 GHz                     [56,23%]
48165642281 instructions              #    2,45  insns per cycle         [67,05%]
5,697960263 seconds time elapsed

10 000 000 iters with kodstark.ex.lgvalues.FindLargestValuesImpl7@429bc5f9 took 6250 ms

6533,482624 task-clock                #    1,026 CPUs utilized
21597583194 cycles                    #    3,306 GHz                     [55,86%]
37862661853 instructions              #    1,75  insns per cycle         [66,78%]
6,369464535 seconds time elapsed
```

##### FindLargestValuesImpl

Implementation based on sorting copy of original array and reverse only last n elements. Most compact and almost most performant implementation.

##### FindLargestValuesImpl2

Implementation based on sorted bounded list PriorityQueue. All added elements are in right order and we need to only check last element to decide if new element should replace this last one.

##### FindLargestValuesImpl3

Implementation based on sorted bounded list LinkedList. All added elements are in right order and we need to only check last element to decide if new element should replace this last one.

##### FindLargestValuesImpl4

Initialy implementation based on gnu trove TIntArrayList [3.0.3] but binarySearch and insert doesn't work correctly in this class.

##### FindLargestValuesImpl5

Implementation based on sorted bounded colt IntArrayList. All added elements are in right order and we need to only check last element to decide if new element should replace this last one.

##### FindLargestValuesImpl6

Implementation based on sorted bounded common collection TreeList. All added elements are in right order and we need to only check last element to decide if new element should replace this last one.

10 times worse performance than best one with sorting copy of array.

##### FindLargestValuesImpl7

Implementation based on sorted bounded hand coded sorted linked list backed by array. All added elements are in right order and we need to only check lower element to decide if new element should replace this last one.

Because assumption of about 50 elements this lists keeps indexes in byte arrays and thus has limitation to 127 elements.

Only faster few percent from Arrays.sort approach but much more complicated.

#### Build

Launch build

```mvn clean install```

Generate coverage report

```mvn clean install -Pcoverage```
 

