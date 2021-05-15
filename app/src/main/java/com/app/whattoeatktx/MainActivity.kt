package com.app.whattoeatktx

import android.content.Intent
import android.os.Bundle
import com.app.whattoeatktx.service.UserService
import com.app.whattoeatktx.ui.auth.LoginRegisterActivity

class MainActivity : MyBaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (UserService().isNotLoggedIn()) {
            intent = Intent(this, LoginRegisterActivity::class.java)
            startActivity(intent)
            return
        }

        super.goToMain()
    }


}