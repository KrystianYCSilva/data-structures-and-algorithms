package br.uem.din.datastructures.tree

/**
 * Interface somente-leitura para uma Árvore Radix (Compressed Trie).
 *
 * Define operações de consulta: busca exata, busca por prefixo e tamanho.
 *
 * Referência: Morrison, D. R. "PATRICIA — Practical Algorithm To Retrieve Information
 *             Coded In Alphanumeric" (1968).
 *
 * @see MutableRadixTree
 */
public interface ImmutableRadixTree {
    /**
     * Número de chaves armazenadas na árvore.
     */
    public val size: Int

    /**
     * Busca uma chave na Radix Tree.
     *
     * @param key a chave a ser procurada.
     * @return `true` se a chave existir na árvore, `false` caso contrário.
     */
    public fun search(key: String): Boolean

    /**
     * Retorna todas as chaves que possuem o prefixo especificado.
     *
     * @param prefix o prefixo a ser buscado.
     * @return lista de todas as chaves com o prefixo dado.
     */
    public fun prefixSearch(prefix: String): List<String>
}

/**
 * Interface mutável para uma Árvore Radix (Compressed Trie).
 *
 * Estende [ImmutableRadixTree] adicionando operações de modificação: inserção e remoção.
 *
 * Referência: Morrison, D. R. "PATRICIA — Practical Algorithm To Retrieve Information
 *             Coded In Alphanumeric" (1968).
 *
 * @see ImmutableRadixTree
 */
public interface MutableRadixTree : ImmutableRadixTree {
    /**
     * Insere uma chave na Radix Tree.
     *
     * @param key a chave a ser inserida.
     */
    public fun insert(key: String)

    /**
     * Remove uma chave da Radix Tree.
     *
     * @param key a chave a ser removida.
     */
    public fun remove(key: String)
}
