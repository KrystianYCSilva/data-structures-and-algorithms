package br.com.leandroluce.algoritmos.datastructures.tree

class BinaryNode<T>(var value: T) {
    var leftChild: BinaryNode<T>? = null
    var rightChild: BinaryNode<T>? = null

    fun traverseInOrder(visit: (T) -> Unit) {
        leftChild?.traverseInOrder(visit)
        visit(value)
        rightChild?.traverseInOrder(visit)
    }

    fun traversePreOrder(visit: (T) -> Unit) {
        visit(value)
        leftChild?.traversePreOrder(visit)
        rightChild?.traversePreOrder(visit)
    }

    fun traversePostOrder(visit: (T) -> Unit) {
        leftChild?.traversePostOrder(visit)
        rightChild?.traversePostOrder(visit)
        visit(value)
    }
}
