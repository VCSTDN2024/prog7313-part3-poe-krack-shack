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

public class RegisterUser : AppCompatActivity() {

    lateinit var _database: DatabaseReference
    lateinit var _auth: FirebaseAuth
    lateinit var _userEmailIn: EditText
    lateinit var _userPasswordIn: EditText
    lateinit var _userConfirmPasswordIn: EditText
    lateinit var _register: Button

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
                _auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
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
                        } else {
                            Toast.makeText(
                                this,
                                "Registration Failed", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }
    }




}

