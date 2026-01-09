package com.studyando

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.studyando.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var appBarConfig: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        // 1. Instalar Splash Screen
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        // 2. Inflar el binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Lógica del Splash Screen (800ms)
        var keepSplashScreen = true
        splashScreen.setKeepOnScreenCondition { keepSplashScreen }

        binding.root.postDelayed({
            keepSplashScreen = false
        }, 800L) // Tiempo reducido para mayor rapidez

        // 4. Configurar la Toolbar para la Idea 1 (Logo Izquierda)
        setSupportActionBar(binding.topAppBar)
        // Ocultamos el título por defecto para que luzca el diseño del XML (Gorro + Studyando)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        // 5. Animación de salida del Splash (Suavizado)
        splashScreen.setOnExitAnimationListener { splashScreenView ->
            val alpha = ObjectAnimator.ofFloat(splashScreenView.view, View.ALPHA, 1f, 0f)
            alpha.duration = 400L
            alpha.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    splashScreenView.remove()
                }
            })
            alpha.start()
        }

        // 6. Configuración de Navegación
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController

        // Definimos las pantallas principales que no llevan botón de "atrás"
        appBarConfig = AppBarConfiguration(
            setOf(R.id.tasksFragment, R.id.pomodoroFragment, R.id.scheduleFragment)
        )

        // Unimos la Toolbar y el BottomNav con el controlador de navegación
        setupActionBarWithNavController(navController, appBarConfig)
        binding.bottomNav.setupWithNavController(navController)

        // IMPORTANTE: Volver a ocultar el título después de configurar la navegación
        // porque setupActionBarWithNavController a veces lo vuelve a activar.
        navController.addOnDestinationChangedListener { _, _, _ ->
            supportActionBar?.setDisplayShowTitleEnabled(false)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHost = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHost.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}