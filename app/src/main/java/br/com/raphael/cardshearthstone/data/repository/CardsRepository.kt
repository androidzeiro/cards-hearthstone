package br.com.raphael.cardshearthstone.data.repository

import kotlinx.coroutines.flow.Flow
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import br.com.raphael.cardshearthstone.data.model.dto.Result

interface CardsRepository {
    suspend fun getCards(): Flow<Result<List<CardResponse>>>
}