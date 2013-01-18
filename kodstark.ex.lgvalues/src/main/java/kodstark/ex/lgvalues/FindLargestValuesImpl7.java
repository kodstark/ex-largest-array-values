/**
 * Copyright (C) 2013 Kamil Demecki <kodstark@gmail.com>
 *
 * Licensed under the terms of any of the following licenses at your
 * choice:
 *
 *  - GNU Lesser General Public License Version 2.1 or later (the "LGPL")
 *    http://www.gnu.org/licenses/lgpl.html
 *
 *  - Mozilla Public License Version 1.1 or later (the "MPL")
 *    http://www.mozilla.org/MPL/MPL-1.1.html
 */
package kodstark.ex.lgvalues;

import cern.colt.Arrays;

/**
 * Implementation based on sorted bounded hand coded sorted linked list backed by array. All added elements are in right
 * order and we need to only check lower element to decide if new element should replace this last one.
 * <p>
 * Because assumption of about 50 elements this lists keeps indexes in byte arrays and thus has limitation to 127
 * elements.
 * <p>
 * Only faster few percent from Arrays.sort approach but much more complicated.
 * 
 * @author kodstark
 */
public class FindLargestValuesImpl7 implements FindLargestValues
{

    @Override
    public int findMaxValue(int[] valuesInAnyOrder)
    {
        assertMaxValuePars(valuesInAnyOrder);
        int result = valuesInAnyOrder[0];
        for (int i = 1; i < valuesInAnyOrder.length; i++)
        {
            int cur = valuesInAnyOrder[i];
            if (cur > result)
            {
                result = cur;
            }
        }
        return result;
    }

    private void assertMaxValuePars(int[] valuesInAnyOrder)
    {
        if (valuesInAnyOrder == null)
        {
            throw new NullPointerException();
        }
        if (valuesInAnyOrder.length == 0)
        {
            throw new IllegalArgumentException();
        }
    }

    @Override
    public int[] findMaxValues(int[] valuesInAnyOrder, int n)
    {
        assertParmFindLargestValues(valuesInAnyOrder, n);
        if (n == 0)
        {
            return new int[] {};
        }
        SortedList sortedList = new SortedList(n);
        addSortedFirstNValues(valuesInAnyOrder, n, sortedList);
        addSortedRestValues(valuesInAnyOrder, n, sortedList);
        int[] result = sortedList.toArray();
        return result;
    }

    private void addSortedFirstNValues(int[] valuesInAnyOrder, int n, SortedList sortedList)
    {
        for (int i = 0; i < n; i++)
        {
            sortedList.addSorted(valuesInAnyOrder[i]);
        }
    }

    private void addSortedRestValues(int[] valuesInAnyOrder, int n, SortedList sortedList)
    {
        for (int i = n; i < valuesInAnyOrder.length; i++)
        {
            int cur = valuesInAnyOrder[i];
            int last = sortedList.getLast();
            if (last < cur)
            {
                sortedList.removeLast();
                sortedList.addSorted(cur);
            }
        }
    }

    private void assertParmFindLargestValues(int[] valuesInAnyOrder, int n)
    {
        if (valuesInAnyOrder == null)
        {
            throw new NullPointerException();
        }
        if (valuesInAnyOrder.length < n)
        {
            throw new IllegalArgumentException();
        }
        if (valuesInAnyOrder.length > 127)
        {
            throw new IllegalArgumentException();
        }
        if (n < 0)
        {
            throw new IllegalArgumentException();
        }
    }

    /**
     * List which tracks reversed natural order in additional arrays of indexes.
     * 
     * @author skajotde
     */
    private static class SortedList
    {
        private final int[] array;
        /** Keeps indexes of previous element for given index in {@link #array} */
        private final byte[] prevs;
        /** Keeps indexes of next element for given index in {@link #array} */
        private final byte[] nexts;
        private final byte maxSize;
        private byte head;
        private byte tail;
        private byte pos;
        private byte size;

        public SortedList(int n)
        {
            maxSize = (byte) n;
            array = new int[n];
            prevs = new byte[n];
            nexts = new byte[n];
            head = -1;
            tail = -1;
            pos = 0;
            size = 0;
        }

        void addSorted(int value)
        {
            if (size >= maxSize)
            {
                throw new IllegalStateException();
            }
            int biggerPos = findBigger(value);
            if (biggerPos != -1)
            {
                addInside(value, biggerPos);
            }
            else
            {
                addAtFront(value);
            }
        }

        private void addInside(int value, int biggerPos)
        {
            array[pos] = value;
            byte beforeNext = nexts[biggerPos];
            byte afterPrev;
            if (beforeNext != -1)
            {
                afterPrev = prevs[beforeNext];
            }
            else
            {
                afterPrev = tail;
            }
            prevs[pos] = afterPrev;
            nexts[pos] = beforeNext;
            if (beforeNext != -1)
            {
                prevs[beforeNext] = pos;
            }
            nexts[biggerPos] = pos;
            if (nexts[pos] == -1)
            {
                tail = pos;
            }
            pos++;
            size++;
        }

        private void addAtFront(int value)
        {
            array[pos] = value;
            if (head != -1)
            {
                prevs[head] = pos;
            }
            prevs[pos] = -1;
            nexts[pos] = head;
            head = pos;
            if (tail == -1)
            {
                tail = pos;
            }
            pos++;
            size++;
        }

        /**
         * Return index after which element should be inserted to conform reversed natural order.
         */
        private int findBigger(int value)
        {
            int curIdx = tail;
            while (curIdx != -1 && array[curIdx] < value)
            {
                curIdx = prevs[curIdx];
            }
            return curIdx;
        }

        int getLast()
        {
            if (tail == -1)
            {
                throw new IllegalStateException();
            }
            return array[tail];
        }

        void removeLast()
        {
            if (tail == -1)
            {
                throw new IllegalStateException();
            }
            pos = tail;
            if (prevs[pos] != -1)
            {
                nexts[prevs[pos]] = -1;
                tail = prevs[pos];
            }
            size--;
            if (size == 0)
            {
                tail = -1;
                head = -1;
            }
        }

        int[] toArray()
        {
            byte curIdx = head;
            int idx = 0;
            int[] result = new int[maxSize];
            while (curIdx != -1)
            {
                result[idx++] = array[curIdx];
                curIdx = nexts[curIdx];
            }
            return result;
        }

        @Override
        public String toString()
        {
            return Arrays.toString(array) + ":" + Arrays.toString(prevs) + ":" + Arrays.toString(nexts) + ":" + head + ":"
                    + tail + ":" + size + ":" + maxSize;
        }
    }
}