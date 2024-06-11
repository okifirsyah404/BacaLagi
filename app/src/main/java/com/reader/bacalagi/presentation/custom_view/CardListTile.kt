package com.reader.bacalagi.presentation.custom_view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.cardview.widget.CardView
import com.reader.bacalagi.R
import com.reader.bacalagi.databinding.ItemLayoutCardListTileBinding

class CardListTile @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : CardView(context, attrs, defStyleAttr) {

    private val binding: ItemLayoutCardListTileBinding

    init {
        // Inflate the layout and attach it to this CardView
        val inflater = LayoutInflater.from(context)
        binding = ItemLayoutCardListTileBinding.inflate(inflater, this, true)

        // Read the custom attributes
        context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.CardListTile,
            0, 0
        ).apply {
            try {
                // Set the prefix icon
                val prefixIcon = getResourceId(R.styleable.CardListTile_prefixIcon, 0)
                if (prefixIcon != 0) {
                    binding.ivPrefix.setImageResource(prefixIcon)
                } else {
                    binding.ivPrefix.visibility = GONE
                }

                // Set the suffix icon
                val suffixIcon = getResourceId(R.styleable.CardListTile_suffixIcon, 0)
                if (suffixIcon != 0) {
                    binding.ivSuffix.setImageResource(suffixIcon)
                } else {
                    binding.ivSuffix.visibility = GONE
                }

                // Set the title text
                val title = getString(R.styleable.CardListTile_title)
                binding.tvTitle.text = title
            } finally {
                recycle()
            }
        }

        background = null

        // Set default properties of CardView
        cardElevation = 4f
        radius = 8f
    }
}