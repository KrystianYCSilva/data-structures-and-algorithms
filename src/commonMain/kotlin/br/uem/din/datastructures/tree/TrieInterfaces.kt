package br.uem.din.datastructures.tree

/**
 * Interface somente-leitura para uma Trie (Árvore de Prefixos).
 *
 * Define operações de consulta: verificação de pertinência e busca por prefixo.
 * Segue o padrão Kotlin stdlib de separar interfaces imutáveis e mutáveis.
 *
 * @param Key o tipo das chaves que compõem as sequências.
 *
 * Referência: Fredkin, E. "Trie Memory" (1960);
 *             Sedgewick, R. & Wayne, K. "Algorithms", Cap. 5.2 — Tries.
 *
 * @see MutableTrie
 */
public interface ImmutableTrie<Key> {
    /**
     * Verifica se a Trie contém a sequência completa especificada.
     *
     * @param list a sequência de chaves a ser verificada.
     * @return `true` se a sequência existir na Trie, `false` caso contrário.
     */
    public fun contains(list: List<Key>): Boolean

    /**
     * Retorna todas as sequências armazenadas na Trie que começam com o prefixo especificado.
     *
     * @param prefix o prefixo a ser buscado.
     * @return lista de todas as sequências completas que possuem o prefixo dado.
     */
    public fun collections(prefix: List<Key>): List<List<Key>>
}

/**
 * Interface mutável para uma Trie (Árvore de Prefixos).
 *
 * Estende [ImmutableTrie] adicionando operações de modificação: inserção e remoção.
 *
 * @param Key o tipo das chaves que compõem as sequências.
 *
 * Referência: Fredkin, E. "Trie Memory" (1960);
 *             Sedgewick, R. & Wayne, K. "Algorithms", Cap. 5.2 — Tries.
 *
 * @see ImmutableTrie
 */
public interface MutableTrie<Key> : ImmutableTrie<Key> {
    /**
     * Insere uma sequência de chaves na Trie.
     *
     * @param list a sequência de chaves a ser inserida.
     */
    public fun insert(list: List<Key>)

    /**
     * Remove uma sequência da Trie.
     *
     * @param list a sequência de chaves a ser removida.
     */
    public fun remove(list: List<Key>)
}
