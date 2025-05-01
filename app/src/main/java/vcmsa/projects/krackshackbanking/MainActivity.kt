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

    // xml buttons
    private lateinit var _register: Button
    private lateinit var _login: Button

    private lateinit var RegesterView: RegisterUser

    private val loginUser = LoginUser()


    override fun onCreate(savedInstanceState: Bundle?) {
        // initalising elements
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        _register = findViewById(R.id.btnRegister)
        _login = findViewById(R.id.btnLogin)

    // button to take user to register
        _register.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)

        }
    // button to take user to login
        _login.setOnClickListener {
            val intent = Intent(this, LoginUser::class.java)
            startActivity(intent)
        }

    }






}