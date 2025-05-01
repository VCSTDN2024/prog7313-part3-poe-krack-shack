package vcmsa.projects.krackshackbanking.User

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
import vcmsa.projects.krackshackbanking.Dashboard
import vcmsa.projects.krackshackbanking.MainActivity
import vcmsa.projects.krackshackbanking.R

class LoginUser: AppCompatActivity()
{
    // firebase auth token
    private lateinit var _auth: FirebaseAuth

    // xml components
    private lateinit var _userEmailIn: EditText
    private lateinit var _userPasswordIn: EditText

    private lateinit var loginBtn: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)
    // initalising components
        _auth = FirebaseAuth.getInstance()
        _userEmailIn = findViewById(R.id.Username_txt)
        _userPasswordIn = findViewById(R.id.Password_txt)
        loginBtn = findViewById(R.id.button)


        // button to run login in logic
        loginBtn.setOnClickListener {

            // taking in user input
            val email: String = _userEmailIn.text.toString()
            val pasword: String = _userPasswordIn.text.toString()

                // checking if input exists
            if (TextUtils.isEmpty(email) || TextUtils.isEmpty(pasword)) {
                Toast.makeText(
                    this,
                    "Please fill all the fields", Toast.LENGTH_LONG
                ).show()
            } else {

                // if auth successful take user to dashboard
                _auth.signInWithEmailAndPassword(email, pasword)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this, Dashboard::class.java)
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


}

