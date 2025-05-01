package vcmsa.projects.krackshackbanking.Budget

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.android.gms.common.util.UidVerifier
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import vcmsa.projects.krackshackbanking.R

class AddCategory {


//     "category" {
//         "CategoryID": "cat_testCate",
//         "Name": "testCat",
//         "UID": 44444,
//         "totalCost": 10000
//       }

    private lateinit var _database: FirebaseDatabase
    private lateinit var _auth: FirebaseAuth


    //
    fun getCategory(category: String): Boolean { // looks for current selected category

        return false
    }

    fun CreateCategory() // if user selects create new category , we go here
    {

        // here we will have a dialogue box to create new category

        var categoryName = ""
        var categoryID = ""
        var categoryTotalCost = 0
        var UID = _auth.currentUser?.uid

        var _catergory = BudgetModel(categoryID, categoryName, UID.toString(), categoryTotalCost)


    }

    fun openDialog(context: Context, onSubmit: (String) -> Unit) {
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