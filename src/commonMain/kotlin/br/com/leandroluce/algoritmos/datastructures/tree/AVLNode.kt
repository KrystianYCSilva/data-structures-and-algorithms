package br.com.leandroluce.algoritmos.datastructures.tree

class AVLNode<T>(var value: T) {
    var leftChild: AVLNode<T>? = null
    var rightChild: AVLNode<T>? = null

    var height = 0

    val leftHeight: Int
        get() = leftChild?.height ?: -1

    val rightHeight: Int
        get() = rightChild?.height ?: -1

    val balanceFactor: Int
        get() = leftHeight - rightHeight

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
