package com.revolut.rates.ui.rate

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import com.revolut.rates.R
import com.revolut.rates.databinding.RateItemBinding
import com.revolut.rates.model.Model
import java.util.*
import kotlin.collections.ArrayList

class RateListAdapter : RecyclerView.Adapter<RateListAdapter.ViewHolder>() {

    private var ratePairList: ArrayList<Pair<String, String>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RateListAdapter.ViewHolder {
        val binding: RateItemBinding =
            DataBindingUtil.inflate(
                LayoutInflater.from(parent.context),
                R.layout.rate_item,
                parent,
                false
            )
        val viewHolder = ViewHolder(binding)

        return viewHolder
    }

    override fun onBindViewHolder(holder: RateListAdapter.ViewHolder, position: Int) {
        holder.bind(ratePairList[position])

        holder.itemView.setOnClickListener {
            Model.currency = ratePairList[position].first
            Model.amount = ratePairList[position].second
            if (position != 0) {
                holder.itemView
                Collections.swap(ratePairList, position, 0)
                notifyItemMoved(position, 0)
                ((holder.itemView.parent) as RecyclerView).smoothScrollToPosition(0)
                notifyDataSetChanged()
            }
        }

        val amountText = holder.itemView.findViewById<EditText>(R.id.amount)
        amountText.isEnabled = position == 0

        amountText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (holder.itemView.findViewById<TextView>(R.id.title).text == Model.currency) {
                    if (s.toString().isEmpty() || s.toString() == "")
                        amountText.setText("0")
                    Model.amount = amountText.text.toString()
                }
            }
        })
    }

    override fun getItemCount(): Int {
        return ratePairList.size
    }

    fun updateRateList(rateList: Map<String, Double>) {
        var newRatePairList: ArrayList<Pair<String, String>> = ArrayList()
        newRatePairList.add(Pair(Model.currency, Model.amount))
        for ((k, v) in rateList) {
            newRatePairList.add(Pair(k, fixAmount(k, v)))
        }
        if (ratePairList.size != newRatePairList.size) {
            ratePairList = newRatePairList
            notifyDataSetChanged()
        } else {
            for (i in newRatePairList.indices) {
                if (ratePairList[i] != newRatePairList[i]) {
                    ratePairList[i] = newRatePairList[i]
                    notifyItemChanged(i)
                }
//                else if (newRatePairList[i].first == Model.currency)
//                    ratePairList[i] = Pair(Model.currency,Model.amount)
            }
        }
    }

    private fun fixAmount(k: String, v: Double): String {
        var amountReturn: String = v.toString()
        if (k != Model.currency) {
            amountReturn =
                String.format(
                    "%.2f", (v * Model.amount.toDouble())
                )
            if (amountReturn.substringAfter(".").toInt() == 0) {
                amountReturn = amountReturn.substringBefore(".")
            }
        } else {
            if (amountReturn.substringAfter(".").toInt() == 0) {
                amountReturn = amountReturn.substringBefore(".")
            }
        }
        return amountReturn
    }

    class ViewHolder(private val binding: RateItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private val viewModel = RateViewModel()
        fun bind(rate: Pair<String, String>) {
            viewModel.bind(rate)
            binding.viewModel = viewModel
        }
    }
}
