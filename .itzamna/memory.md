---
description: |
  Project episodic memory: current state, decisions, next steps, session history.
  Use when: starting any Deliberado+ task (MANDATORY read), ending significant sessions (MANDATORY update).
---

# Memory - Estado do Projeto

> Lido pelo agente no inicio de sessoes Deliberado+.
> Atualizado ao final de sessoes significativas.
> Append-only: nunca apague entradas.

---

## Projeto

- **Nome:** algoritmos_otimizacao
- **Stack:** Kotlin 2.1.0 Multiplatform (JVM, JS IR, mingwX64) + Gradle + kotlin.test
- **Repositorio:** local (IdeaProjects/Modelos/algoritmos_otimizacao)

---

## Estado atual

Biblioteca academica KMP de estruturas de dados, algoritmos e heuristicas de otimizacao.
Fase 1 (36 DS) e Fase 2 (~45 algoritmos) completas. Fase 3 (heuristicas): 3A e 3B completas (8 heuristicas, 132 testes).
Fase 3C (Differential Evolution, VNS, Memetic Algorithm, LNS) em planejamento.

**Auditoria DS:** F1-F5 completas. Biblioteca release-ready (explicitApi, jvmToolchain(8), KDoc completo).

**Reavaliacao F5 — Progresso:**
- SearchTree interface hierarchy: `SearchTree<T>` / `MutableSearchTree<T>` criados (TreeInterfaces.kt)
- 5 arvores (BST, AVL, SplayTree, Treap, RedBlackTree) agora implementam `MutableSearchTree<T>`
- BST/AVL agora rejeitam duplicatas; insert/remove retornam Boolean em todas as arvores
- RedBlackTree: `fun size()` migrado para `val size` (propriedade); expect+3 actuals atualizados
- SplayTree/Treap: `isEmpty()` adicionado
- BinomialHeap/FibonacciHeap: agora implementam `MutableQueue<T>`
- Graph interface split: `Graph<T>` (read-only) / `MutableGraph<T>` (mutable) — AdjacencyList, AdjacencyMatrix, DAG atualizados
- Hash table interfaces: `OpenHashTable<K,V>` / `MutableOpenHashTable<K,V>` criados (HashTableInterfaces.kt)
- OpenAddressingHashTable e CuckooHashTable implementam `MutableOpenHashTable`
- ImmutableViews.kt: `asReadOnly()` adicionado para MutableLinkedList e MutableSearchTree
- Parametro `value` renomeado para `element` em todas as arvores de busca
- Todos os testes atualizados e passando (JVM + JS compilam)

---

## Decisoes

| Data | Decisao | Justificativa |
|------|---------|---------------|
| 2026-02-16 | Itzamna Protocol adicionado a AGENTS.md e GEMINI.md | Sync CLI files com orquestrador cognitivo |
| 2026-02-16 | EdgeType enum adicionado a Edge.kt | Corrigir bug semantico onde weight==null era proxy para undirected em add(Edge) |
| 2026-02-16 | hasEdge boolean matrix em AdjacencyMatrix | Corrigir bug critico onde edges() ignorava arestas com weight==null |
| 2026-02-16 | require(index >= 0) em BitSet clear/get (JS/Native/JVM) | Consistencia cross-platform: negativos agora lancam IllegalArgumentException em todas as plataformas |
| 2026-02-16 | SearchTree/MutableSearchTree interfaces | Padrao Kotlin imutavel/mutavel para todas as arvores de busca; insert/remove retornam Boolean |
| 2026-02-16 | BST/AVL rejeitam duplicatas | Alinhamento com TreeSet semantics (set behavior); interface unificada |
| 2026-02-16 | Graph<T> split em Graph/MutableGraph | Separar leitura de escrita; algoritmos (BFS/DFS/Dijkstra/A*) so precisam Graph read-only |
| 2026-02-16 | OpenHashTable/MutableOpenHashTable interfaces | Padrao imutavel/mutavel para tabelas hash com enderecamento aberto |
| 2026-02-16 | RedBlackTree: fun size() -> val size | Consistencia com SearchTree.size property; expect+3 actuals atualizados |
| 2026-02-17 | explicitApi() habilitado em build.gradle.kts | KMP best practice: toda API publica deve ter visibilidade explicita |
| 2026-02-17 | jvmToolchain(8) adicionado | KMP best practice: compatibilidade JVM 8 para maxima portabilidade |
| 2026-02-17 | SeparateChainingHashTable criado | Preencher lacuna: so havia OpenAddressing e Cuckoo; chaining e a estrategia mais classica |
| 2026-02-17 | HashSetCollection typealias (nao Set) | Evitar conflito com kotlin.collections.Set; typealias para HashSet nativo da plataforma |
| 2026-02-17 | Revisao seletiva de visibilidade (internal) | Conforme Kotlin API Guidelines: BinaryNode, AVLNode, TrieNode, RedBlackTreeImpl, AbstractHeap, ComparableHeapImpl, ComparatorHeapImpl -> internal; TreeTraversalExtensions -> internal (cascade) |
| 2026-02-17 | expect class -> expect fun factory pattern | ArrayStack, ArrayQueue, PriorityQueue, BitSet migrados de expect class para expect fun + interface; APIs usam arrayStackOf(), arrayQueueOf(), priorityQueueOf(), bitSetOf() |

