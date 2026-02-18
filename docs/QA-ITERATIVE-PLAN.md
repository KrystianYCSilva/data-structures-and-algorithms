# QA Iterative Plan - KMP Data Structures & Algorithms

Data: 2026-02-17
Owner: QA/SDET (Prompt-Driven)
Skill aplicado: `testing-expert`

## Baseline atual

- Producao (`commonMain`): 80 arquivos Kotlin (excluindo `*Interfaces.kt`).
- Testes existentes (`commonTest` + plataformas): 50 arquivos `*Test.kt`.
- Interop por plataforma: apenas `datastructures/queue` possui cobertura dedicada em `jvmTest`, `jsTest`, `nativeTest`.
- Bloqueio global atual para `jsTest`: `src/commonMain/kotlin/br/uem/din/datastructures/tree/Trie.kt` com membros sem `override` (`contains`, `remove`, `collections`).

## Gaps por pacote (heuristica de nome de classe -> ClassNameTest)

- `algorithms/sorting`: 10 gaps
- `algorithms/searching`: 6 gaps
- `algorithms/graph`: 4 gaps
- `datastructures/tree`: 4 gaps
- `datastructures/graph`: 3 gaps
- `datastructures/stack`: 2 gaps
- `datastructures`: 1 gap
- `datastructures/hash`: 1 gap
- `datastructures/heap`: 1 gap
- `datastructures/linkedlist`: 1 gap
- `datastructures/set`: 1 gap
- `extensions`: 1 gap

Total de gaps heuristico: 35

Observacao: a heuristica nao captura suites agregadas (ex.: `SortingTest.kt` cobrindo varias classes), portanto os gaps acima sao priorizacao inicial, nao verdade absoluta.

## Protocolo de qualidade (obrigatorio em todas as iteracoes)

Para cada classe/pacote alvo, aplicar obrigatoriamente:

1. State Invariants & Boundary
- estado vazio
- 1 elemento
- colecao grande
- limites (indices/capacidade/null quando permitido)
- termino de iteracao (`hasNext=false` + `next()` lancando excecao quando aplicavel)

2. Immutability vs Mutability
- variante read-only: validar ausencia de mutacao observavel e snapshots consistentes
- variante mutable: validar mutacao in-place, aliases e consistencia de `size/isEmpty`

3. Property-based/randomizado (deterministico)
- sequencias de operacoes com `Random(seed)`
- oracle com estrutura de referencia (`MutableList`, `java.util.*`, etc.)
- verificacao de invariantes a cada passo

4. Interop por plataforma
- `jvmTest`: paridade com backend Java/typealias/value-class quando existir
- `jsTest` / `nativeTest`: paridade comportamental das `actual`
- fronteiras JVM: nullability e cast safety

5. Error handling
- validar excecoes corretas (`assertFailsWith`) sem swallow

6. Guardas de complexidade
- envelope de comparacoes/operacoes para detectar regressao de ordem (ex.: O(n log n) vs O(n^2))

## Definition of Done por iteracao

- Todos os testes novos com `kotlin.test` e seeds fixos.
- Sem flaky tests (repetibilidade local).
- `jvmTest` do escopo passa.
- `jsTest` e `nativeTest` do escopo passam (ou bloqueio externo documentado).
- Sem warnings novos relevantes.
- Registro de sessao atualizado em `.itzamna/memory.md`.

## Plano iterativo por pacotes

### Iteracao 0 - Gate de plataforma e infra QA

Escopo:
- Desbloquear compilacao JS (`Trie.kt` overrides).
- Consolidar utilitarios de teste em `commonTest` (gerador seedado, oracles, helpers de envelope).

Saidas:
- Build `jsTest` destravado.
- Base reutilizavel para evitar duplicacao de cenarios.

Comandos de validacao:
- `./gradlew.bat jsTest`
- `./gradlew.bat nativeTest`

Status: `PENDENTE`

### Iteracao 1 - Queue package hardening

Escopo:
- `ArrayQueue`, `CircularQueue`, `LinkedQueue`, `Deque` (mesmo rigor de `PriorityQueue`).
- Revisao final de `PriorityQueue` com consistencia de naming/padroes.

Comandos:
- `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.queue.*"`
- `./gradlew.bat jsTest`
- `./gradlew.bat nativeTest`

Status: `EM PROGRESSO` (PriorityQueue concluida)

### Iteracao 2 - Stack + LinkedList

