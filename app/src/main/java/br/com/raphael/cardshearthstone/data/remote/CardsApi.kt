package br.com.raphael.cardshearthstone.data.remote

import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import retrofit2.Response
import retrofit2.http.GET

interface CardsApi {

    @GET("cards/sets/Classic?attack=4&locale=ptBR")
    suspend fun getCards(): Response<List<CardResponse>>
}