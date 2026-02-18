package br.uem.din.algorithms.sorting

import kotlin.math.floor

/**
 * Ordena a lista de números de ponto flutuante usando o algoritmo Bucket Sort.
 *
 * O Bucket Sort funciona distribuindo os elementos de um array em vários "baldes" (buckets).
 * Cada balde é então ordenado individualmente (neste caso, usando Insertion Sort).
 * Ideal para dados uniformemente distribuídos no intervalo [0, 1).
 *
 * Esta implementação normaliza os dados se eles não estiverem em [0, 1) ou
 * adapta os buckets ao intervalo real dos dados.
 *
 * Complexidade Temporal:
 * - Médio caso: O(n + k), onde k é o número de buckets.
 * - Pior caso: O(n^2) (se todos elementos caírem no mesmo bucket e usarmos insertion sort).
 *
 * Complexidade Espacial: O(n + k).
 *
 * Estável: Sim (se o algoritmo de ordenação do bucket for estável).
 *
 * @param list a lista mutável de Double a ser ordenada.
 * @param numBuckets número de baldes a utilizar (padrão: raiz quadrada de n ou tamanho de n).
 */
public fun bucketSort(list: MutableList<Double>, numBuckets: Int = list.size) {
    if (list.isEmpty()) return

    // 1. Criar buckets vazios
    val buckets = ArrayList<ArrayList<Double>>(numBuckets)
    for (i in 0 until numBuckets) {
        buckets.add(ArrayList())
    }

    // Encontrar max e min para normalizar
    val max = list.maxOrNull() ?: 1.0
    val min = list.minOrNull() ?: 0.0
    val range = max - min

    // 2. Colocar elementos nos buckets
    for (element in list) {
        // Normaliza para index 0..numBuckets-1
        // Se range == 0 (todos iguais), cai tudo no bucket 0
        var bucketIndex = if (range == 0.0) 0 else ((element - min) / range * (numBuckets - 1)).toInt()
        
        // Garante bounds
        if (bucketIndex < 0) bucketIndex = 0
        if (bucketIndex >= numBuckets) bucketIndex = numBuckets - 1
        
        buckets[bucketIndex].add(element)
    }

    // 3. Ordenar buckets individuais e concatenar
    list.clear()
    for (bucket in buckets) {
        // Usamos insertion sort (já implementado na stdlib como sort() para Lists pequenas é eficiente, 
        // ou nossa implementação customizada. Aqui usaremos sort() da stdlib por simplicidade e performance)
        bucket.sort()
        list.addAll(bucket)
    }
}
