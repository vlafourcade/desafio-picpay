package com.picpay.desafio.android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import com.picpay.desafio.android.user.presentation.ui.activity.UserActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onResume() {
        super.onResume()

        startActivity(
            Intent(
                this, UserActivity::class.java
            )
        )

        finish()
    }
}
