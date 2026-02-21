---
description: |
  Long-term project memory: completed features, releases, hotfixes, and accumulated metrics.
  Use when: reviewing project history, understanding what was built, or onboarding new contributors.
---

# MEMORY.md - Long-Term Project History

> Historico de features, releases e hotfixes do projeto.
> Atualizado quando: feature completa, release, hotfix critico.
> Append-only: nunca apague entradas.

---

## Project

- **Nome:** algoritmos_otimizacao
- **Stack:** Kotlin 2.1.0 Multiplatform (JVM/JS/mingwX64), Gradle, kotlin.test

---

## T0: Regra de Atualizacao (OBRIGATORIA)

SEMPRE atualize este arquivo quando:
1. Feature completa (implementada e testada)
2. Release (versao publicada ou fase concluida)
3. Hotfix critico aplicado
4. Breaking change introduzido

Protocolo:
1. Proponha a atualizacao ao final da implementacao
2. Mostre o diff completo
3. AGUARDE aprovacao antes de escrever
4. NUNCA modifique este arquivo sem aprovacao

---

## Features Implementadas

### Fase 1 — Estruturas de Dados (36/36) - COMPLETO
- **Data:** 2025-02-12
- **Arquivos:** `src/commonMain/kotlin/br/uem/din/datastructures/`
- **Categorias:**
  - **Lineares:** Stack (Array/Linked), Queue (Array/Linked/Circular/Deque), LinkedList (Singly/Doubly/Circular/Unrolled), DynamicArray
  - **Arvores:** BST, AVL, RB, Splay, Treap, Cartesian, B-Tree, B+Tree, Radix, Suffix, Trie
  - **Range Query:** SegmentTree, FenwickTree
  - **Heaps:** Binary (Min/Max), Binomial, Fibonacci, PriorityQueue
  - **Grafos:** Graph (AdjList/AdjMatrix), DAG, Vertex, Edge
  - **Spatial:** KDTree, QuadTree
  - **Probabilisticas:** BloomFilter, SkipList
  - **Outros:** BitSet, UnionFind, Set, Multiset, ParallelArray, Matrix
- **Testes:** Cobertura parcial em `src/commonTest/kotlin/br/uem/din/datastructures/`

### Fase 2 — Algoritmos Fundamentais (100%) - COMPLETO
- **Data:** 2026-02-17
- **Arquivos:** `src/commonMain/kotlin/br/uem/din/algorithms/`
- **Implementados:**
  - **Sorting:** Bubble, Insertion, Selection, Shell, Merge, Quick, Heap, Counting, Radix, Bucket
  - **Searching:** Linear, Binary, Ternary, Jump, Exponential, Interpolation
  - **Graph:** BFS, DFS, Dijkstra, A*, Bellman-Ford, Floyd-Warshall, Kruskal, Prim
  - **String:** Naive, KMP, Rabin-Karp, Boyer-Moore
  - **DP:** LCS, Knapsack 0/1, Edit Distance, LIS, Fibonacci, Coin Change
  - **Greedy:** Activity Selection, Fractional Knapsack, Huffman Coding
  - **Numerical:** GCD, LCM, Extended GCD, Modular Exp, Sieve, Primality
  - **Divide & Conquer:** Max Subarray, Karatsuba, Quick Select
  - **Backtracking:** N-Queens, Subset Sum, Permutations
- **Testes:** Testes unitários implementados em `src/commonTest/kotlin/br/uem/din/algorithms/` para todas as categorias.

### Fase 3 — Heuristicas e Otimizacao (0%) - PLANEJADO
- **Status:** Nenhuma implementacao encontrada no codebase atual.
- **Planejados:**
  - Classicas: Hill Climbing, Simulated Annealing, Tabu Search, Genetic Algorithm
  - Avancadas: ILS, GRASP, PSO, ACO
  - Benchmarks: TSP, Continuous Functions (Sphere, Rastrigin, etc.)

---

## Releases

| Versao | Data | Descricao |
|--------|------|-----------|
| 1.0-SNAPSHOT | ongoing | Versao de desenvolvimento ativa |

---

## Hotfixes

| Data | Problema | Solucao |
|------|----------|---------|
| 2026-02-17 | Discrepancia Documentacao vs Codebase | Atualizacao de MEMORY.md e .context/ para refletir estado real (DS completa, Algos inicial, Heuristicas pendente) |
| 2026-02-17 | Erros de Compilação em Árvores | Correção de overrides e implementação de iterator() em BTree, BPlusTree, etc. |

---

## Metricas Atuais

- **LOC total:** ~15.000+ (Estimado com novos algoritmos)
- **Testes:** Cobertura expandida para Algoritmos (Sorting, Searching, Graph, String, DP, Greedy, Numerical, D&C, Backtracking)
- **Cobertura:** nao medida formalmente

---

*Ultima atualizacao: 2026-02-17.*

## [2026-02-20] - Atualização de Estrutura e Módulo Didático

### Contexto
- A estrutura de arquivos dentro da pasta `.context/` estava fora do padrão definido pelo Itzamna Protocol.
- O projeto não possuía um módulo de código para uso de exemplo, limitando a didática.

### Ações Executadas
- Renomeados `_meta/project-overview.md` para `project.md`, `_meta/tech-stack.md` para `tech.md` e `standards/architectural-rules.md` para `rules.md` em `.context/`.
- Criado o novo módulo Gradle `:sample` dependendo de `:datastructures`, `:algorithms` e `:optimization`.
- Adicionado um arquivo `Main.kt` (`br.uem.din.sample.Main`) exemplificando o uso de Pilhas, Grafos, Algoritmos de Ordenação e Otimização Heurística (`SimulatedAnnealing`).

### Resultado
- Arquitetura de informação alinhada com as necessidades do agente Itzamna.
- Módulo `sample` serve de ponto de entrada imediato para experimentação pelo usuário final e validação de ponta-a-ponta, tornando o repositório inteiramente "pronto para deploy".

### Atualização (Correção de Formato Enterprise)
- Após a estruturação inicial, foi necessário readequar a pasta `.context/` para o formato de maturidade **Enterprise** definido no Itzamna (`/itzamna.context`). 
- **Movimentações Corrigidas:** 
  - `project.md` -> `_meta/project-overview.md`
  - `tech.md` -> `_meta/tech-stack.md`
  - `rules.md` -> `standards/architectural-rules.md`
- **Motivação:** Seguir rigorosamente o padrão estabelecido pelo comando de governança de contexto do framework Itzamna.
