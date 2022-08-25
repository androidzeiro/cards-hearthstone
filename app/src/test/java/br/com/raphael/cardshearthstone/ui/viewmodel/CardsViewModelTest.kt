package br.com.raphael.cardshearthstone.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import br.com.raphael.cardshearthstone.data.model.dto.State
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.test.*
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner
import br.com.raphael.cardshearthstone.data.model.dto.Result
import br.com.raphael.cardshearthstone.data.repository.CardsRepository
import kotlinx.coroutines.flow.flow

@OptIn(ExperimentalCoroutinesApi::class)
@RunWith(MockitoJUnitRunner::class)
class CardsViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    private lateinit var cardsState: Observer<State<List<CardResponse>>?>

    private lateinit var viewModel: CardsViewModel

    @ExperimentalCoroutinesApi
    @Test
    fun `when fun getCards get success`() = runTest {
        // Arrange
        val cards = listOf(
            CardResponse(cardId="NEW1_029", img="https://d15f34w2p8l1cc.cloudfront.net/hearthstone/8c1dc359fe9c5a380222cf086df3b0050dd8131d0cf3d29611217b4a9199bb84.png", name="Millhouse Manavento", flavor="Vou te deixar em brasa, coisa fofa!", text="<b>Grito de Guerra:</b> Feitiços inimigos custam (0) no próximo turno.", cardSet="Classic", type="Minion", faction=null, rarity="Legendary", attack=4, cost=2, health=4),
            CardResponse(cardId="EX1_045", img="https://d15f34w2p8l1cc.cloudfront.net/hearthstone/50cbb62db60ed370da68473aeaf04088369255f6e17d85ad486b55fe99bbee73.png", name="Vigia Anciente", flavor="Por que parece que os olhos dele te seguem quando você passa?", text="Não pode atacar.", cardSet="Classic", type="Minion", faction="Alliance", rarity="Rare", attack=4, cost=2, health=5),
            CardResponse(cardId="EX1_089", img="https://d15f34w2p8l1cc.cloudfront.net/hearthstone/a7698d80f5ecec5faa01251113cc8f3ebe9b1047e2059edddf2dc7a627cb9500.png", name="Golem Arcano", flavor="Ter um Golem Arcano em casa deixa tudo mais chique e ainda é um ótimo tópico para conversas.", text="<b>Investida</b>. <b>Grito de Guerra:</b> Conceda um Cristal de Mana ao seu oponente.", cardSet="Classic", type="Minion", faction="Neutral", rarity="Rare", attack=4, cost=3, health=2)
        )
        val resultSuccess = MockCardsRepository((Result.Success(value = cards, statusCode = 200)))
        viewModel = CardsViewModel(resultSuccess)
        viewModel.cardsState.observeForever(cardsState)

        // Act
        viewModel.getCards()

        // Assert
        advanceUntilIdle()
        verify(cardsState).onChanged(State.Success(cards))
    }

    @ExperimentalCoroutinesApi
    @Test
    fun `when fun getCards get error`() = runTest {
        // Arrange
        val message = "Ops! Algo deu errado."
        val resultError = MockCardsRepository(Result.Error(message = message, statusCode = 500))
        viewModel = CardsViewModel(resultError)
        viewModel.cardsState.observeForever(cardsState)

        // Act
        viewModel.getCards()

        // Assert
        advanceUntilIdle()
        verify(cardsState).onChanged(State.Error(message))
    }
}

class MockCardsRepository(private val result: Result<List<CardResponse>>) : CardsRepository {
    override suspend fun getCards(): Flow<Result<List<CardResponse>>> = flow {
        emit(result)
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) : TestWatcher() {

    override fun starting(description: Description) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}