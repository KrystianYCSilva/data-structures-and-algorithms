package br.uem.din.datastructures.skiplist

import kotlin.random.Random

class SkipList<T : Comparable<T>>(private val maxLevel: Int = 16, private val p: Double = 0.5) {

    private class Node<T>(val value: T?, val level: Int) {
        val forward: Array<Node<T>?> = arrayOfNulls(level + 1)
    }

    private val head = Node<T>(null, maxLevel)
    private var level = 0
    var size = 0
        private set

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

    private fun randomLevel(): Int {
        var lvl = 0
        while (Random.nextDouble() < p && lvl < maxLevel) {
            lvl++
        }
        return lvl
    }
}
