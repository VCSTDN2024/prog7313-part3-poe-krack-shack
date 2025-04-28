package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import vcmsa.projects.krackshackbanking.User.RegisterUser

class MainActivity : AppCompatActivity()
{
    private lateinit var _auth: FirebaseAuth
    private lateinit var _register: Button
    private lateinit var _login: Button

    //Imagine imagining
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _auth = FirebaseAuth.getInstance()
        _register = findViewById(R.id.btnRegister)
        _login = findViewById(R.id.btnLogin)

        _register.setOnClickListener {
            val intent = Intent(this, RegisterUser::class.java)
            startActivity(intent)
        }

    }
}