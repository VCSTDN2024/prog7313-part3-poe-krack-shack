package vcmsa.projects.krackshackbanking.Expense

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await
import vcmsa.projects.krackshackbanking.R
import java.util.Date



// here we will put expense by category  --- expense view
 class ExpenseHandler: AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.expenses)
  }


}
