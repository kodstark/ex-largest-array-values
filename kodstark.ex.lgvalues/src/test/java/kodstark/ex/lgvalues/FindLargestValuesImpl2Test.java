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

import kodstark.ex.lgvalues.FindLargestValuesImpl2;

import org.junit.Before;

public class FindLargestValuesImpl2Test extends AbstractFindLargestValuesTest
{

    @Before
    public void setup()
    {
        finder = new FindLargestValuesImpl2();
    }
}
