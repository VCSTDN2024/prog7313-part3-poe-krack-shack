package vcmsa.projects.krackshackbanking.User

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

// class to fetch current user
class GetUserData {

    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val UID : String = auth.currentUser?.uid.toString()
    private lateinit var _data : FirebaseDatabase


    fun getUserData() {

    }
}