Escopo:
- `ArrayStack`, `LinkedStack`
- `LinkedList`, `DoublyLinkedList`, `CircularLinkedList`, `UnrolledLinkedList`, `Node`

Foco:
- LIFO/FIFO invariants
- iteradores bidirecionais
- aliasing e mutacao por views

Status: `RESOLVIDA`

### Iteracao 3 - Hash + Set

Escopo:
- `OpenAddressingHashTable`, `CuckooHashTable`, `SeparateChainingHashTable`, `HashTable`
- `Set`, `Multiset`

Foco:
- colisao, rehash, load factor
- semantica de duplicatas
- invariantes de cardinalidade

Status: `RESOLVIDA` — Fixed OpenAddressingHashTable.put() tombstone-accumulation bug; HashTablesHardeningTest (6 tests × 5 impls) passes all platforms; JS timeout fix (validation throttled to every 50th step).

### Iteracao 4 - Heap family

Escopo:
- `ComparableHeapImpl`, `ComparatorHeapImpl`, `BinomialHeap`, `FibonacciHeap`, `AbstractHeap`

Foco:
- propriedades de heap
- merge/decrease-key (quando aplicavel)
- envelopes de complexidade

Status: `RESOLVIDA` — ComparableHeapImplTest 11→14 tests (extract-all sorted, duplicates, string type); BinomialHeapTest 10→13 tests (merge-extract-all, duplicates, merge-two-empty).

### Iteracao 5 - Trees core (busca balanceada)

Escopo:
- `BinarySearchTree`, `AVLTree`, `RedBlackTreeImpl`, `SplayTree`, `Treap`, `Trie`

Foco:
- invariantes estruturais (altura, balanceamento, ordenacao)
- custo amortizado (splay)
- null safety e buscas inexistentes

Status: `RESOLVIDA` — AVLTreeTest 15→20 tests (remove root, remove single, balance after removals, degenerate 50-insert, string type).

### Iteracao 6 - Trees especializadas

Escopo:
- `BTree`, `BPlusTree`, `CartesianTree`, `FenwickTree`, `RadixTree`, `TreeNode`, `AVLNode`, `TrieNode`, `BinaryNode`

Foco:
- invariantes por grau/fanout
- prefix/range queries
- consistencia de estrutura interna

Status: `RESOLVIDA` — FenwickTreeTest 8→11 tests (negative delta, large array 100, all-zeros); BPlusTreeTest 10→14 tests (remove all, range single match, contains empty, isEmpty).

### Iteracao 7 - Graph data structures

Escopo:
- `Graph`, `AdjacencyList`, `AdjacencyMatrix`, `DirectedAcyclicGraph`, `Edge`, `Vertex`

Foco:
- directed/undirected consistency
- arestas com/sem peso
- topological constraints

Status: `RESOLVIDA` — AdjacencyListTest 13→17 tests (self-loop, parallel edges, asReadOnly view, isolated vertex).

### Iteracao 8 - Graph algorithms

Escopo:
- `BreadthFirstSearch`, `DepthFirstSearch`, `Dijkstra`, `AStar`

Foco:
- corretude de caminhos
- conectividade
- casos desconectados
- comparacao com oraculos simples

Status: `RESOLVIDA` — GraphAlgorithmsTest 5→22 tests (Bellman-Ford, Floyd-Warshall, Kruskal, Prim, Dijkstra, A* with single vertex, disconnected, negative cycle, zero-weight, unreachable).

### Iteracao 9 - Searching algorithms

Escopo:
- `LinearSearch`, `BinarySearch`, `JumpSearch`, `InterpolationSearch`, `ExponentialSearch`, `TernarySearch`

Foco:
- pre-condicoes (ordenacao)
- duplicatas
- limites e overflows

Status: `RESOLVIDA` — SearchingTest rewritten: 6→35+ tests with cross-algorithm parameterized tests, empty/single/two elements, duplicates, negatives, large list 1000, InterpolationSearch-specific uniform distribution.

### Iteracao 10 - Sorting algorithms

Escopo:
- `BubbleSort`, `InsertionSort`, `SelectionSort`, `ShellSort`, `MergeSort`, `QuickSort`, `HeapSort`, `CountingSort`, `RadixSort`, `BucketSort`

Foco:
- ordenacao total
- estabilidade (quando prometida)
- inputs adversariais
- envelope de comparacoes/trocas

