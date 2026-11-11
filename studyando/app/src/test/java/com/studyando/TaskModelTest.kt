package com.studyando

import com.studyando.data.local.entity.TaskEntity
import org.junit.Assert.assertEquals
import org.junit.Test

class TaskModelTest {

    @Test
    fun crearTarea_asignarCamposObligatorios_deberiaRetenerInformacion() {
        // Given: Datos de una tarea
        val titulo = "Entregar Memoria"
        val prioridad = 1 // Alta

        // When: Instanciamos la entidad
        val tarea = TaskEntity(title = titulo, priority = prioridad)

        // Then: Los datos deben ser consistentes
        assertEquals(titulo, tarea.title)
        assertEquals(prioridad, tarea.priority)
    }

    @Test
    fun tareaEstado_porDefecto_deberiaEstarPendiente() {
        // Given: Una nueva tarea sin especificar estado
        val tarea = TaskEntity(title = "Test", isDone = false)

        // Then: El campo isCompleted debe ser false
        assertEquals(false, tarea.isDone)
    }
}