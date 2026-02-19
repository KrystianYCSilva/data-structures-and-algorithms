---
description: Testing strategy for the multi-module Kotlin Multiplatform project.
---

# Testing Strategy

## Framework

- Unico framework: `kotlin.test`
- Sem Kotest e sem JUnit direto na API de testes
- Assertions padrao: `assertEquals`, `assertTrue`, `assertFalse`, `assertNull`, `assertFailsWith`

## Source Set Strategy

| Source set | Uso |
|---|---|
| `commonTest` | comportamento compartilhado e invariantes |
| `jvmTest` | interop JVM e paridade com estruturas Java quando aplicavel |
| `jsTest` | paridade comportamental JS + constraints de runtime (Karma timeout) |
| `nativeTest` | paridade comportamental Native |

## Module Gates

- `:datastructures:check`
- `:algorithms:check`
- `:extensions:check`
- `:optimization:check`
- `check` no root como gate final

## Quality Protocol (aplicado no QA iterativo)

1. Invariantes de estado e fronteira
2. Imutabilidade vs mutabilidade (`asReadOnly`)
3. Randomizado com `Random(seed)` e oracle
4. Interop por plataforma quando houver `expect/actual`
5. Error handling com `assertFailsWith`
6. Guardas de complexidade (quando viavel)

## Current Status

- QA iteracoes 11->3 resolvidas
- Suite global passando em JVM + JS + Native
- Cobertura formal (percentual) nao configurada
