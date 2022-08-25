package br.com.raphael.cardshearthstone.ui.view.cards.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.raphael.cardshearthstone.R
import br.com.raphael.cardshearthstone.data.model.response.CardResponse
import br.com.raphael.cardshearthstone.databinding.ItemCardBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.Priority
import com.bumptech.glide.load.engine.DiskCacheStrategy

class CardsAdapter(
    private val onCardClicked: (CardResponse) -> Unit
) : ListAdapter<CardResponse, CardsAdapter.CardViewHolder>(CardsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        return CardViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position), onCardClicked)
    }

    class CardViewHolder private constructor(
        private val binding: ItemCardBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(card: CardResponse, onCardClicked: (CardResponse) -> Unit) {
            Glide
                .with(binding.ivCard)
                .load(card.img)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .priority(Priority.HIGH)
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_placeholder)
                .dontTransform()
                .dontAnimate()
                .into(binding.ivCard)

            binding.ivCard.setOnClickListener {
                onCardClicked.invoke(card)
            }
        }

        companion object {
            fun from(parent: ViewGroup): CardViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemCardBinding.inflate(layoutInflater, parent, false)
                return CardViewHolder(binding)
            }
        }
    }
}

class CardsDiffCallback : DiffUtil.ItemCallback<CardResponse>() {
    override fun areItemsTheSame(oldItem: CardResponse, newItem: CardResponse): Boolean {
        return oldItem.cardId == newItem.cardId
    }

    override fun areContentsTheSame(oldItem: CardResponse, newItem: CardResponse): Boolean {
        return oldItem == newItem
    }
}