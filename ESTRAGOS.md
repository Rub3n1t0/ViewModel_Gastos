# ESTRAGOS.md — Etapa 7

Testes feitos com `GastoRepositoryMemoria` (lista começando vazia):

```kotlin
private val repositorio: GastoRepository = GastoRepositoryMemoria()
```

---

## Estrago 1 — `viewModel()` → `GastosViewModel()`

**Previsão:** ao digitar, a recomposição pode criar outro ViewModel e apagar a descrição, dificultando manter o texto digitado.

**O que aconteceu:** _(preencher após rodar)_

---

## Estrago 2 — `mutableStateListOf` → `mutableListOf`

**Previsão:** adicionar pode aparecer, porque limpar os campos provoca recomposição, mas remover não atualiza a tela imediatamente, pois a lista comum não notifica o Compose.

**O que aconteceu:** _(preencher após rodar)_

---

## Estrago 3 — total guardado em variável, atualizado só no `adicionar()`

**Previsão:** após adicionar R$ 10, R$ 20 e R$ 30 e remover R$ 20, o total permanece R$ 60, embora devesse mostrar R$ 40.

**O que aconteceu:** _(preencher após rodar)_

---

## Estrago 4 — construtor limitado a `GastoRepositoryMemoria`

**Previsão:** passar `GastoRepositoryDemo()` deixa de compilar, com um diagnóstico equivalente a:

```
Argument type mismatch: actual type is 'GastoRepositoryDemo',
but 'GastoRepositoryMemoria' was expected.
```

**Mensagem literal do Android Studio:** _(colar aqui)_

---

## Estrago 5 — validação de volta para a tela

Como `vm` não existe dentro de `FormularioGasto`, a experiência é feita em `TelaGastos`, onde o callback é passado:

```kotlin
aoAdicionar = {
    if (vm.descricao.isNotBlank()) {
        vm.adicionar()
    }
}
```

**Previsão:** funciona, mas uma segunda tela precisaria repetir a regra caso ela fosse retirada do ViewModel. Mantendo a validação em `adicionar()`, qualquer tela que usar esse método já estará protegida.

**O que aconteceu:** _(preencher após rodar)_
