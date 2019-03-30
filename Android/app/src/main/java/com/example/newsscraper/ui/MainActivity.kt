package com.example.newsscraper.ui

import android.os.AsyncTask
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.newsscraper.R
import com.example.newsscraper.service.Tcp
import kotlinx.android.synthetic.main.activity_main.*

fun String.remove(): String {
    return this.replace("null", "")
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupButton()
    }

    private fun setupButton() {
        getDataTextView.setOnClickListener {
            entryTextView.text = "pobieranie danych..."
            ConnectTask(tagEditText.text.toString()) { message -> entryTextView.text = message.remove() }.execute()
        }
    }
}

class ConnectTask(private val tag: String, private val messageReceivedListener: (String) -> Unit) :
    AsyncTask<String, String, Tcp>() {
    var text = ""
    var tcp: Tcp? = null
    override fun doInBackground(vararg message: String): Tcp {
        tcp = Tcp(object : Tcp.OnMessageReceived {
            override fun messageReceived(message: String?) {
                publishProgress(message)
            }
        })
        tcp?.run(tag)
        return tcp!!
    }

    override fun onProgressUpdate(vararg values: String) {
        super.onProgressUpdate(values.toString())
        values.forEach { t -> text += t }
        messageReceivedListener.invoke(text)
        tcp?.stopClient()
    }
}
