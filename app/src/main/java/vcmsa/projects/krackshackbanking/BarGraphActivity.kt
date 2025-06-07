package vcmsa.projects.krackshackbanking

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
import vcmsa.projects.krackshackbanking.BarGraph.CustomAdapter
import vcmsa.projects.krackshackbanking.BarGraph.DataModel

private lateinit var bottomNavigationView: BottomNavigationView
private lateinit var barChart: BarChart
private lateinit var barDataSet: BarDataSet
private lateinit var barEntries: ArrayList<BarEntry>

// Declaring the DataModel Array
private var dataModel: ArrayList<DataModel>? = null

// Declaring the elements from the main layout file
private lateinit var listView: ListView
private lateinit var adapter: CustomAdapter

var Income: Float = 20.0f
var Expense: Float = 10.0f

// ArrayList for the first set of bar entries
private val barEntriesList: ArrayList<BarEntry>
    get() {
        // Creating a new ArrayList
        barEntries = ArrayList()

        // Adding entries to the ArrayList for the first set
        barEntries.add(BarEntry(1f, Expense))
        barEntries.add(BarEntry(2f, Income))

        return barEntries
    }

class BarGraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bar_graph)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_graph

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
        barChart.animateY(1000)
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
        leftAxis.textColor = Color.WHITE

        val rightAxis: YAxis = barChart.axisRight

        // Disable right Y-axis
        rightAxis.isEnabled = false

        barChart.xAxis.axisMinimum = 0f
        barChart.animate()

        // Invalidate the chart to refresh
        barChart.invalidate()

        // Initializing the elements from the main layout file
        listView = findViewById<View>(R.id.lvCategories) as ListView

        // Initializing the model and adding data
        // False = not checked; True = checked
        dataModel = ArrayList<DataModel>()
        dataModel!!.add(DataModel("Water", false))
        dataModel!!.add(DataModel("Electricity", false))
        dataModel!!.add(DataModel("Food", false))
        dataModel!!.add(DataModel("Rent", false))
        dataModel!!.add(DataModel("Slave", false))
        // Setting the adapter
        adapter = CustomAdapter(dataModel!!, applicationContext)
        listView.adapter = adapter

        // Upon item click, checkbox will be set to checked
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            val dataModel: DataModel = dataModel!![position] as DataModel
            dataModel.checked = !dataModel.checked
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
}