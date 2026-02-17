package br.uem.din.datastructures.skiplist

/**
 * Interface somente-leitura para uma Skip List.
 *
 * Define as operações de consulta: pertinência, tamanho e conversão para lista.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Pugh, W. "Skip Lists: A Probabilistic Alternative to Balanced Trees" (1990).
 *
 * @see MutableSkipList
 */
public interface ReadOnlySkipList<T : Comparable<T>> : Iterable<T> {
    /**
     * Número de elementos armazenados.
     */
    public val size: Int

    /**
     * Verifica se está vazia.
     *
     * @return `true` se não houver elementos.
     */
    public fun isEmpty(): Boolean

    /**
     * Verifica se contém o elemento especificado.
     *
     * @param element o elemento a ser procurado.
     * @return `true` se existir, `false` caso contrário.
     */
    public fun contains(element: T): Boolean

    /**
     * Retorna os elementos ordenados como [List] imutável.
     *
     * @return lista com todos os elementos em ordem crescente.
     */
    public fun toList(): List<T>
}

/**
 * Interface mutável para uma Skip List.
 *
 * Estende [ReadOnlySkipList] adicionando operações de modificação: inserção, remoção e limpeza.
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Pugh, W. "Skip Lists: A Probabilistic Alternative to Balanced Trees" (1990).
 *
 * @see ReadOnlySkipList
 */
public interface MutableSkipList<T : Comparable<T>> : ReadOnlySkipList<T> {
    /**
     * Insere um elemento, mantendo a ordenação.
     *
     * @param element o elemento a ser inserido.
     */
    public fun insert(element: T)

    /**
     * Remove um elemento.
     *
     * @param element o elemento a ser removido.
     * @return `true` se removido, `false` se não existia.
     */
    public fun remove(element: T): Boolean

    /**
     * Remove todos os elementos.
     */
    public fun clear()
}
