package com.example.currencyconverterapp.presentation.fragments.adapters

import android.graphics.Color
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.example.currencyconverterapp.R
import com.example.currencyconverterapp.databinding.RvCurrencyItemBinding
import com.example.currencyconverterapp.domain.model.Currency
import com.example.currencyconverterapp.domain.useCases.SetNumberIntoPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class CurrencyAdapter @Inject constructor(
    private var glide: RequestManager,
    private var setNumber: SetNumberIntoPreferences,
) : ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback),
    View.OnClickListener {
    private var listener: AdaptersListener? = null

    fun setOnClickListener(onClickListener: AdaptersListener) {
        this.listener = onClickListener
    }

    override fun onClick(v: View) {
        val currency = v.tag as Currency

        CoroutineScope(Dispatchers.IO).launch {
            setNumber.invoke(1.0)
        }

        listener?.onClickItem(currency)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = RvCurrencyItemBinding.inflate(layoutInflater, parent, false)

        binding.cardItem.setOnClickListener(this)

        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    private object CurrencyDiffCallback : DiffUtil.ItemCallback<Currency>() {

        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }

    inner class CurrencyViewHolder(
        private val binding: RvCurrencyItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(currency: Currency?) {
            val image = currency?.img

            glide.load(image)
                .error(R.drawable.ic_baseline_broken_image_24)
                .fitCenter()
                .into(binding.ivCurrencyImage)

            binding.apply {
                cardItem.tag = currency

                currency?.let { info ->
                    tvCurrencyShortName.text = info.base
                    tvCurrencyName.text = info.name
                    etCurrencyValue.setText(String.format("%.5f", (info.value)))
                    if (info.isSelected) {
                        etCurrencyValue.setSelectAllOnFocus(true)
                        etCurrencyValue.isEnabled = true
                        etCurrencyValue.isCursorVisible = true

                        etCurrencyValue.setOnKeyListener(object : View.OnKeyListener {
                            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
                                if (
                                    event.action == KeyEvent.ACTION_DOWN &&
                                    keyCode == KeyEvent.KEYCODE_ENTER
                                ) {
                                    CoroutineScope(Dispatchers.IO).launch {
                                        setNumber.invoke(etCurrencyValue.text.toString().toDouble())
                                    }

                                    etCurrencyValue.clearFocus()
                                    etCurrencyValue.isCursorVisible = false

                                    return true
                                }
                                return false
                            }
                        })
                    } else {
                        etCurrencyValue.isEnabled = false
                        etCurrencyValue.isCursorVisible = false
                        etCurrencyValue.setBackgroundColor(/* color = */ Color.TRANSPARENT)
                    }
                }
            }
        }

    }
}