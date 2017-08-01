package com.mooveit.kotlin.kotlintemplateproject.data.entity

class Tag {

    var id: Long = 0
    var name: String? = null

    override fun toString(): String {
        return "Tag(id=$id, name=$name)"
    }
}