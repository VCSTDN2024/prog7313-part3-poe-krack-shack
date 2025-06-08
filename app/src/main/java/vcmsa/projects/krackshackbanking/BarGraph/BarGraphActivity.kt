package vcmsa.projects.krackshackbanking.BarGraph

import CategoryExpense
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.Color
import android.view.View
import android.widget.AdapterView
import android.widget.ListView
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import vcmsa.projects.krackshackbanking.Dashboard
import vcmsa.projects.krackshackbanking.R
import kotlin.properties.Delegates

private lateinit var bottomNavigationView: BottomNavigationView
private lateinit var barChart: BarChart
private lateinit var barDataSet: BarDataSet
private lateinit var lvCategories: ListView
private lateinit var barEntries: ArrayList<BarEntry>

// Declaring the DataModel Array
private var dataModel: ArrayList<DataModel>? = null


// database components
private lateinit var _data : DatabaseReference
private lateinit var _auth : FirebaseAuth
private lateinit var _uid : String
private val expenseList = mutableListOf<CategoryExpense>()



//global total for bar graph


private lateinit var adapter: CustomAdapter

var Budget: Float = 0f
var Expense: Float = 0f

// ArrayList for the first set of bar entries
private val barEntriesList: ArrayList<BarEntry>
    get() {

        Expense = 0f
        barEntries = ArrayList()

        for (i in dataModel!!.indices) {
            if (dataModel!![i].checked) {
                Expense += dataModel!![i].amount
            }
        }

        // Adding entries to the ArrayList for the first set
        barEntries.add(BarEntry(1f, Expense))
        barEntries.add(BarEntry(2f, Budget))

        return barEntries
    }

class BarGraphActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bar_graph)

        lvCategories = findViewById<View>(R.id.lvCategories) as ListView
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_graph

        //intilize firebase
        _auth = FirebaseAuth.getInstance()
        _uid = _auth.currentUser?.uid.toString()
        _data = FirebaseDatabase.getInstance("https://prog7313poe-default-rtdb.europe-west1.firebasedatabase.app/").getReference(_uid)


        // Initializing the model and adding data
        dataModel = ArrayList<DataModel>()

        // Todo: Replace with data from database
        Budget = 8000f

        dataModel!!.add(DataModel("Water", true, getCategoryTotal("Water")))

        dataModel!!.add(DataModel("Electricity", true, getCategoryTotal("Electricity")))

        dataModel!!.add(DataModel("Food", true, getCategoryTotal("Food")))

        dataModel!!.add(DataModel("Rent", true, getCategoryTotal("Rent")))

        dataModel!!.add(DataModel("Fuel", true, getCategoryTotal("Fuel")))

        // Update the graph
        updateGraph()

        // Setting the adapter
        adapter = CustomAdapter(dataModel!!, applicationContext)
        lvCategories.adapter = adapter

        // Upon item click, checkbox will be opposite
        lvCategories.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModel: DataModel = dataModel!![position]
            dataModel.checked = !dataModel.checked
            // Update the graph
            updateGraph()
            adapter.notifyDataSetChanged()
        }

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
    }

    private fun updateGraph() {

        // Working on DataSet
        barDataSet = BarDataSet(barEntriesList, "Data")

        // Set colour for the bars
        val colors = intArrayOf(Color.RED, Color.GREEN)

        // Set the colors for the bars
        barDataSet.colors = colors.toList()

        barDataSet.valueTextSize = 10f

        // Working on BarChart
        barChart = findViewById(R.id.BCBudgetSummary)
        val data = BarData(barDataSet)
        barChart.data = data
        barChart.animateY(0)
        barChart.description.isEnabled = false
        barChart.isDragEnabled = true
        barChart.setVisibleXRangeMaximum(5f)

        // Set bar width
        data.barWidth = 1f

        // X-Axis Data
        val xAxis: XAxis = barChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.granularity = 1f
        xAxis.isGranularityEnabled = true

        // Enable grid lines for X-axis
        xAxis.setDrawGridLines(false)

        // Set grid line color
        xAxis.gridColor = Color.BLACK

        // Set grid line width
        xAxis.gridLineWidth = 1f

        // Y-Axis Data
        val leftAxis: YAxis = barChart.axisLeft
        leftAxis.setDrawGridLines(true)
        leftAxis.gridColor = Color.LTGRAY
        leftAxis.gridLineWidth = 1f
        leftAxis.textColor = Color.BLACK
        leftAxis.axisMinimum = 0f
        leftAxis.labelCount = 10

        // Set Y-axis maximum to the higher value between 120% of budget and expenses
        val maxExpense = Expense * 1.2f
        val maxBudget = Budget * 1.2f
        leftAxis.axisMaximum = maxOf(maxExpense, maxBudget)

        // Calculate granularity based on the maximum value
        leftAxis.granularity = leftAxis.axisMaximum / 10

        val rightAxis: YAxis = barChart.axisRight

        // Disable right Y-axis
        rightAxis.isEnabled = false

        barChart.xAxis.axisMinimum = 0f
        barChart.animate()

        // Invalidate the chart to refresh
        barChart.invalidate()
    }
    fun getCategoryTotal(categoryID: String) :Float{
        var _total = 0.0F
        _data.addValueEventListener(object : com.google.firebase.database.ValueEventListener {
            override fun onDataChange(snapshot: com.google.firebase.database.DataSnapshot) {
                // Clear old data before updating
                expenseList.clear()

                // Get the total expense per category

                    var total = 0.0
                    for (expense in snapshot.children) {
                        if (expense.child("categoryID").value.toString() == categoryID) {
                            total += expense.child("amount").value.toString().toDouble()
                        }
                    }
                    // Only add categories that have expenses
                    if (total > 0) {
                        _total = total.toFloat()
                    }

            }

            override fun onCancelled(error: DatabaseError) {

            }
        })

        return _total

    }
}