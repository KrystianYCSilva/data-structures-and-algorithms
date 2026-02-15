package br.uem.din.datastructures.array

/**
 * Arrays paralelos (Parallel Arrays / Structure of Arrays — SoA).
 *
 * Em vez de manter um array de objetos (Array of Structures — AoS), utiliza arrays separados
 * para cada campo (id, nome, pontuação). Essa organização melhora a localidade de cache
 * ao processar campos específicos isoladamente (e.g., calcular média de pontuações),
 * pois os dados de um mesmo campo ficam contíguos na memória.
 *
 * Complexidades:
 * - [add]: O(1) amortizado (com redimensionamento por duplicação)
 * - [get] / [getId] / [getName] / [getScore]: O(1)
 *
 * @param initialCapacity capacidade inicial dos arrays internos (padrão: 10).
 *
 * Referência: Drepper, U. "What Every Programmer Should Know About Memory" (2007);
 *             padrão SoA amplamente discutido em otimização de cache para Data-Oriented Design.
 */
class ParallelArray(initialCapacity: Int = 10) {
    private var ids = IntArray(initialCapacity)
    private var names = Array<String?>(initialCapacity) { null }
    private var scores = DoubleArray(initialCapacity)
    
    /** Número de registros armazenados. */
    var size = 0
        private set

    /**
     * Adiciona um novo registro com id, nome e pontuação.
     *
     * Complexidade: O(1) amortizado.
     *
     * @param id identificador do registro.
     * @param name nome associado.
     * @param score pontuação associada.
     */
    fun add(id: Int, name: String, score: Double) {
        if (size == ids.size) resize()
        ids[size] = id
        names[size] = name
        scores[size] = score
        size++
    }

    /**
     * Retorna o registro completo na posição especificada como uma [Triple].
     *
     * Complexidade: O(1).
     *
     * @param index a posição do registro (0-based).
     * @return [Triple] contendo (id, name, score).
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun get(index: Int): Triple<Int, String, Double> {
        checkIndex(index)
        return Triple(ids[index], names[index] ?: "", scores[index])
    }
    
    /**
     * Retorna o id do registro na posição especificada.
     *
     * Complexidade: O(1).
     *
     * @param index a posição do registro (0-based).
     * @return o id do registro.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun getId(index: Int): Int {
        checkIndex(index)
        return ids[index]
    }
    
    /**
     * Retorna o nome do registro na posição especificada.
     *
     * Complexidade: O(1).
     *
     * @param index a posição do registro (0-based).
     * @return o nome do registro, ou `null` se não definido.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun getName(index: Int): String? {
        checkIndex(index)
        return names[index]
    }
    
    /**
     * Retorna a pontuação do registro na posição especificada.
     *
     * Complexidade: O(1).
     *
     * @param index a posição do registro (0-based).
     * @return a pontuação do registro.
     * @throws IndexOutOfBoundsException se o índice for inválido.
     */
    fun getScore(index: Int): Double {
        checkIndex(index)
        return scores[index]
    }

    private fun checkIndex(index: Int) {
        if (index < 0 || index >= size) throw IndexOutOfBoundsException("Index: $index, Size: $size")
    }

    private fun resize() {
        val newCapacity = ids.size * 2
        ids = ids.copyOf(newCapacity)
        names = names.copyOf(newCapacity)
        scores = scores.copyOf(newCapacity)
    }
}
