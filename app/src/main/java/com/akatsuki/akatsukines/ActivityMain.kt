package com.akatsuki.akatsukines

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.ym.library.sdk.EmulatorManager
import java.io.File

class ActivityMain:AppCompatActivity() {
    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val romFile = File("${filesDir.path}/${getString(R.string.rom_name)}")
        EmulatorManager.getInstance().startGame(this, ActivityGame::class.java, romFile)
        finish()
    }
}