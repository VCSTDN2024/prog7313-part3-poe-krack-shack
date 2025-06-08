package vcmsa.projects.krackshackbanking

import CategoryExpense
import CategoryExpenseAdapter
import vcmsa.projects.krackshackbanking.Budget.BudgetHandler
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
import com.google.android.material.search.SearchBar
import com.google.android.material.search.SearchView
import android.view.View
import android.util.Log


class Dashboard : AppCompatActivity() {

    // database auth
    private lateinit var _data: DatabaseReference
    private lateinit var _auth: FirebaseAuth
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CategoryExpenseAdapter
    private val expenseList = mutableListOf<CategoryExpense>() // updated
    private lateinit var searchBar: SearchBar
    private lateinit var searchView: SearchView


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

        searchBar = findViewById(R.id.searchBar)
        searchView = findViewById(R.id.searchView)
        _income = findViewById(R.id.btn_Add_Budget)
        _expense = findViewById(R.id.btn_log_expense)
        _totalExpense = findViewById(R.id.balanceCard)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_home

        searchBar.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                // Handle menu items here if you added them
                else -> false
            }
        }
        searchBar.setOnClickListener {
            searchBar.visibility = View.GONE
            searchView.visibility = View.VISIBLE
            searchView.show() // expand the view with animation
        }
        searchView.editText.setOnEditorActionListener { v, actionId, event ->
            val query = searchView.text.toString()
            // TODO: Perform search logic here
            Log.d("Search", "User searched for: $query")
            searchView.hide()
            searchView.visibility = View.GONE
            searchBar.visibility = View.VISIBLE
            true
        }

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
            startActivity(Intent(this, BudgetHandler::class.java))
        }

        _expense.setOnClickListener {
            startActivity(Intent(this, AddExpense::class.java))
        }

        // Now fetch the data
        getTotalExpense()
    }

    // method to fetch total expense per category and display it on dashboard
     fun getTotalExpense() {
        // this will be the event listener for each category expense
        _data.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                // Clear old data before updating
                expenseList.clear()

                // Get the total expense per category
                for (categoryID in categoryList) {
                    var total = 0.0
                    for (expense in snapshot.children) {
                        if (expense.child("categoryID").value.toString() == categoryID) {
                            total += expense.child("amount").value.toString().toDouble()
                        }
                    }
                    // Only add categories that have expenses
                    if (total > 0) {
                        expenseList.add(CategoryExpense(categoryID, total.toString()))
                    }
                }

                // Notify the adapter that data has changed
                    adapter.notifyDataSetChanged()

                // Optionally calculate the total expense


                // You can display the totalExpense in a TextView or use it as needed
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}


