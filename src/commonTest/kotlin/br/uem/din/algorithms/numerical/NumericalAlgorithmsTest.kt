package br.uem.din.algorithms.numerical

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class NumericalAlgorithmsTest {

    @Test
    fun testGCD() {
        assertEquals(6, gcd(48, 18))
        assertEquals(1, gcd(17, 13))
    }

    @Test
    fun testLCM() {
        assertEquals(144, lcm(48, 18))
    }

    @Test
    fun testExtendedGCD() {
        val (gcd, x, y) = extendedGcd(35, 15)
        assertEquals(5, gcd)
        // 35*1 + 15*(-2) = 35 - 30 = 5
        assertEquals(1, x)
        assertEquals(-2, y)
    }

    @Test
    fun testSieve() {
        val primes = sieveOfEratosthenes(30)
        assertEquals(listOf(2, 3, 5, 7, 11, 13, 17, 19, 23, 29), primes)
    }

    @Test
    fun testIsPrime() {
        assertTrue(isPrime(997))
        assertFalse(isPrime(100))
        assertFalse(isPrime(1))
    }
    
    @Test
    fun testModularExp() {
        assertEquals(1, modularExponentiation(2, 0, 10))
        assertEquals(4, modularExponentiation(2, 2, 10))
        assertEquals(2, modularExponentiation(2, 5, 10)) // 32 % 10 = 2
    }
}
