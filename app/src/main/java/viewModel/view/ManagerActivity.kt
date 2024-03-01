package viewModel.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a.R
import com.example.a.databinding.ActivityManagerBinding

class ManagerActivity : AppCompatActivity() {

    private lateinit var managerBinding: ActivityManagerBinding

    var isRetrofitSelected: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {

        managerBinding = ActivityManagerBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(managerBinding.root)

        goFacturas()
        goSmartSolar()

        managerBinding.rbRetroFit.setOnClickListener() {
            isRetrofitSelected = true
        }

        managerBinding.rbRetroMock.setOnClickListener() {
            isRetrofitSelected = false
        }

        managerBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbRetroFit -> {
                    isRetrofitSelected = true
                }

                R.id.rbRetroMock -> {
                    isRetrofitSelected = false
                }
            }
        }

    }

    override fun onResume() {

        super.onResume()

        managerBinding.rbRetroFit.setOnClickListener() {
            isRetrofitSelected = true
        }

        managerBinding.rbRetroMock.setOnClickListener() {
            isRetrofitSelected = false
        }

        managerBinding.radioGroup.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.rbRetroFit -> {
                    isRetrofitSelected = true
                }

                R.id.rbRetroMock -> {
                    isRetrofitSelected = false
                }
            }
        }
    }

    fun goSmartSolar() {
        managerBinding.btnManagerSmartSolar.setOnClickListener() {
            val intent = Intent(this, SmartSolarActivity::class.java)
            startActivity(intent)
        }
    }

    fun goFacturas() {
        managerBinding.btnManagerFacturas.setOnClickListener() {
            val intent = Intent(this, FacturasActivity::class.java)
            intent.putExtra("isRetrofitSelected", isRetrofitSelected)
            startActivity(intent)
        }
    }
}