package br.uem.din.algorithms.string

import kotlin.test.Test
import kotlin.test.assertEquals

class StringAlgorithmsTest {

    private val text = "ABABDABACDABABCABAB"
    private val pattern = "ABABCABAB"

    @Test
    fun testNaiveSearch() {
        assertEquals(10, naiveStringSearch(text, pattern))
        assertEquals(-1, naiveStringSearch(text, "XYZ"))
    }

    @Test
    fun testKMPSearch() {
        assertEquals(10, kmpSearch(text, pattern))
        assertEquals(-1, kmpSearch(text, "XYZ"))
        assertEquals(0, kmpSearch("AAAA", "A"))
    }

    @Test
    fun testRabinKarpSearch() {
        assertEquals(10, rabinKarpSearch(text, pattern))
        assertEquals(-1, rabinKarpSearch(text, "XYZ"))
    }

    @Test
    fun testBoyerMooreSearch() {
        assertEquals(10, boyerMooreSearch(text, pattern))
        assertEquals(-1, boyerMooreSearch(text, "XYZ"))
    }
}
