package com.example.currencyconverterapp.presentation.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.databinding.FragmentListOfCurrenciesBinding
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.util.Record
import com.example.currencyconverterapp.presentation.fragments.adapters.AdaptersListener
import com.example.currencyconverterapp.presentation.fragments.adapters.CurrencyAdapter
import com.example.currencyconverterapp.presentation.viewmodels.ListOfCurrenciesViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ListOfCurrenciesFragment : Fragment(), AdaptersListener {

    private var _binding: FragmentListOfCurrenciesBinding? = null
    private val binding get() = _binding!!

    private val viewModel: ListOfCurrenciesViewModel by viewModels()

    @Inject
    lateinit var adapter: CurrencyAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentListOfCurrenciesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter.setOnClickListener(this)

        binding.rvCurrencies.adapter = adapter

        lifecycleScope.launch {
            viewModel.response.collect {
                when (it) {
                    is Record.Success -> {
                        binding.progressBar.isVisible = false
                        adapter.submitList(it.data)
                    }
                    is Record.Error -> startToast(it.error.toString())
                    else -> {
                        binding.progressBar.isVisible = true
                    }
                }
            }
        }
    }

    private fun scrollToTop() {
        lifecycleScope.launch {
            delay(500)
            binding.rvCurrencies.scrollToPosition(0)
        }
    }

    private fun startToast(message: String) {
        Snackbar.make(
            binding.root,
            message,
            Snackbar.LENGTH_LONG
        ).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickItem(currency: Currency) {
        viewModel.setCounter(currency)
        adapter.notifyDataSetChanged()
        scrollToTop()
    }
}