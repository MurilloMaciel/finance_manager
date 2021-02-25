package com.maciel.murillo.finance_manager.extensions

import com.maciel.murillo.finance_manager.model.entity.MovementType

fun MovementType.toStringValue() = when (this) {
    MovementType.RECIPE -> "R"
    MovementType.EXPENSE -> "D"
}

fun String.toMovementType() = when (this) {
    "R" -> MovementType.RECIPE
    "D" -> MovementType.EXPENSE
    else -> null
}