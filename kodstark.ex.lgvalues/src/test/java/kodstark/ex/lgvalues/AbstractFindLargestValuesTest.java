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
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import kodstark.ex.lgvalues.FindLargestValues;
import kodstark.ex.lgvalues.FindLargestValuesImpl4;

import org.junit.Before;
import org.junit.Test;


public abstract class AbstractFindLargestValuesTest
{

    protected FindLargestValues finder;

    @Before
    public void setup()
    {
        finder = new FindLargestValuesImpl4();
    }

    @Test
    public void shouldFindFive()
    {
        assertEquals(5, finder.findMaxValue(new int[] { 1, 3, 5, 2, 4 }));
    }

    @Test
    public void shouldFindLast()
    {
        assertEquals(8, finder.findMaxValue(new int[] { 1, 3, 5, 2, 8 }));
    }

    @Test
    public void shouldFindOne()
    {
        assertEquals(1, finder.findMaxValue(new int[] { 1 }));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailOnEmpty()
    {
        finder.findMaxValue(new int[] {});
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailOnNull()
    {
        finder.findMaxValue(null);
    }

    @Test
    public void shouldNoSideEffects()
    {
        int[] source = new int[] { 1, 3, 5, 2, 4 };
        finder.findMaxValue(source);
        assertArrayEquals(new int[] { 1, 3, 5, 2, 4 }, source);
    }

    @Test
    public void shouldFindThreeTops()
    {
        assertArrayEquals(new int[] { 5, 4, 3 }, finder.findMaxValues(new int[] { 3, 1, 5, 2, 4 }, 3));
    }
    
    @Test
    public void shouldFindSixFour()
    {
        assertArrayEquals(new int[] { 6,4 }, finder.findMaxValues(new int[] { 4,6,2,3 }, 2));
    }    
    
    @Test
    public void shouldFindOneTop()
    {
        assertArrayEquals(new int[] { 5 }, finder.findMaxValues(new int[] { 1, 3, 5, 2, 4 }, 1));
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailNValuesTooLong()
    {
        finder.findMaxValues(new int[] { 1, 3 }, 3);
    }

    @Test(expected = NullPointerException.class)
    public void shouldFailnValuesNull()
    {
        finder.findMaxValues(null, 3);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldFailNValuesMinus()
    {
        finder.findMaxValues(new int[] { 1, 3 }, -1);
    }

    @Test
    public void shouldNValuesZero()
    {
        assertArrayEquals(new int[] {}, finder.findMaxValues(new int[] { 1, 3, 5, 2, 4 }, 0));
    }

    @Test
    public void shouldNValuesSorted()
    {
        assertArrayEquals(new int[] { 5, 4, 3, 2, 1 }, finder.findMaxValues(new int[] { 1, 3, 5, 2, 4 }, 5));
    }

    @Test
    public void shouldNValuesNotRemoveDoubled()
    {
        assertArrayEquals(new int[] { 2, 2, 1 }, finder.findMaxValues(new int[] { 1, 2, 2 }, 3));
    }
}
