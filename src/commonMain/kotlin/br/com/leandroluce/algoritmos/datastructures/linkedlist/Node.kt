package br.com.leandroluce.algoritmos.datastructures.linkedlist

data class Node<T>(val value: T, var next: Node<T>? = null) {
    override fun toString(): String {
        return if (next != null) {
            "$value -> ${next.toString()}"
        } else {
            "$value"
        }
    }
}
