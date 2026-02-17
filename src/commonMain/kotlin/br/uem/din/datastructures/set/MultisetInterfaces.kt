package br.uem.din.datastructures.set

/**
 * Interface somente-leitura para um Multiset (Bag).
 *
 * Define as operações de consulta: contagem, pertinência, tamanho e elementos distintos.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis.
 *
 * @param T o tipo dos elementos armazenados.
 *
 * Referência: Blizard, W. D. "Multiset Theory" (1989), Notre Dame Journal of Formal Logic, 30(1).
 *
 * @see MutableMultiset
 */
public interface ImmutableMultiset<T> : Iterable<T> {
    /**
     * Número total de elementos, incluindo duplicatas.
     */
    public val size: Int

    /**
     * Verifica se o multiset está vazio.
     *
     * @return `true` se não houver elementos.
     */
    public fun isEmpty(): Boolean

    /**
     * Retorna a contagem (multiplicidade) de um elemento.
     *
     * @param element o elemento cuja contagem será consultada.
     * @return o número de ocorrências, ou 0 se não existir.
     */
    public fun count(element: T): Int

    /**
     * Verifica se o multiset contém pelo menos uma ocorrência do elemento.
     *
     * @param element o elemento a ser verificado.
     * @return `true` se existir, `false` caso contrário.
     */
    public fun contains(element: T): Boolean

    /**
     * Retorna o conjunto de elementos distintos (sem duplicatas).
     *
     * @return um [Set] contendo os elementos distintos.
     */
    public fun distinctElements(): Set<T>

    /**
     * Número de elementos distintos no multiset.
     */
    public val distinctCount: Int
}

/**
 * Interface mutável para um Multiset (Bag).
 *
 * Estende [ImmutableMultiset] adicionando operações de modificação: adição, remoção e limpeza.
 *
 * @param T o tipo dos elementos armazenados.
 *
 * Referência: Blizard, W. D. "Multiset Theory" (1989), Notre Dame Journal of Formal Logic, 30(1).
 *
 * @see ImmutableMultiset
 */
public interface MutableMultiset<T> : ImmutableMultiset<T> {
    /**
     * Adiciona uma ou mais ocorrências de um elemento.
     *
     * @param element o elemento a ser adicionado.
     * @param occurrences o número de ocorrências a adicionar (padrão: 1).
     */
    public fun add(element: T, occurrences: Int = 1)

    /**
     * Remove uma ou mais ocorrências de um elemento.
     *
     * @param element o elemento a ser removido.
     * @param occurrences o número de ocorrências a remover (padrão: 1).
     * @return `true` se o elemento existia, `false` caso contrário.
     */
    public fun remove(element: T, occurrences: Int = 1): Boolean

    /**
     * Remove todos os elementos do multiset.
     */
    public fun clear()
}
