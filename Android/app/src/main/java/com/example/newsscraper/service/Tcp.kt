package com.example.newsscraper.service

import android.util.Log
import java.io.*
import java.net.InetAddress
import java.net.Socket

class Tcp(listener: OnMessageReceived) {
    private var messageFromServer: String? = null
    private var messageListener: OnMessageReceived? = null
    private var isRunning = false
    private var bufferOut: PrintWriter? = null
    private var bufferIn: BufferedReader? = null

    init {
        messageListener = listener
    }

    fun sendMessage(message: String) {
        Thread(Runnable {
            bufferOut?.let {
                it.println(message)
                it.flush()
            }
        }).start()
    }

    fun stopClient() {
        isRunning = false
        bufferOut?.let {
            it.flush()
            it.close()
        }
        messageListener = null
        bufferIn = null
        bufferOut = null
        messageFromServer = null
    }

    fun run(tag: String) {
        isRunning = true
        try {
            val serverAddress = InetAddress.getByName(SERVER_IP)
            val socket = Socket(serverAddress, SERVER_PORT)
            try {
                bufferOut = PrintWriter(BufferedWriter(OutputStreamWriter(socket.getOutputStream())), true)
                bufferIn = BufferedReader(InputStreamReader(socket.getInputStream()))
                sendMessage(tag.toLowerCase())
                while (isRunning) {
                    messageFromServer = bufferIn?.readLine()
                    messageListener?.messageReceived(messageFromServer)
                }
            } catch (e: Exception) {
                Log.e("Tcp", "S: Error", e)
            } finally {
                socket.close()
            }
        } catch (e: Exception) {
            Log.e("Tcp", "C: Error", e)
        }
    }

    interface OnMessageReceived {
        fun messageReceived(message: String?)
    }

    companion object {
        val TAG = Tcp::class.java.simpleName
        val SERVER_IP = "192.168.43.82"
        val SERVER_PORT = 2048
    }

}