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

import java.util.AbstractList;
import java.util.Collections;

import org.apache.commons.collections.list.TreeList;

/**
 * Implementation based on sorted bounded common collection TreeList. All added elements are in right order and we need
 * to only check last element to decide if new element should replace this last one.
 * <p>
 * 10 times worse performance than best one with sorting copy of array.
 * 
 * @author kodstark
 */
public class FindLargestValuesImpl6 implements FindLargestValues
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

    @SuppressWarnings("unchecked")
    @Override
    public int[] findMaxValues(int[] valuesInAnyOrder, int n)
    {
        assertParmFindLargestValues(valuesInAnyOrder, n);
        if (n == 0)
        {
            return new int[] {};
        }
        SortedList sortedList = new SortedList();
        addSortedFirstNValues(valuesInAnyOrder, n, sortedList);
        addSortedRestValues(valuesInAnyOrder, n, sortedList);
        int[] result = reverse(sortedList);
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
            int last = (Integer) sortedList.get(0);
            if (last < cur)
            {
                sortedList.remove(0);
                sortedList.addSorted(cur);
            }
        }
    }

    private int[] reverse(AbstractList<Integer> list)
    {
        int[] result = new int[list.size()];
        for (int i = list.size() - 1, j = 0; i >= 0; i--, j++)
        {
            result[i] = list.get(j);
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

    private static class SortedList extends TreeList
    {
        @SuppressWarnings("unchecked")
        public void addSorted(int value)
        {
            int foundAt = Collections.binarySearch(this, value, null);
            int insertAt = (foundAt > -1) ? foundAt : (-foundAt) - 1;
            add(insertAt, value);
        }
    }

}