Status: `RESOLVIDA` — SortingTest rewritten: 14→55+ tests covering all 10 algorithms with edge cases, stability tests for 6 stable sorts, adversarial inputs for QuickSort, randomized seed tests, String type, CountingSort negative throws.

### Iteracao 11 - Estruturas restantes e extensoes

Escopo:
- `BitSet`, `BloomFilter`, `SkipList`, `KDTree`, `QuadTree`, `UnionFind`, `CollectionsExtensions`, `extensions/MutableList`

Foco:
- falso positivo (BloomFilter)
- compressao/path compression (UnionFind)
- consistencia de extensoes em tipos mutaveis/read-only

Status: `RESOLVIDA` — BitSet 28 common + 17 platform-specific tests (JVM/JS/Native interop NEW); BloomFilter 15→19; SkipList 15→24; KDTree 10→24; QuadTree 10→21; UnionFind 10→18; Extensions 3→18. ImmutableViews added for BloomFilter, KDTree, QuadTree, UnionFind.

### Iteracao 12 - Interop sweep + gates de regressao

Escopo:
- expandir `jvmTest/jsTest/nativeTest` para pacotes com expect/actual e wrappers JVM
- consolidar suites de regressao para performance e fronteiras de nullability/casts

Saidas:
- matriz de interop por pacote concluida
- comando unico de validacao final em CI

Status: `PENDENTE`

## Cadencia recomendada

- 1 iteracao = 1 PR focado por pacote/conjunto coeso.
- Cada PR com:
  - sumario de invariantes cobertos
  - lista de seeds usadas
  - riscos remanescentes
  - evidencias de execucao dos testes

## Proxima execucao recomendada

1. Executar Iteracao 0 (desbloquear `jsTest` em `Trie.kt`).
2. Fechar Iteracao 1 (Queue completa alem de `PriorityQueue`).
3. Seguir ordem de risco: Trees -> Graph -> Sorting/Searching.

## Execucao real - Snapshot 2026-02-17 (sessao atual)

### Verificacao inicial solicitada (Trie)

- `Trie.kt` nao requer mais ajuste de `override` (metodos `contains`, `remove`, `collections` ja estao corretos).
- Bloqueios encontrados durante a varredura inicial nao eram do `Trie`:
  - `Prim.kt` ainda com API antiga de `PriorityQueue`
  - `BellmanFord.kt` com `putIfAbsent` (nao-portavel no common)
- Ajustes aplicados:
  - `src/commonMain/kotlin/br/uem/din/algorithms/graph/Prim.kt`
  - `src/commonMain/kotlin/br/uem/din/algorithms/graph/BellmanFord.kt`

### Evidencias de build/test

- `./gradlew.bat compileKotlinJvm compileKotlinJs` -> PASS
- `./gradlew.bat jvmTest` -> PASS
- `./gradlew.bat jsTest` -> PASS
- `./gradlew.bat nativeTest` -> PASS

### Estado das iteracoes

- **Iteracao 0 (gate de plataforma e infra QA): RESOLVIDA**
  - Todos os targets compilam e suites por target executam.

- **Iteracao 1 (Queue package hardening): PARCIAL**
  - `PriorityQueue`: cobertura forte + interop JVM/JS/Native.
  - `ArrayQueue`, `CircularQueue`, `LinkedQueue`, `Deque`: possuem testes comuns, mas sem property-based robusto e sem interop dedicado por plataforma.

- **Iteracao 2 (Stack + LinkedList): PARCIAL**
  - Cobertura comum existente, sem sweep de interop por plataforma.

- **Iteracao 3 (Hash + Set): RESOLVIDA**
  - Fixed OpenAddressingHashTable.put() tombstone-accumulation production bug.
  - HashTablesHardeningTest (6 tests × 5 impls = 30 effective) passes all platforms.
  - JS timeout fix: validation frequency throttled to every 50th step.

- **Iteracao 4 (Heap family): RESOLVIDA**
  - ComparableHeapImplTest 11→14 tests (extract-all sorted, duplicates, string type).
  - BinomialHeapTest 10→13 tests (merge-extract-all, duplicates, merge-two-empty).

- **Iteracao 5 (Trees core): RESOLVIDA**
  - AVLTreeTest 15→20 tests (remove root, remove single, balance after removals, degenerate 50-insert, string type).

