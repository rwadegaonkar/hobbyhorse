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

  Dec. 5th, 2001    - changed nextLong() to approximate the 
                      Poisson distribution with a normal 
                      distribution when mean is large.

  Nov. 19th, 2000   - added constructor with extra parameter for
                      specifying a Random object to use

*/

/**
 *
 * A generator of random numbers with Poisson distribution.
 *
 * (P) 2000, Laurentiu Cristofor
 *
 */

public class RandomPoissonDistribution
{
  private Random rand;
  private long mean;

  /**
   * Create a new generator of random numbers
   * with Poisson distribution of specified mean.
   *
   * @param mean   the mean of the Poisson distribution
   */
  public RandomPoissonDistribution(long mean)
  {
    this(mean, new Random());
  }

  /**
   * Create a new generator of random numbers with Poisson
   * distribution of specified mean and that uses <code>randgen</code>
   * as a source of random numbers.
   *
   * @param mean   the mean of the Poisson distribution
   * @param randgen   a Random object to be used by the generator.
   */
  public RandomPoissonDistribution(long mean, Random randgen)
  {
    this.mean = mean;
    rand = randgen;
  }

  /**
   * Return a random number with Poisson distribution.
   */
  public long nextLong()
  {
    // even though a Poisson distribution with mean 10 can be well
    // approximated by a normal distribution, I will use the standard
    // algorithm for means up to 100
    if (mean < 100)
      {
	// See Knuth, TAOCP, vol. 2, second print
	// section 3.4.1, algorithm Q on page 117
	// Q1. [Calculate exponential]
	double p = Math.exp(-(double)mean);
	long N = 0;
	double q = 1.0;

	while (true)
	  {
	    // Q2. [Get uniform variable]
	    double U = rand.nextDouble();
	    // Q3. [Multiply]
	    q = q * U;
	    // Q4. [Test]
	    if (q >= p)
	      N = N + 1;
	    else
	      return N;
	  }
      }
    // for larger mean values we approximate the Poisson distribution
    // using a normal distribution
    else
      {
	double z = rand.nextGaussian();
	long value = (long)(mean + z * Math.sqrt(mean) + 0.5);
	if (value >= 0)
	  return value;
	else return 0;
      }
  }

  /**
   * sample usage and testing
   */
  public static void main(String[] args)
  {
    RandomPoissonDistribution poisson 
      = new RandomPoissonDistribution(10);

    for (int i = 0; i <= 10; i++)
      System.out.println(poisson.nextLong());
  }
}
