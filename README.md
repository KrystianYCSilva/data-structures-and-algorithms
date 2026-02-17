# algoritmos_otimizacao

Biblioteca acadêmica em **Kotlin Multiplatform** (JVM / JS / Native) de estruturas de dados, algoritmos clássicos e heurísticas de otimização.

[![Kotlin](https://img.shields.io/badge/Kotlin-2.1.0-blue.svg)](https://kotlinlang.org)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](LICENSE)
[![Platforms](https://img.shields.io/badge/Platforms-JVM%20%7C%20JS%20%7C%20Native-green.svg)]()

## Conteúdo

| Categoria | Quantidade | Exemplos |
|-----------|-----------|----------|
| Estruturas de Dados | 36 | Stack, Queue, LinkedList, BST, AVL, RedBlackTree, BTree, Trie, SegmentTree, Graph, Heap, BloomFilter, SkipList, KDTree, QuadTree, UnionFind… |
| Algoritmos de Ordenação | 10 | BubbleSort, InsertionSort, SelectionSort, ShellSort, MergeSort, QuickSort, HeapSort, CountingSort, RadixSort, BucketSort |
| Algoritmos de Busca | 6 | Linear, Binary, Interpolation, Ternary, Jump, Exponential |
| Algoritmos de Grafos | 8 | BFS, DFS, Dijkstra, A*, Bellman-Ford, Floyd-Warshall, Kruskal, Prim |

> **Status**: v0.1.0-preview — 36 estruturas de dados e 24 algoritmos implementados e testados em 3 plataformas.

## Instalação

### Gradle (Kotlin DSL)

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    implementation("br.uem.din:algoritmos_otimizacao:0.1.0")
}
```

### Kotlin Multiplatform

```kotlin
kotlin {
    sourceSets {
        val commonMain by getting {
            dependencies {
                implementation("br.uem.din:algoritmos_otimizacao:0.1.0")
            }
        }
    }
}
```

## Uso Rápido

```kotlin
import br.uem.din.datastructures.stack.ArrayStack
import br.uem.din.datastructures.graph.AdjacencyList
import br.uem.din.algorithms.graph.Dijkstra
import br.uem.din.algorithms.sorting.bubbleSort

// Stack
val stack = ArrayStack<Int>()
stack.push(1)
stack.push(2)
println(stack.peek()) // 2

// Grafo + Dijkstra
val graph = AdjacencyList<String>()
val a = graph.createVertex("A")
val b = graph.createVertex("B")
graph.addDirectedEdge(a, b, 5.0)
val dijkstra = Dijkstra(graph)
val distances = dijkstra.shortestPath(a)

// Ordenação
val array = intArrayOf(5, 3, 8, 1, 2)
bubbleSort(array)
```

Para mais exemplos, veja [`docs/USAGE_EXAMPLES.md`](docs/USAGE_EXAMPLES.md).

## Arquitetura

```
src/
├── commonMain/kotlin/br/uem/din/
│   ├── algorithms/          # Sorting, Searching, Graph
│   ├── datastructures/      # 36 estruturas de dados
│   └── extensions/          # Funções de extensão
├── commonTest/              # Testes multiplataforma (kotlin.test)
├── jvmMain/                 # Implementações JVM-specific
├── jsMain/                  # Implementações JS-specific
└── nativeMain/              # Implementações Native-specific (mingwX64)
```

### Padrão de Interfaces

Todas as estruturas seguem o padrão Kotlin stdlib de separação leitura/escrita:

- `Immutable*` / `Mutable*` — onde há conflito com classe concreta (ex: `ImmutableLinkedList` / `MutableLinkedList`)
- Substantivo puro / `Mutable*` — onde não há conflito (ex: `Stack` / `MutableStack`, `Heap` / `MutableHeap`)

## Build

```bash
# Build completo (JVM + JS + Native)
./gradlew build

# Testes em todas as plataformas
./gradlew check

# Apenas JVM
./gradlew jvmTest
```

## Documentação

| Documento | Descrição |
|-----------|-----------|
| [`ALGORITHM_CATALOG.md`](docs/ALGORITHM_CATALOG.md) | Catálogo com complexidades |
| [`USAGE_EXAMPLES.md`](docs/USAGE_EXAMPLES.md) | Exemplos de uso da API |
| [`PROJECT_ROADMAP.md`](docs/PROJECT_ROADMAP.md) | Roadmap do projeto |

## Licença

[MIT](LICENSE) — Krystian Yago C. Silva
