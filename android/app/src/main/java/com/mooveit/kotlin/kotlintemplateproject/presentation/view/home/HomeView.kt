package com.mooveit.kotlin.kotlintemplateproject.presentation.view.home

import com.mooveit.kotlin.kotlintemplateproject.data.entity.Pet
import com.mooveit.kotlin.kotlintemplateproject.presentation.common.view.ProgressView

interface HomeView : ProgressView {

    fun showPets(pets: List<Pet>)

    fun showPet(pet: Pet)

    fun showEditPet(pet: Pet)

    fun showPets()

    fun showError(message: String)
}
