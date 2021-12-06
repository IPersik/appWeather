package com.example.weather.experiments.services

import android.content.Context
import android.content.Intent
import androidx.core.app.JobIntentService

class ServiceWithThread : JobIntentService() {
    override fun onHandleWork(intent: Intent) {
        //ФОНОВЫЙ ПОТОК!
        println("JOB SERVICE WORK IN THREAD")
        sendMyBroadcast()
    }

    private fun sendMyBroadcast() {
        val broadcastIntent = Intent()
        broadcastIntent.putExtra(INTENT_SERVICE_DATA, true)
        broadcastIntent.action = INTENT_ACTION_KEY
        sendBroadcast(broadcastIntent)
    }

    companion object {
        const val INTENT_ACTION_KEY = "com.example.a2kotlinwithmvvm.SERVICE_FINISHED_EVENT"
        const val INTENT_SERVICE_DATA = "INTENT_SERVICE_DATA"

        fun start(context: Context) {
            val intent = Intent(context, ServiceWithThread::class.java)
            enqueueWork(context, ServiceWithThread::class.java, 3322, intent)
        }
    }
}