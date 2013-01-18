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

import java.util.Collections;
import java.util.LinkedList;

/**
 * Implementation based on sorted bounded list LinkedList. All added elements are in right order and we need to only
 * check last element to decide if new element should replace this last one.
 * 
 * @author kodstark
 * 
 */
public class FindLargestValuesImpl3 implements FindLargestValues
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
        SortedList<Integer> sortedList = new SortedList<Integer>();
        addSortedFirstNValues(valuesInAnyOrder, n, sortedList);
        addSortedRestValues(valuesInAnyOrder, n, sortedList);
        int[] result = reverse(sortedList);
        return result;
    }

    private void addSortedFirstNValues(int[] valuesInAnyOrder, int n, SortedList<Integer> sortedList)
    {
        for (int i = 0; i < n; i++)
        {
            sortedList.addSorted(valuesInAnyOrder[i]);
        }
    }

    private void addSortedRestValues(int[] valuesInAnyOrder, int n, SortedList<Integer> sortedList)
    {
        for (int i = n; i < valuesInAnyOrder.length; i++)
        {
            int cur = valuesInAnyOrder[i];
            Integer last = sortedList.peek();
            if (last < cur)
            {
                sortedList.poll();
                sortedList.addSorted(cur);
            }
        }
    }

    private int[] reverse(SortedList<Integer> sortedList)
    {
        int[] result = new int[sortedList.size()];
        for (int i = sortedList.size() - 1; i >= 0; i--)
        {
            result[i] = sortedList.poll();
        }
        return result;
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
        if (n < 0)
        {
            throw new IllegalArgumentException();
        }
    }

    @SuppressWarnings("serial")
    private static class SortedList<T> extends LinkedList<T>
    {
        public void addSorted(T paramT)
        {
            int insertionPoint = Collections.binarySearch(this, paramT, null);
            add((insertionPoint > -1) ? insertionPoint : (-insertionPoint) - 1, paramT);
        }
    }

}
