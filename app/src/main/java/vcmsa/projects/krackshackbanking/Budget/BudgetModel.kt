package vcmsa.projects.krackshackbanking.Budget

import com.google.firebase.firestore.Exclude
import java.util.Date

data class BudgetModel(
    val id: String = "",
    val userId: String = "",
    val category: String = "",
    val monthlyLimit: Double = 0.0,
    val currentSpent: Double = 0.0,
    val startDate: Date = Date(),
    val endDate: Date = Date(),
    val isActive: Boolean = true,
    val color: String = "#000000",
    val icon: String = "default",
    val notificationsEnabled: Boolean = true,
    val notificationThreshold: Double = 0.8, // 80% of limit
    @get:Exclude
    val transactions: List<String> = listOf() // List of transaction IDs
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "userId" to userId,
            "category" to category,
            "monthlyLimit" to monthlyLimit,
            "currentSpent" to currentSpent,
            "startDate" to startDate,
            "endDate" to endDate,
            "isActive" to isActive,
            "color" to color,
            "icon" to icon,
            "notificationsEnabled" to notificationsEnabled,
            "notificationThreshold" to notificationThreshold
        )
    }

    fun getRemainingBudget(): Double {
        return monthlyLimit - currentSpent
    }

    fun getPercentageSpent(): Double {
        return if (monthlyLimit > 0) (currentSpent / monthlyLimit) * 100 else 0.0
    }

    fun isOverBudget(): Boolean {
        return currentSpent > monthlyLimit
    }

    fun isNearLimit(): Boolean {
        return getPercentageSpent() >= (notificationThreshold * 100)
    }
}