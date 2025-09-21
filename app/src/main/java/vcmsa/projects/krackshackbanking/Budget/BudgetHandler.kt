package vcmsa.projects.krackshackbanking.Budget


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.krackshackbanking.Budget.BudgetModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import vcmsa.projects.krackshackbanking.Dashboard
import vcmsa.projects.krackshackbanking.R
import java.time.format.DateTimeFormatter
import java.util.UUID

class BudgetHandler: AppCompatActivity()
{
    //xml components
    private lateinit var budgetInput : EditText
    private lateinit var btnSubmit : Button
    private lateinit var btnCancel : Button

    //data model
    private val _budget = mutableListOf<Pair<String, BudgetModel>>()

    //firebase
    private lateinit var _data : DatabaseReference
    private lateinit var _auth : FirebaseAuth
    private lateinit var _uid : String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        setContentView(R.layout.add_budget)

        //xml components
        budgetInput = findViewById(R.id.edtBudgetName)
        btnSubmit = findViewById(R.id.btnSave)
        btnCancel = findViewById(R.id.btnCancel)

        //firebase
        _auth = FirebaseAuth.getInstance()
        _uid = _auth.currentUser?.uid.toString()
        _data = FirebaseDatabase.getInstance("https://prog7313poe-default-rtdb.europe-west1.firebasedatabase.app/").getReference(_uid)


        btnSubmit.setOnClickListener {
            val BudgetAmount = budgetInput.text.toString()
            val id = UUID.randomUUID().toString()
            val dateFormat = DateTimeFormatter.ofPattern("yyyy-MM")
            val date = java.time.LocalDate.now().format(dateFormat).toString()

            if (BudgetAmount.isNotEmpty()) {
                val budget = BudgetModel(id,date, BudgetAmount)
                _data.child("Budget").setValue(budget).addOnCompleteListener {
                    Toast.makeText(this, "Budget added successfully", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, Dashboard::class.java)
                    startActivity(intent)
                }.addOnFailureListener {
                    Toast.makeText(this, "Failed to set budget", Toast.LENGTH_SHORT).show()
                }

            }
            else{
                Toast.makeText(this, "Please enter a budget", Toast.LENGTH_SHORT).show()
            }
        }

        btnCancel.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }
    }

}
