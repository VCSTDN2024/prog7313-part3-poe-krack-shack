package vcmsa.projects.krackshackbanking.User


import com.google.firebase.Firebase
import com.google.firebase.database.database

//tf is this?????
class UpdateUserDetails {
    private val db = Firebase.database

    private fun updateUserDetails(
        userId: String,
        updates: Map<String, Any>,
        onComplete: (Boolean, String?) -> Unit
    ) {
        // what is collection????


        fun updateUserPassword(
            userId: String,
            newPassword: String,
            onComplete: (Boolean, String?) -> Unit
        ) {
            val updates = mapOf("password" to newPassword)
            updateUserDetails(userId, updates, onComplete)
        }
    }
}



