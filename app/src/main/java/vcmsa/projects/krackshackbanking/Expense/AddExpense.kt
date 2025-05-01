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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import vcmsa.projects.krackshackbanking.R
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddExpense : AppCompatActivity() {
    // xml elements
   /* private lateinit var spinnerCategory: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var btnUploadPhoto: Button
    private lateinit var btnCancel: Button
    private lateinit var btnEnter: Button

    // firebase elements
    private val auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val user = auth.currentUser
    private val uid = user?.uid
    private val expenseHandler = ExpenseHandler()
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imageUrl: String = ""

    private val categoriesMap = mapOf(
        "Food & Dining" to "cat_food",
        "Transportation" to "cat_transport",
        "Shopping" to "cat_shopping",
        "Entertainment" to "cat_entertainment",
        "Utilities" to "cat_utilities",
        "Rent/Mortgage" to "cat_rent",
        "Healthcare" to "cat_healthcare",
        "Education" to "cat_education",
        "Create New" to "create_new"

        // so are we making these our prebuilt categories
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_create)

        initializeViews()
        setupCategorySpinner()
        setCurrentDate()
        setupButtonListeners()
    }

    private fun initializeViews() {
        spinnerCategory = findViewById(R.id.spnCategory)
        editTextAmount = findViewById(R.id.txtAmountInput)
        //editTextDescription = findViewById(R.id.txtDescriptionInput)
        datePicker = findViewById(R.id.dpDate)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)
        btnCancel = findViewById(R.id.btnCancel)
        btnEnter = findViewById(R.id.btnEnter)
    }

    // when the user selects new they should recive a pop up to create a new category
    // code here https://developer.android.com/develop/ui/compose/quick-guides/content/display-user-input
    private fun setupCategorySpinner() {
        val categories = categoriesMap.keys.toList()
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
        btnUploadPhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        btnCancel.setOnClickListener {
            finish()
        }

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

        if (editTextDescription.text.toString().trim().isEmpty()) {
            Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
            return false
        }

        return true
    }

   *//* private fun saveExpense() {
        if (imageUri != null) {
            uploadImageToFirebase { url ->
                imageUrl = url
                createExpense()
            }
        } else {
            createExpense()
        }
    }*//*

   *//* private fun uploadImageToFirebase(onComplete: (String) -> Unit) {
        imageUri?.let { uri ->
            val fileName = UUID.randomUUID().toString()
            val storageRef = storage.reference.child("expense_images/$fileName")

            storageRef.putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        onComplete(downloadUri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Image upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    onComplete("")
                }
        } ?: run {
            onComplete("")
        }
    }*//*

    private fun createExpense() {
        val categoryName = spinnerCategory.selectedItem.toString()
        val categoryID = categoriesMap[categoryName] ?: "cat_other"
        val amount = editTextAmount.text.toString().toDouble()
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year
        val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year)
        val description = editTextDescription.text.toString()
        val expenseID = "${formattedDate}_${UUID.randomUUID()}"

      *//*  val expense = ExpenseModel(
            amount = amount,
            category = categoryID,
            date = formattedDate,
            description = description,
            expenseID = expenseID
        )*//*

*//*        CoroutineScope(Dispatchers.Main).launch {
            try {
                expenseHandler.createExpense(expense)
                Toast.makeText(this@AddExpense, "Expense saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } catch (e: Exception) {
                Toast.makeText(this@AddExpense, "Error saving expense: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
        }
    }
    */

}