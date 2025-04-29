package vcmsa.projects.krackshackbanking.Expense

data class ExpenseModel
    (
    // Expense {
    //     "Amount": 12,
    //     "CategoryID": "cat_testCat",
    //     "Date": "12/04/2045",
    //     "Description": "pnp",
    //     "Image": "filepath",
    //     "UID": 44444,
    //     "expenseID": "12/04/2025_pnp"
    //   },

        val name: String? = null,
        val UID: String = "",
        val categoryID: String = "",
        val cost: String = "",
        val date: String = "",
        val imagePath: String = ""
            ){


}