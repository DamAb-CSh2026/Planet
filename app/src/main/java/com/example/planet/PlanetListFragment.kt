package com.example.planet

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class PlanetListFragment : Fragment(R.layout.fragment_planet_list) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val listView = view.findViewById<ListView>(R.id.listPlanets)
        listView.adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_list_item_activated_1,
            PlanetRepository.planets.map { it.name }
        )

        // Фрагмент только сообщает о выборе; решение принимает Activity
        listView.setOnItemClickListener { _, _, position, _ ->
            val order = PlanetRepository.planets[position].order
            parentFragmentManager.setFragmentResult(
                REQUEST_KEY, bundleOf(KEY_PLANET to order)
            )
        }
    }

    companion object {
        const val REQUEST_KEY = "planet_selected"
        const val KEY_PLANET = "planet_order"
    }
}