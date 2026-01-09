# Studyando

Autor: Pedro José Martínez Manuel
Ciclo: Grado Superior DAM - Proyecto Final  
Año: 2025-2026

1. Descripción del Proyecto
Studyando es una aplicación nativa para Android diseñada para centralizar y optimizar la vida académica del estudiante. Integra tres pilares fundamentales de productividad:

Gestión de Tareas: Organización por prioridades con persistencia local.

Temporizador Pomodoro: Implementación de ciclos de estudio con notificaciones de alta prioridad y servicios en primer plano.

Horario Lectivo: Visualización reactiva de las sesiones de clase.

2. Requisitos del Entorno
Para este proyecto se ha utilizado un stack tecnológico moderno y profesional:

Arquitectura: MVVM (Model-View-ViewModel) con Clean Architecture.

Lenguaje: Kotlin 2.0.21.

Interfaz: Jetpack Compose (UI Declarativa) y XML (DataBinding/ViewBinding).

Base de Datos: Room 2.6.1.

Inyección de Dependencias: Dagger Hilt 2.52.

Gestión de Tareas en Segundo Plano: WorkManager.

SDK: Compilado para API 35 (Android 15).

3. Instrucciones de Ejecución
1. Extraer el contenido del ZIP.
2. Abrir Android Studio y seleccionar File > Open.
3. Navegar hasta la carpeta `cod/` y seleccionar el archivo `build.gradle` de la raíz.
4. Esperar a que la sincronización de Gradle finalice (se requiere conexión a Internet para descargar dependencias).
5. Ejecutar en un emulador o dispositivo físico mediante el botón Run.

4. Configuración para la compilación.
Debido a las particularidades de las versiones actuales de Android Studio y Java, el proyecto está configurado para ser "Plug & Play" siempre que se cumpla lo siguiente:

IDE: Android Studio Ladybug o superior.

JDK: Utilizar JetBrains Runtime 21 (jbr-21).

Gradle Plugin: 8.7.2.

Compose: Utiliza el nuevo Compose Compiler Gradle Plugin (obligatorio para Kotlin 2.0+).

4. Pruebas (Unit Testing)
Se han implementado pruebas unitarias en la carpeta app/src/test para validar la lógica de negocio sin dependencia del framework de Android:
`PomodoroLogicTest`: Valida el algoritmo de formateo de tiempo y cálculo de progreso.
`TaskModelTest`: Verifica la integridad de los datos en la creación de tareas y prioridades.

Para ejecutar los tests: Hacer clic derecho en la carpeta `java (test)` y seleccionar "Run 'Tests in com.studyando'".

5. Solución de Problemas (Build)
En caso de errores de compilación con Kapt, se han integrado dependencias de Glassfish JAXB en el `build.gradle` para garantizar la compatibilidad de DataBinding con versiones modernas de Java.
