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

import java.util.PriorityQueue;

/**
 * Implementation based on sorted bounded list PriorityQueue. All added elements are in right order and we need to only
 * check last element to decide if new element should replace this last one.
 * 
 * @author kodstark
 */
public class FindLargestValuesImpl2 implements FindLargestValues
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
        PriorityQueue<Integer> sortedList = new PriorityQueue<Integer>(n);
        addSortedFirstNValues(valuesInAnyOrder, n, sortedList);
        addSortedRestValues(valuesInAnyOrder, n, sortedList);
        int[] result = reverse(sortedList);
        return result;
    }

    private void addSortedFirstNValues(int[] valuesInAnyOrder, int n, PriorityQueue<Integer> sortedList)
    {
        for (int i = 0; i < n; i++)
        {
            sortedList.add(valuesInAnyOrder[i]);
        }
    }

    private void addSortedRestValues(int[] valuesInAnyOrder, int n, PriorityQueue<Integer> sortedList)
    {
        for (int i = n; i < valuesInAnyOrder.length; i++)
        {
            int cur = valuesInAnyOrder[i];
            Integer last = sortedList.peek();
            if (last < cur)
            {
                sortedList.poll();
                sortedList.add(cur);
            }
        }
    }

    private int[] reverse(PriorityQueue<Integer> list)
    {
        int[] result = new int[list.size()];
        for (int i = list.size() - 1; i >= 0; i--)
        {
            result[i] = list.poll();
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

}
