package vcmsa.projects.krackshackbanking

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

private lateinit var bottomNavigationView: BottomNavigationView

class BarGraphActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.bar_graph)
        bottomNavigationView = findViewById(R.id.bottomNavigationView)
        bottomNavigationView.selectedItemId = R.id.navigation_graph

        // Setup bottom navigation
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    startActivity(Intent(this, Dashboard::class.java))
                    finish()
                    true
                }

                R.id.navigation_graph -> {
                    startActivity(Intent(this, BarGraphActivity::class.java))
                    finish()
                    true
                }

                R.id.navigation_money -> {
                    // TODO: Replace with Money activity when available
                    // startActivity(Intent(this, MoneyActivity::class.java))
                    // finish()
                    true
                }

                R.id.navigation_profile -> {
                    // TODO: Replace with Profile activity when available
                    // startActivity(Intent(this, ProfileActivity::class.java))
                    // finish()
                    true
                }

                R.id.navigation_menu -> {
                    // TODO: Replace with Menu activity when available
                    // startActivity(Intent(this, MenuActivity::class.java))
                    // finish()
                    true
                }

                else -> false
            }
        }
    }
}