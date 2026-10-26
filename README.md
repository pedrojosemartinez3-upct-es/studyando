# Studyando

**Studyando** es una aplicación nativa para Android desarrollada como Proyecto Final del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM). Su objetivo es centralizar y mejorar la organización académica del estudiante mediante herramientas de productividad, planificación y gestión del tiempo.

## Información del proyecto

- **Autor:** Pedro José Martínez Manuel
- **Ciclo formativo:** Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM)
- **Tipo de proyecto:** Proyecto Final
- **Curso académico:** 2025-2026

## 1. Descripción del proyecto

Studyando nace con el propósito de facilitar la organización diaria del estudiante a través de una aplicación que integra tres funcionalidades principales:

### Gestión de tareas
Permite organizar las tareas académicas según su prioridad, facilitando el seguimiento de las actividades pendientes y manteniendo los datos almacenados localmente.

### Temporizador Pomodoro
Incorpora un temporizador basado en la técnica Pomodoro para favorecer la concentración y mejorar los hábitos de estudio. Incluye notificaciones de alta prioridad y servicios en primer plano para gestionar los ciclos de estudio de forma fiable.

### Horario lectivo
Ofrece una visualización reactiva del horario de clases, permitiendo consultar las sesiones lectivas de forma clara y organizada.

## 2. Tecnologías y arquitectura

El proyecto utiliza un conjunto de tecnologías del ecosistema Android moderno, siguiendo principios de separación de responsabilidades y mantenibilidad del código.

| Tecnología | Versión / Implementación |
|---|---|
| Lenguaje de programación | Kotlin 2.0.21 |
| Arquitectura | MVVM + Clean Architecture |
| Interfaz de usuario | Jetpack Compose y XML |
| Enlace de vistas | DataBinding / ViewBinding |
| Persistencia local | Room 2.6.1 |
| Inyección de dependencias | Dagger Hilt 2.52 |
| Tareas en segundo plano | WorkManager |
| Android SDK | API 35 (Android 15) |
| Compilación | Gradle y Android Gradle Plugin 8.7.2 |

La combinación de estas tecnologías permite estructurar la aplicación en capas, separar la lógica de negocio de la interfaz y facilitar el mantenimiento y la evolución del proyecto.

## 3. Instalación y ejecución

Sigue estos pasos para abrir y ejecutar el proyecto en un entorno de desarrollo local.

1. **Extraer el proyecto:** descomprime el archivo ZIP en el directorio que prefieras.
2. **Abrir Android Studio:** selecciona `File > Open`.
3. **Importar el proyecto:** navega hasta la carpeta `cod/` y selecciona el archivo `build.gradle` situado en la raíz del proyecto.
4. **Sincronizar Gradle:** espera a que finalice la sincronización y se descarguen las dependencias necesarias. Se requiere conexión a Internet durante este proceso.
5. **Seleccionar un dispositivo:** configura un emulador Android o conecta un dispositivo físico compatible.
6. **Ejecutar la aplicación:** pulsa el botón `Run` de Android Studio para compilar e iniciar Studyando.

## 4. Configuración del entorno de compilación

Para garantizar la compatibilidad con las herramientas y dependencias utilizadas, se recomienda disponer de la siguiente configuración:

- **IDE:** Android Studio Ladybug o una versión posterior.
- **JDK:** JetBrains Runtime 21 (`jbr-21`).
- **Android Gradle Plugin:** 8.7.2.
- **Kotlin:** 2.0.21.
- **Compose Compiler:** Compose Compiler Gradle Plugin, compatible con Kotlin 2.0 o superior.
- **SDK de compilación:** API 35.

Es importante comprobar que Android Studio utiliza el JDK adecuado para Gradle antes de iniciar la compilación.

## 5. Pruebas unitarias

El proyecto incorpora pruebas unitarias en el directorio `app/src/test`, destinadas a comprobar la lógica de negocio de forma aislada y sin depender directamente del framework de Android.

Actualmente, se incluyen las siguientes pruebas:

- **`PomodoroLogicTest`:** verifica el funcionamiento de los cálculos del temporizador, el formateo del tiempo y la determinación del progreso.
- **`TaskModelTest`:** comprueba la integridad de los datos del modelo de tareas y el comportamiento relacionado con la asignación de prioridades.

### Cómo ejecutar las pruebas

1. Abre el proyecto en Android Studio.
2. Localiza el directorio de pruebas `app/src/test`.
3. Haz clic derecho sobre la carpeta de pruebas o sobre la clase que quieras ejecutar.
4. Selecciona la opción `Run 'Tests in com.studyando'` o la correspondiente a la clase seleccionada.

Los resultados permitirán comprobar si la lógica evaluada funciona según lo esperado.

## 6. Solución de problemas

### Errores de compilación relacionados con Kapt y Java

Si aparecen errores durante la compilación relacionados con Kapt o con la compatibilidad entre Java y DataBinding, comprueba lo siguiente:

- Que el proyecto utiliza el JDK configurado para la compilación.
- Que las versiones de Kotlin, Gradle y Android Gradle Plugin son compatibles entre sí.
- Que la sincronización de Gradle ha finalizado correctamente.
- Que las dependencias necesarias están disponibles y se han descargado correctamente.

Para resolver problemas específicos de compatibilidad con DataBinding, el proyecto incluye dependencias de Glassfish JAXB en la configuración de Gradle.

Si el error persiste, revisa los mensajes de compilación de Android Studio para identificar la dependencia o configuración que está causando el problema.

---

## Licencia

Este proyecto se ha desarrollado con fines académicos como parte del Proyecto Final del Ciclo Formativo de Grado Superior en Desarrollo de Aplicaciones Multiplataforma (DAM), durante el curso 2025-2026.
