package com.example.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    abstract fun createInitialUiState(): State
    private val initialUiState: State by lazy { createInitialUiState() }

    /**
     * StateFlow is just like LiveData but have initial value.
     * So we have always a state.
     * It is also a kind of SharedFlow.
     * We always want to receive last view state when UI become visible.
     */
    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialUiState)
    val uiState = _uiState.asStateFlow()

    val currentState: State
        get() = uiState.value

    /**
     * With the shared flow, events are broadcast to an unknown number (zero or more) of subscribers.
     * In the absence of a subscriber, any posted event is immediately dropped.
     * It is a design pattern to use for events that must be processed immediately or not at all.
     */
    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    private val event = _event.asSharedFlow()

    /**
     * With the channel, each event is delivered to a single subscriber.
     * An attempt to post an event without subscribers will suspend as soon as the channel buffer becomes full,
     * waiting for a subscriber to appear.
     * Posted events are never dropped by default.
     */
    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    init {
        subscribeToEvents()
    }

    abstract fun handleEvent(event: Event)
    private fun subscribeToEvents() {
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

    fun setEffect(newEffect: Effect) {
        viewModelScope.launch {
            _effect.send(newEffect)
        }
    }
}