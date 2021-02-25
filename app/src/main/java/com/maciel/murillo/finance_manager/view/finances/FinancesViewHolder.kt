package com.maciel.murillo.finance_manager.view.finances

import android.view.View
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.databinding.ViewFinanceBinding
import com.maciel.murillo.finance_manager.extensions.toStringValue
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.MovementType

class FinancesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private lateinit var binding: ViewFinanceBinding

    fun bind(financialMovement: FinancialMovement) = with(itemView) {

        binding.tvTitle.text = financialMovement.description
        binding.tvCategory.text = financialMovement.category

        if (financialMovement.type == MovementType.RECIPE.toStringValue()) {
            binding.tvValue.text = financialMovement.value.toString()
            binding.tvValue.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccentReceita))
        } else {
            binding.tvValue.text = "-${financialMovement.value}"
            binding.tvValue.setTextColor(ContextCompat.getColor(itemView.context, R.color.colorAccent))
        }
    }
}