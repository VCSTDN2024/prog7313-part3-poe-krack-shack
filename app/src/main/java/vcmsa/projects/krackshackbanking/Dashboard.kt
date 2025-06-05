package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import vcmsa.projects.krackshackbanking.Expense.AddExpense
import vcmsa.projects.krackshackbanking.Expense.ExpenseHandler


class Dashboard : AppCompatActivity() {

    // database auth
    private lateinit var _data: DatabaseReference
    private lateinit var _auth: FirebaseAuth

    // xml components
    private lateinit var _income: Button
    private lateinit var _expense: Button
    private lateinit var _totalExpense: CardView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        // initialising components
        _auth = FirebaseAuth.getInstance()
        _data = FirebaseDatabase.getInstance().reference
        _income = findViewById(R.id.btn_log_income)
        _expense = findViewById(R.id.btn_log_expense)
        _totalExpense = findViewById(R.id.balanceCard)





        // button logic
        _income.setOnClickListener {
            // here we send the user to the add expense page
            val intent = Intent(this, ExpenseHandler::class.java)
            startActivity(intent)
        }


        // button logic
        _expense.setOnClickListener {
            // method to open the set monthly bidget area
            val intent = Intent(this, AddExpense::class.java)
            startActivity(intent)
        }
    }

    // method to fetch total expense per category and display it on dashboard
    fun GetTotalExpense()
    {

    }

}

