package br.uem.din.extensions

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class ExtensionsTest {

    @Test
    fun testStringExtensions() {
        assertEquals(3, "kitten" levenshteinTo "sitting")
        assertEquals(2, "abc".findKMP("c"))
        assertEquals(3, "abcde" lcsLength "ace")
    }

    @Test
    fun testMathExtensions() {
        assertEquals(6, 48L gcd 18L)
        assertEquals(12, 4.lcm(6))
        assertTrue(17.isPrime())
    }

    @Test
    fun testCollectionExtensions() {
        val list = listOf(1, 2, 3)
        val perms = list.permutations()
        assertEquals(6, perms.size)
        
        val combs = list.combinations(2)
        assertEquals(3, combs.size)
    }
}
