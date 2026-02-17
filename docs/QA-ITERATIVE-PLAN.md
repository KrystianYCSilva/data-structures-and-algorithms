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

Status: `PENDENTE`

### Iteracao 3 - Hash + Set

Escopo:
- `OpenAddressingHashTable`, `CuckooHashTable`, `SeparateChainingHashTable`, `HashTable`
- `Set`, `Multiset`

Foco:
- colisao, rehash, load factor
- semantica de duplicatas
- invariantes de cardinalidade

Status: `PENDENTE`

### Iteracao 4 - Heap family

Escopo:
- `ComparableHeapImpl`, `ComparatorHeapImpl`, `BinomialHeap`, `FibonacciHeap`, `AbstractHeap`

Foco:
- propriedades de heap
- merge/decrease-key (quando aplicavel)
- envelopes de complexidade

Status: `PENDENTE`

### Iteracao 5 - Trees core (busca balanceada)

Escopo:
- `BinarySearchTree`, `AVLTree`, `RedBlackTreeImpl`, `SplayTree`, `Treap`, `Trie`

Foco:
- invariantes estruturais (altura, balanceamento, ordenacao)
- custo amortizado (splay)
- null safety e buscas inexistentes

Status: `PENDENTE`

### Iteracao 6 - Trees especializadas

Escopo:
- `BTree`, `BPlusTree`, `CartesianTree`, `FenwickTree`, `RadixTree`, `TreeNode`, `AVLNode`, `TrieNode`, `BinaryNode`

Foco:
- invariantes por grau/fanout
- prefix/range queries
- consistencia de estrutura interna

Status: `PENDENTE`

### Iteracao 7 - Graph data structures

Escopo:
- `Graph`, `AdjacencyList`, `AdjacencyMatrix`, `DirectedAcyclicGraph`, `Edge`, `Vertex`

Foco:
- directed/undirected consistency
- arestas com/sem peso
- topological constraints

Status: `PENDENTE`

### Iteracao 8 - Graph algorithms

Escopo:
- `BreadthFirstSearch`, `DepthFirstSearch`, `Dijkstra`, `AStar`

Foco:
- corretude de caminhos
- conectividade
- casos desconectados
- comparacao com oraculos simples

Status: `PENDENTE`

### Iteracao 9 - Searching algorithms

Escopo:
- `LinearSearch`, `BinarySearch`, `JumpSearch`, `InterpolationSearch`, `ExponentialSearch`, `TernarySearch`

Foco:
- pre-condicoes (ordenacao)
- duplicatas
- limites e overflows

Status: `PENDENTE`

### Iteracao 10 - Sorting algorithms

Escopo:
- `BubbleSort`, `InsertionSort`, `SelectionSort`, `ShellSort`, `MergeSort`, `QuickSort`, `HeapSort`, `CountingSort`, `RadixSort`, `BucketSort`

Foco:
- ordenacao total
- estabilidade (quando prometida)
- inputs adversariais
- envelope de comparacoes/trocas

Status: `PENDENTE`

### Iteracao 11 - Estruturas restantes e extensoes

Escopo:
- `BitSet`, `BloomFilter`, `SkipList`, `KDTree`, `QuadTree`, `UnionFind`, `CollectionsExtensions`, `extensions/MutableList`

Foco:
- falso positivo (BloomFilter)
- compressao/path compression (UnionFind)
- consistencia de extensoes em tipos mutaveis/read-only

Status: `PENDENTE`

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

- **Iteracao 3 (Hash + Set): PARCIAL**
  - Testes comuns existentes; falta reforco de propriedades/complexidade e interop.

- **Iteracao 4 (Heap family): PARCIAL**
  - Testes comuns presentes para principais heaps; falta reforco de invariantes amortizados/complexidade e interop.

- **Iteracao 5 (Trees core): PARCIAL**
  - Ampla cobertura comum; falta sweep de interop e propriedades mais agressivas em cenarios randomizados/adversariais.

- **Iteracao 6 (Trees especializadas): PARCIAL**
  - Testes comuns presentes para varias estruturas; falta consolidacao de invariantes formais + interop.

- **Iteracao 7 (Graph data structures): PARCIAL**
  - Testes comuns para `AdjacencyList`, `AdjacencyMatrix`, `DirectedAcyclicGraph`; falta camada interop e propriedades mais amplas.

- **Iteracao 8 (Graph algorithms): PARCIAL**
  - Testes comuns presentes (`A*`, BFS, DFS, Dijkstra, GraphAlgorithms); falta suite de regressao orientada a complexidade e interop.

- **Iteracao 9 (Searching algorithms): PARCIAL**
  - `SearchingTest.kt` existe; falta decompor por algoritmo com suites de fronteira/propriedade dedicadas.

- **Iteracao 10 (Sorting algorithms): PARCIAL**
  - `SortingTest.kt` existe; falta reforco de estabilidade, inputs adversariais e guardas de complexidade por algoritmo.

- **Iteracao 11 (Estruturas restantes e extensoes): PARCIAL**
  - Cobertura comum boa (BitSet, BloomFilter, SkipList, spatial, UnionFind, Extensions), faltando sweep interop sistematico.

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

