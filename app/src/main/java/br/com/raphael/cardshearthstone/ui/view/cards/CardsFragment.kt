package br.com.raphael.cardshearthstone.ui.view.cards

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearSnapHelper
import br.com.raphael.cardshearthstone.R
import br.com.raphael.cardshearthstone.data.model.dto.State
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import br.com.raphael.cardshearthstone.databinding.FragmentCardsBinding
import br.com.raphael.cardshearthstone.ui.base.BaseFragment
import br.com.raphael.cardshearthstone.ui.view.cards.adapter.CardsAdapter
import br.com.raphael.cardshearthstone.ui.viewmodel.CardsViewModel
import com.google.android.material.snackbar.Snackbar
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
            state?.let {
                binding.progressBar.visibility = View.INVISIBLE
                when (state) {
                    is State.Success -> {
                        cardsAdapter.submitList(state.value)
                    }
                    is State.Error -> {
                        val snackbar = Snackbar.make(binding.root, state.message, Snackbar.LENGTH_INDEFINITE)
                        snackbar.setAction(R.string.try_again) {
                            cardsViewModel.getCards()
                            binding.progressBar.visibility = View.VISIBLE
                            snackbar.dismiss()
                        }.show()
                    }
                }
            }
        }
    }

    private fun onCardClicked(card: CardResponse) {
        findNavController().navigate(CardsFragmentDirections.actionCardsFragmentToDetailsFragment(card))
    }
}