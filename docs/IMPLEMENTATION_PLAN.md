---
description: "Plano de consolidacao para release v0.1.0: modularizacao, QA final, publicacao e backlog tecnico."
---

# Implementation Plan (Release Track v0.1.0)

## 1. Estado atual (baseline)

- Estruturas de dados: 36 implementadas
- Algoritmos classicos: 46 implementados
- Heuristicas/meta-heuristicas: 12 implementadas
- Modelagens de problema: 7
- Multi-modulo Gradle: concluido (`:datastructures`, `:algorithms`, `:extensions`, `:optimization`, `:bom`)
- Qualidade: `gradlew check` verde em JVM + JS + Native

## 2. Objetivo do plano

Preparar e publicar a versao `0.1.0` com:

1. API estabilizada por modulo
2. Documentacao consistente com o codigo real
3. Metadados de publicacao prontos para repositorio Maven
4. Trilhas de validacao reproduziveis (build/test/release)

## 3. Escopo da release

### Incluido em `0.1.0`

- Todos os 5 modulos publicados
- BOM para alinhamento de versao
- Suites de teste atuais e QA iterativo (iteracoes 11->3 resolvidas)
- Documentacao de uso, arquitetura e roadmap atualizada

### Fora do escopo de `0.1.0`

- Completar backlog de algoritmos remanescentes (2 DP, 1 backtracking, 5 divide-and-conquer)
- Cobertura de benchmark/performance automatizada em CI
- Pipeline de release totalmente automatizado

## 4. Plano por etapas

### Etapa A - Documentacao e contexto (concluida nesta sessao)

- [x] Atualizar `docs/` para API real
- [x] Atualizar `.context/` com arquitetura modular
- [x] Revisar comandos Gradle por modulo

### Etapa B - Hardening de release

- [ ] Revisar coordenadas finais de artefato por modulo
- [ ] Validar POM/SCM/licenca/developers em todos os modulos
- [ ] Gerar checklist de publicacao com preflight local

### Etapa C - Publicacao

- [ ] Executar build limpo (`clean check`)
- [ ] Publicar snapshots internos para validacao de consumo
- [ ] Publicar release `0.1.0`

## 5. Matriz de modulos

| Modulo | Dependencias | Publica |
|---|---|---|
| `:datastructures` | — | `br.uem.din:datastructures:0.1.0` |
| `:algorithms` | `:datastructures` | `br.uem.din:algorithms:0.1.0` |
| `:extensions` | `:datastructures`, `:algorithms` | `br.uem.din:extensions:0.1.0` |
| `:optimization` | — | `br.uem.din:optimization:0.1.0` |
| `:bom` | (constraints) | `br.uem.din:bom:0.1.0` |

## 6. Comandos de validacao

```bash
./gradlew :datastructures:check
./gradlew :algorithms:check
./gradlew :extensions:check
./gradlew :optimization:check
./gradlew check
```

## 7. Riscos e mitigacoes

- Risco: inconsistencias de API entre docs e codigo
  - Mitigacao: exemplos compilaveis com funcoes/fabricas reais
- Risco: regressao em targets JS/Native
  - Mitigacao: manter `check` cross-platform como gate obrigatorio
- Risco: dependencia circular entre modulos
  - Mitigacao: manter `extensions` como camada superior; `optimization` isolado

## 8. Backlog tecnico apos release

1. Completar backlog da Fase 2 (algoritmos faltantes)
2. Ampliar Iteracao 12 (interop sweep) para mais pacotes
3. Introduzir CI com matriz JVM/JS/Native e publicacao assistida

---

Ultima atualizacao: 2026-02-19
