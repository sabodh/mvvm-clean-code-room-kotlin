package com.virgin.money.ui
import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.virgin.money.R


class MainActivity : AppCompatActivity() {
    private val TAG_COLLECTION = "COLLECTION"
    private val TAG_DETAILS = "DETAILS"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onBackPressedDispatcher.addCallback(this, object :
            OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (supportFragmentManager.backStackEntryCount > 1) {
                    supportFragmentManager.popBackStack()
                } else {
                    finish()
                }
            }
        })
        setContentView(R.layout.activity_main_pager)
        setupCollectionFragments()

    }

     fun showSnackbar(message: String, view: View) {
            Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()

    }
    private fun setupCollectionFragments(){
            val fragment = CollectionFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.frame_for_add, fragment, TAG_COLLECTION)
                .addToBackStack(null)
                .commit()
    }
    fun setupDetailsFragments(){
            val fragment = PeopleDetailsFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.frame_for_add, fragment, TAG_DETAILS)
                .addToBackStack(null)
                .commit()
    }
}