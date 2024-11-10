package com.leofranc.violao_suite.ui.tablaturas

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.leofranc.violao_suite.model.Tablatura
import com.leofranc.violao_suite.viewmodel.TablaturaViewModel
import kotlinx.coroutines.launch
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.livedata.observeAsState

@Composable
fun TablaturaEditorScreen(navController: NavController, viewModel: TablaturaViewModel, tablaturaId: Long) {
    val coroutineScope = rememberCoroutineScope()
    var titulo by remember { mutableStateOf("") }
    var descricao by remember { mutableStateOf("") }
    val posicoes by viewModel.posicoes.observeAsState(emptyList())
    var isEditing by remember { mutableStateOf(false) }

    // ScrollState para o scroll horizontal
    val horizontalScrollState = rememberScrollState()

    // Carrega a tablatura ao iniciar a tela para obter título e descrição
    LaunchedEffect(tablaturaId) {
        coroutineScope.launch {
            val tablatura = viewModel.carregarTablatura(tablaturaId)
            titulo = tablatura?.titulo ?: ""
            descricao = tablatura?.descricao ?: ""
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Editar Tablatura") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                actions = {
                    if (isEditing) {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                val tablatura = Tablatura(id = tablaturaId, titulo = titulo, descricao = descricao)
                                viewModel.salvarTablatura(tablatura)
                                isEditing = false
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Save, contentDescription = "Salvar")
                        }
                        IconButton(onClick = {
                            coroutineScope.launch {
                                viewModel.deletarTablatura(Tablatura(id = tablaturaId, titulo = titulo, descricao = descricao))
                                navController.popBackStack()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Deletar Tablatura")
                        }
                    } else {
                        IconButton(onClick = { isEditing = true }) {
                            Icon(imageVector = Icons.Default.Edit, contentDescription = "Editar")
                        }
                    }
                }
            )
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(16.dp)
            ) {
                if (isEditing) {
                    TextField(
                        value = titulo,
                        onValueChange = { titulo = it },
                        label = { Text("Título") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                    TextField(
                        value = descricao,
                        onValueChange = { descricao = it },
                        label = { Text("Descrição") },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)
                    )
                } else {
                    Text(
                        text = titulo,
                        style = MaterialTheme.typography.h4,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = descricao,
                        style = MaterialTheme.typography.body1,
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center
                    )
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Títulos das colunas com rolagem horizontal
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(horizontalScrollState),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Cordas",
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.width(60.dp),
                        textAlign = TextAlign.Center
                    )
                    posicoes.forEachIndexed { index, _ ->
                        Text(
                            text = "${index + 1}",
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.width(60.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Tabela de cordas e posições com rolagem horizontal
                val cordas = listOf("E", "B", "G", "D", "A", "E")
                cordas.forEachIndexed { cordaIndex, corda ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .horizontalScroll(horizontalScrollState),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = corda,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.width(60.dp),
                            textAlign = TextAlign.Center
                        )

                        posicoes.forEach { posicao ->
                            val valor = when (cordaIndex) {
                                0 -> posicao.corda1
                                1 -> posicao.corda2
                                2 -> posicao.corda3
                                3 -> posicao.corda4
                                4 -> posicao.corda5
                                5 -> posicao.corda6
                                else -> 0
                            }

                            PosicaoField(casa = valor) { novoValor ->
                                val novaPosicao = when (cordaIndex) {
                                    0 -> posicao.copy(corda1 = novoValor)
                                    1 -> posicao.copy(corda2 = novoValor)
                                    2 -> posicao.copy(corda3 = novoValor)
                                    3 -> posicao.copy(corda4 = novoValor)
                                    4 -> posicao.copy(corda5 = novoValor)
                                    5 -> posicao.copy(corda6 = novoValor)
                                    else -> posicao
                                }
                                viewModel.atualizarPosicao(novaPosicao)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Botões "+" e "-"
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(onClick = { viewModel.adicionarPosicao(tablaturaId) }) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Posição")
                    }
                    IconButton(onClick = {
                        coroutineScope.launch {
                            if (posicoes.isNotEmpty()) {
                                viewModel.deletarPosicao(posicoes.last())
                            }
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Remove, contentDescription = "Remover Posição")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))

                // Botão de Play
                IconButton(
                    onClick = { /* Lógica para tocar a tablatura */ },
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                ) {
                    Icon(imageVector = Icons.Filled.PlayArrow, contentDescription = "Play", modifier = Modifier.size(48.dp))
                }
            }
        }
    )

    LaunchedEffect(tablaturaId) {
        viewModel.carregarPosicoes(tablaturaId)
    }
}

@Composable
fun PosicaoField(casa: Int, onValueChange: (Int) -> Unit) {
    var displayValue by remember { mutableStateOf(if (casa == -1) "-" else casa.toString()) }

    TextField(
        value = displayValue,
        onValueChange = { newValue ->
            displayValue = newValue  // Atualiza o valor exibido

            // Converte o valor de entrada para Int e chama onValueChange com o valor correto
            val intValue = newValue.toIntOrNull()
            onValueChange(intValue ?: -1)
        },
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        modifier = Modifier.width(60.dp),
        textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center)
    )
}
