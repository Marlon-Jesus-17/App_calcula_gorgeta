package com.example.CalculadoraDeGorjeta

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType

@Composable
fun TipScreen(modifier: Modifier = Modifier){

    var valorConta by rememberSaveable { mutableStateOf("") }
    var porcentagem by rememberSaveable { mutableStateOf("10") }

    val conta = valorConta.replace(',', '.').toDoubleOrNull() ?: 0.0
    val percentual = porcentagem.replace(',', '.').toDoubleOrNull() ?: 0.0
    val gorjeta = conta * percentual / 100
    val total = conta + gorjeta

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp) //Espaçamento entre itens
    ) {
        Text(
            text = "Calculadora de Gorjeta",
            style = MaterialTheme.typography.displaySmall
        )

        CampoNumero(
            valor = valorConta,
            onValorChange = {valorConta = it},
            rotulo = "Valor da conta",
            tipoTeclado = KeyboardType.Decimal  //Teclado númerico
        )

        CampoNumero(
            valor = porcentagem,
            onValorChange = {porcentagem = it},
            rotulo = "Gorjeta (%)",
            tipoTeclado = KeyboardType.Number
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            listOf(10, 15, 20, 25).forEach { opcao ->
                    BotaoPorcentagem(
                        valor = opcao,
                        selecionado = porcentagem == opcao.toString(),
                        onClick = {porcentagem = it.toString()}
                    )
            }
        }

        Text("Gorjeta: R$ ${"%.2f".format(gorjeta)}") //Formata o valor para o padrão do país
        Text("Total: R$ ${"%.2f".format(total)}")
    }
}

@Composable
fun BotaoPorcentagem(valor: Int, onClick: (Int) -> Unit, selecionado: Boolean){
    if (selecionado){
        Button( //Button é o botão preenchido
            onClick = {onClick(valor)}
        ) {
            Text("$valor%")
        }
    }else{
        OutlinedButton( //OutlinedButton é o botão só com contorno
            onClick = {onClick(valor)}
        ) {
            Text("$valor%")
        }
    }
}

@Composable
fun CampoNumero(
    valor: String,
    onValorChange: (String) -> Unit,
    rotulo: String,
    tipoTeclado: KeyboardType,
    modifier: Modifier = Modifier
){
    OutlinedTextField(
        modifier = modifier.fillMaxWidth(),
        value = valor,
        onValueChange = onValorChange,
        label = {Text(rotulo)},
        keyboardOptions = KeyboardOptions(keyboardType = tipoTeclado)
    )
}