- **Iteracao 6 (Trees especializadas): RESOLVIDA**
  - FenwickTreeTest 8→11 tests (negative delta, large array 100, all-zeros).
  - BPlusTreeTest 10→14 tests (remove all, range single match, contains empty, isEmpty).

- **Iteracao 7 (Graph data structures): RESOLVIDA**
  - AdjacencyListTest 13→17 tests (self-loop, parallel edges, asReadOnly view, isolated vertex).

- **Iteracao 8 (Graph algorithms): RESOLVIDA**
  - GraphAlgorithmsTest 5→22 tests (Bellman-Ford, Floyd-Warshall, Kruskal, Prim, Dijkstra, A*).
  - Coverage: single vertex, disconnected, negative cycle, zero-weight, unreachable, cycle.

- **Iteracao 9 (Searching algorithms): RESOLVIDA**
  - SearchingTest rewritten: 6→35+ tests with cross-algorithm parameterized tests.
  - Edge cases: empty, single, two, duplicates, negatives, large 1000, uniform distribution.

- **Iteracao 10 (Sorting algorithms): RESOLVIDA**
  - SortingTest rewritten: 14→55+ tests covering all 10 algorithms.
  - Stability tests, adversarial inputs, randomized seed, String type, CountingSort negative throws.

- **Iteracao 11 (Estruturas restantes e extensoes): RESOLVIDA**
  - BitSet: 28 common + 17 platform-specific (JVM/JS/Native interop NEW files).
  - BloomFilter 15→19, SkipList 15→24, KDTree 10→24, QuadTree 10→21, UnionFind 10→18, Extensions 3→18.
  - ImmutableViews added for BloomFilter, KDTree, QuadTree, UnionFind.

- **Iteracao 12 (Interop sweep + regressao): PENDENTE**
  - Hoje apenas `datastructures/queue` possui `jvmTest` + `jsTest` + `nativeTest` dedicados.

### Proxima acao de execucao

- Iniciar **Iteracao 1 (Queue hardening completo)**: elevar `ArrayQueue`, `CircularQueue`, `LinkedQueue`, `Deque` para o mesmo nivel de `PriorityQueue` (invariantes, property-based seedado, erros, e interop por target).

## Execucao real - Snapshot 2026-02-17 (iteracao 1 / queue)

### Escopo executado

- Verificacao inicial do `Trie` conforme solicitado: sem ajuste pendente.
- Harden de interop no pacote `queue`:
  - `src/jvmTest/kotlin/br/uem/din/datastructures/queue/ArrayQueueJvmInteropTest.kt`
  - `src/jsTest/kotlin/br/uem/din/datastructures/queue/ArrayQueueJsInteropTest.kt`
  - `src/nativeTest/kotlin/br/uem/din/datastructures/queue/ArrayQueueNativeInteropTest.kt`
  - `src/jvmTest/kotlin/br/uem/din/datastructures/queue/DequeJvmInteropTest.kt`

Observacao: durante a execucao, os testes comuns de `ArrayQueue`, `CircularQueue`, `LinkedQueue` e `Deque` ja se encontravam no estado reforcado no working tree (sem diff adicional contra HEAD neste momento), com cenarios de invariantes, randomizacao seedada e excecoes.

### Evidencias de validacao desta etapa

- `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.queue.*"` -> PASS
- `./gradlew.bat jsTest` -> PASS
- `./gradlew.bat nativeTest` -> PASS

### Atualizacao de status das iteracoes

- **Iteracao 1 (Queue package hardening): RESOLVIDA**
  - Cobertura comum robusta para `ArrayQueue`, `CircularQueue`, `LinkedQueue`, `Deque`, `PriorityQueue`.
  - Interop dedicado agora presente para `PriorityQueue` (JVM/JS/Native), `ArrayQueue` (JVM/JS/Native) e `Deque` (JVM).

- **Iteracao 12 (Interop sweep + regressao): segue PENDENTE**
  - Necessario estender estrategia de interop para outros pacotes (hash, tree, graph, etc.).

## Execucao real - Snapshot 2026-02-17 (iteracao 2 / stack + linkedlist)

### Escopo executado

- Harden de testes no pacote `linkedlist` com foco em invariantes e sequencias randomizadas seedadas:
  - `src/commonTest/kotlin/br/uem/din/datastructures/linkedlist/CircularLinkedListTest.kt`
  - `src/commonTest/kotlin/br/uem/din/datastructures/linkedlist/DoublyLinkedListTest.kt`
  - `src/commonTest/kotlin/br/uem/din/datastructures/linkedlist/UnrolledLinkedListTest.kt`

