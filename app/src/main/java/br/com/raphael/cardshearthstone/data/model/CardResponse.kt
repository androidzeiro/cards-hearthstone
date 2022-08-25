package br.com.raphael.cardshearthstone.data.model

data class CardResponse(
    val cardId: String,
    val img: String,
    val name: String,
    val flavor: String,
    val text: String,
    val cardSet: String,
    val type: String,
    val faction: String = "Sem facção",
    val rarity: String,
    val attack: Int,
    val cost: Int,
    val health: Int
)