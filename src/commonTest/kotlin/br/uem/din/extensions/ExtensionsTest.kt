package br.uem.din.extensions

import kotlin.test.*

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

    @Test
    fun testSwapBasic() {
        val list = mutableListOf(1, 2, 3, 4, 5)
        list.swap(0, 4)
        assertEquals(listOf(5, 2, 3, 4, 1), list)
    }

    @Test
    fun testSwapSameIndex() {
        val list = mutableListOf("a", "b", "c")
        list.swap(1, 1)
        assertEquals(listOf("a", "b", "c"), list)
    }

    @Test
    fun testSwapAdjacentElements() {
        val list = mutableListOf(10, 20, 30)
        list.swap(0, 1)
        assertEquals(listOf(20, 10, 30), list)
    }

    @Test
    fun testSwapFirstAndLast() {
        val list = mutableListOf('x', 'y', 'z')
        list.swap(0, 2)
        assertEquals(listOf('z', 'y', 'x'), list)
    }

    @Test
    fun testSwapOutOfBoundsThrows() {
        val list = mutableListOf(1, 2, 3)
        assertFailsWith<IndexOutOfBoundsException> { list.swap(0, 5) }
        assertFailsWith<IndexOutOfBoundsException> { list.swap(-1, 0) }
    }

    @Test
    fun testSwapSingleElementList() {
        val list = mutableListOf(42)
        list.swap(0, 0)
        assertEquals(listOf(42), list)
    }

    @Test
    fun testPowerSetBasic() {
        val ps = listOf(1, 2).powerSet()
        assertEquals(4, ps.size)
        assertTrue(ps.any { it.isEmpty() })
        assertTrue(ps.any { it == listOf(1) })
        assertTrue(ps.any { it == listOf(2) })
        assertTrue(ps.any { it.toSet() == setOf(1, 2) })
    }

    @Test
    fun testPowerSetEmpty() {
        val ps = emptyList<Int>().powerSet()
        assertEquals(1, ps.size)
        assertTrue(ps[0].isEmpty())
    }

    @Test
    fun testPowerSetSingleElement() {
        val ps = listOf("a").powerSet()
        assertEquals(2, ps.size)
    }

    @Test
    fun testPermutationsEmpty() {
        val perms = emptyList<Int>().permutations()
        assertEquals(1, perms.size)
        assertTrue(perms[0].isEmpty())
    }

    @Test
    fun testPermutationsSingleElement() {
        val perms = listOf(42).permutations()
        assertEquals(1, perms.size)
        assertEquals(listOf(42), perms[0])
    }

    @Test
    fun testPermutationsContainsAllArrangements() {
        val perms = listOf(1, 2, 3).permutations()
        assertEquals(6, perms.size)
        assertTrue(perms.contains(listOf(1, 2, 3)))
        assertTrue(perms.contains(listOf(3, 2, 1)))
        assertTrue(perms.contains(listOf(2, 1, 3)))
    }

    @Test
    fun testCombinationsK0() {
        val combs = listOf(1, 2, 3).combinations(0)
        assertEquals(1, combs.size)
        assertTrue(combs[0].isEmpty())
    }

    @Test
    fun testCombinationsKEqualsN() {
        val combs = listOf(1, 2, 3).combinations(3)
        assertEquals(1, combs.size)
        assertEquals(setOf(1, 2, 3), combs[0].toSet())
    }

    @Test
    fun testCombinationsContents() {
        val combs = listOf(1, 2, 3, 4).combinations(2)
        assertEquals(6, combs.size)
        assertTrue(combs.all { it.size == 2 })
    }
}
