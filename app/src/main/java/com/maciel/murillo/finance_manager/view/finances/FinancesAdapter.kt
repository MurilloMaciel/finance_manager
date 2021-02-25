package com.maciel.murillo.finance_manager.view.finances

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.maciel.murillo.finance_manager.R
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement

class FinancesAdapter(
    private var financialMovements: List<FinancialMovement>
) : RecyclerView.Adapter<FinancesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FinancesViewHolder {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.view_finance, parent, false)
        return FinancesViewHolder(view)
    }

    override fun onBindViewHolder(holder: FinancesViewHolder, position: Int) {
        holder.bind(financialMovements[position])
    }

    override fun getItemCount() = financialMovements.size

    fun update(financialMovements: List<FinancialMovement>) {
        this.financialMovements = financialMovements
        notifyDataSetChanged()
    }
}