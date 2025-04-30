package vcmsa.projects.krackshackbanking.User
//

// kotlin has no proper handling for mvc esc architecture

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import vcmsa.projects.krackshackbanking.MainActivity
import vcmsa.projects.krackshackbanking.R

class LoginUser: AppCompatActivity()
{
    private lateinit var _auth: FirebaseAuth
    private lateinit var _userEmailIn: EditText
    private lateinit var _userPasswordIn: EditText

    private lateinit var loginBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        _auth = FirebaseAuth.getInstance()
        _userEmailIn = findViewById(R.id.Username_txt)
        _userPasswordIn = findViewById(R.id.Password_txt)
        loginBtn = findViewById(R.id.button)


        loginBtn.setOnClickListener {
            val email: String = _userEmailIn.text.toString()
            val pasword: String = _userPasswordIn.text.toString()

            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pasword)) {
                Toast.makeText(
                    this,
                    "Please fill all the fields", Toast.LENGTH_LONG
                ).show()
            } else {
                _auth.signInWithEmailAndPassword(email, pasword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(
                                this,
                                "Login Failed", Toast.LENGTH_LONG
                            ).show()
                        }
                    }
            }

        }
    }

    fun loginUser(email: String, password: String, onComplete: (Boolean, String?, String?) -> Unit) {
        _auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val userId = _auth.currentUser?.uid
                    onComplete(true, userId, null)
                } else {
                    onComplete(false, null, task.exception?.message)
                }
            }
    }

    fun isUserLoggedIn(): Boolean {
        return _auth.currentUser != null
    }

    fun getCurrentUserId(): String? {
        return _auth.currentUser?.uid
    }

    fun logout() {
        _auth.signOut()
    }
}

