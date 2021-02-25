package com.maciel.murillo.finance_manager.model.service

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.maciel.murillo.finance_manager.extensions.safe
import com.maciel.murillo.finance_manager.extensions.toBase64
import com.maciel.murillo.finance_manager.extensions.toChoosenDate
import com.maciel.murillo.finance_manager.extensions.toMovementType
import com.maciel.murillo.finance_manager.model.entity.User
import com.maciel.murillo.finance_manager.model.entity.FinancialMovement
import com.maciel.murillo.finance_manager.model.entity.MovementType
import com.maciel.murillo.finance_manager.model.service.DbConstants.FIREBASE_MOVEMENT
import com.maciel.murillo.finance_manager.model.service.DbConstants.FIREBASE_USERS
import com.maciel.murillo.finance_manager.model.service.DbConstants.FIREBASE_USERS_TOTAL_EXPENSES
import com.maciel.murillo.finance_manager.model.service.DbConstants.FIREBASE_USERS_TOTAL_RECIPES
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class DbServiceImpl @Inject constructor(
    private val dbRef: DatabaseReference,
    private val authService: AuthService
) : DbService {

//    private val dbRef = FirebaseDatabase.getInstance().reference

    override suspend fun deleteMovement(movement: FinancialMovement) {
        dbRef.child(FIREBASE_MOVEMENT)
            .child(authService.getCurrentUserId())
            .child(movement.date)
            .child(movement.key)
            .removeValue()
            .await()
    }

    override suspend fun saveUser(user: User) {
        dbRef.child(FIREBASE_USERS)
            .child(user.email.toBase64())
            .setValue(user)
            .await()
    }

    override suspend fun saveMovement(movement: FinancialMovement) {
        dbRef.child(FIREBASE_MOVEMENT)
            .child(authService.getCurrentUserId())
            .child(movement.date.toChoosenDate())
            .push()
            .setValue(movement)
            .await()
    }

    override suspend fun getCurrentUser(): User? {
        val snapshot = dbRef.child(FIREBASE_USERS)
            .child(authService.getCurrentUserId())
            .get()
            .await()
        return snapshot.getValue(User::class.java)
    }

    override suspend fun getAllMovements(date: String): List<FinancialMovement> {
        val snapshot = dbRef.child(FIREBASE_MOVEMENT)
            .child(authService.getCurrentUserId())
            .child(date)
            .get()
            .await()
        val movements = mutableListOf<FinancialMovement>()
        for (data in snapshot.children) {
            if (data.exists()) {
                movements.add(data.getValue(FinancialMovement::class.java)!!.apply { this.key = data.key.safe() })
            }
        }
        return movements
    }

    override suspend fun getTotalExpenses(): Double {
        val snapshot = dbRef.child(FIREBASE_USERS)
            .child(authService.getCurrentUserId())
            .get()
            .await()
        return snapshot.getValue(User::class.java)?.totalExpense ?: 0.0
    }

    override suspend fun getTotalRecipes(): Double {
        val snapshot = dbRef.child(FIREBASE_USERS)
            .child(authService.getCurrentUserId())
            .get()
            .await()
        return snapshot.getValue(User::class.java)?.totalRecipe ?: 0.0
    }

    override suspend fun updateBalance(value: Double, movement: FinancialMovement) {
        if (movement.type.toMovementType() == MovementType.EXPENSE) {
            updateBalanceWithExpense(value)
        } else if (movement.type.toMovementType() == MovementType.RECIPE) {
            updateBalanceWithRecipe(value)
        }
    }

    private suspend fun updateBalanceWithRecipe(value: Double) {
        dbRef.child(FIREBASE_USERS)
            .child(authService.getCurrentUserId())
            .child(FIREBASE_USERS_TOTAL_RECIPES)
            .setValue(value)
            .await()
    }

    private suspend fun updateBalanceWithExpense(value: Double) {
        dbRef.child(FIREBASE_USERS)
            .child(authService.getCurrentUserId())
            .child(FIREBASE_USERS_TOTAL_EXPENSES)
            .setValue(value)
            .await()
    }

}