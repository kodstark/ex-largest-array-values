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

import java.util.Arrays;

/**
 * Implementation based on sorting copy of original array and reverse only last n elements.
 * <p>
 * Most compact and almost most performant implementation.
 * 
 * @author kodstark
 */
public class FindLargestValuesImpl implements FindLargestValues
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
        int[] sortedValues = Arrays.copyOf(valuesInAnyOrder, valuesInAnyOrder.length);
        Arrays.sort(sortedValues);
        int[] result = reverseLastValues(sortedValues, n);
        return result;
    }

    private int[] reverseLastValues(int[] values, int n)
    {
        int backIdx = values.length - 1;
        int[] result = new int[n];
        for (int i = 0; i < n; i++)
        {
            result[i] = values[backIdx--];
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