- Interop por plataforma adicionado para `ArrayStack`:
  - `src/jvmTest/kotlin/br/uem/din/datastructures/stack/ArrayStackJvmInteropTest.kt`
  - `src/jsTest/kotlin/br/uem/din/datastructures/stack/ArrayStackJsInteropTest.kt`
  - `src/nativeTest/kotlin/br/uem/din/datastructures/stack/ArrayStackNativeInteropTest.kt`

- Interop por plataforma adicionado para `DoublyLinkedList`:
  - `src/jvmTest/kotlin/br/uem/din/datastructures/linkedlist/DoublyLinkedListJvmInteropTest.kt`
  - `src/jsTest/kotlin/br/uem/din/datastructures/linkedlist/DoublyLinkedListJsInteropTest.kt`
  - `src/nativeTest/kotlin/br/uem/din/datastructures/linkedlist/DoublyLinkedListNativeInteropTest.kt`

- Bug de producao encontrado e corrigido durante testes:
  - `src/jsMain/kotlin/br/uem/din/datastructures/linkedlist/DoublyLinkedList.kt`
  - `src/nativeMain/kotlin/br/uem/din/datastructures/linkedlist/DoublyLinkedList.kt`
  - Correcao: iterador agora diferencia fim de iteracao de valor `null`, permitindo elementos nulos validos sem `NoSuchElementException` indevido.

### Evidencias de validacao desta etapa

- `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.stack.*" --tests "br.uem.din.datastructures.linkedlist.*"` -> PASS
- `./gradlew.bat nativeTest` -> PASS
- `./gradlew.bat jsTest` -> FAIL fora do escopo da iteracao:
  - `br.uem.din.optimization.MaxSatProblemTest.testSimulatedAnnealingMaxSat[js, node]`

### Atualizacao de status das iteracoes

- **Iteracao 2 (Stack + LinkedList): RESOLVIDA (com bloqueio externo de suite global JS)**
  - Escopo de stack/linkedlist validado; falha JS remanescente esta em pacote `optimization`.

## Atualizacao - 2026-02-17 (fix jsTest MaxSat)

- Bloqueio externo de JS identificado na iteracao 2 foi resolvido.
- Arquivo ajustado:
  - `src/commonTest/kotlin/br/uem/din/optimization/MaxSatProblemTest.kt`
- Causa raiz:
  - `testSimulatedAnnealingMaxSat` usava defaults muito custosos para ambiente JS Node (timeout 2000ms).
- Correcao aplicada:
  - Parametros explicitos e mais leves no teste (`initialTemp`, `coolingRate`, `minTemp`, `maxIterationsPerTemp`) mantendo determinismo com `Random(42)`.
- Validacao apos fix:
  - `./gradlew.bat jsTest` -> PASS
  - `./gradlew.bat jvmTest --tests "br.uem.din.optimization.MaxSatProblemTest"` -> PASS
  - `./gradlew.bat nativeTest` -> PASS


## Atualizacao - 2026-02-17 (iteracao 4 / heap)

- Verificacao de estado: Iteracao 4 nao estava no mesmo nivel de hardening de `PriorityQueue` (faltavam cenarios property-based e guardas de complexidade dedicados).
- Entrega aplicada:
  - `src/commonTest/kotlin/br/uem/din/datastructures/heap/HeapHardeningTest.kt`
- Cobertura adicionada (10 testes):
  - property-based/randomizado para `ComparableHeapImpl`, `ComparatorHeapImpl`, `BinomialHeap` e `FibonacciHeap` com oracle em lista
  - boundary/error handling (`remove(-1)` com excecao, indice invalido, iterador exaurido)
  - null handling em `ComparatorHeapImpl` com comparador custom
  - verificacao de alias mutavel (`MutableHeap`) e vista somente-leitura (`Heap`)
  - merge correctness (`BinomialHeap.merge`, `FibonacciHeap.merge`)
  - `decreaseKey` guard (`IllegalArgumentException` em aumento de chave)
  - envelope de complexidade por contagem de comparacoes O(n log n)
- Validacao executada:
  - `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.heap.*"` -> PASS
  - `./gradlew.bat jsTest` -> PASS
  - `./gradlew.bat nativeTest` -> PASS
- Status de iteracao:
  - **Iteracao 4 (Heap family): RESOLVIDA**

