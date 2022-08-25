package br.com.raphael.cardshearthstone.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import br.com.raphael.cardshearthstone.data.repository.CardsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import br.com.raphael.cardshearthstone.data.model.dto.Result
import br.com.raphael.cardshearthstone.data.model.dto.State
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardsViewModel @Inject constructor(
    private val cardsRepository: CardsRepository
) : ViewModel() {

    init {
        getCards()
    }

    private val _cardsState = MutableLiveData<State<List<CardResponse>>>()
    val cardsState: LiveData<State<List<CardResponse>>> = _cardsState

    fun getCards() {
        viewModelScope.launch {
            cardsRepository.getCards().collect { result ->
                when (result) {
                    is Result.Success -> {
                        _cardsState.postValue(State.Success(result.value))
                    }
                    is Result.Error -> {
                        _cardsState.postValue(State.Error(result.message))
                    }
                    is Result.NetworkError -> {
                        _cardsState.postValue(State.Error(result.message))
                    }
                }
            }
        }
    }
}