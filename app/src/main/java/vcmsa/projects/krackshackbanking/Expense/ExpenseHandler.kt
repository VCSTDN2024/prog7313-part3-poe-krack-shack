package vcmsa.projects.krackshackbanking.Expense

import androidx.appcompat.app.AppCompatActivity
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.ContextThemeWrapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import java.util.Date
import android.widget.Button
import vcmsa.projects.krackshackbanking.R
import java.util.Calendar

 class ExpenseHandler : AppCompatActivity() {
  private lateinit var btnStartDate: Button
  private lateinit var btnEndDate: Button
  private var isStartDate = true  // Flag to track which button was tapped

  override fun onCreate(savedInstanceState: Bundle?) {
   super.onCreate(savedInstanceState)
   setContentView(R.layout.expenses) // <-- Replace with your actual layout XML file name

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
  }

  private fun showDatePickerDialog()
  {
   val calendar = Calendar.getInstance()
   val year = calendar.get(Calendar.YEAR)
   val month = calendar.get(Calendar.MONTH)
   val day = calendar.get(Calendar.DAY_OF_MONTH)

   val dialog = DatePickerDialog(
    ContextThemeWrapper(this, R.style.SpinnerDatePickerDialogTheme),
    { _, selectedYear, selectedMonth, selectedDay ->
     val formattedDate = String.format("%04d-%02d-%02d", selectedYear, selectedMonth + 1, selectedDay)
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
