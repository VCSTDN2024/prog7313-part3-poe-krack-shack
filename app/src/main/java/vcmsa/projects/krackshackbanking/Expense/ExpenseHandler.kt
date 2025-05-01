package vcmsa.projects.krackshackbanking.Expense

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Date

class ExpenseHandler {
    private val expensesCollection = db.collection("expenses")
    private val auth: FirebaseAuth = Firebase.auth

    fun createExpense(expense: ExpenseModel, onComplete: (String?, String?) -> Unit) {
        if (!expense.isExpenseValid()) {
            onComplete(null, "Invalid expense data")
            return
        }

        val expenseRef = expensesCollection.document()
        val expenseWithId = expense.copy(id = expenseRef.id)

        expenseRef.set(expenseWithId.toMap())
            .addOnSuccessListener {
                if (expense.budgetId.isNotBlank()) {
                    budgetHandler.updateBudgetSpent(expense.budgetId, expense.amount) { error ->
                        if (error != null) {
                            onComplete(expenseRef.id, "Expense created but budget update failed: $error")
                        } else {
                            onComplete(expenseRef.id, null)
                        }
                    }
                } else {
                    onComplete(expenseRef.id, null)
                }
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    suspend fun getExpenses(): List<ExpenseModel> {
        val currentUser = auth.currentUser
            ?: throw IllegalStateException("User not authenticated")

        val snapshot = expensesCollection
            .whereEqualTo("UID", currentUser.uid)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            ExpenseModel(
                id = doc.id,
                amount = doc.getDouble("Amount") ?: 0.0,
                categoryID = doc.getString("CategoryID") ?: "",
                date = doc.getString("Date") ?: "",
                description = doc.getString("Description") ?: "",
                imageUrl = doc.getString("Image") ?: "",
                expenseID = doc.getString("expenseID") ?: "",
                budgetId = doc.getString("budgetId") ?: ""
            )
        }
    }

    fun updateExpense(expense: ExpenseModel, onComplete: (String?) -> Unit) {
        if (!expense.isExpenseValid()) {
            onComplete("Invalid expense data")
            return
        }

        expensesCollection.document(expense.id)
            .set(expense.toMap())
            .addOnSuccessListener {
                onComplete(null)
            }
            .addOnFailureListener { e ->
                onComplete(e.message)
            }
    }

    fun deleteExpense(expenseId: String, onComplete: (String?) -> Unit) {
        expensesCollection.document(expenseId)
            .delete()
            .addOnSuccessListener {
                onComplete(null)
            }
            .addOnFailureListener { e ->
                onComplete(e.message)
            }
    }

    fun getExpensesByUser(userId: String, onComplete: (List<ExpenseModel>?, String?) -> Unit) {
        expensesCollection
            .whereEqualTo("UID", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.map { doc ->
                    ExpenseModel(
                        id = doc.id,
                        amount = doc.getDouble("Amount") ?: 0.0,
                        categoryID = doc.getString("CategoryID") ?: "",
                        date = doc.getString("Date") ?: "",
                        description = doc.getString("Description") ?: "",
                        imageUrl = doc.getString("Image") ?: "",
                        UID = doc.getString("UID") ?: "",
                        expenseID = doc.getString("expenseID") ?: "",
                        budgetId = doc.getString("budgetId") ?: ""
                    )
                }
                onComplete(expenses, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun getExpensesByBudget(budgetId: String, onComplete: (List<ExpenseModel>?, String?) -> Unit) {
        expensesCollection
            .whereEqualTo("budgetId", budgetId)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.map { doc ->
                    ExpenseModel(
                        id = doc.id,
                        amount = doc.getDouble("Amount") ?: 0.0,
                        categoryID = doc.getString("CategoryID") ?: "",
                        date = doc.getString("Date") ?: "",
                        description = doc.getString("Description") ?: "",
                        imageUrl = doc.getString("Image") ?: "",
                        UID = doc.getString("UID") ?: "",
                        expenseID = doc.getString("expenseID") ?: "",
                        budgetId = doc.getString("budgetId") ?: ""
                    )
                }
                onComplete(expenses, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun getExpensesByDateRange(
        userId: String,
        startDate: Date,
        endDate: Date,
        onComplete: (List<ExpenseModel>?, String?) -> Unit
    ) {
        expensesCollection
            .whereEqualTo("UID", userId)
            .whereGreaterThanOrEqualTo("date", startDate.toString())
            .whereLessThanOrEqualTo("date", endDate.toString())
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.map { doc ->
                    ExpenseModel(
                        id = doc.id,
                        amount = doc.getDouble("Amount") ?: 0.0,
                        categoryID = doc.getString("CategoryID") ?: "",
                        date = doc.getString("Date") ?: "",
                        description = doc.getString("Description") ?: "",
                        imageUrl = doc.getString("Image") ?: "",
                        UID = doc.getString("UID") ?: "",
                        expenseID = doc.getString("expenseID") ?: "",
                        budgetId = doc.getString("budgetId") ?: ""
                    )
                }
                onComplete(expenses, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun getExpensesByCategory(
        userId: String,
        category: String,
        onComplete: (List<ExpenseModel>?, String?) -> Unit
    ) {
        expensesCollection
            .whereEqualTo("UID", userId)
            .whereEqualTo("CategoryID", category)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.map { doc ->
                    ExpenseModel(
                        id = doc.id,
                        amount = doc.getDouble("Amount") ?: 0.0,
                        categoryID = doc.getString("CategoryID") ?: "",
                        date = doc.getString("Date") ?: "",
                        description = doc.getString("Description") ?: "",
                        imageUrl = doc.getString("Image") ?: "",
                        UID = doc.getString("UID") ?: "",
                        expenseID = doc.getString("expenseID") ?: "",
                        budgetId = doc.getString("budgetId") ?: ""
                    )
                }
                onComplete(expenses, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun getTotalExpensesByUser(userId: String, onComplete: (Double?, String?) -> Unit) {
        expensesCollection
            .whereEqualTo("UID", userId)
            .get()
            .addOnSuccessListener { documents ->
                val total = documents.sumOf { doc ->
                    doc.getDouble("Amount") ?: 0.0
                }
                onComplete(total, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }
}