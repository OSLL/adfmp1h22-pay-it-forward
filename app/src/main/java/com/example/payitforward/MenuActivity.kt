package com.example.payitforward

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.example.payitforward.databinding.ActivityMenuBinding
import com.example.payitforward.pojo.Task
import com.example.payitforward.util.FirestoreUtil
import com.example.payitforward.util.StorageUtil
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.ArrayList

class MenuActivity : AppCompatActivity() {

    private var auth: FirebaseAuth = Firebase.auth

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMenuBinding
    var tasksList: List<Task> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMenuBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.appBarMenu.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)

        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_chat, R.id.nav_statistics
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        updateUI()

        startService(Intent(this, MessageNotificationsService::class.java))
    }

    override fun onRestart() {
        super.onRestart()
        updateUI()
    }

    override fun onResume() {
        super.onResume()
        updateUI()
    }

    private fun updateUI() {
        val navView: NavigationView = binding.navView
        val headerView: View = navView.getHeaderView(0)
        val navUsername: TextView = headerView.findViewById(R.id.user_name_header)
        val navBalance: TextView = headerView.findViewById(R.id.num_of_coins)
        val navPhoto: ImageView = headerView.findViewById(R.id.nav_image)

        val user = auth.currentUser
        if (user != null) {
            FirestoreUtil.getUser(user.uid) { user ->
                if (user != null) {
                    if (user.name.isEmpty()) {
                        navUsername.text = user.username
                    } else {
                        navUsername.text = user.name
                    }
                    if (user.photo != null && !user.photo.isEmpty()) {
                        GlideApp.with(this).load(StorageUtil.pathToReference(user.photo)).into(navPhoto)
                    }
                }
            }
        }

        FirestoreUtil.getCompletedTasks { tasks ->
            tasksList = tasks
            val a = tasksList.sumOf { it.coins }
            navBalance.text = a.toString()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}