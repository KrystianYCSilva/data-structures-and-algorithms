package br.uem.din.algorithms.greedy

import kotlin.test.Test
import kotlin.test.assertEquals

class GreedyAlgorithmsTest {

    @Test
    fun testActivitySelection() {
        val s = intArrayOf(1, 3, 0, 5, 8, 5)
        val f = intArrayOf(2, 4, 6, 7, 9, 9)
        // Atividades: 0(1-2), 1(3-4), 3(5-7), 4(8-9) -> Total 4
        // A lista deve ser ordenada por finish time antes.
        // A função assume ordenação. Vamos garantir.
        // Ordenando pelos tempos de término:
        // 0: 1-2
        // 1: 3-4
        // 2: 0-6
        // 3: 5-7
        // 5: 5-9
        // 4: 8-9
        // Índices originais ordenados por f: 0, 1, 2, 3, 5, 4 (com f: 2, 4, 6, 7, 9, 9)
        
        // Vamos usar uma entrada já ordenada para testar a lógica core
        val startSorted = intArrayOf(1, 3, 5, 8, 0, 5) // Ajustado manual para bater com a ordem de finish
        val finishSorted = intArrayOf(2, 4, 7, 9, 6, 9) // Ordenado: 2, 4, 6 (ops não), 7, 9, 9
        
        // Melhor construir entrada simples e clara
        val s2 = intArrayOf(1, 3, 0, 5, 8, 5)
        val f2 = intArrayOf(2, 4, 6, 7, 9, 9)
        // sorted by f: (1,2), (3,4), (0,6), (5,7), (8,9), (5,9)
        
        // A função activitySelection espera entrada JÁ ordenada.
        // Vamos passar arrays que correspondam a essa ordenação.
        val sFinal = intArrayOf(1, 3, 0, 5, 8, 5)
        val fFinal = intArrayOf(2, 4, 6, 7, 9, 9)
        
        val result = activitySelection(sFinal, fFinal)
        // Seleciona: (1,2) -> finish=2. Prox compativel: (3,4) -> f=4. Prox: (5,7) -> f=7. Prox: (8,9)
        // Indices: 0, 1, 3, 4
        assertEquals(listOf(0, 1, 3, 4), result)
    }

    @Test
    fun testFractionalKnapsack() {
        val w = doubleArrayOf(10.0, 20.0, 30.0)
        val v = doubleArrayOf(60.0, 100.0, 120.0)
        // Ratios: 6, 5, 4
        // Pega 10kg (60)
        // Pega 20kg (100) -> cap usada 30, falta 20
        // Pega 20kg de 30kg (ratio 4) -> 20 * 4 = 80
        // Total: 60 + 100 + 80 = 240
        assertEquals(240.0, fractionalKnapsack(w, v, 50.0))
    }
}
