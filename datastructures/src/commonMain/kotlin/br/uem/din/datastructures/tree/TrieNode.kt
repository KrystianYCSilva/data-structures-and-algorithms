package br.uem.din.datastructures.tree

/**
 * Nó de uma árvore Trie (Trie Node / Prefix Tree Node).
 *
 * Cada nó armazena uma chave parcial, uma referência ao nó pai e um mapa de filhos
 * indexado pelas chaves. O campo [isTerminating] indica se este nó representa
 * o final de uma sequência completa inserida na Trie.
 *
 * @param Key o tipo das chaves que compõem as sequências armazenadas na Trie.
 * @property key a chave associada a este nó (pode ser `null` para a raiz).
 * @property parent referência ao nó pai, ou `null` se este for a raiz.
 * @property children mapa dos nós filhos, indexado por chave.
 * @property isTerminating indica se este nó marca o fim de uma sequência completa.
 *
 * Referência: Fredkin, E. "Trie Memory" (1960);
 *             Sedgewick, R. & Wayne, K. "Algorithms", Cap. 5.2 — Tries.
 */
internal class TrieNode<Key>(public var key: Key?, public var parent: TrieNode<Key>?) {
    public val children: HashMap<Key, TrieNode<Key>> = HashMap()
    public var isTerminating: Boolean = false
}
