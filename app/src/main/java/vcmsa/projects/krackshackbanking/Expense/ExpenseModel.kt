package vcmsa.projects.krackshackbanking.Expense

import com.google.firebase.firestore.Exclude
import java.util.Date

/**
 * Data class representing an expense in the application.
 * This model matches the Firestore document structure.
 *
 * @property id A unique identifier for the expense
 * @property userId The user ID of the person who created the expense
 * @property amount The monetary amount of the expense
 * @property description A description of the expense
 * @property category The ID of the category this expense belongs to
 * @property budgetId The ID of the budget this expense belongs to
 * @property date The date of the expense in format "DD/MM/YYYY"
 * @property paymentMethod The method of payment used for the expense
 * @property location The location of the expense
 * @property receiptUrl The URL of the expense receipt if one was uploaded
 * @property isRecurring Whether the expense is recurring
 * @property recurringFrequency The frequency of the recurring expense
 * @property tags A list of tags associated with the expense
 * @property notes Additional notes about the expense
 * @property attachments A list of URLs to attached files
 */
data class ExpenseModel(
    val id: String = "",
    val userId: String = "",
    val amount: Double = 0.0,
    val description: String = "",
    val category: String = "",
    val budgetId: String = "",
    val date: Date = Date(),
    val location: String = "",
    @get:Exclude
    val attachments: List<String> = listOf() // URLs to attached files
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "userId" to userId,
            "amount" to amount,
            "description" to description,
            "category" to category,
            "budgetId" to budgetId,
            "date" to date,
        )
    }

    fun isExpenseValid(): Boolean {
        return amount > 0 && description.isNotBlank() && category.isNotBlank()
    }


}