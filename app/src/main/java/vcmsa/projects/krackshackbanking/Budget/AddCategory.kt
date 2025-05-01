package vcmsa.projects.krackshackbanking.Budget

import android.os.Bundle
import androidx.compose.ui.graphics.vector.ImageVector
import com.google.android.gms.common.util.UidVerifier
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class AddCategory {



//     "category" {
//         "CategoryID": "cat_testCate",
//         "Name": "testCat",
//         "UID": 44444,
//         "totalCost": 10000
//       }

  private lateinit var _database : FirebaseDatabase
  private lateinit var _auth : FirebaseAuth



  //
  fun getCategory(ctaegory: String): Boolean { // looks for current selected category

  return false
  }

  fun CreateCategory() // if user selects create new category , we go here
  {

    // here we will have a dialouge box to create new category

    var categoryName = ""
    var categoryID = ""
    var categoryTotalCost = 0
    var UID = _auth.currentUser?.uid

    var _catergoy = BudgetModel(categoryID,categoryName, UID.toString(),categoryTotalCost)





  }

  fun DialougeBox
    (
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    dialogText: String,

  )
  {


    }


}