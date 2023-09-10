package com.contacts.people.presentation.ui.activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * Splash screen
 */
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Intent(this, MainActivity::class.java).apply {
            startActivity(this)
            finish()
        }
    }
}