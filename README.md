# 📱 Studyando

Una app nativa para Android diseñada para simplificar la vida académica de los estudiantes mediante herramientas de planificación, concentración y gestión de tareas.

Este proyecto forma parte de mi **Proyecto Final del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)** (curso 2025-2026).

---

## ✨ ¿Qué hace la app?

* **Gestión de tareas:** Organiza tus pendientes por prioridades de forma clara, manteniendo los datos siempre seguros y guardados localmente.
* **Temporizador Pomodoro:** Intervalos de estudio y descanso pensados para rendir al máximo, respaldados por notificaciones y servicios en primer plano para que los ciclos no se interrumpan.
* **Horario lectivo:** Una vista rápida y reactiva para consultar tus clases del día sin mareos.

---

## 🛠️ Tecnologías y Arquitectura

El desarrollo sigue los estándares del ecosistema Android moderno, apostando por código limpio y escalable:

* **Lenguaje:** Kotlin 2.0.21
* **Arquitectura:** MVVM + Clean Architecture
* **Interfaz:** Jetpack Compose + Vistas XML tradicionales
* **Enlace de datos:** DataBinding / ViewBinding
* **Base de datos:** Room (v2.6.1)
* **Inyección de dependencias:** Dagger Hilt (v2.52)
* **Segundo plano:** WorkManager
* **Compatibilidad:** Android SDK API 35 (Android 15) | Gradle 8.7.2

---

## 🚀 Cómo ponerlo en marcha

Para clonar, compilar y probar el proyecto en tu equipo:

1. Clona o descarga el repositorio en tu ordenador.
2. Abre **Android Studio** (versión Ladybug o superior recomendada).
3. Selecciona **File > Open** y busca la carpeta raíz del proyecto.
4. **Sincroniza Gradle:** Deja que Android Studio descargue las dependencias (necesitarás internet en este primer arranque).
5. Conecta un móvil físico o arranca un emulador con **API 35**.
6. Dale al botón de **Run ▶** ¡y a correr!

> 💡 *Asegúrate de que Android Studio esté configurado para compilar con **JetBrains Runtime 21 (jbr-21)** para evitar conflictos con Kotlin.*

---

## 🧪 Pruebas unitarias

El proyecto incluye tests unitarios en la ruta `app/src/test` para comprobar la lógica de negocio de forma aislada:

* **`PomodoroLogicTest`**: Verifica los cálculos del temporizador, los estados y el formateo de los ciclos.
* **`TaskModelTest`**: Comprueba la integridad de las tareas y el manejo de prioridades.

Puedes ejecutarlos fácilmente haciendo clic derecho sobre la carpeta de test en Android Studio y seleccionando **Run Tests**.

---

## 📄 Autoría

Desarrollado por **Pedro José Martínez Manuel** como Proyecto Final de DAM (2025-2026). ¡Cualquier sugerencia o feedback es bienvenido!
