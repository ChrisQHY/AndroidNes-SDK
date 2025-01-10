package com.akatsuki.akatsukines

import android.app.Application
import com.ym.library.sdk.EmulatorManager
import com.ym.library.sdk.EmulatorSDK
import java.io.File
import java.io.InputStream

class ApplicationNes:Application() {

    override fun onCreate() {
        super.onCreate()
        EmulatorSDK.init(this)
        EmulatorManager.getInstance()
            //是否展示界面控制器：否
            .set(EmulatorManager.Settings.SHOW_CONTROLLER, false)
            //模拟器画质：高
            .set(EmulatorManager.Settings.EMULATION_QUALITY, EmulatorManager.EmulationQuality.HIGH)
            //模拟器屏幕方向：水平
            .set(EmulatorManager.Settings.SCREEN_ORIENTATION, EmulatorManager.EmulatorScreenOrientation.HORIZONTAL)
            //是否强制模拟器画面全屏：否
            .set(EmulatorManager.Settings.FORCE_FULL_SCREEN, false)

        if(!File("${filesDir.path}/${getString(R.string.rom_name)}").exists()) {
            assets.list("nes")?.let {nesFiles ->
                for(nesFile in nesFiles) {
                    val inputStream:InputStream = assets.open("nes/$nesFile")
                    val outFile = File(filesDir, nesFile)
                    if(outFile.exists()) {
                        inputStream.close()
                        continue
                    }
                    inputStream.use {input ->
                        outFile.outputStream().use {output ->
                            input.copyTo(output)
                        }
                    }
                }
            }
        }
    }
}