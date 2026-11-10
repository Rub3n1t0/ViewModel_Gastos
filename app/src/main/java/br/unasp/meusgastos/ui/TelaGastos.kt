package br.unasp.meusgastos.ui

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import br.unasp.meusgastos.model.Categoria
import br.unasp.meusgastos.model.Gasto
import br.unasp.meusgastos.viewmodel.GastosViewModel
import java.util.Locale

private val localeBR: Locale = Locale.forLanguageTag("pt-BR")

private fun formatarReais(valor: Double): String =
    String.format(localeBR, "R$ %.2f", valor)

/** A única função da UI que conhece o ViewModel: lê o estado e repassa eventos. */
@Composable
fun TelaGastos(
    vm: GastosViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxSize().padding(16.dp)) {
        Text("Meus Gastos", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(Modifier.height(12.dp))

        FormularioGasto(
            descricao = vm.descricao,
            valorTexto = vm.valorTexto,
            categoria = vm.categoriaSelecionada,
            erro = vm.erro,
            aoDigitarDescricao = vm::aoDigitarDescricao,
            aoDigitarValor = vm::aoDigitarValor,
            aoSelecionarCategoria = vm::aoSelecionarCategoria,
            aoAdicionar = vm::adicionar
        )
        Spacer(Modifier.height(12.dp))

        FiltroCategorias(selecionada = vm.filtro, aoFiltrar = vm::aoFiltrar)
        Spacer(Modifier.height(8.dp))

        ResumoTotal(total = vm.total, quantidade = vm.quantidade)
        Spacer(Modifier.height(8.dp))

        LazyColumn(modifier = Modifier.weight(1f)) {
            items(vm.gastosVisiveis, key = { it.id }) { gasto ->
                LinhaGasto(gasto = gasto, aoRemover = { vm.remover(gasto.id) })
                HorizontalDivider()
            }
        }
    }
}

// ---------------- Componentes sem estado: recebem valores, devolvem eventos ----------------

@Composable
fun FormularioGasto(
    descricao: String,
    valorTexto: String,
    categoria: Categoria,
    erro: String?,
    aoDigitarDescricao: (String) -> Unit,
    aoDigitarValor: (String) -> Unit,
    aoSelecionarCategoria: (Categoria) -> Unit,
    aoAdicionar: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        OutlinedTextField(
            value = descricao,
            onValueChange = aoDigitarDescricao,
            label = { Text("Descrição") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = valorTexto,
            onValueChange = aoDigitarValor,
            label = { Text("Valor (R$)") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(Modifier.height(8.dp))
        Row(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Categoria.entries.forEach { c ->
                FilterChip(
                    selected = c == categoria,
                    onClick = { aoSelecionarCategoria(c) },
                    label = { Text(c.rotulo) }
                )
            }
        }
        if (erro != null) {
            Text(
                text = erro,
                color = MaterialTheme.colorScheme.error,
                modifier = Modifier.padding(top = 4.dp)
            )
        }
        Spacer(Modifier.height(8.dp))
        Button(onClick = aoAdicionar, modifier = Modifier.fillMaxWidth()) {
            Text("Adicionar")
        }
    }
}

@Composable
fun FiltroCategorias(
    selecionada: Categoria?,
    aoFiltrar: (Categoria?) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.horizontalScroll(rememberScrollState()),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        FilterChip(
            selected = selecionada == null,
            onClick = { aoFiltrar(null) },
            label = { Text("Todas") }
        )
        Categoria.entries.forEach { c ->
            FilterChip(
                selected = selecionada == c,
                onClick = { aoFiltrar(c) },
                label = { Text(c.rotulo) }
            )
        }
    }
}

@Composable
fun ResumoTotal(
    total: Double,
    quantidade: Int,
    modifier: Modifier = Modifier
) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text("Total", style = MaterialTheme.typography.labelMedium)
                Text(formatarReais(total), fontSize = 22.sp, fontWeight = FontWeight.Bold)
            }
            Text(if (quantidade == 1) "1 gasto" else "$quantidade gastos")
        }
    }
}

@Composable
fun LinhaGasto(
    gasto: Gasto,
    aoRemover: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(gasto.descricao, fontWeight = FontWeight.Medium)
            Spacer(Modifier.height(4.dp))
            Etiqueta(texto = gasto.categoria.rotulo)
        }
        Text(formatarReais(gasto.valor), fontWeight = FontWeight.Bold)
        TextButton(onClick = aoRemover) { Text("Remover") }
    }
}

@Composable
fun Etiqueta(
    texto: String,
    modifier: Modifier = Modifier
) {
    Surface(
        color = MaterialTheme.colorScheme.secondaryContainer,
        shape = MaterialTheme.shapes.small,
        modifier = modifier
    ) {
        Text(
            text = texto,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp)
        )
    }
}

// ---------------- Previews: componentes desenhados sem rodar o app ----------------

@Preview(showBackground = true)
@Composable
fun ResumoTotalPreview() {
    ResumoTotal(total = 201.70, quantidade = 4)
}

@Preview(showBackground = true)
@Composable
fun LinhaGastoPreview() {
    LinhaGasto(
        gasto = Gasto(1, "Almoço no bandejão", 18.50, Categoria.ALIMENTACAO),
        aoRemover = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FormularioComErroPreview() {
    FormularioGasto(
        descricao = "Café", valorTexto = "abc",
        categoria = Categoria.ALIMENTACAO,
        erro = "O valor precisa ser um número",
        aoDigitarDescricao = {}, aoDigitarValor = {},
        aoSelecionarCategoria = {}, aoAdicionar = {}
    )
}

@Preview(showBackground = true)
@Composable
fun FiltroCategoriasPreview() {
    FiltroCategorias(selecionada = Categoria.TRANSPORTE, aoFiltrar = {})
}

@Preview(showBackground = true)
@Composable
fun EtiquetaPreview() {
    Etiqueta(texto = Categoria.MORADIA.rotulo)
}
