package vcmsa.projects.krackshackbanking.User

import com.google.firebase.firestore.Exclude
import java.util.Date

data class UserModel(
    val id: String = "",
    val email: String = "",
    val name: String = "",
    val phoneNumber: String = "",
    val createdAt: Date = Date(),
    val lastLogin: Date = Date(),
    val monthlyIncome: Double = 0.0,
    val savingsGoal: Double = 0.0,
    val currency: String = "USD",
    val notificationEnabled: Boolean = true,
    val budgetCategories: List<String> = listOf(),
    @get:Exclude
    val password: String = ""
) {
    fun toMap(): Map<String, Any> {
        return mapOf(
            "id" to id,
            "email" to email,
            "name" to name,
            "phoneNumber" to phoneNumber,
            "createdAt" to createdAt,
            "lastLogin" to lastLogin,
            "monthlyIncome" to monthlyIncome,
            "savingsGoal" to savingsGoal,
            "currency" to currency,
            "notificationEnabled" to notificationEnabled,
            "budgetCategories" to budgetCategories
        )
    }
}