package vcmsa.projects.krackshackbanking.Expense

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Date

/**
 * Handler class for managing expense-related operations.
 * This class provides methods for CRUD operations on expenses.
 */
class ExpenseHandler {
    private val db = FirebaseFirestore.getInstance()
    private val expensesCollection = db.collection("expenses")
    private val budgetHandler = BudgetHandler()

    /**
     * Creates a new expense in Firestore
     * @param expense The expense model to create
     * @param onComplete Callback to handle the result of the operation
     */
    fun createExpense(expense: ExpenseModel, onComplete: (String?, String?) -> Unit) {
        if (!expense.isExpenseValid()) {
            onComplete(null, "Invalid expense data")
            return
        }

        val expenseRef = expensesCollection.document()
        val expenseWithId = expense.copy(id = expenseRef.id)

        expenseRef.set(expenseWithId.toMap())
            .addOnSuccessListener {
                // Update the budget's current spent amount
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

    /**
     * Retrieves all expenses for the current user
     * @return List of ExpenseModel objects
     */
    suspend fun getExpenses(): List<ExpenseModel> {
        val currentUser = auth.currentUser
        if (currentUser == null) throw IllegalStateException("User not authenticated")

        val snapshot = db.collection("expenses")
            .whereEqualTo("UID", currentUser.uid)
            .get()
            .await()

        return snapshot.documents.map { doc ->
            ExpenseModel(
                amount = doc.getDouble("Amount") ?: 0.0,
                categoryID = doc.getString("CategoryID") ?: "",
                date = doc.getString("Date") ?: "",
                description = doc.getString("Description") ?: "",
                imageUrl = doc.getString("Image") ?: "",
                UID = doc.getString("UID") ?: "",
                expenseID = doc.getString("expenseID") ?: ""
            )
        }
    }

    /**
     * Updates an existing expense
     * @param expense The updated expense data
     * @param onComplete Callback to handle the result of the operation
     */
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

    /**
     * Deletes an expense
     * @param expenseId The ID of the expense to delete
     * @param onComplete Callback to handle the result of the operation
     */
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
            .whereEqualTo("userId", userId)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.mapNotNull { it.toObject(ExpenseModel::class.java) }
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
                val expenses = documents.mapNotNull { it.toObject(ExpenseModel::class.java) }
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
            .whereEqualTo("userId", userId)
            .whereGreaterThanOrEqualTo("date", startDate)
            .whereLessThanOrEqualTo("date", endDate)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.mapNotNull { it.toObject(ExpenseModel::class.java) }
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
            .whereEqualTo("userId", userId)
            .whereEqualTo("category", category)
            .orderBy("date", Query.Direction.DESCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val expenses = documents.mapNotNull { it.toObject(ExpenseModel::class.java) }
                onComplete(expenses, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun getTotalExpensesByUser(userId: String, onComplete: (Double?, String?) -> Unit) {
        expensesCollection
            .whereEqualTo("userId", userId)
            .get()
            .addOnSuccessListener { documents ->
                val total = documents.mapNotNull { it.toObject(ExpenseModel::class.java) }
                    .sumOf { it.amount }
                onComplete(total, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }
}