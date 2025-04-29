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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import vcmsa.projects.krackshackbanking.R
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale
import java.util.UUID

class AddExpense : AppCompatActivity() {
    // UI Components
    private lateinit var spinnerCategory: Spinner
    private lateinit var editTextAmount: EditText
    private lateinit var editTextDescription: EditText
    private lateinit var datePicker: DatePicker
    private lateinit var btnUploadPhoto: Button
    private lateinit var btnCancel: Button
    private lateinit var btnEnter: Button
    private val db = FirebaseFirestore.getInstance()
    private val storage = FirebaseStorage.getInstance()
    private val auth = FirebaseAuth.getInstance()

    // For photo upload
    private val pickImage = 100
    private var imageUri: Uri? = null
    private var imagePath: String = ""

    // Categories map (category name to category ID)
    private val categoriesMap = mapOf(
        "Food & Dining" to "cat_food",
        "Transportation" to "cat_transport",
        "Shopping" to "cat_shopping",
        "Entertainment" to "cat_entertainment",
        "Utilities" to "cat_utilities",
        "Rent/Mortgage" to "cat_rent",
        "Healthcare" to "cat_healthcare",
        "Education" to "cat_education",
        "Other" to "cat_other"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.expense_create)

        // Initialize UI components
        spinnerCategory = findViewById(R.id.spnCategory)
        editTextAmount = findViewById(R.id.txtAmountInput)
        datePicker = findViewById(R.id.dpDate)
        btnUploadPhoto = findViewById(R.id.btnUploadPhoto)
        btnCancel = findViewById(R.id.btnCancel)
        btnEnter = findViewById(R.id.btnEnter)

        // You'll need to add an EditText for description in your XML
        // editTextDescription = findViewById(R.id.txtDescriptionInput)

        // Set up category spinner
        setupCategorySpinner()

        // Set current date as default
        setCurrentDate()

        // Set up button click listeners
        setupButtonListeners()
    }

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
        // Upload Photo Button
        btnUploadPhoto.setOnClickListener {
            val gallery = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            startActivityForResult(gallery, pickImage)
        }

        // Cancel Button
        btnCancel.setOnClickListener {
            finish()
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

        // Add validation for description if needed
        // if (editTextDescription.text.toString().trim().isEmpty()) {
        //     Toast.makeText(this, "Please enter a description", Toast.LENGTH_SHORT).show()
        //     return false
        // }

        return true
    }

    private fun saveExpense() {
        // Get current user
        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            return
        }

        // Get form data
        val categoryName = spinnerCategory.selectedItem.toString()
        val categoryID = categoriesMap[categoryName] ?: "cat_other"
        val amount = editTextAmount.text.toString()
        val day = datePicker.dayOfMonth
        val month = datePicker.month + 1
        val year = datePicker.year
        val formattedDate = String.format(Locale.getDefault(), "%02d/%02d/%04d", day, month, year)
        val description = "pnp" // Replace with actual description field
        val expenseID = "${formattedDate}_$description"
        val UID = currentUser.uid

        if (imageUri != null) {
            // Upload image first
            uploadImageToFirebase { imageUrl ->
                // After image upload completes, save expense with image URL
                saveExpenseToFirestore(
                    amount = amount,
                    categoryID = categoryID,
                    date = formattedDate,
                    description = description,
                    imageUrl = imageUrl,
                    UID = UID,
                    expenseID = expenseID
                )
            }
        } else {
            // Save expense without image
            saveExpenseToFirestore(
                amount = amount,
                categoryID = categoryID,
                date = formattedDate,
                description = description,
                imageUrl = "",
                UID = UID,
                expenseID = expenseID
            )
        }
    }
    private fun uploadImageToFirebase(onComplete: (String) -> Unit) {
        imageUri?.let { uri ->
            val fileName = UUID.randomUUID().toString()
            val storageRef = storage.reference.child("expense_images/$fileName")

            storageRef.putFile(uri)
                .addOnSuccessListener { taskSnapshot ->
                    // Get download URL after successful upload
                    storageRef.downloadUrl.addOnSuccessListener { downloadUri ->
                        onComplete(downloadUri.toString())
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Image upload failed: ${e.message}", Toast.LENGTH_SHORT).show()
                    onComplete("") // Continue with empty string if upload fails
                }
        } ?: run {
            onComplete("") // No image to upload
        }
    }
    private fun saveExpenseToFirestore(
        amount: String,
        categoryID: String,
        date: String,
        description: String,
        imageUrl: String,
        UID: String,
        expenseID: String
    ) {
        // Create a map of expense data
        val expense = hashMapOf(
            "Amount" to amount.toDouble(),
            "CategoryID" to categoryID,
            "Date" to date,
            "Description" to description,
            "Image" to imageUrl,
            "UID" to UID,
            "expenseID" to expenseID
        )

        // Add document to Firestore
        db.collection("expenses")
            .add(expense)
            .addOnSuccessListener { documentReference ->
                Toast.makeText(
                    this,
                    "Expense saved with ID: ${documentReference.id}",
                    Toast.LENGTH_LONG
                ).show()
                finish() // Close activity after successful save
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    this,
                    "Error saving expense: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == pickImage) {
            imageUri = data?.data
            imagePath = imageUri?.path ?: ""
            Toast.makeText(this, "Image selected", Toast.LENGTH_SHORT).show()

            // Here you would typically:
            // 1. Display the image thumbnail
            // 2. Upload the image to storage and get the download URL
            // 3. Save the URL/path to your expense object
        }
    }
}