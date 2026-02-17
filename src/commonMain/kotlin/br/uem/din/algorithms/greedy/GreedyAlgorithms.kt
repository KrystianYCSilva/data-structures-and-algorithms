package br.uem.din.algorithms.greedy

import br.uem.din.datastructures.queue.priorityQueueOf

/**
 * Seleção de Atividades (Activity Selection Problem).
 *
 * Seleciona o número máximo de atividades compatíveis.
 *
 * @param start array de tempos de início.
 * @param finish array de tempos de término.
 * @return lista de índices das atividades selecionadas (baseado na ordenação original se ordenado, ou índices após sort).
 *
 * NOTA: Assume-se que as atividades JÁ ESTÃO ORDENADAS por tempo de término crescente.
 * Se não estiverem, quem chama deve ordenar.
 *
 * Complexidade: O(n) (se já ordenado)
 */
public fun activitySelection(start: IntArray, finish: IntArray): List<Int> {
    val n = start.size
    val result = mutableListOf<Int>()
    
    if (n == 0) return result

    // A primeira atividade sempre é selecionada
    var i = 0
    result.add(0)

    for (j in 1 until n) {
        if (start[j] >= finish[i]) {
            result.add(j)
            i = j
        }
    }
    return result
}

/**
 * Problema da Mochila Fracionária (Fractional Knapsack).
 *
 * @param weights pesos dos itens.
 * @param values valores dos itens.
 * @param capacity capacidade máxima.
 * @return valor máximo possível (pode ser fracionário).
 *
 * Complexidade: O(n log n)
 */
public fun fractionalKnapsack(weights: DoubleArray, values: DoubleArray, capacity: Double): Double {
    val n = weights.size
    val ratio = Array(n) { i -> Pair(values[i] / weights[i], i) }
    
    // Ordena por razão valor/peso decrescente
    ratio.sortWith(Comparator { a, b -> b.first.compareTo(a.first) })

    var currentWeight = 0.0
    var finalValue = 0.0

    for ((r, i) in ratio) {
        if (currentWeight + weights[i] <= capacity) {
            currentWeight += weights[i]
            finalValue += values[i]
        } else {
            val remain = capacity - currentWeight
            finalValue += values[i] * (remain / weights[i])
            break
        }
    }
    return finalValue
}

/**
 * Huffman Coding (construção da árvore).
 *
 * @param chars caracteres.
 * @param freqs frequências dos caracteres.
 * @return Raiz da árvore de Huffman.
 */
public fun huffmanCoding(chars: CharArray, freqs: IntArray): HuffmanNode? {
    val n = chars.size
    if (n == 0) return null

    // Min-Priority Queue
    val pq = priorityQueueOf<HuffmanNode>(Comparator { a, b -> a.freq - b.freq })

    for (i in 0 until n) {
        pq.enqueue(HuffmanNode(chars[i], freqs[i]))
    }

    while (pq.size > 1) {
        val left = pq.dequeue()!!
        val right = pq.dequeue()!!

        val f = HuffmanNode('-', left.freq + right.freq)
        f.left = left
        f.right = right
        
        pq.enqueue(f)
    }
    return pq.dequeue()
}

public class HuffmanNode(
    public val char: Char,
    public val freq: Int,
    public var left: HuffmanNode? = null,
    public var right: HuffmanNode? = null
)
