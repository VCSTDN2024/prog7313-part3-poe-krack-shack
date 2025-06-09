package vcmsa.projects.krackshackbanking.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.Firebase
import com.google.firebase.database.database
import vcmsa.projects.krackshackbanking.MainActivity
import vcmsa.projects.krackshackbanking.R
import java.util.UUID

class RegisterUser : AppCompatActivity() {

    // Firebase
    private lateinit var _auth: FirebaseAuth
    private lateinit var _database: DatabaseReference

    // UI Components
    private lateinit var _btnRegister: Button
    private lateinit var _btnCancel: Button
    private lateinit var _txtEmail: EditText
    private lateinit var _txtPassword: EditText
    private lateinit var _txtConfirm: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        // Firebase setup
        _auth = FirebaseAuth.getInstance()
        _database = Firebase.database.reference

        // View setup
        _btnRegister = findViewById(R.id.btnEnter)
        _btnCancel = findViewById(R.id.btnCancel)
        _txtEmail = findViewById(R.id.txtEmailInput)
        _txtPassword = findViewById(R.id.txtPasswordInput)
        _txtConfirm = findViewById(R.id.txtConfirmPasswordInput)

        _btnRegister.setOnClickListener {
            val email = _txtEmail.text.toString()
            val password = _txtPassword.text.toString()
            val confirmPassword = _txtConfirm.text.toString()

            if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else if (password != confirmPassword) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                CreateUser(email, password)
            }
        }

        _btnCancel.setOnClickListener {
            finish() // Closes the registration activity
        }
    }

    private fun CreateUser(email: String, password: String) {
        _auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val firebaseUser = _auth.currentUser
                    val uid = firebaseUser?.uid ?: return@addOnCompleteListener

                    // Create and store user model using the Firebase UID
                    val user = UserModel(
                        userEmail = email,
                        userPassword = password,
                        UID = uid,
                        userName = email.substringBefore("@")
                    )

                    Toast.makeText(this, "User registered successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(
                        this,
                        "Registration failed: ${task.exception?.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }
}
