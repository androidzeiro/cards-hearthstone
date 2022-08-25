package br.com.raphael.cardshearthstone.data.remote

import br.com.raphael.cardshearthstone.data.model.Card
import retrofit2.Response
import retrofit2.http.GET

interface ApiCards {

    @GET("cards/sets/Classic?attack=4&locale=ptBR")
    suspend fun getCards(): Response<List<Card>>
}