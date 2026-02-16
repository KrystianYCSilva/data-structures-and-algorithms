---
description: Project-specific architectural decisions (ADRs) with dates and rationale.
---

# Key Decisions & ADRs

| # | Data | Decisao | Status |
|---|------|---------|--------|
| 1 | 2025-02 | Adotar Kotlin Multiplatform com targets JVM, JS(IR), mingwX64 para maximizar portabilidade | Aceita |
| 2 | 2025-02 | Codigo principal em commonMain; adaptadores via expect/actual apenas quando necessario (ArrayStack, BitSet) | Aceita |
| 3 | 2025-02 | Separar interfaces read-only e mutable (Stack/MutableStack, Queue/MutableQueue) seguindo padrao Kotlin stdlib | Aceita |
| 4 | 2025-02 | Retornar null em operacoes sobre colecoes vazias (pop, peek, dequeue) em vez de lancar excecoes | Aceita |
| 5 | 2025-02 | KDoc em portugues com tabelas de complexidade e referencias academicas (CLRS, Knuth, papers originais) | Aceita |
| 6 | 2025-02 | Usar apenas kotlin.test como framework de testes — sem Kotest, sem JUnit direto | Aceita |
| 7 | 2025-02 | Zero dependencias externas — toda implementacao usa apenas Kotlin stdlib | Aceita |
| 8 | 2026-02 | Heuristicas com tipos compartilhados OptSolution/OptResult e benchmarks padronizados (TSP, funcoes continuas) | Aceita |
| 9 | 2026-02-16 | Itzamna Protocol adicionado a AGENTS.md e GEMINI.md para orquestracao cognitiva | Aceita |
