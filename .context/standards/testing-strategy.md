---
description: Testing strategy based on actual project test infrastructure.
---

# Testing Strategy

## Framework

- **Unico framework**: `kotlin.test` (commonTest, jvmTest, jsTest)
- **Sem Kotest**, sem JUnit direto, sem property-based testing configurado
- Assertions: `assertTrue`, `assertFalse`, `assertEquals`, `assertNull` de `kotlin.test`

## Organizacao

| Source Set | Proposito | Runner |
|------------|-----------|--------|
| commonTest | Testes platform-independent (maioria) | kotlin.test |
| jvmTest | Testes JVM-specific | kotlin.test (JUnit runner) |
| jsTest | Testes JS-specific | Karma + ChromeHeadless |

## Convencoes

- Test class: `{ClassUnderTest}Test` no mesmo pacote (e.g., `StackTest`)
- Test method: `test{Implementation}{Operation}` (e.g., `testArrayStackPushPop`)
- Um comportamento por metodo de teste; testes independentes entre si
- Preferir testes em commonTest; usar jvmTest/jsTest apenas para comportamento platform-specific

## Comandos

```sh
gradlew.bat check                 # Todos os testes
gradlew.bat jvmTest               # Apenas JVM
gradlew.bat jsTest                # Apenas JS (Karma/ChromeHeadless requerido)
gradlew.bat jvmTest --tests "br.uem.din.datastructures.stack.StackTest"  # Classe
gradlew.bat jvmTest --tests "br.uem.din.datastructures.stack.StackTest.testArrayStackPushPop"  # Metodo
gradlew.bat jvmTest --tests "br.uem.din.datastructures.heap.*"  # Wildcard
```

## Metricas Atuais

- **Total Testes:** Concentrados em Estruturas de Dados (`datastructures/`). Quantidade exata pendente de execucao completa.
- **Cobertura:** Nao medida formalmente (sem plugin de coverage configurado).
- **Status:** Algoritmos (Fase 2) e Heuristicas (Fase 3) ainda nao possuem cobertura de testes significativa implementada.
