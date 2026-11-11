package com.studyando

import org.junit.Assert.assertEquals
import org.junit.Test

class PomodoroLogicTest {

    @Test
    fun formatearTiempo_milisegundosEstandar_deberiaRetornarFormatoReloj() {
        // Given: 150.000 ms (2 min y 30 seg)
        val ms = 150000L

        // When: Calculamos minutos y segundos
        val minutos = ms / 60000
        val segundos = (ms / 1000) % 60
        val resultado = String.format("%02d:%02d", minutos, segundos)

        // Then: El resultado debe ser "02:30"
        assertEquals("02:30", resultado)
    }

    @Test
    fun calcularPorcentaje_progresoMedio_deberiaRetornarCincuentaPorCiento() {
        // Given: Un tiempo total de 20 min y restan 10 min
        val tiempoTotal = 1200000L // 20 min
        val tiempoRestante = 600000L // 10 min

        // When: Calculamos el progreso para la ProgressBar
        val progreso = ((tiempoRestante.toFloat() / tiempoTotal) * 100).toInt()

        // Then: Debe ser exactamente 50
        assertEquals(50, progreso)
    }
}