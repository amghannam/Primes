package org.aghannam.primes.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.aghannam.primes.Primes;
import org.junit.jupiter.api.Test;

class PrimesTest {
	// Test against this array
	int[] a = {2, 3, 5, 7, 11, 13, 17, 19}; 

	@Test
	void testNthPrime() {
		assertEquals(2, Primes.nthPrime(1));
		assertEquals(13, Primes.nthPrime(6)); 
		assertEquals(7919, Primes.nthPrime(1000)); 
	}

	@Test
	void testIsPrime() {
		assertTrue(Primes.isPrime(2)); 
		assertFalse(Primes.isPrime(4)); 
		assertTrue(Primes.isPrime(7919)); 
	}

	@Test
	void testCount() {
		assertEquals(a.length, Primes.count(a)); 
	}

	@Test
	void testCountSmallerThan() {
		assertEquals(2, Primes.countSmallerThan(5)); 
		assertEquals(a.length - 1, Primes.countSmallerThan(19)); 
	}

	@Test
	void testFirstN() {
		int[] b = Primes.Generator.firstN(a.length); 
		assertArrayEquals(a, b); 
	}

	@Test
	void testUpTo() {
		int[] b = Primes.Generator.upTo(20); 
		assertArrayEquals(a, b); 
	}

	@Test
	void testBetween() {
		int[] b = {5, 7}; 
		int[] c = Primes.Generator.between(4, 11); 
		assertArrayEquals(b, c); 
	}
	
	@Test
	void testNext() {
		for (int i = 0; i < a.length; i++) {
			assertEquals(a[i], Primes.Generator.next()); 
		}
		Primes.Generator.resetSequence(); 
		for (int i = 0; i < a.length; i++) {
			assertEquals(a[i], Primes.Generator.next()); 
		}
	}

}
