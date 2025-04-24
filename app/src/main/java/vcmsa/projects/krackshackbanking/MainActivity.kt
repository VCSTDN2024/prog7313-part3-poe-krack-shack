package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import vcmsa.projects.krackshackbanking.User.RegesterUser

class MainActivity : AppCompatActivity()
{
    private lateinit var _auth: FirebaseAuth
    private lateinit var _regester: Button
    private lateinit var _login: Button

    //Imagine imagining
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        _auth = FirebaseAuth.getInstance()
        _regester = findViewById(R.id.btnRegister)
        _login = findViewById(R.id.btnLogin)

        _regester.setOnClickListener {
            val intent = Intent(this, RegesterUser::class.java)
            startActivity(intent)
        }

    }
}