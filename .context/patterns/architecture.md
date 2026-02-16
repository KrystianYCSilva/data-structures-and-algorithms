---
description: Architecture patterns actually used in this project.
---

# Architecture Patterns

## Modular Library (Single Gradle Module)

Projeto organizado como um unico modulo Gradle com source sets KMP. Nao ha submodulos.

```
commonMain  ->  jvmMain / jsMain / nativeMain
commonTest  ->  jvmTest / jsTest
```

## expect/actual Pattern

Para tipos que precisam de implementacao platform-specific:
- `expect class ArrayStack<T>() : MutableStack<T>` em commonMain
- `actual class ArrayStack<T>` em jvmMain (delega a `java.util.ArrayDeque`), jsMain e nativeMain (usa `ArrayList`)

Tipos atuais com expect/actual: `ArrayStack`, `BitSet`.

## Read-Only / Mutable Interface Pair

Seguindo Kotlin stdlib (`List`/`MutableList`):
- `Stack<T> : Iterable<T>` (read-only: `peek`, `size`, `isEmpty`, `contains`)
- `MutableStack<T> : Stack<T>` (mutavel: `push`, `pop`, `clear`)

Aplicado a: Stack, Queue, e derivados.

## Abstract Base + Concrete Implementations

Para heaps e estruturas com logica compartilhada:
- `AbstractHeap<T> : MutableQueue<T>` (indices, siftUp/siftDown abstratos)
- `ComparableHeapImpl<T>` e `ComparatorHeapImpl<T>` como implementacoes concretas

## Extension Functions

Cross-cutting utilities em `extensions/` como funcoes de extensao:
- `MutableList<T>.swap(index1, index2)` em `extensions/MutableList.kt`
- `Stack<T>.toList()` em `StackInterfaces.kt`

## Top-Level Functions para Algoritmos

Algoritmos de sorting/searching como funcoes top-level genericas:
- `fun <T : Comparable<T>> bubbleSort(list: MutableList<T>)`
- Sem classes wrapper desnecessarias; funcoes puras quando possivel
