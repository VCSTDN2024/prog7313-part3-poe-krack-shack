package vcmsa.projects.krackshackbanking.Expense

import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.ContextThemeWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Date
import android.widget.Button
import com.google.android.material.bottomnavigation.BottomNavigationView
import vcmsa.projects.krackshackbanking.BarGraph.BarGraphActivity
import vcmsa.projects.krackshackbanking.BarGraph.bottomNavigationView
import vcmsa.projects.krackshackbanking.Dashboard
import vcmsa.projects.krackshackbanking.R
import java.util.Calendar

private lateinit var bottomNavigationView: BottomNavigationView

class ExpenseHandler : AppCompatActivity() {
    private lateinit var btnStartDate: Button
    private lateinit var btnEndDate: Button
    private var isStartDate = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expenses)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_money
        btnStartDate = findViewById(R.id.btnStartDate)
        btnEndDate = findViewById(R.id.btnEndDate)

        btnStartDate.setOnClickListener {
            isStartDate = true
            showDatePickerDialog()
        }

        btnEndDate.setOnClickListener {
            isStartDate = false
            showDatePickerDialog()
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
                    startActivity(Intent(this, ExpenseHandler::class.java))
                    finish()
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

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val dialog = DatePickerDialog(
            ContextThemeWrapper(this, R.style.SpinnerDatePickerDialogTheme),
            { _, selectedYear, selectedMonth, selectedDay ->
                val formattedDate =
                    String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
                if (isStartDate) {
                    btnStartDate.text = formattedDate
                } else {
                    btnEndDate.text = formattedDate
                }
            },
            year,
            month,
            day
        )

        // Force spinner style
        dialog.datePicker.calendarViewShown = false
        dialog.datePicker.spinnersShown = true

        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent) // Optional: removes background frame
        dialog.show()
    }
}
