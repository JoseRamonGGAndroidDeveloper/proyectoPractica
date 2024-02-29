package viewModel.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a.R
import com.example.a.databinding.ActivityManagerBinding

class ManagerActivity : AppCompatActivity() {

    private lateinit var managerBindinginding: ActivityManagerBinding
    override fun onCreate(savedInstanceState: Bundle?) {

        managerBindinginding = ActivityManagerBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(managerBindinginding.root)

        managerBindinginding.btnManagerFacturas.setOnClickListener(){
            val intent = Intent(this, FacturasActivity::class.java)
            startActivity(intent)
        }

        managerBindinginding.btnManagerSmartSolar.setOnClickListener(){
            val intent = Intent(this, SmartSolarActivity::class.java)
            startActivity(intent)
        }
    }
}