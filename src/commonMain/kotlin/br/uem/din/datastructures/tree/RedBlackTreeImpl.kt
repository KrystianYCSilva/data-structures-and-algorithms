package br.uem.din.datastructures.tree

/**
 * Cor de um nó na árvore rubro-negra.
 */
internal enum class Color { RED, BLACK }

/**
 * Implementação manual de Red-Black Tree para plataformas JS e Native.
 *
 * Esta classe é usada como `actual` para JS e Native, enquanto JVM delega para `java.util.TreeSet`.
 */
class RedBlackTreeImpl<T : Comparable<T>> {

    private class Node<T>(
        var value: T,
        var color: Color = Color.RED,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
        var parent: Node<T>? = null
    )

    private var root: Node<T>? = null
    private var count = 0

    fun insert(value: T): Boolean {
        var node = root
        var parent: Node<T>? = null
        while (node != null) {
            parent = node
            if (value < node.value) {
                node = node.left
            } else if (value > node.value) {
                node = node.right
            } else {
                return false
            }
        }

        val newNode = Node(value, parent = parent)
        if (parent == null) {
            root = newNode
        } else if (value < parent.value) {
            parent.left = newNode
        } else {
            parent.right = newNode
        }

        count++
        fixInsert(newNode)
        return true
    }

    private fun fixInsert(node: Node<T>) {
        var currentNode = node
        while (currentNode.parent?.color == Color.RED) {
            val parent = currentNode.parent!!
            val grandParent = parent.parent!!

            if (parent == grandParent.left) {
                val uncle = grandParent.right
                if (uncle?.color == Color.RED) {
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandParent.color = Color.RED
                    currentNode = grandParent
                } else {
                    if (currentNode == parent.right) {
                        currentNode = parent
                        rotateLeft(currentNode)
                    }
                    currentNode.parent!!.color = Color.BLACK
                    grandParent.color = Color.RED
                    rotateRight(grandParent)
                }
            } else {
                val uncle = grandParent.left
                if (uncle?.color == Color.RED) {
                    parent.color = Color.BLACK
                    uncle.color = Color.BLACK
                    grandParent.color = Color.RED
                    currentNode = grandParent
                } else {
                    if (currentNode == parent.left) {
                        currentNode = parent
                        rotateRight(currentNode)
                    }
                    currentNode.parent!!.color = Color.BLACK
                    grandParent.color = Color.RED
                    rotateLeft(grandParent)
                }
            }
            if (currentNode == root) {
                break
            }
        }
        root?.color = Color.BLACK
    }

    private fun rotateLeft(node: Node<T>) {
        val rightChild = node.right ?: return
        node.right = rightChild.left
        if (rightChild.left != null) {
            rightChild.left!!.parent = node
        }
        rightChild.parent = node.parent
        if (node.parent == null) {
            root = rightChild
        } else if (node == node.parent!!.left) {
            node.parent!!.left = rightChild
        } else {
            node.parent!!.right = rightChild
        }
        rightChild.left = node
        node.parent = rightChild
    }

    private fun rotateRight(node: Node<T>) {
        val leftChild = node.left ?: return
        node.left = leftChild.right
        if (leftChild.right != null) {
            leftChild.right!!.parent = node
        }
        leftChild.parent = node.parent
        if (node.parent == null) {
            root = leftChild
        } else if (node == node.parent!!.right) {
            node.parent!!.right = leftChild
        } else {
            node.parent!!.left = leftChild
        }
        leftChild.right = node
        node.parent = leftChild
    }

    fun contains(value: T): Boolean {
        var node = root
        while (node != null) {
            if (value == node.value) {
                return true
            }
            node = if (value < node.value) {
                node.left
            } else {
                node.right
            }
        }
        return false
    }

    fun size(): Int = count
    fun isEmpty(): Boolean = count == 0
}
