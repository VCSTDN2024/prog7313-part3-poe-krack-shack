package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import vcmsa.projects.krackshackbanking.BarGraph.BarGraphActivity
import vcmsa.projects.krackshackbanking.Expense.AddExpense
import vcmsa.projects.krackshackbanking.Expense.ExpenseHandler
import vcmsa.projects.krackshackbanking.Expense.ExpenseModel

class Dashboard : AppCompatActivity() {

    // database auth
    private lateinit var _data: DatabaseReference
    private lateinit var _auth: FirebaseAuth


    //

    //budget model array
    private val expenseList = mutableListOf<Pair<String,String>>()

    //UID for serch functions
    private lateinit var _UID: String


    //list of categories
    private val categoryList = mutableListOf<String>("Food", "Water", "Entertainment", "Transportation", "Other")
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
        _UID = _auth.currentUser?.uid.toString()
        _data = FirebaseDatabase.getInstance("https://prog7313poe-default-rtdb.europe-west1.firebasedatabase.app/").getReference(_UID)

        //xml components
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
        // this will be the event listener for each category expense

        _data.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                // here we get the total expense per category
                for (category in categoryList) {
                    var total = 0.0
                    for (expense in snapshot.children) {
                        if (expense.child("category").value.toString() == category) {
                            total += expense.child("amount").value.toString().toDouble()
                        }
                    }
                    // lost of expense per category
                    expenseList.add(Pair(category, total.toString()))
                }
                // here we get the total expense
                var totalExpense = 0.0
                for (expense in snapshot.children) {
                    totalExpense += expense.child("amount").value.toString().toDouble()
                }
                // here you can put what returns you want
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }





}

