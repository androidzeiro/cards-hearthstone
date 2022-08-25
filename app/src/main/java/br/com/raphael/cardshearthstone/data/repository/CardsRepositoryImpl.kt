package br.com.raphael.cardshearthstone.data.repository

import android.content.Context
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import br.com.raphael.cardshearthstone.data.remote.CardsApi
import br.com.raphael.cardshearthstone.data.remote.FlowApiCall
import br.com.raphael.cardshearthstone.data.model.dto.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val cardsApi: CardsApi
) : CardsRepository {

    override suspend fun getCards(): Flow<Result<List<CardResponse>>> = flow {
        FlowApiCall.getResult(context, this) {
            cardsApi.getCards()
        }
    }
}