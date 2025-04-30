package vcmsa.projects.krackshackbanking.Budget

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.Date

// super class to control budget related activities

class BudgetHandler {
    private val db = FirebaseFirestore.getInstance()
    private val budgetsCollection = db.collection("budgets")

    fun createBudget(budget: BudgetModel, onComplete: (String?, String?) -> Unit) {
        val budgetRef = budgetsCollection.document()
        val budgetWithId = budget.copy(id = budgetRef.id)

        budgetRef.set(budgetWithId.toMap())
            .addOnSuccessListener {
                onComplete(budgetRef.id, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun updateBudget(budget: BudgetModel, onComplete: (String?) -> Unit) {
        budgetsCollection.document(budget.id)
            .set(budget.toMap())
            .addOnSuccessListener {
                onComplete(null)
            }
            .addOnFailureListener { e ->
                onComplete(e.message)
            }
    }

    fun deleteBudget(budgetId: String, onComplete: (String?) -> Unit) {
        budgetsCollection.document(budgetId)
            .delete()
            .addOnSuccessListener {
                onComplete(null)
            }
            .addOnFailureListener { e ->
                onComplete(e.message)
            }
    }

    fun getBudgetsByUser(userId: String, onComplete: (List<BudgetModel>?, String?) -> Unit) {
        budgetsCollection
            .whereEqualTo("userId", userId)
            .orderBy("category", Query.Direction.ASCENDING)
            .get()
            .addOnSuccessListener { documents ->
                val budgets = documents.mapNotNull { it.toObject(BudgetModel::class.java) }
                onComplete(budgets, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun getBudgetById(budgetId: String, onComplete: (BudgetModel?, String?) -> Unit) {
        budgetsCollection.document(budgetId)
            .get()
            .addOnSuccessListener { document ->
                if (document.exists()) {
                    val budget = document.toObject(BudgetModel::class.java)
                    onComplete(budget, null)
                } else {
                    onComplete(null, "Budget not found")
                }
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }

    fun updateBudgetSpent(budgetId: String, amount: Double, onComplete: (String?) -> Unit) {
        budgetsCollection.document(budgetId)
            .update("currentSpent", com.google.firebase.firestore.FieldValue.increment(amount))
            .addOnSuccessListener {
                onComplete(null)
            }
            .addOnFailureListener { e ->
                onComplete(e.message)
            }
    }

    fun getActiveBudgets(userId: String, onComplete: (List<BudgetModel>?, String?) -> Unit) {
        val now = Date()
        budgetsCollection
            .whereEqualTo("userId", userId)
            .whereEqualTo("isActive", true)
            .whereGreaterThanOrEqualTo("endDate", now)
            .get()
            .addOnSuccessListener { documents ->
                val budgets = documents.mapNotNull { it.toObject(BudgetModel::class.java) }
                onComplete(budgets, null)
            }
            .addOnFailureListener { e ->
                onComplete(null, e.message)
            }
    }
}