package com.example.weather.framework.receivers.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weather.model.AppState
import com.example.weather.R
import com.example.weather.databinding.FragmentHistiryBinding
import com.example.weather.experiments.showSnackBar
import com.example.weather.framework.receivers.ui.adapters.HistoryAdapter
import org.koin.android.ext.android.inject

class HistoryFragment : Fragment() {
    private var _binding : FragmentHistiryBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HistoryViewModel by inject()
    private val adapter: HistoryAdapter by lazy { HistoryAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistiryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) = with(binding) {
        super.onViewCreated(view, savedInstanceState)
        historyFragmentRecyclerview.adapter = adapter
        viewModel.historyLiveData.observe(viewLifecycleOwner, { renderData(it) })
        viewModel.getAllHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun renderData(appState: AppState) = with(binding) {
        when (appState) {
            is AppState.Success -> {
                historyFragmentRecyclerview.visibility = View.VISIBLE
                progressBar.visibility = View.GONE
                adapter.setData(appState.weatherData)
            }
            is AppState.Loading -> {
                historyFragmentRecyclerview.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                progressBar.visibility = View.GONE
                historyFragmentRecyclerview.showSnackBar(
                    getString(R.string.error),
                    getString(R.string.reload)
                )
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = HistoryFragment()
    }
}