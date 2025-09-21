package vcmsa.projects.krackshackbanking.Budget

import android.app.AlertDialog
import android.content.Context
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import vcmsa.projects.krackshackbanking.R

class AddCategory(private val context: Context) {


    // referebce json object
//     "category" {
//         "CategoryID": "cat_testCate",
//         "Name": "testCat",
//         "UID": 44444,
//         "totalCost": 10000
//       }


    // database tokens

    private lateinit var _database: FirebaseDatabase
    private lateinit var _auth: FirebaseAuth


    fun getCategory(category: String): Boolean {
        return false
    }

    fun CreateCategory() // if user selects create new category , we go here
    {
        //here we initialise our category json attributes
        var categoryName = ""
        var categoryID = ""
        var categoryTotalCost = 0
        var UID = _auth.currentUser?.uid

        // to add a category the app opens a dialog pop up to enter category name
        // then it stores the new category here
        openDialog(context = context, onSubmit = { categoryName = it })
        var _category = CategoryModel(categoryID, categoryName, UID.toString(), categoryTotalCost)
    }


    //method to control opening of the dialoug box
    fun openDialog(context: Context, onSubmit: (String) -> Unit) {

        // we made a custom view for the dialog which we initialise here
        val dialog = AlertDialog.Builder(context).create()
        val view = LayoutInflater.from(context).inflate(R.layout.category_dialogue, null)
        val categoryNameEditText = view.findViewById<EditText>(R.id.txtCategoryInput)
        view.findViewById<Button>(R.id.btnCancel).setOnClickListener {
            dialog.dismiss()
        }
        view.findViewById<Button>(R.id.btnEnter).setOnClickListener {
            val categoryName = categoryNameEditText.text.toString()
            if (categoryName.isNotEmpty()) {
                onSubmit(categoryName)
                dialog.dismiss()
            }
        }
        dialog.setView(view)
        dialog.show()
    }
}