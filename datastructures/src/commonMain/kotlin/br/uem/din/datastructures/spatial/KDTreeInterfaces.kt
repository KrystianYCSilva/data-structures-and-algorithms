package br.uem.din.datastructures.spatial

/**
 * Interface somente-leitura para uma KD-Tree.
 *
 * Define operações de consulta: busca por ponto, vizinho mais próximo e busca por região.
 *
 * Referência: Bentley, J. L. "Multidimensional binary search trees" (1975).
 *
 * @see MutableKDTree
 */
public interface ImmutableKDTree {
    /**
     * Número de pontos armazenados.
     */
    public val size: Int

    /**
     * Verifica se a árvore está vazia.
     *
     * @return `true` se a árvore não contiver pontos.
     */
    public fun isEmpty(): Boolean

    /**
     * Verifica se a KD-Tree contém um ponto específico.
     *
     * @param point as coordenadas do ponto.
     * @return `true` se o ponto existir na árvore.
     */
    public fun contains(point: DoubleArray): Boolean

    /**
     * Encontra o vizinho mais próximo de um ponto alvo.
     *
     * @param target as coordenadas do ponto alvo.
     * @return as coordenadas do vizinho mais próximo, ou `null` se a árvore estiver vazia.
     */
    public fun nearestNeighbor(target: DoubleArray): DoubleArray?

    /**
     * Busca por intervalo ortogonal (range search).
     *
     * @param lowerBound coordenadas mínimas do hiper-retângulo.
     * @param upperBound coordenadas máximas do hiper-retângulo.
     * @return lista de pontos dentro da região.
     */
    public fun rangeSearch(lowerBound: DoubleArray, upperBound: DoubleArray): List<DoubleArray>
}

/**
 * Interface mutável para uma KD-Tree.
 *
 * Estende [ImmutableKDTree] adicionando a operação de inserção.
 *
 * Referência: Bentley, J. L. "Multidimensional binary search trees" (1975).
 *
 * @see ImmutableKDTree
 */
public interface MutableKDTree : ImmutableKDTree {
    /**
     * Insere um ponto na KD-Tree.
     *
     * @param point as coordenadas do ponto.
     */
    public fun insert(point: DoubleArray)
}
