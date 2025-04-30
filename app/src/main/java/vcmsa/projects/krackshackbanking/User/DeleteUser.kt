package vcmsa.projects.krackshackbanking.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class DeleteUser {
    private val auth = FirebaseAuth.getInstance()
    private val db = FirebaseFirestore.getInstance()

    fun deleteUser(userId: String, onComplete: (Boolean, String?) -> Unit) {
        // Delete user from Firestore
        db.collection("users").document(userId)
            .delete()
            .addOnSuccessListener {
                // Delete user from Firebase Auth
                auth.currentUser?.delete()
                    ?.addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            onComplete(true, null)
                        } else {
                            onComplete(false, task.exception?.message)
                        }
                    }
            }
            .addOnFailureListener { e ->
                onComplete(false, e.message)
            }
    }
}