---

## Proximos passos

- [ ] Implementar Phase 3C: Differential Evolution, VNS, Memetic Algorithm, LNS
- [ ] Preencher .context/ (project.md, tech.md, rules.md) com dados reais do projeto
- [x] Auditoria F4: Arrays/BitSet — completa
- [x] Auditoria F5 (reavaliacao completa) — completa (sessao 6)
- [x] explicitApi() + jvmToolchain(8) habilitados em build.gradle.kts
- [x] Visibilidade explicita (public/internal/private) em ~75 arquivos fonte
- [x] SeparateChainingHashTable + HashSetCollection typealias adicionados
- [x] Testes expandidos: DynamicArray (4->16), ArrayQueue (7->14), PriorityQueue (6->18), BloomFilter (7->14)
- [x] KDoc pt-BR completo em RedBlackTreeImpl + 3 actuals + HashTable.kt traduzido
- [x] Revisao seletiva: 7 classes + 6 extensoes migrados para internal (nodes, impl, abstract heap)
- [x] expect class -> expect fun factory migration corrigida (consumidores + testes atualizados)

---

## Sessoes

| # | Data | Nivel | Resumo |
|---|------|-------|--------|
| 1 | 2026-02-16 | Deliberado | Itzamna init: sync AGENTS.md/GEMINI.md, drafts de memoria |
| 2 | 2026-02-16 | Deliberado+ | Auditoria DS F3 completa: fix AdjacencyMatrix.edges() (hasEdge matrix), fix add(Edge) semantics (EdgeType enum), UnionFind KDoc pt-BR, 8 test files (AdjacencyList/Matrix, DAG, OpenAddressing/CuckooHash, Multiset, UnionFind, BloomFilter expanded) |
| 3 | 2026-02-16 | Deliberado+ | Auditoria DS F4 completa: fix BitSet JS/Native/JVM negative index bugs (require guards em clear/get), expanded MatrixTest (+12 tests), expanded ParallelArrayTest (+15 tests), expanded BitSetTest (+3 negative index tests) |
| 4 | 2026-02-16 | Deliberado+ | F5 reavaliacao parcial: RedBlackTree remove()+inOrder() (CLRS RB-Delete) em expect+3 actuals+impl, DAG implements Graph<T> |
| 5 | 2026-02-16 | Deliberado+ | F5 reavaliacao: TreeInterfaces.kt (SearchTree/MutableSearchTree), 5 arvores wired, BST/AVL rejeitam dups, insert/remove Boolean, SplayTree/Treap isEmpty(), RBT size()->val size, BinomialHeap/FibonacciHeap implement MutableQueue, Graph split Graph/MutableGraph, HashTableInterfaces.kt (OpenHashTable/MutableOpenHashTable), OpenAddressing/Cuckoo wired, ImmutableViews.kt +LinkedList +SearchTree asReadOnly(), MultisetInterfaces.kt (ReadOnlyMultiset/MutableMultiset), SkipListInterfaces.kt (ReadOnlySkipList/MutableSkipList), Multiset+SkipList wired, all tests pass (JVM+JS compile) |
| 6 | 2026-02-17 | Deliberado+ | F5 auditoria final: explicitApi()+jvmToolchain(8), visibilidade explicita ~75 arquivos (391 erros), revisao seletiva internal (7 classes+6 ext), expect class->expect fun factory migration (ArrayStack/Queue/PriorityQueue/BitSet), SeparateChainingHashTable+HashSetCollection, testes expandidos (+42 novos), KDoc completo, full clean build passing (JVM+JS+Native) |

---

*Ultima atualizacao: 2026-02-17 (sessao 6).*
