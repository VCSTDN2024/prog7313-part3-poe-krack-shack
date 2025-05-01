package vcmsa.projects.krackshackbanking.Budget

import com.google.firebase.firestore.Exclude
import java.util.Date

//"CategoryID": "cat_testCate",
//         "Name": "testCat",
//         "UID": 44444,
//         "totalCost": 10000
data class BudgetModel(
    val CategoryID: String = "",
    val Name: String = "",
    val UID: String = "",
    val totalCost: String = ""
)
{

}