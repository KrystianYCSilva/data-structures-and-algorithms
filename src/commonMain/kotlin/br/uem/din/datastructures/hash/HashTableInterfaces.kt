package br.uem.din.datastructures.hash

/**
 * Interface somente-leitura para tabelas hash com endereçamento aberto.
 *
 * Define operações de consulta: busca por chave, verificação de pertinência e tamanho.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis.
 *
 * @param K o tipo das chaves.
 * @param V o tipo dos valores.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 11 — Hash Tables.
 *
 * @see MutableOpenHashTable
 */
public interface OpenHashTable<K : Any, V> : Iterable<Pair<K, V>> {
    /**
     * Número de pares chave-valor armazenados na tabela.
     */
    public val size: Int

    /**
     * Verifica se a tabela está vazia.
     *
     * @return `true` se não houver elementos.
     */
    public fun isEmpty(): Boolean = size == 0

    /**
     * Recupera o valor associado à chave especificada.
     *
     * @param key a chave a ser procurada.
     * @return o valor associado, ou `null` se não existir.
     */
    public fun get(key: K): V?

    /**
     * Verifica se a tabela contém a chave especificada.
     *
     * @param key a chave a ser verificada.
     * @return `true` se existir, `false` caso contrário.
     */
    public fun contains(key: K): Boolean

    /**
     * Retorna todos os pares chave-valor armazenados na tabela.
     *
     * @return lista de pares (chave, valor).
     */
    public fun entries(): List<Pair<K, V>>

    /**
     * Retorna todas as chaves armazenadas na tabela.
     *
     * @return lista de chaves.
     */
    public fun keys(): List<K> = entries().map { it.first }

    /**
     * Retorna todos os valores armazenados na tabela.
     *
     * @return lista de valores.
     */
    public fun values(): List<V> = entries().map { it.second }
}

/**
 * Interface mutável para tabelas hash com endereçamento aberto.
 *
 * Estende [OpenHashTable] adicionando operações de modificação: inserção e remoção.
 *
 * Implementações concretas incluem [OpenAddressingHashTable] e [CuckooHashTable].
 *
 * @param K o tipo das chaves.
 * @param V o tipo dos valores.
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 11 — Hash Tables.
 *
 * @see OpenHashTable
 */
public interface MutableOpenHashTable<K : Any, V> : OpenHashTable<K, V> {
    /**
     * Insere ou atualiza o par chave-valor na tabela.
     *
     * @param key a chave a ser inserida ou atualizada.
     * @param value o valor associado.
     */
    public fun put(key: K, value: V)

    /**
     * Remove o par chave-valor associado à chave.
     *
     * @param key a chave a ser removida.
     * @return o valor removido, ou `null` se não existia.
     */
    public fun remove(key: K): V?
}
