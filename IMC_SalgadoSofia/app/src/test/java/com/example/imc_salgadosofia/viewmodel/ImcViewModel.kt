package com.example.imc_salgadosofia.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import android.content.Context
import com.example.imc_salgadosofia.constants.ImcConstants
import com.example.imc_salgadosofia.model.ImcResult
import com.example.imc_salgadosofia.model.ValidacionImc
import com.example.imc_salgadosofia.R
class ImcViewModel : ViewModel() {

    private val _peso = mutableStateOf("")

    private val _altura = mutableStateOf("")

    private val _resultado = mutableStateOf(ImcResult())

    val peso: State<String> = _peso

    val altura: State<String> = _altura

    val resultado: State<ImcResult> = _resultado

    fun actualizarPeso(nuevoPeso: String) {
        if (nuevoPeso.isEmpty() || nuevoPeso.all { it.isDigit() }) {
            _peso.value = nuevoPeso
        }
    }

    fun actualizarAltura(nuevaAltura: String) {
        if (nuevaAltura.isEmpty() || nuevaAltura.all { it.isDigit() }) {
            _altura.value = nuevaAltura
        }
    }
    fun calcularImc(context: Context) {
        if (_peso.value.isBlank() || _altura.value.isBlank()) {
            _resultado.value = ImcResult(
                imc = 0.0,
                clasificacion = context.getString(R.string.error_campos_vacios),
                color = ImcConstants.COLOR_ERROR
            )
            return
        }

        val pesoInt = _peso.value.toIntOrNull()
        val alturaInt = _altura.value.toIntOrNull()

        if (pesoInt == null || alturaInt == null) {
            _resultado.value = ImcResult(
                imc = 0.0,
                clasificacion = context.getString(R.string.error_valores_invalidos),
                color = ImcConstants.COLOR_ERROR
            )
            return
        }

        if (pesoInt <= ImcConstants.VALOR_MIN || alturaInt <= ImcConstants.VALOR_MIN) {
            _resultado.value = ImcResult(
                imc = 0.0,
                clasificacion = context.getString(R.string.error_valores_negativos),
                color = ImcConstants.COLOR_ERROR
            )
            return
        }

        if (pesoInt > ImcConstants.PESO_MAX) {
            _resultado.value = ImcResult(
                imc = 0.0,
                clasificacion = context.getString(R.string.error_peso_alto),
                color = ImcConstants.COLOR_ERROR
            )
            return
        }

        if (alturaInt > ImcConstants.ALTURA_MAX) {
            _resultado.value = ImcResult(
                imc = 0.0,
                clasificacion = context.getString(R.string.error_altura_formato),
                color = ImcConstants.COLOR_ERROR
            )
            return
        }

        val alturaMetros = alturaInt / ImcConstants.CM_A_METROS
        val imc = pesoInt / (alturaMetros * alturaMetros)

        val validacion: ValidacionImc = validarIndice(imc, context)

        _resultado.value = ImcResult(
            imc = imc,
            clasificacion = validacion.clasificacion,
            color = validacion.color
        )
    }

    private fun validarIndice(imc: Double, context: Context): ValidacionImc {
        return when {
            imc < ImcConstants.IMC_BAJO_PESO_LIMITE -> ValidacionImc(
                context.getString(R.string.imc_bajo_peso),
                ImcConstants.COLOR_BAJO_PESO
            )
            imc <= ImcConstants.IMC_NORMAL_LIMITE -> ValidacionImc(
                context.getString(R.string.imc_peso_normal),
                ImcConstants.COLOR_PESO_NORMAL
            )
            imc <= ImcConstants.IMC_SOBREPESO_LIMITE -> ValidacionImc(
                context.getString(R.string.imc_sobrepeso),
                ImcConstants.COLOR_SOBREPESO
            )
            else -> ValidacionImc(
                context.getString(R.string.imc_obesidad),
                ImcConstants.COLOR_OBESIDAD
            )
        }
    }

    fun limpiarCampos() {
        _peso.value = ""
        _altura.value = ""
        _resultado.value = ImcResult()
    }
}
