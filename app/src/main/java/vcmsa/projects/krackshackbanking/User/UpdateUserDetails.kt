package vcmsa.projects.krackshackbanking.User

import com.google.firebase.firestore.FirebaseFirestore

class UpdateUserDetails {
    private val db = FirebaseFirestore.getInstance()

    fun updateUserDetails(
        userId: String,
        updates: Map<String, Any>,
        onComplete: (Boolean, String?) -> Unit
    ) {
        db.collection("users").document(userId)
            .update(updates)
            .addOnSuccessListener {
                onComplete(true, null)
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }

    fun updateUserPassword(
        userId: String,
        newPassword: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        val updates = mapOf("password" to newPassword)
        updateUserDetails(userId, updates, onComplete)
    }

    fun updateUserContactInfo(
        userId: String,
        phoneNumber: String,
        address: String,
        onComplete: (Boolean, String?) -> Unit
    ) {
        val updates = mapOf(
            "phoneNumber" to phoneNumber,
            "address" to address
        )
        updateUserDetails(userId, updates, onComplete)
    }
}