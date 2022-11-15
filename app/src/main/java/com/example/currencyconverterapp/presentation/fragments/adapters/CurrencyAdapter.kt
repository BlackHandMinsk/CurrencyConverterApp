package com.example.currencyconverterapp.presentation.fragments.adapters

import android.util.Log
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
import javax.inject.Inject

class CurrencyAdapter @Inject constructor(
    private val glide: RequestManager
) : ListAdapter<Currency, CurrencyAdapter.CurrencyViewHolder>(CurrencyDiffCallback),
    View.OnClickListener {
    private var listener: AdaptersListener? = null
    private var mRate: Double = 1.0

    fun setOnClickListener(onClickListener: AdaptersListener) {
        this.listener = onClickListener
    }

    override fun onClick(v: View) {
        val currency = v.tag as Currency
//        val num = v.tag as Double
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
                    tvCurrencyShortName.text = info.name
                    etCurrencyValue.setText(String.format("%.5f", (info.value)))
                    if (info.isSelected){
                        val qwert = etCurrencyValue.text.toString()
                        Log.w("LOG", qwert)
                    }
                }
            }
        }
    }
}