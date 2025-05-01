package vcmsa.projects.krackshackbanking.User


import com.google.firebase.Firebase
import com.google.firebase.database.database

//tf is this?????
class UpdateUserDetails {
    private val db = Firebase.database

    fun updateUserDetails(
        userId: String,
        updates: Map<String, Any>,
        onComplete: (Boolean, String?) -> Unit
    ) {
        // what is collection????
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


    // we dont have phone numbers???????
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