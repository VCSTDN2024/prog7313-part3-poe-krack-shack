package vcmsa.projects.krackshackbanking

import CategoryExpense
import CategoryExpenseAdapter
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
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView


class Dashboard : AppCompatActivity() {

    // database auth
    private lateinit var _data: DatabaseReference
    private lateinit var _auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryExpenseAdapter
    private val expenseList = mutableListOf<CategoryExpense>() // updated

    //

    //budget model array

    //UID for search functions
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

        // Set the layout first!
        setContentView(R.layout.dashboard)

        // Now safely initialize views
        recyclerView = findViewById(R.id.parent_recyclerview)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = CategoryExpenseAdapter(expenseList)
        recyclerView.adapter = adapter

        // Firebase init
        _auth = FirebaseAuth.getInstance()
        _UID = _auth.currentUser?.uid.toString()
        _data = FirebaseDatabase.getInstance("https://prog7313poe-default-rtdb.europe-west1.firebasedatabase.app/")
            .getReference(_UID)

        // XML components
        _income = findViewById(R.id.btn_log_income)
        _expense = findViewById(R.id.btn_log_expense)
        _totalExpense = findViewById(R.id.balanceCard)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        bottomNavigationView.selectedItemId = R.id.navigation_home

        // Set up bottom nav logic
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

                R.id.navigation_money,
                R.id.navigation_profile,
                R.id.navigation_menu -> true

                else -> false
            }
        }

        _income.setOnClickListener {
            startActivity(Intent(this, ExpenseHandler::class.java))
        }

        _expense.setOnClickListener {
            startActivity(Intent(this, AddExpense::class.java))
        }

        // Now fetch the data
        getTotalExpense()
    }

    // method to fetch total expense per category and display it on dashboard
    private fun getTotalExpense() {
        // this will be the event listener for each category expense
        _data.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                // Clear old data before updating
                expenseList.clear()

                // Get the total expense per category
                for (category in categoryList) {
                    var total = 0.0
                    for (expense in snapshot.children) {
                        if (expense.child("category").value.toString() == category) {
                            total += expense.child("amount").value.toString().toDouble()
                        }
                    }
                    // Only add categories that have expenses
                    if (total > 0) {
                        expenseList.add(CategoryExpense(category, total.toString()))
                    }
                }

                // Notify the adapter that data has changed
                adapter.notifyDataSetChanged()

                // Optionally calculate the total expense
                var totalExpense = 0.0
                for (expense in snapshot.children) {
                    totalExpense += expense.child("amount").value.toString().toDouble()
                }

                // You can display the totalExpense in a TextView or use it as needed
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }





}

