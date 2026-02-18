package br.uem.din.datastructures.probabilistic

/**
 * Interface somente-leitura para um Filtro de Bloom.
 *
 * Define operações de consulta: verificação probabilística de pertinência e metadados.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis.
 *
 * Referência: Bloom, B. H. "Space/Time Trade-offs in Hash Coding with Allowable Errors" (1970).
 *
 * @see MutableBloomFilter
 */
public interface ImmutableBloomFilter {
    /**
     * Verifica se um elemento possivelmente pertence ao conjunto.
     *
     * @param element a string a ser verificada.
     * @return `true` se o elemento possivelmente pertence ao conjunto, `false` se definitivamente não.
     */
    public fun contains(element: String): Boolean

    /**
     * Retorna o tamanho do bit array interno (m).
     *
     * @return o número de bits no filtro.
     */
    public fun size(): Int

    /**
     * Retorna o número de funções de hash utilizadas (k).
     *
     * @return a quantidade de funções de hash.
     */
    public fun countHashFunctions(): Int
}

/**
 * Interface mutável para um Filtro de Bloom.
 *
 * Estende [ImmutableBloomFilter] adicionando a operação de inserção.
 *
 * Referência: Bloom, B. H. "Space/Time Trade-offs in Hash Coding with Allowable Errors" (1970).
 *
 * @see ImmutableBloomFilter
 */
public interface MutableBloomFilter : ImmutableBloomFilter {
    /**
     * Adiciona um elemento ao filtro, marcando os bits correspondentes.
     *
     * @param element a string a ser adicionada ao filtro.
     */
    public fun add(element: String)
}
