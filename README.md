# 📱 Studyando

> Una app nativa para Android diseñada para simplificar la vida académica de los estudiantes mediante herramientas de planificación, concentración y gestión de tareas.

Este proyecto nace como mi **Proyecto Final del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)** para el curso académico 2025-2026.

---

## ✨ ¿Qué ofrece Studyando?

La aplicación se centra en tres pilares clave para el día a día de cualquier estudiante:

* **Gestión de tareas:** Organiza tus pendientes por prioridades de forma clara, manteniendo todos tus datos seguros y accesibles de manera local.
* **Temporizador Pomodoro:** Un sistema de concentración basado en intervalos de estudio y descanso, optimizado con notificaciones y servicios en primer plano para que los ciclos no se detengan inesperadamente.
* **Horario lectivo:** Una vista rápida y reactiva para consultar tus clases y sesiones de forma fluida.

---

## 🛠️ Tecnologías y Arquitectura

El desarrollo sigue los estándares del ecosistema Android moderno, apostando por una arquitectura limpia y código escalable:

* **Lenguaje:** Kotlin 2.0.21
* **Arquitectura:** MVVM + Clean Architecture (separación estricta de responsabilidades)
* **Interfaz de usuario:** Jetpack Compose combinada con vistas XML tradicionales
* **Enlace de datos:** DataBinding y ViewBinding
* **Persistencia local:** Room (v2.6.1)
* **Inyección de dependencias:** Dagger Hilt (v2.52)
* **Tareas en segundo plano:** WorkManager
* **Compatibilidad:** Android SDK API 35 (Android 15) | Gradle 8.7.2

---

## 🚀 Cómo ponerlo en marcha

Si quieres clonar el repositorio, compilarlo y probarlo en tu máquina, sigue estos pasos:

1. **Clona o descarga** el repositorio en tu equipo.
2. Abre **Android Studio** (versión Ladybug o superior recomendada).
3. Selecciona **File > Open** y busca la carpeta raíz del proyecto.
4. **Sincroniza Gradle:** Deja que Android Studio descargue las dependencias (necesitarás conexión a internet en este primer paso).
5. Conecta un dispositivo físico o configura un emulador compatible con **API 35**.
6. Pulsa el botón **Run ▶** en Android Studio ¡y a disfrutar de la app!

> 💡 *Nota importante:* Asegúrate de que tu Android Studio esté configurado para compilar utilizando **JetBrains Runtime 21 (jbr-21)** para evitar conflictos con el plugin de Kotlin.

---

## 🧪 Pruebas unitarias

Para garantizar que la lógica central del negocio funciona a la perfección sin depender del framework de Android, el proyecto incluye tests unitarios en la ruta `app/src/test`:

* **`PomodoroLogicTest`**: Comprueba los cálculos de tiempo, los estados del temporizador y el formateo correcto de los ciclos.
* **`TaskModelTest`**: Valida la integridad de las tareas y la correcta asignación de prioridades.

Puedes ejecutarlos fácilmente haciendo clic derecho sobre la carpeta de test en Android Studio y seleccionando **Run Tests**.

---

## 🛟 ¿Problemas comunes de compilación?

Si te encuentras con algún pequeño contratiempo al compilar por temas de dependencias o versiones:
* Comprueba que la sincronización de Gradle finalizó al 100% sin errores de red.
* Verifica que tu JDK activo en Android Studio sea el JBR 21.
* Si surge algún aviso aislado con DataBinding, ten en cuenta que el proyecto incluye los bindings de Glassfish JAXB integrados en el Gradle para asegurar la compatibilidad.

---

## 📄 Licencia y Autoría

Desarrollado con dedicación por **Pedro José Martínez Manuel** como Proyecto Final de DAM (2025-2026). ¡Cualquier sugerencia, feedback o aportación para mejorar el código es más que bienvenida!
