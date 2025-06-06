package vcmsa.projects.krackshackbanking

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity

class NavMenuActivity : AppCompatActivity() {
    val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    true
                }

                R.id.navigation_graph -> {
                    startActivity(Intent(this, BarGraphActivity::class.java))
                    true
                }

                //TODO: Change to Money
                R.id.navigation_money -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    true
                }

                //TODO: Change to Profile
                R.id.navigation_profile -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    true
                }

                //TODO: Change to Manu
                R.id.navigation_menu -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    true
                }

                else -> false
            }
        }
    }
}