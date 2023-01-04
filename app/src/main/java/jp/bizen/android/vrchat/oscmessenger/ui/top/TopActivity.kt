package jp.bizen.android.vrchat.oscmessenger.ui.top

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import com.illposed.osc.OSCMessage
import com.illposed.osc.transport.udp.OSCPortOut
import jp.bizen.android.vrchat.oscmessenger.R
import jp.bizen.android.vrchat.oscmessenger.extension.isValidIPAddress
import jp.bizen.android.vrchat.oscmessenger.util.Pref
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.net.InetSocketAddress

class TopActivity : AppCompatActivity() {
    companion object {
        private const val TARGET_SEND_PORT = 9000
    }

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_top)
        val inputIpAddress = findViewById<EditText>(R.id.input_ip_address)
        inputIpAddress.setText(Pref(this).ipAddress)
        findViewById<RecyclerView>(R.id.list)?.also {
            it.adapter = OscMessageAdapter(object : OscMessageAdapter.Listener {
                override fun onClick(operation: String, value: Any?) {
                    val ipAddress = inputIpAddress.editableText.toString()
                    if (ipAddress.isValidIPAddress) {
                        Pref(this@TopActivity).ipAddress = ipAddress
                        lifecycleScope.launch(Dispatchers.IO) {
                            sendMessage(
                                message = operation,
                                value = value,
                                ipAddress = ipAddress
                            )
                        }
                    } else {
                        showToast("IPアドレスを正しく入力してください")
                    }
                }
            })
        }
    }

    private fun sendMessage(message: String, value: Any?, ipAddress: String) {
        try {
            val targetSocketSendAddress = InetSocketAddress(
                ipAddress,
                TARGET_SEND_PORT
            )
            OSCPortOut(targetSocketSendAddress).apply {
                if (value != null) {
                    send(OSCMessage(message, listOf(value)))
                } else {
                    send(OSCMessage(message))
                }
            }
        } catch (e: Exception) {
            println(e.localizedMessage)
            showToast("エラーが発生しました: ${e.localizedMessage}")
        }
    }

    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }
}