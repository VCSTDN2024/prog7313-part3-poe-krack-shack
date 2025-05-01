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

    private val loginUser = LoginUser()

    //Imagine imagining
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        _register = findViewById(R.id.btnRegister)
        _login = findViewById(R.id.btnLogin)


        _register.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

        _login.setOnClickListener {
            val intent = Intent(this, LoginUser::class.java)
            startActivity(intent)
        }

    }






}