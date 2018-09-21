/*
 * File: Primes.java
 */
package org.aghannam.primes;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class implements some common prime number operations, including a fairly
 * efficient prime number generator.
 * 
 * @author Ahmed Ghannam
 *
 */
public class Primes {
	/**
	 * The default maximum value up to which a random prime will be chosen.
	 */
	private static final int MAX_VALUE = 10000000;

	/**
	 * For random number generation.
	 */
	private static Random rnd = ThreadLocalRandom.current();

	private Primes() {
		// Suppressed to prevent instantiability.
	}

	/**
	 * Returns the nth prime number.
	 * 
	 * @param n
	 *            the 'index' of the desired prime number
	 * @return the nth prime number
	 */
	public static int nthPrime(int n) {
		if (n == 1)
			return 2;
		if (n == 2)
			return 3;
		int candidate = 5;
		int step = 4;
		for (int count = 2; count < n; step = 6 - step, candidate += step) {
			if (isPrime(candidate))
				count++;
		}
		return candidate - step;
	}

	/**
	 * Returns the number of primes in the specified list.
	 * 
	 * @param a
	 *            the list containing the numbers
	 * @return the number of primes in this list
	 */
	public static int count(int[] a) {
		int count = 0;
		for (int i : a) {
			if (isPrime(i))
				count++;
		}
		return count;
	}

	/**
	 * Returns the number of primes smaller than the specified value {@code n}.
	 * 
	 * @param n
	 *            the value smaller than which to count primes
	 * @return the number of primes smaller than {@code n}
	 */
	public static int countSmallerThan(int n) {
		int count = 0;
		for (int i = 2; i < n; i++) {
			if (isPrime(i))
				count++;
		}
		return count;
	}

	/**
	 * Returns {@code true} if and only if the specified value {@code n} is a prime
	 * number.
	 * 
	 * @param n
	 *            the value to test for primality
	 * @return {@code true} if {@code n} is a prime, {@code false} otherwise
	 */
	public static boolean isPrime(int n) {
		if (n < 2)
			return false;
		if (n == 2 || n == 3)
			return true;
		if (n % 2 == 0 || n % 3 == 0)
			return false;
		int sqrtN = (int) Math.sqrt(n) + 1;
		for (long i = 6L; i <= sqrtN; i += 6) {
			if (n % (i - 1) == 0 || n % (i + 1) == 0)
				return false;
		}
		return true;
	}

	/**
	 * Returns a random prime number.
	 * 
	 * @return a random prime
	 */
	public static int random() {
		int[] a = Generator.upTo(MAX_VALUE);
		return a[rnd.nextInt(a.length)];

	}

	/**
	 * Returns a random prime number up to, but not including, the specified bound.
	 * 
	 * @param bound
	 *            the upper bound for the generated value (exclusive)
	 * @return a random prime up to the specified bound
	 */
	public static int random(int bound) {
		int[] a = Generator.upTo(bound);
		return a[rnd.nextInt(a.length)];
	}

	/**
	 * Returns a random prime number in the specified range.
	 * 
	 * @param lower
	 *            the lower bound (inclusive, if prime)
	 * @param upper
	 *            the upper bound (exclusive)
	 * @return a random prime in the specified range
	 */
	public static int random(int lower, int upper) {
		int[] a = Generator.between(lower, upper);
		return a[rnd.nextInt(a.length)];
	}

	/**
	 * A sequential prime number generator with multiple useful functions.
	 * 
	 * @author Ahmed Ghannam
	 *
	 */
	public static class Generator {
		/**
		 * Current prime candidate used for sequential generation by method
		 * {@code next}. By default, it is set to 2 (the first prime number).
		 */
		private static int candidate = 2;

		/**
		 * Returns an array of the first {@code n} prime numbers.
		 * 
		 * @param n
		 *            the number of primes to generate
		 * @return an array of the first {@code n} primes
		 */
		public static int[] firstN(int n) {
			int candidate = 2;
			int[] a = new int[n];
			int i = 0;
			while (i < n) {
				if (isPrime(candidate)) {
					a[i++] = candidate++;
				} else {
					candidate++;
				}
			}
			return a;
		}

		/**
		 * Returns an array containing all prime numbers up to, but not including, the
		 * specified value {@code n}.
		 * 
		 * @param n
		 *            the upper bound of the primes to be generated
		 * @return an array containing all primes up to {@code n}
		 */
		public static int[] upTo(int n) {
			return firstN(countSmallerThan(n)); 
		}

		/**
		 * Returns an array containing all prime numbers between the specified
		 * <b>lower</b> and <b>upper</b> bounds.
		 * 
		 * @param lower
		 *            the lower bound (inclusive, if prime)
		 * @param upper
		 *            the upper bound (exclusive)
		 * @return an array of all primes between lower and upper
		 */
		public static int[] between(int lower, int upper) {
			if (lower == 2 && upper > lower)
				return firstN(countSmallerThan(upper));
			var primes = new ArrayList<Integer>();
			for (int i = lower; i < upper; i++) {
				if (isPrime(i))
					primes.add(i);
			}
			return primes.stream().mapToInt(i -> i).toArray();
		}

		/**
		 * Returns the next prime number in this generator's natural-ordered sequence.
		 * <p>
		 * To restart the generator or set a different starting point, use {@code reset}
		 * or {@code startFrom}, respectively.
		 * 
		 * @return the next prime in this generator's sequence, in natural order
		 */
		public static int next() {
			while (!isPrime(candidate)) {
				candidate++;
			}
			return candidate++;
		}

		/**
		 * Resets the sequence of this generator such that a subsequent call to
		 * {@code next} starts from 2, the default beginning value.
		 * 
		 */
		public static void reset() {
			startFrom(2);
		}

		/**
		 * Sets the starting point of the generator's sequence to the specified value
		 * {@code n}, such that a subsequent call to {@code next} generates the prime
		 * that immediately follows {@code n} (assuming {@code n} is not itself a
		 * prime).
		 * 
		 * @param n
		 *            the desired starting point of this generator
		 */
		public static void startFrom(int n) {
			candidate = n < 2 ? 2 : n;
		}
	}
}
