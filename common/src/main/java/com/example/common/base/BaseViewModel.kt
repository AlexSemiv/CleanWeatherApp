package com.example.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event: UiEvent, State: UiState, Effect: UiEffect>: ViewModel() {

    abstract fun createInitialUiState(): State
    private val initialUiState: State by lazy { createInitialUiState() }

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialUiState)
    val uiState = _uiState.asStateFlow()

    val currentState: State
        get() = uiState.value

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    abstract fun handleEvent(event: Event)
    private fun subscribeToEvents(){
        viewModelScope.launch {
            event.collect { event ->
                handleEvent(event)
            }
        }
    }

    fun setEvent(newEvent: Event) {
        viewModelScope.launch {
            _event.emit(newEvent)
        }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(newEffect: Effect) {
        viewModelScope.launch {
            _effect.send(newEffect)
        }
    }
}