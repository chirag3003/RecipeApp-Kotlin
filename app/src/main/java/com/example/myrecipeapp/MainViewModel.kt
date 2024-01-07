package com.example.myrecipeapp

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {
    private val _categoriesState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoriesState

    init{
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                Log.d("Loc", "here 1")
                val response = recipeService.getCategories()
                Log.d("Loc", "here 2")

                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    list = response.categories
                )
            } catch (e: Error) {
                Log.e("Recipe", e.toString())
                _categoriesState.value = _categoriesState.value.copy(
                    loading = false,
                    error = "Error fetching data: ${e.message}",
                )
            }
        }
    }

    data class RecipeState(
        val loading: Boolean = true,
        val list: List<Category> = emptyList(),
        val error: String? = null
    )
}