# 📚 Studyando - Tu Compañero de Estudio

**Studyando** es una aplicación para Android desarrollada en Kotlin, diseñada para optimizar la organización académica y mejorar la productividad de los estudiantes.

Este proyecto ha sido desarrollado como **Proyecto Final de Ciclo Formativo**, implementando una arquitectura moderna y escalable basada en los principios de *Modern Android Development* (MAD).

La aplicación se divide en tres módulos funcionales integrados:

### 1. ✅ Gestión de Tareas.
* Sistema completo **CRUD** (Crear, Leer, Actualizar, Borrar).
* Persistencia de datos local mediante **Room Database**.
* Interfaz reactiva utilizando **Kotlin Flows**.
* Gestos de deslizamiento (*Swipe*) para eliminar tareas.

### 2. 🍅 Temporizador Pomodoro
* Técnica de estudio integrada para gestionar tiempos de concentración y descanso.
* Configuración personalizable (duración de pomodoros, descansos cortos/largos y cantidad de ciclos).
* Persistencia de preferencias de usuario mediante **Jetpack DataStore**.

### 3. 📅 Horario de Clases
* Visualización diaria de asignaturas, aulas y horarios.
* Base de datos relacional para gestionar las sesiones semanales.

---

## 🛠️ Stack Tecnológico y Arquitectura

El proyecto sigue una arquitectura **MVVM (Model-View-ViewModel)** y el patrón **Repository** para asegurar una separación clara de responsabilidades y un código mantenible.

* **Lenguaje:** [Kotlin](https://kotlinlang.org/)
* **Interfaz de Usuario:** XML con **ViewBinding**.
* **Inyección de Dependencias:** [Hilt](https://dagger.dev/hilt/) (Dagger).
* **Base de Datos Local:** [Room](https://developer.android.com/training/data-storage/room).
* **Almacenamiento de Preferencias:** [Jetpack DataStore](https://developer.android.com/topic/libraries/architecture/datastore) (Preferences).
* **Navegación:** [Jetpack Navigation Component](https://developer.android.com/guide/navigation) (Single Activity Architecture).
* **Concurrencia:** Coroutines & Flow.
* **Gestión de Fondo:** WorkManager (para notificaciones de temporizador).

---

## 📱 Capturas de Pantalla

| Lista de Tareas | Temporizador Pomodoro | Horario |
|:---:|:---:|:---:|
| ![Tareas](https://via.placeholder.com/200x400?text=Captura+Tareas) | ![Pomodoro](https://via.placeholder.com/200x400?text=Captura+Pomodoro) | ![Horario](https://via.placeholder.com/200x400?text=Captura+Horario) |

> *Nota: Las capturas muestran la interfaz de usuario en modo claro/oscuro.*

---

## 🚀 Instalación y Uso

1.  Clonar el repositorio:
    ```bash
    git clone [https://github.com/](https://github.com/)[TU_USUARIO]/Studyando.git
    ```
2.  Abrir el proyecto en **Android Studio**.
3.  Sincronizar el proyecto con Gradle.
4.  Ejecutar en un emulador o dispositivo físico (Mínimo SDK 24).

---
## 👤
**Pedro José Martínez Manuel**
* **Ciclo:** Desarrollo de Aplicaciones Multiplataforma
---
