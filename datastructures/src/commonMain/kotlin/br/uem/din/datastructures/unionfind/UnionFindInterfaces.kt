package br.uem.din.datastructures.unionfind

/**
 * Interface somente-leitura para Union-Find (Disjoint Set Union).
 *
 * Define operações de consulta: encontrar representante, verificar conexão e metadados.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 21 — Data Structures for Disjoint Sets.
 *
 * @see MutableUnionFind
 */
public interface ImmutableUnionFind {
    /**
     * Número total de elementos.
     */
    public val size: Int

    /**
     * Número de conjuntos disjuntos atualmente mantidos.
     */
    public val numberOfSets: Int

    /**
     * Encontra o representante (raiz) do conjunto que contém o elemento [i].
     *
     * @param i o índice do elemento (0 ≤ i < [size]).
     * @return o índice do representante do conjunto.
     */
    public fun find(i: Int): Int

    /**
     * Verifica se os elementos [i] e [j] pertencem ao mesmo conjunto.
     *
     * @param i o índice do primeiro elemento.
     * @param j o índice do segundo elemento.
     * @return `true` se ambos estão no mesmo conjunto, `false` caso contrário.
     */
    public fun connected(i: Int, j: Int): Boolean
}

/**
 * Interface mutável para Union-Find (Disjoint Set Union).
 *
 * Estende [ImmutableUnionFind] adicionando a operação de união de conjuntos.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 21 — Data Structures for Disjoint Sets.
 *
 * @see ImmutableUnionFind
 */
public interface MutableUnionFind : ImmutableUnionFind {
    /**
     * Une os conjuntos que contêm os elementos [i] e [j].
     *
     * @param i o índice do primeiro elemento.
     * @param j o índice do segundo elemento.
     */
    public fun union(i: Int, j: Int)
}
