package vcmsa.projects.krackshackbanking

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Dashboard : AppCompatActivity() {

    private lateinit var _data: DatabaseReference
    private lateinit var _auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dashboard) // replace with your correct layout resource name

        _auth = FirebaseAuth.getInstance()
        _data = FirebaseDatabase.getInstance().reference
    }

    fun srtBudget() {
        // Initialize your budget logic
    }
}