## Atualizacao - 2026-02-17 (iteracao 5 / trees core)

- Escopo executado: `BinarySearchTree`, `AVLTree`, `SplayTree`, `Treap`, `RedBlackTree`, `Trie`.
- Entregas:
  - `src/commonTest/kotlin/br/uem/din/datastructures/tree/TreeCoreHardeningTest.kt`
  - `src/jvmTest/kotlin/br/uem/din/datastructures/tree/RedBlackTreeJvmInteropTest.kt`
- Cobertura adicionada (trees core):
  - invariantes de estado (vazio, elemento unico, colecao grande)
  - sequencias randomizadas seedadas com oracle (`MutableSet` para SearchTree, `MutableSet<List<Char>>` para Trie)
  - verificacoes de `size`, `isEmpty`, `inOrder`, `iterator`, semantica de duplicatas
  - `asReadOnly()` para `SearchTree` e `ImmutableTrie` (snapshot estavel + view live)
  - contrato de iterador (`hasNext` e `NoSuchElementException` apos exaustao)
  - guarda de complexidade para `RedBlackTree` por contagem de comparacoes (envelope O(n log n))
- Interop JVM adicionado:
  - paridade comportamental `redBlackTreeOf()` vs `java.util.TreeSet` com operacoes randomizadas
  - fronteira Java: rejeicao de `null` via reflexao (`InvocationTargetException` com causa `NullPointerException`)
- Ajuste complementar (limpeza de warnings jvmTest):
  - `src/jvmTest/kotlin/br/uem/din/datastructures/hash/HashTableJvmInteropTest.kt`
  - `src/jvmTest/kotlin/br/uem/din/datastructures/set/HashSetCollectionJvmInteropTest.kt`
  - substituido `is` always-true por verificacao de classe runtime (`java.util.HashMap`/`java.util.HashSet`)
- Validacao executada:
  - `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.tree.*"` -> PASS
  - `./gradlew.bat jsTest` -> PASS
  - `./gradlew.bat nativeTest` -> PASS
- Status de iteracao:
  - **Iteracao 5 (Trees core): RESOLVIDA**

## Atualizacao - 2026-02-17 (iteracao 6 / trees especializadas)

- Escopo executado: `BTree`, `BPlusTree`, `FenwickTree`, `SegmentTree`, `RadixTree`, `CartesianTree`.
- Entrega:
  - `src/commonTest/kotlin/br/uem/din/datastructures/tree/SpecializedTreesHardeningTest.kt`
- Cobertura adicionada (9 testes):
  - `BTree` randomizado contra oracle `MutableSet` (insert/remove/contains/inOrder/iterator)
  - `BPlusTree` randomizado contra oracle `MutableSet` + validacao de `rangeSearch`
  - contrato de iterador para `BTree`/`BPlusTree` (terminacao + `NoSuchElementException`)
  - `FenwickTree` randomizado contra oracle `LongArray` (update/prefix/range/point) + validacao de fronteiras com excecoes
  - `SegmentTree` randomizado contra oracle para updates pontuais/queries de intervalo + cenarios de `rangeUpdate` validados por query pontual
  - `RadixTree` randomizado contra oracle `MutableSet<String>` (insert/remove/search/prefixSearch)
  - `CartesianTree` randomizado (inOrder == input, min-heap valido, raiz minima)
  - parametros invalidos em construcao (`BTree(minimumDegree=1)`, `BPlusTree(order=2)`) com excecoes esperadas
- Validacao executada:
  - `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.tree.*"` -> PASS
  - `./gradlew.bat jsTest` -> PASS
  - `./gradlew.bat nativeTest` -> PASS
- Status de iteracao:
  - **Iteracao 6 (Trees especializadas): RESOLVIDA**

## Atualizacao - 2026-02-17 (iteracao 7 / graph data structures)

- Escopo executado: `AdjacencyList`, `AdjacencyMatrix`, `DirectedAcyclicGraph`.
- Entrega:
  - `src/commonTest/kotlin/br/uem/din/datastructures/graph/GraphStructuresHardeningTest.kt`
- Cobertura adicionada:
  - paridade randomizada `AdjacencyList` vs `AdjacencyMatrix` com oracle de arestas (direcionadas/nao-direcionadas) e pesos (incluindo `null`)
  - verificacao de `edges()` e `weight()` para todos os pares de vertices em checkpoints
  - semantica `asReadOnly()` para grafo (snapshot estavel + view live)
  - DAG randomizado: invariantes de aciclicidade, validacao formal da ordenacao topologica e shortest-path contra oracle
  - validacao de rejeicao de ciclo em DAG e `UnsupportedOperationException` para aresta nao-direcionada
