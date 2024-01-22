package com.example.ticket

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.ticket.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    lateinit var bottomNav : BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        val done = done_fragment()
        val process = In_process_fragment()
        val to_do = To_Do_Fragment()
        val archivado = Archived_Ticket()
        loadFragment(to_do)

        setSupportActionBar(findViewById(R.id.toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        findViewById<ImageView>(R.id.imageView).setOnClickListener{
            loadFragment(archivado)
        }



        bottomNav = findViewById(R.id.bottomNav) as BottomNavigationView
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.icon_done -> {
                    loadFragment(done)
                    true
                }
                R.id.icon_in_process -> {
                    loadFragment(process)
                    true
                }
                R.id.icon_to_do -> {
                    loadFragment(to_do)
                    true
                }

                else -> { true}
            }
        }
        binding.floatingButton.setOnClickListener{
            val intent = Intent(this, Add_ticke::class.java)
            startActivity(intent)
        }
    }

    private  fun loadFragment(fragment: Fragment){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragmentContainer,fragment)
            commit()
        }
    }


}