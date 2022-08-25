package br.com.raphael.cardshearthstone.ui.view.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearSnapHelper
import br.com.raphael.cardshearthstone.data.model.dto.State
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import br.com.raphael.cardshearthstone.databinding.FragmentCardsBinding
import br.com.raphael.cardshearthstone.ui.base.BaseFragment
import br.com.raphael.cardshearthstone.ui.view.cards.adapter.CardsAdapter
import br.com.raphael.cardshearthstone.ui.viewmodel.CardsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardsFragment : BaseFragment<FragmentCardsBinding>(FragmentCardsBinding::inflate) {

    private val cardsViewModel: CardsViewModel by viewModels()
    private val cardsAdapter: CardsAdapter by lazy {
        CardsAdapter { card -> onCardClicked(card) }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupObservers()
        cardsViewModel.getCards()
    }

    private fun setupViews() {
        val snapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(binding.rvCards)
        binding.rvCards.adapter = cardsAdapter
    }

    private fun setupObservers() {
        cardsViewModel.cardsState.observe(viewLifecycleOwner) { state ->
            when (state) {
                is State.Success -> {
                    cardsAdapter.submitList(state.value)
                }
                is State.Error -> {
                    println(state.message)
                }
            }
        }
    }

    private fun onCardClicked(card: CardResponse) {
        println(card)
    }
}