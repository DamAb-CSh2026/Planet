package com.example.planet

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment

class PlanetDetailFragment : Fragment(R.layout.fragment_planet_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textName = view.findViewById<TextView>(R.id.textName)
        val textOrder = view.findViewById<TextView>(R.id.textOrder)
        val textDiameter = view.findViewById<TextView>(R.id.textDiameter)
        val textDescription = view.findViewById<TextView>(R.id.textDescription)

        val order = arguments?.getInt(ARG_ORDER, -1) ?: -1
        val planet = PlanetRepository.find(order)

        if (planet == null) {
            textName.text = "Планета не выбрана"
            textDescription.text = "Выберите планету в списке слева."
            return
        }

        textName.text = planet.name
        textOrder.text = "Порядковый номер от Солнца: ${planet.order}"
        textDiameter.text = "Диаметр: ≈ %,d км".format(planet.diameterKm)
        textDescription.text = planet.description
    }

    companion object {
        private const val ARG_ORDER = "planet_order"

        fun newInstance(order: Int) = PlanetDetailFragment().apply {
            arguments = bundleOf(ARG_ORDER to order)
        }
    }
}