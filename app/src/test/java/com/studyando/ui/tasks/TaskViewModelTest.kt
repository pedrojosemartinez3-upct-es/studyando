package com.studyando.ui.tasks

import com.studyando.MainDispatcherRule // Asegúrate de importar la regla
import com.studyando.data.repo.StudyRepository
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TasksViewModelTest {

    // ESTA LÍNEA ES LA SOLUCIÓN:
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var viewModel: TasksViewModel
    private val repository: StudyRepository = mockk(relaxed = true)

    @Before
    fun setup() {
        viewModel = TasksViewModel(repository)
    }

    @Test
    fun `al añadir tarea se llama al repositorio una vez`() {
        // Ahora el viewModelScope.launch funcionará correctamente en el test
        viewModel.addTask("Estudiar", "Tema 1", 1, null)

        coVerify { repository.addTask(any()) }
    }
}