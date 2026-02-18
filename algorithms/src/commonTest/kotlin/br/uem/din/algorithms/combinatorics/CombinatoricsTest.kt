package br.uem.din.algorithms.combinatorics

import kotlin.test.Test
import kotlin.test.assertEquals

class CombinatoricsTest {

    @Test
    fun testCombinations() {
        val list = listOf(1, 2, 3)
        val combs = combinations(list, 2)
        // Esperado: [1,2], [1,3], [2,3]
        assertEquals(3, combs.size)
        assertEquals(listOf(1, 2), combs[0])
        assertEquals(listOf(1, 3), combs[1])
        assertEquals(listOf(2, 3), combs[2])
    }

    @Test
    fun testPowerSet() {
        val list = listOf(1, 2)
        val pSet = powerSet(list)
        // [], [2], [1], [1, 2] (ordem pode variar dependendo da implementação recursiva)
        assertEquals(4, pSet.size)
        assertEquals(emptyList(), pSet[0])
    }
}