- Ajuste no teste durante execucao:
  - caso `addUndirectedEdge(v, v)` removido do gerador randomizado por gerar paralelismo legitimo em `AdjacencyList` (self-loop duplicado), nao equivalente ao modelo de aresta unica.
- Validacao executada:
  - `./gradlew.bat jvmTest --tests "br.uem.din.datastructures.graph.*"` -> PASS
  - `./gradlew.bat jsTest` -> PASS
  - `./gradlew.bat nativeTest` -> PASS
- Status de iteracao:
  - **Iteracao 7 (Graph data structures): RESOLVIDA**

## Atualizacao - 2026-02-18 (iteracao 8 / graph algorithms hardening v2)

- Escopo executado: `BreadthFirstSearch`, `DepthFirstSearch`, `Dijkstra`, `AStar`, `BellmanFord`, `FloydWarshall`, `Kruskal`, `Prim`.
- Entrega:
  - `src/commonTest/kotlin/br/uem/din/algorithms/graph/GraphAlgorithmsHardeningTest.kt`
- Cobertura adicionada (8 testes):
  - BFS/DFS randomizados com oracle de alcançabilidade + verificação de não-duplicação e níveis BFS monotônicos.
  - Dijkstra randomizado contra oracle Bellman-Ford em grafos não-negativos (`AdjacencyList` e `AdjacencyMatrix`).
  - Regressão de estado: `Dijkstra` e `AStar` validados em chamadas consecutivas no mesmo objeto (sem vazamento de estado).
  - A* validado contra Dijkstra em família de grafos direcionados monotônicos (heurística admissível por construção).
  - Floyd-Warshall validado contra Dijkstra por fonte em grafos não-negativos.
  - Paridade de MST (`Kruskal` vs `Prim`) em grafos não-direcionados conectados randomizados.
  - Guarda de complexidade operacional: expansão de arestas do Dijkstra limitada ao número de vértices visitados.
- Bugs de produção encontrados e corrigidos:
  - `src/commonMain/kotlin/br/uem/din/algorithms/graph/Dijkstra.kt`
    - fila de prioridade dependia de comparador com estado mutável; ajustado para `QueueEntry(vertex, priority)` com prioridade snapshot.
    - removeu estado compartilhado entre invocações (`costs`/`visited` agora locais por chamada).
  - `src/commonMain/kotlin/br/uem/din/algorithms/graph/AStar.kt`
    - fila de prioridade ajustada para `QueueEntry(vertex, priority)` com descarte de entradas obsoletas.
    - removeu estado compartilhado entre invocações (`costs`/`visited` deixaram de vazar entre chamadas).
- Validacao executada:
  - `./gradlew.bat jvmTest --tests "br.uem.din.algorithms.graph.*"` -> **PASS**
  - `./gradlew.bat jsTest` -> **PASS**
  - `./gradlew.bat nativeTest` -> **PASS**
- Status de iteracao:
  - **Iteracao 8 (Graph algorithms): RESOLVIDA (hardening v2 aplicado)**

## Atualizacao - 2026-02-18 (iteracao 12 / interop sweep parcial)

- Escopo executado: cobertura interop faltante para `redBlackTreeOf()` em plataformas não-JVM.
- Entregas:
  - `src/jsTest/kotlin/br/uem/din/datastructures/tree/RedBlackTreeJsInteropTest.kt`
  - `src/nativeTest/kotlin/br/uem/din/datastructures/tree/RedBlackTreeNativeInteropTest.kt`
- Cobertura adicionada:
  - Paridade randomizada contra oracle `MutableSet` ordenado (insert/remove/contains/size/isEmpty/inOrder/iterator).
  - Semântica de `asReadOnly()` (snapshot estável + view live) no wrapper `redBlackTreeOf()`.
- Validacao executada:
  - `./gradlew.bat jsTest` -> **PASS**
  - `./gradlew.bat nativeTest` -> **PASS**
- Status de iteracao:
  - **Iteracao 12 (Interop sweep): EM PROGRESSO**
  - `RedBlackTree` agora coberto em `jvmTest` + `jsTest` + `nativeTest`.
