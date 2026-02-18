package br.uem.din.algorithms.network

import kotlin.test.Test
import kotlin.test.assertEquals

class NetworkFlowTest {

    @Test
    fun testEdmondsKarp() {
        // Exemplo clássico
        // 0 -> 1 (16), 0 -> 2 (13)
        // 1 -> 2 (10), 1 -> 3 (12)
        // 2 -> 1 (4), 2 -> 4 (14)
        // 3 -> 2 (9), 3 -> 5 (20)
        // 4 -> 3 (7), 4 -> 5 (4)
        // Max Flow esperado: 23
        
        val capacity = Array(6) { DoubleArray(6) }
        capacity[0][1] = 16.0
        capacity[0][2] = 13.0
        capacity[1][2] = 10.0
        capacity[1][3] = 12.0
        capacity[2][1] = 4.0
        capacity[2][4] = 14.0
        capacity[3][2] = 9.0
        capacity[3][5] = 20.0
        capacity[4][3] = 7.0
        capacity[4][5] = 4.0

        val maxFlow = edmondsKarp(capacity, 0, 5)
        assertEquals(23.0, maxFlow)
    }
}
