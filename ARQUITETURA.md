# ARQUITETURA.md

## 1. Se amanhã o app precisar buscar os gastos de uma API em vez da memória, quais arquivos precisam mudar? E quais com certeza NÃO mudam?

Criaria a implementação da API em `GastoRepository.kt` e a usaria no ViewModel, preservando o contrato `GastoRepository`; a tela continuaria recebendo os mesmos estados e eventos.

## 2. Por que o total é calculado e não guardado? Descreva um bug concreto que apareceria se ele fosse uma variável atualizada manualmente.

Ao remover um gasto, o total continuaria incluindo seu valor e ficaria incorreto, pois só seria atualizado ao adicionar.

## 3. O FormularioGasto não recebe o ViewModel. O que isso te permitiu fazer que não seria possível se ele recebesse?

Consegui visualizar o `FormularioGasto` no `@Preview`, inclusive simulando um erro, passando valores e callbacks sem criar um ViewModel.
