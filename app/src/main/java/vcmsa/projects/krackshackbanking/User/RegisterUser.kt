package vcmsa.projects.krackshackbanking.User

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import vcmsa.projects.krackshackbanking.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.Firebase
import com.google.firebase.database.database
import vcmsa.projects.krackshackbanking.MainActivity


import java.util.UUID

public class RegisterUser : AppCompatActivity() {

    //firebase auth component
    private lateinit var _auth: FirebaseAuth


    //xml components
    private lateinit var _database: DatabaseReference
    private lateinit var _btnRegister: Button
    private lateinit var _txtEmail: EditText
    private lateinit var _txtPassword: EditText
    private lateinit var _txtConfirm: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

            // initialising components for database
        _auth = FirebaseAuth.getInstance()
        _database = Firebase.database.reference

        //xml components initialisation
        _btnRegister = findViewById(R.id.btnEnter)
        _txtEmail = findViewById(R.id.txtEmailInput)
        _txtPassword = findViewById(R.id.txtPasswordInput)
        _txtConfirm = findViewById(R.id.txtConfirmPasswordInput)

        _btnRegister.setOnClickListener {
            //when the user submits it creates a new authentication user
            val _txtEmailIn = _txtEmail.text.toString()
            val _txtPasswordIn = _txtPassword.text.toString()
            val _txtConfirmIn = _txtConfirm.text.toString()

            if (_txtEmailIn.isEmpty() || _txtPasswordIn.isEmpty() || _txtConfirmIn.isEmpty()) {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            } else if (_txtPasswordIn != _txtConfirmIn) {
                Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
            } else {
                val isCreated = CreateUser(_txtEmailIn, _txtPasswordIn)

            }

        }

    }

    fun CreateUser(_txtEmailIn: String, _txtPasswordIn: String): Boolean {
    // creating a user object for the database if inputted data is valid
        _auth.createUserWithEmailAndPassword(_txtEmailIn, _txtPasswordIn)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "User created", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)
                } else {
                    Toast.makeText(this, "User could not be created", Toast.LENGTH_SHORT).show()
                }
            }
        // user objects for the database
        val user = UserModel(
            userEmail = _txtEmailIn,
            userPassword = _txtPasswordIn,
            UID = _txtEmailIn.split("@")[0]+ UUID.randomUUID().toString(),
            userName = _txtEmailIn.split("@")[0],
        )
    // writing to the database
        _database.child("User").child(user.UID.toString()).setValue(user)


        return false
    }
}







