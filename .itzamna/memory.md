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

**Auditoria DS:** F1 (Linear) e F2 (Tree/Heap/Spatial) completas. F3 (Graph/Hash/Set/UnionFind/Probabilistic) completa.
F4 (Arrays/BitSet) e F5 (missing structures) pendentes.

---

## Decisoes

| Data | Decisao | Justificativa |
|------|---------|---------------|
| 2026-02-16 | Itzamna Protocol adicionado a AGENTS.md e GEMINI.md | Sync CLI files com orquestrador cognitivo |
| 2026-02-16 | EdgeType enum adicionado a Edge.kt | Corrigir bug semantico onde weight==null era proxy para undirected em add(Edge) |
| 2026-02-16 | hasEdge boolean matrix em AdjacencyMatrix | Corrigir bug critico onde edges() ignorava arestas com weight==null |

---

## Proximos passos

- [ ] Implementar Phase 3C: Differential Evolution, VNS, Memetic Algorithm, LNS
- [ ] Preencher .context/ (project.md, tech.md, rules.md) com dados reais do projeto
- [ ] Auditoria F4: Arrays/BitSet (BitSet JS/Native negative index bug, Matrix/ParallelArray missing tests)
- [ ] Auditoria F5: Missing DS variations (identificar e implementar)

---

## Sessoes

| # | Data | Nivel | Resumo |
|---|------|-------|--------|
| 1 | 2026-02-16 | Deliberado | Itzamna init: sync AGENTS.md/GEMINI.md, drafts de memoria |
| 2 | 2026-02-16 | Deliberado+ | Auditoria DS F3 completa: fix AdjacencyMatrix.edges() (hasEdge matrix), fix add(Edge) semantics (EdgeType enum), UnionFind KDoc pt-BR, 8 test files (AdjacencyList/Matrix, DAG, OpenAddressing/CuckooHash, Multiset, UnionFind, BloomFilter expanded) |

---

*Ultima atualizacao: 2026-02-16 (sessao 2).*
