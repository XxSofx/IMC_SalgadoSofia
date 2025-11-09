package com.example.imc_salgadosofia.ui.theme

import android.content.Context
import com.example.imc_salgadosofia.constants.ImcConstants
import com.example.imc_salgadosofia.R

object ColorUtils {

    fun obtenerNombreColor(color: Long, context: Context): String {
        return when (color) {
            ImcConstants.COLOR_BAJO_PESO -> context.getString(R.string.color_azul_claro)
            ImcConstants.COLOR_PESO_NORMAL -> context.getString(R.string.color_verde_claro)
            ImcConstants.COLOR_SOBREPESO -> context.getString(R.string.color_amarillo_claro)
            ImcConstants.COLOR_OBESIDAD -> context.getString(R.string.color_rojo_claro)
            else -> context.getString(R.string.color_personalizado)
        }
    }
}
