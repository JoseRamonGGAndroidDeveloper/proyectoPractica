package viewModel.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.a.databinding.ActivitySmartSolarBinding

class SmartSolarActivity : AppCompatActivity() {

    private lateinit var solarBinding: ActivitySmartSolarBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        solarBinding = ActivitySmartSolarBinding.inflate(layoutInflater)

        super.onCreate(savedInstanceState)
        setContentView(solarBinding.root)

        val fragmentsAdapter = FragmentsAdapter(supportFragmentManager)

        fragmentsAdapter.addFragment(InstalacionFragment(), "Mi instalación")
        fragmentsAdapter.addFragment(EnergiaFragment(), "Energía")
        fragmentsAdapter.addFragment(DetallesFragment(), "Detalles")

        solarBinding.ViewPagerSmartSolar.adapter=fragmentsAdapter
        solarBinding.tabSmartSolar.setupWithViewPager(solarBinding.ViewPagerSmartSolar)

        solarBinding.btnAtrasSmartSolar.setOnClickListener(){
            val intent = Intent(this, ManagerActivity::class.java)
            startActivity(intent)
        }
    }
}