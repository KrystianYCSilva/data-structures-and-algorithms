package br.uem.din.datastructures.tree

/**
 * Cor de um nó na árvore rubro-negra.
 */
internal enum class Color { RED, BLACK }

/**
 * Implementação manual de Árvore Rubro-Negra (Red-Black Tree) para plataformas JS e Native.
 *
 * Esta classe é usada como delegada pelas declarações `actual` de JS e Native,
 * enquanto a plataforma JVM delega para `java.util.TreeSet`.
 *
 * Mantém as cinco propriedades invariantes da árvore rubro-negra através de
 * rotações e recoloração de nós, garantindo altura máxima de 2·log₂(n+1).
 *
 * | Operação | Complexidade |
 * |----------|--------------|
 * | insert   | O(log n)     |
 * | remove   | O(log n)     |
 * | contains | O(log n)     |
 * | inOrder  | O(n)         |
 * | size     | O(1)         |
 * | isEmpty  | O(1)         |
 *
 * @param T o tipo dos elementos, deve implementar [Comparable].
 *
 * Referência: Cormen, T. H. et al. "Introduction to Algorithms", Cap. 13 — Red-Black Trees.
 *
 * @see RedBlackTree
 */
internal class RedBlackTreeImpl<T : Comparable<T>> {

    private class Node<T>(
        var value: T,
        var color: Color = Color.RED,
        var left: Node<T>? = null,
        var right: Node<T>? = null,
        var parent: Node<T>? = null
    )

    private var root: Node<T>? = null
    private var count = 0

    /**
     * Insere um valor na árvore rubro-negra.
     *
     * Realiza inserção BST padrão seguida de correção das propriedades
     * rubro-negras via recoloração e rotações (procedimento RB-INSERT do CLRS).
     *
     * Complexidade: O(log n) no pior caso.
     *
     * @param value o valor a ser inserido.
     * @return `true` se o valor foi inserido, `false` se já existia na árvore.
     */
    public fun insert(value: T): Boolean {
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

    /**
     * Remove um valor da árvore rubro-negra.
     *
     * Realiza remoção BST padrão seguida de correção das propriedades
     * rubro-negras via recoloração e rotações (procedimento RB-DELETE do CLRS).
     *
     * Complexidade: O(log n) no pior caso.
     *
     * @param value o valor a ser removido.
     * @return `true` se o valor foi removido, `false` se não existia na árvore.
     */
    public fun remove(value: T): Boolean {
        val z = findNode(value) ?: return false
        var y = z
        var yOriginalColor = y.color
        val x: Node<T>?
        val xParent: Node<T>?

        if (z.left == null) {
            x = z.right
            xParent = z.parent
            transplant(z, z.right)
        } else if (z.right == null) {
            x = z.left
            xParent = z.parent
            transplant(z, z.left)
        } else {
            y = minimum(z.right!!)
            yOriginalColor = y.color
            x = y.right
            xParent = if (y.parent == z) y else y.parent
            if (y.parent == z) {
                x?.parent = y
            } else {
                transplant(y, y.right)
                y.right = z.right
                y.right?.parent = y
            }
            transplant(z, y)
            y.left = z.left
            y.left?.parent = y
            y.color = z.color
        }

        count--
        if (yOriginalColor == Color.BLACK) {
            fixDelete(x, xParent)
        }
        return true
    }

    /**
     * Verifica se a árvore contém o valor especificado.
     *
     * Realiza busca binária padrão percorrendo a árvore da raiz até as folhas.
     *
     * Complexidade: O(log n) no pior caso.
     *
     * @param value o valor a ser procurado.
     * @return `true` se o valor existir na árvore, `false` caso contrário.
     */
    public fun contains(value: T): Boolean {
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

    /**
     * Retorna os elementos da árvore em ordem crescente (travessia in-order).
     *
     * Complexidade: O(n), onde n é o número de elementos.
     *
     * @return lista ordenada dos elementos.
     */
    public fun inOrder(): List<T> {
        val result = mutableListOf<T>()
        inOrderTraversal(root, result)
        return result
    }

    /**
     * Retorna o número de elementos armazenados na árvore.
     *
     * Complexidade: O(1).
     *
     * @return quantidade de elementos na árvore.
     */
    public fun size(): Int = count

    /**
     * Verifica se a árvore está vazia.
     *
     * Complexidade: O(1).
     *
     * @return `true` se a árvore não contiver elementos, `false` caso contrário.
     */
    public fun isEmpty(): Boolean = count == 0

    private fun findNode(value: T): Node<T>? {
        var node = root
        while (node != null) {
            if (value == node.value) return node
            node = if (value < node.value) node.left else node.right
        }
        return null
    }

    private fun minimum(node: Node<T>): Node<T> {
        var current = node
        while (current.left != null) {
            current = current.left!!
        }
        return current
    }

    private fun transplant(u: Node<T>, v: Node<T>?) {
        if (u.parent == null) {
            root = v
        } else if (u == u.parent!!.left) {
            u.parent!!.left = v
        } else {
            u.parent!!.right = v
        }
        v?.parent = u.parent
    }

    private fun fixDelete(node: Node<T>?, parent: Node<T>?) {
        var x = node
        var xParent = parent
        while (x != root && (x == null || x.color == Color.BLACK)) {
            val p = xParent ?: break
            if (x == p.left) {
                var w = p.right
                if (w?.color == Color.RED) {
                    w.color = Color.BLACK
                    p.color = Color.RED
                    rotateLeft(p)
                    w = p.right
                }
                if ((w?.left == null || w.left!!.color == Color.BLACK) &&
                    (w?.right == null || w.right!!.color == Color.BLACK)
                ) {
                    w?.color = Color.RED
                    x = p
                    xParent = x.parent
                } else {
                    if (w?.right == null || w.right!!.color == Color.BLACK) {
                        w?.left?.color = Color.BLACK
                        w?.color = Color.RED
                        w?.let { rotateRight(it) }
                        w = p.right
                    }
                    w?.color = p.color
                    p.color = Color.BLACK
                    w?.right?.color = Color.BLACK
                    rotateLeft(p)
                    x = root
                }
            } else {
                var w = p.left
                if (w?.color == Color.RED) {
                    w.color = Color.BLACK
                    p.color = Color.RED
                    rotateRight(p)
                    w = p.left
                }
                if ((w?.right == null || w.right!!.color == Color.BLACK) &&
                    (w?.left == null || w.left!!.color == Color.BLACK)
                ) {
                    w?.color = Color.RED
                    x = p
                    xParent = x.parent
                } else {
                    if (w?.left == null || w.left!!.color == Color.BLACK) {
                        w?.right?.color = Color.BLACK
                        w?.color = Color.RED
                        w?.let { rotateLeft(it) }
                        w = p.left
                    }
                    w?.color = p.color
                    p.color = Color.BLACK
                    w?.left?.color = Color.BLACK
                    rotateRight(p)
                    x = root
                }
            }
        }
        x?.color = Color.BLACK
    }

    private fun inOrderTraversal(node: Node<T>?, result: MutableList<T>) {
        if (node == null) return
        inOrderTraversal(node.left, result)
        result.add(node.value)
        inOrderTraversal(node.right, result)
    }
}
