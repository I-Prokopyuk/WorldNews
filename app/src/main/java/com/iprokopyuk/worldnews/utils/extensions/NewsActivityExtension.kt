package com.iprokopyuk.worldnews.utils.extensions

import android.content.Context
import android.view.View
import androidx.viewpager2.widget.ViewPager2
import com.iprokopyuk.worldnews.R
import com.iprokopyuk.worldnews.models.NewsCategory
import com.iprokopyuk.worldnews.utils.widget.RecyclerViewItemDecoration
import com.iprokopyuk.worldnews.views.CategoryAdapter
import com.iprokopyuk.worldnews.views.NewsActivity
import kotlinx.android.synthetic.main.content_main.*

fun NewsActivity.initializingCategoryNavigation(context: Context) {

    viewPager.offscreenPageLimit = 1

    val nextItemVisiblePx = resources.getDimension(R.dimen.viewpager_next_item_visible)
    val currentItemHorizontalMarginPx =
        resources.getDimension(R.dimen.viewpager_current_item_horizontal_margin)
    val pageTranslationX = nextItemVisiblePx + currentItemHorizontalMarginPx
    val pageTransformer = ViewPager2.PageTransformer { page: View, position: Float ->
        page.translationX = -pageTranslationX * position
// Next line scales the item's height. You can remove it if you don't want this effect
        page.scaleY = 1 - (0.25f * kotlin.math.abs(position))
// If you want a fading effect uncomment the next line:
        page.alpha = 0.25f + (1 - kotlin.math.abs(position))
    }
    viewPager.setPageTransformer(pageTransformer)
// The ItemDecoration gives the current item horizontal margin so that it doesn't occupy the whole screen width.
    val itemDecoration = RecyclerViewItemDecoration(
        this,
        R.dimen.viewpager_current_item_horizontal_margin
    )
    viewPager.addItemDecoration(itemDecoration)

    viewPager.adapter = CategoryAdapter(
        categoryNavigation.getListCategory() as ArrayList<NewsCategory>,
        newsViewModel
    )
}