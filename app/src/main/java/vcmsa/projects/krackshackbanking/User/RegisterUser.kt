package vcmsa.projects.krackshackbanking.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import vcmsa.projects.krackshackbanking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import android.widget.EditText
import android.widget.Toast
import vcmsa.projects.krackshackbanking.MainActivity
import com.google.firebase.firestore.FirebaseFirestore
import java.util.UUID

public class RegisterUser : AppCompatActivity() {

    lateinit var _database: DatabaseReference
    lateinit var _auth: FirebaseAuth
    lateinit var _userEmailIn: EditText
    lateinit var _userPasswordIn: EditText
    lateinit var _userConfirmPasswordIn: EditText
    lateinit var _register: Button
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)
        _userEmailIn = findViewById(R.id.txtUsernameInput)
        _userPasswordIn = findViewById(R.id.txtPasswordInput)
        _userConfirmPasswordIn = findViewById(R.id.txtConfirmPasswordInput)
        _register = findViewById(R.id.btnEnter)
        _auth = FirebaseAuth.getInstance()
        _database = FirebaseDatabase.getInstance().reference

        _register.setOnClickListener {
            var email: String = _userEmailIn.text.toString()
            var password: String = _userPasswordIn.text.toString()
            var confirmPassword: String = _userConfirmPasswordIn.text.toString()
            if (TextUtils.isEmpty(email) ||
                TextUtils.isEmpty(password) ||
                TextUtils.isEmpty(confirmPassword)
            ) {
                Toast.makeText(
                    this,
                    "Please fill all the fields", Toast.LENGTH_LONG
                ).show()
            } else if (password != confirmPassword) {
                registerUser(email, password, "", "", "", onComplete)
            }
        }
    }

    fun registerUser(
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        phoneNumber: String,
        onComplete: (Boolean, String?, String?) -> Unit
    ) {
        _auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val userId = _auth.currentUser?.uid
                    if (userId != null) {
                        val user = UserModel(
                            userId = userId,
                            email = email,
                            firstName = firstName,
                            lastName = lastName,
                            phoneNumber = phoneNumber,
                            accountNumber = generateAccountNumber()
                        )

                        db.collection("users").document(userId)
                            .set(user)
                            .addOnSuccessListener {
                                onComplete(true, userId, null)
                                Toast.makeText(
                                    this,
                                    "Successfully Registered", Toast.LENGTH_LONG
                                ).show()
                                val intent = Intent(
                                    this,
                                    MainActivity::class.java
                                )
                                startActivity(intent)
                                finish()
                            }
                            .addOnFailureListener { e ->
                                onComplete(false, null, e.message)
                                Toast.makeText(
                                    this,
                                    "Registration Failed", Toast.LENGTH_LONG
                                ).show()
                            }
                    } else {
                        onComplete(false, null, "Failed to get user ID")
                    }
                } else {
                    onComplete(false, null, task.exception?.message)
                    Toast.makeText(
                        this,
                        "Registration Failed", Toast.LENGTH_LONG
                    ).show()
                }
            }
    }

    private fun generateAccountNumber(): String {
        return "KR" + UUID.randomUUID().toString().substring(0, 8).toUpperCase()
    }
}

