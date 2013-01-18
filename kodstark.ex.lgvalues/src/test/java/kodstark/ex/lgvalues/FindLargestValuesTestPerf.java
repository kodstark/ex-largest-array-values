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
import java.util.Random;

import kodstark.ex.lgvalues.FindLargestValues;
import kodstark.ex.lgvalues.FindLargestValuesImpl;
import kodstark.ex.lgvalues.FindLargestValuesImpl2;
import kodstark.ex.lgvalues.FindLargestValuesImpl3;
import kodstark.ex.lgvalues.FindLargestValuesImpl4;
import kodstark.ex.lgvalues.FindLargestValuesImpl5;
import kodstark.ex.lgvalues.FindLargestValuesImpl6;
import kodstark.ex.lgvalues.FindLargestValuesImpl7;

public class FindLargestValuesTestPerf
{
    private static final int ITERATIONS = 3000 * 10;
    /** Use N tab with random values to limit jvm optimalizations */
    private static final int VALUES_N = 100;
    private static int[][] VALUES = new int[VALUES_N][];
    private static int[] VALUES_N_SEARCH = new int[VALUES_N];

    public static void main(String[] args)
    {
        init();
        if (args.length > 0)
        {
            if ("1".equals(args[0]))
            {
                perf(new FindLargestValuesImpl());
            }
            else if ("2".equals(args[0]))
            {
                perf(new FindLargestValuesImpl2());
            }
            else if ("3".equals(args[0]))
            {
                perf(new FindLargestValuesImpl3());
            }
            else if ("4".equals(args[0]))
            {
                perf(new FindLargestValuesImpl4());
            }
            else if ("5".equals(args[0]))
            {
                perf(new FindLargestValuesImpl5());
            }
            else if ("6".equals(args[0]))
            {
                perf(new FindLargestValuesImpl6());
            }
            else if ("7".equals(args[0]))
            {
                perf(new FindLargestValuesImpl7());
            }
        }
        else
        {
            perf(new FindLargestValuesImpl2());
            perf(new FindLargestValuesImpl3());
            perf(new FindLargestValuesImpl5());
            perf(new FindLargestValuesImpl6());
            perf(new FindLargestValuesImpl7());
            perf(new FindLargestValuesImpl());
        }
    }

    private static void init()
    {
        Random random = new Random();
        for (int i = 0; i < VALUES_N; i++)
        {
            int elementsNumber = random.nextInt(50) + 2;
            VALUES[i] = new int[elementsNumber];
            for (int j = 0; j < elementsNumber; j++)
            {
                VALUES[i][j] = random.nextInt(1000);
            }
            int searchNumber = random.nextInt(elementsNumber - 1) + 1;
            VALUES_N_SEARCH[i] = searchNumber;
        }
        for (int i = 0; i < VALUES_N; i++)
        {
            System.out.printf("%3s ", VALUES_N_SEARCH[i]);
            System.out.println(Arrays.toString(VALUES[i]));
        }
    }

    private static void perf(FindLargestValues finder)
    {
        long startTimeMs = System.currentTimeMillis();
        int sum = 0;
        for (int i = 0; i < ITERATIONS; i++)
        {
            sum += perfOneIteration(finder);
        }
        long timeMs = System.currentTimeMillis() - startTimeMs;
        System.out.printf("%,d iters with %s took %s ms (%s)\n", ITERATIONS * VALUES_N, finder, timeMs, sum);
    }

    private static int perfOneIteration(FindLargestValues finder)
    {
        int result = 0;
        for (int i = 0; i < VALUES_N; i++)
        {
            int[] found = finder.findMaxValues(VALUES[i], VALUES_N_SEARCH[i]);
            result = addFoundChecksum(result, found);
        }
        return result;
    }

    private static int addFoundChecksum(int result, int[] found)
    {
        for (int i = 0; i < found.length; i++)
        {
            result += found[i];
        }
        return result;
    }
}
