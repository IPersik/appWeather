package com.example.weather.framework.receivers.ui.treads_fragment

import android.content.*
import android.os.*
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.example.weather.R
import com.example.weather.databinding.FragmentThreadsBinding
import com.example.weather.experiments.services.ServiceWithThread
import com.example.weather.services.BoundService
import com.example.weather.services.MyForegroundService
import kotlinx.coroutines.*
import java.util.*
import java.util.concurrent.TimeUnit

class ThreadsFragment : Fragment(), CoroutineScope by MainScope() {
    private var _binding: FragmentThreadsBinding? = null
    private val binding get() = _binding!!

    private var isBound = false
    private var boundService: BoundService.ServiceBinder? = null

    private val testReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            Log.i("SERVICE", "FROM SERVICE:" +
                    " ${intent?.getBooleanExtra(MyForegroundService.INTENT_SERVICE_DATA, false)}")
        }
    }

    // Обработка соединения с сервисом
    private val boundServiceConnection: ServiceConnection = object : ServiceConnection {
        // При соединении с сервисом
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            boundService = service as BoundService.ServiceBinder
            isBound = boundService != null
            Log.i("SERVICE", "BOUND SERVICE")
            Log.i("SERVICE", "next fibonacci: ${service.nextFibonacci}")
        }

        // При разрыве соединения с сервисом
        override fun onServiceDisconnected(name: ComponentName) {
            isBound = false
            boundService = null
        }
    }

    override fun onStart() {
        super.onStart()
        if(!isBound) {
            val bindServiceIntent = Intent(context, BoundService::class.java)
            activity?.bindService(bindServiceIntent, boundServiceConnection, Context.BIND_AUTO_CREATE)
        }
        activity?.registerReceiver(testReceiver, IntentFilter(MyForegroundService.INTENT_ACTION_KEY))
    }

    override fun onStop() {
        activity?.unregisterReceiver(testReceiver)
        if(isBound) {
            activity?.unbindService(boundServiceConnection)
        }
        super.onStop()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentThreadsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?)  {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            button.setOnClickListener {
                launch {
                    val resultJob = async(Dispatchers.Default) {
                        startCalculations(editText.text.toString().toInt())
                    }
                    textView.text = resultJob.await()
                    mainContainer.addView(AppCompatTextView(it.context).apply {
                        text = getString(R.string.in_main_thread)
                        textSize = resources.getDimension(R.dimen.main_container_text_size)
                    })
                }
            }
        }

        val handlerThread = HandlerThread("some thread")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)
        handler.post {
            //...
        }
        handlerThread.quitSafely()
        /*handler.postDelayed({
            //...
        }, 500)*/

        MyForegroundService.start(requireContext())
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }

    private fun startCalculations(seconds: Int): String {
        val date = Date()
        var diffInSec: Long

        do {
            val currentDate = Date()
            val diffInMs: Long = currentDate.time - date.time
            diffInSec = TimeUnit.MILLISECONDS.toSeconds(diffInMs)
        } while (diffInSec < seconds)

        return diffInSec.toString()
    }

    companion object {
        @JvmStatic
        fun newInstance() = ThreadsFragment()
    }
}