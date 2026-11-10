package br.unasp.meusgastos.model

/** O contrato: diz O QUE a fonte de dados faz, sem dizer COMO. */
interface GastoRepository {
    fun listar(): List<Gasto>
    fun adicionar(descricao: String, valor: Double, categoria: Categoria)
    fun remover(id: Long)
}

/** Implementação de hoje: os dados ficam em memória, começando vazios. */
class GastoRepositoryMemoria : GastoRepository {
    private val gastos = mutableListOf<Gasto>()
    private var proximoId = 1L

    override fun listar(): List<Gasto> = gastos.toList()

    override fun adicionar(descricao: String, valor: Double, categoria: Categoria) {
        gastos.add(Gasto(proximoId++, descricao, valor, categoria))
    }

    override fun remover(id: Long) {
        gastos.removeAll { it.id == id }
    }
}
