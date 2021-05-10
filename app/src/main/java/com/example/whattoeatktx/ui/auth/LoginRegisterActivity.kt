package com.example.whattoeatktx.ui.auth

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.example.whattoeatktx.MyBaseActivity
import com.example.whattoeatktx.R
import com.example.whattoeatktx.service.UserService
import com.example.whattoeatktx.service.VersionService
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth


class LoginRegisterActivity : MyBaseActivity() {
    /**
     * https://www.javaer101.com/en/article/14424967.html
     * RC_SIGN in is the request code you will assign for starting the new activity. this can be any number
     */
    private val RC_SIGN_IN = 7
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_register)

        if (UserService().isLoggedIn()) {
            super.goToMain()
            finish()
        }

        findViewById<Button>(R.id.btnLogIn).setOnClickListener { handleLogin() }
        findViewById<Button>(R.id.btnAbout).setOnClickListener { showAbout() }

    }

    private fun handleLogin() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.GitHubBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build(),
            AuthUI.IdpConfig.AnonymousBuilder().build()
        )

        // Create and launch sign-in intent
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .setIsSmartLockEnabled(false)
                .setTheme(R.style.FirebaseUI)
                .build(),
            RC_SIGN_IN,
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
//                 Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                Toast.makeText(
                    this,
                    String.format(getString(R.string.welcome_login), user!!.displayName),
                    Toast.LENGTH_LONG
                ).show()

                super.goToMain()
            } else {
                Toast.makeText(this, getString(R.string.lol_sth_wrong), Toast.LENGTH_LONG).show()
                if (response != null) {
                    Log.e(getString(R.string.error), response.error.toString())
                }
            }
        }
    }


    private fun showAbout() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.about_this_app))
            .setPositiveButton(getString(R.string.OK)) { dialog, _ -> dialog.dismiss() }
            .setMessage(
                String.format(
                    resources.getString(R.string.about_string),
                    resources.getString(R.string.app_name),
                    VersionService().version,
                    resources.getString(R.string.author)
                )
            )
            .show()
    }

}