package br.uem.din.datastructures.skiplist

import kotlin.random.Random

/**
 * Skip List — estrutura de dados probabilística para busca, inserção e remoção eficientes.
 *
 * Uma Skip List é uma lista ligada ordenada com múltiplos níveis de "atalhos" (forward pointers)
 * que permitem pular blocos de elementos durante a busca, similar ao conceito de busca binária
 * em listas ligadas. Os níveis são determinados aleatoriamente com probabilidade [p].
 *
 * Esta implementação possui comportamento de conjunto (Set): valores duplicados não são inseridos.
 *
 * Complexidades (esperadas, com alta probabilidade):
 * - [insert]: O(log n)
 * - [contains]: O(log n)
 * - Espaço: O(n) esperado
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 * @param maxLevel o nível máximo permitido (padrão: 16, suporta até ~2^16 elementos eficientemente).
 * @param p a probabilidade de promoção de um nó para o próximo nível (padrão: 0.5).
 *
 * Referência: Pugh, W. "Skip Lists: A Probabilistic Alternative to Balanced Trees" (1990);
 *             Sedgewick, R. & Wayne, K. "Algorithms", Cap. 3.5.
 */
class SkipList<T : Comparable<T>>(private val maxLevel: Int = 16, private val p: Double = 0.5) {

    /**
     * Nó interno da Skip List, contendo o valor e um array de ponteiros forward
     * para os diferentes níveis.
     *
     * @property value o valor armazenado, ou `null` para o nó sentinela (head).
     * @property level o nível máximo deste nó.
     * @property forward array de referências aos próximos nós em cada nível.
     */
    private class Node<T>(val value: T?, val level: Int) {
        val forward: Array<Node<T>?> = arrayOfNulls(level + 1)
    }

    private val head = Node<T>(null, maxLevel)
    private var level = 0
    /** Número de elementos armazenados na Skip List. */
    var size = 0
        private set

    /**
     * Insere um valor na Skip List, mantendo a ordenação.
     *
     * Se o valor já existir, a inserção é ignorada (comportamento de Set).
     * O nível do novo nó é determinado aleatoriamente pela função [randomLevel].
     *
     * Complexidade esperada: O(log n).
     *
     * @param value o valor a ser inserido.
     */
    fun insert(value: T) {
        val update = arrayOfNulls<Node<T>>(maxLevel + 1)
        var current = head

        for (i in level downTo 0) {
            while (current.forward[i] != null && current.forward[i]!!.value!! < value) {
                current = current.forward[i]!!
            }
            update[i] = current
        }
        
        // Move to next node at level 0
        val next = current.forward[0]

        // If value already exists, we don't insert (Set behavior) or we could duplicate.
        // Assuming Set behavior for simplicity.
        if (next != null && next.value == value) {
            return
        }

        val rLevel = randomLevel()
        if (rLevel > level) {
            for (i in level + 1..rLevel) {
                update[i] = head
            }
            level = rLevel
        }

        val newNode = Node(value, rLevel)
        for (i in 0..rLevel) {
            newNode.forward[i] = update[i]!!.forward[i]
            update[i]!!.forward[i] = newNode
        }
        size++
    }

    /**
     * Verifica se a Skip List contém o valor especificado.
     *
     * Percorre os níveis de cima para baixo, utilizando os atalhos para pular
     * blocos de elementos menores.
     *
     * Complexidade esperada: O(log n).
     *
     * @param value o valor a ser procurado.
     * @return `true` se o valor existir na Skip List, `false` caso contrário.
     */
    fun contains(value: T): Boolean {
        var current = head
        for (i in level downTo 0) {
            while (current.forward[i] != null && current.forward[i]!!.value!! < value) {
                current = current.forward[i]!!
            }
        }
        current = current.forward[0] ?: return false
        return current.value == value
    }

    /**
     * Gera um nível aleatório para um novo nó com base na probabilidade [p].
     *
     * O nível é incrementado enquanto um número aleatório for menor que [p],
     * limitado por [maxLevel].
     *
     * @return o nível gerado aleatoriamente.
     */
    private fun randomLevel(): Int {
        var lvl = 0
        while (Random.nextDouble() < p && lvl < maxLevel) {
            lvl++
        }
        return lvl
    }
}
