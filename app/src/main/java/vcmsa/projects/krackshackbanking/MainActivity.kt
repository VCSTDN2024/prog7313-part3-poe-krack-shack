package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import vcmsa.projects.krackshackbanking.User.LoginUser
import vcmsa.projects.krackshackbanking.User.RegisterUser

class MainActivity : AppCompatActivity()
{
    private lateinit var _auth: FirebaseAuth
    private lateinit var _register: Button
    private lateinit var _login: Button
    private lateinit var emailInput: EditText
    private lateinit var passwordInput: EditText
    private val loginUser = LoginUser()

    //Imagine imagining
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _auth = FirebaseAuth.getInstance()
        _register = findViewById(R.id.btnRegister)
        _login = findViewById(R.id.btnLogin)
        emailInput = findViewById(R.id.emailInput)
        passwordInput = findViewById(R.id.passwordInput)

        // Check if user is already logged in
        if (loginUser.isUserLoggedIn()) {
            startDashboard()
        }

        _register.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        _login.setOnClickListener {
            val email = emailInput.text.toString()
            val password = passwordInput.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser.loginUser(email, password) { success, userId, error ->
                    if (success) {
                        startDashboard()
                    } else {
                        Toast.makeText(this, error ?: "Login failed", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun startDashboard() {
        val intent = Intent(this, DashboardActivity::class.java)
        startActivity(intent)
        finish()
    }
}