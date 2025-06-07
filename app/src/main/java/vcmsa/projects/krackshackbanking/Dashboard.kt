package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView
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
    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard)

        // initialising components
        _auth = FirebaseAuth.getInstance()
        _data = FirebaseDatabase.getInstance().reference
        _income = findViewById(R.id.btn_log_income)
        _expense = findViewById(R.id.btn_log_expense)
        _totalExpense = findViewById(R.id.balanceCard)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
      bottomNavigationView.selectedItemId = R.id.navigation_home
        // Setup bottom navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    finish()
                    true
                }

                R.id.navigation_graph -> {
                    startActivity(Intent(this, BarGraphActivity::class.java))
                    finish()
                    true
                }

                R.id.navigation_money -> {
                    // TODO: Replace with Money activity when available
                    // startActivity(Intent(this, MoneyActivity::class.java))
                    // finish()
                    true
                }

                R.id.navigation_profile -> {
                    // TODO: Replace with Profile activity when available
                    // startActivity(Intent(this, ProfileActivity::class.java))
                    // finish()
                    true
                }

                R.id.navigation_menu -> {
                    // TODO: Replace with Menu activity when available
                    // startActivity(Intent(this, MenuActivity::class.java))
                    // finish()
                    true
                }

                else -> false
            }
        }

        // button logic
        _income.setOnClickListener {
            // here we send the user to the add expense page
            val intent = Intent(this, ExpenseHandler::class.java)
            startActivity(intent)
        }

        // button logic
        _expense.setOnClickListener {
            // method to open the set monthly budget area
            val intent = Intent(this, AddExpense::class.java)
            startActivity(intent)
        }
    }

    // method to fetch total expense per category and display it on dashboard
    fun GetTotalExpense() {
        // Implementation here
    }

}
