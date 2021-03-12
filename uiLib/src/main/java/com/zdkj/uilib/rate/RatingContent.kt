package com.zdkj.uilib.rate

import android.content.Context
import android.widget.RatingBar
import com.zdkj.ktx.ext.logd
import com.zdkj.uilib.R
import com.zdkj.uilib.databinding.UixDialogContentRatingBinding
import com.zdkj.uilib.dialog.content.ViewBindingDialogContent

/**
 * Rating dialog content
 *
 * @author <a href="mailto:shouheng2015@gmail.com">WngShhng</a>
 * @version 2020-07-25 10:53
 */
class RatingContent : ViewBindingDialogContent<UixDialogContentRatingBinding>() {

    private var rating: Int = 0

    override fun doCreateView(ctx: Context) {
        binding.rb.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { _, rating, _ ->
            "$rating".logd()
            this.rating = rating.toInt()
            when(this.rating) {
                0->{ binding.ivFacial.setImageResource(R.drawable.uix_ic_facial_crying) }
                1->{ binding.ivFacial.setImageResource(R.drawable.uix_ic_facial_crying) }
                2->{ binding.ivFacial.setImageResource(R.drawable.uix_ic_facial_sad) }
                3->{ binding.ivFacial.setImageResource(R.drawable.uix_ic_facial_unhappy) }
                4->{ binding.ivFacial.setImageResource(R.drawable.uix_ic_facial_happy) }
                5->{ binding.ivFacial.setImageResource(R.drawable.uix_ic_facial_very_happy) }
            }
        }
    }

    fun getRating(): Int = rating
}