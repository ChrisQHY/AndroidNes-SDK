package com.akatsuki.akatsukines

import android.view.KeyEvent
import com.google.android.material.snackbar.Snackbar
import com.ym.library.base.BaseEmulatorActivity
import com.ym.library.sdk.EmulatorManager

class ActivityGame:BaseEmulatorActivity() {
    private val keyMap = mapOf(
        KeyEvent.KEYCODE_DPAD_LEFT to EmulatorManager.ControllerKey.LEFT,
        KeyEvent.KEYCODE_DPAD_RIGHT to EmulatorManager.ControllerKey.RIGHT,
        KeyEvent.KEYCODE_DPAD_UP to EmulatorManager.ControllerKey.UP,
        KeyEvent.KEYCODE_DPAD_DOWN to EmulatorManager.ControllerKey.DOWN,
        KeyEvent.KEYCODE_BUTTON_SELECT to EmulatorManager.ControllerKey.SELECT,
        KeyEvent.KEYCODE_BUTTON_START to EmulatorManager.ControllerKey.START,
        KeyEvent.KEYCODE_BUTTON_A to EmulatorManager.ControllerKey.A,
        KeyEvent.KEYCODE_BUTTON_B to EmulatorManager.ControllerKey.B,
        KeyEvent.KEYCODE_BUTTON_X to EmulatorManager.ControllerKey.A_TURBO,
        KeyEvent.KEYCODE_BUTTON_Y to EmulatorManager.ControllerKey.B_TURBO,
        KeyEvent.KEYCODE_BUTTON_L1 to null,
        KeyEvent.KEYCODE_BUTTON_R1 to null,
    )
    private var descMainRecode = false
    private var descSubRecode = false
    private var descMain = ""
    private var descSub = ""

    override fun getLayoutResId() = R.layout.game

    override fun dispatchKeyEvent(event:KeyEvent):Boolean {
        if(keyMap.keys.contains(event.keyCode)) {
            if(!descMainRecode) {
                Snackbar.make(window.decorView, "Player1: ${event.device.descriptor}", Snackbar.LENGTH_SHORT).show()
                descMain = event.device.descriptor
                descMainRecode = true
            } else if(!descSubRecode && descMain != event.device.descriptor) {
                Snackbar.make(window.decorView, "Player2: ${event.device.descriptor}", Snackbar.LENGTH_SHORT).show()
                descSub = event.device.descriptor
                descSubRecode = true
            }
            keyHandler(event)
        }

        return true
    }

    private fun checkPlayer(e:KeyEvent) = when {
        descMain == e.device.descriptor -> EmulatorManager.Player.PLAYER1
        descSub == e.device.descriptor -> EmulatorManager.Player.PLAYER2
        else -> null
    }

    private fun keyHandler(e:KeyEvent) {
        if(e.action == KeyEvent.ACTION_DOWN) {
            keyMap[e.keyCode]?.let {key ->
                checkPlayer(e)?.let {player ->
                    EmulatorManager.getInstance().pressKey(player, key)
                }
            } ?: run {
                finish()
            }
        } else if(e.action == KeyEvent.ACTION_UP) {
            keyMap[e.keyCode]?.let {key ->
                checkPlayer(e)?.let {player ->
                    EmulatorManager.getInstance().unPressKey(player, key)
                }
            }
        }
    }
}