/*

ARMiner - Association Rules Miner
Copyright (C) 2000  UMass/Boston - Computer Science Department


This program is free software; you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation; either version 2 of the License, or (at
your option) any later version.

This program is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program; if not, write to the Free Software
Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307
USA


The ARMiner Server was written by Dana Cristofor and Laurentiu
Cristofor.

The ARMiner Client was written by Abdelmajid Karatihy, Xiaoyong Kuang,
and Lung-Tsung Li.


The ARMiner package is currently maintained by Laurentiu Cristofor
(laur@cs.umb.edu).

*/

import java.util.Random;

/*

  Maintenance log started on November 19th, 2000

  Nov. 19th, 2000   - added constructor with extra parameter for
                      specifying a Random object to use

*/

/**
 *
 * A generator of random numbers with exponential distribution.
 *
 * (P) 2000, Laurentiu Cristofor
 *
 */

public class RandomExponentialDistribution
{
  private Random rand;
  private double mean;

  /**
   * Create a new generator of random numbers
   * with exponential distribution of mean 1.
   */
  public RandomExponentialDistribution()
  {
    this(1.0, new Random());
  }

  /**
   * Create a new generator of random numbers
   * with exponential distribution of specified mean.
   *
   * @param mean   the mean of the exponential distribution
   */
  public RandomExponentialDistribution(double mean)
  {
    this(mean, new Random());
  }

  /**
   * Create a new generator of random numbers with exponential
   * distribution of specified mean that will use <code>randgen</code>
   * as its source of random numbers.
   *
   * @param mean   the mean of the exponential distribution
   * @param randgen   a Random object to be used by the generator.
   */
  public RandomExponentialDistribution(double mean, Random randgen)
  {
    this.mean = mean;
    rand = randgen;
  }

  /**
   * Return a random number with exponential distribution.
   */
  public double nextDouble()
  {
    double val;

    do
      val = rand.nextDouble();
    while (val == 0.0);

    return mean * (-Math.log(val));
  }

  /**
   * sample usage and testing
   */
  public static void main(String[] args)
  {
    RandomExponentialDistribution exponential 
      = new RandomExponentialDistribution(10);

    for (int i = 0; i <= 10; i++)
      System.out.println(exponential.nextDouble());
  }
}
