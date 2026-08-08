package com.example.dcsg2_practical7

import androidx.compose.runtime.currentComposer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dcsg2_practical7.model.Contact
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlin.time.Duration.Companion.milliseconds

class ListViewModel : ViewModel() {

    //This variable's value are changeable.
    // Modifications of a value is made through this variable
    private val _items = MutableStateFlow<List<Contact>>(emptyList()) //private

    //This variable is read-only. This variable is used for display purposes only.
    val items = _items.asStateFlow() //public

    private val _name = MutableStateFlow("")
    val name = _name.asStateFlow()

    private val _phone = MutableStateFlow("")
    val phone = _phone.asStateFlow()

    private var nextId = 0

    private var _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    fun loadSampleContacts() {
        viewModelScope.launch {
            _isLoading.value = true
            delay(2000.milliseconds)

            //Imagine if this is from the database or from something that needs waiting
            _items.update { current ->
                current + listOf<Contact>(
                    Contact(nextId++, "Aisyah", "0123456789"),
                    Contact(nextId++, "Daniel", "0123456789"),
                    Contact(nextId++, "MeiLing", "0123456789"),
                )
            }

            _isLoading.value = false
        }
    }

    fun addFromInput() {
        val name = _name.value.trim()
        if (name.isEmpty()) {
            return
        } else {
            add(
                Contact(
                    id = nextId++,
                    name = name,
                    phone = _phone.value.trim()
                )
            )
            _name.value = ""
            _phone.value = ""
        }

    }

    fun onNameChange(name: String) { // name Setter
        _name.value = name
    }

    fun onPhoneChange(phone: String) { //phone Setter
        _phone.value = phone
    }

    fun add(c: Contact) {
        _items.update { current -> current + c }
    }

    fun remove(c: Contact) {
        _items.update { current -> current - c }
    }
}