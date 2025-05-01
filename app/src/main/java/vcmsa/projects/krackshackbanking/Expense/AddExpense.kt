package vcmsa.projects.krackshackbanking.Expense

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vcmsa.projects.krackshackbanking.Dashboard
import vcmsa.projects.krackshackbanking.R
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddExpense : AppCompatActivity() {

    private lateinit var _categorySpinner: Spinner
    private lateinit var _datePicker: DatePicker
    private lateinit var _amountEditText: EditText
    private lateinit var _descriptionEditText: EditText
    private lateinit var _submitButton: Button
    private lateinit var _cacnelButton: Button
    private lateinit var _imageUri: Uri

    // database reference
    private lateinit var _data: DatabaseReference
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_create)

        _categorySpinner = findViewById(R.id.spnCategory)
        _datePicker = findViewById(R.id.dpDate)
        _amountEditText = findViewById(R.id.txtAmountInput)
        _descriptionEditText = findViewById(R.id.txtDescriptionLabel)
        _submitButton = findViewById(R.id.btnEnter)
        _cacnelButton = findViewById(R.id.btnCancel)


        _data = FirebaseDatabase.getInstance().reference





        _submitButton.setOnClickListener {
            val category = _categorySpinner.selectedItem.toString()
            val date =
                _datePicker.dayOfMonth.toString() + "/" + _datePicker.month.toString() + "/" + _datePicker.year.toString()
            val amount = _amountEditText.text.toString().toFloat()
            val description = _descriptionEditText.text.toString()
        }

        _cacnelButton.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

    }
}