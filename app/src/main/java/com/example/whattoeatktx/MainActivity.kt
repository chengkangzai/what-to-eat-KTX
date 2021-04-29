package com.example.whattoeatktx

import android.content.Intent
import android.os.Bundle
import com.example.whattoeatktx.service.UserService
import com.example.whattoeatktx.ui.auth.LoginRegisterActivity

class MainActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        super.goToMain()

        if (UserService().isNotLoggedIn()) {
            intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

    }


}