package vcmsa.projects.krackshackbanking.Expense

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import vcmsa.projects.krackshackbanking.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class AddExpense : AppCompatActivity() {
    // UI Components
    private lateinit var spinnerCategory: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var btnUploadPhoto: Button
    private lateinit var btnCancel: Button
    private lateinit var btnEnter: Button

    // For photo upload
    private val pickImage = 100
    private var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_create) // Make sure your XML is named activity_add_expense.xml

        // Initialize UI components
        spinnerCategory = findViewById(R.id.spnCategory)
        editTextAmount = findViewById(R.id.txtAmountInput)
        datePicker = findViewById(R.id.dpDate)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)
        btnCancel = findViewById(R.id.btnCancel)
        btnEnter = findViewById(R.id.btnEnter)

        // Set up category spinner
        setupCategorySpinner()

        // Set current date as default
        setCurrentDate()

        // Set up button click listeners
        setupButtonListeners()
    }

    private fun setupCategorySpinner() {
        // Example categories - replace with your actual categories
        val categories = arrayOf(
            "Food & Dining",
            "Transportation",
            "Shopping",
            "Entertainment",
            "Utilities",
            "Rent/Mortgage",
            "Healthcare",
            "Education",
            "Other"
        )

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categories
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerCategory.adapter = adapter
    }

    private fun setCurrentDate() {
        val calendar = Calendar.getInstance()
        datePicker.init(
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH),
            null
        )
    }

    private fun setupButtonListeners() {
        // Upload Photo Button
        btnUploadPhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        // Cancel Button
        btnCancel.setOnClickListener {
            finish() // Close the activity
        }

        // Enter Button
        btnEnter.setOnClickListener {
            if (validateInput()) {
                saveExpense()
            }
        }
    }

    private fun validateInput(): Boolean {
        if (editTextAmount.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter an amount", Toast.LENGTH_SHORT).show()
            return false
        }

        try {
            editTextAmount.text.toString().toDouble()
        } catch (e: NumberFormatException) {
            Toast.makeText(this, "Please enter a valid amount", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

    private fun saveExpense() {
        val category = spinnerCategory.selectedItem.toString()
        val amount = editTextAmount.text.toString().toDouble()
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1 // Month is 0-based
        val year = datePicker.year

        // Format date as yyyy-MM-dd
        val formattedDate = String.format(Locale.getDefault(), "%04d-%02d-%02d", year, month, day)

        // Here you would typically save to a database
        // For now, we'll just show a toast
        val message = "Expense saved: $category - $$amount on $formattedDate"
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()

        // Optionally clear the form or close the activity
        // finish()
    }

    @Deprecated("This method has been deprecated in favor of using the Activity Result API\n      which brings increased type safety via an {@link ActivityResultContract} and the prebuilt\n      contracts for common intents available in\n      {@link androidx.activity.result.contract.ActivityResultContracts}, provides hooks for\n      testing, and allow receiving results in separate, testable classes independent from your\n      activity. Use\n      {@link #registerForActivityResult(ActivityResultContract, ActivityResultCallback)}\n      with the appropriate {@link ActivityResultContract} and handling the result in the\n      {@link ActivityResultCallback#onActivityResult(Object) callback}.")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()
            // Here you would typically display the image thumbnail or process the image
        }
    }
}