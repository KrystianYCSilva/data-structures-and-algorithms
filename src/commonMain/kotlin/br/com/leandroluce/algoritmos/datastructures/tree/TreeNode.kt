package br.com.leandroluce.algoritmos.datastructures.tree

class TreeNode<T>(val value: T) {
    private val children: MutableList<TreeNode<T>> = mutableListOf()

    fun add(child: TreeNode<T>) = children.add(child)

    fun forEachDepthFirst(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        children.forEach {
            it.forEachDepthFirst(visit)
        }
    }

    fun forEachLevelOrder(visit: (TreeNode<T>) -> Unit) {
        visit(this)
        val queue = mutableListOf<TreeNode<T>>()
        children.forEach { queue.add(it) }

        var head = queue.firstOrNull()
        while (head != null) {
            visit(head)
            queue.removeAt(0)
            head.children.forEach { queue.add(it) }
            head = queue.firstOrNull()
        }
    }

    fun search(value: T): TreeNode<T>? {
        var result: TreeNode<T>? = null
        forEachLevelOrder {
            if (it.value == value) {
                result = it
            }
        }
        return result
    }
}
