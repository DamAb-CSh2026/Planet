package com.example.planet

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {

    private var twoPane = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val root = findViewById<View>(R.id.root)
        ViewCompat.setOnApplyWindowInsetsListener(root) { v, insets ->
            val bars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(bars.left, bars.top, bars.right, bars.bottom)
            insets
        }

        setSupportActionBar(findViewById(R.id.toolbar))

        // Контейнер деталей есть только в layout-sw600dp
        twoPane = findViewById<View>(R.id.detail_container) != null

        if (savedInstanceState == null) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.add(R.id.main_container, PlanetListFragment())
            if (twoPane) {
                transaction.add(R.id.detail_container, PlanetDetailFragment.newInstance(-1))
            }
            transaction.commit()
        }

        supportFragmentManager.setFragmentResultListener(
            PlanetListFragment.REQUEST_KEY, this
        ) { _, bundle ->
            showPlanet(bundle.getInt(PlanetListFragment.KEY_PLANET))
        }

        supportFragmentManager.addOnBackStackChangedListener { updateUpButton() }
        updateUpButton()
    }

    private fun showPlanet(order: Int) {
        val detail = PlanetDetailFragment.newInstance(order)
        val transaction = supportFragmentManager.beginTransaction()
        if (twoPane) {
            transaction.replace(R.id.detail_container, detail)
        } else {
            transaction.replace(R.id.main_container, detail)
            transaction.addToBackStack(null)
        }
        transaction.commit()
    }

    private fun updateUpButton() {
        val hasBackStack = supportFragmentManager.backStackEntryCount > 0
        supportActionBar?.setDisplayHomeAsUpEnabled(hasBackStack)
    }

    override fun onSupportNavigateUp(): Boolean {
        supportFragmentManager.popBackStack()
        return true
    }
}