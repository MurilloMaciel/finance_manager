package com.maciel.murillo.finance_manager.model.entity

import com.google.firebase.database.Exclude

data class User(
    val name: String,
    val email: String,
    @Exclude val password: String,
    val totalRecipe: Double = 0.0,
    val totalExpense: Double = 0.0,
)