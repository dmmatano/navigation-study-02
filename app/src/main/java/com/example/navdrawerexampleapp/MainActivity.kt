package com.example.navdrawerexampleapp

import android.os.Bundle
import android.view.Menu
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import com.example.navdrawerexampleapp.databinding.ActivityMainBinding
import kotlin.properties.Delegates

/**
 * Navigation using drawer and toolbar
 */
class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private var counter = 0
    private var showToast = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // como mencionado acima, deve-se fazer isso quando cada destino utilizado no menu não têm relação
        // hierarquica entre si. Exemplo: bottomNavigationView
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow
            ), drawerLayout
        )
        // podemos usar também:
        // appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //obs: se houverem toolbars diferentes em alguns fragments, devemos chamar o
        //setubWithNavController() no onViewCreated() de cada fragmt, e excluir o método
        //da activity

        navigationObserver(navController)
    }

    private fun navigationObserver(navController: NavController) {
        //o metodo abaixo add outro listener para poder observar eventos de navegação
        navController.addOnDestinationChangedListener{_,destination,arguments ->
            when(destination.id){
                R.id.nav_home -> Toast.makeText(this, "Nav destination is nav_home", Toast.LENGTH_SHORT).show()
                R.id.nav_gallery -> Toast.makeText(this, "Nav destination is nav_gallery", Toast.LENGTH_SHORT).show()
                R.id.nav_slideshow -> {
                    showToast = if(counter%2 == 0) {
                        arguments?.getBoolean("showToast")?:true
                    }else{
                        false
                    }
                    counter++
                    if(counter>100) counter = 0
                    if(showToast) Toast.makeText(this, "Nav destination is nav_slideshow", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    //necessário para gerenciar ações de "voltar" usando actionbar
    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}