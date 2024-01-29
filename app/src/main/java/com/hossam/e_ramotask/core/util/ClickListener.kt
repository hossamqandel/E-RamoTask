package com.hossam.e_ramotask.core.util

interface ClickListener {
    
    fun <T> onItemClick(value: T, position: Int)
}