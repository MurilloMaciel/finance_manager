package com.maciel.murillo.finance_manager.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onStart() {
        super.onStart()

        handleStartDestination()
    }

    private fun handleStartDestination() {
        val navController = binding.fcvContainer.findNavController()
        val navGraph =  navController.navInflater.inflate(R.navigation.nav_graph)

        intent?.takeIf { it.hasExtra(EXTRA_DESTINATION) }?.run {
            navController.graph = navGraph.apply {
                startDestination = when (getStringExtra(EXTRA_DESTINATION)) {
                    DESTINATION_LOGIN -> R.id.frag_login
                    DESTINATION_SIGNUP -> R.id.frag_signup
                    else -> R.id.frag_finances
                }
            }
        }
    }

    companion object {
        private const val EXTRA_DESTINATION = "EXTRA_DESTINATION"
        const val DESTINATION_LOGIN = "DESTINATION_LOGIN"
        const val DESTINATION_FINANCES = "DESTINATION_FINANCES"
        const val DESTINATION_SIGNUP = "DESTINATION_SIGNUP"

        fun start(activity: AppCompatActivity, destination: String) {
            val intent = Intent(activity, MainActivity::class.java).apply {
                putExtra(EXTRA_DESTINATION, destination)
            }
            activity.startActivity(intent)
            activity.finish()
        }
    }
}