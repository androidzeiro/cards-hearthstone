package br.com.raphael.cardshearthstone.ui.view.details

import android.os.Bundle
import android.text.Html
import android.view.View
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import br.com.raphael.cardshearthstone.R
import br.com.raphael.cardshearthstone.databinding.FragmentDetailsBinding
import br.com.raphael.cardshearthstone.ui.base.BaseFragment
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(FragmentDetailsBinding::inflate) {

    private val args: DetailsFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViews()
        setupListeners()
    }

    private fun setupViews() {
        val card = args.card

        binding.apply {
            Glide
                .with(ivCard)
                .load(card.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .dontTransform()
                .dontAnimate()
                .into(ivCard)

            tvName.text = card.name
            if (card.flavor == null) {
                tvFlavor.visibility = View.GONE
            } else {
                tvFlavor.text = Html.fromHtml(card.flavor.replace("\\n", "<br>"))
            }
            if (card.text == null) {
                tvText.visibility = View.GONE
            } else {
                tvText.text = Html.fromHtml(card.text.replace("\\n", "<br>"))
            }
            tvCardSet.text = getString(R.string.card_set, card.cardSet)
            tvType.text = getString(R.string.type, card.type)
            tvFaction.text = getString(R.string.faction, card.faction ?: "Nenhuma")
            tvRarity.text = getString(R.string.rarity, card.rarity ?: "Nenhuma")
            tvAttack.text = getString(R.string.attack, card.attack)
            tvCost.text = getString(R.string.cost, card.cost)
            tvHealth.text = getString(R.string.health, card.health)
        }
    }

    private fun setupListeners() {
        binding.toolbar.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }
}