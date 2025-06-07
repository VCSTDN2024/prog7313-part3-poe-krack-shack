package vcmsa.projects.krackshackbanking.Expense

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import vcmsa.projects.krackshackbanking.Dashboard
import vcmsa.projects.krackshackbanking.R
import java.util.Calendar
import java.util.Locale
import java.util.Objects
import java.util.UUID
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class AddExpense : AppCompatActivity() {


    // xml components
    private lateinit var _categorySpinner: Spinner
    private lateinit var _datePicker: DatePicker
    private lateinit var _amountEditText: EditText
    private lateinit var _descriptionEditText: EditText
    private lateinit var _submitButton: Button
    private lateinit var _cancelButton: Button
    private lateinit var _imageUri: Uri
    private val CAMERA_PERMISSION_REQUEST_CODE = 100
    private val CAMERA_REQUEST_CODE = 101
    private lateinit var imageView: ImageView
    private lateinit var captureButton: Button


    // read data array from database
    private lateinit var _catArray: Array<String>


    // databsase list
    private val _expense = mutableListOf<Pair<String,ExpenseModel>>()
    // database reference
    private lateinit var _data: DatabaseReference
    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // initialising all components
        setContentView(R.layout.expense_create)
        _categorySpinner = findViewById(R.id.spnCategory)
        _datePicker = findViewById(R.id.dpDate)
        _amountEditText = findViewById(R.id.txtAmountInput)
        _descriptionEditText = findViewById(R.id.txtDescription)
        _submitButton = findViewById(R.id.btnEnter)
        _cancelButton = findViewById(R.id.btnCancel)
        imageView = findViewById(R.id.image_view)
        _data = FirebaseDatabase.getInstance().reference
        //here we make the array for the spinner
        _catArray = RetrieveData()
        // Temporary date
        val today = Calendar.getInstance()
        _datePicker.updateDate(
            today.get(Calendar.YEAR),
            today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)
        )
        captureButton = findViewById(R.id.button_capture)
        captureButton.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    this, Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            )
            {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.CAMERA),
                    CAMERA_PERMISSION_REQUEST_CODE
                )
            } else
            {
                openCamera()
            }
        }

        // converting array to spinner array because kotlin
        val convert: ArrayAdapter<*> =
            ArrayAdapter<Any?>(this, android.R.layout.simple_spinner_item, _catArray)
        convert.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        _categorySpinner.adapter = convert

        // once the user is happy the submit an expense entry to the database
        _submitButton.setOnClickListener {
            val category = _categorySpinner.selectedItem.toString()
            val date =
                _datePicker.dayOfMonth.toString() + "/" + _datePicker.month.toString() + "/" + _datePicker.year.toString()
            val amount = _amountEditText.text.toString().toFloat()
            val description = _descriptionEditText.text.toString()
            val id = UUID.randomUUID().toString()
            var expense = ExpenseModel(id,category, date, amount, description.toString(), _imageUri.toString())

            var UID = FirebaseAuth.getInstance().currentUser?.uid.toString()

            _data.child(UID).child("Expenses").child(id).setValue(expense)


            val Intent = Intent(this, Dashboard::class.java)
            startActivity(Intent)
        }

        // cancel button to return to dashboard
        _cancelButton.setOnClickListener {
            val intent = Intent(this, Dashboard::class.java)
            startActivity(intent)
        }

    }

    // This method is called once with the initial value and again
    // whenever data at this location is updated.
    private fun RetrieveData(): Array<String> {
        var _getData: Array<String> = emptyArray()
        _getData += "Food"
        _getData += "Water"
        _getData += "Entertainment"
        _getData += "Transportation"
        _getData += "Add Category"
        try {
            _data.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    dataSnapshot.children.forEach { snapshot ->
                        val category = snapshot.getValue(String::class.java)
                        _getData += (category.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                }
            })
            return _getData
        } catch (e: Exception) {
            // failed exception handling here
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            return _getData
        }
    }

    private fun openCamera()
    {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null)
        {
            startActivityForResult(cameraIntent, CAMERA_REQUEST_CODE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?)
    {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERA_REQUEST_CODE && resultCode ==
            RESULT_OK
        )
        {
            val extras = data?.extras
            val imageBitmap = extras?.get("data") as Bitmap
            imageView.setImageBitmap(imageBitmap)
            imageView.visibility = View.VISIBLE
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>, grantResults: IntArray
    )
    {
        super.onRequestPermissionsResult(
            requestCode, permissions,
            grantResults
        )
        if (requestCode == CAMERA_PERMISSION_REQUEST_CODE)
        {
            if (grantResults.isNotEmpty() && grantResults[0] ==
                PackageManager.PERMISSION_GRANTED
            )
            {
                openCamera()
            }
        }
    }
}





